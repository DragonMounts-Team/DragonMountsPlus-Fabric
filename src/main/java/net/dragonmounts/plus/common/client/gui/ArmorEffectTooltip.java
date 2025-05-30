package net.dragonmounts.plus.common.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArmorEffectTooltip implements ClientTooltipComponent {
    protected final @Nullable Component trigger;
    protected final List<Component> description;
    protected int widthCache;
    protected int heightCache;

    public ArmorEffectTooltip(List<Component> description, @Nullable Component trigger) {
        this.trigger = trigger;
        this.description = description;
    }

    @Override
    public int getWidth(Font font) {
        if (this.widthCache == 0) {
            int max = this.trigger == null ? 0 : font.width(this.trigger);
            for (var component : this.description) {
                int width = font.width(component);
                if (width > max) {
                    max = width;
                }
            }
            this.widthCache = Math.min(max, Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2);
        }
        return this.widthCache;
    }

    @Override
    public int getHeight(Font font) {
        if (this.heightCache == 0) {
            int width = this.widthCache;
            int height = font.lineHeight + this.description.size() * 2;
            for (var component : this.description) {
                height += font.wordWrapHeight(component, width);
            }
            this.heightCache = this.trigger == null ? height : height + 2 + font.wordWrapHeight(this.trigger, width);
        }
        return this.heightCache;
    }

    /**
     * @see GuiGraphics#drawWordWrap(Font, FormattedText, int, int, int, int, boolean)
     */
    @Override
    public void renderImage(Font font, int x, int y, int width, int height, GuiGraphics graphics) {
        int line = font.lineHeight;
        if (this.trigger == null) {
            y += line;
        } else {
            for (var text : font.split(this.trigger, width)) {
                y += line;
                graphics.drawString(font, text, x, y, -1, true);
            }
        }
        for (var component : this.description) {
            y += 2;
            for (var text : font.split(component, width)) {
                y += line;
                graphics.drawString(font, text, x, y, -1, true);
            }
        }
    }
}
