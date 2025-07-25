package net.dragonmounts.plus.config;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.Nullable;

public interface ConfigValue<T> {
    ConfigEntry getEntry();

    boolean isChanged();

    boolean isDefault();

    String getAsString();

    Tag dump();

    T load(@Nullable Tag data);

    /// @return if it is changed
    boolean set(T value);

    void override(T value);

    /// set to fallback
    void reset();

    /// set to saved
    void revert();

    void setSaved();

    CustomPacketPayload wrap(int id);

    ArgumentType<T> getArgument();

    T parse(CommandContext<?> context, String name);
}
