package net.dragonmounts.plus.common.entity.breath;

public interface BreathEffectHandler {
    boolean decayEffectTick();

    boolean isUnaffected();
}
