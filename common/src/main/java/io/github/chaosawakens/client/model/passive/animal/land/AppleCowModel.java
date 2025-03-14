package io.github.chaosawakens.client.model.passive.animal.land;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.animation.baked.passive.animal.land.AppleCowAnimation;
import io.github.chaosawakens.client.model.base.WrappedAgeableHierarchalModel;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.AppleCow;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.Nullable;

public class AppleCowModel<AC extends AppleCow> extends WrappedAgeableHierarchalModel<AC> {
	public static final ModelLayerLocation BASE_LAYER = new ModelLayerLocation(CAConstants.prefix("apple_cow"), "main");
	private final ModelPart root;
	private final ModelPart innerRoot;
	private final ModelPart body;
	private final ModelPart leaf;
	private final ModelPart rotation;
	private final ModelPart head;
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;
	private final ModelPart leg4;

	public AppleCowModel(ModelPart root) {
		this.root = root;

		this.innerRoot = root.getChild("root");

		this.body = this.innerRoot.getChild("body");
		this.leaf = this.body.getChild("leaf");

		this.rotation = this.body.getChild("rotation");

		this.head = this.body.getChild("head");

		this.leg1 = this.innerRoot.getChild("leg1");
		this.leg2 = this.innerRoot.getChild("leg2");
		this.leg3 = this.innerRoot.getChild("leg3");
		this.leg4 = this.innerRoot.getChild("leg4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition rootPartDefinition = meshDefinition.getRoot();

		PartDefinition root = rootPartDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 2.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition sappling_r1 = body.addOrReplaceChild("sappling_r1", CubeListBuilder.create().texOffs(0, 51).mirror().addBox(-6.0F, -7.5F, -0.005F, 12.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -8.5F, -1.6825F, 0.0F, -0.7854F, 0.0F));
		PartDefinition sappling_r2 = body.addOrReplaceChild("sappling_r2", CubeListBuilder.create().texOffs(0, 51).addBox(-6.0F, -7.5F, -0.005F, 12.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.5F, -1.6825F, 0.0F, -2.3998F, 0.0F));
		PartDefinition sappling_r3 = body.addOrReplaceChild("sappling_r3", CubeListBuilder.create().texOffs(0, 42).addBox(-2.875F, -4.0F, 2.3075F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, -5.0F, 0.005F, 0.0F, -0.3054F, 0.0F));
		PartDefinition sappling_r4 = body.addOrReplaceChild("sappling_r4", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-4.75F, -4.0F, -0.005F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.9285F, -2.1875F, -2.8089F, -0.1373F, 0.2737F, -0.4727F));

		PartDefinition leaf = body.addOrReplaceChild("leaf", CubeListBuilder.create(), PartPose.offset(0.0F, 1.6558F, 7.9798F));

		PartDefinition leaf_r1 = leaf.addOrReplaceChild("leaf_r1", CubeListBuilder.create().texOffs(10, 42).addBox(-1.5F, 4.38F, -1.4375F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0816F, 4.4334F, -1.0908F, 0.0F, 0.0F));

		PartDefinition rotation = body.addOrReplaceChild("rotation", CubeListBuilder.create(), PartPose.offset(0.0F, 4.75F, -1.25F));

		PartDefinition rotation_r1 = rotation.addOrReplaceChild("rotation_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 8.0F, -3.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, -5.25F, 1.5708F, 0.0F, 0.0F));
		PartDefinition rotation_r2 = rotation.addOrReplaceChild("rotation_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -9.0F, -8.0F, 12.0F, 18.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -5.75F, 0.25F, 1.5708F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -10.0F));

		PartDefinition leaf_r2 = head.addOrReplaceChild("leaf_r2", CubeListBuilder.create().texOffs(10, 42).mirror().addBox(-4.75F, 0.6925F, -1.6875F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -5.005F, 1.375F, 0.2182F, -1.2654F, 0.0F));
		PartDefinition leaf_r3 = head.addOrReplaceChild("leaf_r3", CubeListBuilder.create().texOffs(10, 42).addBox(1.75F, 0.6925F, -1.6875F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -5.005F, 1.375F, 0.2182F, 1.2654F, 0.0F));

		PartDefinition leg1 = root.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(28, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 7.0F, 5.0F));
		PartDefinition leg2 = root.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(28, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 7.0F, 5.0F));
		PartDefinition leg3 = root.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(28, 28).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 7.0F, -8.0F));
		PartDefinition leg4 = root.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(28, 28).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 7.0F, -8.0F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(AC pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

		// Base
		animate(pEntity.idleAnimState, AppleCowAnimation.IDLE, pAgeInTicks);

		// Walk Cycle
		animateWalk(pEntity.isPanicking() ? AppleCowAnimation.PANIC_RUN : AppleCowAnimation.WALK, pLimbSwing, pLimbSwingAmount, pEntity.isBaby() && pEntity.isPanicking() ? 0.865F : 1.3545F, pEntity.isPanicking() ? 2.865F : 1.755F);

		// Misc
		if (pEntity.isSheared()) applyStatic(AppleCowAnimation.SHEARED);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public @Nullable ModelPart head() {
		return head;
	}
}