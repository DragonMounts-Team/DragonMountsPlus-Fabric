package net.dragonmounts.plus.common.init;


import net.minecraft.sounds.SoundEvent;

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;
import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerSound;

public class DMSounds {
    public static final SoundEvent DRAGON_AMBIENT = create("entity.dragon.ambient");
    public static final SoundEvent DRAGON_AMBIENT_WATER = create("entity.dragon.ambient.water");
    public static final SoundEvent DRAGON_CHEST = create("entity.dragon.chest");
    public static final SoundEvent DRAGON_DEATH = create("entity.dragon.death");
    public static final SoundEvent DRAGON_DEATH_ZOMBIE = create("entity.dragon.death.zombie");
    public static final SoundEvent DRAGON_ROAR = create("entity.dragon.roar");
    public static final SoundEvent DRAGON_ROAR_HATCHLING = create("entity.dragon.roar.hatchling");
    public static final SoundEvent DRAGON_ROAR_WATER = create("entity.dragon.roar.water");
    public static final SoundEvent DRAGON_PURR = create("entity.dragon.purr");
    public static final SoundEvent DRAGON_PURR_HATCHLING = create("entity.dragon.purr.hatchling");
    public static final SoundEvent DRAGON_PURR_NETHER = create("entity.dragon.purr.nether");
    public static final SoundEvent DRAGON_PURR_NETHER_HATCHLING = create("entity.dragon.purr.nether.hatchling");
    public static final SoundEvent DRAGON_PURR_SKELETON = create("entity.dragon.purr.skeleton");
    public static final SoundEvent DRAGON_PURR_SKELETON_HATCHLING = create("entity.dragon.purr.skeleton.hatchling");
    public static final SoundEvent DRAGON_PURR_ZOMBIE = create("entity.dragon.purr.zombie");
    public static final SoundEvent DRAGON_SNEEZE = create("entity.dragon.sneeze");
    public static final SoundEvent DRAGON_STEP = create("entity.dragon.step");
    public static final SoundEvent DRAGON_BREATH_START_ADULT = create("entity.dragon.breath_start.adult");
    public static final SoundEvent DRAGON_BREATH_START_JUVENILE = create("entity.dragon.breath_start.juvenile");
    public static final SoundEvent DRAGON_BREATH_START_HATCHLING = create("entity.dragon.breath_start.hatchling");
    public static final SoundEvent DRAGON_BREATH_START_ICE = create("entity.dragon.breath_start.ice");
    public static final SoundEvent DRAGON_BREATH_START_AIRFLOW = create("entity.dragon.breath_start.airflow");
    public static final SoundEvent DRAGON_BREATH_START_WATER = create("entity.dragon.breath_start.water");
    public static final SoundEvent DRAGON_BREATH_LOOP_ADULT = create("entity.dragon.breath_loop.adult");
    public static final SoundEvent DRAGON_BREATH_LOOP_JUVENILE = create("entity.dragon.breath_loop.juvenile");
    public static final SoundEvent DRAGON_BREATH_LOOP_HATCHLING = create("entity.dragon.breath_loop.hatchling");
    public static final SoundEvent DRAGON_BREATH_LOOP_ICE = create("entity.dragon.breath_loop.ice");
    public static final SoundEvent DRAGON_BREATH_LOOP_AIRFLOW = create("entity.dragon.breath_loop.airflow");
    public static final SoundEvent DRAGON_BREATH_LOOP_WATER = create("entity.dragon.breath_loop.water");
    public static final SoundEvent DRAGON_BREATH_STOP_ADULT = create("entity.dragon.breath_stop.adult");
    public static final SoundEvent DRAGON_BREATH_STOP_JUVENILE = create("entity.dragon.breath_stop.juvenile");
    public static final SoundEvent DRAGON_BREATH_STOP_HATCHLING = create("entity.dragon.breath_stop.hatchling");
    public static final SoundEvent DRAGON_BREATH_STOP_ICE = create("entity.dragon.breath_stop.ice");
    public static final SoundEvent DRAGON_BREATH_STOP_AIRFLOW = create("entity.dragon.breath_stop.airflow");
    public static final SoundEvent DRAGON_BREATH_STOP_WATER = create("entity.dragon.breath_stop.water");
    public static final SoundEvent DRAGON_EGG_CRACK = create("entity.dragon_egg.crack");
    public static final SoundEvent DRAGON_EGG_SHATTER = create("entity.dragon_egg.shatter");
    public static final SoundEvent WHISTLE_BLOW_SHORT = create("item.whistle.blow.short");
    public static final SoundEvent WHISTLE_BLOW_LONG = create("item.whistle.blow.long");
    public static final SoundEvent VARIATION_ORB_ACTIVATE = create("item.variation_orb.activate");

    static SoundEvent create(final String name) {
        var identifier = makeId(name);
        return registerSound(identifier, SoundEvent.createVariableRangeEvent(identifier));
    }

    public static void init() {}
}