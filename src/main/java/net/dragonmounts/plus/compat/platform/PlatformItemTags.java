package net.dragonmounts.plus.compat.platform;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public interface PlatformItemTags {
    TagKey<Item> WOODEN_CHESTS = ConventionalItemTags.WOODEN_CHESTS;
}
