package net.dragonmounts.plus.data;

import net.dragonmounts.plus.compat.registry.DragonType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public record DMEquipmentAssetProvider(PackOutput.PathProvider path) implements DataProvider {
    public static DMEquipmentAssetProvider from(FabricDataOutput output) {
        return new DMEquipmentAssetProvider(output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment"));
    }

    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        for (var type : DragonType.REGISTRY) {
            var material = type.material;
            if (material == ArmorMaterials.ARMADILLO_SCUTE) continue;
            var key = material.assetId();
            output.accept(key, EquipmentClientInfo.builder().addHumanoidLayers(key.location()).build());
        }
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput output) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> map = new HashMap<>();
        bootstrap((key, info) -> {
            if (map.putIfAbsent(key, info) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + key);
            }
        });
        return DataProvider.saveAll(output, EquipmentClientInfo.CODEC, this.path::json, map);
    }

    @Override
    public @NotNull String getName() {
        return "Dragon Mounts Equipment Asset Definitions";
    }
}
