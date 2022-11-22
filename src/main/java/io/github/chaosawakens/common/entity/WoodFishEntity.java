package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
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

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class WoodFishEntity extends AbstractGroupFishEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);

	public WoodFishEntity(EntityType<? extends AbstractGroupFishEntity> type, World world) {
		super(type, world);
		this.noCulling = true;
		this.setPathfindingMalus(PathNodeType.WATER, 1.0F);
		this.moveControl = new WoodFishEntity.MoveHelperController(this);
	}

	@Override
	protected boolean canRandomSwim() {
		return true;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 8)
				.add(Attributes.MOVEMENT_SPEED, 0.7D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.0D)
				.add(Attributes.FOLLOW_RANGE, 20);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "woodfishcontroller", 0, this::predicate));
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.swim", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.swim", true));
			return PlayState.CONTINUE;
		}
		if (this.dead) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.flop", true));
		}
		if (this.isSwimming()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.swim", true));
		}
		if (this.isDeadOrDying()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wood_fish.flop", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.05D));
		this.goalSelector.addGoal(0, new LookAtGoal(this, PlayerEntity.class, 3.0F, 3.0F));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, PlayerEntity.class, 4.0F, 0.8D, 0.7D, EntityPredicates.NO_SPECTATORS::test));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, RockFishEntity.class, 12.0F, 1.3D, 1.5D, EntityPredicates.NO_SPECTATORS::test));
		this.goalSelector.addGoal(1, new WoodFishEntity.SwimGoal(this));
		this.goalSelector.addGoal(2, new WoodFishEntity.GoToWaterGoal(this, 1.6D));
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

	public float getSoundVolume() {
		return 0.9F;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.TROPICAL_FISH_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.TROPICAL_FISH_DEATH;
	}

	@Override
	public int getMaxAirSupply() {
		return 450;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.TROPICAL_FISH_AMBIENT;
	}

	static class MoveHelperController extends MovementController {
		private final WoodFishEntity fish;

		MoveHelperController(WoodFishEntity woodFish) {
			super(woodFish);
			this.fish = woodFish;
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
		private static WoodFishEntity fish;

		public SwimGoal(WoodFishEntity woodFish) {
			super(woodFish, 1.0D, 40);
			SwimGoal.fish = woodFish;
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

	static class GoToWaterGoal extends Goal {
		private final CreatureEntity entity;
		private double wantedX;
		private double wantedY;
		private double wantedZ;
		private final double speedModifier;
		private final World level;

		public GoToWaterGoal(CreatureEntity entity, double speedModifier) {
			this.entity = entity;
			this.speedModifier = speedModifier;
			this.level = entity.level;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			if (!this.level.isDay()) return false;
			else if (this.entity.isInWater()) return false;
			else {
				Vector3d vector3d = this.getWaterPos();
				if (vector3d == null) return false;
				else {
					this.wantedX = vector3d.x;
					this.wantedY = vector3d.y;
					this.wantedZ = vector3d.z;
					return true;
				}
			}
		}

		public boolean canContinueToUse() {
			return !this.entity.getNavigation().isDone();
		}

		public void start() {
			this.entity.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
		}

		@Nullable
		private Vector3d getWaterPos() {
			Random random = this.entity.getRandom();
			BlockPos blockpos = this.entity.blockPosition();
			for (int i = 0; i < 10; ++i) {
				BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
				if (this.level.getBlockState(blockpos1).is(Blocks.WATER)) return Vector3d.atBottomCenterOf(blockpos1);
			}
			return null;
		}
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected ItemStack getBucketItemStack() {
		return new ItemStack(CAItems.WOOD_FISH_BUCKET.get());
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntitySize size) {
		return this.isBaby() ? size.height * 0.45F : 0.6F;
	}
}
