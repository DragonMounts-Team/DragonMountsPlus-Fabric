package net.dragonmounts.plus.common.entity.ai.navigation;

import net.dragonmounts.plus.common.entity.dragon.ServerDragonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class DragonPathNavigation extends PathNavigation {
    public final ServerDragonEntity dragon;

    public DragonPathNavigation(ServerDragonEntity dragon, Level level) {
        super(dragon, level);
        this.dragon = dragon;
    }

    @Override
    protected PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new DragonNodeEvaluator();
        return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected boolean canMoveDirectly(Vec3 posVec31, Vec3 posVec32) {
        return this.dragon.isFlying() && isClearForMovementBetween(this.dragon, posVec31, posVec32, true);
    }

    @Override
    protected boolean canUpdatePath() {
        return this.canFloat() && this.mob.isInLiquid() || !this.mob.isPassenger();
    }

    @Override
    protected Vec3 getTempMobPos() {
        return this.mob.position();
    }

    @Nullable
    @Override
    public Path createPath(Entity entity, int accuracy) {
        return this.createPath(entity.blockPosition(), accuracy);
    }

    @Override
    public void tick() {
        this.tick++;
        if (this.hasDelayedRecomputation) {
            this.recomputePath();
        }
        if (!this.isDone()) {
            if (this.canUpdatePath()) {
                this.followThePath();
            } else if (this.path != null && !this.path.isDone()) {
                Vec3 vec3 = this.path.getNextEntityPos(this.mob);
                if (this.mob.getBlockX() == Mth.floor(vec3.x) && this.mob.getBlockY() == Mth.floor(vec3.y) && this.mob.getBlockZ() == Mth.floor(vec3.z)) {
                    this.path.advance();
                }
            }
            DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
            if (!this.isDone()) {
                Vec3 vec3 = this.path.getNextEntityPos(this.mob);
                this.mob.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, this.speedModifier);
            }
        }
    }

    @Override
    public boolean isStableDestination(BlockPos pos) {
        return this.level.getBlockState(pos).entityCanStandOn(this.level, pos, this.mob);
    }
}
