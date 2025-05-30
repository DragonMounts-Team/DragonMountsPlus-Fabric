package net.dragonmounts.plus.common.client.variant;

import net.dragonmounts.plus.common.client.DMParticleSprites;
import net.dragonmounts.plus.common.client.breath.BreathParticle;
import net.dragonmounts.plus.common.client.breath.BreathParticleFactory;
import net.dragonmounts.plus.common.client.model.dragon.DragonModel;
import net.dragonmounts.plus.common.client.renderer.dragon.DragonRenderState;
import net.dragonmounts.plus.common.entity.breath.BreathParticleOption;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import static net.dragonmounts.plus.common.util.RenderStateAccessor.ENTITY_TRANSLUCENT_EMISSIVE_DECAL;

public class DefaultAppearance extends VariantAppearance {
    public final ModelLayerLocation modelLocation;
    public final BreathParticleFactory factory;
    public final ResourceLocation breath;
    public final ResourceLocation body;
    public final RenderType bodyForShoulder;
    public final RenderType bodyForBlock;
    public final RenderType decal;
    public final RenderType glow;
    public final RenderType glowDecal;
    private DragonModel model;

    public DefaultAppearance(
            ModelLayerLocation modelLocation,
            BreathParticleFactory factory,
            ResourceLocation breath,
            ResourceLocation body,
            ResourceLocation glow
    ) {
        this.modelLocation = modelLocation;
        this.factory = factory;
        this.breath = breath;
        this.body = body;
        this.bodyForShoulder = RenderType.entityCutoutNoCull(body);
        this.bodyForBlock = RenderType.entityCutoutNoCullZOffset(body);
        this.decal = RenderType.entityDecal(body);
        this.glow = RenderType.entityTranslucentEmissive(glow);
        this.glowDecal = ENTITY_TRANSLUCENT_EMISSIVE_DECAL.apply(glow);
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
    public ResourceLocation getBody(DragonRenderState state) {
        return this.body;
    }

    @Override
    public RenderType getGlow(DragonRenderState state) {
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
    public RenderType getBodyForBlock() {
        return this.bodyForBlock;
    }

    @Override
    public RenderType getGlowForBlock() {
        return this.glow;
    }

    @Override
    public Particle createParticle(BreathParticleOption option, TextureAtlas atlas, ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ) {
        return this.factory.createParticle(option, atlas.getSprite(this.breath), level, x, y, z, motionX, motionY, motionZ);
    }

    public static class Builder {
        public final ModelLayerLocation model;
        public BreathParticleFactory factory = BreathParticle.FACTORY;
        public ResourceLocation breath = DMParticleSprites.FLAME_BREATH;

        public Builder(ModelLayerLocation model) {
            this.model = model;
        }

        public Builder withBreath(ResourceLocation breath) {
            this.breath = breath;
            return this;
        }

        public Builder withBreath(ResourceLocation breath, BreathParticleFactory factory) {
            this.breath = breath;
            this.factory = factory;
            return this;
        }

        public DefaultAppearance build(ResourceLocation folder) {
            String path = folder.getPath();
            return this.build(
                    folder.withPath(TEXTURES_ROOT + path + "/body.png"),
                    folder.withPath(TEXTURES_ROOT + path + "/glow.png")
            );
        }

        public DefaultAppearance build(ResourceLocation body, ResourceLocation glow) {
            return new DefaultAppearance(this.model, this.factory, this.breath, body, glow);
        }
    }
}
