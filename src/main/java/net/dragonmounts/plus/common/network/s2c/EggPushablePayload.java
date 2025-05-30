package net.dragonmounts.plus.common.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record EggPushablePayload(boolean value) implements CustomPacketPayload {
    public static final Type<EggPushablePayload> TYPE = new Type<>(makeId("egg_pushable"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EggPushablePayload> CODEC =
            CustomPacketPayload.codec(EggPushablePayload::encode, EggPushablePayload::decode);

    public static EggPushablePayload decode(FriendlyByteBuf buffer) {
        return new EggPushablePayload(buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.value);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}