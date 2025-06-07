package net.dragonmounts.plus.common.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.compat.registry.BlockHolder;
import net.dragonmounts.plus.compat.registry.BlockItemHolder;
import net.dragonmounts.plus.compat.registry.ItemHolder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ItemGroup implements CreativeModeTab.DisplayItemsGenerator {
    public final ObjectArrayList<ItemLike> items;

    public ItemGroup(ObjectArrayList<ItemLike> items) {
        this.items = items;
    }

    public final <T extends Item> ItemHolder<T> register(String name, Function<Item.Properties, T> factory) {
        var holder = ItemHolder.registerItem(name, factory);
        this.items.add(holder);
        return holder;
    }

    public final <B extends Block, T extends Item> BlockItemHolder<B, T> register(BlockHolder<B> block, BiFunction<B, Item.Properties, T> factory) {
        var holder = BlockItemHolder.registerItem(block, factory);
        this.items.add(holder);
        return holder;
    }

    public void add(ItemLike item) {
        this.items.add(item);
    }

    public void addAll(Collection<? extends ItemLike> items) {
        this.items.addAll(items);
    }

    @Override
    public void accept(@NotNull CreativeModeTab.ItemDisplayParameters args, CreativeModeTab.Output entries) {
        this.items.forEach(entries::accept);
    }
}