package net.dragonmounts.plus.common.component.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dragonmounts.plus.compat.registry.ArmorEffect;

public record ArmorEffectEntry(ArmorEffect effect, int level) {
    public static final Codec<ArmorEffectEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ArmorEffect.REGISTRY.byNameCodec().fieldOf("effect").forGetter(ArmorEffectEntry::effect),
            Codec.INT.optionalFieldOf("level", 1).forGetter(ArmorEffectEntry::level)
    ).apply(instance, ArmorEffectEntry::new));
}