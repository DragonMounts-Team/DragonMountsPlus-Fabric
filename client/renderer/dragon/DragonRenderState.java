package net.dragonmounts.plus.common.client.renderer.dragon;

import net.dragonmounts.plus.common.client.model.dragon.LegPart;
import net.dragonmounts.plus.common.client.renderer.block.DragonHeadRenderState;
import net.dragonmounts.plus.common.client.variant.VariantAppearance;
import net.dragonmounts.plus.common.util.ArrayUtil;
import net.dragonmounts.plus.common.util.Segment;
import net.dragonmounts.plus.compat.registry.DragonVariant;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import static net.dragonmounts.plus.common.entity.dragon.DragonModelContracts.*;

public class DragonRenderState extends LivingEntityRenderState implements DragonHeadRenderState {
    public DragonVariant variant;
    public ItemStack armor = ItemStack.EMPTY;
    public @Nullable Vec3 crystal;
    public boolean renderCrystalBeams;
    public boolean isSaddled;
    public boolean hasChest;
    public int hurtTime;
    public float pitch;
    public float offsetY;
    public int maxDeathTime;
    //--------head--------
    public Segment head;
    public float jawRotX;
    //--------neck--------
    public final Segment[] neckSegments = ArrayUtil.fillArray(new Segment[NECK_SEGMENTS], Segment::new);
    //--------wing--------
    public final Vector3f wingRot = new Vector3f();
    public final Vector3f armRot = new Vector3f();
    public final float[] fingerRotY = new float[WING_FINGERS];
    //--------legs--------
    public final LegPart.Pose leftFrontLeg = new LegPart.Pose();
    public final LegPart.Pose rightFrontLeg = new LegPart.Pose();
    public final LegPart.Pose leftHindLeg = new LegPart.Pose();
    public final LegPart.Pose rightHindLeg = new LegPart.Pose();
    //--------tail--------
    public final Segment[] tailSegments = ArrayUtil.fillArray(new Segment[TAIL_SEGMENTS], Segment::new);

    @Override
    public @Nullable VariantAppearance dragonmounts$plus$getAppearance() {
        return this.variant.appearance;
    }
}
