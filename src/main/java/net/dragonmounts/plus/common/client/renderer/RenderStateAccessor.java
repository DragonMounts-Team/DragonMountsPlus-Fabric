package net.dragonmounts.plus.common.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import static net.dragonmounts.plus.common.client.renderer.DMCoreShaders.RENDERTYPE_ENTITY_CUTOUT_DECAL;
import static net.dragonmounts.plus.common.client.renderer.DMCoreShaders.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_DECAL;

public abstract class RenderStateAccessor extends RenderStateShard {
    public static final ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_DECAL_SHADER = new ShaderStateShard(RENDERTYPE_ENTITY_CUTOUT_DECAL);
    public static final ShaderStateShard RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_DECAL_SHADER = new ShaderStateShard(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_DECAL);

    public static RenderType entityCutoutDecal(ResourceLocation texture, ResourceLocation mask) {
        return RenderType.create(
                "entity_cutout_decal",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                1536,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderStateAccessor.RENDERTYPE_ENTITY_CUTOUT_DECAL_SHADER)
                        .setTextureState(new MaskedTextureStateShard(texture, mask))
                        .setTransparencyState(NO_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .createCompositeState(true)
        );
    }

    public static RenderType entityTranslucentEmissiveDecal(ResourceLocation texture, ResourceLocation mask) {
        return RenderType.create(
                "entity_translucent_emissive_decal",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                1536,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderStateAccessor.RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_DECAL_SHADER)
                        .setTextureState(new MaskedTextureStateShard(texture, mask))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setWriteMaskState(COLOR_WRITE)
                        .setOverlayState(OVERLAY)
                        .createCompositeState(false)
        );
    }

    private RenderStateAccessor(String a, Runnable b, Runnable c) {
        super(a, b, c);
    }
}
