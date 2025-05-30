package net.dragonmounts.plus.common.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.Range;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record ControlDragonPayload(int id, @Range(from = 0, to = 255) int flags) implements CustomPacketPayload {
    public static final Type<ControlDragonPayload> TYPE = new Type<>(makeId("control_dragon"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ControlDragonPayload> CODEC =
            CustomPacketPayload.codec(ControlDragonPayload::encode, ControlDragonPayload::decode);

    public static ControlDragonPayload decode(FriendlyByteBuf buffer) {
        return new ControlDragonPayload(buffer.readVarInt(), buffer.readUnsignedByte());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.id).writeByte(this.flags);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}