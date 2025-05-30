package net.dragonmounts.plus.common.type;

import net.dragonmounts.plus.common.entity.breath.DragonBreath;
import net.dragonmounts.plus.common.entity.breath.impl.WaterBreath;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.init.DMSounds;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class WaterType extends DragonType {
    public WaterType(ResourceLocation identifier, DragonTypeBuilder builder) {
        super(identifier, builder);
    }

    @Override
    public DragonBreath initBreath(TameableDragonEntity dragon) {
        return new WaterBreath(dragon, 0.7F);
    }

    @Override
    public SoundEvent getRoarSound(TameableDragonEntity dragon) {
        return dragon.isBaby() ? DMSounds.DRAGON_ROAR_HATCHLING : DMSounds.DRAGON_ROAR_WATER;
    }
}
