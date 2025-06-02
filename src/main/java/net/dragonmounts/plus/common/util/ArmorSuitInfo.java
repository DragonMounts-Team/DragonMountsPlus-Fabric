package net.dragonmounts.plus.common.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;

public record ArmorSuitInfo<T, I extends Item>(
        ResourceKey<Item> helmet,
        ResourceKey<Item> chestplate,
        ResourceKey<Item> leggings,
        ResourceKey<Item> boots,
        Factory<T, I> factory
) {
    public interface Factory<T, I extends Item> {
        I makeArmor(T suit, ArmorType slot, Item.Properties props);
    }
}
