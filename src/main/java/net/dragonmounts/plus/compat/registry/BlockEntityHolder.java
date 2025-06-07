package net.dragonmounts.plus.compat.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

import static net.dragonmounts.plus.common.DragonMountsShared.makeKey;

public class BlockEntityHolder<T extends BlockEntity> extends ObjectHolder<BlockEntityType<T>, BlockEntityType<?>> {
    public static <T extends BlockEntity> BlockEntityHolder<T> registerBlockEntity(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, BlockHolder<?>... blocks) {
        return new BlockEntityHolder<>(makeKey(Registries.BLOCK_ENTITY_TYPE, name), factory, blocks);
    }

    public static Block[] unwrap(BlockHolder<?>... wrapped) {
        var blocks = new Block[wrapped.length];
        for (int i = 0; i < wrapped.length; ++i) {
            blocks[i] = wrapped[i].get();
        }
        return blocks;
    }

    public final Set<BlockHolder<?>> blocks;

    public BlockEntityHolder(ResourceKey<BlockEntityType<?>> key, FabricBlockEntityTypeBuilder.Factory<? extends T> factory, BlockHolder<?>... blocks) {
        super(BuiltInRegistries.BLOCK_ENTITY_TYPE, key, FabricBlockEntityTypeBuilder.<T>create(factory, unwrap(blocks)).build());
        this.blocks = Set.of(blocks);
    }
}
