package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.entity.breath.BreathNodeEntity;
import net.dragonmounts.plus.common.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.compat.registry.EntityHolder;
import net.minecraft.world.entity.MobCategory;

import static net.dragonmounts.plus.compat.registry.EntityHolder.registerEntity;
import static net.dragonmounts.plus.compat.registry.EntityHolder.registerLivingEntity;

public class DMEntities {
    public static final EntityHolder<BreathNodeEntity> DRAGON_BREATH = registerEntity(
            "dragon_breath",
            MobCategory.MISC,
            BreathNodeEntity::new,
            builder -> builder.noSummon().noLootTable().sized(0.2F, 0.2F).clientTrackingRange(0)
    );
    public static final EntityHolder<HatchableDragonEggEntity> HATCHABLE_DRAGON_EGG = registerLivingEntity(
            "dragon_egg",
            MobCategory.MISC,
            HatchableDragonEggEntity::construct,
            HatchableDragonEggEntity::createAttributes,
            builder -> builder.sized(0.875F, 1.0F).fireImmune()
    );
    public static final EntityHolder<TameableDragonEntity> TAMEABLE_DRAGON = registerLivingEntity(
            "dragon",
            MobCategory.CREATURE,
            TameableDragonEntity::construct,
            TameableDragonEntity::createAttributes,
            builder -> builder.sized(3.0F, 2.5F).fireImmune()
    );

    public static void init() {}
}
