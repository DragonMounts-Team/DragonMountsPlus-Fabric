package net.dragonmounts.plus.common.client.breath.impl;

import net.dragonmounts.plus.common.client.breath.BreathParticle;
import net.dragonmounts.plus.common.client.breath.BreathParticleFactory;
import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

public class NetherBreathParticle extends BreathParticle {
    public static final BreathParticleFactory FACTORY = NetherBreathParticle::new;

    public NetherBreathParticle(BreathParticleOption option, TextureAtlasSprite sprite, ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(option, sprite, level, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    protected ParticleOptions getChildParticle() {
        return this.random.nextFloat() <= SPECIAL_PARTICLE_CHANCE ? ParticleTypes.LAVA : ParticleTypes.LARGE_SMOKE;
    }
}
