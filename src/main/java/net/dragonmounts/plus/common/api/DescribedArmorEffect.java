package net.dragonmounts.plus.common.api;

import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.capability.ArmorEffectManagerImpl;
import net.dragonmounts.plus.common.client.gui.ArmorEffectDescriptor;
import net.dragonmounts.plus.common.client.gui.ArmorEffectTooltip;
import net.dragonmounts.plus.compat.registry.ArmorEffect;
import net.dragonmounts.plus.compat.registry.CooldownCategory;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.jetbrains.annotations.Nullable;

import static net.dragonmounts.plus.common.util.TimeUtil.formatAsFloat;

public interface DescribedArmorEffect extends ArmorEffect, TooltipComponent {
    /// Just to simplify usage, do **NOT** invoke this in server side!
    ArmorEffectTooltip getClientTooltip();

    default boolean isLocalActive() {
        var manager = ArmorEffectManagerImpl.getLocal();
        return manager != null && manager.isActive(this);
    }

    class Advanced extends CooldownCategory implements DescribedArmorEffect {
        protected final MutableComponent trigger;
        protected final Component title;
        public final int cooldown;
        protected ArmorEffectTooltip tooltip;

        public Advanced(ResourceLocation identifier, Component title, int cooldown, @Nullable MutableComponent trigger) {
            super(identifier);
            this.title = title;
            this.cooldown = cooldown;
            this.trigger = trigger;
        }

        public ArmorEffectTooltip makeClientTooltip() {
            return new ArmorEffectTooltip(this.title, new ArmorEffectDescriptor(
                    Component.translatable(Util.makeDescriptionId("tooltip.armor_effect", this.identifier)),
                    this::getCooldownInfo,
                    this.trigger,
                    this::isLocalActive
            ));
        }

        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            return level > 3;
        }

        @Override
        public final ArmorEffectTooltip getClientTooltip() {
            if (this.tooltip == null) {
                this.tooltip = this.makeClientTooltip();
            }
            return this.tooltip;
        }

        protected Component getCooldownInfo() {
            int cooldown = ArmorEffectManagerImpl.getLocalCooldown(this);
            if (cooldown > 0) {
                return Component.translatable("tooltip.armor_effect_remaining_cooldown", formatAsFloat(cooldown));
            } else if (this.cooldown > 0) {
                return Component.translatable("tooltip.armor_effect_cooldown", formatAsFloat(this.cooldown));
            }
            return null;
        }
    }
}
