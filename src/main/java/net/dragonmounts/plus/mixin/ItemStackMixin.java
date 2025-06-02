package net.dragonmounts.plus.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.dragonmounts.plus.common.item.EntityContainer;
import net.dragonmounts.plus.compat.registry.ArmorEffectSourceType;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    protected abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> type, Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag);

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract DataComponentMap getComponents();

    @Inject(method = "getTooltipLines", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V"
    ))//Append tooltip at a position before "additional tooltip"
    public void appendDragonTypifiedText(
            Item.TooltipContext context,
            @Nullable Player a,
            TooltipFlag flag,
            CallbackInfoReturnable<List<Component>> info,
            @Local Consumer<Component> consumer
    ) {
        if (this.getItem() instanceof EntityContainer<?>) return;
        var component = this.getComponents().get(DMDataComponents.ARMOR_EFFECT_SOURCE);
        if (component != null && component.getType() == ArmorEffectSourceType.BUILTIN) return;
        this.addToTooltip(DMDataComponents.DRAGON_TYPE, context, consumer, flag);
    }

    private ItemStackMixin() {}
}
