package net.dragonmounts.plus.compat.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class BlockItemHolder<B extends Block, I extends Item> extends ObjectHolder<I, Item> implements ItemLike {
    public static <B extends Block, I extends Item> BlockItemHolder<B, I> registerItem(BlockHolder<B> block, BiFunction<B, Item.Properties, I> factory) {
        var key = ResourceKey.create(Registries.ITEM, block.key.location());
        var item = factory.apply(block.get(), new Item.Properties().setId(key).useBlockDescriptionPrefix());
        if (item instanceof BlockItem) {
            ((BlockItem) item).registerBlocks(Item.BY_BLOCK, item);
        }
        return new BlockItemHolder<>(block, key, item);
    }

    public final BlockHolder<B> block;

    public BlockItemHolder(BlockHolder<B> block, ResourceKey<Item> key, I item) {
        super(BuiltInRegistries.ITEM, key, item);
        this.block = block;

    }

    @Override
    public @NotNull Item asItem() {
        return this.get();
    }
}