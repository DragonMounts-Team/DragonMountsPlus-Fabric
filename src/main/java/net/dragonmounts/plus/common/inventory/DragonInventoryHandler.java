package net.dragonmounts.plus.common.inventory;

import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.compat.platform.DMScreenHandlers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.NotNull;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.inventory.DragonInventory.*;

public class DragonInventoryHandler extends AbstractContainerMenu {
    protected static final int PLAYER_INVENTORY_SIZE = INVENTORY_SIZE + 27;
    protected static final int PLAYER_HOTBAR_SIZE = PLAYER_INVENTORY_SIZE + 9;
    protected final DragonInventory inventory;
    public final TameableDragonEntity dragon;
    public final WhistleSlot whistle;
    public final Player player;

    public DragonInventoryHandler(int id, Inventory playerInventory, TameableDragonEntity dragon) {
        super(DMScreenHandlers.DRAGON_INVENTORY, id);
        DragonInventory dragonInventory = this.inventory = dragon.inventory;
        this.dragon = dragon;
        this.player = playerInventory.player;
        dragonInventory.startOpen(this.player);
        this.addSlot(this.whistle = new WhistleSlot(this, 8, 8));
        this.addSlot(new ArmorSlot(SLOT_ARMOR_INDEX, 156, 36));
        this.addSlot(new ChestSlot(SLOT_CHEST_INDEX, 156, 54));
        this.addSlot(new SaddleSlot(SLOT_SADDLE_INDEX, 156, 18));
        for (int i = 0; i < 3; ++i) {
            for (int j = 3, y = i * 18 + 75; j < 12; ++j) {
                this.addSlot(new InventorySlot(j + i * 9, j * 18 + 102, y));
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0, y = i * 18 + 142; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, j * 18 + 156, y));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, j * 18 + 156, 200));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        var slot = this.getSlot(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem(), copy = stack.copy();
            if (index < INVENTORY_SIZE) {
                if (!this.moveItemStackTo(stack, INVENTORY_SIZE, this.slots.size(), true)) return ItemStack.EMPTY;
            } else if (canPlaceAt(this, 3, stack)) {
                if (!this.moveItemStackTo(stack, 3, 4, false)) return ItemStack.EMPTY;
            } else if (canPlaceAt(this, 2, stack)) {
                if (!this.moveItemStackTo(stack, 2, 3, false)) return ItemStack.EMPTY;
            } else if (canPlaceAt(this, 1, stack)) {
                if (!this.moveItemStackTo(stack, 1, 2, false)) return ItemStack.EMPTY;
            } else if (canPlaceAt(this, 0, stack)) {
                if (!this.moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
            } else if (!this.dragon.hasChest() || !this.moveItemStackTo(stack, 3, INVENTORY_SIZE, false)) {
                if (index >= PLAYER_INVENTORY_SIZE) {
                    this.moveItemStackTo(stack, INVENTORY_SIZE, PLAYER_INVENTORY_SIZE, false);
                } else {
                    this.moveItemStackTo(stack, PLAYER_INVENTORY_SIZE, PLAYER_HOTBAR_SIZE, false);
                }
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            return copy;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    public class SaddleSlot extends Slot {
        public static final ResourceLocation ICON = ResourceLocation.withDefaultNamespace("container/slot/saddle");

        public SaddleSlot(int slot, int x, int y) {
            super(DragonInventoryHandler.this.inventory, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return DragonInventoryHandler.this.inventory.isSaddle(stack);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public ResourceLocation getNoItemIcon() {
            return ICON;
        }
    }

    public class ArmorSlot extends Slot {
        public static final ResourceLocation ICON = makeId("slot/dragon_armor");

        public ArmorSlot(int slot, int x, int y) {
            super(DragonInventoryHandler.this.inventory, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return TameableDragonEntity.isBodyArmorItem(stack);
        }

        @Override
        public boolean mayPickup(Player player) {
            ItemStack stack = this.getItem();
            return (stack.isEmpty() || player.isCreative() || !EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) && super.mayPickup(player);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public ResourceLocation getNoItemIcon() {
            return ICON;
        }
    }

    public class ChestSlot extends Slot {
        public static final ResourceLocation ICON = makeId("slot/chest");

        public ChestSlot(int slot, int x, int y) {
            super(DragonInventoryHandler.this.inventory, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return DragonInventoryHandler.this.inventory.isChest(stack);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public ResourceLocation getNoItemIcon() {
            return ICON;
        }
    }

    public class InventorySlot extends Slot {
        public InventorySlot(int slot, int x, int y) {
            super(DragonInventoryHandler.this.inventory, slot, x, y);
        }

        @Override
        public boolean isActive() {
            return DragonInventoryHandler.this.dragon.hasChest();
        }
    }

    public static boolean canPlaceAt(AbstractContainerMenu menu, int index, ItemStack stack) {
        var slot = menu.getSlot(index);
        return !slot.hasItem() && slot.mayPlace(stack);
    }
}
