package net.dragonmounts.plus.common.client.breath.impl;

import net.dragonmounts.plus.common.client.ClientDragonEntity;
import net.dragonmounts.plus.common.client.breath.BreathSound;
import net.dragonmounts.plus.common.client.breath.BreathSoundHandler;
import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.dragonmounts.plus.common.entity.breath.BreathState;
import net.dragonmounts.plus.common.entity.breath.DragonBreathHelper;

import static net.minecraft.util.Mth.lerp;

public class ClientBreathHelper extends DragonBreathHelper<ClientDragonEntity> {
    private final BreathSoundHandler sound = new BreathSoundHandler();
    private int previousTickCount = Integer.MIN_VALUE;
    private double throatX;
    private double throatY;
    private double throatZ;
    private double lookX;
    private double lookY;
    private double lookZ;

    public ClientBreathHelper(ClientDragonEntity dragon) {
        super(dragon);
    }

    @Override
    public void tick() {
        ++this.tickCounter;
        var dragon = this.dragon;
        var breath = this.breath;
        if (breath == null) {
            this.currentBreathState = BreathState.IDLE;
            this.onBreathStop();
            return;
        }
        var throat = breath.getSpawnPosition();
        var stage = dragon.getLifeStage();
        this.updateBreathState(dragon.isBreathing());
        this.sound.update();
        if (this.currentBreathState == BreathState.SUSTAIN) {
            /*
             * Created by TGG on 21/06/2015.
             * Used to spawn breath particles on the client side (in future: will be different for different breath weapons)
             * Spawn breath particles for this tick.  If the beam endpoints have moved, interpolate between them, unless
             * the beam stopped for a while (tickCount skipped one or more tick)
             */
            var level = dragon.level();
            var look = dragon.getLookAngle();
            var motion = dragon.getDeltaMovement();
            double throatX = throat.x, throatY = throat.y, throatZ = throat.z,
                    lookX = look.x, lookY = look.y, lookZ = look.z,
                    motionX = motion.x, motionY = motion.y, motionZ = motion.z;
            if (this.tickCounter != previousTickCount + 1) {
                this.throatX = throatX;
                this.throatY = throatY;
                this.throatZ = throatZ;
                this.lookX = lookX;
                this.lookY = lookY;
                this.lookZ = lookZ;
            }
            var option = new BreathParticleOption(dragon.getVariant(), stage.power);
            final int PARTICLES_PER_TICK = 4;
            for (int i = 0; i < PARTICLES_PER_TICK; ++i) {
                double partialTickHeadStart = i / (double) PARTICLES_PER_TICK;
                level.addParticle(
                        option,
                        lerp(partialTickHeadStart, this.throatX, throatX) + motionX,
                        lerp(partialTickHeadStart, this.throatY, throatY) + motionY,
                        lerp(partialTickHeadStart, this.throatZ, throatZ) + motionZ,
                        lerp(partialTickHeadStart, this.lookX, lookX),
                        lerp(partialTickHeadStart, this.lookY, lookY),
                        lerp(partialTickHeadStart, this.lookZ, lookZ)
                );
            }
            this.throatX = throatX;
            this.throatY = throatY;
            this.throatZ = throatZ;
            this.lookX = lookX;
            this.lookY = lookY;
            this.lookZ = lookZ;
            this.previousTickCount = this.tickCounter;
        }
    }

    @Override
    protected void onBreathStart() {
        this.sound.setTimeout();
        var breath = this.breath;
        if (breath == null) return;
        var dragon = this.dragon;
        var stage = dragon.getLifeStage();
        var start = new BreathSound.Scheduled(dragon, breath.getStartSound(stage), 25);
        start.next = new BreathSound(dragon, breath.getLoopSound(stage), true);
        this.sound.play(start);
    }

    @Override
    protected void onBreathStop() {
        this.sound.setTimeout();
        var breath = this.breath;
        if (breath == null) return;
        this.sound.play(new BreathSound.Scheduled(this.dragon, breath.getStopSound(this.dragon.getLifeStage()), 60));
    }
}
