package net.dragonmounts.plus.common.util;

import net.dragonmounts.plus.common.api.ArmorEffectSource;
import net.dragonmounts.plus.common.api.DescribedArmorEffect;
import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.item.DragonScaleArmorItem;
import net.dragonmounts.plus.compat.registry.ArmorEffectSourceType;
import net.dragonmounts.plus.compat.registry.DeferredItem;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static net.dragonmounts.plus.common.DragonMountsShared.ITEM_TRANSLATION_KEY_PREFIX;

public record DragonScaleArmorSuit(
        DragonType type,
        DescribedArmorEffect effect,
        DeferredItem<DragonScaleArmorItem> helmet,
        DeferredItem<DragonScaleArmorItem> chestplate,
        DeferredItem<DragonScaleArmorItem> leggings,
        DeferredItem<DragonScaleArmorItem> boots
) implements DragonTypified, ArmorEffectSource {
    public static final String HELMET_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_helmet";
    public static final String CHESTPLATE_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_chestplate";
    public static final String LEGGINGS_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_leggings";
    public static final String BOOTS_TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_boots";

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
}
