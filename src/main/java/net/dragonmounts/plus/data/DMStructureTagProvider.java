package net.dragonmounts.plus.data;

import net.dragonmounts.plus.common.init.DMStructures;
import net.dragonmounts.plus.common.tag.DMStructureTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.concurrent.CompletableFuture;

public class DMStructureTagProvider extends FabricTagProvider<Structure> {
    public DMStructureTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Registries.STRUCTURE, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.getOrCreateTagBuilder(DMStructureTags.DRAGON_NESTS)
                .add(DMStructures.AETHER_DRAGON_NEST)
                .add(DMStructures.ENCHANTED_DRAGON_NEST)
                .add(DMStructures.FIRE_DRAGON_NEST)
                .add(DMStructures.FOREST_DRAGON_NEST)
                .add(DMStructures.ICE_DRAGON_NEST)
                .add(DMStructures.MOONLIGHT_DRAGON_NEST)
                .add(DMStructures.NETHER_DRAGON_NEST)
                .add(DMStructures.SKELETON_DRAGON_NEST)
                .add(DMStructures.SUNLIGHT_DRAGON_NEST)
                .add(DMStructures.TERRA_DRAGON_NEST)
                .add(DMStructures.WATER_DRAGON_NEST)
                .add(DMStructures.ZOMBIE_DRAGON_NEST);
    }
}
