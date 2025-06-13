package net.dragonmounts.plus.config.network;

import net.dragonmounts.plus.config.ConfigEntry;
import net.dragonmounts.plus.config.ConfigHolder;
import net.dragonmounts.plus.config.ConfigSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class RemoteSource implements ConfigSource {
    private final CompoundTag cache;

    public RemoteSource(CompoundTag cache) {
        this.cache = cache;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public void save(ConfigHolder holder) {}

    @Override
    public void load(ConfigHolder holder) {
        for (var entry : holder.getEntries()) {
            entry.load(this.cache.get(entry.key));
        }
    }

    public void update(ConfigEntry<?> entry, Tag value) {
        entry.load(value);
        this.cache.put(entry.key, value);
    }
}
