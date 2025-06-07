package net.dragonmounts.plus.common.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record ToggleSittingByIDPayload(int dragon) implements CustomPacketPayload {
    public static final Type<ToggleSittingByIDPayload> TYPE = new Type<>(makeId("toggle_sitting_id"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleSittingByIDPayload> CODEC =
            CustomPacketPayload.codec(ToggleSittingByIDPayload::encode, ToggleSittingByIDPayload::decode);

    public static ToggleSittingByIDPayload decode(FriendlyByteBuf buffer) {
        return new ToggleSittingByIDPayload(buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.dragon);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}