package net.dragonmounts.plus.compat.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class DeferredEntity<T extends Entity> extends DeferredHolder<EntityType<T>, EntityType<?>> {
    public static <T extends Entity> DeferredEntity<T> registerEntity(
            String name,
            MobCategory category,
            EntityType.EntityFactory<T> factory,
            Consumer<EntityType.Builder<T>> init
    ) {
        var builder = EntityType.Builder.of(factory, category);
        init.accept(builder);
        var key = makeKey(Registries.ENTITY_TYPE, name);
        return new DeferredEntity<>(key, builder);
    }

    public static <T extends LivingEntity> DeferredEntity<T> registerLivingEntity(
            String name,
            MobCategory category,
            EntityType.EntityFactory<T> factory,
            Supplier<AttributeSupplier.Builder> supplier,
            Consumer<EntityType.Builder<T>> init
    ) {
        var builder = FabricEntityType.Builder.createLiving(factory, category, type -> type.defaultAttributes(supplier));
        init.accept(builder);
        var key = makeKey(Registries.ENTITY_TYPE, name);
        return new DeferredEntity<>(key, builder);
    }

    public DeferredEntity(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        super(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    @SuppressWarnings("unchecked")
    public final <R extends T> EntityType<R> cast() {
        return (EntityType<R>) this.get();
    }
}
