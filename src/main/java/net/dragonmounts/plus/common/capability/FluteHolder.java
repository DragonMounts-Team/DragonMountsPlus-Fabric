package net.dragonmounts.plus.common.capability;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface FluteHolder extends Container {
    ItemStack getFlute();

    void setFlute(@NotNull ItemStack flute);
}
