package net.dragonmounts.plus.common.entity.breath;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum BreathPower implements StringRepresentable {
    TINY(0.5F, 0.04F, 0.05F, 0.05F),
    SMALL(1.5F, 0.35F, 0.35F, 1.2F),
    MEDIUM(3.95F, 0.7F, 2.0F, 1.2F),
    LARGE(4.0F, 0.8F, 3.25F, 1.4F),
    HUGE(5.0F, 1.0F, 3.5F, 1.6F);
    private static final IntFunction<BreathPower> BY_ID = ByIdMap.continuous(BreathPower::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final Codec<BreathPower> CODEC = StringRepresentable.fromEnum(BreathPower::values);
    public static final StreamCodec<ByteBuf, BreathPower> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, BreathPower::ordinal);

    public final float speed;
    public final float lifetime;
    public final float size;
    public final float intensity;
    public final String name;

    BreathPower(
            float speed,
            float lifetime,
            float size,
            float intensity
    ) {
        this.speed = speed;
        this.lifetime = lifetime;
        this.size = size;
        this.intensity = intensity;
        this.name = name().toLowerCase();
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
