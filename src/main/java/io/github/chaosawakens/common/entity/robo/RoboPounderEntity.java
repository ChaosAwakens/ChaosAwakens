package io.github.chaosawakens.common.entity.robo;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableRoboPunchGoal;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings("ALL")
public class RoboPounderEntity extends RoboEntity implements IAnimatable, IUtilityHelper {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final float punchingTicks;
	private final float sideSweepingTicks;

	public RoboPounderEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
		this.moveControl = new MovementController(this);
		this.maxUpStep = 1.0F;
		this.punchingTicks = 0;
		this.sideSweepingTicks = 0;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 300)
				.add(Attributes.ARMOR, 30)
				.add(Attributes.ARMOR_TOUGHNESS, 20)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 3)
				.add(Attributes.ATTACK_DAMAGE, 20)
				.add(Attributes.ATTACK_KNOCKBACK, 15)
				.add(Attributes.ATTACK_SPEED, 2)
				.add(Attributes.FOLLOW_RANGE, 45);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.dead) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.death", true));
			return PlayState.STOP;
		}
		if (this.getAttacking() && this.getPunching()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.swing_arm_attack", false));
			this.animationSpeed = this.animationSpeed + 2.0F;
			return PlayState.CONTINUE;
		}
		if (this.getAttacking() && this.getSideSweep()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.second_swing_arm_attack", false));
			this.animationSpeed = this.animationSpeed + 2.0F;
			return PlayState.CONTINUE;
		}
		if (this.getRageMode()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.rage_mode_enable", false));
			return PlayState.CONTINUE;
		}
 /*       if (AnimatableRageRunGoal.deactivated()) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.rage_mode_disable", false)); 
        	return PlayState.CONTINUE;
        }*/
		if (this.getRageRunning()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.rage_run", true));
			return PlayState.CONTINUE;
		}
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.walk", true));
			//    event.getController().setAnimationSpeed(event.getController().getAnimationSpeed() + 1.0D);
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_pounder.idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new AnimatableMoveToTargetGoal(this, 1.0, 5)); // 1.5
		this.targetSelector.addGoal(2, new AnimatableRoboPunchGoal(this, this, 48.3, 0.4, 0.5));
		this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 1));
		this.goalSelector.addGoal(4, new SwimGoal(this));
	}

	@OnlyIn(Dist.CLIENT)
	protected void sinkInWater() {
		this.setDeltaMovement(this.getDeltaMovement().add(0.0D, (double)-0.04F * this.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue(), 0.0D));
	}

	@Override
	protected void jumpFromGround() {
		float f = this.getJumpPower();

		if (this.hasEffect(Effects.JUMP)) {
			f += 0.1F * (float)(this.getEffect(Effects.JUMP).getAmplifier() + 1);
		}

		Vector3d vector3d = this.getDeltaMovement();
		this.setDeltaMovement(vector3d.x, (double)f, vector3d.z);

		if (this.isSprinting()) {
			float f1 = this.yRot * ((float)Math.PI / 180F);
			this.setDeltaMovement(this.getDeltaMovement().add((double)(-MathHelper.sin(f1) * 0.2F), 0.0D, (double)(MathHelper.cos(f1) * 0.2F)));
		}

		this.hasImpulse = true;
		ForgeHooks.onLivingJump(this);
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (!this.isAlive()) return;

		boolean flag = false;
		AxisAlignedBB axisalignedbb = this.getBoundingBox().inflate(0.2D);

		//When the pounder is crushing things in its way
		if (this.horizontalCollision && ForgeEventFactory.getMobGriefingEvent(level, this)) {
			for(BlockPos blockpos : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
				BlockState blockstate = this.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				// Todo: pack all these up into a tag for the robo pounder to, well, you know, pound
				if (block instanceof LeavesBlock || block.getBlock() == Blocks.TERRACOTTA || block.getBlock() == Blocks.WHITE_TERRACOTTA || block.getBlock() == BlockTags.BEDS ||block.getBlock() == Blocks.MOSSY_COBBLESTONE || block.getBlock() == Blocks.CHISELED_SANDSTONE || block.getBlock().is(BlockTags.CARPETS) || block.getBlock() == Blocks.LECTERN || block.getBlock() instanceof MelonBlock || block.getBlock() instanceof PumpkinBlock || block.getBlock() instanceof BambooBlock || block.getBlock() == Blocks.CRAFTING_TABLE || block.getBlock() == Blocks.FURNACE || block.getBlock() == Blocks.COMPOSTER || block.getBlock() == Blocks.BLAST_FURNACE || block.getBlock() == Blocks.CHEST || block.getBlock() == Blocks.TRAPPED_CHEST || block.getBlock() == Blocks.SMITHING_TABLE || block.getBlock() == Blocks.FLETCHING_TABLE || block.getBlock() == Blocks.BOOKSHELF || block.getBlock() instanceof TorchBlock || block.getBlock() == Blocks.GLASS_PANE || block.getBlock() == Blocks.SMOKER || block instanceof StainedGlassPaneBlock || block.getBlock().is(BlockTags.DOORS) || block.getBlock().is(BlockTags.TRAPDOORS) || block.getBlock().is(BlockTags.WOODEN_TRAPDOORS) || block instanceof GlassBlock || block.getBlock().is(BlockTags.BASE_STONE_OVERWORLD) || block.getBlock().is(BlockTags.BASE_STONE_NETHER) || block.getBlock() == Blocks.HAY_BLOCK || block.getBlock() == Blocks.SMOOTH_SANDSTONE_SLAB || block.getBlock() == Blocks.SMOOTH_SANDSTONE_STAIRS || block.getBlock() == Blocks.COBBLESTONE || block.getBlock() == Blocks.SNOW || block.getBlock() == Blocks.SNOW_BLOCK ||block.getBlock().is(BlockTags.TALL_FLOWERS) || block.getBlock() == Blocks.SMOOTH_SANDSTONE || block.getBlock() == Blocks.SMOOTH_SANDSTONE_SLAB || block.getBlock() == Blocks.SMOOTH_SANDSTONE_STAIRS || block.getBlock() == Blocks.CHISELED_SANDSTONE || block.getBlock().is(BlockTags.BASE_STONE_OVERWORLD) && block.getBlock() != Blocks.STONE || block.getBlock() == Blocks.SANDSTONE || block.getBlock().is(BlockTags.FENCE_GATES) || block.getBlock().is(BlockTags.SIGNS) || block.getBlock() == Blocks.SANDSTONE_SLAB || block.getBlock() == Blocks.SANDSTONE_STAIRS || block.getBlock() == Blocks.SANDSTONE_WALL || block.getBlock().is(BlockTags.STAIRS) || block.getBlock().is(BlockTags.WOODEN_STAIRS)|| block.getBlock() == Blocks.GRASS || block  instanceof DoublePlantBlock || block instanceof AbstractPlantBlock || block instanceof FlowerBlock || block.getBlock().is(BlockTags.LOGS) || block.getBlock().is(BlockTags.PLANKS) || block instanceof SlabBlock || block instanceof StairsBlock || block instanceof FenceBlock || block instanceof WallBlock) {
					flag = this.level.destroyBlock(blockpos, true, this) || flag;
				}
			}

			if (flag == false && this.onGround) {
				this.jumpFromGround();
			}
		}

		// When the pounder lands on something from above
		if (this.verticalCollision && ForgeEventFactory.getMobGriefingEvent(level, this)) {
			for(BlockPos blockpos : BlockPos.betweenClosed(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
				BlockState blockstate = this.level.getBlockState(blockpos);
				Block block = blockstate.getBlock();
				// Todo: pack all these up into a tag for the robo pounder to, well, you know, pound
				if (block instanceof LeavesBlock || block.getBlock() == Blocks.HAY_BLOCK || block.getBlock().is(BlockTags.TRAPDOORS) || block.getBlock().is(BlockTags.FENCES) || block.getBlock().is(BlockTags.FENCE_GATES) || block.getBlock().is(BlockTags.DOORS) || block.getBlock().is(BlockTags.WALLS) || block.getBlock().is(BlockTags.WOODEN_TRAPDOORS) || block.getBlock() instanceof MelonBlock || block.getBlock() instanceof PumpkinBlock || block.getBlock() instanceof BambooBlock || block.getBlock() == Blocks.SNOW || block.getBlock() == Blocks.SNOW_BLOCK ||block.getBlock().is(BlockTags.TALL_FLOWERS) || block.getBlock() == Blocks.GRASS || block  instanceof DoublePlantBlock || block.getBlock() instanceof TorchBlock || block.getBlock() == Blocks.GLASS_PANE || block instanceof AbstractPlantBlock || block instanceof FlowerBlock) {
					flag = this.level.destroyBlock(blockpos, true, this) || flag;
				}
			}
		}

	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "robopoundercontroller", 0, this::predicate));
	}

	@Override
	protected int calculateFallDamage(float f1, float f2) {
		return super.calculateFallDamage(f1, f2) / 2;
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getTarget() != null && this.canSee(this.getTarget()) && this.distanceToSqr(this.getTarget()) >= 12.0F) {
			this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
			this.getTarget().moveTo(this.getTarget().blockPosition(), 3.0F, 10.0F);
			if (this.navigation.isStuck()) {
				this.navigation.recomputePath(this.getTarget().blockPosition());
			}
		}

		if (this.getTarget() != null && this.canSee(this.getTarget()) && this.distanceToSqr(this.getTarget()) <= 12.0F) {
			this.lookControl.setLookAt(this.getTarget(), 30.0F, 30.0F);
			if (this.navigation.isStuck()) {
				this.navigation.recomputePath(this.getTarget().blockPosition());
			}
		}

	}

/*	public boolean canHitTarget(Entity target) {
		if (!this.isAggressive()) return false;
		if (!this.canSee(target)) return false;
		
		Vector3d attackerVector = new Vector3d(this.getX(), this.getEyeY(), this.getZ()); 
		Vector3d targetVector = new Vector3d(target.getX(), target.getEyeY(), target.getZ()); 
		
		if (target.level != this.level || targetVector.distanceToSqr(attackerVector) > 128.0D * 128.0D) return false; 
		
		return this.level.clip(new RayTraceContext(attackerVector, targetVector, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() == RayTraceResult.Type.MISS;
	}*/

	@Override
	public boolean canSee(Entity target) {
		if (target != null && this.distanceTo(target) <= getFollowRange()) return true;
		return super.canSee(target);
	}

	@Override
	public EntityType<?> getType() {
		return CAEntityTypes.ROBO_POUNDER.get();
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public double getFollowRange() {
		return this.getAttributeValue(Attributes.FOLLOW_RANGE);
	}

	public boolean isBreakableBlock(BlockPos pos) {
		boolean bool = false;
		bool = ForgeHooks.canEntityDestroy(level, pos, this) || bool;
		return bool;
	}

	@Override
	protected PathNavigator createNavigation(World world) {
		return new RoboPounderEntity.Navigator(this, world);
	}

	static class Navigator extends GroundPathNavigator {
		public Navigator(MobEntity entity, World world) {
			super(entity, world);
		}

		@Override
		protected PathFinder createPathFinder(int p) {
			this.nodeEvaluator = new RoboPounderEntity.Processor();
			return new PathFinder(this.nodeEvaluator, p);
		}
	}

	static class Processor extends WalkNodeProcessor {
		private Processor() {
		}

		@Override
		protected PathNodeType evaluateBlockPathType(IBlockReader reader, boolean door, boolean canPass, BlockPos pos, PathNodeType type) {
			return type == PathNodeType.BLOCKED ? PathNodeType.OPEN : super.evaluateBlockPathType(reader, door, canPass, pos, type);
		}
	}
}
