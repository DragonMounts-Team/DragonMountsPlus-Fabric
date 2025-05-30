package net.dragonmounts.plus.common.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;

/**
 * <b>RELIES ON {@link net.minecraft.world.entity.ai.sensing.PlayerSensor}</b>, assuming {@link Attributes#TEMPT_RANGE} is smaller than {@link Attributes#FOLLOW_RANGE}
 *
 * @see net.minecraft.world.entity.ai.sensing.TemptingSensor
 */
public class QuickTemptingSensor extends Sensor<PathfinderMob> {
    private static final TargetingConditions TEMPT_TARGETING = TargetingConditions.forNonCombat().ignoreLineOfSight();
    public final Predicate<ItemStack> temptations;

    public QuickTemptingSensor(Predicate<ItemStack> temptations) {
        this.temptations = temptations;
    }

    @Override
    protected void doTick(ServerLevel level, PathfinderMob entity) {
        boolean found = false;
        var predicate = TEMPT_TARGETING.copy().range(entity.getAttributeValue(Attributes.TEMPT_RANGE));
        var brain = entity.getBrain();
        for (var player : brain.getMemory(
                MemoryModuleType.NEAREST_PLAYERS
        ).orElse(Collections.emptyList())) {
            if (predicate.test(level, entity, player) && holdingTemptation(player, this.temptations) && !entity.hasPassenger(player)) {
                found = true;
                brain.setMemory(MemoryModuleType.TEMPTING_PLAYER, player);
                break;
            }
        }
        if (found) return;
        brain.eraseMemory(MemoryModuleType.TEMPTING_PLAYER);
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
                MemoryModuleType.NEAREST_PLAYERS,
                MemoryModuleType.TEMPTING_PLAYER
        );
    }

    public static boolean holdingTemptation(Player player, Predicate<ItemStack> temptation) {
        return temptation.test(player.getMainHandItem()) || temptation.test(player.getOffhandItem());
    }
}
