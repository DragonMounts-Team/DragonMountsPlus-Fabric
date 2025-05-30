package net.dragonmounts.plus.data;

import net.dragonmounts.plus.common.init.DMBlocks;
import net.dragonmounts.plus.common.tag.DMBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class DMBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public DMBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.getOrCreateTagBuilder(BlockTags.PIGLIN_REPELLENTS).add(DMBlocks.DRAGON_CORE);
        this.getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(DMBlocks.DRAGON_CORE);
        this.getOrCreateTagBuilder(DMBlockTags.AIRFLOW_DESTRUCTIBLE)
                .forceAddTag(BlockTags.LEAVES)
                .forceAddTag(BlockTags.FLOWERS)
                .forceAddTag(BlockTags.SAPLINGS)
                .forceAddTag(BlockTags.CROPS)
                .forceAddTag(BlockTags.CAVE_VINES)
                .forceAddTag(BlockTags.FIRE)
                .forceAddTag(BlockTags.SMELTS_TO_GLASS)
                .forceAddTag(BlockTags.CONCRETE_POWDER)
                .forceAddTag(BlockTags.SNOW)
                .forceAddTag(ConventionalBlockTags.GLASS_PANES)
                .forceAddTag(ConventionalBlockTags.SANDS)
                .add(
                        // Overworld:
                        Blocks.SHORT_GRASS,
                        Blocks.FERN,
                        Blocks.DEAD_BUSH,
                        Blocks.VINE,
                        Blocks.GLOW_LICHEN,
                        Blocks.TALL_GRASS,
                        Blocks.LARGE_FERN,
                        Blocks.HANGING_ROOTS,
                        Blocks.BROWN_MUSHROOM,
                        Blocks.RED_MUSHROOM,
                        Blocks.SMALL_DRIPLEAF,
                        Blocks.BIG_DRIPLEAF,
                        Blocks.BIG_DRIPLEAF_STEM,
                        Blocks.COCOA,
                        Blocks.SWEET_BERRY_BUSH,
                        Blocks.LILY_PAD,
                        Blocks.MOSS_CARPET,
                        Blocks.PALE_MOSS_CARPET,
                        Blocks.SUGAR_CANE,
                        Blocks.CACTUS,
                        // Nether:
                        Blocks.NETHER_SPROUTS,
                        Blocks.NETHER_WART,
                        Blocks.CRIMSON_ROOTS,
                        Blocks.CRIMSON_FUNGUS,
                        Blocks.WARPED_ROOTS,
                        Blocks.WARPED_FUNGUS,
                        Blocks.TWISTING_VINES,
                        Blocks.TWISTING_VINES_PLANT,
                        Blocks.WEEPING_VINES,
                        Blocks.WEEPING_VINES_PLANT,
                        // Other:
                        Blocks.CHORUS_PLANT,
                        Blocks.COBWEB,
                        Blocks.TORCH,
                        Blocks.WALL_TORCH,
                        Blocks.SPONGE,
                        Blocks.WET_SPONGE
                );
        DMBlocks.BUILTIN_DRAGON_EGGS.forEach(this.getOrCreateTagBuilder(DMBlockTags.DRAGON_EGGS).add(Blocks.DRAGON_EGG)::add);
        DMBlocks.BUILTIN_DRAGON_SCALE_BLOCKS.forEach(this.getOrCreateTagBuilder(DMBlockTags.DRAGON_SCALE_BLOCKS)::add);
    }
}
