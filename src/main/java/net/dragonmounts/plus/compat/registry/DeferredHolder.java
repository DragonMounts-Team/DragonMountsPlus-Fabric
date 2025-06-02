package net.dragonmounts.plus.compat.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class DeferredHolder<V extends T, T> implements Supplier<V> {
    public final ResourceKey<T> key;
    private final V value;

    public DeferredHolder(Registry<T> registry, ResourceKey<T> key, V value) {
        this.key = key;
        this.value = Registry.register(registry, key, value);
    }

    public final boolean is(@Nullable T other) {
        return this.value == other;
    }

    public final boolean is(ItemStack stack) {
        return this.value == stack.getItem();
    }

    @Override
    public final V get() {
        return this.value;
    }

    @Override
    public final boolean equals(Object other) {
        return this == other || (
                other instanceof DeferredHolder<?, ?> that && Objects.equals(key, that.key)
        );
    }

    @Override
    public final int hashCode() {
        return this.key.hashCode();
    }
}
