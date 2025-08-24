package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.crafting.DragonArmorUpgradeRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerRecipe;

public interface DMRecipes {
    RecipeSerializer<DragonArmorUpgradeRecipe> DRAGON_ARMOR_UPGRADE = registerRecipe(
            makeId("smithing_special_dragon_armor_upgrade"),
            new DragonArmorUpgradeRecipe.Serializer()
    );

    static void init() {}
}
