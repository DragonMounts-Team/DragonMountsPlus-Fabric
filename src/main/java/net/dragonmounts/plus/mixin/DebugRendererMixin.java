package net.dragonmounts.plus.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.dragonmounts.plus.common.client.DebugInfoRenderer;
import net.dragonmounts.plus.config.ClientConfig;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.BrainDebugRenderer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugRenderer.class)
public abstract class DebugRendererMixin {
    @Shadow
    @Final
    public BrainDebugRenderer brainDebugRenderer;

    @Inject(method = "render", at = @At("HEAD"))
    public void renderExtraLayers(
            PoseStack matrices,
            Frustum frustum,
            MultiBufferSource.BufferSource buffers,
            double camX,
            double camY,
            double camZ,
            CallbackInfo info
    ) {
        if (ClientConfig.INSTANCE.debug.get()) {
            this.brainDebugRenderer.render(matrices, buffers, camX, camY, camZ);
            DebugInfoRenderer.INSTANCE.render(matrices, buffers, camX, camY, camZ);
        }
    }
}
