package net.dragonmounts.plus.common.entity.dragon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.dragonmounts.plus.common.entity.ai.behavior.*;
import net.dragonmounts.plus.common.init.DMActivities;
import net.dragonmounts.plus.common.init.DMEntities;
import net.dragonmounts.plus.common.init.DMMemories;
import net.dragonmounts.plus.common.init.DMSensors;
import net.dragonmounts.plus.common.util.BrainUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;

import java.util.Optional;

public class DragonAi {
    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super ServerDragonEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.HURT_BY,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_ADULT,
            SensorType.NEAREST_PLAYERS,
            DMSensors.DRAGON_TARGETS,
            DMSensors.DRAGON_TEMPTATIONS,
            DMSensors.OWNER
    );
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.IS_PANICKING,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.NEAREST_PLAYERS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            DMMemories.IS_ORDERED_TO_SIT,
            DMMemories.IS_CONTROLLED,
            DMMemories.FOLLOWABLE_OWNER,
            DMMemories.DISABLED_FOLLOWING_OWNER
    );
    protected static final ImmutableList<Activity> ORDERED_ACTIVITIES = ImmutableList.of(
            DMActivities.CONTROLLED,
            DMActivities.SITTING
    );
    protected static final ImmutableList<Activity> PRIORITIZED_ACTIVITIES = ImmutableList.of(
            DMActivities.CONTROLLED,
            DMActivities.SITTING,
            Activity.FIGHT,
            Activity.IDLE
    );

    static void initCoreActivity(Brain<ServerDragonEntity> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink()
        ));
    }

    static void initIdleActivity(Brain<ServerDragonEntity> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
                new Swim<>(0.8F),
                new AnimalMakeLoveEx(DMEntities.TAMEABLE_DRAGON.value(), 1.0F, 4, 6),
                new FollowTemptation(entity -> 1.25F, entity -> 3.0),
                new FollowOwner(1.0F, 14.0F, 10),
                StartAttacking.create(DragonAi::findNearestValidAttackTarget),
                SetEntityLookTargetSometimes.create(8.0F, UniformInt.of(30, 60)),
                BrainUtil.dispatch(
                        new TryFindGround<>(32, 48, 0.75F, DragonAi::shouldFollowOwner),
                        new RunOne<>(ImmutableList.of(
                                Pair.of(RandomStroll.stroll(0.75F), 3),
                                Pair.of(SetWalkTargetFromLookTarget.create(
                                        target -> !(target instanceof Player),
                                        target -> 0.75F,
                                        5
                                ), 2),
                                Pair.of(new DoNothing(40, 100), 5)
                        )),
                        (level, dragon) -> dragon.isFlying()
                )
        ));
    }

    static void initFightActivity(Brain<ServerDragonEntity> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 10, ImmutableList.of(
                SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F),
                MeleeAttack.create(40),
                StopAttackingIfTargetInvalid.create(),
                EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
        ), MemoryModuleType.ATTACK_TARGET);
    }

    static void initControlledActivity(Brain<ServerDragonEntity> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
                DMActivities.CONTROLLED,
                0,
                ImmutableList.of(new ControlledByPlayer()),
                DMMemories.IS_CONTROLLED
        );
    }

    static void initSittingActivity(Brain<ServerDragonEntity> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(DMActivities.SITTING, 0, ImmutableList.of(
                BrainUtil.dispatch(
                        new SitWhenOrderedTo(),
                        new TryFindGround<>(32, 48, 0.75F, dragon -> false),
                        (level, dragon) -> dragon.onGround()
                )
        ), DMMemories.IS_ORDERED_TO_SIT);
    }

    public static Brain.Provider<ServerDragonEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    public static Brain<ServerDragonEntity> makeBrain(Brain<ServerDragonEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(brain);
        initControlledActivity(brain);
        initSittingActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void tickBrain(ServerLevel level, ServerDragonEntity dragon) {
        var brain = dragon.getBrain();
        boolean shouldSit = brain.hasMemoryValue(DMMemories.IS_ORDERED_TO_SIT);
        if (!shouldSit && dragon.isOrderedToSit()) {
            brain.setMemory(DMMemories.IS_ORDERED_TO_SIT, Unit.INSTANCE);
            shouldSit = true;
        }
        if ((shouldSit || brain.hasMemoryValue(DMMemories.IS_CONTROLLED)) && !ORDERED_ACTIVITIES.contains(
                brain.getActiveNonCoreActivity().orElse(null)
        )) {
            brain.stopAll(level, dragon);
            brain.setActiveActivityToFirstValid(ORDERED_ACTIVITIES);
        }
        brain.tick(level, dragon);
        brain.setActiveActivityToFirstValid(PRIORITIZED_ACTIVITIES);
    }

    public static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, ServerDragonEntity dragon) {
        return BehaviorUtils.isBreeding(dragon) ? Optional.empty() : dragon.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    public static boolean shouldFollowOwner(ServerDragonEntity dragon) {
        var brain = dragon.getBrain();
        if (brain.hasMemoryValue(DMMemories.DISABLED_FOLLOWING_OWNER)) return false;
        var owner = brain.getMemory(DMMemories.FOLLOWABLE_OWNER).orElse(null);
        return owner != null && dragon.distanceToSqr(owner) < 400.0;
    }
}
