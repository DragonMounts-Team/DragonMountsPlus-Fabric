package net.dragonmounts.plus.common.client.renderer.dragon;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dragonmounts.plus.common.client.model.dragon.DragonModel;
import net.dragonmounts.plus.common.init.DragonArmorMaterials;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

import static net.minecraft.client.renderer.RenderType.armorCutoutNoCull;
import static net.minecraft.client.renderer.entity.ItemRenderer.getArmorFoilBuffer;

public class TameableDragonLayer extends RenderLayer<DragonRenderState, DragonModel> {
    public TameableDragonLayer(RenderLayerParent<DragonRenderState, DragonModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffers, int light, DragonRenderState state, float yRot, float xRot) {
        var model = this.getParentModel();
        var appearance = state.variant.appearance;
        int onOverlay = OverlayTexture.NO_OVERLAY;
        if (!state.isInvisible) {
            if (state.deathTime > 0) {
                boolean hurt = state.hurtTime > 0;
                model.renderToBuffer(matrices, buffers.getBuffer(appearance.getDissolve(state)), light, onOverlay, ARGB.color(
                        Mth.floor(state.deathTime * 255.0F / state.maxDeathTime), -1
                ));
                model.renderToBuffer(matrices, buffers.getBuffer(appearance.getDecal(state)), light, OverlayTexture.pack(0.0F, hurt));
                model.renderToBuffer(matrices, buffers.getBuffer(appearance.getGlowDecal(state)), 15728640, OverlayTexture.pack(0.0F, hurt));
                return;
            }
            //glow
            model.renderToBuffer(matrices, buffers.getBuffer(appearance.getGlow(state)), 15728640, onOverlay);
        }
        //saddle
        if (state.isSaddled) {
            renderColoredCutoutModel(model, appearance.getSaddle(state), matrices, buffers, light, state, -1);
        }
        //chest
        if (state.hasChest) {
            renderColoredCutoutModel(model, appearance.getChest(state), matrices, buffers, light, state, -1);
        }
        //armor
        var equippable = state.armor.get(DataComponents.EQUIPPABLE);
        if (equippable == null) return;
        var texture = equippable.assetId().map(DragonArmorMaterials::getTexture).orElse(null);
        if (texture == null) return;
        model.renderToBuffer(matrices, getArmorFoilBuffer(buffers, armorCutoutNoCull(texture), state.armor.hasFoil()), light, onOverlay);
    }
}
