package net.dragonmounts.plus.common.init;

import com.google.common.collect.ImmutableList;
import net.dragonmounts.plus.common.block.DragonCoreBlock;
import net.dragonmounts.plus.common.block.DragonScaleBlock;
import net.dragonmounts.plus.common.block.HatchableDragonEggBlock;
import net.dragonmounts.plus.common.block.entity.DragonCoreBlockEntity;
import net.dragonmounts.plus.compat.platform.FlammableBlock;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.ToIntFunction;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.init.DMItemGroups.BLOCK_TAB;
import static net.dragonmounts.plus.common.init.DMItemGroups.DRAGON_EGGS;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerBlock;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerItem;

public class DMBlocks {
    private static final ToIntFunction<BlockState> DRAGON_EGG_LUMINANCE = state -> 1;
    public static final ImmutableList<HatchableDragonEggBlock> BUILTIN_DRAGON_EGGS;
    public static final ImmutableList<DragonScaleBlock> BUILTIN_DRAGON_SCALE_BLOCKS;
    public static final FlammableBlock DRAGON_NEST;
    public static final DragonCoreBlock DRAGON_CORE;
    public static final HatchableDragonEggBlock AETHER_DRAGON_EGG;
    public static final HatchableDragonEggBlock DARK_DRAGON_EGG;
    public static final HatchableDragonEggBlock ENCHANTED_DRAGON_EGG;
    public static final HatchableDragonEggBlock ENDER_DRAGON_EGG;
    public static final HatchableDragonEggBlock FIRE_DRAGON_EGG;
    public static final HatchableDragonEggBlock FOREST_DRAGON_EGG;
    public static final HatchableDragonEggBlock ICE_DRAGON_EGG;
    public static final HatchableDragonEggBlock MOONLIGHT_DRAGON_EGG;
    public static final HatchableDragonEggBlock NETHER_DRAGON_EGG;
    public static final HatchableDragonEggBlock SCULK_DRAGON_EGG;
    public static final HatchableDragonEggBlock SKELETON_DRAGON_EGG;
    public static final HatchableDragonEggBlock STORM_DRAGON_EGG;
    public static final HatchableDragonEggBlock SUNLIGHT_DRAGON_EGG;
    public static final HatchableDragonEggBlock TERRA_DRAGON_EGG;
    public static final HatchableDragonEggBlock WATER_DRAGON_EGG;
    public static final HatchableDragonEggBlock WITHER_DRAGON_EGG;
    public static final HatchableDragonEggBlock ZOMBIE_DRAGON_EGG;
    public static final DragonScaleBlock AETHER_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock DARK_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock ENCHANTED_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock ENDER_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock FIRE_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock FOREST_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock ICE_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock MOONLIGHT_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock NETHER_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock SCULK_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock STORM_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock SUNLIGHT_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock TERRA_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock WATER_DRAGON_SCALE_BLOCK;
    public static final DragonScaleBlock ZOMBIE_DRAGON_SCALE_BLOCK;

    static HatchableDragonEggBlock makeDragonEgg(
            String name,
            DragonType type,
            Item.Properties props
    ) {
        var identifier = makeId(name);
        var itemKey = ResourceKey.create(Registries.ITEM, identifier);
        var blockKey = ResourceKey.create(Registries.BLOCK, identifier);
        var block = new HatchableDragonEggBlock(type, BlockBehaviour.Properties.of()
                .setId(blockKey)
                .mapColor(type.scaleColor)
                .strength(0.0F, 9.0F)
                .lightLevel(DRAGON_EGG_LUMINANCE)
                .noOcclusion()
        );
        type.bindInstance(HatchableDragonEggBlock.class, block);
        registerItem(itemKey, new BlockItem(block, props.setId(itemKey).component(DMDataComponents.DRAGON_TYPE, type).overrideDescription(HatchableDragonEggBlock.TRANSLATION_KEY)));
        return registerBlock(blockKey, block);
    }

    static DragonScaleBlock makeDragonScaleBlock(
            String name,
            DragonType type,
            Item.Properties props
    ) {
        var identifier = makeId(name);
        var itemKey = ResourceKey.create(Registries.ITEM, identifier);
        var blockKey = ResourceKey.create(Registries.BLOCK, identifier);
        var block = new DragonScaleBlock(type, BlockBehaviour.Properties.of()
                .setId(blockKey)
                .mapColor(type.scaleColor)
                .sound(SoundType.METAL)
                .strength(4.0F, 20.0F)
                .lightLevel(DRAGON_EGG_LUMINANCE)
                .noOcclusion()
        );
        type.bindInstance(DragonScaleBlock.class, block);
        BLOCK_TAB.register(itemKey, new BlockItem(block, props.setId(itemKey).component(DMDataComponents.DRAGON_TYPE, type).overrideDescription(DragonScaleBlock.TRANSLATION_KEY)));
        return registerBlock(blockKey, block);
    }

    static {
        var identifier = makeId("dragon_nest");
        var itemKey = ResourceKey.create(Registries.ITEM, identifier);
        var blockKey = ResourceKey.create(Registries.BLOCK, identifier);
        var block = new FlammableBlock(30, 80, BlockBehaviour.Properties.of()
                .setId(blockKey)
                .mapColor(MapColor.PODZOL)
                .instrument(NoteBlockInstrument.BASS)
                .strength(1.0F)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        );
        DRAGON_NEST = registerBlock(blockKey, block);
        BLOCK_TAB.register(itemKey, new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix()));
    }

    static {
        BlockBehaviour.StatePredicate predicate = ($, level, pos) -> level.getBlockEntity(pos) instanceof DragonCoreBlockEntity core && core.isClosed();
        var identifier = makeId("dragon_core");
        var itemKey = ResourceKey.create(Registries.ITEM, identifier);
        var blockKey = ResourceKey.create(Registries.BLOCK, identifier);
        var block = new DragonCoreBlock(BlockBehaviour.Properties.of()
                .setId(blockKey)
                .mapColor(MapColor.COLOR_BLACK)
                .strength(2000, 600)
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
        registerItem(itemKey, new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix().rarity(Rarity.RARE)));
        DRAGON_CORE = registerBlock(blockKey, block);
    }

    static {
        var eggs = ImmutableList.<HatchableDragonEggBlock>builderWithExpectedSize(17);
        eggs.add(AETHER_DRAGON_EGG = makeDragonEgg("aether_dragon_egg", DragonTypes.AETHER, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(DARK_DRAGON_EGG = makeDragonEgg("dark_dragon_egg", DragonTypes.DARK, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(ENCHANTED_DRAGON_EGG = makeDragonEgg("enchanted_dragon_egg", DragonTypes.ENCHANTED, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(ENDER_DRAGON_EGG = makeDragonEgg("ender_dragon_egg", DragonTypes.ENDER, new Item.Properties().rarity(Rarity.EPIC)));
        eggs.add(FIRE_DRAGON_EGG = makeDragonEgg("fire_dragon_egg", DragonTypes.FIRE, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(FOREST_DRAGON_EGG = makeDragonEgg("forest_dragon_egg", DragonTypes.FOREST, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(ICE_DRAGON_EGG = makeDragonEgg("ice_dragon_egg", DragonTypes.ICE, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(MOONLIGHT_DRAGON_EGG = makeDragonEgg("moonlight_dragon_egg", DragonTypes.MOONLIGHT, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(NETHER_DRAGON_EGG = makeDragonEgg("nether_dragon_egg", DragonTypes.NETHER, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(SCULK_DRAGON_EGG = makeDragonEgg("sculk_dragon_egg", DragonTypes.SCULK, new Item.Properties().rarity(Rarity.RARE)));
        eggs.add(SKELETON_DRAGON_EGG = makeDragonEgg("skeleton_dragon_egg", DragonTypes.SKELETON, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(STORM_DRAGON_EGG = makeDragonEgg("storm_dragon_egg", DragonTypes.STORM, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(SUNLIGHT_DRAGON_EGG = makeDragonEgg("sunlight_dragon_egg", DragonTypes.SUNLIGHT, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(TERRA_DRAGON_EGG = makeDragonEgg("terra_dragon_egg", DragonTypes.TERRA, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(WATER_DRAGON_EGG = makeDragonEgg("water_dragon_egg", DragonTypes.WATER, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(WITHER_DRAGON_EGG = makeDragonEgg("wither_dragon_egg", DragonTypes.WITHER, new Item.Properties().rarity(Rarity.UNCOMMON)));
        eggs.add(ZOMBIE_DRAGON_EGG = makeDragonEgg("zombie_dragon_egg", DragonTypes.ZOMBIE, new Item.Properties().rarity(Rarity.UNCOMMON)));
        DRAGON_EGGS.addAll(BUILTIN_DRAGON_EGGS = eggs.build());
    }

    static {
        var blocks = ImmutableList.<DragonScaleBlock>builderWithExpectedSize(15);
        blocks.add(AETHER_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("aether_dragon_scale_block", DragonTypes.AETHER, new Item.Properties()));
        blocks.add(DARK_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("dark_dragon_scale_block", DragonTypes.DARK, new Item.Properties()));
        blocks.add(ENCHANTED_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("enchanted_dragon_scale_block", DragonTypes.ENCHANTED, new Item.Properties()));
        blocks.add(ENDER_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("ender_dragon_scale_block", DragonTypes.ENDER, new Item.Properties()));
        blocks.add(FIRE_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("fire_dragon_scale_block", DragonTypes.FIRE, new Item.Properties()));
        blocks.add(FOREST_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("forest_dragon_scale_block", DragonTypes.FOREST, new Item.Properties()));
        blocks.add(ICE_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("ice_dragon_scale_block", DragonTypes.ICE, new Item.Properties()));
        blocks.add(MOONLIGHT_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("moonlight_dragon_scale_block", DragonTypes.MOONLIGHT, new Item.Properties()));
        blocks.add(NETHER_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("nether_dragon_scale_block", DragonTypes.NETHER, new Item.Properties()));
        blocks.add(SCULK_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("sculk_dragon_scale_block", DragonTypes.SCULK, new Item.Properties()));
        blocks.add(STORM_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("storm_dragon_scale_block", DragonTypes.STORM, new Item.Properties()));
        blocks.add(SUNLIGHT_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("sunlight_dragon_scale_block", DragonTypes.SUNLIGHT, new Item.Properties()));
        blocks.add(TERRA_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("terra_dragon_scale_block", DragonTypes.TERRA, new Item.Properties()));
        blocks.add(WATER_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("water_dragon_scale_block", DragonTypes.WATER, new Item.Properties()));
        blocks.add(ZOMBIE_DRAGON_SCALE_BLOCK = makeDragonScaleBlock("zombie_dragon_scale_block", DragonTypes.ZOMBIE, new Item.Properties()));
        BUILTIN_DRAGON_SCALE_BLOCKS = blocks.build();
    }

    public static void init() {}
}
