package net.dragonmounts.plus.data;

import net.dragonmounts.plus.common.block.DragonScaleBlock;
import net.dragonmounts.plus.common.init.DMBlocks;
import net.dragonmounts.plus.common.init.DMItems;
import net.dragonmounts.plus.common.item.*;
import net.dragonmounts.plus.common.tag.DMItemTags;
import net.dragonmounts.plus.compat.registry.DragonScaleArmorSuit;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.blasting;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.smelting;
import static net.minecraft.data.recipes.SmithingTransformRecipeBuilder.smithing;

public class DMRecipeProvider extends RecipeProvider {
    protected DMRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes() {
        var output = this.output;
        var registry = Registries.RECIPE;
        smelting(Ingredient.of(DMItems.IRON_DRAGON_ARMOR), RecipeCategory.MISC, Items.IRON_INGOT, 1.0F, 200)
                .unlockedBy("has_armor", has(DMItems.IRON_DRAGON_ARMOR))
                .save(output, makeKey(registry, "iron_ingot_form_smelting"));
        smelting(Ingredient.of(DMItems.GOLDEN_DRAGON_ARMOR), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 200)
                .unlockedBy("has_armor", has(DMItems.GOLDEN_DRAGON_ARMOR))
                .save(output, makeKey(registry, "gold_ingot_form_smelting"));
        blasting(Ingredient.of(DMItems.IRON_DRAGON_ARMOR), RecipeCategory.MISC, Items.IRON_INGOT, 1.0F, 100)
                .unlockedBy("has_armor", has(DMItems.IRON_DRAGON_ARMOR))
                .save(output, makeKey(registry, "iron_ingot_form_blasting"));
        blasting(Ingredient.of(DMItems.GOLDEN_DRAGON_ARMOR), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 100)
                .unlockedBy("has_armor", has(DMItems.GOLDEN_DRAGON_ARMOR))
                .save(output, makeKey(registry, "gold_ingot_form_blasting"));
        cook(100, (desc, time, method) -> method.cook(
                Ingredient.of(DMItems.DRAGON_MEAT), RecipeCategory.FOOD, DMItems.COOKED_DRAGON_MEAT, 0.35F, time
        ).unlockedBy("has_meat", has(DMItems.DRAGON_MEAT)).save(this.output, makeKey(Registries.RECIPE, "cooked_dragon_meat_form_" + desc)));
        this.dragonArmor(ConventionalItemTags.IRON_INGOTS, ConventionalItemTags.STORAGE_BLOCKS_IRON, DMItems.IRON_DRAGON_ARMOR);
        this.dragonArmor(ConventionalItemTags.GOLD_INGOTS, ConventionalItemTags.STORAGE_BLOCKS_GOLD, DMItems.GOLDEN_DRAGON_ARMOR);
        this.dragonArmor(ConventionalItemTags.EMERALD_GEMS, ConventionalItemTags.STORAGE_BLOCKS_EMERALD, DMItems.EMERALD_DRAGON_ARMOR);
        this.dragonArmor(ConventionalItemTags.DIAMOND_GEMS, ConventionalItemTags.STORAGE_BLOCKS_DIAMOND, DMItems.DIAMOND_DRAGON_ARMOR);
        for (DragonType type : DragonType.REGISTRY) {
            var scales = type.getInstance(DragonScalesItem.class, null);
            if (scales == null) continue;
            this.dragonScaleAxe(scales, type.getInstance(DragonScaleAxeItem.class, null));
            this.dragonScaleArmors(scales, type.getInstance(DragonScaleArmorSuit.class, null));
            this.dragonScaleBlock(scales, type.getInstance(DragonScaleBlock.class, null));
            this.dragonScaleBow(scales, type.getInstance(DragonScaleBowItem.class, null));
            this.dragonScaleHoe(scales, type.getInstance(DragonScaleHoeItem.class, null));
            this.dragonScalePickaxe(scales, type.getInstance(DragonScalePickaxeItem.class, null));
            this.dragonScaleShovel(scales, type.getInstance(DragonScaleShovelItem.class, null));
            this.dragonScaleShield(scales, type.getInstance(DragonScaleShieldItem.class, null));
            this.dragonScaleSword(scales, type.getInstance(DragonScaleSwordItem.class, null));
        }
        this.shaped(RecipeCategory.TOOLS, DMItems.DIAMOND_SHEARS)
                .define('X', ConventionalItemTags.DIAMOND_GEMS)
                .pattern(" X")
                .pattern("X ")
                .unlockedBy("has_diamond", has(ConventionalItemTags.DIAMOND_GEMS))
                .save(output);
        smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(DMItems.DIAMOND_SHEARS),
                this.tag(ItemTags.NETHERITE_TOOL_MATERIALS),
                RecipeCategory.TOOLS,
                DMItems.NETHERITE_SHEARS.get()
        ).unlocks("has_netherite_ingot", this.has(ItemTags.NETHERITE_TOOL_MATERIALS))
                .save(this.output, makeKey(registry, "netherite_shears_from_smithing"));
        this.shaped(RecipeCategory.REDSTONE, Items.DISPENSER)
                .define('R', ConventionalItemTags.REDSTONE_DUSTS)
                .define('#', ConventionalItemTags.COBBLESTONES)
                .define('X', DMItemTags.DRAGON_SCALE_BOWS)
                .pattern("###")
                .pattern("#X#")
                .pattern("#R#")
                .unlockedBy("has_bow", has(DMItemTags.DRAGON_SCALE_BOWS))
                .save(output, makeKey(registry, getItemName(Blocks.DISPENSER)));
        this.shaped(RecipeCategory.TOOLS, DMItems.AMULET)
                .define('#', ConventionalItemTags.STRINGS)
                .define('Y', ConventionalItemTags.COBBLESTONES)
                .define('X', ConventionalItemTags.ENDER_PEARLS)
                .pattern(" Y ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_pearls", has(ConventionalItemTags.ENDER_PEARLS))
                .save(output);
        this.shaped(RecipeCategory.DECORATIONS, DMBlocks.DRAGON_NEST)
                .define('X', ConventionalItemTags.WOODEN_RODS)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_sticks", has(ConventionalItemTags.WOODEN_RODS))
                .save(output);
        this.shaped(RecipeCategory.TOOLS, DMItems.WHISTLE)
                .define('P', ConventionalItemTags.WOODEN_RODS)
                .define('#', ConventionalItemTags.ENDER_PEARLS)
                .define('X', ConventionalItemTags.STRINGS)
                .pattern("P#")
                .pattern("#X")
                .unlockedBy("has_pearls", has(ConventionalItemTags.ENDER_PEARLS))
                .save(output);
        this.shaped(RecipeCategory.TOOLS, Items.SADDLE)
                .define('#', ConventionalItemTags.IRON_INGOTS)
                .define('X', ConventionalItemTags.LEATHERS)
                .pattern(" X ")
                .pattern("X#X")
                .unlockedBy("has_leather", has(ConventionalItemTags.LEATHERS))
                .save(output, makeKey(registry, getItemName(Items.SADDLE)));
        this.shaped(RecipeCategory.TOOLS, DMItems.VARIATION_ORB)
                .define('O', Items.ENDER_EYE)
                .define('#', ConventionalItemTags.AMETHYST_GEMS)
                .define('*', ConventionalItemTags.GOLD_INGOTS)
                .pattern("*#*")
                .pattern("#O#")
                .pattern("*#*")
                .unlockedBy("has_amethyst", has(ConventionalItemTags.AMETHYST_GEMS))
                .save(output);
    }

    public static void cook(int unit, CookingRecipeBuilder builder) {
        builder.build("smelting", unit * 2, SimpleCookingRecipeBuilder::smelting);
        builder.build("smoking", unit, SimpleCookingRecipeBuilder::smoking);
        builder.build("campfire", unit * 6, SimpleCookingRecipeBuilder::campfireCooking);
    }

    void dragonArmor(TagKey<Item> ingot, TagKey<Item> block, ItemLike result) {
        this.shaped(RecipeCategory.COMBAT, result)
                .define('#', ingot)
                .define('X', block)
                .pattern("X #")
                .pattern(" XX")
                .pattern("## ")
                .group("dragonmounts.plus.dragon_armor")
                .unlockedBy("has_ingot", has(ingot))
                .unlockedBy("has_block", has(block))
                .save(this.output);
    }

    void dragonScaleAxe(Item scales, Item result) {
        if (result == null) return;
        this.shaped(RecipeCategory.TOOLS, result)
                .define('#', ConventionalItemTags.WOODEN_RODS)
                .define('X', scales)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .group("dragonmounts.plus.dragon_scale_axe")
                .unlockedBy("has_dragon_scales", has(scales))
                .save(this.output);
    }

    private void dragonScaleArmors(Item scales, DragonScaleArmorSuit suit) {
        if (suit == null) return;
        var hasScales = has(scales);
        this.shaped(RecipeCategory.COMBAT, suit.getHelmet())
                .define('X', scales)
                .pattern("XXX")
                .pattern("X X")
                .group("dragonmounts.plus.dragon_scale_helmet")
                .unlockedBy("has_dragon_scales", hasScales)
                .save(this.output);
        this.shaped(RecipeCategory.COMBAT, suit.getChestplate())
                .define('X', scales)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .group("dragonmounts.plus.dragon_scale_chestplate")
                .unlockedBy("has_dragon_scales", hasScales)
                .save(this.output);
        this.shaped(RecipeCategory.COMBAT, suit.getLeggings())
                .define('X', scales)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .group("dragonmounts.plus.dragon_scale_leggings")
                .unlockedBy("has_dragon_scales", hasScales)
                .save(this.output);
        this.shaped(RecipeCategory.COMBAT, suit.getBoots())
                .define('X', scales)
                .pattern("X X")
                .pattern("X X")
                .group("dragonmounts.plus.dragon_scale_boots")
                .unlockedBy("has_dragon_scales", hasScales)
                .save(this.output);
    }

    private void dragonScaleBlock(Item scales, ItemLike result) {
        if (result == null) return;
        this.shapeless(RecipeCategory.MISC, scales, 9)
                .requires(result)
                .group("dragonmounts.plus.dragon_scales")
                .unlockedBy(getHasName(result), this.has(result))
                .save(this.output, ResourceKey.create(Registries.RECIPE, BuiltInRegistries.ITEM.getKey(scales)));
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('#', scales)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .group("dragonmounts.plus.dragon_scale_block")
                .unlockedBy(getHasName(scales), this.has(scales))
                .save(this.output, ResourceKey.create(Registries.RECIPE, BuiltInRegistries.ITEM.getKey(result.asItem())));
    }

    private void dragonScaleBow(Item scales, Item result) {
        if (result == null) return;
        this.shaped(RecipeCategory.COMBAT, result)
                .define('#', scales)
                .define('X', ConventionalItemTags.STRINGS)
                .pattern(" #X")
                .pattern("# X")
                .pattern(" #X")
                .group("dragonmounts.plus.dragon_scale_bow")
                .unlockedBy("has_dragon_scales", has(scales))
                .save(this.output);
    }

    private void dragonScaleHoe(Item scales, Item result) {
        if (result == null) return;
        this.shaped(RecipeCategory.TOOLS, result)
                .define('#', ConventionalItemTags.WOODEN_RODS)
                .define('X', scales)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .group("dragonmounts.plus.dragon_scale_hoe")
                .unlockedBy("has_dragon_scales", has(scales))
                .save(this.output);
    }

    private void dragonScalePickaxe(Item scales, Item result) {
        if (result == null) return;
        this.shaped(RecipeCategory.TOOLS, result)
                .define('#', ConventionalItemTags.WOODEN_RODS)
                .define('X', scales)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .group("dragonmounts.plus.dragon_scale_pickaxe")
                .unlockedBy("has_dragon_scales", has(scales))
                .save(this.output);
    }

    private void dragonScaleShield(Item scales, Item result) {
        if (result == null) return;
        this.shaped(RecipeCategory.COMBAT, result)
                .define('X', ConventionalItemTags.IRON_INGOTS)
                .define('W', scales)
                .pattern("WXW")
                .pattern("WWW")
                .pattern(" W ")
                .group("dragonmounts.plus.dragon_scale_shield")
                .unlockedBy("has_dragon_scales", has(scales))
                .save(this.output);
    }

    private void dragonScaleShovel(Item scales, Item result) {
        if (result == null) return;
        this.shaped(RecipeCategory.TOOLS, result)
                .define('#', ConventionalItemTags.WOODEN_RODS)
                .define('X', scales)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .group("dragonmounts.plus.dragon_scale_shovel")
                .unlockedBy("has_dragon_scales", has(scales))
                .save(this.output);
    }

    private void dragonScaleSword(Item scales, Item result) {
        if (result == null) return;
        this.shaped(RecipeCategory.COMBAT, result)
                .define('#', ConventionalItemTags.WOODEN_RODS)
                .define('X', scales)
                .pattern("X")
                .pattern("X")
                .pattern("#")
                .unlockedBy("has_dragon_scales", has(scales))
                .save(this.output);
    }

    public static class Factory extends FabricRecipeProvider {
        public Factory(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new DMRecipeProvider(registries, output);
        }

        @Override
        protected ResourceLocation getRecipeIdentifier(ResourceLocation identifier) {
            return makeId(identifier.getPath());
        }

        @Override
        public @NotNull String getName() {
            return "Dragon Mounts Recipes";
        }
    }

    public interface CookingMethod {
        RecipeBuilder cook(Ingredient ingredient, RecipeCategory category, ItemLike result, float experience, int time);
    }

    public interface CookingRecipeBuilder {
        void build(String desc, int time, CookingMethod method);
    }
}
