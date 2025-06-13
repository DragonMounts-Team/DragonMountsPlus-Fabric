package net.dragonmounts.plus.config;

import com.google.common.collect.HashBiMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.DragonMounts;
import net.dragonmounts.plus.common.DragonMountsShared;
import net.dragonmounts.plus.common.client.ClientUtil;
import net.dragonmounts.plus.compat.platform.PlatformCompat;
import net.dragonmounts.plus.compat.platform.ServerNetworkHandler;
import net.dragonmounts.plus.config.network.S2CSyncConfigPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

import static net.dragonmounts.plus.config.EntryBuilder.config;

public class ServerConfig extends ConfigHolder {
    public static final ServerConfig INSTANCE = new ServerConfig(DragonMountsShared.NAMESPACE, "server.dat");
    protected final HashBiMap<ConfigEntry<?>, Integer> entries;
    public final BooleanEntry debug;
    public final BooleanEntry isEggPushable;
    public final BooleanEntry isEggOverridden;
    public final BooleanEntry ignitingBreath;
    public final BooleanEntry destructiveBreath;
    public final BooleanEntry smeltingBreath;
    public final BooleanEntry quenchingBreath;
    public final BooleanEntry frostyBreath;
    public final DoubleEntry baseHealth;
    public final DoubleEntry baseDamage;
    public final DoubleEntry baseArmor;
    private @NotNull ConfigSource source = this.local;

    protected ServerConfig(String mod, String file) {
        super(mod, file);
        var registry = HashBiMap.<ConfigEntry<?>, Integer>create();
        register(registry, this.debug =
                config(this, "debug", false)
        );
        register(registry, this.isEggPushable =
                config(this, "isEggPushable", false)
        );
        register(registry, this.isEggOverridden =
                config(this, "isEggOverridden", true)
        );
        register(registry, this.ignitingBreath =
                config(this, "ignitingBreath", true)
        );
        register(registry, this.destructiveBreath =
                config(this, "destructiveBreath", true)
        );
        register(registry, this.smeltingBreath =
                config(this, "smeltingBreath", false)
        );
        register(registry, this.quenchingBreath =
                config(this, "quenchingBreath", true)
        );
        register(registry, this.frostyBreath =
                config(this, "frostyBreath", false)
        );
        register(registry, this.baseHealth =
                config(this, "baseHealth", 90.0, 1.0, 1024.0)
        );
        register(registry, this.baseDamage =
                config(this, "baseDamage", 12.0, 0.0, 2048.0)
        );
        register(registry, this.baseArmor =
                config(this, "baseArmor", 8.0, 0.0, 30.0)
        );
        this.entries = registry;
        this.local.load(this);
    }

    public ConfigEntry<?> getEntry(int id) {
        return this.entries.inverse().get(id);
    }

    @Override
    public @NotNull ConfigSource getSource() {
        return this.source;
    }

    public void setSource(@Nullable ConfigSource source) {
        var src = source == null ? this.local : source;
        if (this.source == src) return;
        this.source = src;
        src.load(this);
    }

    @Override
    public void save() {
        if (PlatformCompat.isClientSide() && ClientUtil.isRemoteServer()) return;
        this.local.save(this);
    }

    @Override
    public void broadcast(ConfigEntry<?> entry) {
        var server = DragonMounts.getRunningServer();
        if (server == null) return;
        Integer id = this.entries.get(entry);
        if (id == null) return;
        ServerNetworkHandler.sendToAll(server, entry.wrap(id));
    }

    @Override
    public Collection<ConfigEntry<?>> getEntries() {
        return this.entries.keySet();
    }

    public void sync(ServerPlayer player) {
        var entries = new ObjectArrayList<S2CSyncConfigPayload.Entry>();
        for (var entry : this.entries.entrySet()) {
            entries.add(new S2CSyncConfigPayload.Entry(entry.getValue(), entry.getKey().dump()));
        }
        ServerNetworkHandler.sendTo(player, new S2CSyncConfigPayload(entries));
    }

    protected static void register(HashBiMap<ConfigEntry<?>, Integer> registry, ConfigEntry<?> entry) {
        registry.put(entry, registry.size());
    }

    public static void init() {}
}
