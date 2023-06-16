package io.github.chaosawakens.common.entity.base;

import java.util.List;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
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
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AnimatableMonsterEntity extends MonsterEntity implements IAnimatableEntity {
	protected static final DataParameter<Boolean> MOVING = EntityDataManager.defineId(AnimatableMonsterEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Byte> ATTACK_ID = EntityDataManager.defineId(AnimatableMonsterEntity.class, DataSerializers.BYTE);
	public float prevYRot;
	
	public AnimatableMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	@Override
	abstract public AnimationFactory getFactory();

	@Override
	abstract public WrappedAnimationController<? extends AnimatableMonsterEntity> getMainWrappedController();
	
	@Override
	abstract public <E extends IAnimatableEntity> ObjectArrayList<WrappedAnimationController<? extends E>> getWrappedControllers();

	@Override
	abstract public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event);

	@Override
	abstract public int animationInterval();

	abstract public void manageAttack(LivingEntity target);

	@Nullable
	abstract public SingletonAnimationBuilder getIdleAnim();

	@Nullable
	abstract public SingletonAnimationBuilder getWalkAnim();

	@Nullable
	abstract public SingletonAnimationBuilder getDeathAnim();
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MOVING, !isStuck());
		this.entityData.define(ATTACK_ID, (byte) 0);
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

	protected void repelEntities(double x, double y, double z, float radius) {
		List<LivingEntity> validTargets = EntityUtil.getEntitiesAround(this, LivingEntity.class, x, y, z, radius);
		for (Entity target : validTargets) {
			if (target.canBeCollidedWith() && !target.noPhysics) {
				double angle = (MathUtil.getAngleBetweenEntities(this, target) + 90) * Math.PI / 180;
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
		return dxSqr + dzSqr < 2.500000277905201E-7;
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
		if (getDeathAnim() != null) {
			WrappedAnimationController<? extends IAnimatableEntity> wrappedController = getDeathAnim().getWrappedController();
			playAnimation(getDeathAnim(), false);
			
			if (wrappedController.isCurrentAnimationFinished()) {
				this.remove();
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
	public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
		return super.checkSpawnRules(world, reason);
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return super.getBoundingBox();
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
	public boolean doHurtTarget(Entity target) {
		if (target != null && target instanceof LivingEntity) manageAttack((LivingEntity) target);
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
	protected PathNavigator createNavigation(World pLevel) {
		return new CAStrictGroundPathNavigator(this, pLevel);
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
		
		if (!level.isClientSide) setMoving(!isStuck());
		handleBaseAnimations();
	}

	protected double getFollowRange() {
		return getAttributeValue(Attributes.FOLLOW_RANGE);
	}

	protected double getAttackDamage() {
		return getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	protected double getAttackSpeed() {
		return getAttributeValue(Attributes.ATTACK_SPEED);
	}

	protected double getMovementSpeed() {
		return getAttributeValue(Attributes.MOVEMENT_SPEED);
	}

	protected double getFlyingSpeed() {
		return getAttributeValue(Attributes.FLYING_SPEED);
	}

	protected double getKnockbackResistance() {
		return getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
	}

	protected double getArmor() {
		return getAttributeValue(Attributes.ARMOR);
	}

	protected void setFollowRange(double newBaseValue) {
		getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(newBaseValue);;
	}

	protected void setAttackDamage(double newBaseValue) {
		getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(newBaseValue);;
	}

	protected void setAttackSpeed(double newBaseValue) {
		getAttribute(Attributes.ATTACK_SPEED).setBaseValue(newBaseValue);;
	}

	protected void setMovementSpeed(double newBaseValue) {
		getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(newBaseValue);;
	}

	protected void setFlyingSpeed(double newBaseValue) {
		getAttribute(Attributes.FLYING_SPEED).setBaseValue(newBaseValue);;
	}

	protected void setKnockbackResistance(double newBaseValue) {
		getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(newBaseValue);;
	}

	protected void setArmor(double newBaseValue) {
		getAttribute(Attributes.ARMOR).setBaseValue(newBaseValue);;
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

	public double getMeleeAttackReachSqr(LivingEntity target) {
		if (target == null) return 0;
		return (getBbWidth() * 2.0F * getBbWidth() * 2.0F + target.getBbWidth()) / 2;
	}
	
	protected boolean shouldPlayIdleAnim() {
		return getIdleAnim() != null && !isAttacking();
	}

	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isAttacking() && !isMoving() && !isDeadOrDying()) playAnimation(getIdleAnim(), false);
		if (getWalkAnim() != null && isMoving() && !isAttacking() && !isDeadOrDying()) playAnimation(getWalkAnim(), false);
	}
}
