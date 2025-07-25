package net.dragonmounts.plus.config.network;

import net.dragonmounts.plus.common.client.ClientUtil;
import net.dragonmounts.plus.config.ConfigValue;
import net.dragonmounts.plus.config.ServerConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.Tag;

public class ConfigNetworkHandler {
    public static void handleSyncConfig(S2CSyncConfigPayload payload, ClientPlayNetworking.Context ignored) {
        ServerConfig.INSTANCE.getValues().forEach(ConfigValue::reset);
        if (ClientUtil.isRemoteServer()) {
            for (var config : payload.entries()) {
                var value = ServerConfig.INSTANCE.getValue(config.id());
                if (value == null) continue;
                override(value, config.value());
            }
        }
    }

    public static void handleBooleanConfig(S2CBooleanConfigPayload payload, ClientPlayNetworking.Context ignored) {
        var value = ServerConfig.INSTANCE.getValue(payload.id());
        if (value == null) return;
        override(value, ByteTag.valueOf(payload.value()));
    }

    public static void handleDoubleConfig(S2CDoubleConfigPayload payload, ClientPlayNetworking.Context ignored) {
        var value = ServerConfig.INSTANCE.getValue(payload.id());
        if (value == null) return;
        override(value, DoubleTag.valueOf(payload.value()));
    }

    public static <T> void override(ConfigValue<T> value, Tag data) {
        value.override(value.load(data));
    }
}
