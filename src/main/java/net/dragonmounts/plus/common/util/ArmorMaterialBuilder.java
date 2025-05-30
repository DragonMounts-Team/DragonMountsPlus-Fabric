package net.dragonmounts.plus.common.util;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.EnumMap;

public class ArmorMaterialBuilder {
    public final EnumMap<ArmorType, Integer> defense = new EnumMap<>(ArmorType.class);
    public int durabilityFactor;
    public int enchantmentValue = 1;
    public Holder<SoundEvent> sound = SoundEvents.ARMOR_EQUIP_GOLD;
    public float toughness = 0;
    public float knockbackResistance = 0;

    public ArmorMaterialBuilder(int durabilityFactor) {
        this.setDurabilityFactor(durabilityFactor).setDefense(ArmorType.BODY, 11);
    }

    public ArmorMaterialBuilder setDurabilityFactor(int durabilityFactor) {
        this.durabilityFactor = durabilityFactor;
        return this;
    }

    public ArmorMaterialBuilder setDefense(ArmorType type, int defense) {
        this.defense.put(type, defense);
        return this;
    }

    public ArmorMaterialBuilder setEnchantmentValue(int enchantmentValue) {
        this.enchantmentValue = enchantmentValue;
        return this;
    }

    public ArmorMaterialBuilder setSound(Holder<SoundEvent> sound) {
        this.sound = sound;
        return this;
    }

    public ArmorMaterialBuilder setToughness(float toughness) {
        this.toughness = toughness;
        return this;
    }

    public ArmorMaterialBuilder setKnockbackResistance(float knockbackResistance) {
        this.knockbackResistance = knockbackResistance;
        return this;
    }

    public ArmorMaterial build(
            TagKey<Item> ingredient,
            ResourceKey<EquipmentAsset> asset
    ) {
        return new ArmorMaterial(
                this.durabilityFactor,
                new EnumMap<>(this.defense),
                this.enchantmentValue,
                this.sound,
                this.toughness,
                this.knockbackResistance,
                ingredient,
                asset
        );
    }
}
