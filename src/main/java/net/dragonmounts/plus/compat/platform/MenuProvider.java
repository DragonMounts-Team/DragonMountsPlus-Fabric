package net.dragonmounts.plus.compat.platform;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;

public interface MenuProvider<D> extends ExtendedScreenHandlerFactory<D> {
    /// unused
    void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer);
}