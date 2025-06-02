package net.dragonmounts.plus.common.entity.dragon;

import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.api.ScoreboardAccessor;
import net.dragonmounts.plus.common.block.HatchableDragonEggBlock;
import net.dragonmounts.plus.common.init.DMBlocks;
import net.dragonmounts.plus.common.init.DMEntities;
import net.dragonmounts.plus.common.init.DMSounds;
import net.dragonmounts.plus.common.init.DragonTypes;
import net.dragonmounts.plus.common.item.DragonScalesItem;
import net.dragonmounts.plus.common.network.s2c.ShakeEggPayload;
import net.dragonmounts.plus.common.network.s2c.SyncEggAgePayload;
import net.dragonmounts.plus.compat.platform.DMGameRules;
import net.dragonmounts.plus.compat.platform.ServerNetworkHandler;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonVariant;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.UUID;

import static net.dragonmounts.plus.common.util.EntityUtil.addOrMergeEffect;
import static net.dragonmounts.plus.common.util.math.MathUtil.TO_RAD_FACTOR;
import static net.dragonmounts.plus.compat.platform.DMGameRules.DEFAULT_DRAGON_BASE_HEALTH;
import static net.dragonmounts.plus.compat.platform.DMGameRules.DRAGON_BASE_HEALTH;
import static net.minecraft.resources.ResourceLocation.tryParse;

public class HatchableDragonEggEntity extends LivingEntity implements DragonTypified.Mutable {
    public static ServerDragonEntity hatch(ServerLevel world, HatchableDragonEggEntity egg, DragonLifeStage stage) {
        return new ServerDragonEntity(world, (level, dragon) -> {
            CompoundTag data = egg.saveWithoutId(new CompoundTag());
            data.remove(HatchableDragonEggEntity.AGE_DATA_PARAMETER_KEY);
            data.remove(DragonLifeStage.DATA_PARAMETER_KEY);
            dragon.load(data);
            dragon.setDragonType(egg.getDragonType(), false);
            dragon.setLifeStage(stage, true, false);
            dragon.setHealth(dragon.getMaxHealth() + egg.getHealth() - egg.getMaxHealth());
        });
    }

    public static final String AGE_DATA_PARAMETER_KEY = "Age";
    protected static final EntityDataAccessor<DragonType> DATA_DRAGON_TYPE = SynchedEntityData.defineId(HatchableDragonEggEntity.class, DragonType.SERIALIZER);
    private static final float EGG_CRACK_PROCESS_THRESHOLD = 0.9F;
    private static final float EGG_SHAKE_PROCESS_THRESHOLD = 0.75F;
    private static final float EGG_SHAKE_BASE_CHANCE = 20F;
    public static final int MIN_HATCHING_TIME = 36000;
    public static final int EGG_CRACK_THRESHOLD = (int) (EGG_CRACK_PROCESS_THRESHOLD * MIN_HATCHING_TIME);
    public static final int EGG_SHAKE_THRESHOLD = (int) (EGG_SHAKE_PROCESS_THRESHOLD * MIN_HATCHING_TIME);
    public static final String VANILLA_DATA_PARAMETER_KEY = "IsVanilla";
    public static boolean IS_PUSHABLE = false;
    protected String variant;
    protected UUID owner;
    protected boolean hatched;
    protected boolean isVanilla;
    protected float rotationAxis;
    protected float amplitude;
    protected float amplitudeO;
    protected int shaking;
    protected int age;

    public static HatchableDragonEggEntity construct(EntityType<? extends HatchableDragonEggEntity> type, Level level) {
        return new HatchableDragonEggEntity(type, level);
    }

    public HatchableDragonEggEntity(EntityType<? extends HatchableDragonEggEntity> type, Level level) {
        super(type, level);
        if (level instanceof ServerLevel server) {
            //noinspection DataFlowIssue
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(server.getGameRules().getRule(DRAGON_BASE_HEALTH).get());
        }
    }

    public HatchableDragonEggEntity(Level level) {
        this(DMEntities.HATCHABLE_DRAGON_EGG.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, DEFAULT_DRAGON_BASE_HEALTH)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_DRAGON_TYPE, DragonTypes.ENDER);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString(DragonType.DATA_PARAMETER_KEY, this.getDragonType().identifier.toString());
        tag.putInt(AGE_DATA_PARAMETER_KEY, this.age);
        tag.putBoolean(VANILLA_DATA_PARAMETER_KEY, this.isVanilla);
        if (this.owner != null) {
            tag.putUUID("Owner", this.owner);
        }
        if (this.variant != null) {
            tag.putString(DragonVariant.DATA_PARAMETER_KEY, this.variant);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(DragonType.DATA_PARAMETER_KEY)) {
            this.setDragonType(DragonType.REGISTRY.getValue(tryParse(tag.getString(DragonType.DATA_PARAMETER_KEY))), false);
        }
        if (tag.contains(DragonVariant.DATA_PARAMETER_KEY)) {
            this.variant = tag.getString(DragonVariant.DATA_PARAMETER_KEY);
        }
        if (tag.contains(AGE_DATA_PARAMETER_KEY)) {
            this.setAge(tag.getInt(AGE_DATA_PARAMETER_KEY), !this.firstTick);
        }
        if (tag.contains(VANILLA_DATA_PARAMETER_KEY)) {
            this.setVanilla(tag.getBoolean(VANILLA_DATA_PARAMETER_KEY));
        }
        if (tag.hasUUID("Owner")) {
            this.owner = tag.getUUID("Owner");
        } else {
            this.owner = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), tag.getString("Owner"));
        }
    }

    public final void setVanilla(boolean vanilla) {
        this.isVanilla = vanilla;
    }

    protected void spawnScales(ServerLevel level, int amount) {
        if (amount > 0) {
            var scales = this.getDragonType().getInstance(DragonScalesItem.class, null);
            if (scales != null && level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.spawnAtLocation(level, new ItemStack(scales, amount), 1.25F);
            }
        }
    }

    @Override
    public void setLevelCallback(EntityInLevelCallback callback) {
        if (this.hatched && callback == EntityInLevelCallback.NULL && this.level() instanceof ServerLevel level) {
            level.addFreshEntity(hatch(level, this, DragonLifeStage.HATCHLING));
        }
        super.setLevelCallback(callback);
    }

    public void hatch() {
        if (this.level() instanceof ServerLevel level) {
            this.spawnScales(level, this.random.nextInt(4) + 4);
            this.hatched = true;
            ((ScoreboardAccessor) this.level().getScoreboard()).dragonmounts$plus$preventRemoval(this);
        }
        this.discard();
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {return ItemStack.EMPTY;}

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {}

    @Override
    public HumanoidArm getMainArm() {return HumanoidArm.RIGHT;}

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (this.isAlive() && player.isShiftKeyDown()) {
            var block = this.asBlock(null);
            if (block == null) return InteractionResult.FAIL;
            if (this.level().isClientSide) return InteractionResult.SUCCESS;
            this.discard();
            this.level().setBlockAndUpdate(this.blockPosition(), block.defaultBlockState());
            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick() {
        var level = this.level();
        var random = this.random;
        super.tick();
        if (level instanceof ServerLevel server) {
            --this.shaking;
            // play the egg shake animation based on the time the eggs take to hatch
            if (++this.age > EGG_SHAKE_THRESHOLD && this.shaking < 0) {
                float progress = (float) this.age / MIN_HATCHING_TIME;
                // wait until the egg is nearly hatched
                float chance = (progress - EGG_SHAKE_PROCESS_THRESHOLD) / EGG_SHAKE_BASE_CHANCE * (1 - EGG_SHAKE_PROCESS_THRESHOLD);
                if (this.age >= MIN_HATCHING_TIME && random.nextFloat() * 2 < chance) {
                    this.hatch();
                    return;
                }
                if (random.nextFloat() < chance) {
                    boolean crack = progress > EGG_CRACK_PROCESS_THRESHOLD;
                    int flag = crack ? 0b01 : 0b00;
                    ServerNetworkHandler.sendTracking(this, new ShakeEggPayload(
                            this.getId(),
                            this.shaking = random.nextInt(21) + 10,//[10, 30]
                            random.nextInt(180),
                            random.nextBoolean() ? 0b10 | flag : flag
                    ));
                    if (crack) {
                        this.spawnScales(server, 1);
                    }
                }
            }
            if (server.getGameRules().getBoolean(DMGameRules.IS_EGG_PUSHABLE)) {
                var list = server.getEntities(this, this.getBoundingBox().inflate(0.125F, -0.01F, 0.125F), this::isPushable);
                if (!list.isEmpty()) {
                    for (var entity : list) {
                        this.push(entity);
                    }
                }
            }
            return;
        }
        if (--this.shaking > 0) {
            this.amplitudeO = this.amplitude;
            this.amplitude = Mth.sin(level.getGameTime() * 0.5F) * Math.min(this.shaking, 15);
        }
        // spawn generic particles
        var type = this.getDragonType();
        double px = getX() + (random.nextDouble() - 0.5);
        double py = getY() + (random.nextDouble() - 0.3);
        double pz = getZ() + (random.nextDouble() - 0.5);
        double ox = (random.nextDouble() - 0.5) * 2;
        double oy = (random.nextDouble() - 0.3) * 2;
        double oz = (random.nextDouble() - 0.5) * 2;
        level.addParticle(type.eggParticle, px, py, pz, ox, oy, oz);
        if ((++this.age & 1) == 0 && type != DragonTypes.ENDER) {
            level.addParticle(new DustParticleOptions(type.color, 1.0F), px, py + 0.8, pz, ox, oy, oz);
        }
    }

    public boolean isPushable(Entity entity) {
        return !entity.isSpectator() && entity.isPushable() && !entity.hasPassenger(this);
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.asBlock(DMBlocks.ENDER_DRAGON_EGG.get()));
    }

    @Override
    protected Component getTypeName() {
        return this.getDragonType().getFormattedName("entity.dragonmounts.plus.dragon_egg.name");
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, DamageSource source) {
        return super.isInvulnerableTo(level, source) || this.getDragonType().isInvulnerableTo(source);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        var weapon = source.getWeaponItem();
        if (weapon != null && (weapon.is(ItemTags.MACE_ENCHANTABLE))) {
            if (super.hurtServer(level, source, Math.max(20F, amount * 2F))) {
                this.spawnScales(level, 1);
                return true;
            }
            return false;
        }
        return super.hurtServer(level, source, amount);
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return IS_PUSHABLE && super.isPushable();
    }

    @Override
    public void push(Entity entity) {
        if (IS_PUSHABLE) {
            super.push(entity);
        }
    }

    @Override
    protected boolean isImmobile() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt bolt) {
        super.thunderHit(level, bolt);
        addOrMergeEffect(this, MobEffects.DAMAGE_BOOST, 700, 0, false, true, true);//35s
        var current = this.getDragonType();
        if (current == DragonTypes.SKELETON) {
            this.setDragonType(DragonTypes.WITHER, false);
        } else if (current == DragonTypes.WATER) {
            this.setDragonType(DragonTypes.STORM, false);
        } else return;
        this.playSound(SoundEvents.END_PORTAL_SPAWN, 2, 1);
        this.playSound(SoundEvents.PORTAL_TRIGGER, 2, 1);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        ServerNetworkHandler.sendTo(player, new SyncEggAgePayload(this.getId(), this.age));
    }

    public void setAge(int age, boolean lazySync) {
        if (lazySync && this.age != age) {
            ServerNetworkHandler.sendTracking(this, new SyncEggAgePayload(this.getId(), age));
        }
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public float getRotationAxis() {
        return this.rotationAxis;
    }

    public float getAmplitude(float partialTicks) {
        return this.shaking <= 0 ? 0 : Mth.lerp(partialTicks, this.amplitudeO, this.amplitude);
    }

    public void syncShake(int amplitude, int axis, boolean crack) {
        var level = this.level();
        this.shaking = amplitude;
        this.rotationAxis = axis * TO_RAD_FACTOR;
        // use game time to make amplitude consistent between clients
        float target = Mth.sin(level.getGameTime() * 0.5F) * Math.min(amplitude, 15);
        // multiply with a factor to make it smoother
        this.amplitudeO = target * 0.25F;
        this.amplitude = target * 0.75F;
        if (crack) {
            level.levelEvent(2001, this.blockPosition(), Block.getId(
                    this.asBlock(DMBlocks.ENDER_DRAGON_EGG.get()).defaultBlockState()
            ));
        }
        level.playLocalSound(this, DMSounds.DRAGON_EGG_CRACK, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    @Contract("!null -> !null")
    public @Nullable Block asBlock(HatchableDragonEggBlock fallback) {
        return this.isVanilla
                ? Blocks.DRAGON_EGG
                : this.getDragonType().getInstance(HatchableDragonEggBlock.class, fallback);
    }

    public final void setDragonType(DragonType type, boolean reset) {
        var manager = this.getAttributes();
        manager.removeAttributeModifiers(this.getDragonType().attributes);
        this.entityData.set(DATA_DRAGON_TYPE, type);
        manager.addTransientAttributeModifiers(type.attributes);
        if (type != DragonTypes.ENDER) {
            this.setVanilla(false);
        }
        if (reset) {
            AttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
            assert health != null;
            this.setHealth((float) health.getValue());
        }
    }

    @Override
    public final DragonType getDragonType() {
        return this.entityData.get(DATA_DRAGON_TYPE);
    }
}
