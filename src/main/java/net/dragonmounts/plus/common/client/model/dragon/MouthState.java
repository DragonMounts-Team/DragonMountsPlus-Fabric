package net.dragonmounts.plus.common.client.model.dragon;

import static net.dragonmounts.plus.common.entity.breath.DragonBreathHelper.BREATH_START_DURATION;
import static net.dragonmounts.plus.common.entity.breath.DragonBreathHelper.BREATH_STOP_DURATION;

public enum MouthState {
    IDLE(0.0F, 0),
    ATTACKING(0.72F, 3),
    BREATHING(0.58F, BREATH_START_DURATION + BREATH_STOP_DURATION, BREATH_START_DURATION),
    ROARING(0.67F, 5),
    EATING(0.25F, 2);

    public final float amplitude;
    public final int turning;
    public final int duration;

    MouthState(float amplitude, int duration, int turning) {
        this.amplitude = amplitude;
        this.turning = turning;
        this.duration = duration;
    }

    MouthState(float amplitude, int half) {
        this(amplitude, half + half, half);
    }
}
