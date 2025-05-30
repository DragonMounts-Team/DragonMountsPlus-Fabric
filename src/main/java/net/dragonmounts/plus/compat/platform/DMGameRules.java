package net.dragonmounts.plus.compat.platform;

import net.dragonmounts.plus.common.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.plus.common.network.s2c.EggPushablePayload;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory.createBooleanRule;
import static net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory.createDoubleRule;
import static net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry.register;


public class DMGameRules {
    public static final double DEFAULT_DRAGON_BASE_HEALTH = 90D;
    public static final double DEFAULT_DRAGON_BASE_DAMAGE = 12D;
    public static final double DEFAULT_DRAGON_BASE_ARMOR = 8D;
    public static final CustomGameRuleCategory GAMEPLAY = new CustomGameRuleCategory(makeId("gameplay"), Component.translatable("gamerule.category.dragonmounts.plus.gameplay"));
    public static final GameRules.Key<DoubleRule> DRAGON_BASE_HEALTH = register("dragonmounts.plus.dragonBaseHealth", GameRules.Category.MOBS, createDoubleRule(DEFAULT_DRAGON_BASE_HEALTH, 1D, 1024D));
    public static final GameRules.Key<DoubleRule> DRAGON_BASE_DAMAGE = register("dragonmounts.plus.dragonBaseDamage", GameRules.Category.MOBS, createDoubleRule(DEFAULT_DRAGON_BASE_DAMAGE, 0D, 2048D));
    public static final GameRules.Key<DoubleRule> DRAGON_BASE_ARMOR = register("dragonmounts.plus.dragonBaseArmor", GameRules.Category.MOBS, createDoubleRule(DEFAULT_DRAGON_BASE_ARMOR, 0D, 30D));
    public static final GameRules.Key<GameRules.BooleanValue> IS_EGG_PUSHABLE = register("dragonmounts.plus.isEggPushable", GAMEPLAY, createBooleanRule(false, DMGameRules::syncEggPushable));
    public static final GameRules.Key<GameRules.BooleanValue> IS_EGG_OVERRIDDEN = register("dragonmounts.plus.isEggOverridden", GAMEPLAY, createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanValue> IGNITING_BREATH = register("dragonmounts.plus.ignitingBreath", GAMEPLAY, createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanValue> DESTRUCTIVE_BREATH = register("dragonmounts.plus.destructiveBreath", GAMEPLAY, createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanValue> SMELTING_BREATH = register("dragonmounts.plus.smeltingBreath", GAMEPLAY, createBooleanRule(false));
    public static final GameRules.Key<GameRules.BooleanValue> QUENCHING_BREATH = register("dragonmounts.plus.quenchingBreath", GAMEPLAY, createBooleanRule(true));
    public static final GameRules.Key<GameRules.BooleanValue> FROSTY_BREATH = register("dragonmounts.plus.frostyBreath", GAMEPLAY, createBooleanRule(false));

    public static void sendInitPacket(ServerPlayer player) {
        ServerNetworkHandler.sendTo(player, new EggPushablePayload(player.server.getGameRules().getBoolean(IS_EGG_PUSHABLE)));
    }

    public static void init() {}

    static void syncEggPushable(MinecraftServer server, GameRules.BooleanValue value) {
        HatchableDragonEggEntity.IS_PUSHABLE = value.get();
        ServerNetworkHandler.sendToAll(server, new EggPushablePayload(value.get()));
    }
}
