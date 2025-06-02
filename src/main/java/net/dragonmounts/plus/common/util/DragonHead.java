package net.dragonmounts.plus.common.util;

import net.dragonmounts.plus.common.block.DragonHeadStandingBlock;
import net.dragonmounts.plus.common.block.DragonHeadWallBlock;
import net.dragonmounts.plus.common.item.DragonHeadItem;
import net.dragonmounts.plus.compat.registry.DeferredBlock;
import net.dragonmounts.plus.compat.registry.DeferredItem;
import net.dragonmounts.plus.compat.registry.DragonVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public final class DragonHead implements ItemLike {
    public final DragonVariant variant;
    public final DeferredBlock<DragonHeadStandingBlock> standing;
    public final DeferredBlock<DragonHeadWallBlock> wall;
    public final DeferredItem<DragonHeadItem> item;

    public DragonHead(
            DragonVariant variant,
            String standingName,
            String wallName,
            BiFunction<DragonHead, String, DeferredBlock<DragonHeadStandingBlock>> standing,
            BiFunction<DragonHead, String, DeferredBlock<DragonHeadWallBlock>> wall,
            BiFunction<DragonHead, String, DeferredItem<DragonHeadItem>> item
    ) {
        this.variant = variant;
        this.standing = standing.apply(this, standingName);
        this.wall = wall.apply(this, wallName);
        this.item = item.apply(this, standingName);
    }

    @Override
    public @NotNull Item asItem() {
        return this.item.get();
    }
}
