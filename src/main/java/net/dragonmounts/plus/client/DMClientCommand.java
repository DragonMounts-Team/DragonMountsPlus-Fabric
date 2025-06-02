package net.dragonmounts.plus.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;

public class DMClientCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext ignored) {
        dispatcher.register(ClientCommandManager.literal("dragonmounts.plus:client").then(
                ClientCommandManager.literal("config").executes(DMClientCommand::openConfigScreen)
        ));
    }

    public static int openConfigScreen(CommandContext<FabricClientCommandSource> context) {
        var client = context.getSource().getClient();
        client.schedule(() -> client.setScreen(new DMConfigScreen(null)));
        return 1;
    }
}
