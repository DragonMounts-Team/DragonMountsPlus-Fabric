package net.dragonmounts.plus.common.init;

import net.minecraft.world.entity.schedule.Activity;

import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerActivity;

public class DMActivities {
    public static final Activity CONTROLLED = registerActivity("controlled");
    public static final Activity SITTING = registerActivity("sitting");

    public static void init() {}
}
