package net.dragonmounts.plus.common.api;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.capability.ArmorEffectManagerImpl;
import net.dragonmounts.plus.common.client.gui.ArmorEffectTooltip;
import net.dragonmounts.plus.compat.registry.ArmorEffect;
import net.dragonmounts.plus.compat.registry.CooldownCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.dragonmounts.plus.common.util.TimeUtil.formatAsFloat;

public interface DescribedArmorEffect extends ArmorEffect, TooltipComponent {
    static MutableComponent formatTrigger(ArmorEffect effect, MutableComponent trigger) {
        var manager = ArmorEffectManagerImpl.getLocal();
        return manager == null || !manager.isActive(effect)
                ? trigger.withStyle(ChatFormatting.GRAY)
                : trigger.withStyle(ChatFormatting.GREEN);
    }

    /// Just to simplify usage, do **NOT** invoke this in server side!
    ArmorEffectTooltip getClientTooltip();

    class Advanced extends CooldownCategory implements DescribedArmorEffect {
        protected final MutableComponent trigger;
        protected final Component title;
        protected final Component description;
        public final int cooldown;

        public Advanced(ResourceLocation identifier, Component title, int cooldown, @Nullable MutableComponent trigger) {
            super(identifier);
            this.trigger = trigger;
            this.cooldown = cooldown;
            this.title = title;
            this.description = Component.translatable(Util.makeDescriptionId("tooltip.armor_effect", identifier));
        }

        public void appendCooldownInfo(List<Component> tooltips) {
            int value = ArmorEffectManagerImpl.getLocalCooldown(this);
            if (value > 0) {
                tooltips.add(Component.translatable("tooltip.armor_effect_remaining_cooldown", formatAsFloat(value)));
            } else if (this.cooldown > 0) {
                tooltips.add(Component.translatable("tooltip.armor_effect_cooldown", formatAsFloat(this.cooldown)));
            }
        }

        public List<Component> getDescription() {
            var tooltips = new ObjectArrayList<Component>();
            tooltips.add(this.description);
            return tooltips;
        }

        @Override
        public boolean activate(ArmorEffectManager manager, Player player, int level) {
            return level > 3;
        }

        @Override
        public ArmorEffectTooltip getClientTooltip() {
            var description = this.getDescription();
            this.appendCooldownInfo(description);
            return new ArmorEffectTooltip(this.title, description, formatTrigger(this, this.trigger));
        }
    }
}
