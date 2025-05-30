package net.dragonmounts.plus.common.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public interface DMEntityTags {
    TagKey<EntityType<?>> DRAGONS = TagKey.create(Registries.ENTITY_TYPE, makeId("dragons"));
}
