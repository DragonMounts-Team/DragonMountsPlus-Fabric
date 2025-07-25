package net.dragonmounts.plus.common.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record RenameFlutePayload(String name) implements CustomPacketPayload {
    public static final Type<RenameFlutePayload> TYPE = new Type<>(makeId("rename_flute"));
    public static final StreamCodec<FriendlyByteBuf, RenameFlutePayload> CODEC =
            CustomPacketPayload.codec(RenameFlutePayload::encode, RenameFlutePayload::decode);

    public static RenameFlutePayload decode(FriendlyByteBuf buffer) {
        return new RenameFlutePayload(buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.name);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
