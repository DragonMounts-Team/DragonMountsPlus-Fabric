package net.dragonmounts.plus.common.type;

import net.dragonmounts.plus.compat.registry.DragonType;
import net.dragonmounts.plus.compat.registry.DragonTypeBuilder;
import net.minecraft.resources.ResourceLocation;

public class EnchantedType extends DragonType {
    public EnchantedType(ResourceLocation identifier, DragonTypeBuilder builder) {
        super(identifier, builder);
    }
}
