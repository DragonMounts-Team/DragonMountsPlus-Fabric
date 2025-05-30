package net.dragonmounts.plus.common.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record ToggleSittingPayload(UUID dragon) implements CustomPacketPayload {
    public static final Type<ToggleSittingPayload> TYPE = new Type<>(makeId("toggle_sitting"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleSittingPayload> CODEC =
            CustomPacketPayload.codec(ToggleSittingPayload::encode, ToggleSittingPayload::decode);

    public static ToggleSittingPayload decode(FriendlyByteBuf buffer) {
        return new ToggleSittingPayload(buffer.readUUID());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.dragon);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
