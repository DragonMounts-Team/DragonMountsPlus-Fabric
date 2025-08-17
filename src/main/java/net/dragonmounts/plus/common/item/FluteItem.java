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
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FluteItem extends Item {
    public static @Nullable ServerDragonEntity getOrDeny(ServerPlayer player, UUID uuid) {
        if (player.serverLevel().getEntity(uuid) instanceof ServerDragonEntity dragon
                && Relation.checkRelation(dragon, player).isTrusted
        ) return dragon;
        player.sendSystemMessage(Component.translatable("message.dragonmounts.plus.flute.failed"), true);
        return null;
    }

    /// @see net.minecraft.world.item.ItemUtils#startUsingInstantly(Level, Player, InteractionHand)
    public static InteractionResult startPlaying(Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var sound = stack.get(DMDataComponents.FLUTE_SOUND);
        if (sound == null) return InteractionResult.PASS;
        if (player.isLocalPlayer()) {
            ClientUtil.openFluteScreen(sound.dragon());
        }
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    public FluteItem(Properties props) {
        super(props);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 1200;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        return player.isShiftKeyDown() ? InteractionResult.PASS : startPlaying(player, hand);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return startPlaying(player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return stack;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int time) {
        return true;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return stack.has(DMDataComponents.FLUTE_SOUND) ? ItemUseAnimation.TOOT_HORN : ItemUseAnimation.NONE;
    }
}
