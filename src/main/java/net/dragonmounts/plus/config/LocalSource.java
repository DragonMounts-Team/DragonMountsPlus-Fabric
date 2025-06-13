package net.dragonmounts.plus.config;

import com.mojang.logging.LogUtils;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocalSource implements ConfigSource {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Path source;

    public LocalSource(Path source) {
        this.source = source;
    }

    public synchronized void loadSync(ConfigHolder holder) {
        var source = this.source;
        try {
            if (Files.isRegularFile(source)) {
                var root = NbtIo.readCompressed(source, NbtAccounter.unlimitedHeap());
                for (var entry : holder.getEntries()) {
                    entry.load(root.get(entry.key));
                    entry.setSaved();
                }
            }
        } catch (Exception exception) {
            LOGGER.error("Exception reading {}", source, exception);
        }
    }

    public synchronized void saveSync(ConfigHolder holder) {
        var source = this.source;
        try {
            CompoundTag result = null;
            if (Files.isRegularFile(source)) {
                result = write(holder, NbtIo.readCompressed(source, NbtAccounter.unlimitedHeap()));
            } else if (Files.notExists(source)) {
                Files.createDirectories(source.getParent());
                result = write(holder, null);
            }
            if (result != null) {
                NbtIo.writeCompressed(result, source);
            }
        } catch (Exception exception) {
            LOGGER.error("Exception writing {}", source, exception);
        }
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void save(ConfigHolder holder) {
        Util.ioPool().execute(() -> this.saveSync(holder));
    }

    @Override
    public void load(ConfigHolder holder) {
        Util.ioPool().execute(() -> this.loadSync(holder));
    }

    public static @Nullable CompoundTag write(ConfigHolder holder, @Nullable CompoundTag exist) {
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
