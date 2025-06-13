package net.dragonmounts.plus.common.api;

import net.dragonmounts.plus.compat.platform.PlatformCompat;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface CommandOutput {
    void sendSuccess(Supplier<Component> message);

    void sendFailure(Component message);

    static @Nullable CommandOutput wrap(Object object) {
        return object instanceof CommandSourceStack source ? new CommandOutput() {
            @Override
            public void sendSuccess(Supplier<Component> message) {
                source.sendSuccess(message, true);
            }

            @Override
            public void sendFailure(Component message) {
                source.sendFailure(message);
            }
        } : PlatformCompat.isClientSide() ? PlatformCompat.wrapAsOutput(object) : null;
    }
}
