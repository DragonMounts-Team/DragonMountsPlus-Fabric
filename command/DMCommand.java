package net.dragonmounts.plus.common.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.dragonmounts.plus.common.DragonMountsShared;
import net.dragonmounts.plus.config.ServerConfig;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;

import java.util.function.Predicate;

import static net.minecraft.network.chat.HoverEvent.Action.SHOW_ENTITY;


public class DMCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection ignored) {
        Predicate<CommandSourceStack> hasPermissionLevel2 = source -> source.hasPermission(2);
        dispatcher.register(Commands.literal(DragonMountsShared.NAMESPACE)
                .then(ServerConfig.buildCommand(source -> source.hasPermission(3)))
                .then(CooldownCommand.register(context, hasPermissionLevel2))
                .then(FreeCommand.register(hasPermissionLevel2))
                .then(SaveCommand.register(context, hasPermissionLevel2))
                .then(StageCommand.register(hasPermissionLevel2))
                .then(TameCommand.register(hasPermissionLevel2))
                .then(TypeCommand.register(context, hasPermissionLevel2))
        );
    }

    public static Component createClassCastException(Class<?> from, Class<?> to) {
        return Component.literal("java.lang.ClassCastException: " + from.getName() + " cannot be cast to " + to.getName());
    }

    public static Component createClassCastException(Entity entity, Class<?> clazz) {
        return Component.literal("java.lang.ClassCastException: ").append(Component.literal(entity.getClass().getName())
                .setStyle(Style.EMPTY.withInsertion(entity.getStringUUID()).withHoverEvent(
                        new HoverEvent(SHOW_ENTITY, new HoverEvent.EntityTooltipInfo(entity.getType(), entity.getUUID(), entity.getName()))
                ))
        ).append(" cannot be cast to " + clazz.getName());
    }

    public static GameProfile getSingleProfileOrException(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
        var profiles = GameProfileArgument.getGameProfiles(context, name);
        if (profiles.isEmpty()) throw EntityArgument.NO_PLAYERS_FOUND.create();
        if (profiles.size() > 1) throw EntityArgument.ERROR_NOT_SINGLE_PLAYER.create();
        return profiles.iterator().next();
    }
}
