package net.dragonmounts.plus.common.capability;

import net.dragonmounts.plus.compat.registry.ArmorEffect;
import net.dragonmounts.plus.compat.registry.CooldownCategory;
import net.minecraft.nbt.CompoundTag;

public interface ArmorEffectManager {
    @SuppressWarnings("UnusedReturnValue")
    int addLevel(ArmorEffect effect, int delta);

    @SuppressWarnings("UnusedReturnValue")
    int setLevel(ArmorEffect effect, int level);

    int getLevel(ArmorEffect effect, boolean filtered);

    boolean isActive(ArmorEffect effect);

    void setCooldown(CooldownCategory category, int cooldown);

    int getCooldown(CooldownCategory category);

    boolean isAvailable(CooldownCategory category);

    void tick();

    CompoundTag saveNBT();

    void readNBT(CompoundTag tag);

    void sendInitPacket();

    interface Provider {
        default ArmorEffectManagerImpl dragonmounts$getManager() {
            throw new NullPointerException();
        }
    }
}
