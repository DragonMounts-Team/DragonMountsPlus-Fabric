package net.dragonmounts.plus.config;

import net.dragonmounts.plus.common.DragonMountsShared;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.CompoundTag;

public class ClientConfig extends ConfigHolder {
    public static final ClientConfig INSTANCE = new ClientConfig(DragonMountsShared.NAMESPACE);
    public final FloatEntry camera_distance = new FloatEntry("CameraDistance", "camera_distance", 20F);
    public final FloatEntry camera_offset = new FloatEntry("CameraOffset", "camera_offset", 0F);
    public final BooleanEntry converge_pitch_angle = new BooleanEntry("ConvergePitchAngle", "converge_pitch_angle", true);
    public final BooleanEntry converge_yaw_angle = new BooleanEntry("ConvergeYawAngle", "converge_yaw_angle", true);
    public final BooleanEntry hover_animation = new BooleanEntry("HoverAnimation", "hover_animation", true);
    public final BooleanEntry toggle_descending = new BooleanEntry("ToggleDescending", "toggle_descending", false);
    public final BooleanEntry toggle_breathing = new BooleanEntry("ToggleBreathing", "toggle_breathing", false);

    protected ClientConfig(String identifier) {
        super(FabricLoader.getInstance().getConfigDir().resolve(identifier).resolve("client.dat"), false);
        this.load();
    }

    @Override
    protected void read(CompoundTag tag) {
        this.debug.read(tag);
        this.camera_distance.read(tag);
        this.camera_offset.read(tag);
        this.converge_pitch_angle.read(tag);
        this.converge_yaw_angle.read(tag);
        this.hover_animation.read(tag);
        this.toggle_descending.read(tag);
        this.toggle_breathing.read(tag);
    }

    @Override
    protected CompoundTag write(CompoundTag tag) {
        this.debug.save(tag);
        this.camera_distance.save(tag);
        this.camera_offset.save(tag);
        this.converge_pitch_angle.save(tag);
        this.converge_yaw_angle.save(tag);
        this.hover_animation.save(tag);
        this.toggle_descending.save(tag);
        this.toggle_breathing.save(tag);
        return tag;
    }

    public static void init() {}
}
