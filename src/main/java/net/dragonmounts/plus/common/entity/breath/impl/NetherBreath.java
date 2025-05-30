package net.dragonmounts.plus.common.entity.breath.impl;

import net.dragonmounts.plus.common.entity.breath.BreathAffectedBlock;
import net.dragonmounts.plus.common.entity.breath.BreathAffectedEntity;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.compat.platform.DMGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class NetherBreath extends FireBreath {
    public NetherBreath(TameableDragonEntity dragon, float damage) {
        super(dragon, damage);
    }

    @Override
    public BreathAffectedBlock affectBlock(ServerLevel level, long location, BreathAffectedBlock hit) {
        var gamerules = level.getGameRules();
        // Flammable blocks: set fire to them once they have been exposed enough.  After sufficient exposure, destroy the
        //   block (otherwise -if it's raining, the burning block will keep going out)
        // Non-flammable blocks:
        // 1) liquids (except lava) evaporate
        // 2) If the block can be smelted (eg sand), then convert the block to the smelted version
        // 3) If the block can't be smelted then convert to lava
        var pos = BlockPos.of(location);
        var state = level.getBlockState(pos);
        if (this.litBlock(level, pos, state)) return new BreathAffectedBlock();
        boolean consume = false;
        boolean disableIgniting = !gamerules.getBoolean(DMGameRules.IGNITING_BREATH);
        boolean enableSmelting = gamerules.getBoolean(DMGameRules.SMELTING_BREATH);
        if (enableSmelting || !disableIgniting) {
            var random = level.random;
            float max = 0.0F;
            for (var facing : Direction.values()) {
                float density = hit.getHitDensity(facing);
                if (density > max) {
                    max = density;
                }
                if (disableIgniting
                        || density < this.calcIgnitionThreshold(level, pos, state, facing)
                        || random.nextFloat() < 0.1875F
                ) continue;
                var sideToIgnite = pos.relative(facing);
                if (level.getBlockState(sideToIgnite).isAir()) {
                    consume = true;
                    this.burnBlock(level, sideToIgnite, random);
                    //    if (densityOfThisFace >= thresholdForDestruction && state.getBlockHardness(level, pos) != -1 && DragonMountsConfig.canFireBreathAffectBlocks) {
                    //   level.setBlockToAir(pos);
                }
            }
            if (enableSmelting && max > 0.25F) {
                consume = true;
                this.smeltBlock(level, pos, state);
            }
        }
        return consume ? new BreathAffectedBlock() : hit;
    }

    @Override
    public void affectEntity(ServerLevel level, LivingEntity target, BreathAffectedEntity hit) {
        float damage = this.damage * hit.getHitDensity();
        if (target.isInPowderSnow || target.isFullyFrozen()) {
            damage *= 2.5F;
        } else if (target.isInWaterOrRain()) {
            damage *= 2.0F;
        }
        target.hurtServer(level, level.damageSources().mobAttack(this.dragon), damage);
        target.igniteForTicks(160);
    }
}
