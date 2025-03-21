package io.github.chaosawakens.client.model.passive.animal.land;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.animation.baked.passive.animal.land.stink_bug.StinkBugBaseAnimation;
import io.github.chaosawakens.client.animation.baked.passive.animal.land.stink_bug.StinkBugWalkAnimation;
import io.github.chaosawakens.client.model.base.WrappedAgeableHierarchalModel;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.StinkBug;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.Nullable;

public class StinkBugModel<SB extends StinkBug> extends WrappedAgeableHierarchalModel<SB> {
	public static final ModelLayerLocation BASE_LAYER = new ModelLayerLocation(CAConstants.prefix("stink_bug"), "main");
	private final ModelPart root;
	private final ModelPart bugRoot;
	private final ModelPart Torso;
	private final ModelPart Head;
	private final ModelPart right_blink;
	private final ModelPart left_blink;
	private final ModelPart Right_Antena;
	private final ModelPart Right_Antena_segment;
	private final ModelPart Right_antena_segment2;
	private final ModelPart Right_antena_segment3;
	private final ModelPart Right_antena_segment4;
	private final ModelPart Right_antena_segment5;
	private final ModelPart Right_antena_segment6;
	private final ModelPart Right_antena_segment7;
	private final ModelPart Right_antena_segment8;
	private final ModelPart Right_antena_segment9;
	private final ModelPart Right_antena_segment10;
	private final ModelPart Right_antena_segment11;
	private final ModelPart Right_antena_segment12;
	private final ModelPart Right_antena_segment13;
	private final ModelPart Right_antena_segment14;
	private final ModelPart Right_antena_segment15;
	private final ModelPart Left_Antena;
	private final ModelPart Left_Antena_segment;
	private final ModelPart Left_antena_segment2;
	private final ModelPart Left_antena_segment3;
	private final ModelPart Left_antena_segment4;
	private final ModelPart Left_antena_segment5;
	private final ModelPart Left_antena_segment6;
	private final ModelPart Left_antena_segment7;
	private final ModelPart Left_antena_segment8;
	private final ModelPart Left_antena_segment9;
	private final ModelPart Left_antena_segment10;
	private final ModelPart Left_antena_segment11;
	private final ModelPart Left_antena_segment12;
	private final ModelPart Left_antena_segment13;
	private final ModelPart Left_antena_segment14;
	private final ModelPart Left_antena_segment15;
	private final ModelPart Abdomen_start;
	private final ModelPart Abdomen_center;
	private final ModelPart Abdomen_end;
	private final ModelPart Stink_sack;
	private final ModelPart Right_back_leg;
	private final ModelPart Left_back_leg;
	private final ModelPart Right_center_leg;
	private final ModelPart Left_center_leg;
	private final ModelPart Right_front_leg;
	private final ModelPart Left_front_leg;

	public StinkBugModel(ModelPart root) {
		this.root = root;

		this.bugRoot = root.getChild("Root");
		this.Torso = this.bugRoot.getChild("Torso");

		this.Head = this.Torso.getChild("Head");

		this.right_blink = this.Head.getChild("right_blink");
		this.left_blink = this.Head.getChild("left_blink");

		this.Right_Antena = this.Head.getChild("Right_Antena");

		this.Right_Antena_segment = this.Right_Antena.getChild("Right_Antena_segment");
		this.Right_antena_segment2 = this.Right_Antena_segment.getChild("Right_antena_segment2");
		this.Right_antena_segment3 = this.Right_antena_segment2.getChild("Right_antena_segment3");
		this.Right_antena_segment4 = this.Right_antena_segment3.getChild("Right_antena_segment4");
		this.Right_antena_segment5 = this.Right_antena_segment4.getChild("Right_antena_segment5");
		this.Right_antena_segment6 = this.Right_antena_segment5.getChild("Right_antena_segment6");
		this.Right_antena_segment7 = this.Right_antena_segment6.getChild("Right_antena_segment7");
		this.Right_antena_segment8 = this.Right_antena_segment7.getChild("Right_antena_segment8");
		this.Right_antena_segment9 = this.Right_antena_segment8.getChild("Right_antena_segment9");
		this.Right_antena_segment10 = this.Right_antena_segment9.getChild("Right_antena_segment10");
		this.Right_antena_segment11 = this.Right_antena_segment10.getChild("Right_antena_segment11");
		this.Right_antena_segment12 = this.Right_antena_segment11.getChild("Right_antena_segment12");
		this.Right_antena_segment13 = this.Right_antena_segment12.getChild("Right_antena_segment13");
		this.Right_antena_segment14 = this.Right_antena_segment13.getChild("Right_antena_segment14");
		this.Right_antena_segment15 = this.Right_antena_segment14.getChild("Right_antena_segment15");

		this.Left_Antena = this.Head.getChild("Left_Antena");

		this.Left_Antena_segment = this.Left_Antena.getChild("Left_Antena_segment");
		this.Left_antena_segment2 = this.Left_Antena_segment.getChild("Left_antena_segment2");
		this.Left_antena_segment3 = this.Left_antena_segment2.getChild("Left_antena_segment3");
		this.Left_antena_segment4 = this.Left_antena_segment3.getChild("Left_antena_segment4");
		this.Left_antena_segment5 = this.Left_antena_segment4.getChild("Left_antena_segment5");
		this.Left_antena_segment6 = this.Left_antena_segment5.getChild("Left_antena_segment6");
		this.Left_antena_segment7 = this.Left_antena_segment6.getChild("Left_antena_segment7");
		this.Left_antena_segment8 = this.Left_antena_segment7.getChild("Left_antena_segment8");
		this.Left_antena_segment9 = this.Left_antena_segment8.getChild("Left_antena_segment9");
		this.Left_antena_segment10 = this.Left_antena_segment9.getChild("Left_antena_segment10");
		this.Left_antena_segment11 = this.Left_antena_segment10.getChild("Left_antena_segment11");
		this.Left_antena_segment12 = this.Left_antena_segment11.getChild("Left_antena_segment12");
		this.Left_antena_segment13 = this.Left_antena_segment12.getChild("Left_antena_segment13");
		this.Left_antena_segment14 = this.Left_antena_segment13.getChild("Left_antena_segment14");
		this.Left_antena_segment15 = this.Left_antena_segment14.getChild("Left_antena_segment15");

		this.Abdomen_start = this.Torso.getChild("Abdomen_start");
		this.Abdomen_center = this.Abdomen_start.getChild("Abdomen_center");
		this.Abdomen_end = this.Abdomen_center.getChild("Abdomen_end");

		this.Stink_sack = this.Abdomen_end.getChild("Stink_sack");

		this.Right_back_leg = this.bugRoot.getChild("Right_back_leg");
		this.Left_back_leg = this.bugRoot.getChild("Left_back_leg");

		this.Right_center_leg = this.bugRoot.getChild("Right_center_leg");
		this.Left_center_leg = this.bugRoot.getChild("Left_center_leg");

		this.Right_front_leg = this.bugRoot.getChild("Right_front_leg");
		this.Left_front_leg = this.bugRoot.getChild("Left_front_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition rootPartDefinition = meshDefinition.getRoot();

		PartDefinition Root = rootPartDefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 18.25F, 5.05F));
		PartDefinition Torso = Root.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.75F, -6.05F));

		PartDefinition Head = Torso.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(32, 32).addBox(-2.9167F, -1.8833F, -4.9167F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0833F, -1.1167F, -5.0833F));

		PartDefinition right_blink = Head.addOrReplaceChild("right_blink", CubeListBuilder.create().texOffs(38, 5).addBox(-3.0F, -1.5F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0833F, 0.6167F, 0.0833F));
		PartDefinition left_blink = Head.addOrReplaceChild("left_blink", CubeListBuilder.create().texOffs(38, 5).addBox(1.0F, -1.5F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0833F, 0.6167F, 0.0833F));

		PartDefinition Right_Antena = Head.addOrReplaceChild("Right_Antena", CubeListBuilder.create().texOffs(36, 30).addBox(-3.9F, -0.025F, -0.55F, 4.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0167F, -1.8083F, -4.3667F));

		PartDefinition Right_Antena_segment = Right_Antena.addOrReplaceChild("Right_Antena_segment", CubeListBuilder.create().texOffs(36, 31).addBox(-2.0F, -0.025F, -0.55F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.9F, 0.0F, 0.0F));
		PartDefinition Right_antena_segment2 = Right_Antena_segment.addOrReplaceChild("Right_antena_segment2", CubeListBuilder.create().texOffs(36, 31).addBox(-2.0F, -0.025F, -0.55F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 0.0F));
		PartDefinition Right_antena_segment3 = Right_antena_segment2.addOrReplaceChild("Right_antena_segment3", CubeListBuilder.create().texOffs(36, 31).addBox(-2.0F, -0.025F, -0.55F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 0.0F));
		PartDefinition Right_antena_segment4 = Right_antena_segment3.addOrReplaceChild("Right_antena_segment4", CubeListBuilder.create().texOffs(36, 31).addBox(-2.0F, -0.025F, -0.55F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 0.0F));
		PartDefinition Right_antena_segment5 = Right_antena_segment4.addOrReplaceChild("Right_antena_segment5", CubeListBuilder.create().texOffs(38, 10).addBox(-0.5182F, -0.025F, -0.0273F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4818F, 0.0F, 0.4773F));
		PartDefinition Right_antena_segment6 = Right_antena_segment5.addOrReplaceChild("Right_antena_segment6", CubeListBuilder.create().texOffs(38, 10).addBox(-0.55F, -0.025F, -0.05F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0318F, 0.0F, 2.0227F));
		PartDefinition Right_antena_segment7 = Right_antena_segment6.addOrReplaceChild("Right_antena_segment7", CubeListBuilder.create().texOffs(38, 10).addBox(-0.5333F, -0.025F, -0.0222F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0167F, 0.0F, 1.9722F));
		PartDefinition Right_antena_segment8 = Right_antena_segment7.addOrReplaceChild("Right_antena_segment8", CubeListBuilder.create().texOffs(36, 31).addBox(-0.4875F, -0.025F, 0.0125F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0458F, 0.0F, 1.9653F));
		PartDefinition Right_antena_segment9 = Right_antena_segment8.addOrReplaceChild("Right_antena_segment9", CubeListBuilder.create().texOffs(36, 31).addBox(0.0F, -0.025F, -0.5F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5125F, 0.0F, 0.5125F));
		PartDefinition Right_antena_segment10 = Right_antena_segment9.addOrReplaceChild("Right_antena_segment10", CubeListBuilder.create().texOffs(36, 31).addBox(-0.05F, -0.025F, -0.4333F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.05F, 0.0F, -0.0667F));
		PartDefinition Right_antena_segment11 = Right_antena_segment10.addOrReplaceChild("Right_antena_segment11", CubeListBuilder.create().texOffs(38, 10).addBox(-0.5F, -0.025F, -2.0F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.45F, 0.0F, -0.4333F));
		PartDefinition Right_antena_segment12 = Right_antena_segment11.addOrReplaceChild("Right_antena_segment12", CubeListBuilder.create().texOffs(38, 10).addBox(-0.5F, -0.025F, -2.0F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));
		PartDefinition Right_antena_segment13 = Right_antena_segment12.addOrReplaceChild("Right_antena_segment13", CubeListBuilder.create().texOffs(36, 31).addBox(-2.0167F, -0.025F, -0.5833F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.4833F, 0.0F, -1.4167F));
		PartDefinition Right_antena_segment14 = Right_antena_segment13.addOrReplaceChild("Right_antena_segment14", CubeListBuilder.create().texOffs(38, 10).addBox(-0.95F, -0.025F, -0.45F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0667F, 0.0F, -0.1333F));
		PartDefinition Right_antena_segment15 = Right_antena_segment14.addOrReplaceChild("Right_antena_segment15", CubeListBuilder.create().texOffs(36, 31).addBox(-0.5F, -0.025F, 0.0F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.45F, 0.0F, 1.55F));

		PartDefinition Left_Antena = Head.addOrReplaceChild("Left_Antena", CubeListBuilder.create().texOffs(36, 30).addBox(-0.1F, -0.025F, -0.55F, 4.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.1833F, -1.8083F, -4.3667F));

		PartDefinition Left_Antena_segment = Left_Antena.addOrReplaceChild("Left_Antena_segment", CubeListBuilder.create().texOffs(36, 31).addBox(0.0F, -0.025F, -0.55F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.9F, 0.0F, 0.0F));
		PartDefinition Left_antena_segment2 = Left_Antena_segment.addOrReplaceChild("Left_antena_segment2", CubeListBuilder.create().texOffs(36, 31).addBox(0.0F, -0.025F, -0.55F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));
		PartDefinition Left_antena_segment3 = Left_antena_segment2.addOrReplaceChild("Left_antena_segment3", CubeListBuilder.create().texOffs(36, 31).addBox(0.0F, -0.025F, -0.55F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));
		PartDefinition Left_antena_segment4 = Left_antena_segment3.addOrReplaceChild("Left_antena_segment4", CubeListBuilder.create().texOffs(36, 31).addBox(0.0F, -0.025F, -0.55F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));
		PartDefinition Left_antena_segment5 = Left_antena_segment4.addOrReplaceChild("Left_antena_segment5", CubeListBuilder.create().texOffs(38, 10).addBox(-0.4818F, -0.025F, -0.0273F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.4818F, 0.0F, 0.4773F));
		PartDefinition Left_antena_segment6 = Left_antena_segment5.addOrReplaceChild("Left_antena_segment6", CubeListBuilder.create().texOffs(38, 10).addBox(-0.45F, -0.025F, -0.05F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0318F, 0.0F, 2.0227F));
		PartDefinition Left_antena_segment7 = Left_antena_segment6.addOrReplaceChild("Left_antena_segment7", CubeListBuilder.create().texOffs(38, 10).addBox(-0.4667F, -0.025F, -0.0222F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0167F, 0.0F, 1.9722F));
		PartDefinition Left_antena_segment8 = Left_antena_segment7.addOrReplaceChild("Left_antena_segment8", CubeListBuilder.create().texOffs(36, 31).addBox(-1.5125F, -0.025F, 0.0125F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0458F, 0.0F, 1.9653F));
		PartDefinition Left_antena_segment9 = Left_antena_segment8.addOrReplaceChild("Left_antena_segment9", CubeListBuilder.create().texOffs(36, 31).addBox(-2.0F, -0.025F, -0.5F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5125F, 0.0F, 0.5125F));
		PartDefinition Left_antena_segment10 = Left_antena_segment9.addOrReplaceChild("Left_antena_segment10", CubeListBuilder.create().texOffs(36, 31).addBox(-1.95F, -0.025F, -0.4333F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.05F, 0.0F, -0.0667F));
		PartDefinition Left_antena_segment11 = Left_antena_segment10.addOrReplaceChild("Left_antena_segment11", CubeListBuilder.create().texOffs(38, 10).addBox(-0.5F, -0.025F, -2.0F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.45F, 0.0F, -0.4333F));
		PartDefinition Left_antena_segment12 = Left_antena_segment11.addOrReplaceChild("Left_antena_segment12", CubeListBuilder.create().texOffs(38, 10).addBox(-0.5F, -0.025F, -2.0F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));
		PartDefinition Left_antena_segment13 = Left_antena_segment12.addOrReplaceChild("Left_antena_segment13", CubeListBuilder.create().texOffs(36, 31).addBox(0.0167F, -0.025F, -0.5833F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.4833F, 0.0F, -1.4167F));
		PartDefinition Left_antena_segment14 = Left_antena_segment13.addOrReplaceChild("Left_antena_segment14", CubeListBuilder.create().texOffs(38, 10).addBox(-0.05F, -0.025F, -0.45F, 1.0F, 0.05F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0667F, 0.0F, -0.1333F));
		PartDefinition Left_antena_segment15 = Left_antena_segment14.addOrReplaceChild("Left_antena_segment15", CubeListBuilder.create().texOffs(36, 31).addBox(-1.5F, -0.025F, 0.0F, 2.0F, 0.05F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.45F, 0.0F, 1.55F));

		PartDefinition Abdomen_start = Torso.addOrReplaceChild("Abdomen_start", CubeListBuilder.create().texOffs(38, 0).addBox(-3.0F, -2.075F, 0.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.925F, 5.0F));
		PartDefinition Abdomen_center = Abdomen_start.addOrReplaceChild("Abdomen_center", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.075F, 0.0F, 8.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));
		PartDefinition Abdomen_end = Abdomen_center.addOrReplaceChild("Abdomen_end", CubeListBuilder.create().texOffs(36, 16).addBox(-3.0F, -2.075F, 0.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.0F));

		PartDefinition Stink_sack = Abdomen_end.addOrReplaceChild("Stink_sack", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -0.025F, 0.0F, 6.0F, 0.05F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition cube_r1 = Stink_sack.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -0.025F, 0.0F, 6.0F, 0.05F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition Right_back_leg = Root.addOrReplaceChild("Right_back_leg", CubeListBuilder.create(), PartPose.offset(-3.8106F, 1.75F, -1.7215F));

		PartDefinition cube_r2 = Right_back_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(36, 22).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.5F, 0.25F, 0.0F, -0.5236F, 0.0F));

		PartDefinition Left_back_leg = Root.addOrReplaceChild("Left_back_leg", CubeListBuilder.create(), PartPose.offset(3.8106F, 1.75F, -1.7215F));

		PartDefinition cube_r3 = Left_back_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(36, 22).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.5F, 0.25F, 0.0F, 0.5236F, 0.0F));

		PartDefinition Right_center_leg = Root.addOrReplaceChild("Right_center_leg", CubeListBuilder.create(), PartPose.offset(-4.1F, 1.75F, -6.05F));

		PartDefinition cube_r4 = Right_center_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(36, 22).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.5F, 0.0F, 0.0F, -0.6981F, 0.0F));

		PartDefinition Left_center_leg = Root.addOrReplaceChild("Left_center_leg", CubeListBuilder.create(), PartPose.offset(4.1F, 1.75F, -6.05F));

		PartDefinition cube_r5 = Left_center_leg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(36, 22).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.5F, 0.0F, 0.0F, 0.6981F, 0.0F));

		PartDefinition Right_front_leg = Root.addOrReplaceChild("Right_front_leg", CubeListBuilder.create(), PartPose.offset(-3.9638F, 1.75F, -10.5F));

		PartDefinition cube_r6 = Right_front_leg.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(36, 22).addBox(-1.5F, -2.5F, -2.2F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5362F, 1.5F, 0.45F, 0.0F, -0.8727F, 0.0F));

		PartDefinition Left_front_leg = Root.addOrReplaceChild("Left_front_leg", CubeListBuilder.create(), PartPose.offset(3.9638F, 1.75F, -10.5F));

		PartDefinition cube_r7 = Left_front_leg.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(36, 22).addBox(-1.5F, -2.5F, -2.2F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5362F, 1.5F, 0.45F, 0.0F, 0.8727F, 0.0F));

		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(SB pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

		// Base
		animate(pEntity.idleAnimState, StinkBugBaseAnimation.IDLE, pAgeInTicks);

		// Walk Cycle
		animateWalk(pEntity.isPanicking() ? StinkBugWalkAnimation.RUN : StinkBugWalkAnimation.WALK, pLimbSwing, pLimbSwingAmount, (pEntity.isPanicking() ? 1.2F : 1.0F) * (pEntity.isBaby() ? 0.77F : 1.0F), pEntity.isPanicking() ? 1.21F : 2.9F);
	}

	@Override
	public boolean scaleHeadProportionately() {
		return true;
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public @Nullable ModelPart head() {
		return Head;
	}
}