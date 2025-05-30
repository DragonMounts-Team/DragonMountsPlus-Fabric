package net.dragonmounts.plus.common.util;

import net.dragonmounts.plus.common.api.ArmorEffectSource;
import net.dragonmounts.plus.common.api.DescribedArmorEffect;
import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.dragonmounts.plus.common.item.DragonScaleArmorItem;
import net.dragonmounts.plus.compat.registry.ArmorEffectSourceType;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

import static net.dragonmounts.plus.common.DragonMountsShared.ITEM_TRANSLATION_KEY_PREFIX;
import static net.minecraft.world.item.equipment.ArmorType.*;

public class DragonScaleArmorSuit implements DragonTypified, ArmorEffectSource {
    public static final String HELMET_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_helmet";
    public static final String CHESTPLATE_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_chestplate";
    public static final String LEGGINGS_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_leggings";
    public static final String BOOTS_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_boots";
    public final DescribedArmorEffect effect;
    public final DragonType type;
    public final DragonScaleArmorItem helmet;
    public final DragonScaleArmorItem chestplate;
    public final DragonScaleArmorItem leggings;
    public final DragonScaleArmorItem boots;

    public DragonScaleArmorSuit(
            DragonType type,
            DescribedArmorEffect effect,
            Item.Properties props,
            ResourceKey<Item> helmet,
            ResourceKey<Item> chestplate,
            ResourceKey<Item> leggings,
            ResourceKey<Item> boots,
            Factory factory
    ) {
        this.type = type;
        this.effect = effect;
        props.component(DMDataComponents.ARMOR_EFFECT_SOURCE, this);
        this.helmet = factory.makeArmor(type, effect, props, HELMET, helmet, HELMET_TRANSLATION_KEY);
        this.chestplate = factory.makeArmor(type, effect, props, CHESTPLATE, chestplate, CHESTPLATE_TRANSLATION_KEY);
        this.leggings = factory.makeArmor(type, effect, props, LEGGINGS, leggings, LEGGINGS_TRANSLATION_KEY);
        this.boots = factory.makeArmor(type, effect, props, BOOTS, boots, BOOTS_TRANSLATION_KEY);
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }

    @Override
    public void affect(ArmorEffectManager manager, Player player, ItemStack stack) {
        if (this.effect == null) return;
        manager.addLevel(this.effect, 1);
    }

    @Override
    public ArmorEffectSourceType<?> getType() {
        return ArmorEffectSourceType.BUILTIN;
    }

    @FunctionalInterface
    public interface Factory {
        DragonScaleArmorItem makeArmor(
                DragonType type,
                DescribedArmorEffect effect,
                Item.Properties props,
                ArmorType slot,
                ResourceKey<Item> key,
                String desc
        );
    }
}
