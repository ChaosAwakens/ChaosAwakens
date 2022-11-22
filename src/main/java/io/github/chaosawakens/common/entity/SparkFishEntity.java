package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SparkFishEntity extends AbstractFishEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);

	public SparkFishEntity(EntityType<? extends AbstractFishEntity> type, World world) {
		super(type, world);
		this.noCulling = true;
		this.setPathfindingMalus(PathNodeType.WATER, 1.0F);
		this.moveControl = new SparkFishEntity.MoveHelperController(this);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 8.0D)
				.add(Attributes.MOVEMENT_SPEED, 1.3D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
				.add(Attributes.FOLLOW_RANGE, 4.0D);
	}

	@Override
	public int getMaxAirSupply() {
		return 300;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "sparkfishcontroller", 0, this::predicate));
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.05D));
		this.goalSelector.addGoal(0, new LookAtGoal(this, PlayerEntity.class, 3.0F, 3.0F));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 4.0F, 0.8D, 0.7D, EntityPredicates.NO_SPECTATORS::test));
		this.goalSelector.addGoal(4, new SparkFishEntity.SwimGoal(this));
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.spark_fish.swim", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.spark_fish.swim", true));
			this.animationSpeed = 0.5F;
			return PlayState.CONTINUE;
		}
		if (this.dead) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.spark_fish.flop", true));
		}
		if (this.isSwimming()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.spark_fish.swim", true));
		}
		if (this.isDeadOrDying()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.spark_fish.flop", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	static class MoveHelperController extends MovementController {
		private final SparkFishEntity fish;

		MoveHelperController(SparkFishEntity entity) {
			super(entity);
			this.fish = entity;
		}

		public void tick() {
			if (this.fish.isEyeInFluid(FluidTags.WATER)) this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));

			if (this.operation == MovementController.Action.MOVE_TO && !this.fish.getNavigation().isDone()) {
				float f = (float) (this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
				this.fish.setSpeed(MathHelper.lerp(0.125F, this.fish.getSpeed(), f));
				double d0 = this.wantedX - this.fish.getX();
				double d1 = this.wantedY - this.fish.getY();
				double d2 = this.wantedZ - this.fish.getZ();
				if (d1 != 0.0D) {
					double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
					this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, (double) this.fish.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
				}
				if (d0 != 0.0D || d2 != 0.0D) {
					float f1 = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.fish.yRot = this.rotlerp(this.fish.yRot, f1, 90.0F);
					this.fish.yBodyRot = this.fish.yRot;
				}
			} else this.fish.setSpeed(0.0F);
		}
	}

	static class SwimGoal extends RandomSwimmingGoal {
		private static SparkFishEntity fish;

		public SwimGoal(SparkFishEntity entity) {
			super(entity, 1.0D, 40);
			SwimGoal.fish = entity;
		}

		public boolean canUse() {
			return SwimGoal.fish.canRandomSwim() && super.canUse();
		}
	}

	@Override
	public void aiStep() {
		if (!this.isInWater() && this.onGround && this.verticalCollision) {
			this.setDeltaMovement(this.getDeltaMovement().add(((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4F, ((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
			this.onGround = false;
			this.hasImpulse = true;
			this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
		}

		super.aiStep();
	}

	@Override
	public EntityType<?> getType() {
		return CAEntityTypes.SPARK_FISH.get();
	}

	@Override
	protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
			this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
			itemstack.shrink(1);
			ItemStack itemstack1 = this.getBucketItemStack();
			this.saveToBucketTag(itemstack1);
			if (!this.level.isClientSide) CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) player, itemstack1);

			if (itemstack.isEmpty()) player.setItemInHand(hand, itemstack1);
			else if (!player.inventory.add(itemstack1)) player.drop(itemstack1, false);

			this.remove();
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		} else return super.mobInteract(player, hand);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 6;
	}

	@Override
	protected PathNavigator createNavigation(World world) {
		return new SwimmerPathNavigator(this, world);
	}

	@Override
	public void travel(Vector3d vector) {
		if (this.isEffectiveAi() && this.isInWater()) {
			this.moveRelative(0.01F, vector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
			if (this.getTarget() == null) this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
		} else super.travel(vector);
	}

	@Override
	protected ItemStack getBucketItemStack() {
		return new ItemStack(CAItems.SPARK_FISH_BUCKET.get());
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.COD_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.COD_DEATH;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntitySize size) {
		return this.isBaby() ? size.height * 0.45F : 0.6F;
	}
}
