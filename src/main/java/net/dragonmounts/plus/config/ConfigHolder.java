package net.dragonmounts.plus.config;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.logging.LogUtils;
import net.dragonmounts.plus.compat.platform.PlatformCompat;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public abstract class ConfigHolder {
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
                for (var value : this.getValues()) {
                    read(value, root.get(value.getEntry().key));
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

    public abstract Collection<ConfigValue<?>> getValues();

    public abstract void broadcast(ConfigValue<?> entry);

    public <S, T extends ArgumentBuilder<S, T>> T appendCommands(T command) {
        for (var entry : this.getValues()) {
            command.then(this.buildCommand(entry));
        }
        return command;
    }

    <S, T> ArgumentBuilder<S, ?> buildCommand(ConfigValue<T> value) {
        var entry = value.getEntry();
        return LiteralArgumentBuilder.<S>literal(entry.key).executes(context -> PlatformCompat.sendSuccess(context.getSource(), () ->
                Component.translatable("commands.dragonmounts.plus.config.query", value.getEntry().getDisplayName(), value.getAsString())
        )).then(RequiredArgumentBuilder.<S, T>argument("value", value.getArgument()).executes(context -> {
            if (value.set(value.parse(context, "value"))) {
                this.save();
                this.broadcast(value);
            }
            return PlatformCompat.sendSuccess(context.getSource(), () ->
                    Component.translatable("commands.dragonmounts.plus.config.modify", value.getEntry().getDisplayName(), value.getAsString())
            );
        }));
    }

    public static <T> void read(ConfigValue<T> value, Tag data) {
        value.set(value.load(data));
        value.setSaved();
    }

    public static @Nullable CompoundTag write(ConfigHolder holder, @Nullable CompoundTag exist) {
        boolean changed = false;
        boolean full = exist == null;
        var root = full ? new CompoundTag() : exist;
        for (var value : holder.getValues()) {
            if (full || value.isChanged()) {
                changed = true;
                if (value.isDefault()) {
                    root.remove(value.getEntry().key);
                } else {
                    root.put(value.getEntry().key, value.dump());
                }
                value.setSaved();
            }
        }
        return changed ? root : null;
    }
}
