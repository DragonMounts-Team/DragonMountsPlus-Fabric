package net.dragonmounts.plus.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.dragonmounts.plus.common.capability.ArmorEffectManagerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
    @Inject(method = "handleRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;setId(I)V"))
    public void onPlayerClone(
            ClientboundRespawnPacket a,
            CallbackInfo info,
            @Local(ordinal = 0) LocalPlayer oldPlayer,
            @Local(ordinal = 1) LocalPlayer newPlayer
    ) {
        ArmorEffectManagerImpl.onPlayerClone(newPlayer, oldPlayer);
    }
}
