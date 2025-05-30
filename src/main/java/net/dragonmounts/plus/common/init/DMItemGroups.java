package net.dragonmounts.plus.common.init;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.compat.platform.ItemCategory;
import net.dragonmounts.plus.compat.platform.ItemGroup;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class DMItemGroups {
    public static final ItemGroup DRAGON_EGGS = new ItemGroup(new ObjectArrayList<>(16));
    public static final ItemGroup DRAGON_HEADS = new ItemGroup(new ObjectArrayList<>(36));
    public static final ItemGroup DRAGON_SPAWN_EGGS = new ItemGroup(new ObjectArrayList<>(16));
    public static final ItemCategory BLOCK_TAB = new ItemCategory(
            "block",
            "itemGroup.dragonmounts.plus.blocks",
            () -> new ItemStack(DMBlocks.ENDER_DRAGON_EGG),
            List.of(DRAGON_EGGS, DRAGON_HEADS)
    );
    public static final ItemCategory ITEM_TAB = new ItemCategory(
            "items",
            "itemGroup.dragonmounts.plus.items",
            () -> new ItemStack(DMItems.ENDER_DRAGON_SCALES),
            Collections.singletonList(DRAGON_SPAWN_EGGS)
    );
    public static final ItemCategory TOOL_TAB = new ItemCategory(
            "tools",
            "itemGroup.dragonmounts.plus.tools",
            () -> new ItemStack(DMItems.ENDER_DRAGON_SCALE_SWORD),
            Collections.emptyList()
    );

    public static void init() {}
}
