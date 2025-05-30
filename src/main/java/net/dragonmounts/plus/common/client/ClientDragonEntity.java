package net.dragonmounts.plus.common.client;

import net.dragonmounts.plus.common.client.breath.impl.ClientBreathHelper;
import net.dragonmounts.plus.common.client.model.dragon.DragonAnimator;
import net.dragonmounts.plus.common.component.DragonFood;
import net.dragonmounts.plus.common.entity.dragon.DragonLifeStage;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.item.DragonArmorItem;
import net.dragonmounts.plus.common.tag.DMItemTags;
import net.dragonmounts.plus.common.util.math.MathUtil;
import net.dragonmounts.plus.compat.platform.PlatformItemTags;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClientDragonEntity extends TameableDragonEntity {
    public final DragonAnimator animator = new DragonAnimator(this);
    public int controlFlags;
    private float pendingJumpPower;
    private boolean wasOnGround;

    public ClientDragonEntity(EntityType<? extends TameableDragonEntity> type, Level world) {
        super(type, world);
        this.stage = DragonLifeStage.ADULT;
    }

    @Override
    protected @NotNull ClientBreathHelper createBreathHelper() {
        return new ClientBreathHelper(this);
    }

    @Override
    public final @NotNull Vec3 getHeadRelativeOffset(float x, float y, float z) {
        return this.animator.getHeadRelativeOffset(x, y, z);
    }

    /**
     * @see #onFlap()
     */
    @Deprecated
    public void onWingsDown(float speed) {
        // play wing sounds
        this.level().playLocalSound(
                this,
                SoundEvents.ENDER_DRAGON_FLAP,
                this.getSoundSource(),
                0.8f + (this.getAgeScale() - speed),
                1.0F
        );
    }

    @Override
    public void aiStep() {
        this.wasOnGround = this.onGround();
        if (this.isDeadOrDying()) {
            this.nearestCrystal = null;
        } else {
            this.checkCrystals();
        }
        super.aiStep();
        this.animator.tick();
        this.breathHelper.tick();
        if (!this.isAgeLocked()) {
            if (this.age < 0) {
                ++this.age;
            } else if (this.age > 0) {
                --this.age;
            }
        }
    }

    @Override
    protected void checkCrystals() {
        if (this.nearestCrystal != null && this.nearestCrystal.isAlive()) {
            if (this.random.nextInt(20) == 0) {
                this.nearestCrystal = this.findCrystal();
            }
        } else {
            this.nearestCrystal = this.random.nextInt(10) == 0 ? this.findCrystal() : null;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (DragonFood.isDragonFood(stack)) return InteractionResult.CONSUME;
        if (this.isOwnedBy(player)) {
            var item = stack.getItem();
            if (item instanceof SaddleItem || item instanceof DragonArmorItem) {
                return InteractionResult.CONSUME;
            }
            Holder.Reference<Item> holder = item.builtInRegistryHolder();
            if (holder.is(DMItemTags.BATONS) || holder.is(PlatformItemTags.WOODEN_CHESTS)) {
                return InteractionResult.CONSUME;
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void setLifeStage(DragonLifeStage stage, boolean reset, boolean sync) {
        if (this.stage == stage) return;
        this.stage = stage;
        if (reset) {
            this.refreshAge();
        }
        this.reapplyPosition();
        this.refreshDimensions();
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    public void refreshForcedAgeTimer() {
        if (this.forcedAgeTimer <= 0) {
            this.forcedAgeTimer = 40;
        }
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
    }

    @Override
    public void openCustomInventoryScreen(Player player) {}

    @Override
    public TameableDragonEntity getScreenOpeningData(ServerPlayer player) {return this;}

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {return null;}

    public @Nullable Vec3 locateCrystal() {
        return this.nearestCrystal == null ? null : this.nearestCrystal.position();
    }

    @Override
    protected void tickRidden(Player player, Vec3 input) {
        super.tickRidden(player, input);
        if (this.onGround() && this.isControlledByLocalInstance()) {
            // handle jump
            float power = this.pendingJumpPower;
            this.pendingJumpPower = 0.0F;
            if (power > 0.0F) {
                var motion = this.getDeltaMovement();
                this.hasImpulse = true;
                if (input.z > 0.0) {
                    float facing = this.getYRot() * MathUtil.TO_RAD_FACTOR;
                    this.setDeltaMovement(
                            motion.x - 0.4F * Mth.sin(facing) * power,
                            this.getJumpPower(power) * 2.5,
                            motion.z + 0.4F * Mth.cos(facing) * power
                    );
                } else {
                    this.setDeltaMovement(motion.x, this.getJumpPower(power) * 2.5, motion.z);
                }
            }
        }
    }

    @Override
    public boolean canJump() {
        return this.wasOnGround && super.canJump();
    }

    @Override
    public void onPlayerJump(int power) {
        this.pendingJumpPower = power >= 90 ? 1.0F : 0.4F + 0.4F * power / 90.0F;
    }
}
