package net.dragonmounts.plus.mixin;

import net.dragonmounts.plus.common.client.renderer.DMCoreShaders;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.ShaderProgram;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CoreShaders.class)
public abstract class CoreShadersMixin {
    @Final
    @Shadow
    private static List<ShaderProgram> PROGRAMS;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void registerShaders(CallbackInfo info) {
        PROGRAMS.add(DMCoreShaders.RENDERTYPE_ENTITY_CUTOUT_DECAL);
        PROGRAMS.add(DMCoreShaders.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_DECAL);
    }

    private CoreShadersMixin() {}
}
