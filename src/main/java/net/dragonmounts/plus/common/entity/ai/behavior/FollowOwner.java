package net.dragonmounts.plus.common.entity.ai.behavior;

import net.dragonmounts.plus.common.init.DMMemories;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class FollowOwner extends OneShot<TamableAnimal> {
    private static final int COOLDOWN_TICKS = 25;
    public final float speedModifier;
    public final float squaredStart;
    public final int stopDist;
    private long lastTry;

    public FollowOwner(float speedModifier, float startDist, int stopDist) {
        this.speedModifier = speedModifier;
        this.squaredStart = startDist * startDist;
        this.stopDist = stopDist;
    }

    static boolean isUnavailable(Brain<?> brain) {
        return brain.getMemory(MemoryModuleType.IS_TEMPTED).orElse(false)
                || brain.hasMemoryValue(DMMemories.DISABLED_FOLLOWING_OWNER)
                || brain.hasMemoryValue(MemoryModuleType.WALK_TARGET)
                || brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
                || brain.hasMemoryValue(MemoryModuleType.BREED_TARGET);
    }

    /// @see TamableAnimal#unableToMoveToOwner()
    static boolean isUnavailable(TamableAnimal animal) {
        return animal.isOrderedToSit() || animal.isPassenger() || animal.mayBeLeashed();
    }

    @Override
    public boolean trigger(ServerLevel level, TamableAnimal animal, long time) {
        if (isUnavailable(animal)) return false;
        var brain = animal.getBrain();
        if (isUnavailable(brain)) return false;
        var owner = brain.getMemory(DMMemories.FOLLOWABLE_OWNER).orElse(null);
        if (owner == null || animal.distanceToSqr(owner) < this.squaredStart) return false;
        if (time - this.lastTry < COOLDOWN_TICKS) return true;
        this.lastTry = time;
        if (animal.shouldTryTeleportToOwner()) {
            animal.tryToTeleportToOwner();
            return true;
        }
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(owner, true));
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(
                new EntityTracker(owner, false),
                this.speedModifier,
                this.stopDist
        ));
        return true;
    }
}
