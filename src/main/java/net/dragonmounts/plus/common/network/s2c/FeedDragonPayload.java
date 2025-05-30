package net.dragonmounts.plus.common.network.s2c;

import net.dragonmounts.plus.common.entity.dragon.DragonLifeStage;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public record FeedDragonPayload(int id, int age, DragonLifeStage stage, ItemStack food) implements CustomPacketPayload {
    public static final Type<FeedDragonPayload> TYPE = new Type<>(makeId("feed_dragon"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FeedDragonPayload> CODEC =
            CustomPacketPayload.codec(FeedDragonPayload::encode, FeedDragonPayload::decode);

    public static FeedDragonPayload decode(RegistryFriendlyByteBuf buffer) {
        return new FeedDragonPayload(buffer.readVarInt(), buffer.readVarInt(), DragonLifeStage.byId(buffer.readVarInt()), ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer));
    }

    public void encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeVarInt(this.id).writeVarInt(this.age).writeVarInt(this.stage.ordinal());
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, this.food);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}