package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.structure.DragonNestPiece;
import net.dragonmounts.plus.common.structure.DragonNestStructure;
import net.dragonmounts.plus.common.structure.NestConfig;
import net.dragonmounts.plus.common.structure.NestPlacement;
import net.dragonmounts.plus.common.tag.DMBiomeTags;
import net.dragonmounts.plus.common.util.DragonNestRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.List;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerStructure;

public interface DMStructures {
    StructureType<DragonNestStructure> DRAGON_NEST = registerStructure("dragon_nest", () -> DragonNestStructure.CODEC);
    StructurePieceType DRAGON_NEST_PIECE = registerStructure("nest", DragonNestPiece::new);
    ResourceKey<Structure> AETHER_DRAGON_NEST = register("aether_dragon_nest");
    ResourceKey<Structure> ENCHANTED_DRAGON_NEST = register("enchanted_dragon_nest");
    ResourceKey<Structure> FIRE_DRAGON_NEST = register("fire_dragon_nest");
    ResourceKey<Structure> FOREST_DRAGON_NEST = register("forest_dragon_nest");
    ResourceKey<Structure> ICE_DRAGON_NEST = register("ice_dragon_nest");
    ResourceKey<Structure> MOONLIGHT_DRAGON_NEST = register("moonlight_dragon_nest");
    ResourceKey<Structure> NETHER_DRAGON_NEST = register("nether_dragon_nest");
    ResourceKey<Structure> SKELETON_DRAGON_NEST = register("skeleton_dragon_nest");
    ResourceKey<Structure> SUNLIGHT_DRAGON_NEST = register("sunlight_dragon_nest");
    ResourceKey<Structure> TERRA_DRAGON_NEST = register("terra_dragon_nest");
    ResourceKey<Structure> WATER_DRAGON_NEST = register("water_dragon_nest");
    ResourceKey<Structure> ZOMBIE_DRAGON_NEST = register("zombie_dragon_nest");

    static ResourceKey<Structure> register(String name) {
        return makeKey(Registries.STRUCTURE, name);
    }

    static void bootstrap(BootstrapContext<Structure> context) {
        var registry = new DragonNestRegistry(context);
        registry.register(
                AETHER_DRAGON_NEST,
                DMBiomeTags.HAS_AETHER_DRAGON_NEST,
                new NestConfig(NestPlacement.IN_CLOUDS, List.of(makeId("aether"))),
                new NestConfig(NestPlacement.IN_CLOUDS, List.of(makeId("aether2")))
        );
        registry.register(
                ENCHANTED_DRAGON_NEST,
                DMBiomeTags.HAS_ENCHANTED_DRAGON_NEST,
                new NestConfig(NestPlacement.ON_LAND_SURFACE, List.of(makeId("enchanted")))
        );
        var fire = List.of(makeId("fire"), makeId("fire2"));
        registry.register(
                FIRE_DRAGON_NEST,
                DMBiomeTags.HAS_FIRE_DRAGON_NEST,
                new NestConfig(NestPlacement.IN_MOUNTAIN, fire),
                new NestConfig(NestPlacement.PARTLY_BURIED, fire),
                new NestConfig(NestPlacement.UNDERGROUND, List.of(makeId("fire")))
        );
        var forest = List.of(makeId("forest1"), makeId("forest2"));
        registry.register(
                FOREST_DRAGON_NEST,
                DMBiomeTags.HAS_FOREST_DRAGON_NEST,
                new NestConfig(NestPlacement.ON_LAND_SURFACE, forest),
                new NestConfig(NestPlacement.PARTLY_BURIED, forest)
        );
        var ice = List.of(makeId("ice"));
        registry.register(
                ICE_DRAGON_NEST,
                DMBiomeTags.HAS_ICE_DRAGON_NEST,
                new NestConfig(NestPlacement.ON_LAND_SURFACE, ice),
                new NestConfig(NestPlacement.PARTLY_BURIED, ice)
        );
        registry.register(
                MOONLIGHT_DRAGON_NEST,
                DMBiomeTags.HAS_MOONLIGHT_DRAGON_NEST,
                new NestConfig(NestPlacement.IN_CLOUDS, List.of(makeId("moonlight")))
        );
        registry.register(
                NETHER_DRAGON_NEST,
                DMBiomeTags.HAS_NETHER_DRAGON_NEST,
                new NestConfig(NestPlacement.IN_NETHER, List.of(makeId("nether")))
        );
        registry.register(
                SKELETON_DRAGON_NEST,
                DMBiomeTags.HAS_SKELETON_DRAGON_NEST,
                new NestConfig(NestPlacement.FLUSH_WITH_SURFACE, List.of(makeId("skeleton")))
        );
        registry.register(
                SUNLIGHT_DRAGON_NEST,
                DMBiomeTags.HAS_SUNLIGHT_DRAGON_NEST,
                new NestConfig(NestPlacement.PARTLY_BURIED, List.of(makeId("sunlight")))
        );
        var terra = List.of(makeId("terra"));
        registry.register(
                TERRA_DRAGON_NEST,
                DMBiomeTags.HAS_TERRA_DRAGON_NEST,
                new NestConfig(NestPlacement.ON_LAND_SURFACE, terra),
                new NestConfig(NestPlacement.PARTLY_BURIED, terra)
        );
        var water = List.of(makeId("water1"), makeId("water2"), makeId("water3"));
        registry.register(
                WATER_DRAGON_NEST,
                DMBiomeTags.HAS_WATER_DRAGON_NEST,
                new NestConfig(NestPlacement.ON_LAND_SURFACE, water),
                new NestConfig(NestPlacement.ON_OCEAN_FLOOR, water)
        );
        registry.register(
                ZOMBIE_DRAGON_NEST,
                DMBiomeTags.HAS_ZOMBIE_DRAGON_NEST,
                new NestConfig(NestPlacement.FLUSH_WITH_SURFACE, List.of(makeId("zombie")))
        );
    }

    static void init() {}
}
