package io.github.chaosawakens.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
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
import net.minecraft.util.math.vector.Vector3d;
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

import java.util.Objects;
import java.util.Optional;

public class WhaleEntity extends WaterMobEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	protected MovementController.Action operation = MovementController.Action.WAIT;
	protected double wantedX;
	protected double wantedY;
	protected double wantedZ;
	protected double speedModifier;
	int sunburn;

	public WhaleEntity(EntityType<? extends WaterMobEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
		this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
		this.moveControl = new MoveHelperController(this);
		this.lookControl = new DolphinLookController(this, 10);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 80)
				.add(Attributes.MOVEMENT_SPEED, 0.5D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.3D)
				.add(Attributes.FOLLOW_RANGE, 18);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.swimming", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.swimming", true));
			return PlayState.CONTINUE;
		}
		if (this.dead) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.death", true));
		}
		if (this.isSwimming()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.swimming", true));
		}
		return PlayState.CONTINUE;
	}

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }
    
	@Override
	public void aiStep() {
		if (!this.isInWater() && this.onGround && this.verticalCollision) {
			this.onGround = false;
			this.hasImpulse = false;
		}
		
		if (this.isInWater()) {
			this.hasImpulse = false;
		}
		super.aiStep();
	}

	static class MoveHelperController extends MovementController {
		private final WhaleEntity whale;

		MoveHelperController(WhaleEntity whale) {
			super(whale);
			this.whale = whale;
		}

		@Override
		public void tick() {
			if (this.whale.isEyeInFluid(FluidTags.WATER)) this.whale.setDeltaMovement(this.whale.getDeltaMovement().add(0.0D, 0.005D, 0.0D));

			if (this.operation == MovementController.Action.MOVE_TO && !this.whale.getNavigation().isDone()) {
				double d0 = this.wantedX - this.whale.getX();
				double d1 = this.wantedY - this.whale.getY();
				double d2 = this.wantedZ - this.whale.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				if (d3 < (double) 2.5000003E-7F) this.mob.setZza(0.0F);
				else {
					float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
					this.whale.yRot = this.rotlerp(this.whale.yRot, f, 10.0F);
					this.whale.yBodyRot = this.whale.yRot;
					this.whale.yHeadRot = this.whale.yRot;
					float f1 = (float) (this.speedModifier * this.whale.getAttributeValue(Attributes.MOVEMENT_SPEED));
					if (this.whale.isInWater()) {
						this.whale.setSpeed(f1 * 0.02F);
						float f2 = -((float) (MathHelper.atan2(d1, MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double) (180F / (float) Math.PI)));
						f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
						this.whale.xRot = this.rotlerp(this.whale.xRot, f2, 5.0F);
						float f3 = MathHelper.cos(this.whale.xRot * ((float) Math.PI / 180F));
						float f4 = MathHelper.sin(this.whale.xRot * ((float) Math.PI / 180F));
						this.whale.zza = f3 * f1;
						this.whale.yya = -f4 * f1;
					} else this.whale.setSpeed(f1 * 0.1F);

				}
			} else {
				this.whale.setSpeed(0.0F);
				this.whale.setXxa(0.0F);
				this.whale.setYya(0.0F);
				this.whale.setZza(0.0F);
			}
		}

	}

	static class SwimGoal extends RandomSwimmingGoal {
		public final WhaleEntity whale;

		public SwimGoal(WhaleEntity whale) {
			super(whale, 1.0D, 30);
			this.whale = whale;
		}

		public boolean canUse() {
			return super.canUse();
		}
	}

	public void travel(Vector3d vector, World world) {
		if (this.isEffectiveAi() && this.isInWater()) {
			this.moveRelative(this.getSpeed(), vector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
			if (this.getTarget() == null) this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
		} else super.travel(vector);
	}

	@Override
	public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
		return super.checkSpawnRules(p_213380_1_, p_213380_2_) && WhaleEntity.checkWhaleSpawnRules(level, blockPosition());
	}

	public static boolean checkWhaleSpawnRules(IWorld world, BlockPos pos) {
		if (pos.getY() > 45 && pos.getY() < world.getSeaLevel()) {
			Optional<RegistryKey<Biome>> optional = world.getBiomeName(pos);
			return (Objects.equals(optional, Optional.of(Biomes.OCEAN)) || !Objects.equals(optional, Optional.of(Biomes.DEEP_OCEAN))) && world.getFluidState(pos).is(FluidTags.WATER);
		} else return false;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 2.0D, 5));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(8, new FollowBoatGoal(this));
		this.goalSelector.addGoal(4, new FindWaterGoal(this));
		this.goalSelector.addGoal(4, new BreatheAirGoal(this));
		this.goalSelector.addGoal(4, new PanicGoal(this, 2.0D));
	}

	public boolean canStandOnFluid(Fluid water, BlockPos pos) {
		return !this.level.getBlockState(pos.below()).is(Blocks.AIR);
	}

	private boolean clearanceAcquired() {
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
		for (int l1 = 0; l1 < 10; ++l1) {
			BlockState blockstate = level.getBlockState(blockpos$mutable.set(this.getX(), this.getY() + l1, this.getZ()));
			if (!blockstate.getFluidState().is(FluidTags.WATER)) return false;
		}

		return true;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void setAirSupply(int air) {
		air = 900000000;
		super.setAirSupply(air);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.onGround) {
			this.setDeltaMovement(this.getDeltaMovement().add(((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5D, ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)));
			this.yRot = this.random.nextFloat() * 360.0F;
			this.onGround = false;
			this.hasImpulse = true;

			if (!this.isInWaterRainOrBubble()) {
				if (this.level.isDay() && !this.level.isClientSide()) {
					this.sunburn++;
					if (this.sunburn >= 100) { // Time/2, so it'd take 50 seconds for this to take effect
						this.hurt(DamageSource.DRY_OUT, random.nextInt(2) == 0 ? 1F : 0F);
					}
				} else this.sunburn = 0;
			}
		}
	}

	@Override
	public boolean canBeLeashed(PlayerEntity player) {
		return false;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "whalecontroller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public CreatureAttribute getMobType() {
		return CreatureAttribute.WATER;
	}

	public float getSoundVolume() {
		return 16.0F;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.DOLPHIN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.DOLPHIN_DEATH;
	}

	@Override
	public int getMaxAirSupply() {
		return 900000;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.DOLPHIN_AMBIENT_WATER;
	}

	@Override
	protected void tickDeath() {
		super.tickDeath();
		if (this.dead) {

		}
	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.95F : 1.3F; // Just, don't even ask
	}
}
