package net.dragonmounts.plus.compat.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public class RegistryHandler {
    public static Activity registerActivity(String name) {
        return Registry.register(BuiltInRegistries.ACTIVITY, makeId(name), new Activity(name));
    }

    public static <T extends ArmorEffect> T registerArmorEffect(ResourceLocation identifier, T effect) {
        return Registry.register(ArmorEffect.REGISTRY, identifier, effect);
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
