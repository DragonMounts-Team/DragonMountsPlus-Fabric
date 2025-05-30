package net.dragonmounts.plus.common.network.c2s;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record TeleportDragonPayload(UUID dragon, BlockPos pos) implements CustomPacketPayload {
    public static final Type<TeleportDragonPayload> TYPE = new Type<>(makeId("tp_dragon"));
    public static final StreamCodec<FriendlyByteBuf, TeleportDragonPayload> CODEC =
            CustomPacketPayload.codec(TeleportDragonPayload::encode, TeleportDragonPayload::decode);

    public static TeleportDragonPayload decode(FriendlyByteBuf buffer) {
        return new TeleportDragonPayload(buffer.readUUID(), buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.dragon).writeBlockPos(this.pos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
