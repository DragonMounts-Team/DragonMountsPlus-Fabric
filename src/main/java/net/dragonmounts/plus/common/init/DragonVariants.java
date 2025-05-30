package net.dragonmounts.plus.common.init;

import com.google.common.collect.ImmutableList;
import net.dragonmounts.plus.common.block.DragonHeadBlock;
import net.dragonmounts.plus.common.block.DragonHeadStandingBlock;
import net.dragonmounts.plus.common.block.DragonHeadWallBlock;
import net.dragonmounts.plus.common.client.variant.VariantAppearance;
import net.dragonmounts.plus.common.client.variant.VariantAppearances;
import net.dragonmounts.plus.common.item.DragonHeadItem;
import net.dragonmounts.plus.compat.platform.PlatformCompat;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonVariant;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerBlock;

public class DragonVariants {
    public static final ImmutableList<DragonVariant> BUILTIN_VALUES;
    public static final DragonVariant AETHER_FEMALE;
    public static final DragonVariant AETHER_MALE;
    public static final DragonVariant DARK_FEMALE;
    public static final DragonVariant DARK_MALE;
    public static final DragonVariant ENCHANTED_FEMALE;
    public static final DragonVariant ENCHANTED_MALE;
    public static final DragonVariant ENDER_FEMALE;
    public static final DragonVariant ENDER_MALE;
    public static final DragonVariant ENDER_RARE;
    public static final DragonVariant FIRE_FEMALE;
    public static final DragonVariant FIRE_MALE;
    public static final DragonVariant FIRE_RARE;
    public static final DragonVariant FOREST_FEMALE;
    public static final DragonVariant FOREST_MALE;
    public static final DragonVariant FOREST_DRY_FEMALE;
    public static final DragonVariant FOREST_DRY_MALE;
    public static final DragonVariant FOREST_TAIGA_FEMALE;
    public static final DragonVariant FOREST_TAIGA_MALE;
    public static final DragonVariant ICE_FEMALE;
    public static final DragonVariant ICE_MALE;
    public static final DragonVariant MOONLIGHT_FEMALE;
    public static final DragonVariant MOONLIGHT_MALE;
    public static final DragonVariant NETHER_FEMALE;
    public static final DragonVariant NETHER_MALE;
    public static final DragonVariant SCULK;
    public static final DragonVariant SKELETON;
    public static final DragonVariant STORM_FEMALE;
    public static final DragonVariant STORM_MALE;
    public static final DragonVariant STORM_RARE;
    public static final DragonVariant SUNLIGHT_FEMALE;
    public static final DragonVariant SUNLIGHT_MALE;
    public static final DragonVariant TERRA_FEMALE;
    public static final DragonVariant TERRA_MALE;
    public static final DragonVariant WATER_FEMALE;
    public static final DragonVariant WATER_MALE;
    public static final DragonVariant WITHER;
    public static final DragonVariant ZOMBIE;

    static DragonVariant make(Function<String, VariantAppearance> supplier, DragonType type, String name) {
        return new DragonVariant(type, makeId(name), supplier.apply(name), variant -> {
            var full = name + "_dragon_head_wall";
            var base = makeId(full.substring(0, full.length() - 5));
            var itemKey = ResourceKey.create(Registries.ITEM, base);
            var wallKey = makeKey(Registries.BLOCK, full);
            var standingKey = ResourceKey.create(Registries.BLOCK, base);
            DragonHeadStandingBlock standing;
            DragonHeadWallBlock wall;
            return new DragonHeadBlock.Holder(
                    registerBlock(standingKey, standing = new DragonHeadStandingBlock(
                            variant,
                            BlockBehaviour.Properties.of()
                                    .setId(standingKey)
                                    .strength(1.0F)
                                    .instrument(NoteBlockInstrument.DRAGON)
                                    .pushReaction(PushReaction.DESTROY)
                    )),
                    registerBlock(wallKey, wall = new DragonHeadWallBlock(
                            variant,
                            BlockBehaviour.Properties.ofFullCopy(standing).setId(wallKey)
                    )),
                    DMItemGroups.DRAGON_HEADS.register(itemKey, new DragonHeadItem(
                            variant,
                            standing,
                            wall,
                            new Item.Properties()
                                    .setId(itemKey)
                                    .rarity(Rarity.UNCOMMON)
                                    .overrideDescription(DragonHeadBlock.TRANSLATION_KEY)
                    ))
            );
        });
    }

    static {
        Function<String, VariantAppearance> supplier = PlatformCompat.isClientSide()
                ? VariantAppearances.getSupplier()
                : ignored -> null;
        var variants = ImmutableList.<DragonVariant>builderWithExpectedSize(37);
        variants.add(AETHER_FEMALE = make(supplier, DragonTypes.AETHER, "aether_female"));
        variants.add(AETHER_MALE = make(supplier, DragonTypes.AETHER, "aether_male"));
        variants.add(DARK_FEMALE = make(supplier, DragonTypes.DARK, "dark_female"));
        variants.add(DARK_MALE = make(supplier, DragonTypes.DARK, "dark_male"));
        variants.add(ENCHANTED_FEMALE = make(supplier, DragonTypes.ENCHANTED, "enchanted_female"));
        variants.add(ENCHANTED_MALE = make(supplier, DragonTypes.ENCHANTED, "enchanted_male"));
        variants.add(ENDER_FEMALE = make(supplier, DragonTypes.ENDER, "ender_female"));
        variants.add(ENDER_MALE = make(supplier, DragonTypes.ENDER, "ender_male"));
        variants.add(ENDER_RARE = make(supplier, DragonTypes.ENDER, "ender_rare"));
        variants.add(FIRE_FEMALE = make(supplier, DragonTypes.FIRE, "fire_female"));
        variants.add(FIRE_MALE = make(supplier, DragonTypes.FIRE, "fire_male"));
        variants.add(FIRE_RARE = make(supplier, DragonTypes.FIRE, "fire_rare"));
        variants.add(FOREST_FEMALE = make(supplier, DragonTypes.FOREST, "forest_female"));
        variants.add(FOREST_MALE = make(supplier, DragonTypes.FOREST, "forest_male"));
        variants.add(FOREST_DRY_FEMALE = make(supplier, DragonTypes.FOREST, "forest_dry_female"));
        variants.add(FOREST_DRY_MALE = make(supplier, DragonTypes.FOREST, "forest_dry_male"));
        variants.add(FOREST_TAIGA_FEMALE = make(supplier, DragonTypes.FOREST, "forest_taiga_female"));
        variants.add(FOREST_TAIGA_MALE = make(supplier, DragonTypes.FOREST, "forest_taiga_male"));
        variants.add(ICE_FEMALE = make(supplier, DragonTypes.ICE, "ice_female"));
        variants.add(ICE_MALE = make(supplier, DragonTypes.ICE, "ice_male"));
        variants.add(MOONLIGHT_FEMALE = make(supplier, DragonTypes.MOONLIGHT, "moonlight_female"));
        variants.add(MOONLIGHT_MALE = make(supplier, DragonTypes.MOONLIGHT, "moonlight_male"));
        variants.add(NETHER_FEMALE = make(supplier, DragonTypes.NETHER, "nether_female"));
        variants.add(NETHER_MALE = make(supplier, DragonTypes.NETHER, "nether_male"));
        variants.add(SCULK = make(supplier, DragonTypes.SCULK, "sculk"));
        variants.add(SKELETON = make(supplier, DragonTypes.SKELETON, "skeleton"));
        variants.add(STORM_FEMALE = make(supplier, DragonTypes.STORM, "storm_female"));
        variants.add(STORM_MALE = make(supplier, DragonTypes.STORM, "storm_male"));
        variants.add(STORM_RARE = make(supplier, DragonTypes.STORM, "storm_rare"));
        variants.add(SUNLIGHT_FEMALE = make(supplier, DragonTypes.SUNLIGHT, "sunlight_female"));
        variants.add(SUNLIGHT_MALE = make(supplier, DragonTypes.SUNLIGHT, "sunlight_male"));
        variants.add(TERRA_FEMALE = make(supplier, DragonTypes.TERRA, "terra_female"));
        variants.add(TERRA_MALE = make(supplier, DragonTypes.TERRA, "terra_male"));
        variants.add(WATER_FEMALE = make(supplier, DragonTypes.WATER, "water_female"));
        variants.add(WATER_MALE = make(supplier, DragonTypes.WATER, "water_male"));
        variants.add(WITHER = make(supplier, DragonTypes.WITHER, "wither"));
        variants.add(ZOMBIE = make(supplier, DragonTypes.ZOMBIE, "zombie"));
        BUILTIN_VALUES = variants.build();
    }

    public static void init() {}
}
