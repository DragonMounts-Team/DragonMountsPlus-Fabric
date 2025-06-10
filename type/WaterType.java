package net.dragonmounts.plus.common.type;

import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.entity.breath.DragonBreath;
import net.dragonmounts.plus.common.entity.breath.impl.WaterBreath;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.init.DMSounds;
import net.dragonmounts.plus.common.init.DragonTypes;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;

public class WaterType extends DragonType {
    public WaterType(ResourceLocation identifier, DragonTypeBuilder builder) {
        super(identifier, builder);
    }

    @Override
    public <T extends LivingEntity & DragonTypified.Mutable> void onThunderHit(T entity, LightningBolt bolt) {
        super.onThunderHit(entity, bolt);
        convertByLightning(entity, DragonTypes.STORM);
    }

    @Override
    public DragonBreath initBreath(TameableDragonEntity dragon) {
        return new WaterBreath(dragon, 0.7F);
    }

    @Override
    public SoundEvent getAmbientSound(TameableDragonEntity dragon) {
        return dragon.isBaby() ? DMSounds.DRAGON_PURR_HATCHLING : DMSounds.DRAGON_AMBIENT_WATER;
    }

    @Override
    public SoundEvent getRoarSound(TameableDragonEntity dragon) {
        return dragon.isBaby() ? DMSounds.DRAGON_ROAR_HATCHLING : DMSounds.DRAGON_ROAR_WATER;
    }
}
