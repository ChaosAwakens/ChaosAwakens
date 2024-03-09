package io.github.chaosawakens.common.entity.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.goals.passive.water.RandomRoamSwimmingGoal;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.util.EntityUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public abstract class AnimatableFishEntity extends AbstractFishEntity implements IAnimatableEntity {
	private static final DataParameter<Boolean> SWIMMING = EntityDataManager.defineId(AnimatableFishEntity.class, DataSerializers.BOOLEAN);
	protected float prevBodyRot;
	protected float lastDamageAmount;

	public AnimatableFishEntity(EntityType<? extends AbstractFishEntity> type, World world) {
		super(type, world);
	}

	@Override
	public abstract AnimationFactory getFactory();

	@Override
	abstract public WrappedAnimationController<? extends AnimatableFishEntity> getMainWrappedController();
	
	@Override
	abstract public <E extends IAnimatableEntity> ObjectArrayList<WrappedAnimationController<? extends E>> getWrappedControllers();

	@Override
	public abstract int animationInterval();

	@Override
	public abstract <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event);

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0F, 1.6D, 1.4D, EntityPredicates.NO_SPECTATORS::test));
		this.goalSelector.addGoal(1, new RandomRoamSwimmingGoal(this));
	}

	@Nullable
	@Override
	public abstract IAnimationBuilder getIdleAnim();
	
	@Override
	public IAnimationBuilder getWalkAnim() {
		return null;
	}

	@Nullable
	@Override
	public abstract IAnimationBuilder getSwimAnim();

	@Nullable
	@Override
	public abstract IAnimationBuilder getDeathAnim();
	
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
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.COD_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.COD_DEATH;
	}
	
	public boolean canRoam() {
		return canRandomSwim();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SWIMMING, !isStuck() && isInWater());
	}

	public float getLastDamageAmount() {
		return lastDamageAmount;
	}

	public void setLastDamageAmount(float updatedPrevDamageAmount) {
		this.lastDamageAmount = updatedPrevDamageAmount;
	}

	public boolean isSwimming() {
		return this.entityData.get(SWIMMING);
	}

	public void setSwimming(boolean moving) {
		this.entityData.set(SWIMMING, moving);
	}
	
	@Override
	public int getMaxAirSupply() {
		return super.getMaxAirSupply();
	}

	public boolean isStuck() {
		double dx = getX() - xo;
		double dz = getZ() - zo;
		double dxSqr = dx * dx;
		double dzSqr = dz * dz;
		
		return dxSqr + dzSqr < getMovementThreshold();
	}
	
	public double getMovementThreshold() {
		return 2.500000277905201E-7;
	}
	
	public boolean canBeKnockedBack() {
		return true;
	}
	
	@Override
	public boolean isPushable() {
		return canBeKnockedBack();
	}
	
	@Override
	public void push(double pX, double pY, double pZ) {
		if (!canBeKnockedBack()) return;
		super.push(pX, pY, pZ);
	}
	
	@Override
	public void knockback(float pStrength, double pRatioX, double pRatioZ) {
		if (!canBeKnockedBack()) return;
		super.knockback(pStrength, pRatioX, pRatioZ);
	}

	@Override
	protected void tickDeath() {
		if (getDeathAnim() != null) {
			DamageSource lastValidDamageSource = getLastDamageSource() == null ? DamageSource.GENERIC : getLastDamageSource();
			
			playAnimation(getDeathAnim(), false);
			EntityUtil.handleAnimatableDeath(this, lastValidDamageSource, (owner) -> dropAllDeathLoot(lastValidDamageSource));
			
			if (getDeathAnim().hasAnimationFinished()) {
				remove();
				
				for (int i = 0; i < 20; ++i) {
					double xOffset = this.random.nextGaussian() * 0.02D;
					double yOffset = this.random.nextGaussian() * 0.02D;
					double zOffset = this.random.nextGaussian() * 0.02D;
					this.level.addParticle(ParticleTypes.POOF, getRandomX(1.0D), getRandomY(), getRandomZ(1.0D), xOffset, yOffset, zOffset);
				}
			}
		} else {
			super.tickDeath();
		}
	}
	
	@Override
	public void die(DamageSource pCause) {
		if (getDeathAnim() != null) return;
		else super.die(pCause);
	}

	@Override
	protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {
		super.actuallyHurt(pDamageSource, pDamageAmount);

		setLastDamageAmount(pDamageAmount);
	}

	@Override
	public void tick() {
		this.prevBodyRot = yBodyRot;
		tickAnims();
		super.tick();

		if (!level.isClientSide) setSwimming(!isStuck() && isInWater());
		handleBaseAnimations();
	}

	public double getFollowRange() {
		return getAttributeValue(Attributes.FOLLOW_RANGE);
	}

	public double getAttackDamage() {
		return getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	public double getAttackSpeed() {
		return getAttributeValue(Attributes.ATTACK_SPEED);
	}

	public double getMovementSpeed() {
		return getAttributeValue(Attributes.MOVEMENT_SPEED);
	}

	public double getFlyingSpeed() {
		return getAttributeValue(Attributes.FLYING_SPEED);
	}

	public double getKnockbackResistance() {
		return getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
	}

	public double getArmor() {
		return getAttributeValue(Attributes.ARMOR);
	}

	public void setFollowRange(double newBaseValue) {
		getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(newBaseValue);;
	}

	public void setAttackDamage(double newBaseValue) {
		getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(newBaseValue);;
	}

	public void setAttackSpeed(double newBaseValue) {
		getAttribute(Attributes.ATTACK_SPEED).setBaseValue(newBaseValue);;
	}
	
	public void setAttackKnockback(double newBaseValue) {
		getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(newBaseValue);;
	}

	public void setMovementSpeed(double newBaseValue) {
		getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(newBaseValue);;
	}

	public void setFlyingSpeed(double newBaseValue) {
		getAttribute(Attributes.FLYING_SPEED).setBaseValue(newBaseValue);;
	}

	public void setKnockbackResistance(double newBaseValue) {
		getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(newBaseValue);;
	}

	public void setArmor(double newBaseValue) {
		getAttribute(Attributes.ARMOR).setBaseValue(newBaseValue);;
	}
	
	public void setArmorToughness(double newBaseValue) {
		getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(newBaseValue);;
	}

	@Override
	public boolean canUpdate() {
		return super.canUpdate();
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected float getStandingEyeHeight(Pose pose, EntitySize size) {
		return this.isBaby() ? size.height * 0.45F : 0.6F;
	}

	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isSwimming()) playAnimation(getIdleAnim(), false);
		if (getSwimAnim() != null && (isSwimming() || !isStuck())) playAnimation(getSwimAnim(), false);
	}
}
