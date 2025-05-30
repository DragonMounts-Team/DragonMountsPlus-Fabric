package net.dragonmounts.plus.common.block;

import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.world.level.block.Block;

import static net.dragonmounts.plus.common.DragonMountsShared.BLOCK_TRANSLATION_KEY_PREFIX;

public class DragonScaleBlock extends Block {
    public static final String TRANSLATION_KEY = BLOCK_TRANSLATION_KEY_PREFIX + "dragon_scale_block";
    public final DragonType type;

    public DragonScaleBlock(DragonType type, Properties props) {
        super(props);
        this.type = type;
    }
}
