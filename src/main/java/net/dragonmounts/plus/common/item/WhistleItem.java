package net.dragonmounts.plus.common.item;

import net.dragonmounts.plus.common.client.ClientUtil;
import net.dragonmounts.plus.common.entity.dragon.Relation;
import net.dragonmounts.plus.common.entity.dragon.ServerDragonEntity;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WhistleItem extends Item {
    public static @Nullable ServerDragonEntity getOrDeny(ServerPlayer player, UUID uuid) {
        if (player.serverLevel().getEntity(uuid) instanceof ServerDragonEntity dragon
                && Relation.checkRelation(dragon, player).isTrusted
        ) return dragon;
        player.sendSystemMessage(Component.translatable("message.dragonmounts.plus.whistle.failed"), true);
        return null;
    }

    public WhistleItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        return player.isShiftKeyDown() ? InteractionResult.PASS : useWhistle(player, hand);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return useWhistle(player, hand);
    }

    public static InteractionResult useWhistle(Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var sound = stack.get(DMDataComponents.WHISTLE_SOUND);
        if (sound == null) return InteractionResult.PASS;
        if (player.isLocalPlayer()) {
            ClientUtil.openWhistleScreen(sound.dragon());
        }
        return InteractionResult.SUCCESS;
    }
}
