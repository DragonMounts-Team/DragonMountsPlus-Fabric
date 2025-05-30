package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.api.DescribedArmorEffect;
import net.dragonmounts.plus.common.item.*;
import net.dragonmounts.plus.common.util.DragonScaleArmorSuit;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.DispenserBlock;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;
import static net.dragonmounts.plus.common.init.DMItemGroups.*;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerItem;

public class DMItems {
    public static final DragonScalesItem AETHER_DRAGON_SCALES = createDragonScales("aether_dragon_scales", DragonTypes.AETHER, new Properties());
    public static final DragonScalesItem ENCHANTED_DRAGON_SCALES = createDragonScales("enchanted_dragon_scales", DragonTypes.ENCHANTED, new Properties());
    public static final DragonScalesItem ENDER_DRAGON_SCALES = createDragonScales("ender_dragon_scales", DragonTypes.ENDER, new Properties());
    public static final DragonScalesItem FIRE_DRAGON_SCALES = createDragonScales("fire_dragon_scales", DragonTypes.FIRE, new Properties());
    public static final DragonScalesItem FOREST_DRAGON_SCALES = createDragonScales("forest_dragon_scales", DragonTypes.FOREST, new Properties());
    public static final DragonScalesItem ICE_DRAGON_SCALES = createDragonScales("ice_dragon_scales", DragonTypes.ICE, new Properties());
    public static final DragonScalesItem MOONLIGHT_DRAGON_SCALES = createDragonScales("moonlight_dragon_scales", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonScalesItem NETHER_DRAGON_SCALES = createDragonScales("nether_dragon_scales", DragonTypes.NETHER, new Properties());
    public static final DragonScalesItem SCULK_DRAGON_SCALES = createDragonScales("sculk_dragon_scales", DragonTypes.SCULK, new Properties().fireResistant());
    public static final DragonScalesItem STORM_DRAGON_SCALES = createDragonScales("storm_dragon_scales", DragonTypes.STORM, new Properties());
    public static final DragonScalesItem SUNLIGHT_DRAGON_SCALES = createDragonScales("sunlight_dragon_scales", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonScalesItem TERRA_DRAGON_SCALES = createDragonScales("terra_dragon_scales", DragonTypes.TERRA, new Properties());
    public static final DragonScalesItem WATER_DRAGON_SCALES = createDragonScales("water_dragon_scales", DragonTypes.WATER, new Properties());
    public static final DragonScalesItem ZOMBIE_DRAGON_SCALES = createDragonScales("zombie_dragon_scales", DragonTypes.ZOMBIE, new Properties());
    public static final DragonScalesItem DARK_DRAGON_SCALES = createDragonScales("dark_dragon_scales", DragonTypes.DARK, new Properties());
    //Dragon Armor
    public static final DragonArmorItem IRON_DRAGON_ARMOR = createDragonArmor("iron_dragon_armor", DragonArmorMaterials.IRON, new Properties());
    public static final DragonArmorItem GOLDEN_DRAGON_ARMOR = createDragonArmor("golden_dragon_armor", DragonArmorMaterials.GOLD, new Properties());
    public static final DragonArmorItem DIAMOND_DRAGON_ARMOR = createDragonArmor("diamond_dragon_armor", DragonArmorMaterials.DIAMOND, new Properties());
    public static final DragonArmorItem EMERALD_DRAGON_ARMOR = createDragonArmor("emerald_dragon_armor", DragonArmorMaterials.DIAMOND, new Properties());
    public static final DragonArmorItem NETHERITE_DRAGON_ARMOR = createDragonArmor("netherite_dragon_armor", DragonArmorMaterials.NETHERITE, new Properties().fireResistant());
    //Dragon Scale Swords
    public static final DragonScaleSwordItem AETHER_DRAGON_SCALE_SWORD = createDragonScaleSword("aether_dragon_scale_sword", DragonTypes.AETHER, new Properties());
    public static final DragonScaleSwordItem WATER_DRAGON_SCALE_SWORD = createDragonScaleSword("water_dragon_scale_sword", DragonTypes.WATER, new Properties());
    public static final DragonScaleSwordItem ICE_DRAGON_SCALE_SWORD = createDragonScaleSword("ice_dragon_scale_sword", DragonTypes.ICE, new Properties());
    public static final DragonScaleSwordItem FIRE_DRAGON_SCALE_SWORD = createDragonScaleSword("fire_dragon_scale_sword", DragonTypes.FIRE, new Properties());
    public static final DragonScaleSwordItem FOREST_DRAGON_SCALE_SWORD = createDragonScaleSword("forest_dragon_scale_sword", DragonTypes.FOREST, new Properties());
    public static final DragonScaleSwordItem NETHER_DRAGON_SCALE_SWORD = createDragonScaleSword("nether_dragon_scale_sword", DragonTypes.NETHER, new Properties());
    public static final DragonScaleSwordItem ENDER_DRAGON_SCALE_SWORD = createDragonScaleSword("ender_dragon_scale_sword", DragonTypes.ENDER, new Properties());
    public static final DragonScaleSwordItem ENCHANTED_DRAGON_SCALE_SWORD = createDragonScaleSword("enchanted_dragon_scale_sword", DragonTypes.ENCHANTED, new Properties());
    public static final DragonScaleSwordItem SUNLIGHT_DRAGON_SCALE_SWORD = createDragonScaleSword("sunlight_dragon_scale_sword", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonScaleSwordItem MOONLIGHT_DRAGON_SCALE_SWORD = createDragonScaleSword("moonlight_dragon_scale_sword", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonScaleSwordItem STORM_DRAGON_SCALE_SWORD = createDragonScaleSword("storm_dragon_scale_sword", DragonTypes.STORM, new Properties());
    public static final DragonScaleSwordItem TERRA_DRAGON_SCALE_SWORD = createDragonScaleSword("terra_dragon_scale_sword", DragonTypes.TERRA, new Properties());
    public static final DragonScaleSwordItem ZOMBIE_DRAGON_SCALE_SWORD = createDragonScaleSword("zombie_dragon_scale_sword", DragonTypes.ZOMBIE, new Properties());
    public static final DragonScaleSwordItem SCULK_DRAGON_SCALE_SWORD = createDragonScaleSword("sculk_dragon_scale_sword", DragonTypes.SCULK, new Properties().fireResistant());
    public static final DragonScaleSwordItem DARK_DRAGON_SCALE_SWORD = createDragonScaleSword("dark_dragon_scale_sword", DragonTypes.DARK, new Properties());
    //Dragon Scale Axes
    public static final DragonScaleAxeItem AETHER_DRAGON_SCALE_AXE = createDragonScaleAxe("aether_dragon_scale_axe", DragonTypes.AETHER, new Properties());
    public static final DragonScaleAxeItem WATER_DRAGON_SCALE_AXE = createDragonScaleAxe("water_dragon_scale_axe", DragonTypes.WATER, new Properties());
    public static final DragonScaleAxeItem ICE_DRAGON_SCALE_AXE = createDragonScaleAxe("ice_dragon_scale_axe", DragonTypes.ICE, new Properties());
    public static final DragonScaleAxeItem FIRE_DRAGON_SCALE_AXE = createDragonScaleAxe("fire_dragon_scale_axe", DragonTypes.FIRE, new Properties());
    public static final DragonScaleAxeItem FOREST_DRAGON_SCALE_AXE = createDragonScaleAxe("forest_dragon_scale_axe", DragonTypes.FOREST, new Properties());
    public static final DragonScaleAxeItem NETHER_DRAGON_SCALE_AXE = createDragonScaleAxe("nether_dragon_scale_axe", DragonTypes.NETHER, 6.0F, -2.9F, new Properties());
    public static final DragonScaleAxeItem ENDER_DRAGON_SCALE_AXE = createDragonScaleAxe("ender_dragon_scale_axe", DragonTypes.ENDER, 6.0F, -2.9F, new Properties());
    public static final DragonScaleAxeItem ENCHANTED_DRAGON_SCALE_AXE = createDragonScaleAxe("enchanted_dragon_scale_axe", DragonTypes.ENCHANTED, new Properties());
    public static final DragonScaleAxeItem SUNLIGHT_DRAGON_SCALE_AXE = createDragonScaleAxe("sunlight_dragon_scale_axe", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonScaleAxeItem MOONLIGHT_DRAGON_SCALE_AXE = createDragonScaleAxe("moonlight_dragon_scale_axe", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonScaleAxeItem STORM_DRAGON_SCALE_AXE = createDragonScaleAxe("storm_dragon_scale_axe", DragonTypes.STORM, new Properties());
    public static final DragonScaleAxeItem TERRA_DRAGON_SCALE_AXE = createDragonScaleAxe("terra_dragon_scale_axe", DragonTypes.TERRA, new Properties());
    public static final DragonScaleAxeItem ZOMBIE_DRAGON_SCALE_AXE = createDragonScaleAxe("zombie_dragon_scale_axe", DragonTypes.ZOMBIE, new Properties());
    public static final DragonScaleAxeItem SCULK_DRAGON_SCALE_AXE = createDragonScaleAxe("sculk_dragon_scale_axe", DragonTypes.SCULK, new Properties().fireResistant());
    public static final DragonScaleAxeItem DARK_DRAGON_SCALE_AXE = createDragonScaleAxe("dark_dragon_scale_axe", DragonTypes.DARK, new Properties());
    //Dragon Scale Bows
    public static final DragonScaleBowItem AETHER_DRAGON_SCALE_BOW = createDragonScaleBow("aether_dragon_scale_bow", DragonTypes.AETHER, new Properties());
    public static final DragonScaleBowItem WATER_DRAGON_SCALE_BOW = createDragonScaleBow("water_dragon_scale_bow", DragonTypes.WATER, new Properties());
    public static final DragonScaleBowItem ICE_DRAGON_SCALE_BOW = createDragonScaleBow("ice_dragon_scale_bow", DragonTypes.ICE, new Properties());
    public static final DragonScaleBowItem FIRE_DRAGON_SCALE_BOW = createDragonScaleBow("fire_dragon_scale_bow", DragonTypes.FIRE, new Properties());
    public static final DragonScaleBowItem FOREST_DRAGON_SCALE_BOW = createDragonScaleBow("forest_dragon_scale_bow", DragonTypes.FOREST, new Properties());
    public static final DragonScaleBowItem NETHER_DRAGON_SCALE_BOW = createDragonScaleBow("nether_dragon_scale_bow", DragonTypes.NETHER, new Properties());
    public static final DragonScaleBowItem ENDER_DRAGON_SCALE_BOW = createDragonScaleBow("ender_dragon_scale_bow", DragonTypes.ENDER, new Properties());
    public static final DragonScaleBowItem ENCHANTED_DRAGON_SCALE_BOW = createDragonScaleBow("enchanted_dragon_scale_bow", DragonTypes.ENCHANTED, new Properties());
    public static final DragonScaleBowItem SUNLIGHT_DRAGON_SCALE_BOW = createDragonScaleBow("sunlight_dragon_scale_bow", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonScaleBowItem MOONLIGHT_DRAGON_SCALE_BOW = createDragonScaleBow("moonlight_dragon_scale_bow", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonScaleBowItem STORM_DRAGON_SCALE_BOW = createDragonScaleBow("storm_dragon_scale_bow", DragonTypes.STORM, new Properties());
    public static final DragonScaleBowItem TERRA_DRAGON_SCALE_BOW = createDragonScaleBow("terra_dragon_scale_bow", DragonTypes.TERRA, new Properties());
    public static final DragonScaleBowItem ZOMBIE_DRAGON_SCALE_BOW = createDragonScaleBow("zombie_dragon_scale_bow", DragonTypes.ZOMBIE, new Properties());
    public static final DragonScaleBowItem SCULK_DRAGON_SCALE_BOW = createDragonScaleBow("sculk_dragon_scale_bow", DragonTypes.SCULK, new Properties().fireResistant());
    public static final DragonScaleBowItem DARK_DRAGON_SCALE_BOW = createDragonScaleBow("dark_dragon_scale_bow", DragonTypes.DARK, new Properties());
    //Dragon Scale Shields
    public static final DragonScaleShieldItem AETHER_DRAGON_SCALE_SHIELD = createDragonScaleShield("aether_dragon_scale_shield", DragonTypes.AETHER, new Properties());
    public static final DragonScaleShieldItem WATER_DRAGON_SCALE_SHIELD = createDragonScaleShield("water_dragon_scale_shield", DragonTypes.WATER, new Properties());
    public static final DragonScaleShieldItem ICE_DRAGON_SCALE_SHIELD = createDragonScaleShield("ice_dragon_scale_shield", DragonTypes.ICE, new Properties());
    public static final DragonScaleShieldItem FIRE_DRAGON_SCALE_SHIELD = createDragonScaleShield("fire_dragon_scale_shield", DragonTypes.FIRE, new Properties());
    public static final DragonScaleShieldItem FOREST_DRAGON_SCALE_SHIELD = createDragonScaleShield("forest_dragon_scale_shield", DragonTypes.FOREST, new Properties());
    public static final DragonScaleShieldItem NETHER_DRAGON_SCALE_SHIELD = createDragonScaleShield("nether_dragon_scale_shield", DragonTypes.NETHER, new Properties());
    public static final DragonScaleShieldItem ENDER_DRAGON_SCALE_SHIELD = createDragonScaleShield("ender_dragon_scale_shield", DragonTypes.ENDER, new Properties());
    public static final DragonScaleShieldItem ENCHANTED_DRAGON_SCALE_SHIELD = createDragonScaleShield(
            "enchanted_dragon_scale_shield",
            DragonTypes.ENCHANTED,
            new Properties().enchantable(DragonTypes.ENCHANTED.material.enchantmentValue())
    );
    public static final DragonScaleShieldItem SUNLIGHT_DRAGON_SCALE_SHIELD = createDragonScaleShield("sunlight_dragon_scale_shield", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonScaleShieldItem MOONLIGHT_DRAGON_SCALE_SHIELD = createDragonScaleShield("moonlight_dragon_scale_shield", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonScaleShieldItem STORM_DRAGON_SCALE_SHIELD = createDragonScaleShield("storm_dragon_scale_shield", DragonTypes.STORM, new Properties());
    public static final DragonScaleShieldItem TERRA_DRAGON_SCALE_SHIELD = createDragonScaleShield("terra_dragon_scale_shield", DragonTypes.TERRA, new Properties());
    public static final DragonScaleShieldItem ZOMBIE_DRAGON_SCALE_SHIELD = createDragonScaleShield("zombie_dragon_scale_shield", DragonTypes.ZOMBIE, new Properties());
    public static final DragonScaleShieldItem SCULK_DRAGON_SCALE_SHIELD = createDragonScaleShield("sculk_dragon_scale_shield", DragonTypes.SCULK, new Properties().fireResistant());
    public static final DragonScaleShieldItem DARK_DRAGON_SCALE_SHIELD = createDragonScaleShield("dark_dragon_scale_shield", DragonTypes.DARK, new Properties());
    //Dragon Scale Tools - Aether
    public static final DragonScaleShovelItem AETHER_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("aether_dragon_scale_shovel", DragonTypes.AETHER, new Properties());
    public static final DragonScalePickaxeItem AETHER_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("aether_dragon_scale_pickaxe", DragonTypes.AETHER, new Properties());
    public static final DragonScaleHoeItem AETHER_DRAGON_SCALE_HOE = createDragonScaleHoe("aether_dragon_scale_hoe", DragonTypes.AETHER, new Properties());
    //Dragon Scale Tools - Water
    public static final DragonScaleShovelItem WATER_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("water_dragon_scale_shovel", DragonTypes.WATER, new Properties());
    public static final DragonScalePickaxeItem WATER_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("water_dragon_scale_pickaxe", DragonTypes.WATER, new Properties());
    public static final DragonScaleHoeItem WATER_DRAGON_SCALE_HOE = createDragonScaleHoe("water_dragon_scale_hoe", DragonTypes.WATER, new Properties());
    //Dragon Scale Tools - Ice
    public static final DragonScaleShovelItem ICE_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("ice_dragon_scale_shovel", DragonTypes.ICE, new Properties());
    public static final DragonScalePickaxeItem ICE_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("ice_dragon_scale_pickaxe", DragonTypes.ICE, new Properties());
    public static final DragonScaleHoeItem ICE_DRAGON_SCALE_HOE = createDragonScaleHoe("ice_dragon_scale_hoe", DragonTypes.ICE, new Properties());
    //Dragon Scale Tools - Fire
    public static final DragonScaleShovelItem FIRE_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("fire_dragon_scale_shovel", DragonTypes.FIRE, new Properties());
    public static final DragonScalePickaxeItem FIRE_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("fire_dragon_scale_pickaxe", DragonTypes.FIRE, new Properties());
    public static final DragonScaleHoeItem FIRE_DRAGON_SCALE_HOE = createDragonScaleHoe("fire_dragon_scale_hoe", DragonTypes.FIRE, new Properties());
    //Dragon Scale Tools - Forest
    public static final DragonScaleShovelItem FOREST_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("forest_dragon_scale_shovel", DragonTypes.FOREST, new Properties());
    public static final DragonScalePickaxeItem FOREST_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("forest_dragon_scale_pickaxe", DragonTypes.FOREST, new Properties());
    public static final DragonScaleHoeItem FOREST_DRAGON_SCALE_HOE = createDragonScaleHoe("forest_dragon_scale_hoe", DragonTypes.FOREST, new Properties());
    //Dragon Scale Tools - Nether
    public static final DragonScaleShovelItem NETHER_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("nether_dragon_scale_shovel", DragonTypes.NETHER, new Properties());
    public static final DragonScalePickaxeItem NETHER_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("nether_dragon_scale_pickaxe", DragonTypes.NETHER, new Properties());
    public static final DragonScaleHoeItem NETHER_DRAGON_SCALE_HOE = createDragonScaleHoe("nether_dragon_scale_hoe", DragonTypes.NETHER, new Properties());
    //Dragon Scale Tools - Ender
    public static final DragonScaleShovelItem ENDER_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("ender_dragon_scale_shovel", DragonTypes.ENDER, new Properties());
    public static final DragonScalePickaxeItem ENDER_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("ender_dragon_scale_pickaxe", DragonTypes.ENDER, new Properties());
    public static final DragonScaleHoeItem ENDER_DRAGON_SCALE_HOE = createDragonScaleHoe("ender_dragon_scale_hoe", DragonTypes.ENDER, new Properties());
    //Dragon Scale Tools - Enchant
    public static final DragonScaleShovelItem ENCHANTED_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("enchanted_dragon_scale_shovel", DragonTypes.ENCHANTED, new Properties());
    public static final DragonScalePickaxeItem ENCHANTED_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("enchanted_dragon_scale_pickaxe", DragonTypes.ENCHANTED, new Properties());
    public static final DragonScaleHoeItem ENCHANTED_DRAGON_SCALE_HOE = createDragonScaleHoe("enchanted_dragon_scale_hoe", DragonTypes.ENCHANTED, new Properties());
    //Dragon Scale Tools - Sunlight
    public static final DragonScaleShovelItem SUNLIGHT_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("sunlight_dragon_scale_shovel", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonScalePickaxeItem SUNLIGHT_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("sunlight_dragon_scale_pickaxe", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonScaleHoeItem SUNLIGHT_DRAGON_SCALE_HOE = createDragonScaleHoe("sunlight_dragon_scale_hoe", DragonTypes.SUNLIGHT, new Properties());
    //Dragon Scale Tools - Moonlight
    public static final DragonScaleShovelItem MOONLIGHT_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("moonlight_dragon_scale_shovel", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonScalePickaxeItem MOONLIGHT_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("moonlight_dragon_scale_pickaxe", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonScaleHoeItem MOONLIGHT_DRAGON_SCALE_HOE = createDragonScaleHoe("moonlight_dragon_scale_hoe", DragonTypes.MOONLIGHT, new Properties());
    //Dragon Scale Tools - Storm
    public static final DragonScaleShovelItem STORM_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("storm_dragon_scale_shovel", DragonTypes.STORM, new Properties());
    public static final DragonScalePickaxeItem STORM_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("storm_dragon_scale_pickaxe", DragonTypes.STORM, new Properties());
    public static final DragonScaleHoeItem STORM_DRAGON_SCALE_HOE = createDragonScaleHoe("storm_dragon_scale_hoe", DragonTypes.STORM, new Properties());
    //Dragon Scale Tools - Terra
    public static final DragonScaleShovelItem TERRA_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("terra_dragon_scale_shovel", DragonTypes.TERRA, new Properties());
    public static final DragonScalePickaxeItem TERRA_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("terra_dragon_scale_pickaxe", DragonTypes.TERRA, new Properties());
    public static final DragonScaleHoeItem TERRA_DRAGON_SCALE_HOE = createDragonScaleHoe("terra_dragon_scale_hoe", DragonTypes.TERRA, new Properties());
    //Dragon Scale Tools - Zombie
    public static final DragonScaleShovelItem ZOMBIE_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("zombie_dragon_scale_shovel", DragonTypes.ZOMBIE, new Properties());
    public static final DragonScalePickaxeItem ZOMBIE_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("zombie_dragon_scale_pickaxe", DragonTypes.ZOMBIE, new Properties());
    public static final DragonScaleHoeItem ZOMBIE_DRAGON_SCALE_HOE = createDragonScaleHoe("zombie_dragon_scale_hoe", DragonTypes.ZOMBIE, new Properties());
    //Dragon Scale Tools - Sculk
    public static final DragonScaleShovelItem SCULK_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("sculk_dragon_scale_shovel", DragonTypes.SCULK, new Properties().fireResistant());
    public static final DragonScalePickaxeItem SCULK_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("sculk_dragon_scale_pickaxe", DragonTypes.SCULK, new Properties().fireResistant());
    public static final DragonScaleHoeItem SCULK_DRAGON_SCALE_HOE = createDragonScaleHoe("sculk_dragon_scale_hoe", DragonTypes.SCULK, new Properties().fireResistant());
    //Dragon Scale Tools - Dark
    public static final DragonScaleShovelItem DARK_DRAGON_SCALE_SHOVEL = createDragonScaleShovel("dark_dragon_scale_shovel", DragonTypes.DARK, new Properties());
    public static final DragonScalePickaxeItem DARK_DRAGON_SCALE_PICKAXE = createDragonScalePickaxe("dark_dragon_scale_pickaxe", DragonTypes.DARK, new Properties());
    public static final DragonScaleHoeItem DARK_DRAGON_SCALE_HOE = createDragonScaleHoe("dark_dragon_scale_hoe", DragonTypes.DARK, new Properties());
    //Dragon Scale Armors
    public static final DragonScaleArmorSuit AETHER_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "aether_dragon_scale_helmet",
            "aether_dragon_scale_chestplate",
            "aether_dragon_scale_leggings",
            "aether_dragon_scale_boots",
            DragonTypes.AETHER,
            DMArmorEffects.AETHER,
            new Properties()
    );
    public static final DragonScaleArmorSuit WATER_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "water_dragon_scale_helmet",
            "water_dragon_scale_chestplate",
            "water_dragon_scale_leggings",
            "water_dragon_scale_boots",
            DragonTypes.WATER,
            DMArmorEffects.WATER,
            new Properties()
    );
    public static final DragonScaleArmorSuit ICE_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "ice_dragon_scale_helmet",
            "ice_dragon_scale_chestplate",
            "ice_dragon_scale_leggings",
            "ice_dragon_scale_boots",
            DragonTypes.ICE,
            DMArmorEffects.ICE,
            new Properties()
    );
    public static final DragonScaleArmorSuit FIRE_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "fire_dragon_scale_helmet",
            "fire_dragon_scale_chestplate",
            "fire_dragon_scale_leggings",
            "fire_dragon_scale_boots",
            DragonTypes.FIRE,
            DMArmorEffects.FIRE,
            new Properties()
    );
    public static final DragonScaleArmorSuit FOREST_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "forest_dragon_scale_helmet",
            "forest_dragon_scale_chestplate",
            "forest_dragon_scale_leggings",
            "forest_dragon_scale_boots",
            DragonTypes.FOREST,
            DMArmorEffects.FOREST,
            new Properties()
    );
    public static final DragonScaleArmorSuit NETHER_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "nether_dragon_scale_helmet",
            "nether_dragon_scale_chestplate",
            "nether_dragon_scale_leggings",
            "nether_dragon_scale_boots",
            DragonTypes.NETHER,
            DMArmorEffects.NETHER,
            new Properties()
    );
    public static final DragonScaleArmorSuit ENDER_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "ender_dragon_scale_helmet",
            "ender_dragon_scale_chestplate",
            "ender_dragon_scale_leggings",
            "ender_dragon_scale_boots",
            DragonTypes.ENDER,
            DMArmorEffects.ENDER,
            new Properties()
    );
    public static final DragonScaleArmorSuit ENCHANTED_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "enchanted_dragon_scale_helmet",
            "enchanted_dragon_scale_chestplate",
            "enchanted_dragon_scale_leggings",
            "enchanted_dragon_scale_boots",
            DragonTypes.ENCHANTED,
            DMArmorEffects.ENCHANTED,
            new Properties()
    );
    public static final DragonScaleArmorSuit SUNLIGHT_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "sunlight_dragon_scale_helmet",
            "sunlight_dragon_scale_chestplate",
            "sunlight_dragon_scale_leggings",
            "sunlight_dragon_scale_boots",
            DragonTypes.SUNLIGHT,
            DMArmorEffects.SUNLIGHT,
            new Properties()
    );
    public static final DragonScaleArmorSuit MOONLIGHT_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "moonlight_dragon_scale_helmet",
            "moonlight_dragon_scale_chestplate",
            "moonlight_dragon_scale_leggings",
            "moonlight_dragon_scale_boots",
            DragonTypes.MOONLIGHT,
            DMArmorEffects.MOONLIGHT,
            new Properties()
    );
    public static final DragonScaleArmorSuit STORM_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "storm_dragon_scale_helmet",
            "storm_dragon_scale_chestplate",
            "storm_dragon_scale_leggings",
            "storm_dragon_scale_boots",
            DragonTypes.STORM,
            DMArmorEffects.STORM,
            new Properties()
    );
    public static final DragonScaleArmorSuit TERRA_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "terra_dragon_scale_helmet",
            "terra_dragon_scale_chestplate",
            "terra_dragon_scale_leggings",
            "terra_dragon_scale_boots",
            DragonTypes.TERRA,
            DMArmorEffects.TERRA,
            new Properties()
    );
    public static final DragonScaleArmorSuit ZOMBIE_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "zombie_dragon_scale_helmet",
            "zombie_dragon_scale_chestplate",
            "zombie_dragon_scale_leggings",
            "zombie_dragon_scale_boots",
            DragonTypes.ZOMBIE,
            DMArmorEffects.ZOMBIE,
            new Properties()
    );
    public static final DragonScaleArmorSuit SCULK_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "sculk_dragon_scale_helmet",
            "sculk_dragon_scale_chestplate",
            "sculk_dragon_scale_leggings",
            "sculk_dragon_scale_boots",
            DragonTypes.SCULK,
            null,
            new Properties().fireResistant()
    );
    public static final DragonScaleArmorSuit DARK_DRAGON_SCALE_ARMORS = createDragonScaleArmors(
            "dark_dragon_scale_helmet",
            "dark_dragon_scale_chestplate",
            "dark_dragon_scale_leggings",
            "dark_dragon_scale_boots",
            DragonTypes.DARK,
            null,
            new Properties()
    );
    //Dragon Spawn Eggs
    public static final DragonSpawnEggItem AETHER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("aether_dragon_spawn_egg", DragonTypes.AETHER, new Properties());
    public static final DragonSpawnEggItem DARK_DRAGON_SPAWN_EGG = createDragonSpawnEgg("dark_dragon_spawn_egg", DragonTypes.DARK, new Properties());
    public static final DragonSpawnEggItem ENCHANTED_DRAGON_SPAWN_EGG = createDragonSpawnEgg("enchanted_dragon_spawn_egg", DragonTypes.ENCHANTED, new Properties());
    public static final DragonSpawnEggItem ENDER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("ender_dragon_spawn_egg", DragonTypes.ENDER, new Properties());
    public static final DragonSpawnEggItem FIRE_DRAGON_SPAWN_EGG = createDragonSpawnEgg("fire_dragon_spawn_egg", DragonTypes.FIRE, new Properties());
    public static final DragonSpawnEggItem FOREST_DRAGON_SPAWN_EGG = createDragonSpawnEgg("forest_dragon_spawn_egg", DragonTypes.FOREST, new Properties());
    public static final DragonSpawnEggItem ICE_DRAGON_SPAWN_EGG = createDragonSpawnEgg("ice_dragon_spawn_egg", DragonTypes.ICE, new Properties());
    public static final DragonSpawnEggItem MOONLIGHT_DRAGON_SPAWN_EGG = createDragonSpawnEgg("moonlight_dragon_spawn_egg", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonSpawnEggItem NETHER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("nether_dragon_spawn_egg", DragonTypes.NETHER, new Properties());
    public static final DragonSpawnEggItem SCULK_DRAGON_SPAWN_EGG = createDragonSpawnEgg("sculk_dragon_spawn_egg", DragonTypes.SCULK, new Properties());
    public static final DragonSpawnEggItem SKELETON_DRAGON_SPAWN_EGG = createDragonSpawnEgg("skeleton_dragon_spawn_egg", DragonTypes.SKELETON, new Properties());
    public static final DragonSpawnEggItem STORM_DRAGON_SPAWN_EGG = createDragonSpawnEgg("storm_dragon_spawn_egg", DragonTypes.STORM, new Properties());
    public static final DragonSpawnEggItem SUNLIGHT_DRAGON_SPAWN_EGG = createDragonSpawnEgg("sunlight_dragon_spawn_egg", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonSpawnEggItem TERRA_DRAGON_SPAWN_EGG = createDragonSpawnEgg("terra_dragon_spawn_egg", DragonTypes.TERRA, new Properties());
    public static final DragonSpawnEggItem WATER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("water_dragon_spawn_egg", DragonTypes.WATER, new Properties());
    public static final DragonSpawnEggItem WITHER_DRAGON_SPAWN_EGG = createDragonSpawnEgg("wither_dragon_spawn_egg", DragonTypes.WITHER, new Properties());
    public static final DragonSpawnEggItem ZOMBIE_DRAGON_SPAWN_EGG = createDragonSpawnEgg("zombie_dragon_spawn_egg", DragonTypes.ZOMBIE, new Properties());
    //Shears
    public static final TieredShearsItem DIAMOND_SHEARS = createTieredShears("diamond_shears", ToolMaterial.DIAMOND, new Properties());
    public static final TieredShearsItem NETHERITE_SHEARS = createTieredShears("netherite_shears", ToolMaterial.NETHERITE, new Properties().fireResistant());
    //Misc
    public static final WhistleItem WHISTLE = ITEM_TAB.register(
            makeId("whistle"),
            props -> new WhistleItem(props.stacksTo(1))
    );
    public static final VariationOrbItem VARIATION_ORB = ITEM_TAB.register(
            makeId("variation_orb"),
            props -> new VariationOrbItem(props.stacksTo(16))
    );
    //Dragon Amulets
    public static final AmuletItem<Entity> AMULET = ITEM_TAB.register(
            makeId("amulet"),
            props -> new AmuletItem<>(Entity.class, props.overrideDescription(AmuletItem.TRANSLATION_KEY))
    );
    public static final DragonAmuletItem FOREST_DRAGON_AMULET = createDragonAmulet("forest_dragon_amulet", DragonTypes.FOREST, new Properties());
    public static final DragonAmuletItem FIRE_DRAGON_AMULET = createDragonAmulet("fire_dragon_amulet", DragonTypes.FIRE, new Properties());
    public static final DragonAmuletItem ICE_DRAGON_AMULET = createDragonAmulet("ice_dragon_amulet", DragonTypes.ICE, new Properties());
    public static final DragonAmuletItem WATER_DRAGON_AMULET = createDragonAmulet("water_dragon_amulet", DragonTypes.WATER, new Properties());
    public static final DragonAmuletItem AETHER_DRAGON_AMULET = createDragonAmulet("aether_dragon_amulet", DragonTypes.AETHER, new Properties());
    public static final DragonAmuletItem NETHER_DRAGON_AMULET = createDragonAmulet("nether_dragon_amulet", DragonTypes.NETHER, new Properties());
    public static final DragonAmuletItem ENDER_DRAGON_AMULET = createDragonAmulet("ender_dragon_amulet", DragonTypes.ENDER, new Properties());
    public static final DragonAmuletItem SUNLIGHT_DRAGON_AMULET = createDragonAmulet("sunlight_dragon_amulet", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonAmuletItem ENCHANTED_DRAGON_AMULET = createDragonAmulet("enchanted_dragon_amulet", DragonTypes.ENCHANTED, new Properties());
    public static final DragonAmuletItem STORM_DRAGON_AMULET = createDragonAmulet("storm_dragon_amulet", DragonTypes.STORM, new Properties());
    public static final DragonAmuletItem TERRA_DRAGON_AMULET = createDragonAmulet("terra_dragon_amulet", DragonTypes.TERRA, new Properties());
    public static final DragonAmuletItem ZOMBIE_DRAGON_AMULET = createDragonAmulet("zombie_dragon_amulet", DragonTypes.ZOMBIE, new Properties());
    public static final DragonAmuletItem MOONLIGHT_DRAGON_AMULET = createDragonAmulet("moonlight_dragon_amulet", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonAmuletItem SCULK_DRAGON_AMULET = createDragonAmulet("sculk_dragon_amulet", DragonTypes.SCULK, new Properties().fireResistant());
    public static final DragonAmuletItem SKELETON_DRAGON_AMULET = createDragonAmulet("skeleton_dragon_amulet", DragonTypes.SKELETON, new Properties());
    public static final DragonAmuletItem WITHER_DRAGON_AMULET = createDragonAmulet("wither_dragon_amulet", DragonTypes.WITHER, new Properties());
    public static final DragonAmuletItem DARK_DRAGON_AMULET = createDragonAmulet("dark_dragon_amulet", DragonTypes.DARK, new Properties());
    //Dragon Essences
    public static final DragonEssenceItem FOREST_DRAGON_ESSENCE = createDragonEssence("forest_dragon_essence", DragonTypes.FOREST, new Properties());
    public static final DragonEssenceItem FIRE_DRAGON_ESSENCE = createDragonEssence("fire_dragon_essence", DragonTypes.FIRE, new Properties());
    public static final DragonEssenceItem ICE_DRAGON_ESSENCE = createDragonEssence("ice_dragon_essence", DragonTypes.ICE, new Properties());
    public static final DragonEssenceItem WATER_DRAGON_ESSENCE = createDragonEssence("water_dragon_essence", DragonTypes.WATER, new Properties());
    public static final DragonEssenceItem AETHER_DRAGON_ESSENCE = createDragonEssence("aether_dragon_essence", DragonTypes.AETHER, new Properties());
    public static final DragonEssenceItem NETHER_DRAGON_ESSENCE = createDragonEssence("nether_dragon_essence", DragonTypes.NETHER, new Properties());
    public static final DragonEssenceItem ENDER_DRAGON_ESSENCE = createDragonEssence("ender_dragon_essence", DragonTypes.ENDER, new Properties());
    public static final DragonEssenceItem SUNLIGHT_DRAGON_ESSENCE = createDragonEssence("sunlight_dragon_essence", DragonTypes.SUNLIGHT, new Properties());
    public static final DragonEssenceItem ENCHANTED_DRAGON_ESSENCE = createDragonEssence("enchanted_dragon_essence", DragonTypes.ENCHANTED, new Properties());
    public static final DragonEssenceItem STORM_DRAGON_ESSENCE = createDragonEssence("storm_dragon_essence", DragonTypes.STORM, new Properties());
    public static final DragonEssenceItem TERRA_DRAGON_ESSENCE = createDragonEssence("terra_dragon_essence", DragonTypes.TERRA, new Properties());
    public static final DragonEssenceItem ZOMBIE_DRAGON_ESSENCE = createDragonEssence("zombie_dragon_essence", DragonTypes.ZOMBIE, new Properties());
    public static final DragonEssenceItem MOONLIGHT_DRAGON_ESSENCE = createDragonEssence("moonlight_dragon_essence", DragonTypes.MOONLIGHT, new Properties());
    public static final DragonEssenceItem SCULK_DRAGON_ESSENCE = createDragonEssence("sculk_dragon_essence", DragonTypes.SCULK, new Properties().fireResistant());
    public static final DragonEssenceItem SKELETON_DRAGON_ESSENCE = createDragonEssence("skeleton_dragon_essence", DragonTypes.SKELETON, new Properties());
    public static final DragonEssenceItem WITHER_DRAGON_ESSENCE = createDragonEssence("wither_dragon_essence", DragonTypes.WITHER, new Properties());
    public static final DragonEssenceItem DARK_DRAGON_ESSENCE = createDragonEssence("dark_dragon_essence", DragonTypes.DARK, new Properties());

    static DragonAmuletItem createDragonAmulet(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonAmuletItem(type, props.setId(key).overrideDescription(AmuletItem.TRANSLATION_KEY));
        type.bindInstance(DragonAmuletItem.class, item);
        return registerItem(key, item);
    }

    static DragonArmorItem createDragonArmor(String name, ArmorMaterial material, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        return TOOL_TAB.register(key, new DragonArmorItem(material, props.setId(key).stacksTo(1)));
    }

    static DragonEssenceItem createDragonEssence(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonEssenceItem(type, props.setId(key).overrideDescription(DragonEssenceItem.TRANSLATION_KEY));
        type.bindInstance(DragonEssenceItem.class, item);
        return registerItem(key, item);
    }

    static DragonScaleAxeItem createDragonScaleAxe(String name, DragonType type, float damage, float speed, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonScaleAxeItem(type, damage, speed, props.setId(key).overrideDescription(DragonScaleAxeItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleAxeItem.class, item);
        return TOOL_TAB.register(key, item);
    }

    static DragonScaleAxeItem createDragonScaleAxe(String name, DragonType type, Properties props) {
        return createDragonScaleAxe(name, type, 5.0F, -2.8F, props);
    }

    static DragonScaleBowItem createDragonScaleBow(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonScaleBowItem(type, props.setId(key).overrideDescription(DragonScaleBowItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleBowItem.class, item);
        return TOOL_TAB.register(key, item);
    }

    static DragonScaleHoeItem createDragonScaleHoe(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        float damage = type.tier.attackDamageBonus();
        var item = new DragonScaleHoeItem(type, -damage, damage - 3.0F, props.setId(key).overrideDescription(DragonScaleHoeItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleHoeItem.class, item);
        return TOOL_TAB.register(key, item);
    }

    static DragonScalePickaxeItem createDragonScalePickaxe(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonScalePickaxeItem(type, 1.0F, -2.8F, props.setId(key).overrideDescription(DragonScalePickaxeItem.TRANSLATION_KEY));
        type.bindInstance(DragonScalePickaxeItem.class, item);
        return TOOL_TAB.register(key, item);
    }

    static DragonScaleArmorItem createDragonScaleArmor(
            DragonType type,
            DescribedArmorEffect effect,
            Properties props,
            ArmorType slot,
            ResourceKey<Item> key,
            String desc
    ) {
        return TOOL_TAB.register(key, new DragonScaleArmorItem(type, effect, slot, props.setId(key).overrideDescription(desc)));
    }

    static DragonScaleArmorSuit createDragonScaleArmors(
            String helmet,
            String chestplate,
            String leggings,
            String boots,
            DragonType type,
            DescribedArmorEffect effect,
            Properties props
    ) {
        var registry = Registries.ITEM;
        var suit = new DragonScaleArmorSuit(
                type,
                effect,
                props,
                makeKey(registry, helmet),
                makeKey(registry, chestplate),
                makeKey(registry, leggings),
                makeKey(registry, boots),
                DMItems::createDragonScaleArmor
        );
        type.bindInstance(DragonScaleArmorSuit.class, suit);
        return suit;
    }

    static DragonScalesItem createDragonScales(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonScalesItem(type, props.setId(key).overrideDescription(DragonScalesItem.TRANSLATION_KEY));
        type.bindInstance(DragonScalesItem.class, item);
        return ITEM_TAB.register(key, item);
    }

    static DragonScaleShieldItem createDragonScaleShield(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonScaleShieldItem(type, props.setId(key).overrideDescription(DragonScaleShieldItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleShieldItem.class, item);
        return TOOL_TAB.register(key, item);
    }

    static DragonScaleShovelItem createDragonScaleShovel(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonScaleShovelItem(type, 1.5F, -3.0F, props.setId(key).overrideDescription(DragonScaleShovelItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleShovelItem.class, item);
        return TOOL_TAB.register(key, item);
    }

    static DragonScaleSwordItem createDragonScaleSword(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonScaleSwordItem(type, 3, -2.0F, props.setId(key).overrideDescription(DragonScaleSwordItem.TRANSLATION_KEY));
        type.bindInstance(DragonScaleSwordItem.class, item);
        return TOOL_TAB.register(key, item);
    }

    static DragonSpawnEggItem createDragonSpawnEgg(String name, DragonType type, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new DragonSpawnEggItem(type, props.setId(key).overrideDescription(DragonSpawnEggItem.TRANSLATION_KEY));
        type.bindInstance(DragonSpawnEggItem.class, item);
        return DRAGON_SPAWN_EGGS.register(key, item);
    }

    static TieredShearsItem createTieredShears(String name, ToolMaterial tier, Properties props) {
        var key = makeKey(Registries.ITEM, name);
        var item = new TieredShearsItem(tier, props.setId(key));
        DispenserBlock.registerBehavior(item, TieredShearsItem.DISPENSE_ITEM_BEHAVIOR);
        return ITEM_TAB.register(key, item);
    }

    public static void init() {}
}