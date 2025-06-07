package net.dragonmounts.plus.mixin;

import net.dragonmounts.plus.common.api.NoiseColumnExtension;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NoiseColumn.class)
public class NoiseColumnMixin implements NoiseColumnExtension {
    @Shadow
    @Final
    private BlockState[] column;

    @Shadow
    @Final
    private int minY;

    @Override
    public int dragonmounts$plus$getMaxHeight() {
        return this.column.length - this.minY - 1;
    }
}
