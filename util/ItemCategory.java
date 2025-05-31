package net.dragonmounts.plus.common.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class ItemCategory extends ItemGroup {
    public final List<CreativeModeTab.DisplayItemsGenerator> children;
    public final ResourceKey<CreativeModeTab> key;

    public ItemCategory(String name, List<CreativeModeTab.DisplayItemsGenerator> children) {
        super(new ObjectArrayList<>());
        this.children = children;
        this.key = makeKey(Registries.CREATIVE_MODE_TAB, name);
    }

    @Override
    public void accept(@NotNull CreativeModeTab.ItemDisplayParameters args, CreativeModeTab.Output entries) {
        super.accept(args, entries);
        for (var child : this.children) {
            child.accept(args, entries);
        }
    }

    public interface Registry {
        void register(ItemCategory category, String title, Supplier<ItemStack> icon);
    }
}