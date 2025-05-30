package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.minecraft.core.particles.ParticleType;

import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerParticle;

public class DMParticles {
    public static final ParticleType<BreathParticleOption> DRAGON_BREATH =
            registerParticle("dragon_breath", false, BreathParticleOption.CODEC, BreathParticleOption.STREAM_CODEC);

    public static void init() {}
}
