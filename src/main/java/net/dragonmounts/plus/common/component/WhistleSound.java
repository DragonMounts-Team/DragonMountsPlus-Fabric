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

public record WhistleSound(
        UUID dragon,
        Component name,
        Optional<UUID> owner,
        Optional<DragonLifeStage> stage
) {
    public static final Codec<WhistleSound> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("Dragon").forGetter(WhistleSound::dragon),
            ComponentSerialization.CODEC.optionalFieldOf("Name", Component.empty()).forGetter(WhistleSound::name),
            UUIDUtil.CODEC.optionalFieldOf("Owner").forGetter(WhistleSound::owner),
            DragonLifeStage.CODEC.optionalFieldOf("Stage").forGetter(WhistleSound::stage)
    ).apply(instance, WhistleSound::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, WhistleSound> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            WhistleSound::dragon,
            ComponentSerialization.STREAM_CODEC,
            WhistleSound::name,
            UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs::optional),
            WhistleSound::owner,
            DragonLifeStage.STREAM_CODEC.apply(ByteBufCodecs::optional),
            WhistleSound::stage,
            WhistleSound::new
    );

    public static void bindWhistle(ItemStack stack, TameableDragonEntity dragon, Player player) {
        stack.set(DMDataComponents.WHISTLE_SOUND, new WhistleSound(
                dragon.getUUID(),
                dragon.getName(),
                Optional.of(player.getUUID()),
                Optional.of(dragon.getLifeStage())
        ));
        stack.set(DMDataComponents.PLAYER_NAME, player.getName());
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(dragon.getDragonType().color, false));
    }
}
