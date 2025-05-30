package net.dragonmounts.plus.common.type;

import net.dragonmounts.plus.common.entity.breath.DragonBreath;
import net.dragonmounts.plus.common.entity.breath.impl.ZombieBreath;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.init.DMSounds;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ZombieType extends DragonType {
    public ZombieType(ResourceLocation identifier, DragonTypeBuilder builder) {
        super(identifier, builder);
    }

    @Override
    public DragonBreath initBreath(TameableDragonEntity dragon) {
        return new ZombieBreath(dragon, 0.6F);
    }

    @Override
    public SoundEvent getLivingSound(TameableDragonEntity dragon) {
        return dragon.isBaby() ? DMSounds.DRAGON_PURR_HATCHLING : DMSounds.DRAGON_PURR_ZOMBIE;
    }

    @Override
    public SoundEvent getDeathSound(TameableDragonEntity dragon) {
        return DMSounds.DRAGON_DEATH_ZOMBIE;
    }
}
