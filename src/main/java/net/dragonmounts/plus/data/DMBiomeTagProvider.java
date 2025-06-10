package net.dragonmounts.plus.data;

import net.dragonmounts.plus.common.tag.DMBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class DMBiomeTagProvider extends FabricTagProvider<Biome> {
    public DMBiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Registries.BIOME, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_AETHER_DRAGON_NEST)
                .forceAddTag(BiomeTags.IS_OCEAN);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_ENCHANTED_DRAGON_NEST)
                .forceAddTag(BiomeTags.IS_END);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_FIRE_DRAGON_NEST)
                .forceAddTag(BiomeTags.IS_MOUNTAIN)
                .forceAddTag(ConventionalBiomeTags.IS_STONY_SHORES);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_FOREST_DRAGON_NEST)
                .add(Biomes.PLAINS)
                .add(Biomes.MEADOW)
                .forceAddTag(BiomeTags.IS_JUNGLE)
                .forceAddTag(BiomeTags.IS_FOREST);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_ICE_DRAGON_NEST)
                .forceAddTag(ConventionalBiomeTags.IS_ICY)
                .forceAddTag(ConventionalBiomeTags.IS_SNOWY);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_MOONLIGHT_DRAGON_NEST)
                .forceAddTag(BiomeTags.IS_OCEAN);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_NETHER_DRAGON_NEST)
                .forceAddTag(BiomeTags.IS_NETHER);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_SKELETON_DRAGON_NEST)
                .forceAddTag(BiomeTags.IS_NETHER);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_SUNLIGHT_DRAGON_NEST)
                .add(Biomes.DESERT);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_TERRA_DRAGON_NEST)
                .forceAddTag(BiomeTags.IS_BADLANDS);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_WATER_DRAGON_NEST)
                .forceAddTag(ConventionalBiomeTags.IS_SWAMP)
                .forceAddTag(BiomeTags.IS_OCEAN);
        this.getOrCreateTagBuilder(DMBiomeTags.HAS_ZOMBIE_DRAGON_NEST)
                .forceAddTag(BiomeTags.IS_NETHER);
    }
}
