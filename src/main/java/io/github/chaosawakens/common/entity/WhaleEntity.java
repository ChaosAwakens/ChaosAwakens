package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.goals.AnimalAISwimBottom;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.BreatheAirGoal;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.FollowBoatGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
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

import java.util.Objects;

public class WhaleEntity extends WaterMobEntity implements IAnimatable{
	private final AnimationFactory factory = new AnimationFactory(this);
	 protected MovementController.Action operation = MovementController.Action.WAIT;
	   protected double wantedX;
	   protected double wantedY;
	   protected double wantedZ;
	   protected double speedModifier;
	
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
                .add(Attributes.MOVEMENT_SPEED, 1.2D)
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
        if(this.dead) {
        	event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.death", true));
        }
        if(this.isSwimming()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.whale.swimming", true));
        }
        return PlayState.CONTINUE;
    }
    
    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }
  
    static class MoveHelperController extends MovementController {
        private final WhaleEntity whale;

        MoveHelperController(WhaleEntity whale) {
            super(whale);
            this.whale = whale;
        }

        @Override
        public void tick() {
            if (this.whale.isEyeInFluid(FluidTags.WATER)) {
                this.whale.setDeltaMovement(this.whale.getDeltaMovement().add(0.0D, 0.05D, 0.0D));
            }     

            if (this.operation == MovementController.Action.MOVE_TO && !this.whale.getNavigation().isDone()) {
                double d0 = this.wantedX - this.whale.getX();
                double d1 = this.wantedY - this.whale.getY();
                double d2 = this.wantedZ - this.whale.getZ();
                double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.whale.yRot = this.rotlerp(this.whale.yRot, f, 90.0F);
                this.whale.yBodyRot = this.whale.yRot;
                float f1 = (float) (this.speedModifier * Objects.requireNonNull(this.whale.getAttribute(Attributes.MOVEMENT_SPEED)).getValue());
                this.whale.setSpeed(MathHelper.lerp(0.9F, this.whale.getSpeed(), f1));
                this.whale.setDeltaMovement(this.whale.getDeltaMovement().add(0.0D, (double)this.whale.getSpeed() * d1 * 0.3D, 0.0D));
                whale.setSwimming(true);
            } else {
            	this.whale.setSpeed(0.0F);
            	whale.setSwimming(false);
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
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(vector);
        }
    }
    
    @Override
    protected void registerGoals() {
      this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 6.0D, 5));
      this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
      //this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
      //this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.COD), false));
      //this.goalSelector.addGoal(4, new FollowParentGoal(this, 2.25D));
      this.goalSelector.addGoal(8, new FollowBoatGoal(this));
      this.goalSelector.addGoal(4, new FindWaterGoal(this));
      this.goalSelector.addGoal(4, new BreatheAirGoal(this));
      this.goalSelector.addGoal(4, new PanicGoal(this, 2.0D));
      //this.goalSelector.addGoal(3, new AnimalAISwimBottom(this, 1.0D, 7));
    }
    
    public boolean canStandOnFluid(Fluid water, BlockPos pos) {
    	return !this.level.getBlockState(pos.below()).is(Blocks.AIR);
    }
    
    private boolean clearanceAcquired() {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        for (int l1 = 0; l1 < 10; ++l1) {
            BlockState blockstate = level.getBlockState(blockpos$mutable.set(this.getX(), this.getY() + l1, this.getZ()));
            if (!blockstate.getFluidState().is(FluidTags.WATER)) {
                return false;
            }
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
    public void tick() {
    	super.tick();
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().add(((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5D, ((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)));
            this.yRot = this.random.nextFloat() * 360.0F;
            this.onGround = false;
            this.hasImpulse = true;
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
	protected SoundEvent getAmbientSound() {
		return SoundEvents.DOLPHIN_AMBIENT_WATER;
	}
	
	protected void updateAir(int p_209207_1_) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(p_209207_1_ - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(DamageSource.DRY_OUT, random.nextInt(2) == 0 ? 1F : 0F);
            }
        } else {
            this.setAirSupply(1000);
        }
    }
    
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.95F : 1.3F;
    }
	
}
