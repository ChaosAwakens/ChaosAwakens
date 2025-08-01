package io.github.chaosawakens.client.model.hostile;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.animation.baked.hostile.ent.EntAltBaseAnimation;
import io.github.chaosawakens.client.animation.baked.hostile.ent.EntAttackAnimation;
import io.github.chaosawakens.client.animation.baked.hostile.ent.EntBaseAnimation;
import io.github.chaosawakens.client.animation.baked.hostile.ent.EntWalkAnimation;
import io.github.chaosawakens.client.model.base.WrappedHierarchicalModel;
import io.github.chaosawakens.common.entity.prototype.hostile.Ent;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntModel<E extends Ent> extends WrappedHierarchicalModel<E> {
	public static final ModelLayerLocation BASE_LAYER = new ModelLayerLocation(CAConstants.prefix("ent"), "main");
	private final ModelPart root;
	private final ModelPart left_leg;
	private final ModelPart left_leg_segment;
	private final ModelPart right_leg;
	private final ModelPart right_leg_segment;
	private final ModelPart torso;
	private final ModelPart branches;
	private final ModelPart leaves;
	private final ModelPart branches3;
	private final ModelPart leaves3;
	private final ModelPart branches2;
	private final ModelPart leaves2;
	private final ModelPart left_arm;
	private final ModelPart left_arm_segment;
	private final ModelPart left_hand;
	private final ModelPart punch_effect;
	private final ModelPart punch_effect2;
	private final ModelPart punch_effect3;
	private final ModelPart right_arm;
	private final ModelPart right_arm_segment;
	private final ModelPart right_hand;
	private final ModelPart punch_effect4;
	private final ModelPart punch_effect5;
	private final ModelPart punch_effect6;
	private final ModelPart head;
	private final ModelPart core;

	public EntModel(ModelPart root) {
		this.root = root.getChild("root");

		this.left_leg = this.root.getChild("left_leg");
		this.left_leg_segment = this.left_leg.getChild("left_leg_segment");
		this.right_leg = this.root.getChild("right_leg");
		this.right_leg_segment = this.right_leg.getChild("right_leg_segment");

		this.torso = this.root.getChild("torso");

		this.branches = this.torso.getChild("branches");
		this.leaves = this.branches.getChild("leaves");

		this.branches3 = this.torso.getChild("branches3");
		this.leaves3 = this.branches3.getChild("leaves3");

		this.branches2 = this.torso.getChild("branches2");
		this.leaves2 = this.branches2.getChild("leaves2");

		this.left_arm = this.torso.getChild("left_arm");
		this.left_arm_segment = this.left_arm.getChild("left_arm_segment");
		this.left_hand = this.left_arm_segment.getChild("left_hand");

		this.punch_effect = this.left_hand.getChild("punch_effect");
		this.punch_effect2 = this.left_hand.getChild("punch_effect2");
		this.punch_effect3 = this.left_hand.getChild("punch_effect3");

		this.right_arm = this.torso.getChild("right_arm");
		this.right_arm_segment = this.right_arm.getChild("right_arm_segment");
		this.right_hand = this.right_arm_segment.getChild("right_hand");

		this.punch_effect4 = this.right_hand.getChild("punch_effect4");
		this.punch_effect5 = this.right_hand.getChild("punch_effect5");
		this.punch_effect6 = this.right_hand.getChild("punch_effect6");

		this.head = this.torso.getChild("head");
		this.core = this.torso.getChild("core");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition rootPartDefinition = meshDefinition.getRoot();

		PartDefinition root = rootPartDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0093F, 24.258F, 10.6565F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(94, 89).addBox(-3.0F, -0.5F, -4.5F, 6.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(4.4907F, -17.508F, -3.6565F));

		PartDefinition left_leg_segment = left_leg.addOrReplaceChild("left_leg_segment", CubeListBuilder.create().texOffs(0, 36).addBox(-4.0646F, -1.2829F, -4.9585F, 8.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(34, 68).addBox(-4.0646F, 4.7171F, -4.9585F, 8.0F, 6.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offset(0.5646F, 6.5329F, -0.0415F));

		PartDefinition cube_r1 = left_leg_segment.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(93, 21).addBox(-4.75F, 1.5F, -4.1875F, 6.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.4354F, -3.5329F, 0.0415F, -0.0873F, 0.0F, 0.6109F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(64, 89).addBox(-3.0F, -0.5F, -4.5F, 6.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5093F, -17.508F, -3.6565F));

		PartDefinition right_leg_segment = right_leg.addOrReplaceChild("right_leg_segment", CubeListBuilder.create().texOffs(32, 24).addBox(-3.9354F, -1.2829F, -4.9585F, 8.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(60, 58).addBox(-3.9354F, 4.7171F, -4.9585F, 8.0F, 6.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offset(-0.5646F, 6.5329F, -0.0415F));

		PartDefinition cube_r2 = right_leg_segment.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(34, 84).addBox(-1.25F, 1.5F, -4.1875F, 6.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.4354F, -3.5329F, 0.0415F, -0.0873F, 0.0F, -0.6109F));

		PartDefinition torso = root.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 17).addBox(-6.1621F, -4.1874F, -3.2942F, 11.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.6528F, -17.8205F, -5.3623F));

		PartDefinition cube_r3 = torso.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(60, 116).addBox(-3.5F, -5.0F, -4.0F, 4.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6621F, -17.4601F, 2.6268F, 0.0F, 0.0F, -1.5708F));
		PartDefinition cube_r4 = torso.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(114, 13).addBox(0.4375F, -2.25F, -5.5F, 10.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8816F, -5.375F, 6.2964F, -2.8008F, 0.6219F, -1.504F));
		PartDefinition cube_r5 = torso.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(115, 84).addBox(-5.0F, -3.5F, -3.0F, 10.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.6532F, -4.6915F, 6.7294F, 0.0F, 0.5236F, 1.0908F));
		PartDefinition cube_r6 = torso.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(104, 71).addBox(-5.0F, -2.5F, -3.5F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8351F, -10.9314F, 0.5469F, 2.4024F, 0.1073F, 0.502F));
		PartDefinition cube_r7 = torso.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(113, 50).addBox(-5.0F, -2.5F, -0.5F, 10.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1593F, -10.9314F, 0.5469F, 2.5759F, -0.188F, -1.1024F));
		PartDefinition cube_r8 = torso.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(105, 37).addBox(-4.5F, -3.5F, -5.75F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.6719F, -4.7275F, 1.4558F, 0.0F, -0.0873F, 1.0908F));

		PartDefinition cube_r9 = torso.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -8.5F, -5.25F, 11.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6621F, -5.6874F, 1.7058F, 0.0F, 0.0F, 2.0071F));

		PartDefinition branches = torso.addOrReplaceChild("branches", CubeListBuilder.create(), PartPose.offset(4.192F, -6.5961F, 6.3941F));

		PartDefinition cube_r10 = branches.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(48, 22).addBox(-8.3272F, 1.2448F, -4.805F, 11.0F, 0.1F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9294F, -2.6698F, 8.2341F, 2.0396F, -1.4935F, -2.597F));

		PartDefinition cube_r11 = branches.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(49, 46).addBox(-9.0746F, -0.8521F, -4.0937F, 11.0F, 0.1F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9294F, -2.6698F, 8.2341F, 2.0292F, -1.1795F, -0.9963F));

		PartDefinition leaves = branches.addOrReplaceChild("leaves", CubeListBuilder.create(), PartPose.offset(0.4522F, -3.761F, 10.6981F));

		PartDefinition cube_r12 = leaves.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 58).addBox(-0.2727F, -5.6719F, -5.6496F, 6.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4772F, 1.0912F, -2.464F, 1.2098F, -1.1996F, -2.0496F));

		PartDefinition branches3 = torso.addOrReplaceChild("branches3", CubeListBuilder.create(), PartPose.offset(-0.445F, -1.041F, 7.1317F));

		PartDefinition cube_r13 = branches3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(55, 0).addBox(-8.4056F, -1.2022F, -4.5045F, 11.0F, 0.1F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0625F, 1.9918F, 6.6528F, 0.4037F, -1.0992F, 0.2929F));

		PartDefinition cube_r14 = branches3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(58, 32).addBox(-7.8021F, 1.5959F, -5.3879F, 11.0F, 0.1F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0625F, 1.9918F, 6.6528F, -1.8842F, -1.008F, 1.1017F));

		PartDefinition leaves3 = branches3.addOrReplaceChild("leaves3", CubeListBuilder.create(), PartPose.offset(1.997F, 2.5096F, 9.7094F));

		PartDefinition cube_r15 = leaves3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(42, 0).addBox(0.1594F, -5.0381F, -6.0375F, 6.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9345F, -0.5177F, -3.0566F, -2.2942F, -1.3119F, 1.2311F));

		PartDefinition branches2 = torso.addOrReplaceChild("branches2", CubeListBuilder.create(), PartPose.offset(-5.2561F, -11.42F, 8.047F));

		PartDefinition cube_r16 = branches2.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(70, 20).addBox(-8.4673F, -0.6466F, -4.0558F, 11.0F, 0.1F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4343F, -4.2136F, 6.3309F, 2.6101F, -0.9859F, -1.4577F));

		PartDefinition cube_r17 = branches2.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(66, 10).addBox(-7.6943F, 1.1425F, -4.8433F, 11.0F, 0.1F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4343F, -4.2136F, 6.3309F, 1.733F, -1.1702F, -2.2379F));

		PartDefinition leaves2 = branches2.addOrReplaceChild("leaves2", CubeListBuilder.create(), PartPose.offset(-1.3563F, -6.2092F, 8.8806F));

		PartDefinition cube_r18 = leaves2.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(36, 46).addBox(0.3632F, -5.5857F, -5.6223F, 6.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.922F, 1.9955F, -2.5497F, 1.8548F, -0.8746F, -2.068F));

		PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(3.9352F, -14.2016F, -0.2364F));

		PartDefinition cube_r19 = left_arm.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(32, 100).addBox(-10.0647F, -3.4501F, -2.6942F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.1514F, 1.9817F, 1.4694F, 2.4024F, 0.1073F, 0.022F));

		PartDefinition left_arm_segment = left_arm.addOrReplaceChild("left_arm_segment", CubeListBuilder.create(), PartPose.offset(8.0F, 1.5F, 0.0F));

		PartDefinition cube_r20 = left_arm_segment.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(130, 67).addBox(-3.0F, -4.875F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(10.0172F, -2.4752F, 2.3726F, 2.5336F, 0.462F, 0.4748F));
		PartDefinition cube_r21 = left_arm_segment.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 119).addBox(0.8535F, -2.9102F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(2.6837F, 2.8131F, -1.606F, 2.3974F, 0.0483F, -0.0425F));
		PartDefinition cube_r22 = left_arm_segment.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 108).addBox(-7.7652F, -3.394F, -2.5F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6837F, 2.8131F, -1.606F, 2.4917F, 0.3952F, 0.374F));
		PartDefinition cube_r23 = left_arm_segment.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(30, 113).addBox(-2.9353F, -1.9874F, -2.8058F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0264F, -0.5183F, 1.0319F, 2.3964F, 0.0247F, -0.0682F));

		PartDefinition left_hand = left_arm_segment.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(9.7187F, 0.8459F, -1.1314F));

		PartDefinition cube_r24 = left_hand.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(98, 0).addBox(-2.75F, -5.75F, -3.0F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2813F, -0.3459F, 1.1314F, 2.6453F, 0.5816F, 0.6977F));

		PartDefinition punch_effect = left_hand.addOrReplaceChild("punch_effect", CubeListBuilder.create(), PartPose.offset(4.2667F, 3.8334F, -3.0399F));

		PartDefinition cube_r25 = punch_effect.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 80).addBox(-6.9375F, -5.5F, -5.0938F, 17.0F, 15.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0734F, -4.6571F, 0.8637F, 0.6545F, -0.7854F, 0.0F));

		PartDefinition punch_effect2 = left_hand.addOrReplaceChild("punch_effect2", CubeListBuilder.create(), PartPose.offset(4.365F, -3.2381F, -1.5983F));

		PartDefinition cube_r26 = punch_effect2.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(70, 134).addBox(-3.1875F, 2.0F, 0.95F, 7.0F, 8.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.865F, -5.7619F, 0.6483F, -0.4085F, -0.9132F, -0.0881F));

		PartDefinition punch_effect3 = left_hand.addOrReplaceChild("punch_effect3", CubeListBuilder.create(), PartPose.offsetAndRotation(4.365F, 0.7619F, 4.4017F, -1.0208F, -0.0437F, 0.5235F));

		PartDefinition cube_r27 = punch_effect3.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(85, 89).addBox(-3.1875F, 2.0F, 0.95F, 7.0F, 8.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.865F, -5.7619F, 0.6483F, -0.4085F, -0.9132F, -0.0881F));

		PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.2595F, -14.2016F, -0.2364F));

		PartDefinition cube_r28 = right_arm.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(96, 58).addBox(0.0647F, -3.4501F, -2.6942F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.1514F, 1.9817F, 1.4694F, 2.4024F, -0.1073F, -0.022F));

		PartDefinition right_arm_segment = right_arm.addOrReplaceChild("right_arm_segment", CubeListBuilder.create(), PartPose.offset(-8.0F, 1.5F, 0.0F));

		PartDefinition cube_r29 = right_arm_segment.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(82, 116).addBox(-3.0F, -4.875F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-10.0172F, -2.4752F, 2.3726F, 2.5336F, -0.462F, -0.4748F));
		PartDefinition cube_r30 = right_arm_segment.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(104, 116).addBox(-6.8535F, -2.9102F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-2.6837F, 2.8131F, -1.606F, 2.3974F, -0.0483F, 0.0425F));
		PartDefinition cube_r31 = right_arm_segment.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(64, 105).addBox(-2.2348F, -3.394F, -2.5F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6837F, 2.8131F, -1.606F, 2.4917F, -0.3952F, -0.374F));
		PartDefinition cube_r32 = right_arm_segment.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(94, 105).addBox(-7.0647F, -1.9874F, -2.8058F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0264F, -0.5183F, 1.0319F, 2.3964F, -0.0247F, 0.0682F));

		PartDefinition right_hand = right_arm_segment.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-9.7187F, 0.8459F, -1.1314F));

		PartDefinition cube_r33 = right_hand.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(0, 95).addBox(-7.25F, -5.75F, -3.0F, 10.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2813F, -0.3459F, 1.1314F, 2.6453F, -0.5816F, -0.6977F));

		PartDefinition punch_effect4 = right_hand.addOrReplaceChild("punch_effect4", CubeListBuilder.create(), PartPose.offset(-4.2667F, 3.8334F, -3.0399F));

		PartDefinition cube_r34 = punch_effect4.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(70, 74).addBox(-10.0625F, -5.5F, -5.0938F, 17.0F, 15.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0734F, -4.6571F, 0.8637F, 0.6545F, 0.7854F, 0.0F));

		PartDefinition punch_effect5 = right_hand.addOrReplaceChild("punch_effect5", CubeListBuilder.create(), PartPose.offset(-4.365F, -3.2381F, -1.5983F));

		PartDefinition cube_r35 = punch_effect5.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(55, 84).addBox(-3.8125F, 2.0F, 0.95F, 7.0F, 8.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.865F, -5.7619F, 0.6483F, -0.4085F, 0.9132F, 0.0881F));

		PartDefinition punch_effect6 = right_hand.addOrReplaceChild("punch_effect6", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.365F, 0.7619F, 4.4017F, -1.0208F, 0.0437F, -0.5235F));

		PartDefinition cube_r36 = punch_effect6.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(32, 0).addBox(-3.8125F, 2.0F, 0.95F, 7.0F, 8.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.865F, -5.7619F, 0.6483F, -0.4085F, 0.9132F, 0.0881F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(81, 42).addBox(-4.0F, -7.4727F, -5.1631F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.6621F, -18.4874F, 1.2899F));

		PartDefinition cube_r37 = head.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(82, 125).addBox(-5.4591F, -2.4918F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.2448F, -7.5766F, 1.5042F, 2.8373F, 0.6974F, 1.5874F));
		PartDefinition cube_r38 = head.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(17, 124).addBox(-0.5398F, -2.4867F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-3.2448F, -7.5766F, 1.5042F, 2.5822F, 0.5299F, 1.1505F));
		PartDefinition cube_r39 = head.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(123, 23).addBox(-2.375F, -0.6875F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.4045F, -13.284F, 2.9766F, 2.8313F, 0.3483F, 1.2528F));
		PartDefinition cube_r40 = head.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(121, 120).addBox(-6.625F, 0.3125F, -2.25F, 6.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-6.1284F, -4.7613F, 0.3069F, 2.7252F, 0.207F, 0.8712F));
		PartDefinition cube_r41 = head.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(121, 111).addBox(-3.5F, -2.0F, -2.25F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.1284F, -4.7613F, 0.3069F, 2.9723F, 0.4323F, 1.6196F));
		PartDefinition cube_r42 = head.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(119, 100).addBox(-3.0F, -1.25F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9098F, -1.2678F, -1.3882F, 2.7233F, 0.3602F, 1.0489F));
		PartDefinition cube_r43 = head.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(34, 128).addBox(-3.625F, -0.6875F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.4045F, -13.284F, 2.9766F, 2.8313F, -0.3483F, -1.2528F));
		PartDefinition cube_r44 = head.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(128, 58).addBox(0.625F, 0.3125F, -2.25F, 6.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(6.1284F, -4.7613F, 0.3069F, 2.7252F, -0.207F, -0.8712F));
		PartDefinition cube_r45 = head.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(104, 125).addBox(-0.5409F, -2.4918F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.2448F, -7.5766F, 1.5042F, 2.8373F, -0.6974F, -1.5874F));
		PartDefinition cube_r46 = head.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(0, 128).addBox(-5.4602F, -2.4867F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(3.2448F, -7.5766F, 1.5042F, 2.5822F, -0.5299F, -1.1505F));
		PartDefinition cube_r47 = head.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(121, 129).addBox(-2.5F, -2.0F, -2.25F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.1284F, -4.7613F, 0.3069F, 2.9723F, -0.4323F, -1.6196F));
		PartDefinition cube_r48 = head.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(130, 0).addBox(-3.0F, -1.25F, -2.5F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.9098F, -1.2678F, -1.3882F, 2.7233F, -0.3602F, -1.0489F));

		PartDefinition core = torso.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0189F, -8.0938F, 0.0339F));

		PartDefinition cube_r49 = core.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(131, 32).addBox(-3.5F, -4.0F, -0.05F, 7.0F, 8.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1623F, -0.9132F, -0.0881F));
		PartDefinition cube_r50 = core.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(56, 133).addBox(-3.1875F, 2.0F, 0.95F, 7.0F, 8.0F, 0.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.865F, -5.7619F, 0.6483F, -0.4085F, -0.9132F, -0.0881F));

		return LayerDefinition.create(meshDefinition, 256, 256);
	}

	@Override
	public void setupAnim(E pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
		super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

		// Base
		animate(pEntity.idleAnimState, EntBaseAnimation.IDLE, pAgeInTicks);
		animate(pEntity.coreAnimState, EntAltBaseAnimation.CORE, pAgeInTicks);

		animate(pEntity.deathAnimState, EntAltBaseAnimation.DEATH, pAgeInTicks);

		// Attack
		animate(pEntity.leftArborealPunchAttackAnim, EntAttackAnimation.ARBOREAL_PUNCH_LEFT, pAgeInTicks);
		animate(pEntity.rightArborealPunchAttackAnim, EntAttackAnimation.ARBOREAL_PUNCH_RIGHT, pAgeInTicks);

		animate(pEntity.entSmashAttackAnim, EntAttackAnimation.ENT_SMASH, pAgeInTicks);

		// Walk Cycle
		if (!pEntity.isFunctionallyAnimatingAttack() && !pEntity.isDeadOrDying()) {
			animateWalk(EntWalkAnimation.WALK, pLimbSwing, pLimbSwingAmount, 1.0F, 1.0F);
		}
	}

	@Override
	protected void animateWalk(AnimationDefinition targetAnimDef, float limbSwing, float limbSwingAmount, float animSpeed, float animScale) {
		long accumulatedTime = (long) (limbSwing * 50.0F * animSpeed);
		float adjustedAnimScale = Math.min(limbSwingAmount * animScale, 1.0F);

		KeyframeAnimations.animate(this, targetAnimDef, accumulatedTime, adjustedAnimScale, ANIMATION_VECTOR_CACHE);
	}

	@Override
	public @NotNull ModelPart root() {
		return root;
	}

	@Override
	public @Nullable ModelPart head() {
		return head;
	}
}