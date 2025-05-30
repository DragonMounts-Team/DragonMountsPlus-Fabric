package net.dragonmounts.plus.common.component;

import com.mojang.serialization.MapCodec;
import net.dragonmounts.plus.common.api.ArmorEffectSource;
import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.component.impl.ArmorEffectEntry;
import net.dragonmounts.plus.compat.registry.ArmorEffectSourceType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public final class ListBasedArmorEffectSource implements ArmorEffectSource {
    public static final MapCodec<ListBasedArmorEffectSource> CODEC = ArmorEffectEntry.CODEC.listOf().xmap(
            ListBasedArmorEffectSource::new,
            ListBasedArmorEffectSource::getEffects
    ).fieldOf("effects");
    public static final ListBasedArmorEffectSource EMPTY = new ListBasedArmorEffectSource(Collections.emptyList());

    public static ListBasedArmorEffectSource of(List<ArmorEffectEntry> effects) {
        return effects.isEmpty() ? EMPTY : new ListBasedArmorEffectSource(effects);
    }

    public static ListBasedArmorEffectSource of(ArmorEffectEntry... effects) {
        return of(List.of(effects));
    }

    public final List<ArmorEffectEntry> effects;

    private ListBasedArmorEffectSource(final List<ArmorEffectEntry> effects) {
        this.effects = effects;
    }

    public List<ArmorEffectEntry> getEffects() {
        return this.effects;
    }

    @Override
    public void affect(ArmorEffectManager manager, Player player, ItemStack stack) {
        for (var entry : this.effects) {
            manager.addLevel(entry.effect(), entry.level());
        }
    }

    @Override
    public ArmorEffectSourceType<?> getType() {
        return ArmorEffectSourceType.COMPONENT;
    }
}
