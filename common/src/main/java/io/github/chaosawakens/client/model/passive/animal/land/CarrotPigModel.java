package io.github.chaosawakens.client.model.passive.animal.land;// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.animation.baked.passive.animal.land.CarrotPigAnimation;
import io.github.chaosawakens.client.model.base.WrappedAgeableHierarchalModel;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.CarrotPig;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.Nullable;

public class CarrotPigModel<CP extends CarrotPig> extends WrappedAgeableHierarchalModel<CP> {
	public static final ModelLayerLocation BASE_LAYER = new ModelLayerLocation(CAConstants.prefix("carrot_pig"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart SingleCarrot2;
	private final ModelPart leg0;
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;
	private final ModelPart SingleCarrot1P2;
	private final ModelPart BigCarrot1;
	private final ModelPart BigCarrot2;
	private final ModelPart SingleCarrot1;

	public CarrotPigModel(ModelPart root) {
		this.root = root;

		this.body = root.getChild("body");
		this.head = this.body.getChild("head");

		this.SingleCarrot2 = this.head.getChild("SingleCarrot2");

		this.leg0 = this.body.getChild("leg0");
		this.leg1 = this.body.getChild("leg1");
		this.leg2 = this.body.getChild("leg2");
		this.leg3 = this.body.getChild("leg3");

		this.SingleCarrot1P2 = this.body.getChild("SingleCarrot1P2");

		this.BigCarrot1 = this.body.getChild("BigCarrot1");
		this.BigCarrot2 = this.body.getChild("BigCarrot2");

		this.SingleCarrot1 = this.body.getChild("SingleCarrot1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition rootPartDefinition = meshDefinition.getRoot();

		PartDefinition body = rootPartDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -3.9875F, -7.4F, 10.0F, 8.0F, 15.0F, new CubeDeformation(-0.05F)), PartPose.offset(0.0F, 13.9875F, 0.4F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 37).addBox(-2.0F, 0.025F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.0125F, 7.9F, -1.5708F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(48, 41).addBox(-2.0F, 0.0F, -8.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 23).addBox(-4.0F, -4.0F, -7.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.9875F, -7.4F));

		PartDefinition SingleCarrot2 = head.addOrReplaceChild("SingleCarrot2", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0408F, -3.7125F, -3.2127F, -3.1416F, -0.6109F, 3.1416F));

		PartDefinition cube_r2 = SingleCarrot2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(48, 45).addBox(-0.6984F, -2.4061F, 0.1165F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.6059F, -2.0875F, -0.55F, 0.3353F, -0.7209F, 0.303F));
		PartDefinition cube_r3 = SingleCarrot2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(48, 45).addBox(-2.5F, -2.5F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5446F, -2.0875F, -0.5298F, 0.5236F, -0.6109F, 0.0F));

		PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(32, 37).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 5.0125F, 6.6F));
		PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 39).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 5.0125F, 6.6F));
		PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(16, 39).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 5.0125F, -5.4F));
		PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(32, 47).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 5.0125F, -5.4F));

		PartDefinition SingleCarrot1P2 = body.addOrReplaceChild("SingleCarrot1P2", CubeListBuilder.create().texOffs(6, 49).addBox(-8.6864F, -13.0F, -2.622F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0125F, 2.6F, 0.0F, -0.7418F, 0.0F));

		PartDefinition BigCarrot1 = body.addOrReplaceChild("BigCarrot1", CubeListBuilder.create().texOffs(32, 30).addBox(-5.0F, -15.0F, 1.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 4.0125F, 2.6F, 0.0F, -0.7418F, 0.0F));
		PartDefinition BigCarrot2 = body.addOrReplaceChild("BigCarrot2", CubeListBuilder.create().texOffs(32, 23).addBox(-5.0F, -15.0F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0125F, 2.6F, 0.0F, 0.9163F, 0.0F));

		PartDefinition SingleCarrot1 = body.addOrReplaceChild("SingleCarrot1", CubeListBuilder.create().texOffs(0, 49).addBox(3.378F, -13.0F, 6.6864F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 4.0125F, -3.4F, -3.1416F, -0.829F, 3.1416F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(CP pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

		// Base
		animate(pEntity.idleAnimState, CarrotPigAnimation.IDLE, pAgeInTicks);

		// Walk Cycle
		animateWalk(pEntity.isPanicking() ? CarrotPigAnimation.RUN : CarrotPigAnimation.WALK, pLimbSwing, pLimbSwingAmount, pEntity.isBaby() && pEntity.isPanicking() ? 0.565F : 1.3545F, pEntity.isPanicking() ? 2.865F : 1.655F);

		// Misc
		if (pEntity.isSheared()) applyStatic(CarrotPigAnimation.SHEARED);
	}

	@Override
	public @Nullable ModelPart head() {
		return head;
	}

	@Override
	public ModelPart root() {
		return root;
	}
}