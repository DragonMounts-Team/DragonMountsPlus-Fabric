package net.dragonmounts.plus.common.entity.ai.control;

import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.util.EntityUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class DragonBodyControl extends BodyRotationControl {
    public final TameableDragonEntity dragon;
    private int headStableTime;
    private float lastStableYHeadRot;

    public DragonBodyControl(TameableDragonEntity dragon) {
        super(dragon);
        this.dragon = dragon;
    }

    @Override
    public void clientTick() {
        TameableDragonEntity dragon = this.dragon;
        if (dragon.isBreathing() || dragon.isFlying() || dragon.isInSittingPose() || EntityUtil.isMoving(dragon)) {
            dragon.yBodyRot = dragon.getYRot();
            dragon.yHeadRot = Mth.rotateIfNecessary(dragon.yHeadRot, dragon.yBodyRot, dragon.getMaxHeadYRot());
            this.lastStableYHeadRot = dragon.yHeadRot;
            this.headStableTime = 0;
            return;
        }
        if (dragon.getFirstPassenger() instanceof Mob) return;
        float maxDiff = dragon.getMaxHeadYRot();
        if (Math.abs(dragon.yHeadRot - this.lastStableYHeadRot) > 15.0F) {
            this.headStableTime = 0;
            this.lastStableYHeadRot = dragon.yHeadRot;
        } else if (++this.headStableTime > 10) {
            maxDiff *= Mth.clamp((20 - this.headStableTime) / 10.0F, 0.0F, 1.0F);
        }
        dragon.yBodyRot = Mth.rotateIfNecessary(dragon.yBodyRot, dragon.yHeadRot, maxDiff);
    }
}
