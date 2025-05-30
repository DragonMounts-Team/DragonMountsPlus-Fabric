package net.dragonmounts.plus.common.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public interface DMStructureTags {
    TagKey<Structure> DRAGON_NESTS = TagKey.create(Registries.STRUCTURE, makeId("dragon_nests"));
}
