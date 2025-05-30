package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.tag.DMItemTags;
import net.dragonmounts.plus.common.type.*;
import net.dragonmounts.plus.common.util.ArmorMaterialBuilder;
import net.dragonmounts.plus.common.util.ItemTierBuilder;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.compat.registry.DragonTypeBuilder.BONUS_ID;
import static net.minecraft.world.item.equipment.ArmorType.*;

public class DragonTypes {
    public static final DragonType AETHER;
    public static final DragonType DARK;
    public static final DragonType ENCHANTED;
    public static final DragonType ENDER;
    public static final DragonType FIRE;
    public static final DragonType FOREST;
    public static final DragonType ICE;
    public static final DragonType MOONLIGHT;
    public static final DragonType NETHER;
    public static final DragonType SCULK;
    public static final DragonType SKELETON;
    public static final DragonType STORM;
    public static final DragonType SUNLIGHT;
    public static final DragonType TERRA;
    public static final DragonType WATER;
    public static final DragonType WITHER;
    public static final DragonType ZOMBIE;

    static {
        var netherite = BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        var material = new ArmorMaterialBuilder(50)
                .setDefense(HELMET, 3)
                .setDefense(CHESTPLATE, 8)
                .setDefense(LEGGINGS, 7)
                .setDefense(BOOTS, 3)
                .setEnchantmentValue(11)
                .setToughness(7.0F);
        var tier = new ItemTierBuilder(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2700, 8.0F, 5.0F)
                .setEnchantmentValue(11);
        MOONLIGHT = new DragonTypeBuilder(0x2C427C, material, tier)
                .setMaterial(DMItemTags.MOONLIGHT_DRAGON_SCALES)
                .setScaleColor(MapColor.COLOR_BLUE)
                .addHabitat(Blocks.BLUE_GLAZED_TERRACOTTA)
                .register(MoonlightType::new, makeId("moonlight"));
        TERRA = new DragonTypeBuilder(0xA56C21, material, tier)
                .setMaterial(DMItemTags.TERRA_DRAGON_SCALES)
                .setScaleColor(MapColor.DIRT)
                .addHabitat(Blocks.TERRACOTTA)
                .addHabitat(Blocks.SAND)
                .addHabitat(Blocks.SANDSTONE)
                .addHabitat(Blocks.SANDSTONE_SLAB)
                .addHabitat(Blocks.SANDSTONE_STAIRS)
                .addHabitat(Blocks.SANDSTONE_WALL)
                .addHabitat(Blocks.RED_SAND)
                .addHabitat(Blocks.RED_SANDSTONE)
                .addHabitat(Blocks.RED_SANDSTONE_SLAB)
                .addHabitat(Blocks.RED_SANDSTONE_STAIRS)
                .addHabitat(Blocks.RED_SANDSTONE_WALL)
                //.addHabitat(BiomeKeys.MESA)
                //.addHabitat(BiomeKeys.MESA_ROCK)
                //.addHabitat(BiomeKeys.MESA_CLEAR_ROCK)
                //.addHabitat(BiomeKeys.MUTATED_MESA_CLEAR_ROCK)
                //.addHabitat(BiomeKeys.MUTATED_MESA_ROCK)
                .register(TerraType::new, makeId("terra"));
        ZOMBIE = new DragonTypeBuilder(0x5A5602, material, tier)
                .setMaterial(DMItemTags.ZOMBIE_DRAGON_SCALES)
                .setScaleColor(MapColor.TERRACOTTA_GREEN)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .addHabitat(Blocks.SOUL_SAND)
                .addHabitat(Blocks.SOUL_SAND)
                .addHabitat(Blocks.NETHER_WART_BLOCK)
                .addHabitat(Blocks.WARPED_WART_BLOCK)
                .register(ZombieType::new, makeId("zombie"));
        DARK = new DragonTypeBuilder(0x808080, material.setDefense(BOOTS, 5), tier)
                .setMaterial(DMItemTags.DARK_DRAGON_SCALES)
                .setScaleColor(MapColor.DEEPSLATE)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .register(DarkType::new, makeId("dark"));
        material.setDefense(HELMET, 4).setDefense(BOOTS, 4);
        AETHER = new DragonTypeBuilder(0x0294BD, material, new ItemTierBuilder(netherite, 2700, 8.0F, 5.0F).setEnchantmentValue(11))
                .setMaterial(DMItemTags.AETHER_DRAGON_SCALES)
                .setScaleColor(MapColor.COLOR_LIGHT_BLUE)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .addHabitat(Blocks.LAPIS_BLOCK)
                //.addHabitat(Blocks.LIT_FURNACE)
                //.addHabitat(Blocks.FLOWING_LAVA)
                .addHabitat(Blocks.LAPIS_ORE)
                .register(AetherType::new, makeId("aether"));
        FIRE = new DragonTypeBuilder(0x960B0F, material, tier)
                .setMaterial(DMItemTags.FIRE_DRAGON_SCALES)
                .setScaleColor(MapColor.FIRE)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .addHabitat(Blocks.FIRE)
                //.addHabitat(Blocks.LIT_FURNACE)
                //.addHabitat(Blocks.YELLOW_FLOWER)
                //.addHabitat(Blocks.RED_FLOWER)
                //.addHabitat(Blocks.SAPLING)
                //.addHabitat(Blocks.LEAVES)
                //.addHabitat(Blocks.LEAVES2)
                //.addHabitat(Biomes.JUNGLE_HILLS)
                .addHabitat(Blocks.LAVA)
                .register(FireType::new, makeId("fire"));
        FOREST = new DragonTypeBuilder(0x298317, material, tier)
                .setMaterial(DMItemTags.FOREST_DRAGON_SCALES)
                .setScaleColor(MapColor.COLOR_GREEN)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                //.addHabitat(Blocks.YELLOW_FLOWER)
                //.addHabitat(Blocks.RED_FLOWER)
                .addHabitat(Blocks.MOSSY_COBBLESTONE)
                .addHabitat(Blocks.VINE)
                //.addHabitat(Blocks.SAPLING)
                //.addHabitat(Blocks.LEAVES)
                //.addHabitat(Blocks.LEAVES2)
                //.addHabitat(BiomeKeys.JUNGLE)
                //.addHabitat(BiomeKeys.JUNGLE_HILLS)
                .addHabitat(Biomes.JUNGLE)
                .register(ForestType::new, makeId("forest"));
        ICE = new DragonTypeBuilder(0x00F2FF, material, tier)
                .setMaterial(DMItemTags.ICE_DRAGON_SCALES)
                .setScaleColor(MapColor.SNOW)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .addHabitat(Blocks.SNOW)
                .addHabitat(Blocks.ICE)
                .addHabitat(Blocks.PACKED_ICE)
                .addHabitat(Blocks.FROSTED_ICE)
                .addHabitat(Biomes.FROZEN_OCEAN)
                .addHabitat(Biomes.FROZEN_RIVER)
                .register(IceType::new, makeId("ice"));
        STORM = new DragonTypeBuilder(0xF5F1E9, material, tier)
                .setMaterial(DMItemTags.STORM_DRAGON_SCALES)
                .setScaleColor(MapColor.WOOL)
                .register(StormType::new, makeId("storm"));
        SUNLIGHT = new DragonTypeBuilder(0xFFDE00, material, tier)
                .setMaterial(DMItemTags.SUNLIGHT_DRAGON_SCALES)
                .setScaleColor(MapColor.COLOR_YELLOW)
                .addHabitat(Blocks.GLOWSTONE)
                .addHabitat(Blocks.JACK_O_LANTERN)
                .addHabitat(Blocks.SHROOMLIGHT)
                .addHabitat(Blocks.YELLOW_GLAZED_TERRACOTTA)
                .register(SunlightType::new, makeId("sunlight"));
        WATER = new DragonTypeBuilder(0x4F69A8, material, tier)
                .setMaterial(DMItemTags.WATER_DRAGON_SCALES)
                .setScaleColor(MapColor.WATER)
                .addImmunity(DamageTypes.DROWN)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .addHabitat(Blocks.WATER)
                .addHabitat(Biomes.OCEAN)
                .addHabitat(Biomes.RIVER)
                .register(WaterType::new, makeId("water"));
        //modify builder
        ENCHANTED = new DragonTypeBuilder(0x8359AE, material.setEnchantmentValue(30), tier.setEnchantmentValue(30))
                .setMaterial(DMItemTags.ENCHANTED_DRAGON_SCALES)
                .setScaleColor(MapColor.COLOR_PURPLE)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .addHabitat(Blocks.BOOKSHELF)
                .addHabitat(Blocks.ENCHANTING_TABLE)
                .register(EnchantedType::new, makeId("enchanted"));
        material.setDurabilityFactor(70)
                .setDefense(CHESTPLATE, 9)
                .setEnchantmentValue(11)
                .setToughness(9.0F);
        tier = new ItemTierBuilder(netherite, 3000, 8.0F, 6.0F).setEnchantmentValue(11);
        ENDER = new DragonTypeBuilder(0xAB39BE, material, tier)
                .setMaterial(DMItemTags.ENDER_DRAGON_SCALES)
                .setScaleColor(MapColor.COLOR_BLACK)
                .notConvertible()
                .putAttributeModifier(Attributes.MAX_HEALTH, BONUS_ID, 10.0D, AttributeModifier.Operation.ADD_VALUE)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .setSneezeParticle(ParticleTypes.PORTAL)
                .setEggParticle(ParticleTypes.PORTAL)
                .register(EnderType::new, DragonType.DEFAULT_KEY);
        SCULK = new DragonTypeBuilder(0x29DFEB, material, tier)
                .setMaterial(DMItemTags.SCULK_DRAGON_SCALES)
                .setScaleColor(MapColor.COLOR_BLACK)
                .notConvertible()
                .putAttributeModifier(Attributes.MAX_HEALTH, BONUS_ID, 10.0D, AttributeModifier.Operation.ADD_VALUE)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .register(SculkType::new, makeId("sculk"));
        //modify builders
        NETHER = new DragonTypeBuilder(0xE5B81B, material.setDurabilityFactor(55).setToughness(8.0F), new ItemTierBuilder(netherite, 2700, 8.0F, 6.0F).setEnchantmentValue(11))
                .setMaterial(DMItemTags.NETHER_DRAGON_SCALES)
                .setScaleColor(MapColor.NETHER)
                .putAttributeModifier(Attributes.MAX_HEALTH, BONUS_ID, 5.0D, AttributeModifier.Operation.ADD_VALUE)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                //.addHabitat(BiomeKeys.HELL)
                .setEggParticle(ParticleTypes.DRIPPING_LAVA)
                .register(NetherType::new, makeId("nether"));
        //no scale items
        SKELETON = new DragonTypeBuilder(0xFFFFFF, null, null)
                .putAttributeModifier(Attributes.MAX_HEALTH, BONUS_ID, -15.0D, AttributeModifier.Operation.ADD_VALUE)
                .setScaleColor(MapColor.QUARTZ)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .addHabitat(Blocks.BONE_BLOCK)
                .register(SkeletonType::new, makeId("skeleton"));
        WITHER = new DragonTypeBuilder(0x50260A, null, null)
                .notConvertible()
                .putAttributeModifier(Attributes.MAX_HEALTH, BONUS_ID, -10.0D, AttributeModifier.Operation.ADD_VALUE)
                .setScaleColor(MapColor.COLOR_GRAY)
                .addImmunity(DamageTypes.MAGIC)
                .addImmunity(DamageTypes.HOT_FLOOR)
                .addImmunity(DamageTypes.LIGHTNING_BOLT)
                .addImmunity(DamageTypes.WITHER)
                .register(WitherType::new, makeId("wither"));
    }
}
