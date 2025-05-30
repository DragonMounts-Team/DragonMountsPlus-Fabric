package net.dragonmounts.plus.common.api;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ConditionalShearable {
    default boolean readyForShearing(ServerLevel level, ItemStack stack) {
        return true;
    }

    boolean shear(ServerLevel level, @Nullable Player player, ItemStack stack, BlockPos pos, SoundSource source);
}
