package net.dragonmounts.plus.config;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.dragonmounts.plus.common.api.CommandOutput;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigEntry<T> {
    public final ConfigHolder holder;
    public final String key;
    public final String name;
    public final String tooltip;

    public ConfigEntry(ConfigHolder holder, String key, String name, String tooltip) {
        this.holder = holder;
        this.key = key;
        this.name = name;
        this.tooltip = tooltip;
    }

    public abstract String getAsString();

    public boolean store(T value) {
        if (!this.holder.getSource().isReadOnly() && this.modify(value)) {
            this.holder.broadcast(this);
            return true;
        }
        return false;
    }

    public abstract T parse(CommandContext<?> context, String name);

    public abstract ArgumentType<T> getArgument();

    /// @return if it is changed
    public abstract boolean modify(T value);

    public abstract boolean isChanged();

    public abstract boolean isDefault();

    public abstract void setSaved();

    public abstract Tag dump();

    public abstract void load(@Nullable Tag data);

    public abstract CustomPacketPayload wrap(int id);

    public final MutableComponent getDisplayName() {
        return ComponentUtils.wrapInSquareBrackets(Component.translatable(this.name)).withStyle(style ->
                style.withHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT, Component.translatable(this.tooltip))
                ).withColor(ChatFormatting.GREEN)
        );
    }

    public final int modify(CommandContext<?> context) {
        var output = CommandOutput.wrap(context.getSource());
        if (this.store(this.parse(context, "value"))) {
            this.holder.save();
            if (output != null) {
                output.sendSuccess(() ->
                        Component.translatable("commands.dragonmounts.plus.config.modify", this.getDisplayName(), this.getAsString())
                );
            }
            return 1;
        } else if (output != null) {
            output.sendFailure(Component.translatable("commands.dragonmounts.plus.config.readonly", this.getDisplayName()));
        }
        return 0;
    }

    public final int query(CommandContext<?> context) {
        var output = CommandOutput.wrap(context.getSource());
        if (output != null) {
            output.sendSuccess(() ->
                    Component.translatable("commands.dragonmounts.plus.config.query", this.getDisplayName(), this.getAsString())
            );
        }
        return 1;
    }
}
