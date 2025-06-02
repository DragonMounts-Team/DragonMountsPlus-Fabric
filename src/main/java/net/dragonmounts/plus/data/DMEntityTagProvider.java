package net.dragonmounts.plus.data;

import net.dragonmounts.plus.common.init.DMEntities;
import net.dragonmounts.plus.common.tag.DMEntityTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class DMEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public DMEntityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.getOrCreateTagBuilder(DMEntityTags.DRAGONS)
                .add(DMEntities.TAMEABLE_DRAGON.key)
                .add(DMEntities.HATCHABLE_DRAGON_EGG.key);
        this.getOrCreateTagBuilder(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
                .addTag(DMEntityTags.DRAGONS);
        this.getOrCreateTagBuilder(EntityTypeTags.FALL_DAMAGE_IMMUNE)
                .addTag(DMEntityTags.DRAGONS);
    }
}
