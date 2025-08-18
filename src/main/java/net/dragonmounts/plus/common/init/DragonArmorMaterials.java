package net.dragonmounts.plus.common.init;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.*;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.minecraft.resources.ResourceLocation.withDefaultNamespace;

public class DragonArmorMaterials {
    private static final Object2ObjectOpenHashMap<ResourceKey<EquipmentAsset>, ResourceLocation> TEXTURES = new Object2ObjectOpenHashMap<>();
    public static final String TEXTURE_PREFIX = "textures/entity/equipment/dragon_body/";
    public static final ArmorMaterial COPPER;
    public static final ArmorMaterial IRON = makeMaterial(ArmorMaterials.IRON, 3, makeId(TEXTURE_PREFIX + "iron.png"));
    public static final ArmorMaterial GOLD = makeMaterial(ArmorMaterials.GOLD, 5, makeId(TEXTURE_PREFIX + "gold.png"));
    public static final ArmorMaterial EMERALD;
    public static final ArmorMaterial DIAMOND = makeMaterial(ArmorMaterials.DIAMOND, 9, makeId(TEXTURE_PREFIX + "diamond.png"));
    public static final ArmorMaterial NETHERITE = makeMaterial(ArmorMaterials.NETHERITE, 11, makeId(TEXTURE_PREFIX + "netherite.png"));

    static {
        var asset = ResourceKey.create(EquipmentAssets.ROOT_ID, withDefaultNamespace("copper"));
        bindTexture(asset, makeId(TEXTURE_PREFIX + "copper.png"));
        var defense = new EnumMap<ArmorType, Integer>(ArmorType.class);
        defense.put(ArmorType.BOOTS, 1);
        defense.put(ArmorType.LEGGINGS, 3);
        defense.put(ArmorType.CHESTPLATE, 4);
        defense.put(ArmorType.HELMET, 2);
        defense.put(ArmorType.BODY, 2);
        COPPER = new ArmorMaterial(11, defense, 8, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, ItemTags.REPAIRS_GOLD_ARMOR, asset);

    }

    static {
        var asset = ResourceKey.create(EquipmentAssets.ROOT_ID, withDefaultNamespace("emerald"));
        bindTexture(asset, makeId(TEXTURE_PREFIX + "emerald.png"));
        var base = ArmorMaterials.DIAMOND;
        var defense = new EnumMap<>(base.defense());
        defense.put(ArmorType.BODY, 6);
        EMERALD = new ArmorMaterial(
                base.durability(),
                defense,
                base.enchantmentValue(),
                base.equipSound(),
                base.toughness(),
                base.knockbackResistance(),
                base.repairIngredient(),
                asset
        );
    }

    public static ArmorMaterial makeMaterial(ArmorMaterial base, int defense, ResourceLocation texture) {
        var asset = base.assetId();
        bindTexture(asset, texture);
        var copy = new EnumMap<>(base.defense());
        copy.put(ArmorType.BODY, defense);
        return new ArmorMaterial(
                base.durability(),
                copy,
                base.enchantmentValue(),
                base.equipSound(),
                base.toughness(),
                base.knockbackResistance(),
                base.repairIngredient(),
                asset
        );
    }

    public static void bindTexture(ResourceKey<EquipmentAsset> asset, ResourceLocation texture) {
        if (TEXTURES.putIfAbsent(asset, texture) != null) {
            throw new IllegalStateException("Duplicate key: " + asset);
        }
    }

    public static @Nullable ResourceLocation getTexture(ResourceKey<EquipmentAsset> asset) {
        return TEXTURES.get(asset);
    }
}
