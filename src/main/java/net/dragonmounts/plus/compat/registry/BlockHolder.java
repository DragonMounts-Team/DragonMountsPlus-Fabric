package net.dragonmounts.plus.compat.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class BlockHolder<T extends Block> extends ObjectHolder<T, Block> implements ItemLike {
    public static <T extends Block> BlockHolder<T> registerBlock(String name, Function<Properties, T> factory) {
        return new BlockHolder<>(makeKey(Registries.BLOCK, name), factory);
    }

    public BlockHolder(ResourceKey<Block> key, Function<Properties, T> factory) {
        super(BuiltInRegistries.BLOCK, key, factory.apply(Properties.of().setId(key)));
    }

    @Override
    public final @NotNull Item asItem() {
        return this.value.asItem();
    }

    public final BlockState defaultBlockState() {
        return this.value.defaultBlockState();
    }
}