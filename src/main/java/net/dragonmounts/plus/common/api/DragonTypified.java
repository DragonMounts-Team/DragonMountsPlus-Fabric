package net.dragonmounts.plus.common.api;

import net.dragonmounts.plus.compat.registry.DragonType;

public interface DragonTypified {
    DragonType getDragonType();

    interface Mutable extends DragonTypified {
        void setDragonType(DragonType type, boolean reset);
    }
}