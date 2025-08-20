package net.dragonmounts.plus.common.client.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;

public class DragonCoreModel extends Model {
    public final ModelPart lid;

    public DragonCoreModel(ModelPart root) {
        super(root, RenderType::entityCutoutNoCull);
        this.lid = root.getChild("lid");
    }

    public void animate(float progress) {
        this.lid.setPos(0.0F, 24.0F - progress * 0.5F * 16.0F, 0.0F);
        this.lid.yRot = 270.0F * progress * (float) (Math.PI / 180.0);
    }
}
