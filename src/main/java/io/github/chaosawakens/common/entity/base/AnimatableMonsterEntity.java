package io.github.chaosawakens.common.entity.base;

import java.util.List;
import java.util.UUID;

import io.github.chaosawakens.api.IAnimatableEntity;
import io.github.chaosawakens.api.IUtilityHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AnimatableMonsterEntity extends MonsterEntity implements IAnimatableEntity, IAnimationTickable {
	protected static final DataParameter<Boolean> MOVING = EntityDataManager.defineId(AnimatableMonsterEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> ATTACKING = EntityDataManager.defineId(AnimatableMonsterEntity.class, DataSerializers.BOOLEAN);
	protected boolean isAnimationFinished = false;
	
	public AnimatableMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	abstract public void registerControllers(AnimationData data);

	@Override
	abstract public AnimationFactory getFactory();
	
	@Override
	abstract public AnimationController<?> getController();
	
	@Override
	abstract public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event);
	
	@Override
	abstract public int animationInterval();
	
	abstract public void manageAttack(LivingEntity target);

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MOVING, false);
		this.entityData.define(ATTACKING, false);
	}

	public boolean getMoving() {
		return this.entityData.get(MOVING);
	}

	public void setMoving(boolean moving) {
		this.entityData.set(MOVING, moving);
	}

	public boolean getAttacking() {
		return this.entityData.get(ATTACKING);
	}

	public void setAttacking(boolean attacking) {
		this.entityData.set(ATTACKING, attacking);
	}
	
/*	public void summonEntitiesAroundHC(LivingEntity entityToSummon, double dX, double dZ, double maxYDiff, int amountToSummon, boolean shouldBeOnSameTeam) {
		AxisAlignedBB summoningArea = this.getBoundingBox().inflate(dX, 4.0D, dZ);
		
		for (int x = (int) -dX; x < dX; x++) {
			for (int z = (int) -dZ; z < dZ; z++) {
				BlockPos.Mutable validY = new Mutable();
				
				double summonY = summoningArea.minY;
				double totalHorizontalSize = Math.sqrt((summoningArea.getXsize() * summoningArea.getXsize()) + (summoningArea.getZsize() * summoningArea.getZsize()));
				
				Stream<BlockState> allPositions = this.level.getBlockStates(summoningArea);
				
				allPositions.forEach(state -> {					
					validY.set(dX, summonY, dZ);
					
					state = level.getBlockState(validY);
					
					
					
				});
				
			}
		}
	}*/
	
    protected void repelEntities(float x, float y, float z, float radius) {
        List<LivingEntity> targetList = IUtilityHelper.getEntitiesAround(this, LivingEntity.class, x, y, z, radius);
        for (Entity target : targetList) {
            if (target.canBeCollidedWith() && !target.noPhysics) {
                double angle = (IUtilityHelper.getAngleBetweenEntities(this, target) + 90) * Math.PI / 180;
                target.setDeltaMovement(-0.1 * Math.cos(angle), target.getDeltaMovement().y, -0.1 * Math.sin(angle));
            }
        }
    }
    
    protected boolean isStuck() {
  //  	return Math.abs(xOld - getX()) < 2.25D && Math.abs(yOld - getY()) < 2.25D && Math.abs(zOld - getZ()) < 2.25D;
 //   	return getX() == xOld && getY() == yOld && getZ() == zOld;
    	return false;
    }
    
    protected boolean isStuckVertically() {
    	return Math.abs(yOld - getY()) < 0.001F;
    }
    
    protected boolean isStuckHorizontally() {
    	return Math.abs(xOld - getX()) < 0.001F && Math.abs(zOld - getZ()) < 0.001F;
    }
	
    //TODO Vanilla copy
	@SuppressWarnings("deprecation")
	@Override
	public void travel(Vector3d vec) {
	      if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
	    	  UUID SLOW_FALLING_ID = UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA");
	    	  AttributeModifier SLOW_FALLING = new AttributeModifier(SLOW_FALLING_ID, "Slow falling acceleration reduction", -0.07, AttributeModifier.Operation.ADDITION);
	          double d0 = 0.08D;
	          ModifiableAttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
	          boolean flag = this.getDeltaMovement().y <= 0.0D;
	          if (flag && this.hasEffect(Effects.SLOW_FALLING)) {
	             if (!gravity.hasModifier(SLOW_FALLING)) gravity.addTransientModifier(SLOW_FALLING);
	             this.fallDistance = 0.0F;
	          } else if (gravity.hasModifier(SLOW_FALLING)) {
	             gravity.removeModifier(SLOW_FALLING);
	          }
	          d0 = gravity.getValue();

	          FluidState fluidstate = this.level.getFluidState(this.blockPosition());
	          if (this.isInWater() && this.isAffectedByFluids() && !this.canStandOnFluid(fluidstate.getType())) {
	             double d8 = this.getY();
	             float f5 = this.isSprinting() ? 0.9F : this.getWaterSlowDown();
	             float f6 = 0.02F;
	             float f7 = (float)EnchantmentHelper.getDepthStrider(this);
	             if (f7 > 3.0F) {
	                f7 = 3.0F;
	             }

	             if (!this.onGround) {
	                f7 *= 0.5F;
	             }

	             if (f7 > 0.0F) {
	                f5 += (0.54600006F - f5) * f7 / 3.0F;
	                f6 += (this.getSpeed() - f6) * f7 / 3.0F;
	             }

	             if (this.hasEffect(Effects.DOLPHINS_GRACE)) {
	                f5 = 0.96F;
	             }

	             f6 *= (float)this.getAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).getValue();
	             this.moveRelative(f6, vec);
	             this.move(MoverType.SELF, this.getDeltaMovement());
	             Vector3d vector3d6 = this.getDeltaMovement();
	             if (this.horizontalCollision && this.onClimbable()) {
	                vector3d6 = new Vector3d(vector3d6.x, 0.2D, vector3d6.z);
	             }

	             this.setDeltaMovement(vector3d6.multiply((double)f5, (double)0.8F, (double)f5));
	             Vector3d vector3d2 = this.getFluidFallingAdjustedMovement(d0, flag, this.getDeltaMovement());
	             this.setDeltaMovement(vector3d2);
	             if (this.horizontalCollision && this.isFree(vector3d2.x, vector3d2.y + (double)0.6F - this.getY() + d8, vector3d2.z)) {
	                this.setDeltaMovement(vector3d2.x, (double)0.3F, vector3d2.z);
	             }
	          } else if (this.isInLava() && this.isAffectedByFluids() && !this.canStandOnFluid(fluidstate.getType())) {
	             double d7 = this.getY();
	             this.moveRelative(0.02F, vec);
	             this.move(MoverType.SELF, this.getDeltaMovement());
	             if (this.getFluidHeight(FluidTags.LAVA) <= this.getFluidJumpThreshold()) {
	                this.setDeltaMovement(this.getDeltaMovement().multiply(0.5D, (double)0.8F, 0.5D));
	                Vector3d vector3d3 = this.getFluidFallingAdjustedMovement(d0, flag, this.getDeltaMovement());
	                this.setDeltaMovement(vector3d3);
	             } else {
	                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
	             }

	             if (!this.isNoGravity()) {
	                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));
	             }

	             Vector3d vector3d4 = this.getDeltaMovement();
	             if (this.horizontalCollision && this.isFree(vector3d4.x, vector3d4.y + (double)0.6F - this.getY() + d7, vector3d4.z)) {
	                this.setDeltaMovement(vector3d4.x, (double)0.3F, vector3d4.z);
	             }
	          } else if (this.isFallFlying()) {
	             Vector3d vector3d = this.getDeltaMovement();
	             if (vector3d.y > -0.5D) {
	                this.fallDistance = 1.0F;
	             }

	             Vector3d vector3d1 = this.getLookAngle();
	             float f = this.xRot * ((float)Math.PI / 180F);
	             double d1 = Math.sqrt(vector3d1.x * vector3d1.x + vector3d1.z * vector3d1.z);
	             double d3 = Math.sqrt(getHorizontalDistanceSqr(vector3d));
	             double d4 = vector3d1.length();
	             float f1 = MathHelper.cos(f);
	             f1 = (float)((double)f1 * (double)f1 * Math.min(1.0D, d4 / 0.4D));
	             vector3d = this.getDeltaMovement().add(0.0D, d0 * (-1.0D + (double)f1 * 0.75D), 0.0D);
	             if (vector3d.y < 0.0D && d1 > 0.0D) {
	                double d5 = vector3d.y * -0.1D * (double)f1;
	                vector3d = vector3d.add(vector3d1.x * d5 / d1, d5, vector3d1.z * d5 / d1);
	             }

	             if (f < 0.0F && d1 > 0.0D) {
	                double d9 = d3 * (double)(-MathHelper.sin(f)) * 0.04D;
	                vector3d = vector3d.add(-vector3d1.x * d9 / d1, d9 * 3.2D, -vector3d1.z * d9 / d1);
	             }

	             if (d1 > 0.0D) {
	                vector3d = vector3d.add((vector3d1.x / d1 * d3 - vector3d.x) * 0.1D, 0.0D, (vector3d1.z / d1 * d3 - vector3d.z) * 0.1D);
	             }

	             this.setDeltaMovement(vector3d.multiply((double)0.99F, (double)0.98F, (double)0.99F));
	             this.move(MoverType.SELF, this.getDeltaMovement());
	             if (this.horizontalCollision && !this.level.isClientSide) {
	                double d10 = Math.sqrt(getHorizontalDistanceSqr(this.getDeltaMovement()));
	                double d6 = d3 - d10;
	                float f2 = (float)(d6 * 10.0D - 3.0D);
	                if (f2 > 0.0F) {
	                   this.playSound(this.getFallDamageSound((int)f2), 1.0F, 1.0F);
	                   this.hurt(DamageSource.FLY_INTO_WALL, f2);
	                }
	             }

	             if (this.onGround && !this.level.isClientSide) {
	                this.setSharedFlag(7, false);
	             }
	          } else {
	             BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement();
	             float f3 = this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getSlipperiness(level, this.getBlockPosBelowThatAffectsMyMovement(), this);
	             float f4 = this.onGround ? f3 * 0.91F : 0.91F;
	             Vector3d vector3d5 = this.handleRelativeFrictionAndCalculateMovement(vec, f3);
	             double d2 = vector3d5.y;
	             if (this.hasEffect(Effects.LEVITATION)) {
	                d2 += (0.05D * (double)(this.getEffect(Effects.LEVITATION).getAmplifier() + 1) - vector3d5.y) * 0.2D;
	                this.fallDistance = 0.0F;
	             } else if (this.level.isClientSide && !this.level.hasChunkAt(blockpos)) {
	                if (this.getY() > 0.0D) {
	                   d2 = -0.1D;
	                } else {
	                   d2 = 0.0D;
	                }
	             } else if (!this.isNoGravity()) {
	                d2 -= d0;
	             }         
	             this.setDeltaMovement(vector3d5.x * (double)f4, d2 * (double)0.98F, vector3d5.z * (double)f4);         
	          }	      
	      }       
	      this.calculateEntityAnimation(this, this instanceof IFlyingAnimal);
	}
	
	@Override
	protected void tickDeath() {
		super.tickDeath();
	}
	
	@Override
	public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
		return super.checkSpawnRules(world, reason);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox() {
		return super.getBoundingBox();
	}
	
	@Override
	public boolean doHurtTarget(Entity target) {
		return super.doHurtTarget(target);
	}
	
	protected void divertTarget() {
		List<LivingEntity> allAttackableEntitiesAround = IUtilityHelper.getAllEntitiesAround(this, getFollowRange(), getFollowRange(), getFollowRange(), getFollowRange());
		
		for (LivingEntity target : allAttackableEntitiesAround) {
			if (getTarget() != null) {
				if (this.distanceTo(target) < this.distanceTo(getTarget()) && EntityPredicates.ATTACK_ALLOWED.test(target) && this.getSensing().canSee(target)) {
					this.setTarget(target);
				}
			}
		}
	}
	
	@Override
	public void aiStep() {
		divertTarget();
		super.aiStep();
	}
	
	@Override
	public void tick() {	
		super.tick();
	}
	
	protected double getFollowRange() {
		return this.getAttributeValue(Attributes.FOLLOW_RANGE);
	}
	
	protected double getAttackDamage() {
		return this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}
	
	protected double getAttackSpeed() {
		return this.getAttributeValue(Attributes.ATTACK_SPEED);
	}
	
	protected double getMovementSpeed() {
		return this.getAttributeValue(Attributes.MOVEMENT_SPEED);
	}
	
	protected double getKnockbackResistance() {
		return this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
	}
	
	protected double getArmor() {
		return this.getAttributeValue(Attributes.ARMOR);
	}
	
	protected void setFollowRange(double newBaseValue) {
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(newBaseValue);;
	}
	
	protected void setAttackDamage(double newBaseValue) {
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(newBaseValue);;
	}
	
	protected void setAttackSpeed(double newBaseValue) {
		this.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(newBaseValue);;
	}
	
	protected void setMovementSpeed(double newBaseValue) {
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(newBaseValue);;
	}
	
	protected void setKnockbackResistance(double newBaseValue) {
		this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(newBaseValue);;
	}
	
	protected void setArmor(double newBaseValue) {
		this.getAttribute(Attributes.ARMOR).setBaseValue(newBaseValue);;
	}
	
	@Override
	public int tickTimer() {
		return tickCount;
	}
	
}
