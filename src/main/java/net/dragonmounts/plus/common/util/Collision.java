package net.dragonmounts.plus.common.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public record Collision(Direction side, AABB box) {
    public static Collision down(AABB box, double delta) {
        return new Collision(Direction.DOWN, new AABB(box.minX, box.minY + delta, box.minZ, box.maxX, box.minY, box.maxZ));
    }

    public static Collision up(AABB box, double delta) {
        return new Collision(Direction.UP, new AABB(box.minX, box.maxY, box.minZ, box.maxX, box.maxY + delta, box.maxZ));
    }

    public static Collision north(AABB box, double delta) {
        return new Collision(Direction.NORTH, new AABB(box.minX, box.minY, box.minZ + delta, box.maxX, box.maxY, box.minZ));
    }

    public static Collision south(AABB box, double delta) {
        return new Collision(Direction.SOUTH, new AABB(box.minX, box.minY, box.maxZ, box.maxX, box.maxY, box.maxZ + delta));
    }

    public static Collision west(AABB box, double delta) {
        return new Collision(Direction.WEST, new AABB(box.minX + delta, box.minY, box.minZ, box.minX, box.maxY, box.maxZ));
    }

    public static Collision east(AABB box, double delta) {
        return new Collision(Direction.EAST, new AABB(box.maxX, box.minY, box.minZ, box.maxX + delta, box.maxY, box.maxZ));
    }
}
