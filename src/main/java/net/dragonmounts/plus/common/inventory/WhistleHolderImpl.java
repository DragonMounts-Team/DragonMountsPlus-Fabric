package net.dragonmounts.plus.common.inventory;

import net.dragonmounts.plus.common.capability.WhistleHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WhistleHolderImpl implements WhistleHolder {
    public static WhistleHolderImpl of(@NotNull ItemStack whistle) {
        var holder = new WhistleHolderImpl();
        holder.setWhistle(whistle);
        return holder;
    }

    private @NotNull ItemStack whistle = ItemStack.EMPTY;

    @Override
    public ItemStack getWhistle() {
        return this.whistle;
    }

    @Override
    public void setWhistle(@NotNull ItemStack whistle) {
        this.whistle = whistle;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.whistle.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.whistle;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (this.whistle.isEmpty()) return ItemStack.EMPTY;
        ItemStack result = this.whistle.split(amount);
        if (!result.isEmpty()) {
            this.setChanged();
        }
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = this.whistle;
        this.whistle = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.whistle = stack;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setChanged() {}

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void stopOpen(Player player) {
        if (this.whistle.isEmpty()) return;
        if (!player.addItem(this.whistle)) {
            player.drop(this.whistle, false);
        }
        this.whistle = ItemStack.EMPTY;
    }

    @Override
    public void clearContent() {
        this.setWhistle(ItemStack.EMPTY);
    }
}
