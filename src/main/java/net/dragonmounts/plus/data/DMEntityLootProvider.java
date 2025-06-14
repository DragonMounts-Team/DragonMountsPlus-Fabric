package net.dragonmounts.plus.data;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.dragonmounts.plus.common.init.DMItems;
import net.dragonmounts.plus.common.init.DragonTypes;
import net.dragonmounts.plus.common.item.DragonScalesItem;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.EntityEquipmentPredicate.Builder.equipment;
import static net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder.flags;
import static net.minecraft.advancements.critereon.EntityPredicate.Builder.entity;
import static net.minecraft.advancements.critereon.ItemEnchantmentsPredicate.enchantments;
import static net.minecraft.advancements.critereon.ItemPredicate.Builder.item;
import static net.minecraft.world.level.storage.loot.LootPool.lootPool;
import static net.minecraft.world.level.storage.loot.LootTable.lootTable;
import static net.minecraft.world.level.storage.loot.entries.LootItem.lootTableItem;
import static net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction.lootingMultiplier;
import static net.minecraft.world.level.storage.loot.functions.SetItemCountFunction.setCount;
import static net.minecraft.world.level.storage.loot.functions.SmeltItemFunction.smelted;
import static net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition.hasProperties;
import static net.minecraft.world.level.storage.loot.providers.number.UniformGenerator.between;

public class DMEntityLootProvider extends SimpleFabricLootTableProvider {
    private final ReferenceOpenHashSet<DragonType> types = new ReferenceOpenHashSet<>(DragonType.REGISTRY.iterator());
    private final CompletableFuture<HolderLookup.Provider> future;

    public DMEntityLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future, LootContextParamSets.ENTITY);
        this.future = future;
    }

    protected final AnyOfCondition.Builder shouldSmeltLoot(HolderLookup.Provider registries) {
        return AnyOfCondition.anyOf(
                hasProperties(LootContext.EntityTarget.THIS, entity().flags(flags().setOnFire(true))),
                hasProperties(LootContext.EntityTarget.DIRECT_ATTACKER, entity().equipment(equipment().mainhand(
                        item().withSubPredicate(ItemSubPredicates.ENCHANTMENTS, enchantments(List.of(new EnchantmentPredicate(
                                registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(EnchantmentTags.SMELTS_LOOT),
                                MinMaxBounds.Ints.ANY
                        ))))
                )))
        );
    }

    void makeLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output, DragonType type, Consumer<LootTable.Builder> action) {
        this.types.remove(type);
        var builder = lootTable();
        var scales = type.getInstance(DragonScalesItem.class, null);
        if (scales != null) {
            builder.withPool(lootPool().setRolls(between(1.0F, 2.0F))
                    .add(lootTableItem(scales).apply(setCount(between(3.0F, 7.0F))))
            );
        }
        action.accept(builder);
        output.accept(type.getLootTable(), builder);
    }

    LootTable.Builder dropMeat(LootTable.Builder builder) {
        var registries = this.future.join();
        return builder.withPool(lootPool().add(lootTableItem(DMItems.DRAGON_MEAT)
                .apply(setCount(between(3.0F, 8.0F)))
                .apply(smelted().when(this.shouldSmeltLoot(registries)))
                .apply(lootingMultiplier(registries, between(0.0F, 1.0F)))
        ));
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        this.makeLoot(output, DragonTypes.AETHER, this::dropMeat);
        this.makeLoot(output, DragonTypes.DARK, this::dropMeat);
        this.makeLoot(output, DragonTypes.ENCHANTED, this::dropMeat);
        this.makeLoot(output, DragonTypes.ENDER, this::dropMeat);
        this.makeLoot(output, DragonTypes.FIRE, this::dropMeat);
        this.makeLoot(output, DragonTypes.FOREST, this::dropMeat);
        this.makeLoot(output, DragonTypes.ICE, this::dropMeat);
        this.makeLoot(output, DragonTypes.MOONLIGHT, this::dropMeat);
        this.makeLoot(output, DragonTypes.NETHER, this::dropMeat);
        this.makeLoot(output, DragonTypes.SCULK, builder -> dropMeat(builder).withPool(lootPool()
                .add(lootTableItem(Items.SCULK_CATALYST)).setRolls(between(-1.0F, 1.0F))
        ));
        this.makeLoot(output, DragonTypes.SKELETON, builder -> builder.withPool(lootPool().setRolls(between(1.0F, 2.0F))
                .add(lootTableItem(Items.BONE).apply(setCount(between(3.0F, 7.0F)))))
        );
        this.makeLoot(output, DragonTypes.STORM, this::dropMeat);
        this.makeLoot(output, DragonTypes.SUNLIGHT, this::dropMeat);
        this.makeLoot(output, DragonTypes.TERRA, this::dropMeat);
        this.makeLoot(output, DragonTypes.WATER, this::dropMeat);
        this.makeLoot(output, DragonTypes.WITHER, builder -> builder.withPool(lootPool()
                .add(lootTableItem(Items.COAL).apply(setCount(between(0.0F, 5.0F))))
        ).withPool(lootPool().setRolls(between(1.0F, 2.0F))
                .add(lootTableItem(Items.BONE).apply(setCount(between(3.0F, 7.0F))))
        ));
        this.makeLoot(output, DragonTypes.ZOMBIE, builder -> builder.withPool(lootPool()
                .add(lootTableItem(Items.ROTTEN_FLESH)
                        .apply(setCount(between(8.0F, 18.0F)))
                        .apply(lootingMultiplier(this.future.join(), between(2.0F, 6.0F)))))
        );
        if (this.types.isEmpty()) return;
        for (var type : this.types) {
            LOGGER.error("Dragon with type {} has no loot table!", type.getId());
        }
    }
}
