package net.dragonmounts.plus.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.dragonmounts.plus.common.api.AutoJumpRideable;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.PlayerRideableJumping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @Shadow
    private int jumpRidingTicks;

    @Shadow
    private float jumpRidingScale;

    @Shadow
    public ClientInput input;

    @Shadow
    protected abstract void sendRidingJump();

    @ModifyExpressionValue(method = "aiStep", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/PlayerRideableJumping;getJumpCooldown()I"
    ))
    public int autoJump(
            int original,
            @Share("autoJumped") LocalBooleanRef jumped,
            @Local(ordinal = 0) boolean jumping,
            @Local PlayerRideableJumping vehicle
    ) {
        if (jumping && original == 0 &&
                vehicle instanceof AutoJumpRideable rideable &&
                this.jumpRidingTicks >= 9 &&
                this.input.keyPresses.jump()
        ) {
            jumped.set(true);
            rideable.onPlayerJump(100);
            this.sendRidingJump();
            return 1;
        }
        return original;
    }

    @Inject(method = "aiStep", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/AbstractClientPlayer;aiStep()V"
    ))
    public void updateFields(CallbackInfo info, @Share("autoJumped") LocalBooleanRef jumped) {
        if (jumped.get()) {
            this.jumpRidingTicks = -10;
            this.jumpRidingScale = 1.0F;
        }
    }

    private LocalPlayerMixin() {}
}
