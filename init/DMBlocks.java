package net.dragonmounts.plus.common.init;

import com.google.common.collect.ImmutableList;
import net.dragonmounts.plus.common.block.DragonCoreBlock;
import net.dragonmounts.plus.common.block.DragonScaleBlock;
import net.dragonmounts.plus.common.block.HatchableDragonEggBlock;
import net.dragonmounts.plus.common.block.entity.DragonCoreBlockEntity;
import net.dragonmounts.plus.compat.platform.FlammableBlock;
import net.dragonmounts.plus.compat.registry.BlockHolder;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.ToIntFunction;

import static net.dragonmounts.plus.compat.registry.BlockHolder.registerBlock;

public class DMBlocks {
    private static final ToIntFunction<BlockState> DRAGON_EGG_LUMINANCE = state -> 1;
    public static final ImmutableList<BlockHolder<HatchableDragonEggBlock>> BUILTIN_DRAGON_EGGS;
    public static final ImmutableList<BlockHolder<DragonScaleBlock>> BUILTIN_DRAGON_SCALE_BLOCKS;
    public static final BlockHolder<DragonCoreBlock> DRAGON_CORE = registerBlock("dragon_core", props -> {
        BlockBehaviour.StatePredicate predicate = ($, level, pos) ->
                level.getBlockEntity(pos) instanceof DragonCoreBlockEntity core && core.isClosed();
        return new DragonCoreBlock(props.strength(2000, 600)
                .mapColor(MapColor.COLOR_BLACK)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .sound(new SoundType(
                        1.0F,
                        1.0F,
                        SoundEvents.CHEST_LOCKED,
                        SoundEvents.DEEPSLATE_STEP,
                        SoundEvents.BOOK_PUT,
                        SoundEvents.DEEPSLATE_STEP,
                        SoundEvents.DEEPSLATE_FALL
                ))
                .noLootTable()
                .forceSolidOn()
                .dynamicShape()
                .noOcclusion()
                .isSuffocating(predicate)
                .isViewBlocking(predicate)
                .pushReaction(PushReaction.BLOCK)
        );
    });
    public static final BlockHolder<FlammableBlock> DRAGON_NEST = registerBlock("dragon_nest", props ->
            new FlammableBlock(30, 80, props.strength(1.0F)
                    .mapColor(MapColor.PODZOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .sound(SoundType.WOOD)
                    .ignitedByLava())
    );
    public static final BlockHolder<HatchableDragonEggBlock> AETHER_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> DARK_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> ENCHANTED_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> ENDER_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> FIRE_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> FOREST_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> ICE_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> MOONLIGHT_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> NETHER_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> SCULK_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> SKELETON_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> STORM_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> SUNLIGHT_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> TERRA_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> WATER_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> WITHER_DRAGON_EGG;
    public static final BlockHolder<HatchableDragonEggBlock> ZOMBIE_DRAGON_EGG;
    public static final BlockHolder<DragonScaleBlock> AETHER_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> DARK_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> ENCHANTED_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> ENDER_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> FIRE_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> FOREST_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> ICE_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> MOONLIGHT_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> NETHER_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> SCULK_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> STORM_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> SUNLIGHT_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> TERRA_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> WATER_DRAGON_SCALE_BLOCK;
    public static final BlockHolder<DragonScaleBlock> ZOMBIE_DRAGON_SCALE_BLOCK;

    public static BlockBehaviour.Properties configureDragonHead(BlockBehaviour.Properties props) {
        return props.strength(1.0F).instrument(NoteBlockInstrument.DRAGON).pushReaction(PushReaction.DESTROY);
    }

    static HatchableDragonEggBlock makeDragonEgg(DragonType type, BlockBehaviour.Properties props) {
        var block = new HatchableDragonEggBlock(type, props.strength(0.0F, 9.0F)
                .mapColor(type.scaleColor)
                .lightLevel(DRAGON_EGG_LUMINANCE)
                .noOcclusion()
        );
        type.bindInstance(HatchableDragonEggBlock.class, block);
        return block;
    }

    static DragonScaleBlock makeDragonScaleBlock(DragonType type, BlockBehaviour.Properties props) {
        var block = new DragonScaleBlock(type, props.strength(4.0F, 20.0F)
                .mapColor(type.scaleColor)
                .sound(SoundType.METAL)
                .lightLevel(DRAGON_EGG_LUMINANCE)
                .noOcclusion()
        );
        type.bindInstance(DragonScaleBlock.class, block);
        return block;
    }

    static {
        var eggs = ImmutableList.<BlockHolder<HatchableDragonEggBlock>>builderWithExpectedSize(17);
        eggs.add(AETHER_DRAGON_EGG = registerBlock("aether_dragon_egg", props -> makeDragonEgg(DragonTypes.AETHER, props)));
        eggs.add(DARK_DRAGON_EGG = registerBlock("dark_dragon_egg", props -> makeDragonEgg(DragonTypes.DARK, props)));
        eggs.add(ENCHANTED_DRAGON_EGG = registerBlock("enchanted_dragon_egg", props -> makeDragonEgg(DragonTypes.ENCHANTED, props)));
        eggs.add(ENDER_DRAGON_EGG = registerBlock("ender_dragon_egg", props -> makeDragonEgg(DragonTypes.ENDER, props)));
        eggs.add(FIRE_DRAGON_EGG = registerBlock("fire_dragon_egg", props -> makeDragonEgg(DragonTypes.FIRE, props)));
        eggs.add(FOREST_DRAGON_EGG = registerBlock("forest_dragon_egg", props -> makeDragonEgg(DragonTypes.FOREST, props)));
        eggs.add(ICE_DRAGON_EGG = registerBlock("ice_dragon_egg", props -> makeDragonEgg(DragonTypes.ICE, props)));
        eggs.add(MOONLIGHT_DRAGON_EGG = registerBlock("moonlight_dragon_egg", props -> makeDragonEgg(DragonTypes.MOONLIGHT, props)));
        eggs.add(NETHER_DRAGON_EGG = registerBlock("nether_dragon_egg", props -> makeDragonEgg(DragonTypes.NETHER, props)));
        eggs.add(SCULK_DRAGON_EGG = registerBlock("sculk_dragon_egg", props -> makeDragonEgg(DragonTypes.SCULK, props)));
        eggs.add(SKELETON_DRAGON_EGG = registerBlock("skeleton_dragon_egg", props -> makeDragonEgg(DragonTypes.SKELETON, props)));
        eggs.add(STORM_DRAGON_EGG = registerBlock("storm_dragon_egg", props -> makeDragonEgg(DragonTypes.STORM, props)));
        eggs.add(SUNLIGHT_DRAGON_EGG = registerBlock("sunlight_dragon_egg", props -> makeDragonEgg(DragonTypes.SUNLIGHT, props)));
        eggs.add(TERRA_DRAGON_EGG = registerBlock("terra_dragon_egg", props -> makeDragonEgg(DragonTypes.TERRA, props)));
        eggs.add(WATER_DRAGON_EGG = registerBlock("water_dragon_egg", props -> makeDragonEgg(DragonTypes.WATER, props)));
        eggs.add(WITHER_DRAGON_EGG = registerBlock("wither_dragon_egg", props -> makeDragonEgg(DragonTypes.WITHER, props)));
        eggs.add(ZOMBIE_DRAGON_EGG = registerBlock("zombie_dragon_egg", props -> makeDragonEgg(DragonTypes.ZOMBIE, props)));
        BUILTIN_DRAGON_EGGS = eggs.build();
    }

    static {
        var blocks = ImmutableList.<BlockHolder<DragonScaleBlock>>builderWithExpectedSize(15);
        blocks.add(AETHER_DRAGON_SCALE_BLOCK = registerBlock("aether_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.AETHER, props)));
        blocks.add(DARK_DRAGON_SCALE_BLOCK = registerBlock("dark_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.DARK, props)));
        blocks.add(ENCHANTED_DRAGON_SCALE_BLOCK = registerBlock("enchanted_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.ENCHANTED, props)));
        blocks.add(ENDER_DRAGON_SCALE_BLOCK = registerBlock("ender_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.ENDER, props)));
        blocks.add(FIRE_DRAGON_SCALE_BLOCK = registerBlock("fire_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.FIRE, props)));
        blocks.add(FOREST_DRAGON_SCALE_BLOCK = registerBlock("forest_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.FOREST, props)));
        blocks.add(ICE_DRAGON_SCALE_BLOCK = registerBlock("ice_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.ICE, props)));
        blocks.add(MOONLIGHT_DRAGON_SCALE_BLOCK = registerBlock("moonlight_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.MOONLIGHT, props)));
        blocks.add(NETHER_DRAGON_SCALE_BLOCK = registerBlock("nether_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.NETHER, props)));
        blocks.add(SCULK_DRAGON_SCALE_BLOCK = registerBlock("sculk_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.SCULK, props)));
        blocks.add(STORM_DRAGON_SCALE_BLOCK = registerBlock("storm_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.STORM, props)));
        blocks.add(SUNLIGHT_DRAGON_SCALE_BLOCK = registerBlock("sunlight_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.SUNLIGHT, props)));
        blocks.add(TERRA_DRAGON_SCALE_BLOCK = registerBlock("terra_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.TERRA, props)));
        blocks.add(WATER_DRAGON_SCALE_BLOCK = registerBlock("water_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.WATER, props)));
        blocks.add(ZOMBIE_DRAGON_SCALE_BLOCK = registerBlock("zombie_dragon_scale_block", props -> makeDragonScaleBlock(DragonTypes.ZOMBIE, props)));
        BUILTIN_DRAGON_SCALE_BLOCKS = blocks.build();
    }

    public static void init() {}
}
