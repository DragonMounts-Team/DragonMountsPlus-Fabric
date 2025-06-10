package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.effect.DarkDragonsGraceEffect;
import net.dragonmounts.plus.compat.registry.EffectHolder;
import net.minecraft.world.effect.MobEffectCategory;

import static net.dragonmounts.plus.compat.registry.EffectHolder.registerMobEffect;

public class DMMobEffects {
    public static final EffectHolder<DarkDragonsGraceEffect> DARK_DRAGONS_GRACE = registerMobEffect(
            "dark_dragons_grace",
            new DarkDragonsGraceEffect(MobEffectCategory.BENEFICIAL, 0x6908265)
    );

    public static void init() {}
}
