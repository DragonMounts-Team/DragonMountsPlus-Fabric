package net.dragonmounts.plus.common.client.renderer.block;

import net.dragonmounts.plus.common.client.variant.VariantAppearance;
import org.jetbrains.annotations.Nullable;

public interface DragonHeadRenderState {
    default void dragonmounts$plus$setAppearance(@Nullable VariantAppearance appearance) {}

    default @Nullable VariantAppearance dragonmounts$plus$getAppearance() {
        return null;
    }
}
