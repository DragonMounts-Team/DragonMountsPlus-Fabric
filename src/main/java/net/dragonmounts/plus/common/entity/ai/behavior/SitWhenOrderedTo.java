package net.dragonmounts.plus.common.entity.ai.behavior;

import net.dragonmounts.plus.common.init.DMMemories;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class SitWhenOrderedTo extends GoalBehavior<TamableAnimal> {
    @Override
    protected boolean canUse(ServerLevel level, TamableAnimal entity) {
        if (!entity.isTame() || entity.isInWaterOrBubble() || !entity.onGround()) return false;
        var owner = entity.getOwner();
        return owner == null || entity.isOrderedToSit() && (
                entity.distanceToSqr(owner) >= 144.0 || owner.getLastHurtByMob() == null
        );
    }

    @Override
    protected boolean canContinueToUse(ServerLevel level, TamableAnimal animal) {
        return animal.isOrderedToSit();
    }

    @Override
    protected void doStart(ServerLevel level, TamableAnimal animal) {
        super.doStart(level, animal);
        animal.getNavigation().stop();
        animal.setInSittingPose(true);
        var brian = animal.getBrain();
        brian.eraseMemory(MemoryModuleType.PATH);
        brian.eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    public void doStop(ServerLevel level, TamableAnimal animal, long time) {
        super.doStop(level, animal, time);
        animal.setInSittingPose(false);
        animal.getBrain().eraseMemory(DMMemories.IS_ORDERED_TO_SIT);
    }
}
