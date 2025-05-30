package net.dragonmounts.plus.common.type;

import net.dragonmounts.plus.common.entity.breath.DragonBreath;
import net.dragonmounts.plus.common.entity.breath.impl.StormBreath;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.resources.ResourceLocation;

public class StormType extends DragonType {
    public StormType(ResourceLocation identifier, DragonTypeBuilder builder) {
        super(identifier, builder);
    }

    @Override
    public DragonBreath initBreath(TameableDragonEntity dragon) {
        return new StormBreath(dragon, 0.7F);
    }
}
