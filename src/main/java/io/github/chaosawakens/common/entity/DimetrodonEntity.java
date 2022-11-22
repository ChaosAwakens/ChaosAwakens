package io.github.chaosawakens.common.entity;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.entity.ai.AnimatableGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMovableGoal;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAGroundMovementController;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAStrictGroundPathNavigator;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DimetrodonEntity extends AnimatableAnimalEntity implements IAngerable {
	private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.defineId(DimetrodonEntity.class, DataSerializers.INT);
	private static final RangedInteger ANGER_TIME_RANGE = TickRangeConverter.rangeOfSeconds(60, 120);
	public static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(DimetrodonEntity.class, DataSerializers.INT);
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<IAnimatable>(this, "dimetrodoncontroller", animationInterval(), this::predicate);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.COD, Items.PUFFERFISH, Items.SALMON, Items.TROPICAL_FISH, CAItems.GREEN_FISH.get(), CAItems.SPARK_FISH.get());
	private UUID persistentAngerTarget;

	public DimetrodonEntity(EntityType<? extends AnimatableAnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
		this.moveControl = new DimetrodonMovementHelper(this);
	}

	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("DimetrodonType", this.getDimetrodonType());
		this.addPersistentAngerSaveData(nbt);
	}

	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		this.setDimetrodonType(nbt.getInt("DimetrodonType"));

		if (!level.isClientSide)
			this.readPersistentAngerSaveData((ServerWorld) this.level, nbt);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 18)
				.add(Attributes.ATTACK_DAMAGE, 3)
				.add(Attributes.ATTACK_KNOCKBACK, 1)
				.add(Attributes.ATTACK_SPEED, 1)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 20);
	}

	@Override
	public int animationInterval() {
		return 1;
	}
	
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving() && !this.isInWater()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimetrodon.walking_animation", true));
			return PlayState.CONTINUE;
		}
		if (this.getAttacking()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimetrodon.bite_animation", true));
			return PlayState.CONTINUE;
		}
		if (this.isSwimming() || this.isInWater()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimetrodon.swim_animation", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimetrodon.idle_animation", true));
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new LeapAtTargetGoal(this, 0.4F));
		this.targetSelector.addGoal(0, new MeleeAttackGoal(this, 1.0, true));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 1.0));
		this.goalSelector.addGoal(2, new SwimGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, false, FOOD_ITEMS));
		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
//		this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}

	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TYPE_ID, 0);
		this.entityData.define(ANGER_TIME, 0);
	}

	public int getDimetrodonType() {
		return MathHelper.clamp(this.entityData.get(DATA_TYPE_ID), 0, 4);
	}

	public void setDimetrodonType(int type) {
		this.entityData.set(DATA_TYPE_ID, type);
	}

	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
		int i = this.random.nextInt(4);
		if (entityData instanceof DimetrodonEntity.DimetrodonData) i = ((DimetrodonData) entityData).dimetrodonType;
		else entityData = new DimetrodonEntity.DimetrodonData(i);
		this.setDimetrodonType(i);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
	}

	public boolean hurt(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) return false;
		else {
			Entity entity = source.getEntity();
			if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) amount = (amount + 1.0F) / 2.0F;
			return super.hurt(source, amount);
		}
	}

	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(ANGER_TIME);
	}

	public void setRemainingPersistentAngerTime(int time) {
		this.entityData.set(ANGER_TIME, time);
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.randomValue(this.random));
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public void setPersistentAngerTarget(@Nullable UUID target) {
		this.persistentAngerTarget = target;
	}

	public boolean canBeLeashed(PlayerEntity player) {
		return !this.isAngry() && super.canBeLeashed(player);
	}
	
	@Override
	public boolean isPushedByFluid() {
		return true;
	}
	
	

	@OnlyIn(Dist.CLIENT)
	public Vector3d getLeashOffset() {
		return new Vector3d(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
	}

	public void tick() {
		super.tick();
	}
	
	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getAttacking()) {
			if (this.getTarget() != null) {
				assert this.getTarget() != null;
				this.lookAt(getTarget(), 100, 100);
				this.getLookControl().setLookAt(getTarget(), 30F, 30F);
				do {
					if (this.distanceTo(getTarget()) > AnimatableMeleeGoal.getAttackReachSq(this, getTarget())) this.getNavigation().moveTo(getNavigation().getPath(), 1);
				} while (this.isAngryAt(this.getTarget()));
				if (!this.isAngryAt(this.getTarget()) && !this.getTarget().isDeadOrDying()) {
					this.setTarget(this.getTarget());
					this.setRemainingPersistentAngerTime(1200);
				}
			}
		}
		
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "dimetrodoncontroller", 5, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	static class DimetrodonData extends AgeableEntity.AgeableData {
		public final int dimetrodonType;

		private DimetrodonData(int dimetrodonType) {
			super(true);
			this.dimetrodonType = dimetrodonType;
		}
	}

	@Override
	public DimetrodonEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
		DimetrodonEntity entity = CAEntityTypes.DIMETRODON.get().create(world);

		assert entity != null;
		entity.setDimetrodonType(((DimetrodonEntity) mate).getDimetrodonType());

		return entity;
	}
	
	static class DimetrodonMovementHelper extends MovementController {
		private final DimetrodonEntity dimetrodon;
		private float maxRotation = 90;

		public DimetrodonMovementHelper(DimetrodonEntity entity) {
			super(entity);
			this.dimetrodon = entity;
		}
		
		private void tickWater() {
			if (this.dimetrodon.isEyeInFluid(FluidTags.WATER)) this.dimetrodon.setDeltaMovement(this.dimetrodon.getDeltaMovement().add(0.0D, 0.005D, 0.0D));

			if (this.operation == MovementController.Action.MOVE_TO && !this.dimetrodon.getNavigation().isDone()) {
				float f = (float) (this.speedModifier * this.dimetrodon.getAttributeValue(Attributes.MOVEMENT_SPEED));
				this.dimetrodon.setSpeed(MathHelper.lerp(0.125F, this.dimetrodon.getSpeed(), f));
				double d0 = this.wantedX - this.dimetrodon.getX();
				double d1 = this.wantedY - this.dimetrodon.getY();
				double d2 = this.wantedZ - this.dimetrodon.getZ();
				if (d1 != 0.0D) {
					double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
					this.dimetrodon.setDeltaMovement(this.dimetrodon.getDeltaMovement().add(0.0D, (double) this.dimetrodon.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
				}
				if (d0 != 0.0D || d2 != 0.0D) {
					float f1 = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.dimetrodon.yRot = this.rotlerp(this.dimetrodon.yRot, f1, 90.0F);
					this.dimetrodon.yBodyRot = this.dimetrodon.yRot;
				}
			} else this.dimetrodon.setSpeed(0.0F);
		}
		
		private void tickWalking() {
			if (this.dimetrodon.getTarget() != null && this.dimetrodon.getNavigation().getTargetPos() == this.dimetrodon.getTarget().blockPosition()) {
				this.dimetrodon.lookAt(this.dimetrodon.getTarget(), 100, 100);
				this.dimetrodon.getLookControl().setLookAt(dimetrodon.getTarget(), 30F, 30F);
			}
	        if (this.operation == CAGroundMovementController.Action.STRAFE)
	        {
	            float f = (float)this.dimetrodon.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
	            float f1 = (float)this.speedModifier * f;
	            float f2 = this.strafeForwards;
	            float f3 = this.strafeRight;
	            float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);

	            if (f4 < 1.0F)
	            {
	                f4 = 1.0F;
	            }

	            f4 = f1 / f4;
	            f2 = f2 * f4;
	            f3 = f3 * f4;
	            float f5 = MathHelper.sin(this.dimetrodon.yRot * 0.017453292F);
	            float f6 = MathHelper.cos(this.dimetrodon.yRot * 0.017453292F);
	            float f7 = f2 * f6 - f3 * f5;
	            float f8 = f3 * f6 + f2 * f5;
	            PathNavigator pathnavigate = this.dimetrodon.getNavigation();

	            if (pathnavigate != null)
	            {
	                NodeProcessor nodeprocessor = pathnavigate.getNodeEvaluator();

	                if (nodeprocessor != null && nodeprocessor.getBlockPathType(this.dimetrodon.level, MathHelper.floor(this.dimetrodon.getX() + (double)f7), MathHelper.floor(this.dimetrodon.getY()), MathHelper.floor(this.dimetrodon.getZ() + (double)f8)) != PathNodeType.WALKABLE)
	                {
	                    this.strafeForwards = 1.0F;
	                    this.strafeRight = 0.0F;
	                    f1 = f;
	                }
	            }

	            this.dimetrodon.setSpeed(f1);
	            this.dimetrodon.setZza(this.strafeForwards);
	            this.dimetrodon.setXxa(this.strafeRight);
	            this.operation = CAGroundMovementController.Action.WAIT;
	        }
	        else if (this.operation == CAGroundMovementController.Action.MOVE_TO)
	        {
	            this.operation = CAGroundMovementController.Action.WAIT;
	            double d0 = this.wantedX - this.dimetrodon.getX();
	            double d1 = this.wantedZ - this.dimetrodon.getZ();
	            double d2 = this.wantedY - this.dimetrodon.getY();
	            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

	            if (d3 < 2.500000277905201E-7D)
	            {
	                this.dimetrodon.setZza(0.0F);
	                return;
	            }

	            float f9 = (float)(MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
	            this.dimetrodon.yRot = this.rotlerp(this.dimetrodon.yRot, f9, maxRotation);
	            this.dimetrodon.setSpeed((float)(this.speedModifier * this.dimetrodon.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));

	            if (d2 > (double)this.dimetrodon.maxUpStep && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.dimetrodon.getBbWidth()))
	            {
	                this.dimetrodon.getJumpControl().jump();
	                this.operation = CAGroundMovementController.Action.JUMPING;
	            }
	        }
	        else if (this.operation == CAGroundMovementController.Action.JUMPING)
	        {
	            this.dimetrodon.setSpeed((float)(this.speedModifier * this.dimetrodon.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));

	            if (this.dimetrodon.isOnGround()) {
	                this.operation = CAGroundMovementController.Action.WAIT;
	            }
	        }
	        else
	        {
	            this.dimetrodon.setZza(0.0F);
	        }
		}
		
		@Override
		public void tick() {
			if (dimetrodon.isInWater()) {
				tickWater();
			} else {
				tickWalking();
			}
		}
		
	}
	
	static class DimetrodonSwimGoal extends SwimGoal {
		private final DimetrodonEntity dimetrodon;

		public DimetrodonSwimGoal(DimetrodonEntity entity) {
			super(entity);
			this.dimetrodon = entity;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}
		
		@Override
		public boolean canUse() {
			return this.dimetrodon.isInWater();
		}
		
		@Override
		public boolean canContinueToUse() {
			return this.dimetrodon.isInWater();
		}
		
		@Override
		public void start() {
			dimetrodon.setSwimming(true);
		}
		
		@Override
		public void stop() {
			dimetrodon.setSwimming(false);
		}
		
		@Override
		public void tick() {
			if (this.dimetrodon.getFluidHeight(FluidTags.WATER) > this.dimetrodon.getFluidJumpThreshold()) {			      
				if (this.dimetrodon.getRandom().nextFloat() < 0.8F) {			       
					this.dimetrodon.getJumpControl().jump();		     
				}
			}
		}
	}
	
	static class AnimatableMeleeDimetrodonGoal extends AnimatableGoal {
		protected final double animationLength;
		protected final BiFunction<Double, Double, Boolean> attackPredicate;
		protected boolean hasHit;
		private final DimetrodonEntity dimetrodon;

		public AnimatableMeleeDimetrodonGoal(DimetrodonEntity dimetrodon, double animationLength, double attackBegin, double attackEnd) {
			this.dimetrodon = dimetrodon;
			this.animationLength = animationLength;
			this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		private static boolean checkIfValid(AnimatableMeleeDimetrodonGoal goal, AnimatableAnimalEntity attacker, LivingEntity target) {
			if (target == null) return false;
			if (target.isAlive() && !target.isSpectator()) {
				if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
					attacker.setAttacking(false);
					return false;
				}
				double distance = goal.dimetrodon.distanceToSqr(target.getX(), target.getY(), target.getZ());
				if (distance <= AnimatableGoal.getAttackReachSq(attacker, target)) return true;
			}
			attacker.setAttacking(false);
			return false;
		}

		@Override
		public boolean canUse() {
			if (Math.random() <= 0.1) return false;
			return AnimatableMeleeDimetrodonGoal.checkIfValid(this, dimetrodon, this.dimetrodon.getTarget());
		}

		@Override
		public boolean canContinueToUse() {
			if (Math.random() <= 0.1) return true;
			return AnimatableMeleeDimetrodonGoal.checkIfValid(this, dimetrodon, this.dimetrodon.getTarget());
		}

		@Override
		public void start() {
			this.dimetrodon.setAttacking(true);
			this.dimetrodon.setAggressive(true);
			this.animationProgress = 0;
		}

		@Override
		public void stop() {
			LivingEntity target = this.dimetrodon.getTarget();
			if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) this.dimetrodon.setTarget(null);
			this.dimetrodon.setAttacking(false);
			this.dimetrodon.setAggressive(false);
			this.hasHit = false;
			this.animationProgress = 0;
		}

		public void animateAttack(AnimatableAnimalEntity attacker) {
			if (attacker.getTarget() == null) return;
			
			double reach = AnimatableGoal.getAttackReachSq(attacker, attacker.getTarget());
			double reachSqr = reach * reach;
			
			LivingEntity target = attacker.getTarget();
			
			List<LivingEntity> targets = IUtilityHelper.getAllEntitiesAround(attacker, reach, reach, reach, 12.0D);
			
			attacker.lookAt(target, 100, 100);
			attacker.getLookControl().setLookAt(target, 30F, 30F);
			
			float targetAngle = (float) ((Math.atan2(target.getZ() - attacker.getZ(), target.getX() - attacker.getX()) * (180 / Math.PI) - 90) % 360);
			float attackAngle = attacker.yRot % 360;
			
			if (targetAngle < 0) targetAngle += 360;
			if (attackAngle < 0) attackAngle += 360;
			
			float relativeHitAngle = targetAngle - attackAngle;
			float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - attacker.getZ()) * (target.getZ() - attacker.getZ()) + (target.getX() - attacker.getX()) * (target.getX() - attacker.getX())) - attacker.getBbWidth() / 2F);
			
			if (hitDistanceSqr <= reach && (relativeHitAngle <= 10F / 2 && relativeHitAngle >= -10F / 2) || (relativeHitAngle >= 360 - 10F / 2 || relativeHitAngle <= -360 + 10F / 2)) {
				attacker.doHurtTarget(target);
				this.hasHit = true;
			}
		}
		
		@Override
		public void tick() {
			this.baseTick();
			LivingEntity target = this.dimetrodon.getTarget();
			
			if (target != null) {								 
				if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
					animateAttack(this.dimetrodon);
				}

				if (this.animationProgress >= this.animationLength) {
					this.animationProgress = 0;
					this.hasHit = false;
				}
			}
		}
		
	}
	
	
	
	static class AnimatableDimetrodonMoveToTargetGoal extends AnimatableMovableGoal {
		private final double speedMultiplier;
		private final int checkRate;
		private int pathCheckRate;
		private final DimetrodonEntity dimetrodon;

		/**
		 * Move a Dimetrodon to a target entity
		 *
		 * @param dimetrodon          Dimetrodon instance
		 * @param speedMultiplier Entity will move by base speed * this
		 * @param checkRate       Check rate with formula:
		 *                        {@code if(RANDOM.nextInt(rate) == 0)}, so bigger =
		 *                        less often
		 */
		public AnimatableDimetrodonMoveToTargetGoal(DimetrodonEntity dimetrodon, double speedMultiplier, int checkRate) {
			this.dimetrodon = dimetrodon;
			this.speedMultiplier = speedMultiplier;
			this.checkRate = checkRate;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}
		
		protected boolean isExecutable(AnimatableMovableGoal goal, AnimatableAnimalEntity attacker, LivingEntity target) {
			if (target == null) return false;
			if (target.isAlive() && !target.isSpectator()) {
				if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) return false;
				double distance = dimetrodon.distanceToSqr(target.getX(), target.getY(), target.getZ());
				path = attacker.getNavigation().createPath(target, 0);
				return attacker.getSensing().canSee(target) && distance >= AnimatableGoal.getAttackReachSq(attacker, target) && path != null;
			}
			return false;
		}

		@Override
		public boolean canUse() {
	//		if (RANDOM.nextInt(this.checkRate) == 0) return false;
			return this.isExecutable(this, this.dimetrodon, this.dimetrodon.getTarget());
		}

		@Override
		public boolean canContinueToUse() {
//			if (RANDOM.nextInt(this.checkRate) == 0) return true;
			if (this.dimetrodon.getTarget() == null) return false;
			return this.isExecutable(this, this.dimetrodon, this.dimetrodon.getTarget()) && this.dimetrodon.isWithinRestriction(this.dimetrodon.getTarget().blockPosition());
		}

		@Override
		public void start() {
			pathCheckRate = 0;
			this.dimetrodon.setAggressive(true);
			this.dimetrodon.setMoving(true);
			this.dimetrodon.lookAt(this.dimetrodon.getTarget(), 100, 100);
			this.dimetrodon.getLookControl().setLookAt(this.dimetrodon.getTarget(), 30F, 30F);
			this.dimetrodon.getNavigation().moveTo(this.path, this.speedMultiplier);
		}
		
		@Override
		public void stop() {
			LivingEntity target = this.dimetrodon.getTarget();
			if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) this.dimetrodon.setTarget(null);
			this.dimetrodon.setAggressive(false);
			this.dimetrodon.setMoving(false);
			this.dimetrodon.getNavigation().stop();
		}

		@SuppressWarnings("unused")
		@Override
		public void tick() {
			LivingEntity target = this.dimetrodon.getTarget();
			if (target == null || !EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target) || !target.isAlive()) return;
			if (pathCheckRate > 0) pathCheckRate--;
			
			this.dimetrodon.lookAt(target, 100, 100);
			this.dimetrodon.getLookControl().setLookAt(target, 30F, 30F);
			
			if (pathCheckRate <= 0 && this.dimetrodon.getSensing().canSee(target) && this.dimetrodon.distanceToSqr(target) >= AnimatableGoal.getAttackReachSq(this.dimetrodon, target) - 1) {
				Vector3d targetPosition = target.position();
				pathCheckRate = IUtilityHelper.randomBetween(4, 11);
				this.dimetrodon.getNavigation().moveTo(path, this.speedMultiplier);
				
				if (this.dimetrodon.distanceToSqr(target.getX(), target.getY(), target.getZ()) > 256) {
					pathCheckRate += 5;
					if (this.dimetrodon.distanceToSqr(target.getX(), target.getY(), target.getZ()) > 1024) {
						pathCheckRate += 10;
					}
				}
				
				if (!this.dimetrodon.getNavigation().moveTo(target, this.speedMultiplier)) pathCheckRate += 15;
			}
		}
	}
	
	@Override
	protected PathNavigator createNavigation(World world) {
		return new CAStrictGroundPathNavigator(this, world);
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}
}
