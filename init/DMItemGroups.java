package net.dragonmounts.plus.common.init;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.common.util.ItemCategory;
import net.dragonmounts.plus.common.util.ItemGroup;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class DMItemGroups {
    public static final ItemGroup DRAGON_EGGS = new ItemGroup(new ObjectArrayList<>(16));
    public static final ItemGroup DRAGON_HEADS = new ItemGroup(new ObjectArrayList<>(36));
    public static final ItemGroup DRAGON_SPAWN_EGGS = new ItemGroup(new ObjectArrayList<>(16));
    public static final ItemCategory BLOCK_TAB = new ItemCategory("block", List.of(DRAGON_EGGS, DRAGON_HEADS));
    public static final ItemCategory ITEM_TAB = new ItemCategory("items", Collections.singletonList(DRAGON_SPAWN_EGGS));
    public static final ItemCategory TOOL_TAB = new ItemCategory("tools", Collections.emptyList());

    public static void register(ItemCategory.Registry registry) {
        registry.register(BLOCK_TAB, "itemGroup.dragonmounts.plus.blocks", () -> new ItemStack(DMBlocks.ENDER_DRAGON_EGG));
        registry.register(ITEM_TAB, "itemGroup.dragonmounts.plus.items", () -> new ItemStack(DMItems.ENDER_DRAGON_SCALES.get()));
        registry.register(TOOL_TAB, "itemGroup.dragonmounts.plus.tools", () -> new ItemStack(DMItems.ENDER_DRAGON_SCALE_SWORD.get()));
    }
}
