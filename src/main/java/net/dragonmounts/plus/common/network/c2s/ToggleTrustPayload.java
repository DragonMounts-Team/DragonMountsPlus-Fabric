package net.dragonmounts.plus.common.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record ToggleTrustPayload(int dragon) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ToggleTrustPayload> TYPE = new CustomPacketPayload.Type<>(makeId("toggle_trust"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleTrustPayload> CODEC =
            CustomPacketPayload.codec(ToggleTrustPayload::encode, ToggleTrustPayload::decode);

    public static ToggleTrustPayload decode(FriendlyByteBuf buffer) {
        return new ToggleTrustPayload(buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.dragon);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}