package io.github.chaosawakens.common.entity.base;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.registry.CAEffects;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AnimatableWaterMobEntity extends WaterMobEntity implements IAnimatableEntity {
	private static final DataParameter<Boolean> SWIMMING = EntityDataManager.defineId(AnimatableWaterMobEntity.class, DataSerializers.BOOLEAN);
	public float prevBodyRot;

	public AnimatableWaterMobEntity(EntityType<? extends WaterMobEntity> type, World world) {
		super(type, world);
		this.setPathfindingMalus(PathNodeType.WATER, 8.0F);
	}

	@Override
	public abstract AnimationFactory getFactory();

	@Override
	abstract public WrappedAnimationController<? extends AnimatableWaterMobEntity> getMainWrappedController();
	
	@Override
	abstract public <E extends IAnimatableEntity> ObjectArrayList<WrappedAnimationController<? extends E>> getWrappedControllers();

	@Override
	public abstract int animationInterval();

	@Override
	public abstract <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event);
	
	@Nullable
	abstract public SingletonAnimationBuilder getIdleAnim();

	@Nullable
	abstract public SingletonAnimationBuilder getSwimAnim();

	@Nullable
	abstract public SingletonAnimationBuilder getDeathAnim();
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SWIMMING, !isStuck());
	}

	public boolean isSwimming() {
		return this.entityData.get(SWIMMING);
	}

	public void setSwimming(boolean moving) {
		this.entityData.set(SWIMMING, moving);
	}
	
	public boolean isStuck() {
		double dx = getX() - xo;
		double dz = getZ() - zo;
		double dxSqr = dx * dx;
		double dzSqr = dz * dz;
		return dxSqr + dzSqr < 2.500000277905201E-7;
	}
	
	@Override
	protected void tickDeath() {
		if (getDeathAnim() != null) {
			playAnimation(getDeathAnim(), false);
			if (getDeathAnim().hasAnimationFinished()) remove();

			for(int i = 0; i < 20; ++i) {
				double xOffset = this.random.nextGaussian() * 0.02D;
				double yOffset = this.random.nextGaussian() * 0.02D;
				double zOffset = this.random.nextGaussian() * 0.02D;
				this.level.addParticle(ParticleTypes.POOF, getRandomX(1.0D), getRandomY(), getRandomZ(1.0D), xOffset, yOffset, zOffset);
			}
		} else {
			super.tickDeath();
		}
	}
	
	@Override
	public void tick() {
		this.prevBodyRot = yBodyRot;
		super.tick();
		
		if (!level.isClientSide) setSwimming(!isStuck() && isInWater());
		handleBaseAnimations();
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

	protected double getFlyingSpeed() {
		return this.getAttributeValue(Attributes.FLYING_SPEED);
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

	protected void setFlyingSpeed(double newBaseValue) {
		this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(newBaseValue);;
	}

	protected void setKnockbackResistance(double newBaseValue) {
		this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(newBaseValue);;
	}

	protected void setArmor(double newBaseValue) {
		this.getAttribute(Attributes.ARMOR).setBaseValue(newBaseValue);;
	}

	@Override
	public boolean canUpdate() {
		if (hasEffect(CAEffects.PARALYSIS_EFFECT.get())) return false;
		return super.canUpdate();
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public double getMeleeAttackReachSqr(LivingEntity target) {
		return (double) (this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + target.getBbWidth());
	}

	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isSwimming()) playAnimation(getIdleAnim(), false);
		if (getSwimAnim() != null && isSwimming()) playAnimation(getSwimAnim(), false);
	}
}
