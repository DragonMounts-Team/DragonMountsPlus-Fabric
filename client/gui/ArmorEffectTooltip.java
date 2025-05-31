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
    protected final Component title;
    protected final @Nullable Component trigger;
    protected final List<Component> description;
    protected int widthCache;
    protected int heightCache;

    public ArmorEffectTooltip(Component title, List<Component> description, @Nullable Component trigger) {
        this.title = title;
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
            int width = font.width(this.title);
            if (width > max) {
                max = width;
            }
            this.widthCache = Math.min(max, Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2);
        }
        return this.widthCache;
    }

    @Override
    public int getHeight(Font font) {
        if (this.heightCache == 0) {
            int width = this.widthCache;
            int height = font.lineHeight + this.description.size();
            for (var component : this.description) {
                height += font.wordWrapHeight(component, width);
            }
            height += font.wordWrapHeight(this.title, width) + 1;
            this.heightCache = this.trigger == null ? height : height + 1 + font.wordWrapHeight(this.trigger, width);
        }
        return this.heightCache;
    }

    /**
     * @see GuiGraphics#drawWordWrap(Font, FormattedText, int, int, int, int, boolean)
     */
    @Override
    public void renderImage(Font font, int x, int y, int width, int height, GuiGraphics graphics) {
        int line = font.lineHeight;
        for (var text : font.split(this.title, width)) {
            graphics.drawString(font, text, x, y, -1, true);
            y += line;
        }
        if (this.trigger != null) {
            ++y;
            for (var text : font.split(this.trigger, width)) {
                y += line;
                graphics.drawString(font, text, x, y, -1, true);
            }
        }
        for (var component : this.description) {
            ++y;
            for (var text : font.split(component, width)) {
                y += line;
                graphics.drawString(font, text, x, y, -1, true);
            }
        }
    }
}
