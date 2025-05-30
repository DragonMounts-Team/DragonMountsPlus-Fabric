package net.dragonmounts.plus.compat.platform;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FlammableBlock extends Block {
    public static int getFlammability(Level level, BlockPos pos, BlockState state, Direction side) {
        return FlammableBlockRegistry.getDefaultInstance().get(state.getBlock()).getBurnChance();
    }

    public FlammableBlock(int burn, int speed, Properties props) {
        super(props);
        FlammableBlockRegistry.getDefaultInstance().add(this, burn, speed);
    }
}
