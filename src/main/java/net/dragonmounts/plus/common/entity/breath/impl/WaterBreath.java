package net.dragonmounts.plus.common.entity.breath.impl;

import net.dragonmounts.plus.common.entity.breath.BreathAffectedBlock;
import net.dragonmounts.plus.common.entity.breath.BreathAffectedEntity;
import net.dragonmounts.plus.common.entity.breath.DragonBreath;
import net.dragonmounts.plus.common.entity.dragon.DragonLifeStage;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.init.DMSounds;
import net.dragonmounts.plus.compat.platform.DMGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.material.Fluids;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.MOISTURE;

public class WaterBreath extends DragonBreath {
    public WaterBreath(TameableDragonEntity dragon, float damage) {
        super(dragon, damage);
    }

    @Override
    public BreathAffectedBlock affectBlock(ServerLevel level, long location, BreathAffectedBlock hit) {
        var gamerules = level.getGameRules();
        var pos = BlockPos.of(location);
        var state = level.getBlockState(pos);
        if (state.is(Blocks.LAVA)) {
            if (!gamerules.getBoolean(DMGameRules.QUENCHING_BREATH)) return hit;
            level.setBlockAndUpdate(pos, (
                    level.getFluidState(pos).isSource() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE
            ).defaultBlockState());
            level.levelEvent(1501, pos, 0);
        } else if (state.is(Blocks.WATER)) {
            if (!level.getFluidState(pos).is(Fluids.WATER)) return hit; // requires full water block
            level.setBlockAndUpdate(pos, (
                    gamerules.getBoolean(DMGameRules.FROSTY_BREATH) ? Blocks.ICE : Blocks.FROSTED_ICE
            ).defaultBlockState());
        } else if (AbstractCandleBlock.isLit(state)) {
            AbstractCandleBlock.extinguish(null, state, level, pos);
        } else if (CampfireBlock.isLitCampfire(state)) {
            level.levelEvent(null, 1009, pos, 0);
            level.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, false));
        } else if (state.is(BlockTags.FIRE)) {
            level.destroyBlock(pos, true, this.dragon);
        } else if (state.hasProperty(MOISTURE)) {
            int moisture = state.getValue(MOISTURE);
            if (moisture >= FarmBlock.MAX_MOISTURE) return hit;
            level.setBlock(pos, state.setValue(MOISTURE, moisture + 1), 2);
        }
        return new BreathAffectedBlock(); // reset to zero
    }

    @Override
    public void affectEntity(ServerLevel level, LivingEntity target, BreathAffectedEntity hit) {
        float density = hit.getHitDensity();
        float damage = this.damage * hit.getHitDensity();
        if (target.getType().is(EntityTypeTags.AQUATIC)) damage += 4;
        if (target.canBreatheUnderwater() || MobEffectUtil.hasWaterBreathing(target)) damage *= 0.5F;
        if (target.isInPowderSnow) {
            target.setTicksFrozen(Math.min(target.getTicksRequiredToFreeze(), target.getTicksFrozen() + 2));
        }
        if (target.isOnFire()) {
            target.clearFire();
            target.playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 1.0f, 0.0f);
            damage *= 1.5F;
        } else {
            target.playSound(SoundEvents.GENERIC_SPLASH, 0.4f, 1.0f);
        }
        target.hurtServer(level, level.damageSources().mobAttack(this.dragon), damage);
        var direction = hit.getHitDirection();
        target.knockback(0.05F * density, -direction.x, -direction.z);
    }

    @Override
    public SoundEvent getStartSound(DragonLifeStage stage) {
        return DMSounds.DRAGON_BREATH_START_WATER;
    }

    @Override
    public SoundEvent getLoopSound(DragonLifeStage stage) {
        return DMSounds.DRAGON_BREATH_LOOP_WATER;
    }

    @Override
    public SoundEvent getStopSound(DragonLifeStage stage) {
        return DMSounds.DRAGON_BREATH_STOP_WATER;
    }

}
