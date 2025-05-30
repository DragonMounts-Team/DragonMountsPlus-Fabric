package net.dragonmounts.plus.common.structure;

import com.mojang.logging.LogUtils;
import net.dragonmounts.plus.common.init.DMStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.slf4j.Logger;

/// @see net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece
public class DragonNestPiece extends TemplateStructurePiece {
    private static StructurePlaceSettings makeSettings(StructureTemplateManager manager, CompoundTag tag, ResourceLocation structure) {
        return makeSettings(
                Mirror.valueOf(tag.getString("Mirror")),
                Rotation.valueOf(tag.getString("Rotation")),
                getPivot(manager.getOrCreate(structure).getSize())
        );
    }

    private static StructurePlaceSettings makeSettings(
            Mirror mirror,
            Rotation rotation,
            BlockPos pivot
    ) {
        return new StructurePlaceSettings()
                .setRotation(rotation)
                .setMirror(mirror)
                .setRotationPivot(pivot)
                .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK)
                .addProcessor(new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE))
                .addProcessor(new LavaSubmergedBlockProcessor());
    }

    @SuppressWarnings("deprecation")
    public static void encapsulate(BoundingBox target, BoundingBox other) {
        target.encapsulate(other);
    }

    public static void checkAndPlace(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (!level.getBlockState(pos).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
            level.setBlock(pos, state, 3);
        }
    }

    public static void placeCircle(WorldGenLevel level, BlockPos.MutableBlockPos center, double radius, BlockState state) {
        double dist = radius * (radius + 0.8);
        for (int offsetX = 0,
             x = center.getX(),
             y = center.getY(),
             z = center.getZ(),
             end = Mth.ceil(radius);
             offsetX < end;
             ++offsetX
        ) {
            for (int offsetZ = 0; offsetZ < end; ++offsetZ) {
                if (offsetX * offsetX + offsetZ * offsetZ > dist) continue;
                checkAndPlace(level, center.set(x + offsetX, y, z + offsetZ), state);
                checkAndPlace(level, center.setZ(z - offsetZ), state);
                checkAndPlace(level, center.set(x - offsetX, y, z + offsetZ), state);
                checkAndPlace(level, center.setZ(z - offsetZ), state);
            }
        }
    }

    public static Heightmap.Types getHeightMapType(NestPlacement placement) {
        return placement == NestPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;
    }

    public static BlockPos getPivot(Vec3i size) {
        return new BlockPos(size.getX() / 2, 0, size.getZ() / 2);
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    public final NestPlacement placement;
    public final BlockState island;

    public DragonNestPiece(
            StructureTemplateManager manager,
            BlockPos pos,
            NestConfig config,
            ResourceLocation template,
            Rotation rotation,
            Mirror mirror,
            BlockPos pivot
    ) {
        super(DMStructures.DRAGON_NEST_PIECE, 0, manager, template, template.toString(), makeSettings(mirror, rotation, pivot), pos);
        this.placement = config.placement();
        this.island = config.island().orElse(null);
    }

    public DragonNestPiece(StructureTemplateManager manager, CompoundTag tag) {
        super(DMStructures.DRAGON_NEST_PIECE, tag, manager, structure -> makeSettings(manager, tag, structure));
        this.placement = NestPlacement.CODEC.byName(tag.getString("Placement"));
        this.island = BlockState.CODEC.parse(NbtOps.INSTANCE, tag.get("Island")).resultOrPartial().orElse(null);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {}

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rotation", this.placeSettings.getRotation().name());
        tag.putString("Mirror", this.placeSettings.getMirror().name());
        tag.putString("Placement", this.placement.getSerializedName());
        if (this.island == null) return;
        BlockState.CODEC.encodeStart(NbtOps.INSTANCE, this.island)
                .resultOrPartial(LOGGER::error)
                .ifPresent(data -> tag.put("Island", data));
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager manager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunk, BlockPos pos) {
        var structure = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
        var center = structure.getCenter();
        if (box.isInside(center)) {
            encapsulate(box, structure);
            super.postProcess(level, manager, generator, random, box, chunk, pos);
            var island = this.island;
            if (island == null) return;
            int radius = Math.max(structure.getXSpan(), structure.getZSpan()) / 2 - 1,
                    centerX = center.getX(),
                    centerY = structure.minY(),
                    centerZ = center.getZ(),
                    shrink = 2;
            var place = new BlockPos.MutableBlockPos();
            do {
                placeCircle(level, place.set(centerX, --centerY, centerZ), radius, island);
            } while ((radius -= random.nextInt(++shrink) + 1) > 3);
        }
    }
}
