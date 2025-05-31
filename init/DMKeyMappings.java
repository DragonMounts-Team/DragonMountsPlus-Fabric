package net.dragonmounts.plus.common.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.dragonmounts.plus.config.ClientConfig;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.ToggleKeyMapping;

import java.util.function.Consumer;

public class DMKeyMappings {
    public static final String KEY_CATEGORY = "key.categories.dragonmounts.plus";
    public static final ToggleKeyMapping DESCEND = new ToggleKeyMapping(
            "key.dragonmounts.plus.descend",
            InputConstants.KEY_Z,
            KEY_CATEGORY,
            ClientConfig.INSTANCE.toggle_descending::get
    );
    public static final ToggleKeyMapping BREATHE = new ToggleKeyMapping(
            "key.dragonmounts.plus.breathe",
            InputConstants.KEY_R,
            KEY_CATEGORY,
            ClientConfig.INSTANCE.toggle_breathing::get
    );

    public static void register(Consumer<KeyMapping> registry) {
        registry.accept(DESCEND);
        registry.accept(BREATHE);
    }
}
