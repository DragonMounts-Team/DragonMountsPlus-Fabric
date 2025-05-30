package net.dragonmounts.plus.common.structure;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum NestPlacement implements StringRepresentable {
    ON_LAND_SURFACE("on_land_surface"),
    PARTLY_BURIED("partly_buried"),
    ON_OCEAN_FLOOR("on_ocean_floor"),
    IN_MOUNTAIN("in_mountain"),
    UNDERGROUND("underground"),
    IN_CLOUDS("in_clouds"),
    IN_NETHER("in_nether");
    public static final @SuppressWarnings("deprecation") EnumCodec<NestPlacement> CODEC = StringRepresentable.fromEnum(NestPlacement::values);

    public final String name;

    NestPlacement(final String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
