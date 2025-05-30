package net.dragonmounts.plus.common.inventory;

import net.dragonmounts.plus.common.capability.WhistleHolder;
import net.dragonmounts.plus.common.component.WhistleSound;
import net.dragonmounts.plus.common.init.DMItems;
import net.dragonmounts.plus.compat.platform.DMAttachments;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public class WhistleSlot extends Slot {
    public static final ResourceLocation ICON = makeId("slot/whistle");
    public final WhistleHolder holder;
    public final DragonInventoryHandler inventory;
    public SlotListener<? super WhistleSlot> listener;
    public String desiredName;

    public WhistleSlot(
            DragonInventoryHandler handler,
            int x,
            int y
    ) {
        this(DMAttachments.getOrCreate(
                handler.player,
                DMAttachments.WHISTLE_HOLDER
        ), handler, x, y);
    }

    public WhistleSlot(
            WhistleHolder holder,
            DragonInventoryHandler handler,
            int x,
            int y
    ) {
        super(holder, 0, x, y);
        this.holder = holder;
        this.inventory = handler;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return !stack.isEmpty() && stack.is(DMItems.WHISTLE);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void set(ItemStack stack) {
        if (this.listener != null) {
            this.listener.beforePlaceItem(this, stack);
        }
        super.set(stack);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        if (this.listener != null) {
            this.listener.afterTakeItem(this, stack);
        }
        super.onTake(player, stack);
    }

    @Override
    public void setChanged() {
        var stack = this.getItem();
        if (!stack.isEmpty() && stack.is(DMItems.WHISTLE)) {
            var result = stack.copy();
            WhistleSound.bindWhistle(result, this.inventory.dragon, this.inventory.player);
            if (this.desiredName != null && !StringUtil.isBlank(this.desiredName)) {
                if (!this.desiredName.equals(getItemName(stack))) {
                    result.set(DataComponents.CUSTOM_NAME, Component.literal(this.desiredName));
                }
            } else if (stack.has(DataComponents.CUSTOM_NAME)) {
                result.remove(DataComponents.CUSTOM_NAME);
            }
            this.holder.setWhistle(result);
        }
        super.setChanged();
    }

    @Override
    public ResourceLocation getNoItemIcon() {
        return ICON;
    }

    public boolean applyName(String name) {
        var string = StringUtil.filterText(name);
        if (string.length() > 50 || string.equals(this.desiredName)) return false;
        this.desiredName = string;
        var stack = this.getItem();
        if (!stack.isEmpty()) {
            if (StringUtil.isBlank(string)) {
                stack.remove(DataComponents.CUSTOM_NAME);
            } else {
                stack.set(DataComponents.CUSTOM_NAME, Component.literal(string));
            }
        }
        this.setChanged();
        return true;
    }

    public static String getItemName(ItemStack stack) {
        return stack.getHoverName().getString();
    }
}
