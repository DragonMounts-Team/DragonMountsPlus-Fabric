package net.dragonmounts.plus.config;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.dragonmounts.plus.config.network.S2CBooleanConfigPayload;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.Nullable;

public class BooleanEntry extends ConfigEntry<Boolean> {
    public final boolean fallback;
    protected boolean saved;
    protected boolean value;

    public BooleanEntry(ConfigHolder holder, String key, String name, String tooltip, boolean fallback) {
        super(holder, key, name, tooltip);
        this.value = this.saved = this.fallback = fallback;
    }

    public boolean get() {
        return this.value;
    }

    @Override
    public boolean modify(Boolean wrapped) {
        boolean value = wrapped; // unbox
        if (this.value == value) return false;
        this.value = value;
        return true;
    }

    @Override
    public boolean isChanged() {
        return this.get() != this.saved;
    }

    @Override
    public void setSaved() {
        this.saved = this.value;
    }

    @Override
    public boolean isDefault() {
        return this.get() == this.fallback;
    }

    @Override
    public Boolean parse(CommandContext<?> context, String name) {
        return BoolArgumentType.getBool(context, name);
    }

    @Override
    public ArgumentType<Boolean> getArgument() {
        return BoolArgumentType.bool();
    }

    @Override
    public String getAsString() {
        return Boolean.toString(this.get());
    }

    @Override
    public Tag dump() {
        return ByteTag.valueOf(this.get());
    }

    @Override
    public void load(@Nullable Tag data) {
        if (data instanceof NumericTag tag) {
            this.modify(tag.getAsByte() != 0);
        }
    }

    @Override
    public CustomPacketPayload wrap(int id) {
        return new S2CBooleanConfigPayload(id, this.get());
    }
}
