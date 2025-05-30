package net.dragonmounts.plus.common.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record ArmorRipostePayload(int id, int flag) implements CustomPacketPayload {
    public static final Type<ArmorRipostePayload> TYPE = new Type<>(makeId("armor_riposte"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorRipostePayload> CODEC = CustomPacketPayload.codec(ArmorRipostePayload::encode, ArmorRipostePayload::decode);

    public static ArmorRipostePayload decode(FriendlyByteBuf buffer) {
        return new ArmorRipostePayload(buffer.readVarInt(), buffer.readVarInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.id).writeVarInt(this.flag);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}