package net.dragonmounts.plus.common.api;

import com.mojang.serialization.Codec;
import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.util.DefaultedDispatchCodec;
import net.dragonmounts.plus.compat.registry.ArmorEffectSourceType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ArmorEffectSource {
    Codec<ArmorEffectSource> CODEC = new DefaultedDispatchCodec<ArmorEffectSourceType<?>, ArmorEffectSource>(
            ArmorEffectSourceType.REGISTRY.byNameCodec(),
            "type",
            ArmorEffectSourceType.COMPONENT,
            ArmorEffectSource::getType,
            ArmorEffectSourceType::codec
    ).codec();

    void affect(ArmorEffectManager manager, Player player, ItemStack stack);

    ArmorEffectSourceType<?> getType();
}
