package net.dragonmounts.plus.common.client.gui;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DoubleRange implements OptionInstance.SliderableValueSet<Double> {
    public final double max;
    public final double min;
    public final double step;
    protected final double range;

    public DoubleRange(double min, double max, double step) {
        if (min >= max) throw new IllegalArgumentException();
        this.max = max;
        this.min = min;
        this.step = step;
        this.range = max - min;
    }

    @Override
    public double toSliderValue(Double value) {
        return value < this.max ? value > this.min ? (value - this.min) / this.range : 0.0 : 1.0;
    }

    @Override
    public @NotNull Double fromSliderValue(double delta) {
        return Math.round(Mth.lerp(delta, this.min, this.max) / this.step) * this.step;
    }

    @Override
    public @NotNull Optional<Double> validateValue(Double value) {
        return value.compareTo(this.min) < 0 || value.compareTo(this.max) > 0 ? Optional.empty() : Optional.of(value);
    }

    @Override
    public @NotNull Codec<Double> codec() {
        return Codec.doubleRange(this.min, this.max);
    }
}
