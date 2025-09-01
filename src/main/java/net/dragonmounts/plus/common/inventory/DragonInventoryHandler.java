package net.dragonmounts.plus.common.inventory;

import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.dragonmounts.plus.compat.platform.DMScreenHandlers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.inventory.DragonInventory.*;

public class DragonInventoryHandler extends AbstractContainerMenu {
    protected static final int LOGICAL_SIZE = INVENTORY_SIZE + 1;
    protected static final int PLAYER_INVENTORY_SIZE = LOGICAL_SIZE + 27;
    protected static final int PLAYER_HOTBAR_SIZE = PLAYER_INVENTORY_SIZE + 9;
    protected final DragonInventory inventory;
    public final TameableDragonEntity dragon;
    public final FluteSlot flute;
    public final Player player;
    public final DataSlot sitting;

    public DragonInventoryHandler(int id, Inventory playerInventory, TameableDragonEntity dragon) {
        super(DMScreenHandlers.DRAGON_INVENTORY, id);
        var inventory = this.inventory = (this.dragon = dragon).inventory;
        inventory.startOpen(this.player = playerInventory.player);
        this.addSlot(this.flute = new FluteSlot(this, 8, 8));
        this.addSlot(new ArmorSlot(inventory, SLOT_ARMOR_INDEX, 156, 36));
        this.addSlot(new ChestSlot(inventory, SLOT_CHEST_INDEX, 156, 54));
        this.addSlot(new SaddleSlot(inventory, SLOT_SADDLE_INDEX, 156, 18));
        for (int i = 0; i < 3; ++i) {
            for (int j = 3, y = i * 18 + 75; j < 12; ++j) {
                this.addSlot(new InventorySlot(inventory, j + i * 9, j * 18 + 102, y));
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
        this.sitting = this.addDataSlot(dragon.level().isClientSide ? DataSlot.standalone() : new SittingState(dragon));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var slot = this.getSlot(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem(), copy = stack.copy();
            if (index < LOGICAL_SIZE) {
                if (!this.moveItemStackTo(stack, LOGICAL_SIZE, this.slots.size(), true)) return ItemStack.EMPTY;
            } else if (canPlaceAt(this, 3, stack)) {
                if (!this.moveItemStackTo(stack, 3, 4, false)) return ItemStack.EMPTY;
            } else if (canPlaceAt(this, 2, stack)) {
                if (!this.moveItemStackTo(stack, 2, 3, false)) return ItemStack.EMPTY;
            } else if (canPlaceAt(this, 1, stack)) {
                if (!this.moveItemStackTo(stack, 1, 2, false)) return ItemStack.EMPTY;
            } else if (canPlaceAt(this, 0, stack) && allowFastBindOrRename(stack, this.dragon)) {
                if (!this.moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
            } else if (!this.dragon.hasChest() || !this.moveItemStackTo(stack, 4, LOGICAL_SIZE, false)) {
                if (index >= PLAYER_INVENTORY_SIZE) {
                    this.moveItemStackTo(stack, LOGICAL_SIZE, PLAYER_INVENTORY_SIZE, false);
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

    /// @see AbstractContainerMenu#clearContainer(Player, Container)
    @Override
    public void removed(Player player) {
        super.removed(player);
        if (player.isRemoved() && player.getRemovalReason() != Entity.RemovalReason.CHANGED_DIMENSION) {
            player.drop(this.flute.takeItem(player), false);
        } else if (player instanceof ServerPlayer) {
            if (((ServerPlayer) player).hasDisconnected()) {
                player.drop(this.flute.takeItem(player), false);
            } else {
                player.getInventory().placeItemBackInInventory(this.flute.takeItem(player));
            }
        }
        this.inventory.stopOpen(player);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    public static class SaddleSlot extends Slot {
        public static final ResourceLocation ICON = ResourceLocation.withDefaultNamespace("container/slot/saddle");
        public final TameableDragonEntity dragon;

        public SaddleSlot(DragonInventory inventory, int slot, int x, int y) {
            super(inventory, slot, x, y);
            this.dragon = inventory.dragon;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return isDragonSaddle(stack);
        }

        @Override
        public boolean mayPickup(Player player) {
            return !this.dragon.hasControllingPassenger();
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

    public static class ArmorSlot extends Slot {
        public static final ResourceLocation ICON = makeId("slot/dragon_armor");

        public ArmorSlot(DragonInventory inventory, int slot, int x, int y) {
            super(inventory, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return isDragonArmor(stack);
        }

        @Override
        public boolean mayPickup(Player player) {
            var stack = this.getItem();
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

    public static class ChestSlot extends Slot {
        public static final ResourceLocation ICON = makeId("slot/chest");

        public ChestSlot(DragonInventory inventory, int slot, int x, int y) {
            super(inventory, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return isChest(stack);
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

    public static class InventorySlot extends Slot {
        public final TameableDragonEntity dragon;

        public InventorySlot(DragonInventory inventory, int slot, int x, int y) {
            super(inventory, slot, x, y);
            this.dragon = inventory.dragon;
        }

        @Override
        public boolean isActive() {
            return this.hasItem() || this.dragon.hasChest();
        }
    }

    public static class SittingState extends DataSlot {
        public final TameableDragonEntity dragon;

        public SittingState(TameableDragonEntity dragon) {
            this.dragon = dragon;
        }

        @Override
        public int get() {
            return this.dragon.isOrderedToSit() ? 1 : 0;
        }

        @Override
        public void set(int value) {}
    }

    public static boolean canPlaceAt(AbstractContainerMenu menu, int index, ItemStack stack) {
        var slot = menu.getSlot(index);
        return !slot.hasItem() && slot.mayPlace(stack);
    }

    public static boolean allowFastBindOrRename(ItemStack stack, TameableDragonEntity dragon) {
        var sound = stack.get(DMDataComponents.FLUTE_SOUND);
        return sound == null || sound.dragon().equals(dragon.getUUID());
    }
}
