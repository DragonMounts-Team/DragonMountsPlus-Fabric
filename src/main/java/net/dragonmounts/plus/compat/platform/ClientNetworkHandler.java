package net.dragonmounts.plus.compat.platform;

import net.dragonmounts.plus.common.capability.ArmorEffectManager.Provider;
import net.dragonmounts.plus.common.capability.ArmorEffectManagerImpl;
import net.dragonmounts.plus.common.client.ClientDragonEntity;
import net.dragonmounts.plus.common.component.DragonFood;
import net.dragonmounts.plus.common.entity.dragon.DragonLifeStage;
import net.dragonmounts.plus.common.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.plus.common.network.s2c.*;
import net.dragonmounts.plus.common.util.math.MathUtil;
import net.dragonmounts.plus.compat.registry.CooldownCategory;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

import static net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.registerGlobalReceiver;

public class ClientNetworkHandler {
    public static void send(CustomPacketPayload payload) {
        ClientPlayNetworking.send(payload);
    }

    public static void handleArmorRiposte(ArmorRipostePayload payload, ClientPlayNetworking.Context context) {
        var level = context.player().clientLevel;
        var entity = level.getEntity(payload.id());
        if (entity == null) return;
        int flag = payload.flag();
        double x = entity.getX();
        double z = entity.getZ();
        if ((flag & 0b01) == 0b01) {
            double y = entity.getY() + 0.1;
            for (int i = -30; i < 31; ++i) {
                level.addParticle(ParticleTypes.CLOUD, x, y, z, Math.sin(i), 0, Math.cos(i));
            }
            level.playLocalSound(entity, SoundEvents.GRASS_BREAK, SoundSource.PLAYERS, 0.46F, 1.0F);
        }
        if ((flag & 0b10) == 0b10) {
            double y = entity.getY() + 1;
            for (int i = -27; i < 28; ++i) {
                level.addParticle(ParticleTypes.FLAME, x, y, z, Math.sin(i) / 3, 0, Math.cos(i) / 3);
            }
            level.playLocalSound(entity, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.46F, 1.0F);
        }
    }

    public static void handleCooldownInit(InitCooldownPayload payload, @SuppressWarnings("unused") ClientPlayNetworking.Context context) {
        ArmorEffectManagerImpl.init(payload.data());
    }

    public static void handleCooldownSync(SyncCooldownPayload payload, ClientPlayNetworking.Context context) {
        var category = CooldownCategory.REGISTRY.byId(payload.id());
        if (category == null) return;
        ((Provider) context.player()).dragonmounts$plus$getManager().setCooldown(category, payload.cd());
    }

    public static void handleEggShake(ShakeEggPayload payload, ClientPlayNetworking.Context context) {
        if (context.player().clientLevel.getEntity(payload.id()) instanceof HatchableDragonEggEntity egg) {
            int flag = payload.flag();
            egg.syncShake(
                    payload.amplitude(),
                    (flag & 0b10) == 0b10 ? -payload.axis() : payload.axis(),
                    (flag & 0b01) == 0b01
            );
        }
    }

    public static void handleDragonSync(SyncDragonAgePayload payload, ClientPlayNetworking.Context context) {
        if (context.player().clientLevel.getEntity(payload.id()) instanceof ClientDragonEntity dragon) {
            dragon.setAge(payload.age());
            dragon.setLifeStage(payload.stage(), false, false);
        }
    }

    public static void handleFeedDragon(FeedDragonPayload payload, ClientPlayNetworking.Context context) {
        var level = context.player().clientLevel;
        if (level.getEntity(payload.id()) instanceof ClientDragonEntity dragon) {
            dragon.setAge(payload.age());
            dragon.setLifeStage(payload.stage(), false, false);
            var stack = payload.food();
            var food = DragonFood.getInstance(stack);
            if (food == null) return;
            if (dragon.getLifeStage() != DragonLifeStage.ADULT) {
                dragon.refreshForcedAgeTimer();
            }
            level.playLocalSound(dragon, food.majorSound().value(), SoundSource.NEUTRAL, 1F, 0.75F);
            food.minorSound().ifPresent(sound -> level.playLocalSound(dragon, sound.value(), SoundSource.NEUTRAL, 0.25F, 0.75F));
            var particles = food.particles().orElse(stack);
            if (particles.isEmpty()) return;
            var pos = dragon.getHeadRelativeOffset(0.0F, -5.0F, 22.0F);
            var option = new ItemParticleOption(ParticleTypes.ITEM, particles);
            var random = dragon.getRandom();
            float xRot = -dragon.getXRot() * MathUtil.TO_RAD_FACTOR, yRot = -dragon.getYRot() * MathUtil.TO_RAD_FACTOR;
            double cosX = Mth.cos(xRot), sinX = Mth.sin(xRot), cosY = Mth.cos(yRot), sinY = Mth.sin(yRot);
            for (int i = 0; i < 8; ++i) {
                double x = (random.nextFloat() - 0.5) * 0.1, y = random.nextFloat() * 0.1 + 0.1;
                level.addParticle(option, pos.x, pos.y, pos.z, x * cosY + x * sinX * sinY, y * cosX + 0.05, x * sinX * cosY - x * sinY);
            }
        }
    }

    public static void handleEggSync(SyncEggAgePayload payload, ClientPlayNetworking.Context context) {
        if (context.player().clientLevel.getEntity(payload.id()) instanceof HatchableDragonEggEntity egg) {
            egg.setAge(payload.age(), false);
        }
    }

    public static void handleEggPushable(EggPushablePayload payload, ClientPlayNetworking.Context context) {
        HatchableDragonEggEntity.IS_PUSHABLE = payload.value();
    }

    public static void initClient() {
        registerGlobalReceiver(SyncCooldownPayload.TYPE, ClientNetworkHandler::handleCooldownSync);
        registerGlobalReceiver(ArmorRipostePayload.TYPE, ClientNetworkHandler::handleArmorRiposte);
        registerGlobalReceiver(InitCooldownPayload.TYPE, ClientNetworkHandler::handleCooldownInit);
        registerGlobalReceiver(ShakeEggPayload.TYPE, ClientNetworkHandler::handleEggShake);
        registerGlobalReceiver(SyncDragonAgePayload.TYPE, ClientNetworkHandler::handleDragonSync);
        registerGlobalReceiver(FeedDragonPayload.TYPE, ClientNetworkHandler::handleFeedDragon);
        registerGlobalReceiver(SyncEggAgePayload.TYPE, ClientNetworkHandler::handleEggSync);
        registerGlobalReceiver(EggPushablePayload.TYPE, ClientNetworkHandler::handleEggPushable);
    }
}
