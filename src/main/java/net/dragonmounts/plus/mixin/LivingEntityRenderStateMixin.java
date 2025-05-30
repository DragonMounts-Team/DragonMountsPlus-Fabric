package net.dragonmounts.plus.mixin;

import net.dragonmounts.plus.common.client.renderer.block.DragonHeadRenderState;
import net.dragonmounts.plus.common.client.variant.VariantAppearance;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public abstract class LivingEntityRenderStateMixin implements DragonHeadRenderState {
    @Unique
    private VariantAppearance headAppearance;

    @Override
    public void dragonmounts$setAppearance(VariantAppearance appearance) {
        this.headAppearance = appearance;
    }

    @Override
    public @Nullable VariantAppearance dragonmounts$getAppearance() {
        return this.headAppearance;
    }

    private LivingEntityRenderStateMixin() {}
}
