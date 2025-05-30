package net.dragonmounts.plus.common.util;

import net.dragonmounts.plus.common.structure.DragonNestStructure;
import net.dragonmounts.plus.common.structure.NestConfig;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.List;

public final class DragonNestRegistry {
    public final BootstrapContext<Structure> context;
    public final HolderGetter<Biome> biomes;

    public DragonNestRegistry(BootstrapContext<Structure> context) {
        this.context = context;
        this.biomes = context.lookup(Registries.BIOME);
    }

    public void register(ResourceKey<Structure> key, TagKey<Biome> biome, NestConfig... configs) {
        this.context.register(key, new DragonNestStructure(
                new Structure.StructureSettings(this.biomes.getOrThrow(biome)),
                List.of(configs)
        ));
    }
}
