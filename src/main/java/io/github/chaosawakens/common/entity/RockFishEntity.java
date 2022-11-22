package io.github.chaosawakens.common.entity;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RockFishEntity extends AbstractGroupFishEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);

	public RockFishEntity(EntityType<? extends RockFishEntity> entityType, World world) {
		super(entityType, world);
		this.noCulling = true;
		this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
		this.moveControl = new RockFishEntity.MoveHelperController(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new RockFishEntity.SwimGoal(this));
		this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, WoodFishEntity.class, true));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5D, false));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 12.0D)
				.add(Attributes.MOVEMENT_SPEED, 1.2D)
				.add(Attributes.ATTACK_SPEED, 0.25D)
				.add(Attributes.ATTACK_DAMAGE, 1.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 0)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
				.add(Attributes.FOLLOW_RANGE, 4.0D);
	}

	protected ItemStack getBucketItemStack() {
		return new ItemStack(CAItems.ROCK_FISH_BUCKET.get());
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.COD_AMBIENT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.COD_DEATH;
	}

	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.COD_HURT;
	}

	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rock_fish.swim_animation", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rock_fish.swim_animation", true));
			return PlayState.CONTINUE;
		}
		if (this.isSwimming()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.rock_fish.swim_animation", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected PathNavigator createNavigation(World worldIn) {
		return new SwimmerPathNavigator(this, worldIn);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "rockfishcontroller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public void aiStep() {
		if (!this.isInWater() && this.onGround && this.verticalCollision) {
			this.onGround = false;
			this.hasImpulse = true;
		}
		super.aiStep();
	}

	public static boolean checkRockFishSpawnRules(EntityType<RockFishEntity> rockfish, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
		if (pos.getY() > 25 && pos.getY() < 35) {
			Optional<RegistryKey<Biome>> optional = world.getBiomeName(pos);
			return (Objects.equals(optional, Optional.of(Biomes.OCEAN)) || Objects.equals(optional, Optional.of(Biomes.DEEP_OCEAN))) && world.getFluidState(pos).is(FluidTags.WATER);
		} else return false;
	}

	static class MoveHelperController extends MovementController {
		private final RockFishEntity rockfish;

		MoveHelperController(RockFishEntity rockfishEntity) {
			super(rockfishEntity);
			this.rockfish = rockfishEntity;
		}

		public void tick() {
			if (this.rockfish.isEyeInFluid(FluidTags.WATER)) this.rockfish.setDeltaMovement(this.rockfish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));

			if (this.operation == MovementController.Action.MOVE_TO && !this.rockfish.getNavigation().isDone()) {
				float f = (float) (this.speedModifier * this.rockfish.getAttributeValue(Attributes.MOVEMENT_SPEED));
				this.rockfish.setSpeed(MathHelper.lerp(0.125F, this.rockfish.getSpeed(), f));
				double d0 = this.wantedX - this.rockfish.getX();
				double d1 = this.wantedY - this.rockfish.getY();
				double d2 = this.wantedZ - this.rockfish.getZ();
				if (d1 != 0.0D) {
					double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
					this.rockfish.setDeltaMovement(this.rockfish.getDeltaMovement().add(0.0D, (double) this.rockfish.getSpeed() * (d1 / d3) * 0.1D, 0.0D));
				}
				if (d0 != 0.0D || d2 != 0.0D) {
					float f1 = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.rockfish.yRot = this.rotlerp(this.rockfish.yRot, f1, 90.0F);
					this.rockfish.yBodyRot = this.rockfish.yRot;
				}
			} else this.rockfish.setSpeed(0.0F);
		}
	}

	static class SwimGoal extends RandomSwimmingGoal {
		private final RockFishEntity rockfish;

		public SwimGoal(RockFishEntity rockfishEntity) {
			super(rockfishEntity, 1.0D, 40);
			this.rockfish = rockfishEntity;
		}

		public boolean canUse() {
			return this.rockfish.canRandomSwim() && super.canUse();
		}
	}
}
