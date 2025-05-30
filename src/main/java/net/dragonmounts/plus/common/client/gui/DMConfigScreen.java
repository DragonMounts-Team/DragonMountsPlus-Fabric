package net.dragonmounts.plus.common.client.gui;

import net.dragonmounts.plus.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

import static net.dragonmounts.plus.common.client.gui.LazyBooleanConfigOption.TOGGLE_STRINGIFIER;
import static net.dragonmounts.plus.common.client.gui.LazyFloatConfigOption.X_2F_STRINGIFIER;
import static net.minecraft.client.OptionInstance.BOOLEAN_TO_STRING;

public class DMConfigScreen extends OptionsSubScreen {
    protected static final LazyBooleanConfigOption DEBUG;
    protected static final LazyFloatConfigOption CAMERA_DISTANCE;
    protected static final LazyFloatConfigOption CAMERA_OFFSET;
    protected static final LazyBooleanConfigOption CONVERGE_PITCH;
    protected static final LazyBooleanConfigOption CONVERGE_YAW;
    protected static final LazyBooleanConfigOption HOVER_ANIMATION;
    protected static final LazyBooleanConfigOption TOGGLE_DESCENDING;
    protected static final LazyBooleanConfigOption TOGGLE_BREATHING;

    static {
        ClientConfig config = ClientConfig.INSTANCE;
        DEBUG = new LazyBooleanConfigOption("options.dragonmounts.plus.debug", config.debug, null, BOOLEAN_TO_STRING);
        Component cameraNote = Component.translatable("options.dragonmounts.plus.camera.note");
        CAMERA_DISTANCE = new LazyFloatConfigOption("options.dragonmounts.plus.camera_distance", config.camera_distance, new LazyFloatConfigOption.Range(0.0F, 64.0F, 0.25F), cameraNote, X_2F_STRINGIFIER);
        CAMERA_OFFSET = new LazyFloatConfigOption("options.dragonmounts.plus.camera_offset", config.camera_offset, new LazyFloatConfigOption.Range(-16.0F, 16.0F, 0.25F), cameraNote, X_2F_STRINGIFIER);
        CONVERGE_PITCH = new LazyBooleanConfigOption("options.dragonmounts.plus.converge_pitch_angle", config.converge_pitch_angle, null, BOOLEAN_TO_STRING);
        CONVERGE_YAW = new LazyBooleanConfigOption("options.dragonmounts.plus.converge_yaw_angle", config.converge_yaw_angle, null, BOOLEAN_TO_STRING);
        HOVER_ANIMATION = new LazyBooleanConfigOption("options.dragonmounts.plus.hover_animation", config.hover_animation, null, BOOLEAN_TO_STRING);
        TOGGLE_DESCENDING = new LazyBooleanConfigOption("key.dragonmounts.plus.descend", config.toggle_descending, null, TOGGLE_STRINGIFIER);
        TOGGLE_BREATHING = new LazyBooleanConfigOption("key.dragonmounts.plus.breathe", config.toggle_breathing, null, TOGGLE_STRINGIFIER);
    }

    public DMConfigScreen(Screen lastScreen) {
        super(lastScreen, Minecraft.getInstance().options, Component.translatable("options.dragonmounts.plus.config"));
    }

    @Override
    protected void addOptions() {
        assert this.list != null;
        this.list.addBig(CAMERA_DISTANCE.makeInstance());
        this.list.addBig(CAMERA_OFFSET.makeInstance());
        this.list.addSmall(
                DEBUG.makeInstance(),
                TOGGLE_DESCENDING.makeInstance(),
                CONVERGE_PITCH.makeInstance(),
                TOGGLE_BREATHING.makeInstance(),
                CONVERGE_YAW.makeInstance(),
                HOVER_ANIMATION.makeInstance()
        );
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.lastScreen);
    }

    @Override
    public void removed() {
        ClientConfig.INSTANCE.save();
    }

    @Override
    protected void setInitialFocus() {}
}
