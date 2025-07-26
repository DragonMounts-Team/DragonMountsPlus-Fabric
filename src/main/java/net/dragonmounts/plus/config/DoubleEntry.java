package net.dragonmounts.plus.config;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.dragonmounts.plus.common.network.s2c.DoubleConfigPayload;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.function.DoubleConsumer;

public class DoubleEntry extends ConfigEntry<Double> {
    public static final double MIN_DOUBLE = -Double.MAX_VALUE;
    public final double fallback;
    public final double min;
    public final double max;
    protected final DoubleConsumer onChanged;
    protected double saved;
    protected double value;
    protected double effective;

    public DoubleEntry(
            String key,
            String name,
            String tooltip,
            double fallback,
            double min,
            double max,
            DoubleConsumer onChanged
    ) {
        super(key, name, tooltip);
        this.min = min;
        this.max = max;
        this.onChanged = onChanged;
        this.set(this.saved = this.fallback = fallback);
    }

    public double get() {
        return this.effective;
    }

    public float getAsFloat() {
        return (float) this.effective;
    }

    protected void overrideImpl(double value) {
        if (value == this.effective) return;
        this.effective = value;
        if (this.onChanged == null) return;
        this.onChanged.accept(value);
    }

    @Override
    public void override(Double value) {
        this.overrideImpl(Mth.clamp(value, this.min, this.max));
    }

    @Override
    public boolean set(Double wrapped) {
        double value = Mth.clamp(wrapped, this.min, this.max);
        this.overrideImpl(value);
        if (this.value == value) return false;
        this.value = value;
        return true;
    }

    @Override
    public String getAsString() {
        return Double.toString(this.get());
    }

    @Override
    public Tag dump() {
        return DoubleTag.valueOf(this.value);
    }

    @Override
    public Double load(@Nullable Tag data) {
        return data instanceof NumericTag ? ((NumericTag) data).getAsDouble() : this.fallback;
    }

    @Override
    public boolean isChanged() {
        return this.value != this.saved;
    }

    @Override
    public boolean isDefault() {
        return this.value == this.fallback;
    }

    @Override
    public void reset() {
        this.set(this.fallback);
    }

    @Override
    public void revert() {
        this.set(this.saved);
    }

    @Override
    public void setSaved() {
        this.saved = this.value;
    }

    @Override
    public CustomPacketPayload wrap(int id) {
        return new DoubleConfigPayload(id, this.get());
    }

    @Override
    public ArgumentType<Double> getArgument() {
        return DoubleArgumentType.doubleArg(this.min, this.max);
    }

    @Override
    public Double parse(CommandContext<?> context, String name) {
        return DoubleArgumentType.getDouble(context, name);
    }
}
