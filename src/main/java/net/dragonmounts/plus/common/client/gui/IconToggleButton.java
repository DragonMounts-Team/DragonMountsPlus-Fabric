package net.dragonmounts.plus.common.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

import static net.minecraft.resources.ResourceLocation.withDefaultNamespace;

public class IconToggleButton extends ToggleButton {
    public IconToggleButton(
            int x,
            int y,
            Component message,
            Button.OnPress onPress,
            Function<ToggleButton, MutableComponent> narration
    ) {
        super(x, y, 20, 20, message, onPress, narration);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Icon icon;
        if (!this.active) {
            icon = this.getState() ? Icon.LOCKED_DISABLED : Icon.UNLOCKED_DISABLED;
        } else if (this.isHoveredOrFocused()) {
            icon = this.getState() ? Icon.LOCKED_HOVER : Icon.UNLOCKED_HOVER;
        } else {
            icon = this.getState() ? Icon.LOCKED : Icon.UNLOCKED;
        }
        guiGraphics.blitSprite(RenderType::guiTextured, icon.sprite, this.getX(), this.getY(), this.width, this.height);
    }

    enum Icon {
        LOCKED("widget/locked_button"),
        LOCKED_HOVER("widget/locked_button_highlighted"),
        LOCKED_DISABLED("widget/locked_button_disabled"),
        UNLOCKED("widget/unlocked_button"),
        UNLOCKED_HOVER("widget/unlocked_button_highlighted"),
        UNLOCKED_DISABLED("widget/unlocked_button_disabled");

        final ResourceLocation sprite;

        Icon(String sprite) {
            this.sprite = withDefaultNamespace(sprite);
        }
    }
}
