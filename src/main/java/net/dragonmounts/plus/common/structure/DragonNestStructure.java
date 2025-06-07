package net.dragonmounts.plus.common.structure;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.dragonmounts.plus.common.api.NoiseColumnExtension;
import net.dragonmounts.plus.common.init.DMStructures;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static net.dragonmounts.plus.common.structure.DragonNestPiece.getPivot;

/**
 * @see net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure
 */
public class DragonNestStructure extends Structure {
    private static final int MIN_Y_INDEX = 15;
    private final List<NestConfig> configs;
    public static final MapCodec<DragonNestStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            settingsCodec(instance),
            ExtraCodecs.nonEmptyList(NestConfig.CODEC.listOf())
                    .fieldOf("configs")
                    .forGetter(structure -> structure.configs)
    ).apply(instance, DragonNestStructure::new));

    public DragonNestStructure(Structure.StructureSettings settings, List<NestConfig> configs) {
        super(settings);
        this.configs = configs;
    }

    @Override
    public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        var random = context.random();
        var pos = context.chunkPos().getWorldPosition();
        var config = drawConfig(this.configs, random);
        var structure = Util.getRandom(config.templates(), random);
        var template = context.structureTemplateManager().getOrCreate(structure);
        var rotation = Util.getRandom(Rotation.values(), random);
        var mirror = random.nextBoolean() ? Mirror.NONE : Mirror.FRONT_BACK;
        var pivot = getPivot(template.getSize());
        var height = findSuitableY(
                context, config, template.getBoundingBox(pos, rotation, pivot, mirror), random
        );
        if (height.isEmpty()) return Optional.empty();
        var location = new BlockPos(pos.getX(), height.getAsInt(), pos.getZ());
        return Optional.of(new Structure.GenerationStub(location, builder -> builder.addPiece(new DragonNestPiece(
                context.structureTemplateManager(), location, structure, rotation, mirror, pivot
        ))));
    }

    public static int getGroundHeight(ChunkGenerator generator, LevelHeightAccessor level, BoundingBox box, Heightmap.Types type, RandomState random) {
        var center = box.getCenter();
        return generator.getBaseHeight(center.getX(), center.getZ(), type, level, random) - 1;
    }

    public static OptionalInt findSuitableY(
            Structure.GenerationContext context,
            NestConfig config,
            BoundingBox box,
            RandomSource random
    ) {
        var placement = config.placement();
        var generator = context.chunkGenerator();
        var state = context.randomState();
        var level = context.heightAccessor();
        int bottom = level.getMinY() + MIN_Y_INDEX;
        int height;
        switch (placement) {
            case IN_MOUNTAIN:
                height = getRandomWithinInterval(random, 70, getGroundHeight(generator, level, box, placement.type, state) - box.getYSpan());
                break;
            case UNDERGROUND:
                height = getRandomWithinInterval(random, bottom, getGroundHeight(generator, level, box, placement.type, state) - box.getYSpan());
                break;
            case PARTLY_BURIED:
                height = getGroundHeight(generator, level, box, placement.type, state) - Mth.randomBetweenInclusive(random, 2, box.getYSpan() / 2);
                break;
            case IN_NETHER:
                height = Mth.randomBetweenInclusive(random, 27, 127 - box.getYSpan());
                break;
            case IN_CLOUDS: {
                var maxY = level.getMaxY();
                return OptionalInt.of(Math.max(
                        getGroundHeight(generator, level, box, placement.type, state) + MIN_Y_INDEX,
                        Mth.randomBetweenInclusive(random, maxY - 96, maxY - 48)
                ));
            }
            case ON_LAND_SURFACE:
                height = getGroundHeight(generator, level, box, placement.type, state);
                if (height < bottom) return OptionalInt.empty();
                break;
            case FLUSH_WITH_SURFACE: {
                var center = box.getCenter();
                int centerX = center.getX(), centerZ = center.getZ();
                var candidates = new IntArrayList();
                var isSupport = placement.type.isOpaque();
                var major = generator.getBaseColumn(centerX, centerZ, level, state);
                var minors = new NoiseColumn[]{
                        generator.getBaseColumn(box.minX(), box.minZ(), level, state),
                        generator.getBaseColumn(box.maxX(), box.minZ(), level, state),
                        generator.getBaseColumn(box.minX(), box.maxZ(), level, state),
                        generator.getBaseColumn(box.maxX(), box.maxZ(), level, state)
                };
                height = NoiseColumnExtension.getMaxHeight(major);
                boolean wasEmpty = !isSupport.test(major.getBlock(height--));
                do {
                    var block = major.getBlock(height);
                    if (isSupport.test(block)) {
                        if (wasEmpty) {
                            int layers = 0;
                            for (var column : minors) {
                                if (isSupport.test(column.getBlock(height)) && ++layers == 3) {
                                    candidates.add(height);
                                    break;
                                }
                            }
                        }
                        wasEmpty = false;
                    } else {
                        wasEmpty = true;
                    }
                } while (--height > bottom);
                return candidates.isEmpty()
                        ? OptionalInt.of(bottom)
                        : OptionalInt.of(Math.max(Util.getRandom(candidates, random) + 1 - box.getYSpan(), bottom));
            }
            default:
                height = getGroundHeight(generator, level, box, placement.type, state);
        }
        var columns = new NoiseColumn[]{
                generator.getBaseColumn(box.minX(), box.minZ(), level, state),
                generator.getBaseColumn(box.maxX(), box.minZ(), level, state),
                generator.getBaseColumn(box.minX(), box.maxZ(), level, state),
                generator.getBaseColumn(box.maxX(), box.maxZ(), level, state)
        };
        var isSupport = placement.type.isOpaque();
        do {
            int layers = 0;
            for (var column : columns) {
                if (isSupport.test(column.getBlock(height)) && ++layers == 3) return OptionalInt.of(height);
            }
        } while (--height > bottom);
        return OptionalInt.of(bottom);
    }

    public static int getRandomWithinInterval(RandomSource random, int min, int max) {
        return min < max ? Mth.randomBetweenInclusive(random, min, max) : max;
    }

    public static NestConfig drawConfig(List<NestConfig> configs, RandomSource random) {
        if (configs.size() > 1) {
            float total = 0.0F;
            for (var candidate : configs) {
                total += candidate.weight();
            }
            float target = random.nextFloat() * total;
            for (var candidate : configs) {
                if ((target -= candidate.weight()) < 0.0F) return candidate;
            }
        }
        return configs.getFirst();
    }

    @Override
    public @NotNull StructureType<?> type() {
        return DMStructures.DRAGON_NEST;
    }
}
