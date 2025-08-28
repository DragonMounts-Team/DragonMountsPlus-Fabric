package net.dragonmounts.plus.common.client.variant;

import net.dragonmounts.plus.common.client.DMParticleSprites;
import net.dragonmounts.plus.common.client.breath.BreathParticleFactory;
import net.dragonmounts.plus.common.client.breath.impl.FlameBreathParticle;
import net.dragonmounts.plus.common.client.model.dragon.DragonModel;
import net.dragonmounts.plus.common.client.renderer.RenderStateAccessor;
import net.dragonmounts.plus.common.client.renderer.dragon.DragonRenderState;
import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class DefaultAppearance implements VariantAppearance {
    public final ModelLayerLocation modelLocation;
    public final BreathParticleFactory factory;
    public final ResourceLocation breath;
    public final ResourceLocation body;
    public final RenderType base;
    public final RenderType decal;
    public final RenderType glow;
    public final RenderType glowDecal;
    public final RenderType chest;
    public final RenderType saddle;
    private DragonModel model;

    public DefaultAppearance(
            ModelLayerLocation modelLocation,
            ResourceLocation body,
            ResourceLocation glow,
            ResourceLocation breath,
            BreathParticleFactory factory
    ) {
        this.modelLocation = modelLocation;
        this.factory = factory;
        this.breath = breath;
        this.body = body;
        this.base = RenderType.entityCutoutNoCull(body);
        this.decal = RenderStateAccessor.entityCutoutDecal(body, DEFAULT_DISSOLVE);
        this.glow = RenderType.entityTranslucentEmissive(glow);
        this.glowDecal = RenderStateAccessor.entityTranslucentEmissiveDecal(glow, DEFAULT_DISSOLVE);
        this.chest = RenderType.entityCutoutNoCull(DEFAULT_CHEST);
        this.saddle = RenderType.entityCutoutNoCull(DEFAULT_SADDLE);
    }

    @Override
    public void onReload(EntityModelSet models) {
        this.model = new DragonModel(models.bakeLayer(this.modelLocation));
    }

    @Override
    public DragonModel getModel() {
        return this.model;
    }

    @Override
    public ResourceLocation getBodyTexture(DragonRenderState state) {
        return this.body;
    }

    @Override
    public RenderType getBase(@Nullable DragonRenderState state) {
        return this.base;
    }

    @Override
    public RenderType getGlow(@Nullable DragonRenderState state) {
        return this.glow;
    }

    @Override
    public RenderType getDecal(DragonRenderState state) {
        return this.decal;
    }

    @Override
    public RenderType getGlowDecal(DragonRenderState state) {
        return this.glowDecal;
    }

    @Override
    public RenderType getChest(DragonRenderState state) {
        return this.chest;
    }

    @Override
    public RenderType getSaddle(DragonRenderState state) {
        return this.saddle;
    }

    @Override
    public Particle createBreathParticle(BreathParticleOption option, TextureAtlas atlas, ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ) {
        return this.factory.createParticle(option, atlas.getSprite(this.breath), level, x, y, z, motionX, motionY, motionZ);
    }

    public static class Builder {
        public final ModelLayerLocation model;
        public BreathParticleFactory factory = FlameBreathParticle.FACTORY;
        public ResourceLocation breath = DMParticleSprites.FLAME_BREATH;

        public Builder(ModelLayerLocation model) {
            this.model = model;
        }

        public Builder withBreath(ResourceLocation breath) {
            this.breath = breath;
            return this;
        }

        public Builder withBreath(ResourceLocation breath, BreathParticleFactory factory) {
            this.factory = factory;
            return this.withBreath(breath);
        }

        public DefaultAppearance build(ResourceLocation folder) {
            String path = folder.getPath();
            return this.build(
                    folder.withPath(TEXTURES_ROOT + path + "/body.png"),
                    folder.withPath(TEXTURES_ROOT + path + "/glow.png")
            );
        }

        public DefaultAppearance build(ResourceLocation body, ResourceLocation glow) {
            return new DefaultAppearance(this.model, body, glow, this.breath, this.factory);
        }
    }
}
