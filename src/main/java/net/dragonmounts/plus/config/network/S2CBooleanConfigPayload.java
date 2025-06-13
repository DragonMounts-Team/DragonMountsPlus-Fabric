package net.dragonmounts.plus.config.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record S2CBooleanConfigPayload(int id, boolean value) implements CustomPacketPayload {
    public static final Type<S2CBooleanConfigPayload> TYPE = new Type<>(makeId("bool_config"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CBooleanConfigPayload> CODEC =
            CustomPacketPayload.codec(S2CBooleanConfigPayload::encode, S2CBooleanConfigPayload::decode);

    public static S2CBooleanConfigPayload decode(FriendlyByteBuf buffer) {
        return new S2CBooleanConfigPayload(buffer.readVarInt(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.id).writeBoolean(this.value);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
