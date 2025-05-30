package net.dragonmounts.plus.common.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public interface DMBiomeTags {
    TagKey<Biome> HAS_AETHER_DRAGON_NEST = create("has_structure/aether_dragon_nest");
    TagKey<Biome> HAS_ENCHANTED_DRAGON_NEST = create("has_structure/enchanted_dragon_nest");
    TagKey<Biome> HAS_FIRE_DRAGON_NEST = create("has_structure/fire_dragon_nest");
    TagKey<Biome> HAS_FOREST_DRAGON_NEST = create("has_structure/forest_dragon_nest");
    TagKey<Biome> HAS_ICE_DRAGON_NEST = create("has_structure/ice_dragon_nest");
    TagKey<Biome> HAS_MOONLIGHT_DRAGON_NEST = create("has_structure/moonlight_dragon_nest");
    TagKey<Biome> HAS_NETHER_DRAGON_NEST = create("has_structure/nether_dragon_nest");
    TagKey<Biome> HAS_SKELETON_DRAGON_NEST = create("has_structure/skeleton_dragon_nest");
    TagKey<Biome> HAS_SUNLIGHT_DRAGON_NEST = create("has_structure/sunlight_dragon_nest");
    TagKey<Biome> HAS_TERRA_DRAGON_NEST = create("has_structure/terra_dragon_nest");
    TagKey<Biome> HAS_WATER_DRAGON_NEST = create("has_structure/water_dragon_nest");
    TagKey<Biome> HAS_ZOMBIE_DRAGON_NEST = create("has_structure/zombie_dragon_nest");

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, makeId(name));
    }
}
