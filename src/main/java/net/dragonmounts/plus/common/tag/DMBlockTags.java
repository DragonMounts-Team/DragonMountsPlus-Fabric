package net.dragonmounts.plus.common.tag;


import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public interface DMBlockTags {
    TagKey<Block> DRAGON_EGGS = create("dragon_eggs");
    TagKey<Block> DRAGON_SCALE_BLOCKS = create("dragon_scale_blocks");
    TagKey<Block> AIRFLOW_DESTRUCTIBLE = create("airflow_destructible");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, makeId(name));
    }
}
