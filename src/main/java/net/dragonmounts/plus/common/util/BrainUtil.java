package net.dragonmounts.plus.common.util;

import net.dragonmounts.plus.common.entity.ai.behavior.DispatchBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;

import java.util.function.BiPredicate;

public class BrainUtil {
    public static <E extends LivingEntity> DispatchBehavior<E> dispatch(
            BehaviorControl<? super E> onSuccess,
            BehaviorControl<? super E> onFailure,
            BiPredicate<ServerLevel, ? super E> condition
    ) {
        return new DispatchBehavior<>(condition, onSuccess, onFailure);
    }

    public static <E extends LivingEntity> void startBehavior(
            BehaviorControl<? super E> behavior,
            ServerLevel level,
            E entity,
            long time
    ) {
        if (behavior.getStatus() == Behavior.Status.STOPPED) {
            behavior.tryStart(level, entity, time);
        }
    }

    public static <E extends LivingEntity> void stopBehavior(
            BehaviorControl<? super E> behavior,
            ServerLevel level,
            E entity,
            long time
    ) {
        if (behavior.getStatus() == Behavior.Status.RUNNING) {
            behavior.doStop(level, entity, time);
        }
    }
}
