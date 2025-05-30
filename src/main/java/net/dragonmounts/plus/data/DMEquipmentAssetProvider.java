package net.dragonmounts.plus.data;

import net.dragonmounts.plus.common.init.DragonArmorMaterials;
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

import static net.minecraft.resources.ResourceLocation.withDefaultNamespace;

public class DMEquipmentAssetProvider implements DataProvider {
    private static EquipmentClientInfo makeAsset(String name) {
        return EquipmentClientInfo.builder()
                .addHumanoidLayers(withDefaultNamespace(name))
                .addLayers(
                        EquipmentClientInfo.LayerType.HORSE_BODY,
                        EquipmentClientInfo.Layer.leatherDyeable(withDefaultNamespace(name), false))
                .build();
    }

    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        output.accept(DragonArmorMaterials.IRON.assetId(), makeAsset("iron"));
        output.accept(DragonArmorMaterials.GOLD.assetId(), makeAsset("gold"));
        output.accept(DragonArmorMaterials.DIAMOND.assetId(), makeAsset("diamond"));
        output.accept(DragonArmorMaterials.EMERALD.assetId(), EquipmentClientInfo.builder().addLayers(
                // emeralds go well with wandering traders' llamas (x
                EquipmentClientInfo.LayerType.LLAMA_BODY,
                new EquipmentClientInfo.Layer(withDefaultNamespace("trader_llama"))
        ).build());
        output.accept(DragonArmorMaterials.NETHERITE.assetId(), EquipmentClientInfo.builder().addHumanoidLayers(withDefaultNamespace("netherite")).build());
        for (var type : DragonType.REGISTRY) {
            var material = type.material;
            if (material == ArmorMaterials.ARMADILLO_SCUTE) continue;
            var key = material.assetId();
            output.accept(key, EquipmentClientInfo.builder().addHumanoidLayers(key.location()).build());
        }
    }

    private final PackOutput.PathProvider pathProvider;

    public DMEquipmentAssetProvider(FabricDataOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput output) {
        Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> map = new HashMap<>();
        bootstrap((key, info) -> {
            if (map.putIfAbsent(key, info) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + key);
            }
        });
        return DataProvider.saveAll(output, EquipmentClientInfo.CODEC, this.pathProvider::json, map);
    }

    @Override
    public @NotNull String getName() {
        return "Dragon Mounts Equipment Asset Definitions";
    }
}
