package net.dragonmounts.plus.config;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.common.DragonMountsShared;

import java.util.Collection;

import static net.dragonmounts.plus.config.EntryBuilder.config;

public class ClientConfig extends ConfigHolder {
    public static final ClientConfig INSTANCE = new ClientConfig(DragonMountsShared.NAMESPACE, "client.dat");
    protected final ObjectArrayList<ConfigValue<?>> values;
    public final BooleanEntry debug;
    public final DoubleEntry cameraDistance;
    public final DoubleEntry cameraOffset;
    public final BooleanEntry convergePitchAngle;
    public final BooleanEntry convergeYawAngle;
    public final BooleanEntry hoverState;
    public final BooleanEntry toggleDescending;
    public final BooleanEntry toggleBreathing;
    public final BooleanEntry pauseOnFluting;

    protected ClientConfig(String mod, String file) {
        super(mod, file);
        var values = new ObjectArrayList<ConfigValue<?>>();
        values.add(this.debug = config("debug", false));
        values.add(this.cameraDistance = config("cameraDistance", 20.0, 0.0, 64.0));
        values.add(this.cameraOffset = config("cameraOffset", 0.0, -32.0, 32.0));
        values.add(this.convergePitchAngle = config("convergePitchAngle", true));
        values.add(this.convergeYawAngle = config("convergeYawAngle", true));
        values.add(this.hoverState = config("hoverState", true));
        values.add(this.toggleDescending = config("toggleDescending", "key.dragonmounts.plus.descend", false));
        values.add(this.toggleBreathing = config("toggleBreathing", "key.dragonmounts.plus.breathe", false));
        values.add(this.pauseOnFluting = config("pauseOnFluting", true));
        this.values = values;
        this.load();
    }

    @Override
    public Collection<ConfigValue<?>> getValues() {
        return this.values;
    }

    @Override
    public void broadcast(ConfigValue<?> entry) {}

    public static void init() {}
}
