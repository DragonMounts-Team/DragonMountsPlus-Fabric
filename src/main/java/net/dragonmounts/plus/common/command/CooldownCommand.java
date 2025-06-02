package net.dragonmounts.plus.common.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.dragonmounts.plus.common.DragonMountsShared;
import net.dragonmounts.plus.common.capability.ArmorEffectManager.Provider;
import net.dragonmounts.plus.compat.registry.CooldownCategory;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.function.Predicate;

public class CooldownCommand {
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext builder, Predicate<CommandSourceStack> permission) {
        return Commands.literal("cooldown")
                .requires(permission)
                .then(Commands.argument("players", EntityArgument.players())
                        .then(Commands.argument("category", ResourceArgument.resource(builder, DragonMountsShared.COOLDOWN_CATEGORY))
                                .executes(context -> get(
                                        context.getSource(),
                                        EntityArgument.getPlayers(context, "players"),
                                        ResourceArgument.getResource(context, "category", DragonMountsShared.COOLDOWN_CATEGORY).value()
                                ))
                                .then(Commands.argument("cooldown", IntegerArgumentType.integer(0))
                                        .executes(context -> set(
                                                context.getSource(),
                                                EntityArgument.getPlayers(context, "players"),
                                                ResourceArgument.getResource(context, "category", DragonMountsShared.COOLDOWN_CATEGORY).value(),
                                                IntegerArgumentType.getInteger(context, "cooldown")
                                        ))
                                )
                        )
                );
    }

    public static int get(CommandSourceStack source, Collection<ServerPlayer> players, CooldownCategory category) {
        if (players.isEmpty()) {
            source.sendFailure(Component.translatable("commands.dragonmounts.plus.cooldown.get.failure"));
            return 0;
        }
        source.sendSuccess(() -> Component.translatable(
                "commands.dragonmounts.plus.cooldown.get.success",
                category.identifier.toString(),
                ComponentUtils.formatList(players, player -> Component.translatable(
                        "commands.dragonmounts.plus.cooldown.get.entry",
                        player.getDisplayName(),
                        ((Provider) player).dragonmounts$plus$getManager().getCooldown(category)
                ))
        ), true);
        return players.size();
    }

    public static int set(CommandSourceStack source, Collection<ServerPlayer> players, CooldownCategory category, int value) {
        if (players.isEmpty()) {
            source.sendFailure(Component.translatable("commands.dragonmounts.plus.cooldown.set.failure"));
            return 0;
        }
        for (var player : players) {
            ((Provider) player).dragonmounts$plus$getManager().setCooldown(category, value);
        }
        int size = players.size();
        if (size == 1) {
            source.sendSuccess(() -> Component.translatable("commands.dragonmounts.plus.cooldown.set.single", players.iterator().next().getDisplayName(), category.identifier.toString(), value), true);
        } else {
            source.sendSuccess(() -> Component.translatable("commands.dragonmounts.plus.cooldown.set.multiple", size, category.identifier.toString(), value), true);
        }
        return size;
    }
}
