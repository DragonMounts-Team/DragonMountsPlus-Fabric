package net.dragonmounts.plus.common.entity.ai.control;

import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.util.math.MathUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;

public class DragonMoveControl extends MoveControl {
    public final TameableDragonEntity dragon;

    public DragonMoveControl(TameableDragonEntity dragon) {
        super(dragon);
        this.dragon = dragon;
    }

    /**
     * @see FlyingMoveControl#tick()
     */
    @Override
    public void tick() {
        var dragon = this.dragon;
        if (dragon.onGround()) {
            super.tick();
        } else if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            var pos = dragon.position();
            double distX = this.wantedX - pos.x, distY = this.wantedY - pos.y, distZ = this.wantedZ - pos.z;
            double squared = distX * distX + distZ * distZ;
            if (squared + distY * distY < 2.5E-7) {
                dragon.setYya(0.0F);
                dragon.setZza(0.0F);
                return;
            }
            dragon.setYRot(this.rotlerp(
                    dragon.getYRot(),
                    (float) (Mth.atan2(distZ, distX) * 180.0F / MathUtil.PI) - 90.0F,
                    90.0F
            ));
            float speed = (float) (this.speedModifier * dragon.getAttributeValue(Attributes.FLYING_SPEED));
            dragon.setSpeed(speed);
            double dist = Math.sqrt(squared);
            if (dist > 1.0E-5 || Math.abs(distY) > 1.0E-5) { // adjusted order to simplify population
                dragon.setXRot(this.rotlerp(
                        dragon.getXRot(),
                        (float) (Mth.atan2(distY, dist) * -180.0F / MathUtil.PI),
                        20
                ));
                dragon.setYya(distY > 0.0 ? speed : -speed);
            }
        } else if (this.operation == MoveControl.Operation.JUMPING) {
            this.operation = MoveControl.Operation.WAIT;
            dragon.setSpeed((float) (this.speedModifier * dragon.getAttributeValue(Attributes.FLYING_SPEED)));
        } else {
            dragon.setYya(0.0F);
            dragon.setZza(0.0F);
        }
    }
}
