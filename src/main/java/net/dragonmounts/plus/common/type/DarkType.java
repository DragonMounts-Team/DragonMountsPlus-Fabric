package net.dragonmounts.plus.common.type;

import net.dragonmounts.plus.common.entity.breath.DragonBreath;
import net.dragonmounts.plus.common.entity.breath.impl.DarkBreath;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class DarkType extends DragonType {
    public DarkType(ResourceLocation identifier, DragonTypeBuilder builder) {
        super(identifier, builder);
    }

    @Override
    public boolean isInHabitat(LivingEntity entity) {
        return entity.getY() > entity.level().getHeight() * 0.66;
    }

    @Override
    public DragonBreath initBreath(TameableDragonEntity dragon) {
        return new DarkBreath(dragon, 0.6F);
    }
}
