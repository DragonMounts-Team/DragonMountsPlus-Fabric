package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.block.DragonCoreBlock;
import net.dragonmounts.plus.common.block.DragonScaleBlock;
import net.dragonmounts.plus.common.block.HatchableDragonEggBlock;
import net.dragonmounts.plus.common.item.*;
import net.dragonmounts.plus.compat.platform.FlammableBlock;
import net.dragonmounts.plus.compat.registry.DeferredBlockItem;
import net.dragonmounts.plus.compat.registry.DeferredItem;
import net.dragonmounts.plus.compat.registry.DragonScaleArmorSuit;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.DispenserBlock;

import static net.dragonmounts.plus.common.init.DMItemGroups.*;
import static net.dragonmounts.plus.compat.registry.DeferredItem.registerItem;
import static net.dragonmounts.plus.compat.registry.DragonScaleArmorSuit.makeSuit;

public class DMItems {
    public static final DeferredBlockItem<DragonCoreBlock, ?> DRAGON_CORE = DeferredBlockItem.registerItem(
            DMBlocks.DRAGON_CORE,
            (block, props) -> new BlockItem(block, props.rarity(Rarity.RARE))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> AETHER_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.AETHER_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> DARK_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.DARK_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> ENCHANTED_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.ENCHANTED_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> ENDER_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.ENDER_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.EPIC))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> FIRE_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.FIRE_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> FOREST_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.FOREST_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> ICE_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.ICE_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> MOONLIGHT_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.MOONLIGHT_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> NETHER_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.NETHER_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> SCULK_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.SCULK_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.RARE).fireResistant())
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> SKELETON_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.SKELETON_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> STORM_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.STORM_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> SUNLIGHT_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.SUNLIGHT_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> TERRA_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.TERRA_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> WATER_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.WATER_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> WITHER_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.WITHER_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<HatchableDragonEggBlock, ?> ZOMBIE_DRAGON_EGG = DRAGON_EGGS.register(
            DMBlocks.ZOMBIE_DRAGON_EGG,
            (block, props) -> makeDragonEggBlock(block, props.rarity(Rarity.UNCOMMON))
    );
    public static final DeferredBlockItem<FlammableBlock, ?> DRAGON_NEST = BLOCK_TAB.register(DMBlocks.DRAGON_NEST, BlockItem::new);
    public static final DeferredBlockItem<DragonScaleBlock, ?> AETHER_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.AETHER_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> DARK_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.DARK_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> ENCHANTED_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.ENCHANTED_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> ENDER_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.ENDER_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> FIRE_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.FIRE_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> FOREST_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.FOREST_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> ICE_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.ICE_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> MOONLIGHT_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.MOONLIGHT_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> NETHER_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.NETHER_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> SCULK_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.SCULK_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> STORM_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.STORM_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> SUNLIGHT_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.SUNLIGHT_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> TERRA_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.TERRA_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> WATER_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.WATER_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredBlockItem<DragonScaleBlock, ?> ZOMBIE_DRAGON_SCALE_BLOCK = BLOCK_TAB.register(DMBlocks.ZOMBIE_DRAGON_SCALE_BLOCK, DMItems::makeDragonScaleBlock);
    public static final DeferredItem<DragonScalesItem> AETHER_DRAGON_SCALES = ITEM_TAB.register("aether_dragon_scales", props -> makeDragonScales(DragonTypes.AETHER, props));
    public static final DeferredItem<DragonScalesItem> ENCHANTED_DRAGON_SCALES = ITEM_TAB.register("enchanted_dragon_scales", props -> makeDragonScales(DragonTypes.ENCHANTED, props));
    public static final DeferredItem<DragonScalesItem> ENDER_DRAGON_SCALES = ITEM_TAB.register("ender_dragon_scales", props -> makeDragonScales(DragonTypes.ENDER, props));
    public static final DeferredItem<DragonScalesItem> FIRE_DRAGON_SCALES = ITEM_TAB.register("fire_dragon_scales", props -> makeDragonScales(DragonTypes.FIRE, props));
    public static final DeferredItem<DragonScalesItem> FOREST_DRAGON_SCALES = ITEM_TAB.register("forest_dragon_scales", props -> makeDragonScales(DragonTypes.FOREST, props));
    public static final DeferredItem<DragonScalesItem> ICE_DRAGON_SCALES = ITEM_TAB.register("ice_dragon_scales", props -> makeDragonScales(DragonTypes.ICE, props));
    public static final DeferredItem<DragonScalesItem> MOONLIGHT_DRAGON_SCALES = ITEM_TAB.register("moonlight_dragon_scales", props -> makeDragonScales(DragonTypes.MOONLIGHT, props));
    public static final DeferredItem<DragonScalesItem> NETHER_DRAGON_SCALES = ITEM_TAB.register("nether_dragon_scales", props -> makeDragonScales(DragonTypes.NETHER, props));
    public static final DeferredItem<DragonScalesItem> SCULK_DRAGON_SCALES = ITEM_TAB.register("sculk_dragon_scales", props -> makeDragonScales(DragonTypes.SCULK, props.fireResistant()));
    public static final DeferredItem<DragonScalesItem> STORM_DRAGON_SCALES = ITEM_TAB.register("storm_dragon_scales", props -> makeDragonScales(DragonTypes.STORM, props));
    public static final DeferredItem<DragonScalesItem> SUNLIGHT_DRAGON_SCALES = ITEM_TAB.register("sunlight_dragon_scales", props -> makeDragonScales(DragonTypes.SUNLIGHT, props));
    public static final DeferredItem<DragonScalesItem> TERRA_DRAGON_SCALES = ITEM_TAB.register("terra_dragon_scales", props -> makeDragonScales(DragonTypes.TERRA, props));
    public static final DeferredItem<DragonScalesItem> WATER_DRAGON_SCALES = ITEM_TAB.register("water_dragon_scales", props -> makeDragonScales(DragonTypes.WATER, props));
    public static final DeferredItem<DragonScalesItem> ZOMBIE_DRAGON_SCALES = ITEM_TAB.register("zombie_dragon_scales", props -> makeDragonScales(DragonTypes.ZOMBIE, props));
    public static final DeferredItem<DragonScalesItem> DARK_DRAGON_SCALES = ITEM_TAB.register("dark_dragon_scales", props -> makeDragonScales(DragonTypes.DARK, props));
    //Dragon Armor
    public static final DeferredItem<DragonArmorItem> IRON_DRAGON_ARMOR = TOOL_TAB.register("iron_dragon_armor", props -> makeDragonArmor(DragonArmorMaterials.IRON, props));
    public static final DeferredItem<DragonArmorItem> GOLDEN_DRAGON_ARMOR = TOOL_TAB.register("golden_dragon_armor", props -> makeDragonArmor(DragonArmorMaterials.GOLD, props));
    public static final DeferredItem<DragonArmorItem> DIAMOND_DRAGON_ARMOR = TOOL_TAB.register("diamond_dragon_armor", props -> makeDragonArmor(DragonArmorMaterials.DIAMOND, props));
    public static final DeferredItem<DragonArmorItem> EMERALD_DRAGON_ARMOR = TOOL_TAB.register("emerald_dragon_armor", props -> makeDragonArmor(DragonArmorMaterials.DIAMOND, props));
    public static final DeferredItem<DragonArmorItem> NETHERITE_DRAGON_ARMOR = TOOL_TAB.register("netherite_dragon_armor", props -> makeDragonArmor(DragonArmorMaterials.NETHERITE, props.fireResistant()));
    //Dragon Scale Swords
    public static final DeferredItem<DragonScaleSwordItem> AETHER_DRAGON_SCALE_SWORD = TOOL_TAB.register("aether_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.AETHER, props));
    public static final DeferredItem<DragonScaleSwordItem> WATER_DRAGON_SCALE_SWORD = TOOL_TAB.register("water_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.WATER, props));
    public static final DeferredItem<DragonScaleSwordItem> ICE_DRAGON_SCALE_SWORD = TOOL_TAB.register("ice_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.ICE, props));
    public static final DeferredItem<DragonScaleSwordItem> FIRE_DRAGON_SCALE_SWORD = TOOL_TAB.register("fire_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.FIRE, props));
    public static final DeferredItem<DragonScaleSwordItem> FOREST_DRAGON_SCALE_SWORD = TOOL_TAB.register("forest_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.FOREST, props));
    public static final DeferredItem<DragonScaleSwordItem> NETHER_DRAGON_SCALE_SWORD = TOOL_TAB.register("nether_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.NETHER, props));
    public static final DeferredItem<DragonScaleSwordItem> ENDER_DRAGON_SCALE_SWORD = TOOL_TAB.register("ender_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.ENDER, props));
    public static final DeferredItem<DragonScaleSwordItem> ENCHANTED_DRAGON_SCALE_SWORD = TOOL_TAB.register("enchanted_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.ENCHANTED, props));
    public static final DeferredItem<DragonScaleSwordItem> SUNLIGHT_DRAGON_SCALE_SWORD = TOOL_TAB.register("sunlight_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.SUNLIGHT, props));
    public static final DeferredItem<DragonScaleSwordItem> MOONLIGHT_DRAGON_SCALE_SWORD = TOOL_TAB.register("moonlight_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.MOONLIGHT, props));
    public static final DeferredItem<DragonScaleSwordItem> STORM_DRAGON_SCALE_SWORD = TOOL_TAB.register("storm_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.STORM, props));
    public static final DeferredItem<DragonScaleSwordItem> TERRA_DRAGON_SCALE_SWORD = TOOL_TAB.register("terra_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.TERRA, props));
    public static final DeferredItem<DragonScaleSwordItem> ZOMBIE_DRAGON_SCALE_SWORD = TOOL_TAB.register("zombie_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.ZOMBIE, props));
    public static final DeferredItem<DragonScaleSwordItem> SCULK_DRAGON_SCALE_SWORD = TOOL_TAB.register("sculk_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.SCULK, props.fireResistant()));
    public static final DeferredItem<DragonScaleSwordItem> DARK_DRAGON_SCALE_SWORD = TOOL_TAB.register("dark_dragon_scale_sword", props -> makeDragonScaleSword(DragonTypes.DARK, props));
    //Dragon Scale Axes
    public static final DeferredItem<DragonScaleAxeItem> AETHER_DRAGON_SCALE_AXE = TOOL_TAB.register("aether_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.AETHER, props));
    public static final DeferredItem<DragonScaleAxeItem> WATER_DRAGON_SCALE_AXE = TOOL_TAB.register("water_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.WATER, props));
    public static final DeferredItem<DragonScaleAxeItem> ICE_DRAGON_SCALE_AXE = TOOL_TAB.register("ice_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.ICE, props));
    public static final DeferredItem<DragonScaleAxeItem> FIRE_DRAGON_SCALE_AXE = TOOL_TAB.register("fire_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.FIRE, props));
    public static final DeferredItem<DragonScaleAxeItem> FOREST_DRAGON_SCALE_AXE = TOOL_TAB.register("forest_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.FOREST, props));
    public static final DeferredItem<DragonScaleAxeItem> NETHER_DRAGON_SCALE_AXE = TOOL_TAB.register("nether_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.NETHER, 6.0F, -2.9F, props));
    public static final DeferredItem<DragonScaleAxeItem> ENDER_DRAGON_SCALE_AXE = TOOL_TAB.register("ender_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.ENDER, 6.0F, -2.9F, props));
    public static final DeferredItem<DragonScaleAxeItem> ENCHANTED_DRAGON_SCALE_AXE = TOOL_TAB.register("enchanted_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.ENCHANTED, props));
    public static final DeferredItem<DragonScaleAxeItem> SUNLIGHT_DRAGON_SCALE_AXE = TOOL_TAB.register("sunlight_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.SUNLIGHT, props));
    public static final DeferredItem<DragonScaleAxeItem> MOONLIGHT_DRAGON_SCALE_AXE = TOOL_TAB.register("moonlight_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.MOONLIGHT, props));
    public static final DeferredItem<DragonScaleAxeItem> STORM_DRAGON_SCALE_AXE = TOOL_TAB.register("storm_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.STORM, props));
    public static final DeferredItem<DragonScaleAxeItem> TERRA_DRAGON_SCALE_AXE = TOOL_TAB.register("terra_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.TERRA, props));
    public static final DeferredItem<DragonScaleAxeItem> ZOMBIE_DRAGON_SCALE_AXE = TOOL_TAB.register("zombie_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.ZOMBIE, props));
    public static final DeferredItem<DragonScaleAxeItem> SCULK_DRAGON_SCALE_AXE = TOOL_TAB.register("sculk_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.SCULK, props.fireResistant()));
    public static final DeferredItem<DragonScaleAxeItem> DARK_DRAGON_SCALE_AXE = TOOL_TAB.register("dark_dragon_scale_axe", props -> makeDragonScaleAxe(DragonTypes.DARK, props));
    //Dragon Scale Bows
    public static final DeferredItem<DragonScaleBowItem> AETHER_DRAGON_SCALE_BOW = TOOL_TAB.register("aether_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.AETHER, props));
    public static final DeferredItem<DragonScaleBowItem> WATER_DRAGON_SCALE_BOW = TOOL_TAB.register("water_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.WATER, props));
    public static final DeferredItem<DragonScaleBowItem> ICE_DRAGON_SCALE_BOW = TOOL_TAB.register("ice_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.ICE, props));
    public static final DeferredItem<DragonScaleBowItem> FIRE_DRAGON_SCALE_BOW = TOOL_TAB.register("fire_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.FIRE, props));
    public static final DeferredItem<DragonScaleBowItem> FOREST_DRAGON_SCALE_BOW = TOOL_TAB.register("forest_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.FOREST, props));
    public static final DeferredItem<DragonScaleBowItem> NETHER_DRAGON_SCALE_BOW = TOOL_TAB.register("nether_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.NETHER, props));
    public static final DeferredItem<DragonScaleBowItem> ENDER_DRAGON_SCALE_BOW = TOOL_TAB.register("ender_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.ENDER, props));
    public static final DeferredItem<DragonScaleBowItem> ENCHANTED_DRAGON_SCALE_BOW = TOOL_TAB.register("enchanted_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.ENCHANTED, props));
    public static final DeferredItem<DragonScaleBowItem> SUNLIGHT_DRAGON_SCALE_BOW = TOOL_TAB.register("sunlight_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.SUNLIGHT, props));
    public static final DeferredItem<DragonScaleBowItem> MOONLIGHT_DRAGON_SCALE_BOW = TOOL_TAB.register("moonlight_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.MOONLIGHT, props));
    public static final DeferredItem<DragonScaleBowItem> STORM_DRAGON_SCALE_BOW = TOOL_TAB.register("storm_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.STORM, props));
    public static final DeferredItem<DragonScaleBowItem> TERRA_DRAGON_SCALE_BOW = TOOL_TAB.register("terra_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.TERRA, props));
    public static final DeferredItem<DragonScaleBowItem> ZOMBIE_DRAGON_SCALE_BOW = TOOL_TAB.register("zombie_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.ZOMBIE, props));
    public static final DeferredItem<DragonScaleBowItem> SCULK_DRAGON_SCALE_BOW = TOOL_TAB.register("sculk_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.SCULK, props.fireResistant()));
    public static final DeferredItem<DragonScaleBowItem> DARK_DRAGON_SCALE_BOW = TOOL_TAB.register("dark_dragon_scale_bow", props -> makeDragonScaleBow(DragonTypes.DARK, props));
    //Dragon Scale Shields
    public static final DeferredItem<DragonScaleShieldItem> AETHER_DRAGON_SCALE_SHIELD = TOOL_TAB.register("aether_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.AETHER, props));
    public static final DeferredItem<DragonScaleShieldItem> WATER_DRAGON_SCALE_SHIELD = TOOL_TAB.register("water_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.WATER, props));
    public static final DeferredItem<DragonScaleShieldItem> ICE_DRAGON_SCALE_SHIELD = TOOL_TAB.register("ice_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.ICE, props));
    public static final DeferredItem<DragonScaleShieldItem> FIRE_DRAGON_SCALE_SHIELD = TOOL_TAB.register("fire_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.FIRE, props));
    public static final DeferredItem<DragonScaleShieldItem> FOREST_DRAGON_SCALE_SHIELD = TOOL_TAB.register("forest_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.FOREST, props));
    public static final DeferredItem<DragonScaleShieldItem> NETHER_DRAGON_SCALE_SHIELD = TOOL_TAB.register("nether_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.NETHER, props));
    public static final DeferredItem<DragonScaleShieldItem> ENDER_DRAGON_SCALE_SHIELD = TOOL_TAB.register("ender_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.ENDER, props));
    public static final DeferredItem<DragonScaleShieldItem> ENCHANTED_DRAGON_SCALE_SHIELD = TOOL_TAB.register("enchanted_dragon_scale_shield", props ->
            makeDragonScaleShield(DragonTypes.ENCHANTED, props.enchantable(DragonTypes.ENCHANTED.material.enchantmentValue()))
    );
    public static final DeferredItem<DragonScaleShieldItem> SUNLIGHT_DRAGON_SCALE_SHIELD = TOOL_TAB.register("sunlight_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.SUNLIGHT, props));
    public static final DeferredItem<DragonScaleShieldItem> MOONLIGHT_DRAGON_SCALE_SHIELD = TOOL_TAB.register("moonlight_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.MOONLIGHT, props));
    public static final DeferredItem<DragonScaleShieldItem> STORM_DRAGON_SCALE_SHIELD = TOOL_TAB.register("storm_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.STORM, props));
    public static final DeferredItem<DragonScaleShieldItem> TERRA_DRAGON_SCALE_SHIELD = TOOL_TAB.register("terra_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.TERRA, props));
    public static final DeferredItem<DragonScaleShieldItem> ZOMBIE_DRAGON_SCALE_SHIELD = TOOL_TAB.register("zombie_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.ZOMBIE, props));
    public static final DeferredItem<DragonScaleShieldItem> SCULK_DRAGON_SCALE_SHIELD = TOOL_TAB.register("sculk_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.SCULK, props.fireResistant()));
    public static final DeferredItem<DragonScaleShieldItem> DARK_DRAGON_SCALE_SHIELD = TOOL_TAB.register("dark_dragon_scale_shield", props -> makeDragonScaleShield(DragonTypes.DARK, props));
    //Dragon Scale Tools - Aether
    public static final DeferredItem<DragonScaleShovelItem> AETHER_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("aether_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.AETHER, props));
    public static final DeferredItem<DragonScalePickaxeItem> AETHER_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("aether_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.AETHER, props));
    public static final DeferredItem<DragonScaleHoeItem> AETHER_DRAGON_SCALE_HOE = TOOL_TAB.register("aether_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.AETHER, props));
    //Dragon Scale Tools - Water
    public static final DeferredItem<DragonScaleShovelItem> WATER_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("water_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.WATER, props));
    public static final DeferredItem<DragonScalePickaxeItem> WATER_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("water_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.WATER, props));
    public static final DeferredItem<DragonScaleHoeItem> WATER_DRAGON_SCALE_HOE = TOOL_TAB.register("water_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.WATER, props));
    //Dragon Scale Tools - Ice
    public static final DeferredItem<DragonScaleShovelItem> ICE_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("ice_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.ICE, props));
    public static final DeferredItem<DragonScalePickaxeItem> ICE_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("ice_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.ICE, props));
    public static final DeferredItem<DragonScaleHoeItem> ICE_DRAGON_SCALE_HOE = TOOL_TAB.register("ice_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.ICE, props));
    //Dragon Scale Tools - Fire
    public static final DeferredItem<DragonScaleShovelItem> FIRE_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("fire_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.FIRE, props));
    public static final DeferredItem<DragonScalePickaxeItem> FIRE_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("fire_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.FIRE, props));
    public static final DeferredItem<DragonScaleHoeItem> FIRE_DRAGON_SCALE_HOE = TOOL_TAB.register("fire_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.FIRE, props));
    //Dragon Scale Tools - Forest
    public static final DeferredItem<DragonScaleShovelItem> FOREST_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("forest_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.FOREST, props));
    public static final DeferredItem<DragonScalePickaxeItem> FOREST_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("forest_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.FOREST, props));
    public static final DeferredItem<DragonScaleHoeItem> FOREST_DRAGON_SCALE_HOE = TOOL_TAB.register("forest_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.FOREST, props));
    //Dragon Scale Tools - Nether
    public static final DeferredItem<DragonScaleShovelItem> NETHER_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("nether_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.NETHER, props));
    public static final DeferredItem<DragonScalePickaxeItem> NETHER_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("nether_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.NETHER, props));
    public static final DeferredItem<DragonScaleHoeItem> NETHER_DRAGON_SCALE_HOE = TOOL_TAB.register("nether_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.NETHER, props));
    //Dragon Scale Tools - Ender
    public static final DeferredItem<DragonScaleShovelItem> ENDER_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("ender_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.ENDER, props));
    public static final DeferredItem<DragonScalePickaxeItem> ENDER_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("ender_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.ENDER, props));
    public static final DeferredItem<DragonScaleHoeItem> ENDER_DRAGON_SCALE_HOE = TOOL_TAB.register("ender_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.ENDER, props));
    //Dragon Scale Tools - Enchant
    public static final DeferredItem<DragonScaleShovelItem> ENCHANTED_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("enchanted_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.ENCHANTED, props));
    public static final DeferredItem<DragonScalePickaxeItem> ENCHANTED_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("enchanted_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.ENCHANTED, props));
    public static final DeferredItem<DragonScaleHoeItem> ENCHANTED_DRAGON_SCALE_HOE = TOOL_TAB.register("enchanted_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.ENCHANTED, props));
    //Dragon Scale Tools - Sunlight
    public static final DeferredItem<DragonScaleShovelItem> SUNLIGHT_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("sunlight_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.SUNLIGHT, props));
    public static final DeferredItem<DragonScalePickaxeItem> SUNLIGHT_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("sunlight_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.SUNLIGHT, props));
    public static final DeferredItem<DragonScaleHoeItem> SUNLIGHT_DRAGON_SCALE_HOE = TOOL_TAB.register("sunlight_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.SUNLIGHT, props));
    //Dragon Scale Tools - Moonlight
    public static final DeferredItem<DragonScaleShovelItem> MOONLIGHT_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("moonlight_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.MOONLIGHT, props));
    public static final DeferredItem<DragonScalePickaxeItem> MOONLIGHT_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("moonlight_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.MOONLIGHT, props));
    public static final DeferredItem<DragonScaleHoeItem> MOONLIGHT_DRAGON_SCALE_HOE = TOOL_TAB.register("moonlight_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.MOONLIGHT, props));
    //Dragon Scale Tools - Storm
    public static final DeferredItem<DragonScaleShovelItem> STORM_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("storm_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.STORM, props));
    public static final DeferredItem<DragonScalePickaxeItem> STORM_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("storm_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.STORM, props));
    public static final DeferredItem<DragonScaleHoeItem> STORM_DRAGON_SCALE_HOE = TOOL_TAB.register("storm_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.STORM, props));
    //Dragon Scale Tools - Terra
    public static final DeferredItem<DragonScaleShovelItem> TERRA_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("terra_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.TERRA, props));
    public static final DeferredItem<DragonScalePickaxeItem> TERRA_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("terra_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.TERRA, props));
    public static final DeferredItem<DragonScaleHoeItem> TERRA_DRAGON_SCALE_HOE = TOOL_TAB.register("terra_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.TERRA, props));
    //Dragon Scale Tools - Zombie
    public static final DeferredItem<DragonScaleShovelItem> ZOMBIE_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("zombie_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.ZOMBIE, props));
    public static final DeferredItem<DragonScalePickaxeItem> ZOMBIE_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("zombie_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.ZOMBIE, props));
    public static final DeferredItem<DragonScaleHoeItem> ZOMBIE_DRAGON_SCALE_HOE = TOOL_TAB.register("zombie_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.ZOMBIE, props));
    //Dragon Scale Tools - Sculk
    public static final DeferredItem<DragonScaleShovelItem> SCULK_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("sculk_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.SCULK, props.fireResistant()));
    public static final DeferredItem<DragonScalePickaxeItem> SCULK_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("sculk_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.SCULK, props.fireResistant()));
    public static final DeferredItem<DragonScaleHoeItem> SCULK_DRAGON_SCALE_HOE = TOOL_TAB.register("sculk_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.SCULK, props.fireResistant()));
    //Dragon Scale Tools - Dark
    public static final DeferredItem<DragonScaleShovelItem> DARK_DRAGON_SCALE_SHOVEL = TOOL_TAB.register("dark_dragon_scale_shovel", props -> makeDragonScaleShovel(DragonTypes.DARK, props));
    public static final DeferredItem<DragonScalePickaxeItem> DARK_DRAGON_SCALE_PICKAXE = TOOL_TAB.register("dark_dragon_scale_pickaxe", props -> makeDragonScalePickaxe(DragonTypes.DARK, props));
    public static final DeferredItem<DragonScaleHoeItem> DARK_DRAGON_SCALE_HOE = TOOL_TAB.register("dark_dragon_scale_hoe", props -> makeDragonScaleHoe(DragonTypes.DARK, props));
    //Dragon Scale Armors
    public static final DragonScaleArmorSuit AETHER_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.AETHER,
            DMArmorEffects.AETHER,
            TOOL_TAB,
            "aether_dragon_scale_helmet",
            "aether_dragon_scale_chestplate",
            "aether_dragon_scale_leggings",
            "aether_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit WATER_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.WATER,
            DMArmorEffects.WATER,
            TOOL_TAB,
            "water_dragon_scale_helmet",
            "water_dragon_scale_chestplate",
            "water_dragon_scale_leggings",
            "water_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit ICE_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.ICE,
            DMArmorEffects.ICE,
            TOOL_TAB,
            "ice_dragon_scale_helmet",
            "ice_dragon_scale_chestplate",
            "ice_dragon_scale_leggings",
            "ice_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit FIRE_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.FIRE,
            DMArmorEffects.FIRE,
            TOOL_TAB,
            "fire_dragon_scale_helmet",
            "fire_dragon_scale_chestplate",
            "fire_dragon_scale_leggings",
            "fire_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit FOREST_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.FOREST,
            DMArmorEffects.FOREST,
            TOOL_TAB,
            "forest_dragon_scale_helmet",
            "forest_dragon_scale_chestplate",
            "forest_dragon_scale_leggings",
            "forest_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit NETHER_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.NETHER,
            DMArmorEffects.NETHER,
            TOOL_TAB,
            "nether_dragon_scale_helmet",
            "nether_dragon_scale_chestplate",
            "nether_dragon_scale_leggings",
            "nether_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit ENDER_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.ENDER,
            DMArmorEffects.ENDER,
            TOOL_TAB,
            "ender_dragon_scale_helmet",
            "ender_dragon_scale_chestplate",
            "ender_dragon_scale_leggings",
            "ender_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit ENCHANTED_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.ENCHANTED,
            DMArmorEffects.ENCHANTED,
            TOOL_TAB,
            "enchanted_dragon_scale_helmet",
            "enchanted_dragon_scale_chestplate",
            "enchanted_dragon_scale_leggings",
            "enchanted_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit SUNLIGHT_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.SUNLIGHT,
            DMArmorEffects.SUNLIGHT,
            TOOL_TAB,
            "sunlight_dragon_scale_helmet",
            "sunlight_dragon_scale_chestplate",
            "sunlight_dragon_scale_leggings",
            "sunlight_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit MOONLIGHT_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.MOONLIGHT,
            DMArmorEffects.MOONLIGHT,
            TOOL_TAB,
            "moonlight_dragon_scale_helmet",
            "moonlight_dragon_scale_chestplate",
            "moonlight_dragon_scale_leggings",
            "moonlight_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit STORM_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.STORM,
            DMArmorEffects.STORM,
            TOOL_TAB,
            "storm_dragon_scale_helmet",
            "storm_dragon_scale_chestplate",
            "storm_dragon_scale_leggings",
            "storm_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit TERRA_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.TERRA,
            DMArmorEffects.TERRA,
            TOOL_TAB,
            "terra_dragon_scale_helmet",
            "terra_dragon_scale_chestplate",
            "terra_dragon_scale_leggings",
            "terra_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit ZOMBIE_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.ZOMBIE,
            DMArmorEffects.ZOMBIE,
            TOOL_TAB,
            "zombie_dragon_scale_helmet",
            "zombie_dragon_scale_chestplate",
            "zombie_dragon_scale_leggings",
            "zombie_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    public static final DragonScaleArmorSuit SCULK_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.SCULK,
            null,
            TOOL_TAB,
            "sculk_dragon_scale_helmet",
            "sculk_dragon_scale_chestplate",
            "sculk_dragon_scale_leggings",
            "sculk_dragon_scale_boots",
            (suit, slot, props) -> makeDragonScaleArmor(suit, slot, props.fireResistant())
    );
    public static final DragonScaleArmorSuit DARK_DRAGON_SCALE_ARMORS = makeSuit(
            DragonTypes.DARK,
            null,
            TOOL_TAB,
            "dark_dragon_scale_helmet",
            "dark_dragon_scale_chestplate",
            "dark_dragon_scale_leggings",
            "dark_dragon_scale_boots",
            DMItems::makeDragonScaleArmor
    );
    //Dragon Spawn Eggs
    public static final DeferredItem<DragonSpawnEggItem> AETHER_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("aether_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.AETHER, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> DARK_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("dark_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.DARK, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> ENCHANTED_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("enchanted_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.ENCHANTED, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> ENDER_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("ender_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.ENDER, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> FIRE_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("fire_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.FIRE, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> FOREST_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("forest_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.FOREST, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> ICE_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("ice_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.ICE, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> MOONLIGHT_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("moonlight_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.MOONLIGHT, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> NETHER_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("nether_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.NETHER, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> SCULK_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("sculk_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.SCULK, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> SKELETON_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("skeleton_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.SKELETON, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> STORM_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("storm_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.STORM, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> SUNLIGHT_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("sunlight_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.SUNLIGHT, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> TERRA_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("terra_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.TERRA, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> WATER_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("water_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.WATER, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> WITHER_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("wither_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.WITHER, props)
    );
    public static final DeferredItem<DragonSpawnEggItem> ZOMBIE_DRAGON_SPAWN_EGG = DRAGON_SPAWN_EGGS.register("zombie_dragon_spawn_egg", props ->
            makeDragonSpawnEgg(DragonTypes.ZOMBIE, props)
    );
    //Shears
    public static final DeferredItem<TieredShearsItem> DIAMOND_SHEARS = ITEM_TAB.register("diamond_shears", props ->
            makeTieredShears(ToolMaterial.DIAMOND, props)
    );
    public static final DeferredItem<TieredShearsItem> NETHERITE_SHEARS = ITEM_TAB.register("netherite_shears", props ->
            makeTieredShears(ToolMaterial.NETHERITE, props.fireResistant())
    );
    //Misc
    public static final DeferredItem<WhistleItem> WHISTLE = ITEM_TAB.register("whistle", props ->
            new WhistleItem(props.stacksTo(1))
    );
    public static final DeferredItem<VariationOrbItem> VARIATION_ORB = ITEM_TAB.register("variation_orb", props ->
            new VariationOrbItem(props.stacksTo(16))
    );
    //Dragon Amulets
    public static final DeferredItem<AmuletItem<Entity>> AMULET = ITEM_TAB.register("amulet", props ->
            new AmuletItem<>(Entity.class, props.overrideDescription(AmuletItem.TRANSLATION_KEY))
    );
    public static final DeferredItem<DragonAmuletItem> FOREST_DRAGON_AMULET = registerItem("forest_dragon_amulet", props -> makeDragonAmulet(DragonTypes.FOREST, props));
    public static final DeferredItem<DragonAmuletItem> FIRE_DRAGON_AMULET = registerItem("fire_dragon_amulet", props -> makeDragonAmulet(DragonTypes.FIRE, props));
    public static final DeferredItem<DragonAmuletItem> ICE_DRAGON_AMULET = registerItem("ice_dragon_amulet", props -> makeDragonAmulet(DragonTypes.ICE, props));
    public static final DeferredItem<DragonAmuletItem> WATER_DRAGON_AMULET = registerItem("water_dragon_amulet", props -> makeDragonAmulet(DragonTypes.WATER, props));
    public static final DeferredItem<DragonAmuletItem> AETHER_DRAGON_AMULET = registerItem("aether_dragon_amulet", props -> makeDragonAmulet(DragonTypes.AETHER, props));
    public static final DeferredItem<DragonAmuletItem> NETHER_DRAGON_AMULET = registerItem("nether_dragon_amulet", props -> makeDragonAmulet(DragonTypes.NETHER, props));
    public static final DeferredItem<DragonAmuletItem> ENDER_DRAGON_AMULET = registerItem("ender_dragon_amulet", props -> makeDragonAmulet(DragonTypes.ENDER, props));
    public static final DeferredItem<DragonAmuletItem> SUNLIGHT_DRAGON_AMULET = registerItem("sunlight_dragon_amulet", props -> makeDragonAmulet(DragonTypes.SUNLIGHT, props));
    public static final DeferredItem<DragonAmuletItem> ENCHANTED_DRAGON_AMULET = registerItem("enchanted_dragon_amulet", props -> makeDragonAmulet(DragonTypes.ENCHANTED, props));
    public static final DeferredItem<DragonAmuletItem> STORM_DRAGON_AMULET = registerItem("storm_dragon_amulet", props -> makeDragonAmulet(DragonTypes.STORM, props));
    public static final DeferredItem<DragonAmuletItem> TERRA_DRAGON_AMULET = registerItem("terra_dragon_amulet", props -> makeDragonAmulet(DragonTypes.TERRA, props));
    public static final DeferredItem<DragonAmuletItem> ZOMBIE_DRAGON_AMULET = registerItem("zombie_dragon_amulet", props -> makeDragonAmulet(DragonTypes.ZOMBIE, props));
    public static final DeferredItem<DragonAmuletItem> MOONLIGHT_DRAGON_AMULET = registerItem("moonlight_dragon_amulet", props -> makeDragonAmulet(DragonTypes.MOONLIGHT, props));
    public static final DeferredItem<DragonAmuletItem> SCULK_DRAGON_AMULET = registerItem("sculk_dragon_amulet", props -> makeDragonAmulet(DragonTypes.SCULK, props.fireResistant()));
    public static final DeferredItem<DragonAmuletItem> SKELETON_DRAGON_AMULET = registerItem("skeleton_dragon_amulet", props -> makeDragonAmulet(DragonTypes.SKELETON, props));
    public static final DeferredItem<DragonAmuletItem> WITHER_DRAGON_AMULET = registerItem("wither_dragon_amulet", props -> makeDragonAmulet(DragonTypes.WITHER, props));
    public static final DeferredItem<DragonAmuletItem> DARK_DRAGON_AMULET = registerItem("dark_dragon_amulet", props -> makeDragonAmulet(DragonTypes.DARK, props));
    //Dragon Essences
    public static final DeferredItem<DragonEssenceItem> FOREST_DRAGON_ESSENCE = registerItem("forest_dragon_essence", props -> makeDragonEssence(DragonTypes.FOREST, props));
    public static final DeferredItem<DragonEssenceItem> FIRE_DRAGON_ESSENCE = registerItem("fire_dragon_essence", props -> makeDragonEssence(DragonTypes.FIRE, props));
    public static final DeferredItem<DragonEssenceItem> ICE_DRAGON_ESSENCE = registerItem("ice_dragon_essence", props -> makeDragonEssence(DragonTypes.ICE, props));
    public static final DeferredItem<DragonEssenceItem> WATER_DRAGON_ESSENCE = registerItem("water_dragon_essence", props -> makeDragonEssence(DragonTypes.WATER, props));
    public static final DeferredItem<DragonEssenceItem> AETHER_DRAGON_ESSENCE = registerItem("aether_dragon_essence", props -> makeDragonEssence(DragonTypes.AETHER, props));
    public static final DeferredItem<DragonEssenceItem> NETHER_DRAGON_ESSENCE = registerItem("nether_dragon_essence", props -> makeDragonEssence(DragonTypes.NETHER, props));
    public static final DeferredItem<DragonEssenceItem> ENDER_DRAGON_ESSENCE = registerItem("ender_dragon_essence", props -> makeDragonEssence(DragonTypes.ENDER, props));
    public static final DeferredItem<DragonEssenceItem> SUNLIGHT_DRAGON_ESSENCE = registerItem("sunlight_dragon_essence", props -> makeDragonEssence(DragonTypes.SUNLIGHT, props));
    public static final DeferredItem<DragonEssenceItem> ENCHANTED_DRAGON_ESSENCE = registerItem("enchanted_dragon_essence", props -> makeDragonEssence(DragonTypes.ENCHANTED, props));
    public static final DeferredItem<DragonEssenceItem> STORM_DRAGON_ESSENCE = registerItem("storm_dragon_essence", props -> makeDragonEssence(DragonTypes.STORM, props));
    public static final DeferredItem<DragonEssenceItem> TERRA_DRAGON_ESSENCE = registerItem("terra_dragon_essence", props -> makeDragonEssence(DragonTypes.TERRA, props));
    public static final DeferredItem<DragonEssenceItem> ZOMBIE_DRAGON_ESSENCE = registerItem("zombie_dragon_essence", props -> makeDragonEssence(DragonTypes.ZOMBIE, props));
    public static final DeferredItem<DragonEssenceItem> MOONLIGHT_DRAGON_ESSENCE = registerItem("moonlight_dragon_essence", props -> makeDragonEssence(DragonTypes.MOONLIGHT, props));
    public static final DeferredItem<DragonEssenceItem> SCULK_DRAGON_ESSENCE = registerItem("sculk_dragon_essence", props -> makeDragonEssence(DragonTypes.SCULK, props.fireResistant()));
    public static final DeferredItem<DragonEssenceItem> SKELETON_DRAGON_ESSENCE = registerItem("skeleton_dragon_essence", props -> makeDragonEssence(DragonTypes.SKELETON, props));
    public static final DeferredItem<DragonEssenceItem> WITHER_DRAGON_ESSENCE = registerItem("wither_dragon_essence", props -> makeDragonEssence(DragonTypes.WITHER, props));
    public static final DeferredItem<DragonEssenceItem> DARK_DRAGON_ESSENCE = registerItem("dark_dragon_essence", props -> makeDragonEssence(DragonTypes.DARK, props));

    static DragonAmuletItem makeDragonAmulet(DragonType type, Properties props) {
        var item = new DragonAmuletItem(type, props.overrideDescription(AmuletItem.TRANSLATION_KEY));
        type.bindInstance(DragonAmuletItem.class, item);
        return item;
    }

    static DragonArmorItem makeDragonArmor(ArmorMaterial material, Properties props) {
        return new DragonArmorItem(material, props.stacksTo(1));
    }

    static DragonEssenceItem makeDragonEssence(DragonType type, Properties props) {
        var item = new DragonEssenceItem(type, props.overrideDescription(DragonEssenceItem.TRANSLATION_KEY));
        type.bindInstance(DragonEssenceItem.class, item);
        return item;
    }

    static DragonScaleAxeItem makeDragonScaleAxe(DragonType type, float damage, float speed, Properties props) {
        var item = new DragonScaleAxeItem(type, damage, speed, props.overrideDescription(DragonScaleAxeItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleAxeItem.class, item);
        return item;
    }

    static DragonScaleAxeItem makeDragonScaleAxe(DragonType type, Properties props) {
        return makeDragonScaleAxe(type, 5.0F, -2.8F, props);
    }

    static DragonScaleBowItem makeDragonScaleBow(DragonType type, Properties props) {
        var item = new DragonScaleBowItem(type, props.overrideDescription(DragonScaleBowItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleBowItem.class, item);
        return item;
    }

    static DragonScaleHoeItem makeDragonScaleHoe(DragonType type, Properties props) {
        float damage = type.tier.attackDamageBonus();
        var item = new DragonScaleHoeItem(type, -damage, damage - 3.0F, props.overrideDescription(DragonScaleHoeItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleHoeItem.class, item);
        return item;
    }

    static DragonScalePickaxeItem makeDragonScalePickaxe(DragonType type, Properties props) {
        var item = new DragonScalePickaxeItem(type, 1.0F, -2.8F, props.overrideDescription(DragonScalePickaxeItem.TRANSLATION_KEY));
        type.bindInstance(DragonScalePickaxeItem.class, item);
        return item;
    }

    static DragonScaleArmorItem makeDragonScaleArmor(DragonScaleArmorSuit suit, ArmorType slot, Properties props) {
        if (suit.effect != null) {
            props.component(DMDataComponents.ARMOR_EFFECT_SOURCE, suit);
        }
        switch (slot) {
            case HELMET -> props.overrideDescription(DragonScaleArmorSuit.HELMET_TRANSLATION_KEY);
            case CHESTPLATE -> props.overrideDescription(DragonScaleArmorSuit.CHESTPLATE_TRANSLATION_KEY);
            case LEGGINGS -> props.overrideDescription(DragonScaleArmorSuit.LEGGINGS_TRANSLATION_KEY);
            case BOOTS -> props.overrideDescription(DragonScaleArmorSuit.BOOTS_TRANSLATION_KEY);
        }
        return new DragonScaleArmorItem(suit.type, suit.effect, slot, props);
    }

    static DragonScalesItem makeDragonScales(DragonType type, Properties props) {
        var item = new DragonScalesItem(type, props.overrideDescription(DragonScalesItem.TRANSLATION_KEY));
        type.bindInstance(DragonScalesItem.class, item);
        return item;
    }

    static DragonScaleShieldItem makeDragonScaleShield(DragonType type, Properties props) {
        var item = new DragonScaleShieldItem(type, props.overrideDescription(DragonScaleShieldItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleShieldItem.class, item);
        return item;
    }

    static DragonScaleShovelItem makeDragonScaleShovel(DragonType type, Properties props) {
        var item = new DragonScaleShovelItem(type, 1.5F, -3.0F, props.overrideDescription(DragonScaleShovelItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleShovelItem.class, item);
        return item;
    }

    static DragonScaleSwordItem makeDragonScaleSword(DragonType type, Properties props) {
        var item = new DragonScaleSwordItem(type, 3, -2.0F, props.overrideDescription(DragonScaleSwordItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleSwordItem.class, item);
        return item;
    }

    static DragonSpawnEggItem makeDragonSpawnEgg(DragonType type, Properties props) {
        var item = new DragonSpawnEggItem(type, props.overrideDescription(DragonSpawnEggItem.TRANSLATION_KEY));
        type.bindInstance(DragonSpawnEggItem.class, item);
        return item;
    }

    static TieredShearsItem makeTieredShears(ToolMaterial tier, Properties props) {
        var item = new TieredShearsItem(tier, props);
        DispenserBlock.registerBehavior(item, TieredShearsItem.DISPENSE_ITEM_BEHAVIOR);
        return item;
    }

    static BlockItem makeDragonEggBlock(HatchableDragonEggBlock block, Properties props) {
        return new BlockItem(block, props.component(DMDataComponents.DRAGON_TYPE, block.type).overrideDescription(HatchableDragonEggBlock.TRANSLATION_KEY));
    }

    static BlockItem makeDragonScaleBlock(DragonScaleBlock block, Properties props) {
        return new BlockItem(block, props.component(DMDataComponents.DRAGON_TYPE, block.type).overrideDescription(DragonScaleBlock.TRANSLATION_KEY));
    }

    public static void init() {}
}