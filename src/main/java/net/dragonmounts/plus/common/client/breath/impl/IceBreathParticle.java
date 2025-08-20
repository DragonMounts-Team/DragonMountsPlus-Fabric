package net.dragonmounts.plus.common.client.breath.impl;

import net.dragonmounts.plus.common.client.breath.BreathParticle;
import net.dragonmounts.plus.common.client.breath.BreathParticleFactory;
import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ParticleTypes;

public class IceBreathParticle extends BreathParticle {
    public static final BreathParticleFactory FACTORY = IceBreathParticle::new;

    public IceBreathParticle(BreathParticleOption option, TextureAtlasSprite sprite, ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(option, sprite, level, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    protected void tickIfAlive() {
        if (this.shouldExtinguish()) {
            this.level.addParticle(ParticleTypes.SNOWFLAKE, this.x, this.y, this.z, 0, 0, 0);
        } else if (this.random.nextFloat() <= NORMAL_PARTICLE_CHANCE && this.random.nextFloat() < this.node.getLifetimeFraction()) {
            this.level.addParticle(ParticleTypes.SNOWFLAKE, this.x, this.y, this.z, this.xd * 0.5, this.yd * 0.5, this.zd * 0.5);
        }
    }
}
