package net.dragonmounts.plus.common.item;

import net.dragonmounts.plus.common.init.DMEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;

import static net.minecraft.resources.ResourceLocation.withDefaultNamespace;

/**
 * @see net.minecraft.world.item.AnimalArmorItem
 */
public class DragonArmorItem extends Item {
    /**
     * @see ArmorMaterial@createAttributes(ArmorType)
     */
    public static Properties applyMaterial(Properties props, ArmorMaterial material) {
        var builder = ItemAttributeModifiers.builder();
        var group = EquipmentSlotGroup.bySlot(ArmorType.BODY.getSlot());
        var name = withDefaultNamespace("armor." + ArmorType.BODY.getName());
        builder.add(Attributes.ARMOR, new AttributeModifier(name, material.defense().getOrDefault(ArmorType.BODY, 0), AttributeModifier.Operation.ADD_VALUE), group);
        builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(name, material.toughness(), AttributeModifier.Operation.ADD_VALUE), group);
        if (material.knockbackResistance() > 0.0F) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(name, material.knockbackResistance(), AttributeModifier.Operation.ADD_VALUE), group);
        }
        return props.attributes(builder.build());
    }

    public DragonArmorItem(ArmorMaterial material, Properties props) {
        super(applyMaterial(props, material).component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.BODY)
                .setEquipSound(SoundEvents.HORSE_ARMOR)
                .setAsset(material.assetId())
                .setAllowedEntities(DMEntities.TAMEABLE_DRAGON.get())
                .setDamageOnHurt(false)
                .build()
        ));
    }
}
