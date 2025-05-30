package net.dragonmounts.plus.common.item;

import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.world.item.AxeItem;

import static net.dragonmounts.plus.common.DragonMountsShared.ITEM_TRANSLATION_KEY_PREFIX;

public class DragonScaleAxeItem extends AxeItem implements DragonTypified {
    public static final String TRANSLATION_KEY = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_axe";
    public final DragonType type;

    public DragonScaleAxeItem(DragonType type, float damage, float speed, Properties props) {
        super(type.tier, damage, speed, props.component(DMDataComponents.DRAGON_TYPE, type));
        this.type = type;
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}