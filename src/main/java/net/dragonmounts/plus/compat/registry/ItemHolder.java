package net.dragonmounts.plus.compat.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class ItemHolder<T extends Item> extends ObjectHolder<T, Item> implements ItemLike {
    public static <T extends Item> ItemHolder<T> registerItem(String name, Function<Item.Properties, T> factory) {
        return new ItemHolder<>(makeKey(Registries.ITEM, name), factory);
    }

    public ItemHolder(ResourceKey<Item> key, Function<Item.Properties, T> factory) {
        super(BuiltInRegistries.ITEM, key, factory.apply(new Item.Properties().setId(key)));
    }

    @Override
    public @NotNull Item asItem() {
        return this.get();
    }
}
