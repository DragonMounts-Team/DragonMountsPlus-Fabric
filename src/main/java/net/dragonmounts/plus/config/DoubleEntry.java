package net.dragonmounts.plus.config;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.dragonmounts.plus.config.network.S2CDoubleConfigPayload;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class DoubleEntry extends ConfigEntry<Double> {
    public static final double MIN_DOUBLE = -Double.MAX_VALUE;
    public final double fallback;
    public final double min;
    public final double max;
    protected double saved;
    protected double value;

    public DoubleEntry(
            ConfigHolder holder,
            String key,
            String name,
            String tooltip,
            double fallback,
            double min,
            double max
    ) {
        super(holder, key, name, tooltip);
        this.min = min;
        this.max = max;
        this.value = this.saved = this.fallback = Mth.clamp(fallback, min, max);
    }

    public double get() {
        return this.value;
    }

    public float getAsFloat() {
        return (float) this.value;
    }

    @Override
    public boolean modify(Double wrapped) {
        double value = Mth.clamp(wrapped, this.min, this.max);
        if (this.value == value) return false;
        this.value = value;
        return true;
    }

    @Override
    public boolean isChanged() {
        return this.get() != this.saved;
    }

    @Override
    public void setSaved() {
        this.saved = this.value;
    }

    @Override
    public boolean isDefault() {
        return this.get() == this.fallback;
    }

    @Override
    public Double parse(CommandContext<?> context, String name) {
        return DoubleArgumentType.getDouble(context, name);
    }

    @Override
    public ArgumentType<Double> getArgument() {
        return DoubleArgumentType.doubleArg(this.min, this.max);
    }

    @Override
    public String getAsString() {
        return Double.toString(this.get());
    }

    @Override
    public Tag dump() {
        return DoubleTag.valueOf(this.get());
    }

    @Override
    public void load(@Nullable Tag data) {
        if (data instanceof NumericTag tag) {
            this.modify(tag.getAsDouble());
        }
    }

    @Override
    public CustomPacketPayload wrap(int id) {
        return new S2CDoubleConfigPayload(id, this.get());
    }
}
