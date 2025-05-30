package net.dragonmounts.plus.common.init;

import net.dragonmounts.plus.common.component.DragonFood;
import net.dragonmounts.plus.common.entity.ai.sensing.DragonTargetSensor;
import net.dragonmounts.plus.common.entity.ai.sensing.OwnerSensor;
import net.dragonmounts.plus.common.entity.ai.sensing.QuickTemptingSensor;
import net.minecraft.world.entity.ai.sensing.SensorType;

import static net.dragonmounts.plus.compat.registry.RegistryHandler.registerSensor;

public class DMSensors {
    public static final SensorType<DragonTargetSensor> DRAGON_TARGETS =
            registerSensor("dragon_targets", DragonTargetSensor::new);

    public static final SensorType<QuickTemptingSensor> DRAGON_TEMPTATIONS =
            registerSensor("dragon_temptations", () -> new QuickTemptingSensor(DragonFood::isDragonFood));

    public static final SensorType<OwnerSensor> OWNER =
            registerSensor("owner", OwnerSensor::new);

    public static void init() {}
}
