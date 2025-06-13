package net.dragonmounts.plus.config.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record S2CDoubleConfigPayload(int id, double value) implements CustomPacketPayload {
    public static final Type<S2CDoubleConfigPayload> TYPE = new Type<>(makeId("double_config"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CDoubleConfigPayload> CODEC =
            CustomPacketPayload.codec(S2CDoubleConfigPayload::encode, S2CDoubleConfigPayload::decode);

    public static S2CDoubleConfigPayload decode(FriendlyByteBuf buffer) {
        return new S2CDoubleConfigPayload(buffer.readVarInt(), buffer.readDouble());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.id).writeDouble(this.value);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
