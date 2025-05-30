package net.dragonmounts.plus.common.block;

import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import static net.dragonmounts.plus.common.DragonMountsShared.BLOCK_TRANSLATION_KEY_PREFIX;

public class HatchableDragonEggBlock extends DragonEggBlock implements DragonTypified {
    public static InteractionResult spawn(Level level, BlockPos pos, DragonType type, boolean isVanilla) {
        level.removeBlock(pos, false);
        var entity = new HatchableDragonEggEntity(level);
        entity.setDragonType(type, true);
        entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        entity.setVanilla(isVanilla);
        level.addFreshEntity(entity);
        return InteractionResult.CONSUME;
    }

    public static final String TRANSLATION_KEY = BLOCK_TRANSLATION_KEY_PREFIX + "dragon_egg";
    public final DragonType type;

    public HatchableDragonEggBlock(DragonType type, Properties props) {
        super(props);
        this.type = type;
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.dimension().equals(Level.END)) {
            super.attack(state, level, pos, player);
        }
    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if (player.level().dimension().equals(Level.END)) return 0.0F;
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (level.dimension().equals(Level.END)) return super.useWithoutItem(state, level, pos, player, hit);
        return spawn(level, pos, this.type, false);
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
