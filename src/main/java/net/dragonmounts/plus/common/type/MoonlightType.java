package net.dragonmounts.plus.common.type;

import net.dragonmounts.plus.common.api.DragonTypified;
import net.dragonmounts.plus.common.entity.breath.DragonBreath;
import net.dragonmounts.plus.common.entity.breath.impl.MoonlightBreath;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.init.DragonTypes;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;

public class MoonlightType extends DragonType {
    public MoonlightType(ResourceLocation identifier, DragonTypeBuilder builder) {
        super(identifier, builder);
    }

    @Override
    public <T extends LivingEntity & DragonTypified.Mutable> void onThunderHit(T entity, LightningBolt bolt) {
        super.onThunderHit(entity, bolt);
        convertByLightning(entity, DragonTypes.DARK);
    }

    @Override
    public DragonBreath initBreath(TameableDragonEntity dragon) {
        return new MoonlightBreath(dragon, 0.7F);
    }
}
