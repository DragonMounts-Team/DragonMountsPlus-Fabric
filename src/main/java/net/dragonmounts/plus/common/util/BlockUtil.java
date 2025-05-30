package net.dragonmounts.plus.common.util;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtil {
    public static void updateNeighborStates(Level level, BlockPos pos, BlockState state, int flag) {
        state.updateNeighbourShapes(level, pos, flag);
        level.updateNeighborsAt(pos, state.getBlock());
    }

    @SuppressWarnings("deprecation")
    public static boolean isSolid(Level level, BlockPos pos) {
        return level.getBlockState(pos).isSolid();
    }
}
