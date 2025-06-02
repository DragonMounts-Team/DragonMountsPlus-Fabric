package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.block.entity.DragonCoreBlockEntity;
import net.dragonmounts.plus.common.block.entity.DragonHeadBlockEntity;
import net.dragonmounts.plus.compat.registry.DeferredBlock;
import net.dragonmounts.plus.compat.registry.DeferredBlockEntity;

import static net.dragonmounts.plus.compat.registry.DeferredBlockEntity.registerBlockEntity;


public class DMBlockEntities {
    public static final DeferredBlockEntity<DragonCoreBlockEntity> DRAGON_CORE = registerBlockEntity(
            "dragon_core",
            DragonCoreBlockEntity::new,
            DMBlocks.DRAGON_CORE
    );
    public static final DeferredBlockEntity<DragonHeadBlockEntity> DRAGON_HEAD;

    static {
        var blocks = new DeferredBlock<?>[DragonVariants.BUILTIN_VALUES.size() << 1];
        int i = 0;
        for (var variant : DragonVariants.BUILTIN_VALUES) {
            var head = variant.head;
            blocks[i++] = head.standing;
            blocks[i++] = head.wall;
        }
        DRAGON_HEAD = registerBlockEntity("dragon_head", DragonHeadBlockEntity::new, blocks);
    }

    public static void init() {}
}
