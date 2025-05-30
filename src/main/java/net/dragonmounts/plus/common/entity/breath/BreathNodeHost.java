package net.dragonmounts.plus.common.entity.breath;

public interface BreathNodeHost {
    /// extinguish in water
    boolean shouldExtinguish();

    boolean isCollided();

    double getMotionLengthSqr();
}
