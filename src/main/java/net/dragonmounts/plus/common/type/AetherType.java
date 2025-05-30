package net.dragonmounts.plus.common.type;

import net.dragonmounts.plus.common.entity.breath.DragonBreath;
import net.dragonmounts.plus.common.entity.breath.impl.AetherBreath;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class AetherType extends DragonType {
    public AetherType(ResourceLocation identifier, DragonTypeBuilder builder) {
        super(identifier, builder);
    }

    @Override
    public boolean isInHabitat(LivingEntity entity) {
        return entity.getY() >= entity.level().getHeight() * 0.625;
    }

    @Override
    public DragonBreath initBreath(TameableDragonEntity dragon) {
        return new AetherBreath(dragon, 0.7F);
    }
}
