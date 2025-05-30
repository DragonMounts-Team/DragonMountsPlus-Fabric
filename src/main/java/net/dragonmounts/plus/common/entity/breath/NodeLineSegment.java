package net.dragonmounts.plus.common.entity.breath;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dragonmounts.plus.common.util.Collision;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.LongFunction;

/**
 * Created by TGG on 31/07/2015.
 * NodeLineSegment is used to represent a spherical node which has moved from one [x,y,z] point to a second [x,y,z] point.
 * Each line segment has a start point and a finish point.  The node has a defined radius.
 * The line segment is then used to detect collisions with other objects
 * <p>
 * Optionally, the segment
 * can be provided with a collection of collisions as well (each collision corresponds to an AABB which is known to
 * overlap with a block or entity, as discovered while moving the node.  the facing shows which face of the node
 * collided with the object.)
 * Typical usage:
 * (1) create node segment with a start point, finish point, and node radius.  Optional collisions from entity moving.
 * (2) collisionCheckAABB(), collisionCheckAABBcorners(), addStochasticCloud() and/or addBlockCollisions() to
 * perform collision checks of the against the node against blocks or entities
 */
public class NodeLineSegment {
    public static final int CLOUD_POINTS = 10;
    public final Vec3 startPoint;
    public final Vec3 direction;
    public final Vec3 unit;
    public final AABB box;
    public final float squaredRadius;
    public final float totalDensity;
    public final double squaredLength;
    private final Collection<AABB> collisions;
    private final Vec3[] hitPoints;

    /**
     * Creates a cloud of points around the line segment, to simulate the movement of a sphere starting from the
     * beginning of the line segment and moving to the end.  Each point is mapped onto the world grid.
     * Uses stochastic simulation, each point is generated as
     * 1) a point [x1,y1,z1] is chosen along the line segment, evenly distributed according to the number of cloud points,
     * plus a small random jitter
     * 2) a random point [x2,y2,z2] is chosen within the sphere centred on [x1,y1,z1].  This is generated from spherical
     * coordinates radius, phi, theta, uniformly distributed.  This puts more points near the centre of the sphere
     * i.e. the density of points is highest in the centre which is roughly what we want.
     * Each call to addStochasticCloud adds a total of totalDensity to the world grid -
     * eg if totalDensity = 1.0, it adds 1.0 to a single location, or 0.2 to location 1 and 0.8 to location 2, etc
     * Then:
     * For each of the direct collisions for this node (overlaps between the node AABB and the world, as calculated
     * in the entity movement), increment the hit density of the corresponding blocks
     * (The collision may have been caused by an entity not the blocks, however if the block actually has nothing in
     * it then it won't be affected anyway.)
     *
     * @param hitDensity the density of points at each world grid location - is updated by the method
     */
    public NodeLineSegment(
            RandomSource random,
            Vec3 start,
            Vec3 end,
            float radius,
            float totalDensity,
            Long2ObjectMap<BreathAffectedBlock> hitDensity,
            Collection<Collision> collisions
    ) {
        this.startPoint = start;
        this.direction = end.subtract(start);
        this.squaredLength = this.direction.lengthSqr();
        this.unit = this.direction.scale(Math.sqrt(this.squaredLength));
        this.squaredRadius = radius * radius;
        this.totalDensity = totalDensity;


        int numberOfCloudPoints = CLOUD_POINTS;
        Vec3[] hitPoints = new Vec3[numberOfCloudPoints];
        final float DENSITY_PER_POINT = totalDensity / numberOfCloudPoints;
        final double SUBSEGMENT_WIDTH = 1.0 / (numberOfCloudPoints + 1);
        LongFunction<BreathAffectedBlock> fallback = ignored -> new BreathAffectedBlock();
        for (int i = 0; i < numberOfCloudPoints; ++i) {
            double linePos = i * SUBSEGMENT_WIDTH + random.nextDouble() * SUBSEGMENT_WIDTH;
            double x = Mth.lerp(linePos, start.x, end.x);
            double y = Mth.lerp(linePos, start.y, end.y);
            double z = Mth.lerp(linePos, start.z, end.z);
            int theta = random.nextInt(TABLE_POINTS);
            int phi = random.nextInt(TABLE_POINTS);
            double r = random.nextDouble() * radius;
            double hitX = r * cosTable[theta] * sinTable[phi] + x;
            double hitY = r * sinTable[theta] * sinTable[phi] + y;
            double hitZ = r * cosTable[phi] + z;
            hitPoints[i] = new Vec3(hitX, hitY, hitZ);
            hitDensity.computeIfAbsent(BlockPos.asLong(Mth.floor(hitX), Mth.floor(hitY), Mth.floor(hitZ)), fallback)
                    .addHitDensity(getIntersectedFace(x, y, z, hitX, hitY, hitZ), DENSITY_PER_POINT);
        }
        this.hitPoints = hitPoints;

        double minX = Math.min(start.x, end.x) - radius;
        double maxX = Math.max(start.x, end.x) + radius;
        double minY = Math.min(start.y, end.y) - radius;
        double maxY = Math.max(start.y, end.y) + radius;
        double minZ = Math.min(start.z, end.z) - radius;
        double maxZ = Math.max(start.z, end.z) + radius;
        AABB box = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
        ObjectArrayList<AABB> collided = new ObjectArrayList<>(collisions.size());
        final double CONTRACTION = 0.001;
        for (var collision : collisions) {
            var aabb = collision.box();
            box = box.minmax(aabb);
            if (aabb.maxX - aabb.minX > 2 * CONTRACTION && aabb.maxY - aabb.minY > 2 * CONTRACTION && aabb.maxZ - aabb.minZ > 2 * CONTRACTION) {
                var side = collision.side().getOpposite();
                for (BlockPos pos : BlockPos.betweenClosed(aabb.contract(CONTRACTION, CONTRACTION, CONTRACTION))) {
                    hitDensity.computeIfAbsent(pos.asLong(), fallback)
                            .addHitDensity(side, totalDensity);
                }
            }
        }
        this.collisions = collided;
        this.box = box;
    }

    /**
     * Models collision between the node and the given aabb (of an entity)
     * Three checks:
     * a) For each of the direct collisions for this node (overlaps between the node AABB and the world, as calculated
     * in the entity movement) - if any overlap occurs, apply the full density.  (Currently not used)
     * Otherwise:
     * b) stochastically, based on the area of effect on nearby objects, even if no direct AABB overlap.  see below.  This
     * is most useful when the node is small compared to the aabb.
     * and
     * c) check corner of the aabb to see if it lies inside the nodelinesegment.  This is most useful when the node is large
     * compared to the aabb.
     * <p>
     * stochastically check how much the line segment collides with the specified aabb
     * Uses stochastic simulation, each point is generated as
     * 1) a point [x1,y1,z1] is chosen along the line segment, evenly distributed according to the number of cloud points,
     * plus a small random jitter
     * 2) a random point [x2,y2,z2] is chosen within the sphere centred on [x1,y1,z1].  This is generated from spherical
     * coordinates radius, phi, theta, uniformly distributed.  This puts more points near the centre of the sphere
     * i.e. the density of points is highest in the centre which is roughly what we want.
     *
     * @param aabb the aabb to check against
     * @return a value from 0.0 (no collision) to totalDensity (total collision)
     */
    public float collisionCheckAABB(AABB aabb) {
        for (var collision : this.collisions) {
            if (collision.intersects(aabb)) return this.totalDensity;
        }
        return Math.max(
                checkAABBHits(aabb), // stochastic density
                checkAABBCorners(aabb) // corner density
        );
    }

    /**
     * Check all eight corners of the aabb to see how many lie within the nodelinesegment.
     * Most useful for when the aabb is smaller than the nodelinesegment
     *
     * @param aabb the aabb to check against
     * @return a value from 0.0 (no collision) to totalDensity (total collision)
     */
    public float checkAABBCorners(AABB aabb) {
        int cornersInside = 0;
        cornersInside += isPointWithinNodeLineSegment(aabb.minX, aabb.minY, aabb.minZ) ? 1 : 0;
        cornersInside += isPointWithinNodeLineSegment(aabb.minX, aabb.minY, aabb.maxZ) ? 1 : 0;
        cornersInside += isPointWithinNodeLineSegment(aabb.minX, aabb.maxY, aabb.minZ) ? 1 : 0;
        cornersInside += isPointWithinNodeLineSegment(aabb.minX, aabb.maxY, aabb.maxZ) ? 1 : 0;
        cornersInside += isPointWithinNodeLineSegment(aabb.maxX, aabb.minY, aabb.minZ) ? 1 : 0;
        cornersInside += isPointWithinNodeLineSegment(aabb.maxX, aabb.minY, aabb.maxZ) ? 1 : 0;
        cornersInside += isPointWithinNodeLineSegment(aabb.maxX, aabb.maxY, aabb.minZ) ? 1 : 0;
        cornersInside += isPointWithinNodeLineSegment(aabb.maxX, aabb.maxY, aabb.maxZ) ? 1 : 0;
        return cornersInside * this.totalDensity * 0.125F;
    }

    /**
     * check whether the given point lies within the nodeLineSegment (i.e. within the sphere around the start point,
     * within the sphere around the end point, or within the cylinder about the line connecting start and end)
     *
     * @param x [x,y,z] is the world coordinate to check
     * @return true if it lies inside, false otherwise
     */
    public boolean isPointWithinNodeLineSegment(double x, double y, double z) {
        // first, find the closest point on the line segment between start and finish.
        // This is given by the formula
        //  projection_of_u_on_v = v . ( u dot v) / length(v)^2
        // where u = vector from startpoint to test point, and v = vector from startpoint to endpoint

        Vec3 deltaAxis = this.direction;
        Vec3 deltaPointToCheck = new Vec3(x - startPoint.x, y - startPoint.y, z - startPoint.z);
        double dotProduct = deltaAxis.dot(deltaPointToCheck);
        Vec3 closestPoint;
        if (dotProduct <= 0) {
            closestPoint = Vec3.ZERO;
        } else if (dotProduct >= this.squaredLength) {
            closestPoint = deltaAxis;
        } else {
            closestPoint = deltaAxis.scale(
                    dotProduct / this.squaredLength // projectionFraction
            );
        }
        return closestPoint.distanceToSqr(deltaPointToCheck) <= this.squaredRadius;
    }

    /**
     * Choose a number of random points from the nodelinesegment and see how many of them lie within the given aabb.
     * Most useful for when the aabb is larger than the nodelinesegment.
     *
     * @param aabb the aabb to check against
     * @return a value from 0.0 (no collision) to totalDensity (total collision)
     */
    private float checkAABBHits(AABB aabb) {
        float density = 0.0F;
        final float DENSITY_PER_POINT = totalDensity / CLOUD_POINTS;
        for (var hit : this.hitPoints) {
            if (aabb.contains(hit)) {
                density += DENSITY_PER_POINT;
            }
        }
        return density;
    }

    /**
     * Given a ray which originated at xyzOrigin and terminated at xyzHit:
     * Find which face of the block at xyzHit was hit by the ray.
     *
     * @return the face which was hit.  If none (was inside block), returns null
     */
    @Nullable
    public static Direction getIntersectedFace(double xOrigin, double yOrigin, double zOrigin, double xHit, double yHit, double zHit) {
        return AABB.getDirection(
                Math.floor(xHit),
                Math.floor(yHit),
                Math.floor(zHit),
                Math.ceil(xHit),
                Math.ceil(yHit),
                Math.ceil(zHit),
                new Vec3(xOrigin, yOrigin, zOrigin),
                new double[]{1.0},
                null,
                xHit - xOrigin,
                yHit - yOrigin,
                zHit - zOrigin
        );
    }

    private static final int TABLE_POINTS = 256;
    private static final float[] sinTable;
    private static final float[] cosTable;

    static {
        float[] sin = new float[TABLE_POINTS];
        float[] cos = new float[TABLE_POINTS];
        for (int i = 0; i < TABLE_POINTS; ++i) {
            double angle = i * 2.0 * Math.PI / TABLE_POINTS;
            sin[i] = (float) Math.sin(angle);
            cos[i] = (float) Math.cos(angle);
        }
        sinTable = sin;
        cosTable = cos;
    }
}
