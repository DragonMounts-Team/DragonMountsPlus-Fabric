package net.dragonmounts.plus.compat.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;

import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class EffectHolder<T extends MobEffect> extends ObjectHolder<T, MobEffect> {
    public static <T extends MobEffect> EffectHolder<T> registerMobEffect(String name, T effect) {
        return new EffectHolder<>(makeKey(Registries.MOB_EFFECT, name), effect);
    }

    public EffectHolder(ResourceKey<MobEffect> key, T effect) {
        super(BuiltInRegistries.MOB_EFFECT, key, effect);
    }
}
