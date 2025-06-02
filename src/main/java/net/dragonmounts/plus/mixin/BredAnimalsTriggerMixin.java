package net.dragonmounts.plus.mixin;

import net.dragonmounts.plus.common.api.BredDragonsTrigger;
import net.dragonmounts.plus.common.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.plus.common.entity.dragon.ServerDragonEntity;
import net.minecraft.advancements.critereon.BredAnimalsTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BredAnimalsTrigger.class)
public abstract class BredAnimalsTriggerMixin extends SimpleCriterionTrigger<BredAnimalsTrigger.TriggerInstance> implements BredDragonsTrigger {
    @Override
    public void dragonmounts$plus$trigger(ServerPlayer player, ServerDragonEntity parent, ServerDragonEntity partner, HatchableDragonEggEntity egg) {
        var parentContext = EntityPredicate.createContext(player, parent);
        var partnerContext = EntityPredicate.createContext(player, partner);
        var eggContext = EntityPredicate.createContext(player, egg);
        this.trigger(player, instance -> instance.matches(parentContext, partnerContext, eggContext));
    }
}
