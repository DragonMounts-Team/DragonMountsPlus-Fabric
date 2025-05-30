package net.dragonmounts.plus.common.item;

import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ShieldItem;

import static net.dragonmounts.plus.common.DragonMountsShared.ITEM_TRANSLATION_KEY_PREFIX;

public class DragonScaleShieldItem extends ShieldItem implements DragonTypified {
    public static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_shield";
    public static final int UNIT_DURABILITY = 50;
    public final DragonType type;

    public DragonScaleShieldItem(DragonType type, Properties props) {
        super(props.component(DMDataComponents.DRAGON_TYPE, type)
                .durability(UNIT_DURABILITY * type.material.durability())
                .repairable(type.material.repairIngredient())
                .equippableUnswappable(EquipmentSlot.OFFHAND)
        );
        this.type = type;
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
