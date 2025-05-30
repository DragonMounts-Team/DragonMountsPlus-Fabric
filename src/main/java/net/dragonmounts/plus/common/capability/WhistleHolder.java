package net.dragonmounts.plus.common.capability;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface WhistleHolder extends Container {
    ItemStack getWhistle();

    void setWhistle(@NotNull ItemStack whistle);
}
