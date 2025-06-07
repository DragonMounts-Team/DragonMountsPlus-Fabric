package net.dragonmounts.plus.common.api;

import net.minecraft.world.level.chunk.BlockColumn;

public interface NoiseColumnExtension {
    int dragonmounts$plus$getMaxHeight();

    static int getMaxHeight(BlockColumn column) {
        assert column instanceof NoiseColumnExtension;
        return ((NoiseColumnExtension) column).dragonmounts$plus$getMaxHeight();
    }
}
