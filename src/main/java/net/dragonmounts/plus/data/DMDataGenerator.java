package net.dragonmounts.plus.data;

import net.dragonmounts.plus.common.init.DMStructureSets;
import net.dragonmounts.plus.common.init.DMStructures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.SnbtToNbt;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.nbt.CompoundTag;

import java.util.Collections;

public class DMDataGenerator implements DataGeneratorEntrypoint {
    static final boolean STRINGIFY_STRUCTURE = false;
    static final boolean UPDATE_STRUCTURE = false;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();
        pack.addProvider(DMModelProvider::new);
        pack.addProvider(DMBiomeTagProvider::new);
        pack.addProvider(DMEntityTagProvider::new);
        pack.addProvider(DMStructureTagProvider::new);
        var block = pack.addProvider(DMBlockTagProvider::new);
        pack.addProvider((output, future) -> new DMItemTagProvider(output, future, block));
        pack.addProvider(DMRecipeProvider.Factory::new);
        pack.addProvider(DMEquipmentAssetProvider::from);
        pack.addProvider(DMDynamicProvider::new);
        pack.addProvider(DMBlockLootProvider::new);
        pack.addProvider(DMChestLootProvider::new);
        pack.addProvider(DMEntityLootProvider::new);
        if (STRINGIFY_STRUCTURE) {
            pack.addProvider(DMDataGenerator::stringifyStructures);
        }
        if (UPDATE_STRUCTURE) {
            pack.addProvider(DMDataGenerator::updateStructures);
        }
    }

    public static NbtToSnbt stringifyStructures(FabricDataOutput output) {
        return new NbtToSnbt(
                new PackOutput(output.getOutputFolder().resolve(".cache").resolve("plain")),
                Collections.singleton(FabricLoader.getInstance().getGameDir().resolve("structures"))
        );
    }

    public static SnbtToNbt updateStructures(FabricDataOutput output) {
        return new SnbtToNbt(
                new PackOutput(output.getOutputFolder().resolve(".cache").resolve("updated")),
                Collections.singleton(FabricLoader.getInstance().getGameDir().resolve("structures"))
        ).addFilter(DMDataGenerator::updateStructure);
    }

    public static CompoundTag updateStructure(String path, CompoundTag structure) {
        var updated = StructureUpdater.update(path, structure);
        if (structure.contains("author")) {
            updated.putString("author", structure.getString("author"));
        }
        return updated;
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        builder.add(Registries.STRUCTURE, DMStructures::bootstrap);
        builder.add(Registries.STRUCTURE_SET, DMStructureSets::bootstrap);
    }
}