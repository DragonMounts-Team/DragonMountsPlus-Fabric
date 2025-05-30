package net.dragonmounts.plus.compat.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class RegistryHandler {
    public static Activity registerActivity(String name) {
        return Registry.register(BuiltInRegistries.ACTIVITY, makeId(name), new Activity(name));
    }

    public static <T extends ArmorEffect> T registerArmorEffect(ResourceLocation identifier, T effect) {
        return Registry.register(ArmorEffect.REGISTRY, identifier, effect);
    }

    public static <T extends Block> T registerBlock(ResourceKey<Block> key, T block) {
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    public static <T extends Item> T registerItem(ResourceKey<Item> key, T item) {
        if (item instanceof BlockItem block) {
            block.registerBlocks(Item.BY_BLOCK, item);
        }
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static <T> MemoryModuleType<T> registerSensoryMemory(String name) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, makeId(name), new MemoryModuleType<>(Optional.empty()));
    }

    public static MemoryModuleType<Unit> registerMemory(String name) {
        return registerMemory(name, Unit.CODEC);
    }

    public static <T> MemoryModuleType<T> registerMemory(String name, Codec<T> codec) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, makeId(name), new MemoryModuleType<>(Optional.of(codec)));
    }

    public static <T extends ParticleOptions> ParticleType<T> registerParticle(
            String name,
            boolean overrideLimiter,
            MapCodec<T> codec,
            StreamCodec<? super RegistryFriendlyByteBuf, T> packetCodec
    ) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, makeId(name), FabricParticleTypes.complex(overrideLimiter, codec, packetCodec));
    }

    public static <T extends Sensor<?>> SensorType<T> registerSensor(String name, Supplier<T> factory) {
        return Registry.register(BuiltInRegistries.SENSOR_TYPE, makeId(name), new SensorType<>(factory));
    }

    public static <T extends SoundEvent> T registerSound(ResourceLocation identifier, T sound) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, sound);
    }

    public static <S extends Structure> StructureType<S> registerStructure(String name, StructureType<S> structure) {
        return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, makeId(name), structure);
    }

    public static StructurePieceType registerStructure(String name, StructurePieceType.StructureTemplateType piece) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, makeId(name), piece);
    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(
            String name,
            FabricBlockEntityTypeBuilder.Factory<T> factory,
            Block... blocks
    ) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, makeId(name), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    public static <T extends ArmorEffect> T registerArmorEffect(String name, Function<ResourceLocation, T> factory) {
        var identifier = makeId(name);
        return registerArmorEffect(identifier, factory.apply(identifier));
    }

    public static <T> DataComponentType<T> registerComponent(String name, UnaryOperator<DataComponentType.Builder<T>> operator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, makeId(name), operator.apply(new DataComponentType.Builder<>()).build());
    }

    public static <T extends ConsumeEffect> ConsumeEffect.Type<T> registerConsumeEffect(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> network) {
        return Registry.register(BuiltInRegistries.CONSUME_EFFECT_TYPE, makeId(name), new ConsumeEffect.Type<>(codec, network));
    }

    public static <T extends Entity> EntityType<T> registerEntity(
            String name,
            MobCategory category,
            EntityType.EntityFactory<T> factory,
            Consumer<EntityType.Builder<T>> init
    ) {
        var builder = EntityType.Builder.of(factory, category);
        init.accept(builder);
        var key = makeKey(Registries.ENTITY_TYPE, name);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static <T extends LivingEntity> EntityType<T> registerLivingEntity(
            String name,
            MobCategory category,
            EntityType.EntityFactory<T> factory,
            Supplier<AttributeSupplier.Builder> supplier,
            Consumer<EntityType.Builder<T>> init
    ) {
        var builder = FabricEntityType.Builder.createLiving(factory, category, type -> type.defaultAttributes(supplier));
        init.accept(builder);
        var key = makeKey(Registries.ENTITY_TYPE, name);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static <T extends Item> T registerItem(String name, Item.Properties props, Function<Item.Properties, T> factory) {
        var key = makeKey(Registries.ITEM, name);
        return registerItem(key, factory.apply(props.setId(key)));
    }

    public static <T extends AbstractContainerMenu, D> ExtendedScreenHandlerType<T, D> registerMenu(
            String name,
            ExtendedScreenHandlerType.ExtendedFactory<T, D> factory,
            StreamCodec<? super RegistryFriendlyByteBuf, D> codec
    ) {
        return Registry.register(BuiltInRegistries.MENU, makeId(name), new ExtendedScreenHandlerType<>(factory, codec));
    }

    public static <T> MappedRegistry<T> makeSimpleRegistry(ResourceKey<Registry<T>> key) {
        return FabricRegistryBuilder.createSimple(key).buildAndRegister();
    }

    public static <T> DefaultedMappedRegistry<T> makeDefaultedRegistry(ResourceKey<Registry<T>> key, ResourceLocation fallback) {
        return FabricRegistryBuilder.createDefaulted(key, fallback).buildAndRegister();
    }
}
