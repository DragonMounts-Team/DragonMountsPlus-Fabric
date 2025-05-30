package net.dragonmounts.plus.compat.registry;

import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.minecraft.core.MappedRegistry;
import net.minecraft.world.entity.player.Player;

import static net.dragonmounts.plus.common.DragonMountsShared.ARMOR_EFFECT;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.makeSimpleRegistry;

public interface ArmorEffect {
    MappedRegistry<ArmorEffect> REGISTRY = makeSimpleRegistry(ARMOR_EFFECT);

    boolean activate(ArmorEffectManager manager, Player player, int level);
}
