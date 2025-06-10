package net.dragonmounts.plus.common.init;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.common.api.DescribedArmorEffect;
import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.capability.ArmorEffectManagerImpl;
import net.dragonmounts.plus.common.client.gui.ArmorEffectTooltip;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.util.EntityUtil.addOrMergeEffect;
import static net.dragonmounts.plus.common.util.EntityUtil.addOrResetEffect;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerArmorEffect;

public class DMArmorEffects {
    public static final Component FISHING_LUCK = Component.translatable("tooltip.dragonmounts.plus.armor_effect_fishing_luck");
    public static final MutableComponent TRIGGER_PIECE_4 = Component.translatable("tooltip.armor_effect_trigger_piece_4");

    public static final DescribedArmorEffect.Advanced AETHER = registerArmorEffect("aether", identifier -> new DescribedArmorEffect.Advanced(
            identifier,
            DragonTypes.AETHER.getName(),
            300,
            TRIGGER_PIECE_4
    ) {
        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            boolean flag = level > 3;
            Level world = player.level();
            if (flag && !world.isClientSide && manager.getCooldown(this) <= 0 && player.isSprinting() && addOrMergeEffect(player, MobEffects.MOVEMENT_SPEED, 100, 1, true, true, true)) {
                world.playSound(null, player, SoundEvents.GUARDIAN_HURT, SoundSource.NEUTRAL, 1.0F, 1.0F);
                manager.setCooldown(this, this.cooldown);
            }
            return flag;
        }
    });

    public static final DescribedArmorEffect DARK = registerArmorEffect(makeId("dark"), new DescribedArmorEffect() {
        private static final Component TITLE = DragonTypes.DARK.getName();
        private static final List<Component> DESCRIPTION = Collections.singletonList(Component.translatable("tooltip.armor_effect.dragonmounts.plus.dark"));

        @Override
        public ArmorEffectTooltip getClientTooltip() {
            return new ArmorEffectTooltip(TITLE, DESCRIPTION, DescribedArmorEffect.formatTrigger(this, TRIGGER_PIECE_4));
        }

        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            if (level > 3) {
                addOrResetEffect(player, DMMobEffects.DARK_DRAGONS_GRACE.wrap(), 600, 0, true, true, true, 201);
                return true;
            }
            return false;
        }
    });

    public static final DescribedArmorEffect ENCHANTED = registerArmorEffect(makeId("enchanted"), new DescribedArmorEffect() {
        private static final Component TITLE = DragonTypes.ENCHANTED.getName();
        private static final List<Component> DESCRIPTION = Collections.singletonList(Component.translatable("tooltip.armor_effect.dragonmounts.plus.enchanted"));

        @Override
        public ArmorEffectTooltip getClientTooltip() {
            return new ArmorEffectTooltip(TITLE, DESCRIPTION, DescribedArmorEffect.formatTrigger(this, TRIGGER_PIECE_4));
        }

        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            Level world = player.level();
            if (world.isClientSide) {
                RandomSource random = player.getRandom();
                Vec3 pos = player.position();
                double x = pos.x, y = pos.y + 1.5, z = pos.z;
                for (int i = -2; i <= 2; ++i) {
                    for (int j = -2; j <= 2; ++j) {
                        if (i > -2 && i < 2 && j == -1) j = 2;
                        if (random.nextInt(30) == 0) {
                            for (int k = 0; k <= 1; ++k) {
                                world.addParticle(
                                        ParticleTypes.ENCHANT,
                                        x,
                                        y + random.nextFloat(),
                                        z,
                                        i + random.nextFloat() - 0.5D,
                                        k - random.nextFloat() - 1.0F,
                                        j + random.nextFloat() - 0.5D
                                );
                            }
                        }
                    }
                }
            }
            return level > 3;
        }
    });

    public static final DescribedArmorEffect.Advanced ENDER = registerArmorEffect("ender", identifier -> new DescribedArmorEffect.Advanced(
            identifier,
            DragonTypes.ENDER.getName(),
            1200,
            TRIGGER_PIECE_4
    ) {
        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            Level world = player.level();
            if (world.isClientSide) {
                RandomSource random = player.getRandom();
                Vec3 pos = player.position();
                world.addParticle(
                        ParticleTypes.PORTAL,
                        pos.x + random.nextFloat() - 0.3,
                        pos.y + random.nextFloat() - 0.3,
                        pos.z + random.nextFloat() - 0.3,
                        random.nextFloat() * 2 - 0.15,
                        random.nextFloat() * 2 - 0.15,
                        random.nextFloat() * 2 - 0.15
                );
                return level > 3;
            }
            // use `|` instead of `||` to avoid short-circuit evaluation when trying to add both of these two effects
            if (level > 3 && manager.getCooldown(this) <= 0 && player.getHealth() < 10 && (addOrMergeEffect(player, MobEffects.DAMAGE_RESISTANCE, 600, 2, true, true, true) | addOrMergeEffect(player, MobEffects.DAMAGE_BOOST, 300, 1, true, true, true))) {
                world.levelEvent(2003, player.blockPosition(), 0);
                world.playSound(null, player, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 0.05F, 1.0F);
                manager.setCooldown(this, this.cooldown);
                return true;
            }
            return false;
        }
    });

    public static final DescribedArmorEffect.Advanced FIRE = registerArmorEffect("fire", identifier -> new DescribedArmorEffect.Advanced(
            identifier,
            DragonTypes.FIRE.getName(),
            900,
            TRIGGER_PIECE_4
    ) {
        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level().isClientSide && manager.getCooldown(this) <= 0 && player.isOnFire()) {
                if (addOrMergeEffect(player, MobEffects.FIRE_RESISTANCE, 600, 0, true, true, true)) {
                    manager.setCooldown(this, this.cooldown);
                }
                player.clearFire();
            }
            return flag;
        }
    });

    public static final DescribedArmorEffect.Advanced FOREST = registerArmorEffect("forest", identifier -> new DescribedArmorEffect.Advanced(
            identifier,
            DragonTypes.FOREST.getName(),
            1200,
            TRIGGER_PIECE_4
    ) {
        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level().isClientSide) {
                if (player.fishing != null) {
                    addOrResetEffect(player, MobEffects.LUCK, 200, 0, true, true, true, 21);
                }
                if (player.getHealth() < 10 && manager.getCooldown(this) <= 0) {
                    if (addOrMergeEffect(player, MobEffects.REGENERATION, 200, 1, true, true, true)) {
                        manager.setCooldown(this, this.cooldown);
                    }
                }
            }
            return flag;
        }

        @Override
        public List<Component> getDescription() {
            var tooltips = new ObjectArrayList<Component>();
            tooltips.add(this.description);
            tooltips.add(FISHING_LUCK);
            return tooltips;
        }
    });

    public static final DescribedArmorEffect.Advanced ICE = registerArmorEffect(
            "ice",
            identifier -> new DescribedArmorEffect.Advanced(identifier, DragonTypes.ICE.getName(), 1200, TRIGGER_PIECE_4)
    );

    public static final DescribedArmorEffect MOONLIGHT = registerArmorEffect(makeId("moonlight"), new DescribedArmorEffect() {
        private static final Component TITLE = DragonTypes.MOONLIGHT.getName();
        private static final List<Component> DESCRIPTION = Collections.singletonList(Component.translatable("tooltip.armor_effect.dragonmounts.plus.moonlight"));

        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level().isClientSide) {
                addOrResetEffect(player, MobEffects.NIGHT_VISION, 600, 0, true, true, true, 201);
            }
            return flag;
        }

        @Override
        public ArmorEffectTooltip getClientTooltip() {
            return new ArmorEffectTooltip(TITLE, DESCRIPTION, DescribedArmorEffect.formatTrigger(this, TRIGGER_PIECE_4));
        }
    });

    public static final DescribedArmorEffect.Advanced NETHER = registerArmorEffect(
            "nether",
            identifier -> new DescribedArmorEffect.Advanced(identifier, DragonTypes.NETHER.getName(), 1200, TRIGGER_PIECE_4)
    );

    public static final DescribedArmorEffect.Advanced STORM = registerArmorEffect(
            "storm",
            identifier -> new DescribedArmorEffect.Advanced(identifier, DragonTypes.STORM.getName(), 160, TRIGGER_PIECE_4)
    );

    public static final DescribedArmorEffect.Advanced SUNLIGHT = registerArmorEffect("sunlight", identifier -> new DescribedArmorEffect.Advanced(
            identifier,
            DragonTypes.SUNLIGHT.getName(),
            1200,
            TRIGGER_PIECE_4
    ) {
        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level().isClientSide) {
                if (player.fishing != null) {
                    addOrResetEffect(player, MobEffects.LUCK, 200, 0, true, true, true, 21);
                }
                if (manager.getCooldown(this) <= 0 && player.getFoodData().getFoodLevel() < 6 && addOrMergeEffect(player, MobEffects.SATURATION, 200, 0, true, true, true)) {
                    manager.setCooldown(this, this.cooldown);
                }
            }
            return flag;
        }

        @Override
        public List<Component> getDescription() {
            var tooltips = new ObjectArrayList<Component>();
            tooltips.add(this.description);
            tooltips.add(FISHING_LUCK);
            return tooltips;
        }
    });

    public static final DescribedArmorEffect TERRA = registerArmorEffect(makeId("terra"), new DescribedArmorEffect() {
        private static final Component TITLE = DragonTypes.TERRA.getName();
        private static final List<Component> DESCRIPTION = Collections.singletonList(Component.translatable("tooltip.armor_effect.dragonmounts.plus.terra"));

        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level().isClientSide) {
                addOrResetEffect(player, MobEffects.DIG_SPEED, 600, 0, true, true, true, 201);
            }
            return flag;
        }

        @Override
        public ArmorEffectTooltip getClientTooltip() {
            return new ArmorEffectTooltip(TITLE, DESCRIPTION, DescribedArmorEffect.formatTrigger(this, TRIGGER_PIECE_4));
        }
    });

    public static final DescribedArmorEffect WATER = registerArmorEffect(makeId("water"), new DescribedArmorEffect() {
        private static final Component TITLE = DragonTypes.WATER.getName();
        private static final List<Component> DESCRIPTION = Collections.singletonList(Component.translatable("tooltip.armor_effect.dragonmounts.plus.water"));

        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level().isClientSide && player.isEyeInFluid(FluidTags.WATER)) {
                addOrResetEffect(player, MobEffects.WATER_BREATHING, 600, 0, true, true, true, 201);
            }
            return flag;
        }

        @Override
        public ArmorEffectTooltip getClientTooltip() {
            return new ArmorEffectTooltip(TITLE, DESCRIPTION, DescribedArmorEffect.formatTrigger(this, TRIGGER_PIECE_4));
        }
    });

    public static final DescribedArmorEffect.Advanced ZOMBIE = registerArmorEffect("zombie", identifier -> new DescribedArmorEffect.Advanced(
            identifier,
            DragonTypes.ZOMBIE.getName(),
            400,
            TRIGGER_PIECE_4
    ) {
        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            boolean flag = level > 3;
            if (flag && !player.level().isClientSide && !player.level().isDay() && manager.getCooldown(this) <= 0 && addOrMergeEffect(player, MobEffects.DAMAGE_BOOST, 300, 0, true, true, true)) {
                manager.setCooldown(this, this.cooldown);
            }
            return flag;
        }
    });

    @SuppressWarnings("SameReturnValue")
    public static InteractionResult meleeChanneling(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult hit) {
        if (!(level instanceof ServerLevel server) || player.getRandom().nextBoolean()) return InteractionResult.PASS;
        ArmorEffectManagerImpl manager = ((ArmorEffectManager.Provider) player).dragonmounts$plus$getManager();
        if (manager.isActive(STORM) && manager.getCooldown(STORM) <= 0) {
            BlockPos pos = entity.blockPosition();
            if (level.canSeeSky(pos)) {
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.spawn(server, pos, EntitySpawnReason.TRIGGERED);
                if (bolt == null) return InteractionResult.PASS;
                bolt.setCause((ServerPlayer) player);
            }
            manager.setCooldown(STORM, STORM.cooldown);
        }
        return InteractionResult.PASS;
    }
}
