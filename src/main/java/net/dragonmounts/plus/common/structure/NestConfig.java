package net.dragonmounts.plus.common.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public record NestConfig(
        float weight,
        NestPlacement placement,
        Optional<BlockState> island,
        List<ResourceLocation> templates
) {
    public static final Codec<NestConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("weight", 1.0F).forGetter(NestConfig::weight),
            NestPlacement.CODEC.fieldOf("placement").forGetter(NestConfig::placement),
            BlockState.CODEC.optionalFieldOf("island").forGetter(NestConfig::island),
            ExtraCodecs.nonEmptyList(ResourceLocation.CODEC.listOf()).fieldOf("templates").forGetter(NestConfig::templates)
    ).apply(instance, NestConfig::new));

    public NestConfig(NestPlacement placement, List<ResourceLocation> templates) {
        this(1.0F, placement, Optional.empty(), templates);
    }

    public NestConfig(NestPlacement placement, BlockState island, List<ResourceLocation> templates) {
        this(1.0F, placement, Optional.of(island), templates);
    }
}
