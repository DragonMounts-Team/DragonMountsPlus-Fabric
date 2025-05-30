package net.dragonmounts.plus.common.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record RenameWhistlePayload(String name) implements CustomPacketPayload {
    public static final Type<RenameWhistlePayload> TYPE = new Type<>(makeId("rename_whistle"));
    public static final StreamCodec<FriendlyByteBuf, RenameWhistlePayload> CODEC =
            CustomPacketPayload.codec(RenameWhistlePayload::encode, RenameWhistlePayload::decode);

    public static RenameWhistlePayload decode(FriendlyByteBuf buffer) {
        return new RenameWhistlePayload(buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.name);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
