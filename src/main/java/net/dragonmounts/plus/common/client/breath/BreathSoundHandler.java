package net.dragonmounts.plus.common.client.breath;

import net.minecraft.client.Minecraft;

public class BreathSoundHandler {
    private BreathSound playing;

    public void update() {
        var manager = Minecraft.getInstance().getSoundManager();
        var sound = this.playing;
        while (sound != null) {
            if (!sound.timeout && manager.isActive(sound)) break;
            this.playing = sound = sound.next;
            if (sound != null) {
                manager.play(sound);
            }
        }
    }

    public void play(BreathSound sound) {
        this.setTimeout();
        Minecraft.getInstance().getSoundManager().play(this.playing = sound);
    }

    public void setTimeout() {
        var playing = this.playing;
        if (playing != null) {
            playing.timeout = true;
        }
    }
}
