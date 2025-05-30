package net.dragonmounts.plus.common.entity.breath.impl;

import net.dragonmounts.plus.common.entity.dragon.DragonLifeStage;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.init.DMSounds;
import net.minecraft.sounds.SoundEvent;

public class StormBreath extends WaterBreath {
    public StormBreath(TameableDragonEntity dragon, float damage) {
        super(dragon, damage);
    }

    @Override
    public SoundEvent getStartSound(DragonLifeStage stage) {
        return DMSounds.DRAGON_BREATH_START_ICE;
    }

    @Override
    public SoundEvent getLoopSound(DragonLifeStage stage) {
        return DMSounds.DRAGON_BREATH_LOOP_ICE;
    }

    @Override
    public SoundEvent getStopSound(DragonLifeStage stage) {
        return DMSounds.DRAGON_BREATH_STOP_ICE;
    }
}
