package net.dragonmounts.plus.common.init;

import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;

import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerMemory;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerSensoryMemory;

public class DMMemories {
    public static final MemoryModuleType<Unit> IS_CONTROLLED = registerSensoryMemory("is_controlled");
    public static final MemoryModuleType<Unit> IS_ORDERED_TO_SIT = registerSensoryMemory("is_ordered_to_sit");
    public static final MemoryModuleType<Unit> DISABLED_FOLLOWING_OWNER = registerMemory("disabled_following_owner");
    public static final MemoryModuleType<Player> FOLLOWABLE_OWNER = registerSensoryMemory("followable_owner");

    public static void init() {}
}
