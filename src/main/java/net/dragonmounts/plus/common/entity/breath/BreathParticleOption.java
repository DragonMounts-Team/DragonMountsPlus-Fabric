package net.dragonmounts.plus.common.entity.breath;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dragonmounts.plus.common.init.DMParticles;
import net.dragonmounts.plus.compat.registry.DragonVariant;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record BreathParticleOption(DragonVariant variant, BreathPower power) implements ParticleOptions {
    public static final MapCodec<BreathParticleOption> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            DragonVariant.CODEC.fieldOf("variant").forGetter(BreathParticleOption::variant),
            BreathPower.CODEC.fieldOf("power").forGetter(BreathParticleOption::power)
    ).apply(instance, BreathParticleOption::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BreathParticleOption> STREAM_CODEC = StreamCodec.composite(
            DragonVariant.STREAM_CODEC,
            BreathParticleOption::variant,
            BreathPower.STREAM_CODEC,
            BreathParticleOption::power,
            BreathParticleOption::new
    );

    @Override
    public ParticleType<?> getType() {
        return DMParticles.DRAGON_BREATH;
    }
}
