package net.dragonmounts.plus.common.init;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.*;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class DragonArmorMaterials {
    private static final Object2ObjectOpenHashMap<ResourceKey<EquipmentAsset>, ResourceLocation> TEXTURES = new Object2ObjectOpenHashMap<>();
    public static final String TEXTURE_PREFIX = "textures/entity/equipment/dragon_body/";
    public static final ArmorMaterial IRON = makeMaterial(ArmorMaterials.IRON, makeAsset("iron"), 3, TEXTURE_PREFIX + "iron.png");
    public static final ArmorMaterial GOLD = makeMaterial(ArmorMaterials.GOLD, makeAsset("gold"), 5, TEXTURE_PREFIX + "gold.png");
    public static final ArmorMaterial EMERALD = makeMaterial(ArmorMaterials.DIAMOND, makeAsset("emerald"), 6, TEXTURE_PREFIX + "emerald.png");
    public static final ArmorMaterial DIAMOND = makeMaterial(ArmorMaterials.DIAMOND, makeAsset("diamond"), 9, TEXTURE_PREFIX + "diamond.png");
    public static final ArmorMaterial NETHERITE = makeMaterial(ArmorMaterials.NETHERITE, makeAsset("netherite"), 11, TEXTURE_PREFIX + "netherite.png");

    static ResourceKey<EquipmentAsset> makeAsset(String name) {
        return makeKey(EquipmentAssets.ROOT_ID, name);
    }

    public static ArmorMaterial makeMaterial(ArmorMaterial base, ResourceKey<EquipmentAsset> asset, int defense, String texture) {
        bindTexture(asset, asset.location().withPath(texture));
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
