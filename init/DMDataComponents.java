package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.api.ArmorEffectSource;
import net.dragonmounts.plus.common.component.DragonFood;
import net.dragonmounts.plus.common.component.ScoreboardInfo;
import net.dragonmounts.plus.common.component.WhistleSound;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;

import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerComponent;

public class DMDataComponents {
    public static final DataComponentType<ArmorEffectSource> ARMOR_EFFECT_SOURCE = registerComponent(
            "armor_effect_source",
            builder -> builder.persistent(ArmorEffectSource.CODEC)
    );
    public static final DataComponentType<DragonFood> DRAGON_FOOD = registerComponent(
            "dragon_food",
            builder -> builder.persistent(DragonFood.CODEC).networkSynchronized(DragonFood.STREAM_CODEC)
    );
    public static final DataComponentType<DragonType> DRAGON_TYPE = registerComponent(
            "dragon_type",
            builder -> builder.persistent(DragonType.CODEC).networkSynchronized(DragonType.STREAM_CODEC)
    );
    public static final DataComponentType<Component> PLAYER_NAME = registerComponent(
            "player_name",
            builder -> builder.cacheEncoding()
                    .persistent(ComponentSerialization.FLAT_CODEC)
                    .networkSynchronized(ComponentSerialization.STREAM_CODEC)
    );
    public static final DataComponentType<ScoreboardInfo> SCORES = registerComponent(
            "scores",
            builder -> builder.persistent(ScoreboardInfo.CODEC)
    );
    public static final DataComponentType<WhistleSound> WHISTLE_SOUND = registerComponent(
            "whistle_sound",
            builder -> builder.cacheEncoding()
                    .persistent(WhistleSound.CODEC)
                    .networkSynchronized(WhistleSound.STREAM_CODEC)
    );

    public static void init() {}
}
