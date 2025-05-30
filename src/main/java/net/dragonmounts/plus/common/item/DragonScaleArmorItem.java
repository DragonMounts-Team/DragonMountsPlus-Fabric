package net.dragonmounts.plus.common.item;

import net.dragonmounts.plus.common.api.DescribedArmorEffect;
import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Optional;

public class DragonScaleArmorItem extends ArmorItem implements DragonTypified {
    public final DragonType type;
    public final DescribedArmorEffect effect;

    public DragonScaleArmorItem(DragonType type, DescribedArmorEffect effect, ArmorType slot, Properties props) {
        super(type.material, slot, props.component(DMDataComponents.DRAGON_TYPE, type));
        this.type = type;
        this.effect = effect;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.ofNullable(this.effect);
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}
