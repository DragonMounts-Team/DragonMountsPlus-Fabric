package net.dragonmounts.plus.common.init;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.*;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class DragonArmorMaterials {
    public static final String TEXTURE_PREFIX = "textures/entity/equipment/dragon_body/";
    private static final Object2ObjectOpenHashMap<ResourceKey<EquipmentAsset>, ResourceLocation> TEXTURES = new Object2ObjectOpenHashMap<>();
    public static final ArmorMaterial IRON = makeMaterial(ArmorMaterials.IRON, makeAsset("iron"), TEXTURE_PREFIX + "iron.png");
    public static final ArmorMaterial GOLD = makeMaterial(ArmorMaterials.GOLD, makeAsset("gold"), TEXTURE_PREFIX + "gold.png");
    public static final ArmorMaterial DIAMOND;
    public static final ArmorMaterial EMERALD;
    public static final ArmorMaterial NETHERITE;

    static {
        var copy = new EnumMap<>(ArmorMaterials.DIAMOND.defense());
        copy.put(ArmorType.BODY, 11);
        DIAMOND = makeMaterial(ArmorMaterials.DIAMOND, makeAsset("diamond"), copy, TEXTURE_PREFIX + "diamond.png");
        EMERALD = makeMaterial(ArmorMaterials.DIAMOND, makeAsset("emerald"), copy, TEXTURE_PREFIX + "emerald.png");
        copy = new EnumMap<>(ArmorMaterials.NETHERITE.defense());
        copy.put(ArmorType.BODY, 15);
        NETHERITE = makeMaterial(ArmorMaterials.NETHERITE, makeAsset("netherite"), copy, TEXTURE_PREFIX + "netherite.png");
    }

    static ResourceKey<EquipmentAsset> makeAsset(String name) {
        return makeKey(EquipmentAssets.ROOT_ID, name);
    }

    static ArmorMaterial makeMaterial(ArmorMaterial base, ResourceKey<EquipmentAsset> asset, Map<ArmorType, Integer> defense, String texture) {
        bindTexture(asset, makeId(texture));
        return new ArmorMaterial(
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

    static ArmorMaterial makeMaterial(ArmorMaterial base, ResourceKey<EquipmentAsset> asset, String texture) {
        return makeMaterial(base, asset, base.defense(), texture);
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
