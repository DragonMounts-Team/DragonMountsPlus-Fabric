package net.dragonmounts.plus.common.client.model.dragon;

import net.minecraft.client.model.geom.ModelPart;

public final class LegPart {
    public final ModelPart leg;
    public final ModelPart shank;
    public final ModelPart foot;
    public final ModelPart toe;

    public LegPart(ModelPart leg) {
        this.leg = leg;
        this.shank = leg.getChild("shank");
        this.foot = this.shank.getChild("foot");
        this.toe = this.foot.getChild("toe");
    }

    public void loadPose(Pose pose) {
        this.leg.yRot = pose.rotY;
        var rotX = pose.rotX;
        this.leg.xRot = rotX[0];
        this.shank.xRot = rotX[1];
        this.foot.xRot = rotX[2];
        this.toe.xRot = rotX[3];
    }

    public final static class Pose {
        public final float[] rotX = new float[4];
        public float rotY;
    }
}
