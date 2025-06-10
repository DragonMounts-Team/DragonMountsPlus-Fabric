package net.dragonmounts.plus.data;

import net.dragonmounts.plus.common.init.DMBlocks;
import net.dragonmounts.plus.common.init.DragonVariants;
import net.dragonmounts.plus.compat.registry.BlockHolder;
import net.dragonmounts.plus.compat.registry.DragonVariant;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DMBlockLootProvider extends FabricBlockLootTableProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.concat(
            DMBlocks.BUILTIN_DRAGON_EGGS.stream().map(ItemLike::asItem),
            DragonVariants.BUILTIN_VALUES.stream().map(variant -> variant.head.item.get())
    ).collect(Collectors.toSet());

    protected DMBlockLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    public void dropSelf(BlockHolder<?> block) {
        this.dropSelf(block.get());
    }

    public void dropHead(DragonVariant variant) {
        var head = variant.head;
        this.dropOther(head.standing.get(), head);
    }

    @Override
    public void generate() {
        this.dropSelf(DMBlocks.DRAGON_NEST);
        DMBlocks.BUILTIN_DRAGON_EGGS.forEach(this::dropSelf);
        DMBlocks.BUILTIN_DRAGON_SCALE_BLOCKS.forEach(this::dropSelf);
        DragonVariants.BUILTIN_VALUES.forEach(this::dropHead);
        this.fixShear();
    }

    /// @see #hasShears()
    public void fixShear() {
        this.add(Blocks.GLOW_LICHEN, block -> this.createMultifaceBlockDrops(block, this.hasShears()));
        this.fixShearOrSilkTouch();
        this.fixShearsDispatchTable();
        this.fixShearsOnlyDrop();
        this.fixDoublePlantShearsDrop();
        this.fixDoublePlantWithSeedDrops();
    }

    /// @see #hasShearsOrSilkTouch()
    public void fixShearOrSilkTouch() {
        this.fixNotShearsNorSilkTouch();
        this.fixSilkTouchOrShearsDispatchTable();
        this.fixShearsOrSilkTouchOnlyDrop();
    }

    /// @see #createShearsDispatchTable(Block, LootPoolEntryContainer.Builder)
    public void fixShearsDispatchTable() {
        this.add(Blocks.DEAD_BUSH, block -> this.createShearsDispatchTable(block, this.applyExplosionDecay(
                block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
        )));
        this.fixGrassDrops();
    }

    /// @see #createShearsOnlyDrop(ItemLike)
    public void fixShearsOnlyDrop() {
        Function<Block, LootTable.Builder> fixer = this::createShearsOnlyDrop;
        this.add(Blocks.NETHER_SPROUTS, fixer);
        this.add(Blocks.SEAGRASS, fixer);
        this.add(Blocks.VINE, fixer);
        this.add(Blocks.HANGING_ROOTS, fixer);
        this.add(Blocks.SMALL_DRIPLEAF, fixer);
    }

    /// @see #createDoublePlantShearsDrop(Block)
    public void fixDoublePlantShearsDrop() {
        this.add(Blocks.TALL_SEAGRASS, this.createDoublePlantShearsDrop(Blocks.SEAGRASS));
    }

    /// @see #createDoublePlantWithSeedDrops(Block, Block)
    public void fixDoublePlantWithSeedDrops() {
        this.add(Blocks.LARGE_FERN, block -> this.createDoublePlantWithSeedDrops(block, Blocks.FERN));
        this.add(Blocks.TALL_GRASS, block -> this.createDoublePlantWithSeedDrops(block, Blocks.SHORT_GRASS));
    }

    /// @see #doesNotHaveShearsOrSilkTouch()
    public void fixNotShearsNorSilkTouch() {
        this.fixLeavesDrops();
        this.fixOakLeavesDrops();
    }

    /// @see #createSilkTouchOrShearsDispatchTable(Block, LootPoolEntryContainer.Builder)
    public void fixSilkTouchOrShearsDispatchTable() {
        this.add(Blocks.COBWEB, block -> this.createSilkTouchOrShearsDispatchTable(
                block, this.applyExplosionCondition(block, LootItem.lootTableItem(Items.STRING))
        ));
        this.fixMangroveLeavesDrops();
        this.fixNetherVinesDropTable();
    }

    /// @see #createShearsOrSilkTouchOnlyDrop(ItemLike)
    public void fixShearsOrSilkTouchOnlyDrop() {
        this.add(Blocks.PALE_HANGING_MOSS, this::createShearsOrSilkTouchOnlyDrop);
    }

    /// @see #createGrassDrops(Block)
    public void fixGrassDrops() {
        this.add(Blocks.FERN, this::createGrassDrops);
        this.add(Blocks.SHORT_GRASS, this::createGrassDrops);
    }

    /// @see #createLeavesDrops(Block, Block, float...)
    public void fixLeavesDrops() {
        this.add(Blocks.SPRUCE_LEAVES, block -> this.createLeavesDrops(block, Blocks.SPRUCE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.BIRCH_LEAVES, block -> this.createLeavesDrops(block, Blocks.BIRCH_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.JUNGLE_LEAVES, block -> this.createLeavesDrops(block, Blocks.JUNGLE_SAPLING, 0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F));
        this.add(Blocks.ACACIA_LEAVES, block -> this.createLeavesDrops(block, Blocks.ACACIA_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.PALE_OAK_LEAVES, block -> this.createLeavesDrops(block, Blocks.PALE_OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.CHERRY_LEAVES, block -> this.createLeavesDrops(block, Blocks.CHERRY_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.AZALEA_LEAVES, block -> this.createLeavesDrops(block, Blocks.AZALEA, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.FLOWERING_AZALEA_LEAVES, block -> this.createLeavesDrops(block, Blocks.FLOWERING_AZALEA, NORMAL_LEAVES_SAPLING_CHANCES));
    }

    /// @see #createOakLeavesDrops(Block, Block, float...)
    public void fixOakLeavesDrops() {
        this.add(Blocks.OAK_LEAVES, block -> this.createOakLeavesDrops(block, Blocks.OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(Blocks.DARK_OAK_LEAVES, block -> this.createOakLeavesDrops(block, Blocks.DARK_OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
    }

    /// @see #createMangroveLeavesDrops(Block)
    public void fixMangroveLeavesDrops() {
        this.add(Blocks.MANGROVE_LEAVES, this::createMangroveLeavesDrops);
    }

    /// @see #addNetherVinesDropTable(Block, Block)
    public void fixNetherVinesDropTable() {
        this.addNetherVinesDropTable(Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT);
        this.addNetherVinesDropTable(Blocks.TWISTING_VINES, Blocks.TWISTING_VINES_PLANT);
    }

    @Override
    public LootItemCondition.@NotNull Builder hasShears() {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), ConventionalItemTags.SHEAR_TOOLS));
    }

    @Override
    public <T extends FunctionUserBuilder<T>> @NotNull T applyExplosionDecay(ItemLike item, FunctionUserBuilder<T> builder) {
        return EXPLOSION_RESISTANT.contains(item.asItem())
                ? builder.unwrap()
                : builder.apply(ApplyExplosionDecay.explosionDecay());
    }

    @Override
    public <T extends ConditionUserBuilder<T>> @NotNull T applyExplosionCondition(ItemLike item, ConditionUserBuilder<T> builder) {
        return EXPLOSION_RESISTANT.contains(item.asItem())
                ? builder.unwrap()
                : builder.when(ExplosionCondition.survivesExplosion());
    }
}
