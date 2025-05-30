package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.component.impl.ContorlGrowthConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerConsumeEffect;

public class DMConsumeEffects {
    public static final ConsumeEffect.Type<ContorlGrowthConsumeEffect> CONTROL_GROWTH = registerConsumeEffect(
            "control_growth",
            ContorlGrowthConsumeEffect.CODEC,
            ContorlGrowthConsumeEffect.STREAM_CODEC
    );

    public void init() {}
}
