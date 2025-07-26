package net.dragonmounts.plus.config;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigEntry<T> {
    public final String key;
    public final String name;
    public final String tooltip;

    public ConfigEntry(String key, String name, String tooltip) {
        this.key = key;
        this.name = name;
        this.tooltip = tooltip;
    }

    public final MutableComponent getDisplayName() {
        return ComponentUtils.wrapInSquareBrackets(Component.translatable(this.name)).withStyle(style ->
                style.withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT, Component.translatable(this.tooltip))
                ).withColor(ChatFormatting.GREEN)
        );
    }

    public abstract boolean isChanged();

    public abstract boolean isDefault();

    public abstract String getAsString();

    public abstract Tag dump();

    public abstract T load(@Nullable Tag data);

    /// @return if it is changed
    public abstract boolean set(T value);

    public abstract void override(T value);

    /// set to fallback
    public abstract void reset();

    /// set to saved
    public abstract void revert();

    public abstract void setSaved();

    public abstract CustomPacketPayload wrap(int id);

    public abstract ArgumentType<T> getArgument();

    public abstract T parse(CommandContext<?> context, String name);
}
