package net.dragonmounts.plus.common.api;

import net.dragonmounts.plus.common.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.plus.common.entity.dragon.ServerDragonEntity;
import net.minecraft.server.level.ServerPlayer;

/// @see net.minecraft.advancements.critereon.BredAnimalsTrigger
public interface BredDragonsTrigger {
    default void dragonmounts$plus$trigger(
            ServerPlayer player,
            ServerDragonEntity parent,
            ServerDragonEntity partner,
            HatchableDragonEggEntity egg
    ) {}
}
