package net.dragonmounts.plus.common.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.client.renderer.ShaderProgram;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public class DMCoreShaders {
    public static final ShaderProgram RENDERTYPE_ENTITY_CUTOUT_DECAL = of("rendertype_entity_cutout_decal", DefaultVertexFormat.NEW_ENTITY);
    public static final ShaderProgram RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_DECAL = of("rendertype_entity_translucent_emissive_decal", DefaultVertexFormat.NEW_ENTITY);

    static ShaderProgram of(String name, VertexFormat format) {
        return new ShaderProgram(makeId("core/" + name), format, ShaderDefines.EMPTY);
    }
}
