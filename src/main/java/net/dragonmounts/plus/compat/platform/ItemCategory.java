package net.dragonmounts.plus.compat.platform;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class ItemCategory extends ItemGroup {
    public final List<CreativeModeTab.DisplayItemsGenerator> children;
    public final ResourceKey<CreativeModeTab> key;

    public ItemCategory(String name, String title, Supplier<ItemStack> icon, List<CreativeModeTab.DisplayItemsGenerator> children) {
        super(new ObjectArrayList<>());
        this.children = children;
        Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                this.key = makeKey(Registries.CREATIVE_MODE_TAB, name),
                FabricItemGroup.builder()
                        .title(Component.translatable(title))
                        .icon(icon)
                        .displayItems(this)
                        .build()
        );
    }

    @Override
    public void accept(@Nullable CreativeModeTab.ItemDisplayParameters args, CreativeModeTab.Output entries) {
        super.accept(args, entries);
        for (var child : this.children) {
            child.accept(args, entries);
        }
    }
}