package net.dragonmounts.plus.common.client.renderer;

import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/// @see net.minecraft.client.renderer.RenderStateShard.MultiTextureStateShard
public class MaskedTextureStateShard extends RenderStateShard.EmptyTextureStateShard {
    public final ResourceLocation texture;

    public MaskedTextureStateShard(ResourceLocation texture, ResourceLocation mask) {
        super(() -> {
            var manager = Minecraft.getInstance().getTextureManager();
            RenderSystem.setShaderTexture(0, bindTexture(manager, texture));
            RenderSystem.setShaderTexture(3, bindTexture(manager, mask));
        }, Runnables.doNothing());
        this.texture = texture;
    }

    @Override
    protected @NotNull Optional<ResourceLocation> cutoutTexture() {
        return Optional.of(this.texture);
    }

    static int bindTexture(TextureManager manager, ResourceLocation location) {
        var texture = manager.getTexture(location);
        texture.setFilter(false, false);
        return texture.getId();
    }
}
