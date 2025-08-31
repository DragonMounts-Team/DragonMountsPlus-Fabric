package net.dragonmounts.plus.common.init;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

import static net.minecraft.resources.ResourceLocation.withDefaultNamespace;

public class DragonArmorMaterials {
    public static final ArmorMaterial COPPER;
    public static final ArmorMaterial IRON = makeMaterial(ArmorMaterials.IRON, 3);
    public static final ArmorMaterial GOLD = makeMaterial(ArmorMaterials.GOLD, 5);
    public static final ArmorMaterial EMERALD;
    public static final ArmorMaterial DIAMOND = makeMaterial(ArmorMaterials.DIAMOND, 9);
    public static final ArmorMaterial NETHERITE = makeMaterial(ArmorMaterials.NETHERITE, 11);

    static {
        var defense = new EnumMap<ArmorType, Integer>(ArmorType.class);
        defense.put(ArmorType.BOOTS, 1);
        defense.put(ArmorType.LEGGINGS, 3);
        defense.put(ArmorType.CHESTPLATE, 4);
        defense.put(ArmorType.HELMET, 2);
        defense.put(ArmorType.BODY, 2);
        COPPER = new ArmorMaterial(
                11,
                defense,
                8,
                SoundEvents.ARMOR_EQUIP_GENERIC,
                0.0F,
                0.0F,
                ItemTags.REPAIRS_CHAIN_ARMOR,
                ResourceKey.create(EquipmentAssets.ROOT_ID, withDefaultNamespace("copper"))
        );
    }

    static {
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
                ResourceKey.create(EquipmentAssets.ROOT_ID, withDefaultNamespace("emerald"))
        );
    }

    public static ArmorMaterial makeMaterial(ArmorMaterial base, int defense) {
        var asset = base.assetId();
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


}
