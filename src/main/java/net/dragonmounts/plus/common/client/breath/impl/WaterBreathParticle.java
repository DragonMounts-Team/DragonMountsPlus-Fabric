package net.dragonmounts.plus.common.client.breath.impl;

import net.dragonmounts.plus.common.client.breath.BreathParticle;
import net.dragonmounts.plus.common.client.breath.BreathParticleFactory;
import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleTypes;

public class WaterBreathParticle extends BreathParticle {
    public static final BreathParticleFactory FACTORY = WaterBreathParticle::new;

    public WaterBreathParticle(BreathParticleOption option, TextureAtlasSprite sprite, ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(option, sprite, level, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    protected void tickIfAlive() {
        if (this.random.nextFloat() <= NORMAL_PARTICLE_CHANCE && this.random.nextFloat() < this.node.getLifetimeFraction()) {
            this.level.addParticle(
                    ParticleTypes.SPLASH,
                    this.x + (this.random.nextFloat() * 2.0F - 1.0F) * this.bbWidth * 0.5F,
                    this.y + 0.8F,
                    this.z + (this.random.nextFloat() * 2.0F - 1.0F) * this.bbWidth * 0.5F,
                    this.xd,
                    this.yd,
                    this.zd
            );
        }
    }
}
