package net.dragonmounts.plus.compat.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class ObjectHolder<V extends T, T> implements Supplier<V> {
    public final ResourceKey<T> key;
    protected final Holder<T> holder;
    protected final V value;

    public ObjectHolder(Registry<T> registry, ResourceKey<T> key, V value) {
        this.key = key;
        this.holder = Registry.registerForHolder(registry, key, this.value = value);
    }

    public final Holder<T> wrap() {
        return this.holder;
    }

    @Override
    public final V get() {
        return this.value;
    }

    public final boolean is(@Nullable T other) {
        return this.value == other;
    }

    @Override
    public final boolean equals(Object other) {
        return this == other || (
                other instanceof ObjectHolder<?, ?> that && Objects.equals(this.key, that.key)
        );
    }

    @Override
    public final int hashCode() {
        return this.key.hashCode();
    }
}
