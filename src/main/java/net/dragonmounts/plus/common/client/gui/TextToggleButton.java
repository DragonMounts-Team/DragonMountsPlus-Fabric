package net.dragonmounts.plus.common.client.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Function;

public class TextToggleButton extends ToggleButton {
    public TextToggleButton(
            int x,
            int y,
            int width,
            Component message,
            OnPress onPress,
            Function<ToggleButton, MutableComponent> narration
    ) {
        super(x, y, width, 20, message, onPress, narration);
    }

    public void setState(boolean state, Component enabled, Component disabled) {
        this.setState(state);
        this.setMessage(this.getState() ? enabled : disabled);
    }
}
