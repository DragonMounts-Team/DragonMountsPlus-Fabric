package net.dragonmounts.plus.config;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.logging.LogUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public abstract class ConfigHolder<S> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Path source;

    public ConfigHolder(String mod, String file) {
        this.source = FabricLoader.getInstance().getConfigDir().resolve(mod).resolve(file);
    }

    public synchronized void loadSync() {
        var source = this.source;
        try {
            if (Files.isRegularFile(source)) {
                var root = NbtIo.readCompressed(source, NbtAccounter.unlimitedHeap());
                for (var entry : this.getEntries()) {
                    read(entry, root.get(entry.key));
                }
            }
        } catch (Exception exception) {
            LOGGER.error("Exception reading {}", source, exception);
        }
    }

    public synchronized void saveSync() {
        var source = this.source;
        try {
            CompoundTag result = null;
            if (Files.isRegularFile(source)) {
                result = write(this, NbtIo.readCompressed(source, NbtAccounter.unlimitedHeap()));
            } else if (Files.notExists(source)) {
                Files.createDirectories(source.getParent());
                result = write(this, null);
            }
            if (result != null) {
                NbtIo.writeCompressed(result, source);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception writing {}", source, exception);
        }
    }

    public void save() {
        Util.ioPool().execute(this::saveSync);
    }

    public void load() {
        Util.ioPool().execute(this::loadSync);
    }

    public abstract Collection<ConfigEntry<?>> getEntries();

    protected abstract <T> ArgumentBuilder<S, ?> buildCommand(ConfigEntry<T> entry);

    public <T extends ArgumentBuilder<S, T>> T appendCommands(T command) {
        for (var entry : this.getEntries()) {
            command.then(this.buildCommand(entry));
        }
        return command;
    }

    public static <T> void read(ConfigEntry<T> entry, Tag data) {
        entry.set(entry.load(data));
        entry.setSaved();
    }

    public static @Nullable CompoundTag write(ConfigHolder<?> holder, @Nullable CompoundTag exist) {
        boolean changed = false;
        boolean full = exist == null;
        var root = full ? new CompoundTag() : exist;
        for (var entry : holder.getEntries()) {
            if (full || entry.isChanged()) {
                changed = true;
                if (entry.isDefault()) {
                    root.remove(entry.key);
                } else {
                    root.put(entry.key, entry.dump());
                }
                entry.setSaved();
            }
        }
        return changed ? root : null;
    }
}
