package io.github.chaosawakens.client.model.passive.animal.land;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.animation.baked.passive.animal.land.LettuceChickenAnimation;
import io.github.chaosawakens.client.model.base.WrappedAgeableHierarchalModel;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.LettuceChicken;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.Nullable;

public class LettuceChickenModel<LC extends LettuceChicken> extends WrappedAgeableHierarchalModel<LC> {
	public static final ModelLayerLocation BASE_LAYER = new ModelLayerLocation(CAConstants.prefix("lettuce_chicken"), "main");
	private final ModelPart root;
	private final ModelPart chickenRoot;
	private final ModelPart head;
	private final ModelPart rotation4;
	private final ModelPart rotation5;
	private final ModelPart chin;
	private final ModelPart bill;
	private final ModelPart bone;
	private final ModelPart body;
	private final ModelPart rotation;
	private final ModelPart rotation2;
	private final ModelPart rotation3;
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart right_wing;
	private final ModelPart left_wing;

	public LettuceChickenModel(ModelPart root) {
		this.root = root;

		this.chickenRoot = root.getChild("root");

		this.head = this.chickenRoot.getChild("head");

		this.rotation4 = this.head.getChild("rotation4");
		this.rotation5 = this.head.getChild("rotation5");

		this.chin = this.head.getChild("chin");
		this.bill = this.head.getChild("bill");

		this.bone = this.bill.getChild("bone");
		this.body = this.chickenRoot.getChild("body");

		this.rotation = this.body.getChild("rotation");
		this.rotation2 = this.body.getChild("rotation2");
		this.rotation3 = this.body.getChild("rotation3");

		this.right_leg = this.chickenRoot.getChild("right_leg");
		this.left_leg = this.chickenRoot.getChild("left_leg");

		this.right_wing = this.chickenRoot.getChild("right_wing");
		this.left_wing = this.chickenRoot.getChild("left_wing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition rootPartDefinition = meshDefinition.getRoot();

		PartDefinition root = rootPartDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(14, 14).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -4.0F));

		PartDefinition rotation4 = head.addOrReplaceChild("rotation4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -7.0F, 1.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -2.0F, -0.074F, 0.6864F, 0.5033F));
		PartDefinition rotation5 = head.addOrReplaceChild("rotation5", CubeListBuilder.create().texOffs(0, 24).addBox(-2.5F, -6.0F, 1.0F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -3.0F, -1.0F, -0.0665F, -0.533F, 0.5841F));

		PartDefinition chin = head.addOrReplaceChild("chin", CubeListBuilder.create().texOffs(24, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition bill = head.addOrReplaceChild("bill", CubeListBuilder.create().texOffs(23, 23).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone = bill.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 14).addBox(-6.8F, -13.0F, 1.5F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.7F, -7.0F, -3.0F, 0.0F, 0.0F, -1.8326F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition rotation = body.addOrReplaceChild("rotation", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
		PartDefinition rotation2 = body.addOrReplaceChild("rotation2", CubeListBuilder.create().texOffs(18, 0).addBox(-2.5F, -6.5F, 1.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.1309F, -0.6109F, 0.0F));
		PartDefinition rotation3 = body.addOrReplaceChild("rotation3", CubeListBuilder.create().texOffs(18, 0).addBox(-2.5F, -7.2F, 1.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, -0.1447F, 0.6855F, -0.2262F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(11, 23).addBox(-5.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -5.0F, 1.0F));
		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(11, 23).addBox(1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -5.0F, 1.0F));

		PartDefinition right_wing = root.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -11.0F, 0.0F));
		PartDefinition left_wing = root.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -11.0F, 0.0F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(LC pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

		// Base
		animate(pEntity.idleAnimState, LettuceChickenAnimation.IDLE, pAgeInTicks);

		// Misc
		animate(pEntity.perchAnimState, LettuceChickenAnimation.PERCH, pAgeInTicks);
		animate(pEntity.flapAnimState, LettuceChickenAnimation.FLAP, pAgeInTicks);

		// Walk Cycle
		if (!pEntity.isPerching()) {
			if (!pEntity.onGround() || pEntity.isFallFlying()) animateWalk(LettuceChickenAnimation.FLAP, pLimbSwing, pLimbSwingAmount, 1.0F, 1.45F);
			else animateWalk(pEntity.isPanicking() ? LettuceChickenAnimation.RUN : LettuceChickenAnimation.WALK, pLimbSwing, pLimbSwingAmount, 1.0F, 1.45F);
		}
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