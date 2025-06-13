package net.dragonmounts.plus.config.network;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.minecraft.network.FriendlyByteBuf.readNbt;

public record S2CSyncConfigPayload(List<Entry> entries) implements CustomPacketPayload {
    public static final Type<S2CSyncConfigPayload> TYPE = new Type<>(makeId("sync_config"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncConfigPayload> CODEC =
            CustomPacketPayload.codec(S2CSyncConfigPayload::encode, S2CSyncConfigPayload::decode);

    public static S2CSyncConfigPayload decode(FriendlyByteBuf buffer) {
        int size = buffer.readVarInt();
        var entries = new ObjectArrayList<Entry>(size);
        for (int i = 0; i < size; ++i) {
            entries.add(new Entry(buffer.readVarInt(), readNbt(buffer, NbtAccounter.create(2097152L))));
        }
        return new S2CSyncConfigPayload(entries);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeVarInt(this.entries.size());
        for (var entry : this.entries) {
            buffer.writeVarInt(entry.id).writeNbt(entry.value);
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public record Entry(int id, Tag value) {}
}