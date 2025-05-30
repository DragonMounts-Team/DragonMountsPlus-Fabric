package net.dragonmounts.plus.common.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public interface DMItemTags {
    TagKey<Item> DRAGON_EGGS = create("dragon_eggs");
    TagKey<Item> DRAGON_SCALE_BLOCKS = create("dragon_scale_blocks");
    TagKey<Item> DRAGON_SCALE_BOWS = create("dragon_scale_bows");
    TagKey<Item> DRAGON_SCALE_SHIELDS = create("dragon_scale_shields");
    TagKey<Item> DRAGON_SCALES = create("dragon_scales");
    TagKey<Item> DRAGON_INEDIBLE = create("dragon_inedible");
    TagKey<Item> COOKED_DRAGON_FOODS = create("cooked_dragon_foods");
    TagKey<Item> RAW_DRAGON_FOODS = create("raw_dragon_foods");
    TagKey<Item> BATONS = create("batons");
    TagKey<Item> HARD_SHEARS = create("hard_shears");
    TagKey<Item> AETHER_DRAGON_SCALES = create("dragon_scales/aether");
    TagKey<Item> DARK_DRAGON_SCALES = create("dragon_scales/dark");
    TagKey<Item> ENCHANTED_DRAGON_SCALES = create("dragon_scales/enchanted");
    TagKey<Item> ENDER_DRAGON_SCALES = create("dragon_scales/ender");
    TagKey<Item> FIRE_DRAGON_SCALES = create("dragon_scales/fire");
    TagKey<Item> FOREST_DRAGON_SCALES = create("dragon_scales/forest");
    TagKey<Item> ICE_DRAGON_SCALES = create("dragon_scales/ice");
    TagKey<Item> MOONLIGHT_DRAGON_SCALES = create("dragon_scales/moonlight");
    TagKey<Item> NETHER_DRAGON_SCALES = create("dragon_scales/nether");
    TagKey<Item> SCULK_DRAGON_SCALES = create("dragon_scales/sculk");
    TagKey<Item> STORM_DRAGON_SCALES = create("dragon_scales/storm");
    TagKey<Item> SUNLIGHT_DRAGON_SCALES = create("dragon_scales/sunlight");
    TagKey<Item> TERRA_DRAGON_SCALES = create("dragon_scales/terra");
    TagKey<Item> WATER_DRAGON_SCALES = create("dragon_scales/water");
    TagKey<Item> ZOMBIE_DRAGON_SCALES = create("dragon_scales/zombie");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, makeId(name));
    }
}
