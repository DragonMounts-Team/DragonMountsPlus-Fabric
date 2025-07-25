package net.dragonmounts.plus.config;

import com.google.common.collect.HashBiMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.DragonMounts;
import net.dragonmounts.plus.common.DragonMountsShared;
import net.dragonmounts.plus.common.entity.dragon.HatchableDragonEggEntity;
import net.dragonmounts.plus.common.entity.dragon.TameableDragonEntity;
import net.dragonmounts.plus.compat.platform.ServerNetworkHandler;
import net.dragonmounts.plus.config.network.S2CSyncConfigPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.Collection;

import static net.dragonmounts.plus.config.EntryBuilder.config;

public class ServerConfig extends ConfigHolder {
    public static final ServerConfig INSTANCE = new ServerConfig(DragonMountsShared.NAMESPACE, "server.dat");
    protected final HashBiMap<ConfigValue<?>, Integer> entries;
    public final BooleanEntry debug;
    public final BooleanEntry isEggPushable;
    public final BooleanEntry isEggOverridden;
    public final BooleanEntry ignitingBreath;
    public final BooleanEntry destructiveBreath;
    public final BooleanEntry smeltingBreath;
    public final BooleanEntry quenchingBreath;
    public final BooleanEntry frostyBreath;
    public final DoubleEntry baseArmor;
    public final DoubleEntry baseArmorToughness;
    public final DoubleEntry baseBodySize;
    public final DoubleEntry baseDamage;
    public final DoubleEntry baseFlyingSpeed;
    public final DoubleEntry baseFollowRange;
    public final DoubleEntry baseHealth;
    public final DoubleEntry baseJumpStrength;
    public final DoubleEntry baseKnockback;
    public final DoubleEntry baseKnockbackResistance;
    public final DoubleEntry baseMovementSpeed;
    public final DoubleEntry baseStepHeight;
    public final DoubleEntry baseTemptRange;
    public final DoubleEntry baseWaterMovementEfficiency;
    private AttributeSupplier dragonAttributes;
    private AttributeSupplier dragonEggAttributes;

    public AttributeSupplier getDragonAttributes() {
        var attrs = this.dragonAttributes;
        if (attrs == null) {
            this.dragonAttributes = attrs = TameableDragonEntity.createAttributes().build();
        }
        return attrs;
    }

    public AttributeSupplier getDragonEggAttributes() {
        var attrs = this.dragonEggAttributes;
        if (attrs == null) {
            this.dragonEggAttributes = attrs = HatchableDragonEggEntity.createAttributes().build();
        }
        return attrs;
    }

    protected ServerConfig(String mod, String file) {
        super(mod, file);
        var registry = HashBiMap.<ConfigValue<?>, Integer>create();
        register(registry, this.debug =
                config("debug", false)
        );
        register(registry, this.isEggPushable =
                config("isEggPushable", false)
        );
        register(registry, this.isEggOverridden =
                config("isEggOverridden", true)
        );
        register(registry, this.ignitingBreath =
                config("ignitingBreath", true)
        );
        register(registry, this.destructiveBreath =
                config("destructiveBreath", true)
        );
        register(registry, this.smeltingBreath =
                config("smeltingBreath", false)
        );
        register(registry, this.quenchingBreath =
                config("quenchingBreath", true)
        );
        register(registry, this.frostyBreath =
                config("frostyBreath", false)
        );
        register(registry, this.baseArmor =
                config("baseArmor", 8.0, 0.0, 30.0, this::invalidateAttributes)
        );
        register(registry, this.baseArmorToughness =
                config("baseArmorToughness", 20.0, 0.0, 20.0, this::invalidateAttributes)
        );
        register(registry, this.baseBodySize =
                config("baseBodySize", 1.0, 0.0625, 16.0, this::invalidateAttributes)
        );
        register(registry, this.baseDamage =
                config("baseDamage", 12.0, 0.0, 2048.0, this::invalidateAttributes)
        );
        register(registry, this.baseFlyingSpeed =
                config("baseFlyingSpeed", 0.25, 0.0, 1024.0, this::invalidateAttributes)
        );
        register(registry, this.baseFollowRange =
                config("baseFollowRange", 64.0, 0.0, 2048.0, this::invalidateAttributes)
        );
        register(registry, this.baseHealth =
                config("baseHealth", 90.0, 1.0, 1024.0, this::invalidateAttributes)
        );
        register(registry, this.baseJumpStrength =
                config("baseJumpStrength", 1.0, 0.0, 32.0, this::invalidateAttributes)
        );
        register(registry, this.baseKnockback =
                config("baseKnockback", 0.0, 0.0, 5.0, this::invalidateAttributes)
        );
        register(registry, this.baseKnockbackResistance =
                config("baseKnockbackResistance", 1.0, 0.0, 1.0, this::invalidateAttributes)
        );
        register(registry, this.baseMovementSpeed =
                config("baseMovementSpeed", 0.4, 0.0, 1024.0, this::invalidateAttributes)
        );
        register(registry, this.baseStepHeight =
                config("baseStepHeight", 1.25, 0.0, 10, this::invalidateAttributes)
        );
        register(registry, this.baseTemptRange =
                config("baseTemptRange", 16.0, 0.0, 2048.0, this::invalidateAttributes)
        );
        register(registry, this.baseWaterMovementEfficiency =
                config("baseWaterMovementEfficiency", 0.25, 0.0, 1.0, this::invalidateAttributes)
        );
        this.entries = registry;
        this.load();
    }

    public ConfigValue<?> getValue(int id) {
        return this.entries.inverse().get(id);
    }

    @Override
    public void broadcast(ConfigValue<?> entry) {
        var server = DragonMounts.getRunningServer();
        if (server == null) return;
        Integer id = this.entries.get(entry);
        if (id == null) return;
        ServerNetworkHandler.sendToAll(server, entry.wrap(id));
    }

    @Override
    public Collection<ConfigValue<?>> getValues() {
        return this.entries.keySet();
    }

    public void sync(ServerPlayer player) {
        var entries = new ObjectArrayList<S2CSyncConfigPayload.Entry>();
        for (var entry : this.entries.entrySet()) {
            entries.add(new S2CSyncConfigPayload.Entry(entry.getValue(), entry.getKey().dump()));
        }
        ServerNetworkHandler.sendTo(player, new S2CSyncConfigPayload(entries));
    }

    protected static void register(HashBiMap<ConfigValue<?>, Integer> registry, ConfigValue<?> value) {
        registry.put(value, registry.size());
    }

    public void invalidateAttributes(double ignored) {
        this.dragonAttributes = null;
        this.dragonEggAttributes = null;
    }

    public static void init() {}
}
