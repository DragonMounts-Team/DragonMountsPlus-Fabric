package net.dragonmounts.plus.common.client.model.dragon;

import net.dragonmounts.plus.common.client.renderer.dragon.DragonRenderState;
import net.dragonmounts.plus.common.util.Segment;
import net.dragonmounts.plus.common.util.math.MathUtil;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static net.dragonmounts.plus.common.client.ClientUtil.*;
import static net.dragonmounts.plus.common.entity.dragon.DragonModelContracts.*;

public class DragonModel extends EntityModel<DragonRenderState> implements HeadedModel {
    public static final int HEAD_SIZE = 16;
    public static final int HEAD_OFS = -16;
    public static final int JAW_WIDTH = 12;
    public static final int JAW_HEIGHT = 5;
    public static final int JAW_LENGTH = 16;
    public static final int HORN_THICK = 3;
    public static final float HORN_OFS = -0.5F * HORN_THICK;
    public static final int HORN_LENGTH = 12;
    public static final int LEG_LENGTH = 26;
    public static final int FOOT_HEIGHT = 4;
    public final ModelPart head;
    public final ModelPart jaw;
    public final ModelPart leftWing;
    public final ModelPart leftArm;
    private final ModelPart[] leftFingers;
    public final ModelPart rightWing;
    public final ModelPart rightArm;
    private final ModelPart[] rightFingers;
    public final LegPart leftFrontLeg;
    public final LegPart rightFrontLeg;
    public final LegPart leftHindLeg;
    public final LegPart rightHindLeg;
    private final ModelPart[] necks;
    private final ModelPart[] tails;
    public final ModelPart body;
    public final ModelPart chest;
    public final ModelPart back;

    public DragonModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.leftWing = root.getChild("left_wing");
        this.leftArm = this.leftWing.getChild("forearm");
        this.leftFingers = getChildren(this.leftArm.getChild("fingers"), WING_FINGERS);
        this.rightWing = root.getChild("right_wing");
        this.rightArm = this.rightWing.getChild("forearm");
        this.rightFingers = getChildren(this.rightArm.getChild("fingers"), WING_FINGERS);
        this.leftFrontLeg = new LegPart(root.getChild("left_front_leg"));
        this.rightFrontLeg = new LegPart(root.getChild("right_front_leg"));
        this.leftHindLeg = new LegPart(root.getChild("left_hind_leg"));
        this.rightHindLeg = new LegPart(root.getChild("right_hind_leg"));
        this.necks = getChildren(root.getChild("neck"), NECK_SEGMENTS);
        this.tails = getChildren(root.getChild("tail"), TAIL_SEGMENTS);
        this.body = root.getChild("body");
        this.chest = this.body.getChild("chest");
        this.back = this.body.getChild("back");
    }

    public void setupBlock(float ticks, float yRot, float scale) {
        var head = this.head;
        head.resetPose();
        head.xScale = head.yScale = head.zScale = scale;
        this.jaw.xRot = Mth.sin(ticks * MathUtil.PI * 0.2F) * 0.2F + 0.2F;
        head.yRot = yRot * MathUtil.TO_RAD_FACTOR;
        head.y = -6F;
    }

    @Override
    public @NotNull ModelPart getHead() {
        return this.head;
    }

    @Override
    public void setupAnim(DragonRenderState state) {
        super.setupAnim(state);
        this.root.xRot = -state.pitch;
        var head = this.head;
        loadBasic(head, state.head);
        head.xScale = head.yScale = head.zScale = 0.92F;
        this.jaw.xRot = state.jawRotX;
        loadWithScale(this.necks, state.neckSegments, NECK_SEGMENTS);
        loadWithScale(this.tails, state.tailSegments, TAIL_SEGMENTS);
        loadMirroredRot(this.leftWing, this.rightWing, state.wingRot);
        loadMirroredRot(this.leftArm, this.rightArm, state.armRot);
        this.leftFrontLeg.loadPose(state.leftFrontLeg);
        this.rightFrontLeg.loadPose(state.rightFrontLeg);
        this.leftHindLeg.loadPose(state.leftHindLeg);
        this.rightHindLeg.loadPose(state.rightHindLeg);
        this.chest.visible = state.hasChest;
        this.back.visible = !state.isSaddled;
        for (int i = 0; i < WING_FINGERS; ++i) {
            this.leftFingers[i].yRot = -(this.rightFingers[i].yRot = state.fingerRotY[i]);
        }
    }

    public static void loadMirroredRot(ModelPart left, ModelPart right, Vector3f rot) {
        left.xRot = right.xRot = rot.x;
        left.yRot = -(right.yRot = rot.y);
        left.zRot = -(right.zRot = rot.z);
    }

    private static void loadWithScale(ModelPart[] parts, Segment[] segments, int length) {
        for (int i = 0; i < length; ++i) {
            var part = parts[i];
            var segment = segments[i];
            loadBasic(part, segment);
            loadScale(part, segment);
        }
    }
}
