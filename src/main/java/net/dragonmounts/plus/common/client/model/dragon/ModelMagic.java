package net.dragonmounts.plus.common.client.model.dragon;

import static net.dragonmounts.plus.common.client.model.dragon.DragonModel.HORN_THICK;
import static net.dragonmounts.plus.common.entity.dragon.DragonModelContracts.TAIL_SIZE;
import static net.dragonmounts.plus.common.util.math.MathUtil.TO_RAD_FACTOR;

public interface ModelMagic {
    float HALF_TAIL_SIZE = 0.5F * TAIL_SIZE;
    float TAIL_HORN_OFFSET = -0.5F * HORN_THICK;
    float TAIL_HORN_ROT_X = -15F * TO_RAD_FACTOR;
    float TAIL_HORN_ROT_Y = 35F * TO_RAD_FACTOR;
}
