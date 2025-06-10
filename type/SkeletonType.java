package net.dragonmounts.plus.common.type;

import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.entity.breath.DragonBreath;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.init.DMSounds;
import net.dragonmounts.plus.common.init.DragonTypes;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class SkeletonType extends DragonType {
    public SkeletonType(ResourceLocation identifier, DragonTypeBuilder builder) {
        super(identifier, builder);
    }

    @Override
    public boolean isInHabitat(LivingEntity entity) {
        //noinspection deprecation
        return entity.getY() * 5 < entity.level().getHeight() && entity.getLightLevelDependentMagicValue() < 0.25;
    }

    @Override
    public <T extends LivingEntity & DragonTypified.Mutable> void onThunderHit(T entity, LightningBolt bolt) {
        super.onThunderHit(entity, bolt);
        convertByLightning(entity, DragonTypes.WITHER);
    }

    @Override
    public @Nullable DragonBreath initBreath(TameableDragonEntity dragon) {
        return null;
    }

    @Override
    public SoundEvent getAmbientSound(TameableDragonEntity dragon) {
        return dragon.isBaby() ? DMSounds.DRAGON_PURR_SKELETON_HATCHLING : DMSounds.DRAGON_PURR_SKELETON;
    }
}
