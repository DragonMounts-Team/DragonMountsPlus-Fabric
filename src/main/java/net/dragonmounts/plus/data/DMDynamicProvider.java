package net.dragonmounts.plus.data;

import net.dragonmounts.plus.common.DragonMountsShared;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DMDynamicProvider extends FabricDynamicRegistryProvider {
    static <T> void addAll(Entries entries, HolderLookup.RegistryLookup<T> registry) {
        registry.listElementIds().forEach(key -> {
            if (DragonMountsShared.MOD_ID.equals(key.location().getNamespace())) {
                entries.add(registry, key);
            }
        });
    }

    public DMDynamicProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        addAll(entries, registries.lookupOrThrow(Registries.STRUCTURE));
        addAll(entries, registries.lookupOrThrow(Registries.STRUCTURE_SET));
    }

    @Override
    public @NotNull String getName() {
        return "Dragon Mounts Dynamic Definitions";
    }
}
