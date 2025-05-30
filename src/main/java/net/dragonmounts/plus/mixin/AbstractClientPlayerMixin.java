package net.dragonmounts.plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.dragonmounts.plus.common.item.DragonScaleBowItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends LivingEntity {
    @ModifyExpressionValue(method = "getFieldOfViewModifier", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
    ))
    public boolean isBow(boolean original) {
        return original || this.getUseItem().getItem() instanceof DragonScaleBowItem; // to reduce the impact
    }

    private AbstractClientPlayerMixin(EntityType<? extends LivingEntity> type, Level level) {super(type, level);}
}
