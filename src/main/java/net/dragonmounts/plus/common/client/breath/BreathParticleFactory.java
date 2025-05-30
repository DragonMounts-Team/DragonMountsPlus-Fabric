package net.dragonmounts.plus.common.client.breath;

import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface BreathParticleFactory {
    Particle createParticle(
            BreathParticleOption option,
            TextureAtlasSprite sprite,
            ClientLevel level,
            double x,
            double y,
            double z,
            double motionX,
            double motionY,
            double motionZ
    );
}