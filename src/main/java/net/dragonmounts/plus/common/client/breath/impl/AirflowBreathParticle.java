package net.dragonmounts.plus.common.client.breath.impl;

import net.dragonmounts.plus.common.client.breath.BreathParticle;
import net.dragonmounts.plus.common.client.breath.BreathParticleFactory;
import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.dragonmounts.plus.common.util.math.MathUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class AirflowBreathParticle extends BreathParticle {
    public static final BreathParticleFactory FACTORY = AirflowBreathParticle::new;
    private static final float ROLL_SPEED = MathUtil.PI / 3;
    protected final float rollSpeed;

    public AirflowBreathParticle(BreathParticleOption option, TextureAtlasSprite sprite, ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(option, sprite, level, x, y, z, motionX, motionY, motionZ);
        this.rollSpeed = this.random.nextBoolean() ? ROLL_SPEED : -ROLL_SPEED;
    }

    @Override
    protected void tickIfAlive() {
        this.oRoll = this.roll;
        this.roll += this.rollSpeed;
    }

    @Override
    protected float getRenderSize() {
        return super.getRenderSize() * 0.625F;
    }
}
