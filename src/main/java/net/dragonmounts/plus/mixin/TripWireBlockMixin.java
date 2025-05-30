package net.dragonmounts.plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.TripWireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TripWireBlock.class)
public abstract class TripWireBlockMixin {
    @ModifyExpressionValue(
            method = "playerWillDestroy",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    public boolean isShears(boolean original, @Local(argsOnly = true) Player player) {
        return original || player.getMainHandItem().is(ConventionalItemTags.SHEAR_TOOLS);
    }

    private TripWireBlockMixin() {}
}
