package net.dragonmounts.plus.common.init;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.common.util.ItemCategory;
import net.dragonmounts.plus.common.util.ItemGroup;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class DMItemGroups {
    public static final ItemGroup DRAGON_EGGS = new ItemGroup(new ObjectArrayList<>(17));
    public static final ItemGroup DRAGON_HEADS = new ItemGroup(new ObjectArrayList<>(46));
    public static final ItemGroup DRAGON_SPAWN_EGGS = new ItemGroup(new ObjectArrayList<>(17));
    public static final ItemCategory BLOCK_TAB = new ItemCategory("blocks", List.of(DRAGON_EGGS, DRAGON_HEADS));
    public static final ItemCategory MISC_TAB = new ItemCategory("misc", Collections.singletonList(DRAGON_SPAWN_EGGS));
    public static final ItemCategory TOOL_TAB = new ItemCategory("tools", Collections.emptyList());
    public static final ItemCategory COMBAT_TAB = new ItemCategory("combat", Collections.emptyList());

    public static void register(ItemCategory.Registry registry) {
        registry.register(BLOCK_TAB, "itemGroup.dragonmounts.plus.blocks", () -> new ItemStack(DMBlocks.ENDER_DRAGON_EGG));
        registry.register(MISC_TAB, "itemGroup.dragonmounts.plus.misc", () -> new ItemStack(DMItems.ENDER_DRAGON_SCALES));
        registry.register(TOOL_TAB, "itemGroup.dragonmounts.plus.tools", () -> new ItemStack(DMItems.AMULET));
        registry.register(COMBAT_TAB, "itemGroup.dragonmounts.plus.combat", () -> new ItemStack(DMItems.ENDER_DRAGON_SCALE_SWORD));
    }
}
