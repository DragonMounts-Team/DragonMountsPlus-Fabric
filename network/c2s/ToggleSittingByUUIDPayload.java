package net.dragonmounts.plus.common.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record ToggleSittingByUUIDPayload(UUID dragon) implements CustomPacketPayload {
    public static final Type<ToggleSittingByUUIDPayload> TYPE = new Type<>(makeId("toggle_sitting_uuid"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleSittingByUUIDPayload> CODEC =
            CustomPacketPayload.codec(ToggleSittingByUUIDPayload::encode, ToggleSittingByUUIDPayload::decode);

    public static ToggleSittingByUUIDPayload decode(FriendlyByteBuf buffer) {
        return new ToggleSittingByUUIDPayload(buffer.readUUID());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.dragon);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
