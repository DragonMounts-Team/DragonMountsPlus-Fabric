package net.dragonmounts.plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin({Bogged.class, MushroomCow.class, Sheep.class, SnowGolem.class})
public class ShearableEntityMixin {
    @ModifyExpressionValue(
            method = "mobInteract",
            allow = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"),
            slice = @Slice(from = @At(value = "FIELD", target = "net/minecraft/world/item/Items.SHEARS:Lnet/minecraft/world/item/Item;"))
    )
    public boolean isShears(boolean original, @Local(ordinal = 0) ItemStack stack) {
        return original || stack.is(ConventionalItemTags.SHEAR_TOOLS);
    }
}
