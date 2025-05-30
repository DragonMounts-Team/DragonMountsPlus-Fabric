package net.dragonmounts.plus.common.init;

import io.netty.buffer.ByteBuf;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.common.inventory.DragonCoreHandler;
import net.dragonmounts.plus.common.inventory.DragonInventoryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import static net.dragonmounts.plus.common.client.ClientUtil.getLevel;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerMenu;

public class DMScreenHandlers {
    public static final MenuType<DragonCoreHandler> DRAGON_CORE = registerMenu("dragon_core", DragonCoreHandler::new, BlockPos.STREAM_CODEC);
    public static final MenuType<DragonInventoryHandler> DRAGON_INVENTORY = registerMenu("dragon_inventory", DragonInventoryHandler::new, new StreamCodec<ByteBuf, TameableDragonEntity>() {
        public @NotNull TameableDragonEntity decode(ByteBuf buffer) {
            if (getLevel().getEntity(VarInt.read(buffer)) instanceof TameableDragonEntity dragon) return dragon;
            throw new NullPointerException();
        }

        public void encode(ByteBuf buffer, TameableDragonEntity dragon) {
            VarInt.write(buffer, dragon.getId());
        }
    });

    public static void init() {}
}
