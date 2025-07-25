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

public class BooleanEntry extends ConfigEntry implements ConfigValue<Boolean> {
    public final boolean fallback;
    protected boolean saved;
    protected boolean value;
    protected boolean effective;

    public BooleanEntry(String key, String name, String tooltip, boolean fallback) {
        super(key, name, tooltip);
        this.set(this.saved = this.fallback = fallback);
    }

    public boolean get() {
        return this.effective;
    }

    @Override
    public void override(Boolean value) {
        this.effective = value;
    }

    @Override
    public boolean set(Boolean wrapped) {
        boolean value = wrapped; // unbox
        this.effective = value;
        if (this.value == value) return false;
        this.value = value;
        return true;
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
    public Boolean load(@Nullable Tag data) {
        return data instanceof NumericTag ? ((NumericTag) data).getAsByte() != 0 : this.fallback;
    }

    @Override
    public boolean isChanged() {
        return this.value != this.saved;
    }

    @Override
    public boolean isDefault() {
        return this.value == this.fallback;
    }

    @Override
    public void reset() {
        this.set(this.fallback);
    }

    @Override
    public void revert() {
        this.set(this.saved);
    }

    @Override
    public void setSaved() {
        this.saved = this.value;
    }

    @Override
    public CustomPacketPayload wrap(int id) {
        return new S2CBooleanConfigPayload(id, this.get());
    }

    @Override
    public ArgumentType<Boolean> getArgument() {
        return BoolArgumentType.bool();
    }

    @Override
    public Boolean parse(CommandContext<?> context, String name) {
        return BoolArgumentType.getBool(context, name);
    }

    @Override
    public ConfigEntry getEntry() {
        return this;
    }
}
