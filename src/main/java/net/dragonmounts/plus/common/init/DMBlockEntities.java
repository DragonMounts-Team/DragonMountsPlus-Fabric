package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.block.entity.DragonCoreBlockEntity;
import net.dragonmounts.plus.common.block.entity.DragonHeadBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerBlockEntity;

public class DMBlockEntities {
    public static final BlockEntityType<DragonCoreBlockEntity> DRAGON_CORE = registerBlockEntity(
            "dragon_core",
            DragonCoreBlockEntity::new,
            DMBlocks.DRAGON_CORE
    );
    public static final BlockEntityType<DragonHeadBlockEntity> DRAGON_HEAD;

    static {
        Block[] blocks = new Block[DragonVariants.BUILTIN_VALUES.size() << 1];
        int i = 0;
        for (var variant : DragonVariants.BUILTIN_VALUES) {
            var head = variant.head;
            blocks[i++] = head.standing();
            blocks[i++] = head.wall();
        }
        DRAGON_HEAD = registerBlockEntity(
                "dragon_head",
                DragonHeadBlockEntity::new,
                blocks
        );
    }

    public static void init() {}
}
