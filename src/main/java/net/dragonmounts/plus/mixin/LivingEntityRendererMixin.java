package net.dragonmounts.plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.dragonmounts.plus.common.client.renderer.block.DragonHeadRenderState;
import net.dragonmounts.plus.common.item.DragonHeadItem;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;")
    )
    public void resetDragonHeadRenderState(
            LivingEntity a,
            LivingEntityRenderState state,
            float f,
            CallbackInfo info
    ) {
        ((DragonHeadRenderState) state).dragonmounts$plus$setAppearance(null);
    }

    @ModifyExpressionValue(
            method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;shouldRender(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;)Z")
    )
    public boolean extractDragonHeadRenderState(
            boolean original,
            @Local(argsOnly = true) LivingEntityRenderState state,
            @Local ItemStack stack
    ) {
        if (!original && stack.getItem() instanceof DragonHeadItem head) {
            ((DragonHeadRenderState) state).dragonmounts$plus$setAppearance(head.variant.appearance);
            return true;
        }
        return original;
    }

    private LivingEntityRendererMixin() {}
}
