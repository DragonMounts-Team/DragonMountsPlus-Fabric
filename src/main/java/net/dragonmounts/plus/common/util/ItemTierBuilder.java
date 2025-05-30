package net.dragonmounts.plus.common.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;

public class ItemTierBuilder {
    public final TagKey<Block> incorrectBlocks;
    public final int durability;
    public final float speed;
    public final float damage;
    public int enchantmentValue = 1;

    public ItemTierBuilder(TagKey<Block> incorrectBlocks, int durability, float speed, float damage) {
        this.incorrectBlocks = incorrectBlocks;
        this.durability = durability;
        this.speed = speed;
        this.damage = damage;
    }

    public ItemTierBuilder setEnchantmentValue(int enchantmentValue) {
        this.enchantmentValue = enchantmentValue;
        return this;
    }

    public ToolMaterial build(TagKey<Item> ingredient) {
        return new ToolMaterial(
                this.incorrectBlocks,
                this.durability,
                this.speed,
                this.damage,
                this.enchantmentValue,
                ingredient
        );
    }
}
