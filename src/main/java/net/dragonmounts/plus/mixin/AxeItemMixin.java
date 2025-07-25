package net.dragonmounts.plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.dragonmounts.plus.common.item.DragonScaleShieldItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {
    @ModifyExpressionValue(method = "playerHasShieldUseIntent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean isShield(boolean original, @Local Player player) {
        return original || player.getOffhandItem().getItem() instanceof DragonScaleShieldItem;
    }

    private AxeItemMixin() {}
}
