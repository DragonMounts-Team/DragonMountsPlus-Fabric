package net.dragonmounts.plus;

import net.dragonmounts.plus.common.capability.ArmorEffectManager;
import net.dragonmounts.plus.common.capability.ArmorEffectManagerImpl;
import net.dragonmounts.plus.common.command.DMCommand;
import net.dragonmounts.plus.common.init.*;
import net.dragonmounts.plus.common.network.c2s.*;
import net.dragonmounts.plus.common.network.s2c.*;
import net.dragonmounts.plus.compat.platform.DMAttachments;
import net.dragonmounts.plus.compat.platform.DMGameRules;
import net.dragonmounts.plus.compat.platform.DMScreenHandlers;
import net.dragonmounts.plus.compat.platform.ServerNetworkHandler;
import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonVariant;
import net.dragonmounts.plus.config.ClientConfig;
import net.dragonmounts.plus.config.ServerConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
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

import static net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.registerGlobalReceiver;

public class DragonMounts implements ModInitializer, ServerPlayConnectionEvents.Join {
    public void onInitialize() {
        DMGameRules.init();
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
        initNetwork();
        EntityDataSerializers.registerSerializer(DragonType.SERIALIZER);
        EntityDataSerializers.registerSerializer(DragonVariant.SERIALIZER);
        CommandRegistrationCallback.EVENT.register(DMCommand::register);
        ServerPlayerEvents.COPY_FROM.register((player, priorPlayer, $) -> ArmorEffectManagerImpl.onPlayerClone(player, priorPlayer));
        AttackEntityCallback.EVENT.register(DMArmorEffects::meleeChanneling);
        ServerPlayConnectionEvents.JOIN.register(this);
    }

    static void initNetwork() {
        registerPayloads(PayloadTypeRegistry.playS2C());
        registerPayloads(PayloadTypeRegistry.playC2S());
        registerGlobalReceiver(ControlDragonPayload.TYPE, ServerNetworkHandler::handleDragonRiding);
        registerGlobalReceiver(TeleportDragonPayload.TYPE, ServerNetworkHandler::handleTeleportDragon);
        registerGlobalReceiver(ToggleSittingPayload.TYPE, ServerNetworkHandler::handleToggleSitting);
        registerGlobalReceiver(ToggleFollowingPayload.TYPE, ServerNetworkHandler::handleToggleFollowing);
        registerGlobalReceiver(RenameWhistlePayload.TYPE, ServerNetworkHandler::handleRenameWhistle);
    }

    static void registerPayloads(PayloadTypeRegistry<RegistryFriendlyByteBuf> registry) {
        registry.register(ArmorRipostePayload.TYPE, ArmorRipostePayload.CODEC);
        registry.register(EggPushablePayload.TYPE, EggPushablePayload.CODEC);
        registry.register(FeedDragonPayload.TYPE, FeedDragonPayload.CODEC);
        registry.register(InitCooldownPayload.TYPE, InitCooldownPayload.CODEC);
        registry.register(ControlDragonPayload.TYPE, ControlDragonPayload.CODEC);
        registry.register(ShakeEggPayload.TYPE, ShakeEggPayload.CODEC);
        registry.register(SyncCooldownPayload.TYPE, SyncCooldownPayload.CODEC);
        registry.register(SyncDragonAgePayload.TYPE, SyncDragonAgePayload.CODEC);
        registry.register(SyncEggAgePayload.TYPE, SyncEggAgePayload.CODEC);
        registry.register(TeleportDragonPayload.TYPE, TeleportDragonPayload.CODEC);
        registry.register(ToggleSittingPayload.TYPE, ToggleSittingPayload.CODEC);
        registry.register(ToggleFollowingPayload.TYPE, ToggleFollowingPayload.CODEC);
        registry.register(RenameWhistlePayload.TYPE, RenameWhistlePayload.CODEC);
    }

    @Override
    public void onPlayReady(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
        var player = handler.player;
        ((ArmorEffectManager.Provider) player).dragonmounts$plus$getManager().sendInitPacket();
        DMGameRules.sendInitPacket(player);
    }
}
