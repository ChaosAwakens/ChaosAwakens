package io.github.chaosawakens.common.entity.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.controllers.body.base.SmoothBodyController;
import io.github.chaosawakens.common.entity.ai.navigation.ground.base.RefinedGroundPathNavigator;
import io.github.chaosawakens.common.entity.ai.pathfinding.CAStrictGroundPathNavigator;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AnimatableMonsterEntity extends MonsterEntity implements IAnimatableEntity {
	protected static final DataParameter<Boolean> MOVING = EntityDataManager.defineId(AnimatableMonsterEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Byte> ATTACK_ID = EntityDataManager.defineId(AnimatableMonsterEntity.class, DataSerializers.BYTE);
	protected static final DataParameter<Integer> ATTACK_COOLDOWN = EntityDataManager.defineId(AnimatableMonsterEntity.class, DataSerializers.INT);
	protected float prevYRot;
	protected float lastDamageAmount; // I ONLY JUST FOUND OUT ABOUT lastHurt BRUH :skull:
	
	public AnimatableMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
		this.maxUpStep = 1.0F;
	}

	@Override
	public abstract AnimationFactory getFactory();

	@Override
	public abstract WrappedAnimationController<? extends IAnimatableEntity> getMainWrappedController();
	
	@Override
	public abstract <E extends IAnimatableEntity> ObjectArrayList<WrappedAnimationController<? extends E>> getWrappedControllers();

	@Override
	public abstract <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event);

	@Override
	public abstract int animationInterval();

	public abstract void manageAttack(LivingEntity target);

	@Nullable
	@Override
	public abstract IAnimationBuilder getIdleAnim();

	@Nullable
	@Override
	public abstract IAnimationBuilder getWalkAnim();
	
	@Nullable
	@Override
	public IAnimationBuilder getSwimAnim() {
		return null;
	}

	@Nullable
	@Override
	public abstract IAnimationBuilder getDeathAnim();
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MOVING, !isStuck());
		this.entityData.define(ATTACK_ID, (byte) 0);
		this.entityData.define(ATTACK_COOLDOWN, 0);
	}

	public boolean isMoving() {
		return this.entityData.get(MOVING);
	}

	public void setMoving(boolean moving) {
		this.entityData.set(MOVING, moving);
	}

	public byte getAttackID() {
		return this.entityData.get(ATTACK_ID);
	}

	public void setAttackID(byte attackID) {
		this.entityData.set(ATTACK_ID, attackID);
	}
	
	public boolean isAttacking() {
		return getAttackID() != (byte) 0;
	}
	
	public int getAttackCooldown() {
		return this.entityData.get(ATTACK_COOLDOWN);
	}
	
	public void setAttackCooldown(int attackCooldownTicks) {
		this.entityData.set(ATTACK_COOLDOWN, attackCooldownTicks);
	}
	
	public boolean isOnAttackCooldown() {
		return getAttackCooldown() > 0;
	}

	public float getLastDamageAmount() {
		return lastDamageAmount;
	}

	public void setLastDamageAmount(float updatedPrevDamageAmount) {
		this.lastDamageAmount = updatedPrevDamageAmount;
	}

	protected void repelEntities(double x, double y, double z, float radius) {
		List<LivingEntity> validTargets = EntityUtil.getEntitiesAround(this, LivingEntity.class, x, y, z, radius);
		
		for (Entity target : validTargets) {
			if (target.canBeCollidedWith() && !target.noPhysics && target != this) {
				double angle = (MathUtil.getRelativeAngleBetweenEntities(this, target) + 90) * Math.PI / 180;
				target.setDeltaMovement(-0.1 * Math.cos(angle), target.getDeltaMovement().y, -0.1 * Math.sin(angle));
			}
		}
	}

	protected void repelEntities(BlockPos originPos, float radius) {
		repelEntities(originPos.getX(), originPos.getY(), originPos.getZ(), radius);
	}

	protected void repelEntities(Vector3d originPos, float radius) {
		repelEntities(originPos.x, originPos.y, originPos.z, radius);
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
	
	public boolean shouldAllowDismount() {
		return false;
	}
	
	public boolean canBeKnockedBack() {
		return true;
	}

	@Override
	protected void tickDeath() {
		EntityUtil.freezeEntityRotation(this);
		setAttackID((byte) 0);
		setMoving(false);
				
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
	public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
		return super.checkSpawnRules(world, reason);
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return super.getBoundingBox();
	}

	protected void onSpawn(boolean hasAlreadyDied) {

	}
	
	@Override
	public boolean isPushable() {
		return canBeKnockedBack();
	}
	
	@Override
	protected boolean isMovementNoisy() {
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
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (pSource.getEntity() != null && !canBeKnockedBack()) {
			super.hurt(pSource, pAmount);
			return false;
		}
		return super.hurt(pSource, pAmount);
	}
		
	@Override
	public boolean doHurtTarget(Entity target) {
		if (target instanceof LivingEntity) manageAttack((LivingEntity) target);
		return super.doHurtTarget(target);
	}

	protected void divertTarget() {
		List<LivingEntity> allAttackableEntitiesAround = EntityUtil.getAllEntitiesAround(this, getFollowRange(), getFollowRange(), getFollowRange(), getFollowRange());
		
		for (LivingEntity target : allAttackableEntitiesAround) {
			if (getTarget() != null) {
				if (!isAttacking() && distanceTo(target) < distanceTo(getTarget()) && EntityPredicates.ATTACK_ALLOWED.test(target) && getSensing().canSee(target)) {
					setTarget(target);
				}
			}
		}
	}

	@Override
	public ITextComponent getName() {
		return getTypeName();
	}

	@Override
	public SoundCategory getSoundSource() {
		return SoundCategory.HOSTILE;
	}

	@Override
	protected PathNavigator createNavigation(World pLevel) {
		return new CAStrictGroundPathNavigator(this, pLevel);
	}

	@Override
	protected BodyController createBodyControl() {
		return new SmoothBodyController(this);
	}

	@Override
	public void aiStep() {
		divertTarget();
		super.aiStep();
	}

	@Override
	public void tick() {
		this.prevYRot = yRot;
		
		tickAnims();
		super.tick();
		
		updateAttackCooldown();
		setMoving(!isStuck());
		handleBaseAnimations();

		if (tickCount <= 1) onSpawn(isDeadOrDying());
	}
	
	protected void updateAttackCooldown() {
		if (getAttackCooldown() > 0) setAttackCooldown(getAttackCooldown() - 1);
		if (getAttackCooldown() < 0) setAttackCooldown(0);
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
		getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(newBaseValue);
	}

	public void setAttackDamage(double newBaseValue) {
		getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(newBaseValue);
	}

	public void setAttackSpeed(double newBaseValue) {
		getAttribute(Attributes.ATTACK_SPEED).setBaseValue(newBaseValue);
	}
	
	public void setAttackKnockback(double newBaseValue) {
		getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(newBaseValue);
	}

	public void setMovementSpeed(double newBaseValue) {
		getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(newBaseValue);
	}

	public void setFlyingSpeed(double newBaseValue) {
		getAttribute(Attributes.FLYING_SPEED).setBaseValue(newBaseValue);
	}

	public void setKnockbackResistance(double newBaseValue) {
		getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(newBaseValue);
	}

	public void setArmor(double newBaseValue) {
		getAttribute(Attributes.ARMOR).setBaseValue(newBaseValue);
	}
	
	public void setArmorToughness(double newBaseValue) {
		getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(newBaseValue);
	}

	@Override
	public boolean canUpdate() {
		return super.canUpdate();
	}

	public float getDeltaKnockbackResistance() {
		return (float) MathHelper.clamp(getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * 100, 0.0D, 100.0D);
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public float getWalkTargetValue(BlockPos pPos, IWorldReader pLevel) {
		return 0.0F;
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT pCompound) {
		super.readAdditionalSaveData(pCompound);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT pCompound) {
		super.addAdditionalSaveData(pCompound);
	}

	public float getMeleeAttackReach(LivingEntity target) {
		if (target == null) return 0;
		return ((1.0F + getBbWidth() / 2.0F) * 2) + 0.2F;
	}
	
	protected boolean shouldPlayIdleAnim() {
		return getIdleAnim() != null && !isAttacking();
	}

	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isAttacking() && !isMoving() && !isDeadOrDying()) playAnimation(getIdleAnim(), true);
		if (getWalkAnim() != null && isMoving() && !isAttacking() && !isDeadOrDying()) playAnimation(getWalkAnim(), true);

		if (isDeadOrDying()) {
			stopAnimation(getIdleAnim());
			stopAnimation(getWalkAnim());
		}
	}
}