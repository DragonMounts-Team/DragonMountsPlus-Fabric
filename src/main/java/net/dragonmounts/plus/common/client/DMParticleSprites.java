package net.dragonmounts.plus.common.client;

import net.minecraft.resources.ResourceLocation;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public interface DMParticleSprites {
    ResourceLocation FLAME_BREATH = makeId("breath_fire");
    ResourceLocation AIRFLOW_BREATH = makeId("breath_air");
    ResourceLocation DARK_BREATH = makeId("breath_dark");
    ResourceLocation ENDER_BREATH = makeId("breath_acid");
    ResourceLocation WATER_BREATH = makeId("breath_hydro");
    ResourceLocation ICE_BREATH = makeId("breath_ice");
    ResourceLocation NETHER_BREATH = makeId("breath_nether");
    ResourceLocation POISON_BREATH = makeId("breath_poison");
    ResourceLocation WITHER_BREATH = makeId("breath_wither");
}
