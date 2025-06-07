package net.dragonmounts.plus.common.structure;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;

public enum NestPlacement implements StringRepresentable {
    ON_LAND_SURFACE,
    FLUSH_WITH_SURFACE(Heightmap.Types.MOTION_BLOCKING),
    PARTLY_BURIED,
    ON_OCEAN_FLOOR(Heightmap.Types.OCEAN_FLOOR_WG),
    IN_MOUNTAIN,
    UNDERGROUND,
    IN_CLOUDS,
    IN_NETHER;
    public static final @SuppressWarnings("deprecation") EnumCodec<NestPlacement> CODEC = StringRepresentable.fromEnum(NestPlacement::values);

    public final String name;
    public final Heightmap.Types type;

    NestPlacement(Heightmap.Types type) {
        this.name = this.name().toLowerCase();
        this.type = type;
    }

    NestPlacement() {
        this(Heightmap.Types.WORLD_SURFACE_WG);
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
