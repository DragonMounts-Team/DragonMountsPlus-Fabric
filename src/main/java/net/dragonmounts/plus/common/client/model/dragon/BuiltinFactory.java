package net.dragonmounts.plus.common.client.model.dragon;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.client.ClientUtil.scaledPose;
import static net.dragonmounts.plus.common.client.model.dragon.DragonModel.*;
import static net.dragonmounts.plus.common.entity.dragon.DragonModelContracts.*;
import static net.dragonmounts.plus.common.util.math.MathUtil.TO_RAD_FACTOR;

public enum BuiltinFactory implements ModelFactory {
    NORMAL("normal"),
    TAIL_HORNED("tail_horned") {
        @Override
        public void makeTail(PartDefinition root) {
            makeHornedTail(root);
        }
    },
    TAIL_SCALE_INCLINED("tail_scale_inclined") {
        @Override
        public void makeTail(PartDefinition root) {
            var tail = root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 62.0F));
            var segment = CubeListBuilder.create()
                    .texOffs(152, 88)
                    .addBox(-5, -5, -5, TAIL_SIZE, TAIL_SIZE, TAIL_SIZE)
                    .getCubes();
            var rotZ = 45F * TO_RAD_FACTOR;
            var scale = CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-1, -8, -3, 2, 4, 6)
                    .getCubes();
            var left = new PartDefinition(scale, PartPose.rotation(0.0F, 0.0F, rotZ));
            var right = new PartDefinition(scale, PartPose.rotation(0.0F, 0.0F, -rotZ));
            for (int i = 0; i < TAIL_SEGMENTS; ++i) {
                var part = tail.addOrReplaceChild(
                        Integer.toString(i),
                        new PartDefinition(segment, scaledPose(calcTailSize(i)))
                );
                part.addOrReplaceChild("left_scale", left);
                part.addOrReplaceChild("right_scale", right);
            }
        }
    },
    SKELETON("skeleton") {
        @Override
        public void makeTail(PartDefinition root) {
            makeHornedTail(root);
        }

        @Override
        public void makeFrontLegs(PartDefinition root) {
            makeFrontLeg(root, "left_front_leg", SKELETON_LEG_WIDTH, LEG_LENGTH, true, PartPose.offset(-11, 18, 4));
            makeFrontLeg(root, "right_front_leg", SKELETON_LEG_WIDTH, LEG_LENGTH, false, PartPose.offset(11, 18, 4));
        }

        @Override
        public void makeHindLegs(PartDefinition root) {
            makeHindLeg(root, "left_hind_leg", SKELETON_LEG_WIDTH, LEG_LENGTH, true, PartPose.offset(-11, 13, 46));
            makeHindLeg(root, "right_hind_leg", SKELETON_LEG_WIDTH, LEG_LENGTH, false, PartPose.offset(11, 13, 46));
        }
    };
    public static final int NORMAL_LEG_WIDTH = 9;
    public static final int SKELETON_LEG_WIDTH = 7;
    public final ModelLayerLocation location;

    BuiltinFactory(String name) {
        this.location = new ModelLayerLocation(makeId("dragon"), name);
    }

    public static float calcNeckSize(int index) {
        return Mth.lerp((index + 1) / (float) NECK_SEGMENTS, 1.6F, 1.0F);
    }

    public static float calcTailSize(int index) {
        return Mth.lerp((index + 1) / (float) TAIL_SEGMENTS, 1.5F, 0.3F);
    }

    public static void makeHornedTail(PartDefinition root) {
        var tail = root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 62.0F));
        var segment = CubeListBuilder.create()
                .texOffs(152, 88)
                .addBox(-5, -5, -5, TAIL_SIZE, TAIL_SIZE, TAIL_SIZE)
                .getCubes();
        float offset = -0.5F * HORN_THICK;
        float rotY = 145F * TO_RAD_FACTOR;
        float rotX = -15F * TO_RAD_FACTOR;
        var left = new PartDefinition(
                CubeListBuilder.create().mirror()
                        .texOffs(0, 117)
                        .addBox(offset, offset, offset, HORN_THICK, HORN_THICK, HORN_LENGTH)
                        .getCubes(),
                PartPose.offsetAndRotation(0.0F, offset, 0.5F * TAIL_SIZE, rotX, rotY, 0.0F)
        );
        var right = new PartDefinition(
                CubeListBuilder.create()
                        .texOffs(0, 117)
                        .addBox(offset, offset, offset, HORN_THICK, HORN_THICK, HORN_LENGTH)
                        .getCubes(),
                PartPose.offsetAndRotation(0.0F, offset, 0.5F * TAIL_SIZE, rotX, -rotY, 0.0F)
        );
        for (int i = 0; i < TAIL_SEGMENTS; ++i) {
            var part = tail.addOrReplaceChild(Integer.toString(i), new PartDefinition(segment, scaledPose(calcTailSize(i))));
            if (i + 7 > TAIL_SEGMENTS && i + 3 < TAIL_SEGMENTS) {
                part.addOrReplaceChild("left_horn", left);
                part.addOrReplaceChild("right_horn", right);
            }
        }
    }

    public static void makeFrontLeg(
            PartDefinition root,
            String name,
            int width,
            int length,
            boolean mirror,
            PartPose pose
    ) {
        int thighLength = (int) (length * 0.77F);
        int shankLength = (int) (length * 0.80F);
        int footLength = (int) (length * 0.34F);
        int toeLength = (int) (length * 0.33F);
        int shankWidth = width - 2;
        float thighOffset = width * -0.5F;
        float shankOffset = shankWidth * -0.5F;
        float footOffsetY = FOOT_HEIGHT * -0.5F;
        float footOffsetZ = (int) (length * 0.34F) * -0.75F;
        root.addOrReplaceChild(
                name,
                CubeListBuilder.create().mirror(mirror).texOffs(112, 0).addBox(
                        thighOffset,
                        thighOffset,
                        thighOffset,
                        width,
                        thighLength,
                        width
                ),
                pose
        ).addOrReplaceChild(
                "shank",
                CubeListBuilder.create().mirror(mirror).texOffs(148, 0).addBox(
                        shankOffset,
                        shankOffset,
                        shankOffset,
                        shankWidth,
                        shankLength,
                        shankWidth
                ),
                PartPose.offset(0.0F, thighLength + thighOffset, 0.0F)
        ).addOrReplaceChild(
                "foot",
                CubeListBuilder.create().mirror(mirror).texOffs(210, 0).addBox(
                        thighOffset,
                        footOffsetY,
                        footOffsetZ,
                        width,
                        FOOT_HEIGHT,
                        footLength
                ),
                PartPose.offset(0.0F, shankLength + shankOffset * 0.5F, 0.0F)
        ).addOrReplaceChild(
                "toe",
                CubeListBuilder.create().mirror(mirror).texOffs(176, 0).addBox(
                        thighOffset,
                        footOffsetY,
                        -toeLength,
                        width,
                        FOOT_HEIGHT,
                        toeLength
                ),
                PartPose.offset(0.0F, 0.0F, footOffsetZ - footOffsetY * 0.5F)
        );
    }

    public static void makeHindLeg(
            PartDefinition root,
            String name,
            int width,
            int length,
            boolean mirror,
            PartPose pose
    ) {
        int thighLength = (int) (length * 0.90F);
        int shankLength = (int) (length * 0.70F) - 2;
        int footLength = (int) (length * 0.67F);
        int toeLength = (int) (length * 0.27F);
        int thighWidth = width + 1;
        int shankWidth = width - 2;
        float thighOffset = thighWidth * -0.5F;
        float shankOffset = shankWidth * -0.5F;
        float footOffsetY = FOOT_HEIGHT * -0.5F;
        float footOffsetZ = footLength * -0.75F;
        root.addOrReplaceChild(
                name,
                CubeListBuilder.create().mirror(mirror).texOffs(112, 29).addBox(
                        thighOffset,
                        thighOffset,
                        thighOffset,
                        thighWidth,
                        thighLength,
                        thighWidth
                ),
                pose
        ).addOrReplaceChild(
                "shank",
                CubeListBuilder.create().mirror(mirror).texOffs(152, 29).addBox(
                        shankOffset,
                        shankOffset,
                        shankOffset,
                        shankWidth,
                        shankLength,
                        shankWidth
                ),
                PartPose.offset(0.0F, thighLength + thighOffset, 0.0F)
        ).addOrReplaceChild(
                "foot",
                CubeListBuilder.create().mirror(mirror).texOffs(180, 29).addBox(
                        thighOffset,
                        footOffsetY,
                        footOffsetZ,
                        width,
                        FOOT_HEIGHT,
                        footLength
                ),
                PartPose.offset(0.0F, shankLength + shankOffset * 0.5F, 0.0F)
        ).addOrReplaceChild(
                "toe",
                CubeListBuilder.create().mirror(mirror).texOffs(215, 29).addBox(
                        thighOffset,
                        footOffsetY,
                        -toeLength,
                        width,
                        FOOT_HEIGHT,
                        toeLength
                ),
                PartPose.offset(0.0F, 0.0F, footOffsetZ - footOffsetY * 0.5F)
        );
    }
}
