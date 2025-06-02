package net.dragonmounts.plus.compat.registry;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import static net.dragonmounts.plus.common.DragonMountsShared.COOLDOWN_CATEGORY;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.makeSimpleRegistry;

public class CooldownCategory {
    public static final MappedRegistry<CooldownCategory> REGISTRY = makeSimpleRegistry(COOLDOWN_CATEGORY);

    public final ResourceLocation identifier;
    private final int id;

    public CooldownCategory(ResourceLocation identifier) {
        this.id = REGISTRY.getId(Registry.register(REGISTRY, this.identifier = identifier, this));
    }

    public final int getId() {
        return this.id;
    }
}
