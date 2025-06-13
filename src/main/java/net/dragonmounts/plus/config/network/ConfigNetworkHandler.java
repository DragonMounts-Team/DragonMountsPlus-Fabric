package net.dragonmounts.plus.config.network;

import net.dragonmounts.plus.common.client.ClientUtil;
import net.dragonmounts.plus.config.ServerConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;

public class ConfigNetworkHandler {
    public static void handleSyncConfig(S2CSyncConfigPayload payload, ClientPlayNetworking.Context ignored) {
        if (ClientUtil.isRemoteServer()) {
            var cache = new CompoundTag();
            for (var config : payload.entries()) {
                var entry = ServerConfig.INSTANCE.getEntry(config.id());
                if (entry != null) {
                    cache.put(entry.key, config.value());
                }
            }
            ServerConfig.INSTANCE.setSource(new RemoteSource(cache));
        } else {
            ServerConfig.INSTANCE.setSource(null);
        }
    }

    public static void handleBooleanConfig(S2CBooleanConfigPayload payload, ClientPlayNetworking.Context ignored) {
        var entry = ServerConfig.INSTANCE.getEntry(payload.id());
        if (entry != null && ServerConfig.INSTANCE.getSource() instanceof RemoteSource source) {
            source.update(entry, ByteTag.valueOf(payload.value()));
        }
    }

    public static void handleDoubleConfig(S2CDoubleConfigPayload payload, ClientPlayNetworking.Context ignored) {
        var entry = ServerConfig.INSTANCE.getEntry(payload.id());
        if (entry != null && ServerConfig.INSTANCE.getSource() instanceof RemoteSource source) {
            source.update(entry, DoubleTag.valueOf(payload.value()));
        }
    }
}
