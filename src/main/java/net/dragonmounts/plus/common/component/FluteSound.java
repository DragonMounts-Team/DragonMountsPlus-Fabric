package net.dragonmounts.plus.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dragonmounts.plus.common.entity.dragon.DragonLifeStage;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.init.DMDataComponents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.Optional;
import java.util.UUID;

public record FluteSound(
        UUID dragon,
        Component name,
        Optional<UUID> owner,
        Optional<DragonLifeStage> stage
) {
    public static final Codec<FluteSound> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("Dragon").forGetter(FluteSound::dragon),
            ComponentSerialization.CODEC.optionalFieldOf("Name", Component.empty()).forGetter(FluteSound::name),
            UUIDUtil.CODEC.optionalFieldOf("Owner").forGetter(FluteSound::owner),
            DragonLifeStage.CODEC.optionalFieldOf("Stage").forGetter(FluteSound::stage)
    ).apply(instance, FluteSound::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FluteSound> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            FluteSound::dragon,
            ComponentSerialization.STREAM_CODEC,
            FluteSound::name,
            UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs::optional),
            FluteSound::owner,
            DragonLifeStage.STREAM_CODEC.apply(ByteBufCodecs::optional),
            FluteSound::stage,
            FluteSound::new
    );

    public static void bindFlute(ItemStack stack, TameableDragonEntity dragon, Player player) {
        stack.set(DMDataComponents.FLUTE_SOUND, new FluteSound(
                dragon.getUUID(),
                dragon.getName(),
                Optional.of(player.getUUID()),
                Optional.of(dragon.getLifeStage())
        ));
        stack.set(DMDataComponents.PLAYER_NAME, player.getName());
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(dragon.getDragonType().color, false));
    }
}
