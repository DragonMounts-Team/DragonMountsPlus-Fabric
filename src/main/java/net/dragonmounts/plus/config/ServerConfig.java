package net.dragonmounts.plus.config;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.dragonmounts.plus.common.DragonMountsShared;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;

import java.util.function.Predicate;

public class ServerConfig extends ConfigHolder {
    public static ArgumentBuilder<CommandSourceStack, ?> buildCommand(Predicate<CommandSourceStack> permission) {
        return Commands.literal("config").requires(permission).then(ServerConfig.INSTANCE.debug.buildCommand());
    }

    public static final ServerConfig INSTANCE = new ServerConfig(DragonMountsShared.NAMESPACE);

    protected ServerConfig(String identifier) {
        super(FabricLoader.getInstance().getConfigDir().resolve(identifier).resolve("server.dat"), false);
        this.load();
    }

    @Override
    protected void read(CompoundTag tag) {
        this.debug.read(tag);
    }

    @Override
    protected CompoundTag write(CompoundTag tag) {
        this.debug.save(tag);
        return tag;
    }

    public static void init() {}
}
