package net.dragonmounts.plus.common.item;

import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonVariant;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;

public class DragonHeadItem extends StandingAndWallBlockItem implements DragonTypified {
    public final DragonVariant variant;

    public DragonHeadItem(DragonVariant variant, Block standing, Block wall, Properties props) {
        super(standing, wall, Direction.DOWN, props.component(DMDataComponents.DRAGON_TYPE, variant.type).equippableUnswappable(EquipmentSlot.HEAD));
        this.variant = variant;
    }

    @Override
    public DragonType getDragonType() {
        return this.variant.type;
    }
}
