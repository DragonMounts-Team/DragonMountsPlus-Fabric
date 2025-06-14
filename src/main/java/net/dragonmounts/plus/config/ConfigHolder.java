package net.dragonmounts.plus.config;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.util.Collection;

public abstract class ConfigHolder {
    public final LocalSource local;

    public ConfigHolder(String mod, String file) {
        this.local = new LocalSource(FabricLoader.getInstance().getConfigDir().resolve(mod).resolve(file));
    }

    public abstract ConfigSource getSource();

    public abstract Collection<ConfigEntry<?>> getEntries();

    public abstract void save();

    public abstract void broadcast(ConfigEntry<?> entry);

    public <S, T extends ArgumentBuilder<S, T>> T appendCommands(T command) {
        for (var entry : this.getEntries()) {
            command.then(buildCommand(entry));
        }
        return command;
    }

    static <S, T> ArgumentBuilder<S, ?> buildCommand(ConfigEntry<T> entry) {
        return LiteralArgumentBuilder.<S>literal(entry.key).executes(entry::query)
                .then(RequiredArgumentBuilder.<S, T>argument("value", entry.getArgument()).executes(entry::modify));
    }
}
