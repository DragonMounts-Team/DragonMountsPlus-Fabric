package net.dragonmounts.plus.config;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.common.DragonMountsShared;

import java.util.Collection;

import static net.dragonmounts.plus.config.EntryBuilder.config;

public class ClientConfig extends ConfigHolder {
    public static final ClientConfig INSTANCE = new ClientConfig(DragonMountsShared.NAMESPACE, "client.dat");
    protected final ObjectArrayList<ConfigEntry<?>> entries;
    public final BooleanEntry debug;
    public final DoubleEntry cameraDistance;
    public final DoubleEntry cameraOffset;
    public final BooleanEntry convergePitchAngle;
    public final BooleanEntry convergeYawAngle;
    public final BooleanEntry hoverState;
    public final BooleanEntry toggleDescending;
    public final BooleanEntry toggleBreathing;
    public final BooleanEntry pauseOnWhistle;

    protected ClientConfig(String mod, String file) {
        super(mod, file);
        var entries = new ObjectArrayList<ConfigEntry<?>>();
        entries.add(this.debug = config(this, "debug", false));
        entries.add(this.cameraDistance = config(this, "cameraDistance", 20.0, 0.0, 64.0));
        entries.add(this.cameraOffset = config(this, "cameraOffset", 0.0, -32.0, 32.0));
        entries.add(this.convergePitchAngle = config(this, "convergePitchAngle", true));
        entries.add(this.convergeYawAngle = config(this, "convergeYawAngle", true));
        entries.add(this.hoverState = config(this, "hoverState", true));
        entries.add(this.toggleDescending = config(this, "toggleDescending", "key.dragonmounts.plus.descend", false));
        entries.add(this.toggleBreathing = config(this, "toggleBreathing", "key.dragonmounts.plus.breathe", false));
        entries.add(this.pauseOnWhistle = config(this, "pauseOnWhistle", true));
        this.entries = entries;
        this.local.load(this);
    }

    @Override
    public ConfigSource getSource() {
        return this.local;
    }

    @Override
    public Collection<ConfigEntry<?>> getEntries() {
        return this.entries;
    }

    @Override
    public void save() {
        this.local.save(this);
    }

    @Override
    public void broadcast(ConfigEntry<?> entry) {}

    public static void init() {}
}
