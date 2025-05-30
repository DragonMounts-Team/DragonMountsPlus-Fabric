package net.dragonmounts.plus.compat.platform;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.compat.registry.RegistryHandler;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;

public class ItemGroup implements CreativeModeTab.DisplayItemsGenerator, ItemGroupEvents.ModifyEntries {
    public final ObjectArrayList<ItemLike> items;

    public ItemGroup(ObjectArrayList<ItemLike> items) {
        this.items = items;
    }

    public <T extends Item> T register(ResourceKey<Item> key, T item) {
        this.items.add(item);
        return RegistryHandler.registerItem(key, item);
    }

    public final <T extends Item> T register(ResourceLocation identifier, Function<Item.Properties, T> factory) {
        var key = ResourceKey.create(Registries.ITEM, identifier);
        return this.register(key, factory.apply(new Item.Properties().setId(key)));
    }

    public void add(ItemLike item) {
        this.items.add(item);
    }

    public void addAll(Collection<? extends ItemLike> items) {
        this.items.addAll(items);
    }

    @Override
    public void modifyEntries(FabricItemGroupEntries entries) {
        this.accept(null, entries);
    }

    @Override
    public void accept(@Nullable CreativeModeTab.ItemDisplayParameters args, CreativeModeTab.Output entries) {
        this.items.forEach(entries::accept);
    }
}
