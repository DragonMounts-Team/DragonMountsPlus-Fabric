package net.dragonmounts.plus.common.inventory;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public interface SlotListener<T extends Slot> {
    void beforePlaceItem(T slot, ItemStack stack);

    void afterTakeItem(T slot, ItemStack stack);
}
