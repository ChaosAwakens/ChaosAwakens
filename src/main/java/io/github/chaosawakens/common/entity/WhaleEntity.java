package io.github.chaosawakens.common.entity;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.IAnimatableEntity;
import io.github.chaosawakens.common.registry.CADamageSources;
import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.FollowBoatGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WhaleEntity extends WaterMobEntity implements IAnimatableEntity, IAnimationTickable {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<>(this, "whalecontroller", animationInterval(), this::predicate);
	protected MovementController.Action operation = MovementController.Action.WAIT;
	private static final DataParameter<Integer> AIR = EntityDataManager.defineId(WhaleEntity.class, DataSerializers.INT);
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
		this.lookControl = new DolphinLookController(this, 1);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 120)
				.add(Attributes.MOVEMENT_SPEED, 0.5D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
				.add(Attributes.FOLLOW_RANGE, 18);
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.swimming", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.idle", true));
			return PlayState.CONTINUE;
		}
//		if (this.dead || this.getHealth() <= 0) {
//			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.death", true));
//			return PlayState.CONTINUE;
//		}
		if (this.isSwimming()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.swimming", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}
	
	@Override
	public int animationInterval() {
		return 5;
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.entityData.define(AIR, 3000);
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
					} else this.whale.setSpeed(0);
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
		return super.checkSpawnRules(p_213380_1_, p_213380_2_) && checkWhaleSpawnRules(level, blockPosition());
	}

	@SuppressWarnings("deprecation")
	public static boolean checkWhaleSpawnRules(IWorld world, BlockPos pos) {
		if (pos.getY() > 25 && pos.getY() < world.getSeaLevel()) {
			Optional<RegistryKey<Biome>> optional = world.getBiomeName(pos);
			return (Objects.equals(optional, Optional.of(Biomes.OCEAN)) || !Objects.equals(optional, Optional.of(Biomes.DEEP_OCEAN))) && world.getFluidState(pos).is(FluidTags.WATER);
		} else return false;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1.0D, 3));
		this.goalSelector.addGoal(0, new WhaleBreatheGoal(this));
//		this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(1, new FollowBoatGoal(this));
		this.goalSelector.addGoal(2, new FindWaterGoal(this));
	}

	public boolean canStandOnFluid(Fluid water, BlockPos pos) {
		return !this.level.getBlockState(pos.below()).is(Blocks.AIR);
	}

	@SuppressWarnings("unused")
	private boolean clearanceAcquired() {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (int l1 = 0; l1 < 10; ++l1) {
			BlockState blockstate = level.getBlockState(mutable.set(this.getX(), this.getY() + l1, this.getZ()));
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
		return false;
	}
	
	@Override
	public int getAirSupply() {
		return this.entityData.get(AIR);
	}

	@Override
	public void setAirSupply(int air) {
		this.entityData.set(AIR, air);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.onGround) {
			this.setDeltaMovement(Vector3d.ZERO);

			if (!this.isInWaterRainOrBubble()) {
				if (this.level.isDay() && !this.level.isClientSide()) {
					this.sunburn++;
					if (this.sunburn >= 100 && this.isSunBurnTick()) {
						this.hurt(CADamageSources.SUNBURN, random.nextInt(2) == 0 ? 1F : 0F);
					}
				} else this.sunburn-=0;
			}
		}
		
		 ChaosAwakens.LOGGER.debug(getAirSupply());
	}
	
	@Override
	protected void handleAirSupply(int air) {
		if (this.isAlive() && (!this.isInWater() || this.fluidOnEyes == null)) {
			air++;
			
			if (air >= getMaxAirSupply()) {
				air = getMaxAirSupply();
			}
		}
		
		if (this.isAlive() && this.isInWater() && this.fluidOnEyes != null) {
			air--;
			
			if (air <= 0) {
				air = 0;
				
				this.hurt(DamageSource.DROWN, 2);
			}
		}
		
		setAirSupply(air);
	}

	@Override
	public boolean canBeLeashed(PlayerEntity player) {
		return false;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return CASoundEvents.WHALE_AMBIENT.get();
	}
	
	@Override
	public int getAmbientSoundInterval() {
		return random.nextInt(200);
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return CASoundEvents.WHALE_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.WHALE_DEATH.get();
	}
	
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public CreatureAttribute getMobType() {
		return CreatureAttribute.WATER;
	}

	public float getSoundVolume() {
		return 2.0F;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}

	@Override
	public int getMaxAirSupply() {
		return 10000;
	}
	
	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@SuppressWarnings("unused")
	@Override
	protected void tickDeath() {
		++deathTime;
		int maxXRot = this.getMaxHeadXRot();
		int maxYRot = this.getMaxHeadYRot();
		maxXRot = 1;
		maxYRot = 1;
		this.setDeltaMovement(new Vector3d(0, this.getDeltaMovement().y > 0 ? -this.getDeltaMovement().y : this.getDeltaMovement().y, 0));
		if (deathTime == 120) {
			boolean explode = random.nextBoolean();
			
			//Extra check
			if (deathTime == 120 && explode) {
				if (!level.isClientSide) this.level.explode(this, this.getX(), this.getY(), this.getZ(), 24.0F, false, Explosion.Mode.DESTROY);
				this.remove();
			} else {
				this.remove();
			}
			
		}
	}
	
	@Override
	public int getHeadRotSpeed() {
		return 6;
	}
	
	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.95F : 1.8F; 
	}
	
    static class WhaleBreatheGoal extends Goal {
    	private final WhaleEntity whale;

        public WhaleBreatheGoal(WhaleEntity whale) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.whale = whale;
        }
        
        @Override
        public void start() {
        	findPos();
        }
        
        @Override
        public void stop() {
        	whale.getNavigation().stop();
        	whale.getNavigation().recomputePath();
        }

        @Override
        public boolean canUse() {
            return whale.getAirSupply() < 500;
        }

        @Override
        public boolean canContinueToUse() {
            return whale.getAirSupply() < whale.getMaxAirSupply() / whale.random.nextInt(4);
        }

        @SuppressWarnings("rawtypes")
		private void findPos() {
            Iterable<BlockPos> whaleBox = BlockPos.betweenClosed(MathHelper.floor(whale.getX() - 1.0D), MathHelper.floor(whale.getY()), MathHelper.floor(whale.getZ() - 1.0D), MathHelper.floor(whale.getX() + 1.0D), MathHelper.floor(whale.getY() + 8.0D), MathHelper.floor(whale.getZ() + 1.0D));
            BlockPos destination = null;
            Iterator whaleBoxIterator = whaleBox.iterator();

            while (whaleBoxIterator.hasNext()) {
                BlockPos nextPosInWhaleBox = (BlockPos) whaleBoxIterator.next();
                if (this.canBreatheAt(whale.level, nextPosInWhaleBox)) {
                    destination = nextPosInWhaleBox.above((int) (whale.getBbHeight() * 0.25D));
                    break;
                }
            }

            if (destination == null) {
                destination = new BlockPos(whale.getX(), whale.getY() + 4.0D, whale.getZ());
            }
            
            //Continue to go up until the whale is at the surface
            if (whale.isEyeInFluid(FluidTags.WATER)) {
            	whale.setDeltaMovement(whale.getDeltaMovement().add(0, 0.03F, 0));
            }

            whale.getNavigation().moveTo(destination.getX(), destination.getY(), destination.getZ(), 0.7D);
        }

        private boolean canBreatheAt(IWorldReader world, BlockPos pos) {
            BlockState targetState = world.getBlockState(pos);
            return targetState.isPathfindable(world, pos, PathType.LAND) && (world.getFluidState(pos).isEmpty() || targetState.is(Blocks.BUBBLE_COLUMN));
        }
        
        @Override
        public void tick() {
            findPos();
        }
    }
    
    static class WhaleBodyController extends BodyController {

		public WhaleBodyController(WhaleEntity whale) {
			super(whale);
		}
		
		
    	
    }

	@Override
	public int tickTimer() {
		return tickCount;
	}
}
