package net.dragonmounts.plus;

import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.capability.ArmorEffectManagerImpl;
import net.dragonmounts.plus.common.command.DMCommand;
import net.dragonmounts.plus.common.init.*;
import net.dragonmounts.plus.common.network.c2s.*;
import net.dragonmounts.plus.common.network.s2c.*;
import net.dragonmounts.plus.compat.platform.DMAttachments;
import net.dragonmounts.plus.compat.platform.DMScreenHandlers;
import net.dragonmounts.plus.compat.platform.ServerNetworkHandler;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonVariant;
import net.dragonmounts.plus.config.ClientConfig;
import net.dragonmounts.plus.config.ConfigValue;
import net.dragonmounts.plus.config.ServerConfig;
import net.dragonmounts.plus.config.network.S2CBooleanConfigPayload;
import net.dragonmounts.plus.config.network.S2CDoubleConfigPayload;
import net.dragonmounts.plus.config.network.S2CSyncConfigPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;

import static net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.registerGlobalReceiver;

public class DragonMounts implements ModInitializer, ServerPlayConnectionEvents.Join, ServerLifecycleEvents.ServerStarting, ServerLifecycleEvents.ServerStopped {
    private static MinecraftServer RUNNING_SERVER;

    public static @Nullable MinecraftServer getRunningServer() {
        return RUNNING_SERVER;
    }

    public void onInitialize() {
        ClientConfig.init();
        ServerConfig.init();
        DMDataComponents.init();
        DMEntities.init();
        DMItems.init();
        DMBlocks.init();
        DMBlockEntities.init();
        DMScreenHandlers.init();
        DMItemGroups.register((category, title, icon) -> Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                category.key,
                FabricItemGroup.builder()
                        .title(Component.translatable(title))
                        .icon(icon)
                        .displayItems(category)
                        .build()
        ));
        DMAttachments.init();
        DMSounds.init();
        DMActivities.init();
        DMMemories.init();
        DMSensors.init();
        DMStructures.init();
        DMParticles.init();
        DMMobEffects.init();
        initNetwork();
        EntityDataSerializers.registerSerializer(DragonType.SERIALIZER);
        EntityDataSerializers.registerSerializer(DragonVariant.SERIALIZER);
        CommandRegistrationCallback.EVENT.register(DMCommand::register);
        ServerPlayerEvents.COPY_FROM.register((player, priorPlayer, $) -> ArmorEffectManagerImpl.onPlayerClone(player, priorPlayer));
        AttackEntityCallback.EVENT.register(DMArmorEffects::meleeChanneling);
        ServerPlayConnectionEvents.JOIN.register(this);
        ServerLifecycleEvents.SERVER_STARTING.register(this);
        ServerLifecycleEvents.SERVER_STOPPED.register(this);
    }

    static void initNetwork() {
        registerPayloads(PayloadTypeRegistry.playS2C());
        registerPayloads(PayloadTypeRegistry.playC2S());
        registerGlobalReceiver(ControlDragonPayload.TYPE, ServerNetworkHandler::handleDragonRiding);
        registerGlobalReceiver(TeleportDragonPayload.TYPE, ServerNetworkHandler::handleTeleportDragon);
        registerGlobalReceiver(ToggleSittingByUUIDPayload.TYPE, ServerNetworkHandler::handleToggleSitting);
        registerGlobalReceiver(ToggleSittingByIDPayload.TYPE, ServerNetworkHandler::handleToggleSitting);
        registerGlobalReceiver(ToggleTrustPayload.TYPE, ServerNetworkHandler::handleToggleTrust);
        registerGlobalReceiver(ToggleFollowingPayload.TYPE, ServerNetworkHandler::handleToggleFollowing);
        registerGlobalReceiver(RenameFlutePayload.TYPE, ServerNetworkHandler::handleRenameFlute);
    }

    static void registerPayloads(PayloadTypeRegistry<RegistryFriendlyByteBuf> registry) {
        registry.register(ArmorRipostePayload.TYPE, ArmorRipostePayload.CODEC);
        registry.register(FeedDragonPayload.TYPE, FeedDragonPayload.CODEC);
        registry.register(InitCooldownPayload.TYPE, InitCooldownPayload.CODEC);
        registry.register(ControlDragonPayload.TYPE, ControlDragonPayload.CODEC);
        registry.register(ShakeEggPayload.TYPE, ShakeEggPayload.CODEC);
        registry.register(SyncCooldownPayload.TYPE, SyncCooldownPayload.CODEC);
        registry.register(SyncDragonAgePayload.TYPE, SyncDragonAgePayload.CODEC);
        registry.register(SyncEggAgePayload.TYPE, SyncEggAgePayload.CODEC);
        registry.register(TeleportDragonPayload.TYPE, TeleportDragonPayload.CODEC);
        registry.register(ToggleSittingByUUIDPayload.TYPE, ToggleSittingByUUIDPayload.CODEC);
        registry.register(ToggleSittingByIDPayload.TYPE, ToggleSittingByIDPayload.CODEC);
        registry.register(ToggleTrustPayload.TYPE, ToggleTrustPayload.CODEC);
        registry.register(ToggleFollowingPayload.TYPE, ToggleFollowingPayload.CODEC);
        registry.register(RenameFlutePayload.TYPE, RenameFlutePayload.CODEC);
        registry.register(S2CSyncConfigPayload.TYPE, S2CSyncConfigPayload.CODEC);
        registry.register(S2CBooleanConfigPayload.TYPE, S2CBooleanConfigPayload.CODEC);
        registry.register(S2CDoubleConfigPayload.TYPE, S2CDoubleConfigPayload.CODEC);
    }

    @Override
    public void onPlayReady(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
        var player = handler.player;
        ((ArmorEffectManager.Provider) player).dragonmounts$plus$getManager().sendInitPacket();
        ServerConfig.INSTANCE.sync(player);
    }

    @Override
    public void onServerStarting(MinecraftServer server) {
        RUNNING_SERVER = server;
        ServerConfig.INSTANCE.getValues().forEach(ConfigValue::reset);
    }

    @Override
    public void onServerStopped(MinecraftServer server) {
        RUNNING_SERVER = null;
    }
}
