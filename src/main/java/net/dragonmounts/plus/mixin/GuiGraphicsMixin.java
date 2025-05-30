package net.dragonmounts.plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.dragonmounts.plus.common.api.DescribedArmorEffect;
import net.dragonmounts.plus.common.capability.ArmorEffectManagerImpl;
import net.dragonmounts.plus.common.item.DragonScaleArmorItem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @ModifyExpressionValue(
            method = "renderItemCooldown",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;getCooldownPercent(Lnet/minecraft/world/item/ItemStack;F)F")
    )
    public float getCooldown(float original, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() instanceof DragonScaleArmorItem armor && armor.effect instanceof DescribedArmorEffect.Advanced advanced) {
            int cooldown = ArmorEffectManagerImpl.getLocalCooldown(advanced);
            if (cooldown > 0) return Mth.clamp(cooldown / (float) advanced.cooldown, original, 1F);
        }
        return original;
    }

    private GuiGraphicsMixin() {}
}
