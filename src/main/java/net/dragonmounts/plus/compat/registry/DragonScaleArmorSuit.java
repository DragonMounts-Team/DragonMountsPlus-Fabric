package net.dragonmounts.plus.compat.registry;

import net.dragonmounts.plus.common.api.ArmorEffectSource;
import net.dragonmounts.plus.common.api.DescribedArmorEffect;
import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.item.DragonScaleArmorItem;
import net.dragonmounts.plus.common.util.ArmorSuitInfo;
import net.dragonmounts.plus.common.util.ItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Objects;

import static net.dragonmounts.plus.common.DragonMountsShared.ITEM_TRANSLATION_KEY_PREFIX;
import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public final class DragonScaleArmorSuit implements DragonTypified, ArmorEffectSource {
    public static final String HELMET_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_helmet";
    public static final String CHESTPLATE_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_chestplate";
    public static final String LEGGINGS_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_leggings";
    public static final String BOOTS_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_boots";

    public static DragonScaleArmorSuit makeSuit(
            DragonType type,
            DescribedArmorEffect effect,
            ItemGroup group,
            String helmet,
            String chestplate,
            String leggings,
            String boots,
            ArmorSuitInfo.Factory<DragonScaleArmorSuit, DragonScaleArmorItem> factory
    ) {
        var registry = Registries.ITEM;
        var suit = new DragonScaleArmorSuit(new ArmorSuitInfo<>(
                makeKey(registry, helmet),
                makeKey(registry, chestplate),
                makeKey(registry, leggings),
                makeKey(registry, boots),
                factory
        ), type, effect);
        type.bindInstance(DragonScaleArmorSuit.class, suit);
        group.add(suit::getHelmet);
        group.add(suit::getChestplate);
        group.add(suit::getLeggings);
        group.add(suit::getBoots);
        return suit;
    }

    public final DragonType type;
    public final DescribedArmorEffect effect;
    public final ArmorSuitInfo<DragonScaleArmorSuit, DragonScaleArmorItem> info;
    private final DragonScaleArmorItem helmet;
    private final DragonScaleArmorItem chestplate;
    private final DragonScaleArmorItem leggings;
    private final DragonScaleArmorItem boots;

    public DragonScaleArmorSuit(
            ArmorSuitInfo<DragonScaleArmorSuit, DragonScaleArmorItem> info,
            DragonType type,
            DescribedArmorEffect effect
    ) {
        this.info = info;
        this.type = type;
        this.effect = effect;
        var factory = info.factory();
        var registry = BuiltInRegistries.ITEM;
        var key = info.helmet();
        this.helmet = Registry.register(registry, key, factory.makeArmor(this, ArmorType.HELMET, new Item.Properties().setId(key)));
        key = info.chestplate();
        this.chestplate = Registry.register(registry, key, factory.makeArmor(this, ArmorType.CHESTPLATE, new Item.Properties().setId(key)));
        key = info.leggings();
        this.leggings = Registry.register(registry, key, factory.makeArmor(this, ArmorType.LEGGINGS, new Item.Properties().setId(key)));
        key = info.boots();
        this.boots = Registry.register(registry, key, factory.makeArmor(this, ArmorType.BOOTS, new Item.Properties().setId(key)));
    }

    public DragonScaleArmorItem getHelmet() {
        return this.helmet;
    }

    public DragonScaleArmorItem getChestplate() {
        return this.chestplate;
    }

    public DragonScaleArmorItem getLeggings() {
        return this.leggings;
    }

    public DragonScaleArmorItem getBoots() {
        return this.boots;
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

    @Override
    public boolean equals(Object other) {
        return this == other || (
                other instanceof DragonScaleArmorSuit that && Objects.equals(this.type, that.type)
        );
    }

    @Override
    public int hashCode() {
        return this.type.hashCode();
    }
}
