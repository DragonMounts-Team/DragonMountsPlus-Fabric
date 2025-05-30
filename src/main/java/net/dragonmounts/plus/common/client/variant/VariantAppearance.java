package net.dragonmounts.plus.common.client.variant;

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

import static net.dragonmounts.plus.common.DragonMountsShared.makeId;

public abstract class VariantAppearance {
    public final static String TEXTURES_ROOT = "textures/entity/dragon/";
    public static final ModelLayerLocation MODEL = new ModelLayerLocation(makeId("dragon"), "normal");
    public final static ResourceLocation DEFAULT_CHEST = makeId(TEXTURES_ROOT + "chest.png");
    public final static ResourceLocation DEFAULT_SADDLE = makeId(TEXTURES_ROOT + "saddle.png");
    public final static ResourceLocation DEFAULT_DISSOLVE = makeId(TEXTURES_ROOT + "dissolve.png");

    public abstract void onReload(EntityModelSet models);

    public abstract DragonModel getModel();

    public abstract ResourceLocation getBody(DragonRenderState state);

    public abstract RenderType getGlow(DragonRenderState state);

    public abstract RenderType getDecal(DragonRenderState state);

    public abstract RenderType getGlowDecal(DragonRenderState state);

    public abstract RenderType getBodyForBlock();

    public abstract RenderType getGlowForBlock();

    public abstract Particle createParticle(
            BreathParticleOption option,
            TextureAtlas atlas,
            ClientLevel level,
            double x,
            double y,
            double z,
            double motionX,
            double motionY,
            double motionZ
    );

    public ResourceLocation getChest(DragonRenderState state) {
        return DEFAULT_CHEST;
    }

    public ResourceLocation getSaddle(DragonRenderState state) {
        return DEFAULT_SADDLE;
    }

    public RenderType getDissolve(DragonRenderState state) {
        return RenderType.dragonExplosionAlpha(DEFAULT_DISSOLVE);
    }
}
