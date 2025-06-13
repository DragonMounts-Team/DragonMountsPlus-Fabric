package net.dragonmounts.plus.compat.platform;

import net.dragonmounts.plus.common.api.CommandOutput;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class PlatformCompat {
    public static boolean isClientSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static @Nullable CommandOutput wrapAsOutput(Object object) {
        return object instanceof FabricClientCommandSource source ? new CommandOutput() {
            @Override
            public void sendSuccess(Supplier<Component> message) {
                source.sendFeedback(message.get());
            }

            @Override
            public void sendFailure(Component message) {
                source.sendError(message);
            }
        } : null;
    }
}
