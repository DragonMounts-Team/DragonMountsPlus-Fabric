package net.dragonmounts.plus.config;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;

public class ConfigEntry {
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
}
