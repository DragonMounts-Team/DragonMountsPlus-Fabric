package net.dragonmounts.plus.mixin;

import net.dragonmounts.plus.common.entity.dragon.ServerDragonEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow
    protected int lastHurtByPlayerTime;

    @Shadow
    @Nullable
    protected Player lastHurtByPlayer;

    @Inject(method = "resolvePlayerResponsibleForDamage", at = @At("HEAD"), cancellable = true)
    public void appendDragonTypifiedText(DamageSource source, CallbackInfoReturnable<Player> info) {
        if (source.getEntity() instanceof ServerDragonEntity dragon && dragon.isTame()) {
            this.lastHurtByPlayerTime = 100;
            this.lastHurtByPlayer = dragon.getOwner() instanceof Player player ? player : null;
            info.setReturnValue(this.lastHurtByPlayer);
        }
    }
}
