package io.github.chaosawakens.client.model.neutral;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.animation.baked.neutral.rubber_ducky.RubberDuckyAnimation;
import io.github.chaosawakens.client.animation.baked.neutral.rubber_ducky.RubberDuckyMiscAnimation;
import io.github.chaosawakens.client.animation.baked.neutral.rubber_ducky.RubberDuckyTransformedAnimation;
import io.github.chaosawakens.client.animation.baked.neutral.rubber_ducky.RubberDuckyTransformedWaterAnimation;
import io.github.chaosawakens.client.model.base.WrappedHierarchicalModel;
import io.github.chaosawakens.common.entity.prototype.neutral.RubberDucky;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RubberDuckyModel<RD extends RubberDucky> extends WrappedHierarchicalModel<RD> {
    public static final ModelLayerLocation BASE_LAYER = new ModelLayerLocation(CAConstants.prefix("rubber_ducky"), "main");
    private final ModelPart Root;
    private final ModelPart Rubber_ducky;
    private final ModelPart Rubber_ducky_up;
    private final ModelPart Left_tendril_eye;
    private final ModelPart Right_tendril_eye;
    private final ModelPart tail;
    private final ModelPart attack_tendril;
    private final ModelPart Left_tendril;
    private final ModelPart Left_tendril2;
    private final ModelPart Right_tendril2;
    private final ModelPart Right_tendril;

    public RubberDuckyModel(ModelPart root) {
        this.Root = root.getChild("Root");

        this.Rubber_ducky = this.Root.getChild("Rubber_ducky");
        this.Rubber_ducky_up = this.Rubber_ducky.getChild("Rubber_ducky_up");

        this.Left_tendril_eye = this.Rubber_ducky_up.getChild("Left_tendril_eye");
        this.Right_tendril_eye = this.Rubber_ducky_up.getChild("Right_tendril_eye");

        this.tail = this.Rubber_ducky_up.getChild("tail");

        this.attack_tendril = this.Rubber_ducky_up.getChild("attack_tendril");

        this.Left_tendril = this.Rubber_ducky.getChild("Left_tendril");
        this.Left_tendril2 = this.Rubber_ducky.getChild("Left_tendril2");

        this.Right_tendril2 = this.Rubber_ducky.getChild("Right_tendril2");
        this.Right_tendril = this.Rubber_ducky.getChild("Right_tendril");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition rootPartDefinition = meshDefinition.getRoot();

        PartDefinition Root = rootPartDefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Rubber_ducky = Root.addOrReplaceChild("Rubber_ducky", CubeListBuilder.create(), PartPose.offset(0.0F, -2.4375F, 0.1563F));
        PartDefinition Rubber_ducky_up = Rubber_ducky.addOrReplaceChild("Rubber_ducky_up", CubeListBuilder.create().texOffs(86, 0).addBox(-3.5F, -2.5F, -2.5F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(86, 12).addBox(-3.0F, -5.5F, -5.4375F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(25, 75).addBox(-2.0F, -3.5F, -7.4375F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(25, 80).addBox(-1.5F, -2.5F, 3.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.0625F, -0.6563F));

        PartDefinition Left_tendril_eye = Rubber_ducky_up.addOrReplaceChild("Left_tendril_eye", CubeListBuilder.create(), PartPose.offset(1.875F, -3.0625F, -4.375F));

        PartDefinition cube_r1 = Left_tendril_eye.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(61, 0).addBox(-0.05F, -12.5F, -6.0F, 0.05F, 17.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition Right_tendril_eye = Rubber_ducky_up.addOrReplaceChild("Right_tendril_eye", CubeListBuilder.create(), PartPose.offset(-2.875F, -3.0625F, -4.375F));

        PartDefinition cube_r2 = Right_tendril_eye.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 75).addBox(-0.05F, -12.5F, -6.0F, 0.05F, 17.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -3.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition tail = Rubber_ducky_up.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 42).addBox(-0.05F, -4.5F, -3.5F, 0.05F, 13.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.875F, 3.5F));

        PartDefinition attack_tendril = Rubber_ducky_up.addOrReplaceChild("attack_tendril", CubeListBuilder.create().texOffs(0, 0).addBox(-0.05F, -5.5F, -25.0F, 0.05F, 11.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -0.875F, -4.0F));

        PartDefinition Left_tendril = Rubber_ducky.addOrReplaceChild("Left_tendril", CubeListBuilder.create(), PartPose.offset(3.3125F, -1.3125F, -1.9688F));

        PartDefinition cube_r3 = Left_tendril.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(39, 42).addBox(-0.05F, -8.5F, -6.0F, 0.05F, 17.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.625F, 7.375F, -2.6875F, 0.0F, 2.0944F, 0.0F));

        PartDefinition Left_tendril2 = Rubber_ducky.addOrReplaceChild("Left_tendril2", CubeListBuilder.create(), PartPose.offset(3.3125F, -1.3125F, 0.7813F));

        PartDefinition cube_r4 = Left_tendril2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 60).addBox(-0.05F, -8.5F, -6.0F, 0.05F, 17.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.625F, 7.375F, 2.5625F, 0.0F, 1.0472F, 0.0F));

        PartDefinition Right_tendril2 = Rubber_ducky.addOrReplaceChild("Right_tendril2", CubeListBuilder.create(), PartPose.offset(-3.3125F, -1.3125F, 0.7813F));

        PartDefinition cube_r5 = Right_tendril2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(39, 72).addBox(-0.05F, -8.5F, -6.0F, 0.05F, 17.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.625F, 7.375F, 2.5625F, 0.0F, -1.0472F, 0.0F));

        PartDefinition Right_tendril = Rubber_ducky.addOrReplaceChild("Right_tendril", CubeListBuilder.create(), PartPose.offset(-3.3125F, -1.3125F, -1.9688F));

        PartDefinition cube_r6 = Right_tendril.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(64, 30).addBox(-0.05F, -8.5F, -6.0F, 0.05F, 17.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.625F, 7.375F, -2.6875F, 0.0F, -2.0944F, 0.0F));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }

    @Override
    public void setupAnim(RD pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

        // Base
        animate(pEntity.idleAnimState, RubberDuckyAnimation.SHAKE_IDLE, pAgeInTicks);
        animate(pEntity.waterIdleAnimState, RubberDuckyAnimation.WATER_IDLE, pAgeInTicks);
        animate(pEntity.bounceAnimState, RubberDuckyAnimation.BOUNCE, pAgeInTicks);

        animate(pEntity.hideTendrilAnimState, RubberDuckyMiscAnimation.HIDE_ATTACK, pAgeInTicks);

        // Transformation
        animate(pEntity.transformAnimState, RubberDuckyAnimation.TRANSFORM, pAgeInTicks);

        // Transformed
        animate(pEntity.transformedIdleAnimState, RubberDuckyTransformedAnimation.TRANSFORMED_IDLE, (long) ((1 / Math.min(pLimbSwing * 50.0F, 1.0F)) * pEntity.transformedIdleAnimState.getAccumulatedTime()) * (pEntity.transformedIdleAnimState.isStarted() ? 1 : 0), 1.0F, pAgeInTicks, 1.0F);
        animate(pEntity.transformedWaterIdleAnimState, RubberDuckyTransformedWaterAnimation.TRANSFORMED_WATER_IDLE, (long) ((1 / Math.min(pLimbSwing * 50.0F, 1.0F)) * pEntity.transformedWaterIdleAnimState.getAccumulatedTime()) * (pEntity.transformedWaterIdleAnimState.isStarted() ? 1 : 0), 1.0F, pAgeInTicks, 1.0F);

        // Attack
        animate(pEntity.tendrilStabAttackAnimState, RubberDuckyTransformedAnimation.STAB_ATTACK, pAgeInTicks);

        // Walk Cycle
        if (!pEntity.isDeadOrDying()) {
            if (!pEntity.isInWater()) {
                if (pEntity.getStateId() == RubberDucky.TRANSFORMED_STATE_ID) animateWalk(RubberDuckyTransformedAnimation.TRANSFORMED_WALK, pLimbSwing, pLimbSwingAmount, 2.0F, 1.0F);
            } else if (pEntity.getStateId() == RubberDucky.TRANSFORMED_STATE_ID) {
                animateWalk(RubberDuckyTransformedWaterAnimation.TRANSFORMED_SWIM, pLimbSwing, pLimbSwingAmount, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public @Nullable ModelPart head() {
        return Rubber_ducky_up;
    }

    @Override
    public @NotNull ModelPart root() {
        return Root;
    }
}