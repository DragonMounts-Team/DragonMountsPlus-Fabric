package net.dragonmounts.plus.common.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record ToggleFollowingPayload(UUID dragon) implements CustomPacketPayload {
    public static final Type<ToggleFollowingPayload> TYPE = new Type<>(makeId("toggle_following"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleFollowingPayload> CODEC =
            CustomPacketPayload.codec(ToggleFollowingPayload::encode, ToggleFollowingPayload::decode);

    public static ToggleFollowingPayload decode(FriendlyByteBuf buffer) {
        return new ToggleFollowingPayload(buffer.readUUID());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.dragon);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
