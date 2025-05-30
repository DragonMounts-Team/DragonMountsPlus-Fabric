package net.dragonmounts.plus.common.init;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;

import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public interface DMStructureSets {
    ResourceKey<StructureSet> DRAGON_NESTS = makeKey(Registries.STRUCTURE_SET, "dragon_nests");

    @SafeVarargs
    static List<StructureSet.StructureSelectionEntry> makeEntries(
            HolderGetter<Structure> registry,
            ResourceKey<Structure>... structures
    ) {
        var list = new ObjectArrayList<StructureSet.StructureSelectionEntry>(structures.length);
        for (var structure : structures) {
            list.add(StructureSet.entry(registry.getOrThrow(structure)));
        }
        return list;
    }

    static void bootstrap(BootstrapContext<StructureSet> context) {
        context.register(DRAGON_NESTS, new StructureSet(makeEntries(
                context.lookup(Registries.STRUCTURE),
                DMStructures.AETHER_DRAGON_NEST,
                DMStructures.ENCHANTED_DRAGON_NEST,
                DMStructures.FIRE_DRAGON_NEST,
                DMStructures.FOREST_DRAGON_NEST,
                DMStructures.ICE_DRAGON_NEST,
                DMStructures.MOONLIGHT_DRAGON_NEST,
                DMStructures.NETHER_DRAGON_NEST,
                DMStructures.SKELETON_DRAGON_NEST,
                DMStructures.SUNLIGHT_DRAGON_NEST,
                DMStructures.TERRA_DRAGON_NEST,
                DMStructures.WATER_DRAGON_NEST,
                DMStructures.ZOMBIE_DRAGON_NEST
        ), new RandomSpreadStructurePlacement(40, 15, RandomSpreadType.LINEAR, 60052411)));
    }
}
