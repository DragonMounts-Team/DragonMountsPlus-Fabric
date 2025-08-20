package net.dragonmounts.plus.common.client.breath.impl;

import net.dragonmounts.plus.common.client.breath.BreathParticle;
import net.dragonmounts.plus.common.client.breath.BreathParticleFactory;
import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

public class FlameBreathParticle extends BreathParticle {
    public static final BreathParticleFactory FACTORY = FlameBreathParticle::new;

    public FlameBreathParticle(BreathParticleOption option, TextureAtlasSprite sprite, ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(option, sprite, level, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    public int getLightColor(float partialTick) {
        return LightTexture.FULL_BLOCK;
    }

    protected ParticleOptions getChildParticle() {
        return this.random.nextFloat() < SPECIAL_PARTICLE_CHANCE ? ParticleTypes.LARGE_SMOKE : ParticleTypes.SMOKE;
    }

    @Override
    protected void tickIfAlive() {
        if (this.shouldExtinguish()) {
            // smoke / steam when hitting water.  node is responsible for aging to death
            this.level.addParticle(this.getChildParticle(), this.x, this.y, this.z, 0, 0, 0);
        } else if (this.random.nextFloat() <= NORMAL_PARTICLE_CHANCE && this.random.nextFloat() < this.node.getLifetimeFraction()) {
            // spawn a smoke trail after some time
            this.level.addParticle(this.getChildParticle(), this.x, this.y, this.z, this.xd * 0.5, this.yd * 0.5, this.zd * 0.5);
        }
    }
}
