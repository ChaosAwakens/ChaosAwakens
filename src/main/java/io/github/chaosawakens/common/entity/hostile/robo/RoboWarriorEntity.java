package io.github.chaosawakens.common.entity.hostile.robo;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableShootGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.robo.robowarrior.RoboWarriorShieldGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.entity.projectile.RoboLaserEntity;
import io.github.chaosawakens.common.entity.projectile.RoboRayEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.registry.CATeams;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.common.util.SoundUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class RoboWarriorEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<RoboWarriorEntity>> roboWarriorControllers = new ObjectArrayList<WrappedAnimationController<RoboWarriorEntity>>(4);
	private final ObjectArrayList<IAnimationBuilder> roboWarriorAnimations = new ObjectArrayList<IAnimationBuilder>(12);
	private static final DataParameter<Boolean> HAS_HIT_HEALTH_THRESHOLD = EntityDataManager.defineId(RoboWarriorEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_SHIELDED = EntityDataManager.defineId(RoboWarriorEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> SHIELD_DESTROYED = EntityDataManager.defineId(RoboWarriorEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SHIELD_DAMAGE = EntityDataManager.defineId(RoboWarriorEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> STORED_DAMAGE = EntityDataManager.defineId(RoboWarriorEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> SHIELD_ACTIVATION_TIME = EntityDataManager.defineId(RoboWarriorEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> SHIELD_ACTIVATION_DAMAGE_THRESHOLD = EntityDataManager.defineId(RoboWarriorEntity.class, DataSerializers.INT);
	private final WrappedAnimationController<RoboWarriorEntity> mainController = createMainMappedController("robowarriormaincontroller");
	private final WrappedAnimationController<RoboWarriorEntity> ambienceController = createMappedController("robowarriorambiencecontroller", this::ambiencePredicate);
	private final WrappedAnimationController<RoboWarriorEntity> attackController = createMappedController("robowarriorattackcontroller", this::attackPredicate);
	private final WrappedAnimationController<RoboWarriorEntity> shieldController = createMappedController("robowarriorshieldcontroller", this::shieldPredicate);
	private final WrappedAnimationController<RoboWarriorEntity> walkController = createMappedController("robowarriorwalkcontroller", this::walkPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP).setWrappedController(walkController);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(shieldController);
	private final SingletonAnimationBuilder idleExtrasAnim = new SingletonAnimationBuilder(this, "Idle Extras", EDefaultLoopTypes.LOOP).setWrappedController(ambienceController);
	private final SingletonAnimationBuilder leftUppercutAnim = new SingletonAnimationBuilder(this, "Left Uppercut Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder rightUppercutAnim = new SingletonAnimationBuilder(this, "Right Uppercut Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder burstLaserAttackAnim = new SingletonAnimationBuilder(this, "Burst Laser Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController).setAnimSpeed(0.85D);
	private final SingletonAnimationBuilder chargedLaserAttackAnim = new SingletonAnimationBuilder(this, "Charged Laser Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder shieldUpAnim = new SingletonAnimationBuilder(this, "Activate Shield", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(shieldController);
	private final SingletonAnimationBuilder shieldedAnim = new SingletonAnimationBuilder(this, "Shield Up", EDefaultLoopTypes.LOOP).setWrappedController(shieldController);
	private final SingletonAnimationBuilder shieldDownAnim = new SingletonAnimationBuilder(this, "Deactivate Shield", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(shieldController);
	private final SingletonAnimationBuilder shieldDestroyedAnim = new SingletonAnimationBuilder(this, "Destroy Shield", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(shieldController);
	private static final byte UPPERCUT_ATTACK_ID = 1;
	private static final byte LASER_BURST_ATTACK_ID = 2;
	private static final byte CHARGED_SHOT_ATTACK_ID = 3;
	public static final String ROBO_WARRIOR_MDF_NAME = "robo_warrior";
	private static final BiFunction<AnimatableMonsterEntity, Vector3d, Entity> LASER_FACTORY_BURST = (owner, offset) -> {
		LivingEntity target = owner.getTarget();
		World world = owner.level;

		if (target == null) return null;

		Vector3d viewVector = owner.getViewVector(1.0F);
		double offsetX = target.getX() - (owner.getX() + viewVector.x * offset.x());
		double offsetY = target.getY(0.5D) - (offset.y() + owner.getY(0.5D));
		double offsetZ = target.getZ() - (owner.getZ() + viewVector.z * offset.z());

		RoboLaserEntity laser = new RoboLaserEntity(world, owner, offsetX, offsetY, offsetZ);
		laser.setPower(20, 0, false);
		laser.setPos(owner.getX() + viewVector.x * offset.x(), owner.getY(0.5D) + offset.y(),
				owner.getZ() + viewVector.z * offset.z());

		laser.changeSpeed(4);

		return laser;
	};
	private static final BiFunction<AnimatableMonsterEntity, Vector3d, Entity> LASER_FACTORY_CHARGED = (owner, offset) -> {
		LivingEntity target = owner.getTarget();
		World world = owner.level;

		if (target == null) return null;

		Vector3d viewVector = owner.getViewVector(1.0F);
		double offsetX = target.getX() - (owner.getX() + viewVector.x * offset.x());
		double offsetY = target.getY(0.5D) - (offset.y() + owner.getY(0.5D));
		double offsetZ = target.getZ() - (owner.getZ() + viewVector.z * offset.z());

		RoboRayEntity ray = new RoboRayEntity(world, owner, offsetX, offsetY, offsetZ);
		ray.setPower(50, 3, false);
		ray.changeSpeed(1);
		ray.setPos(owner.getX() + viewVector.x * offset.x(), owner.getY(0.5D) + offset.y(), owner.getZ() + viewVector.z * offset.z());
		ray.setShot(true);

		return ray;
	};
	private static final Vector3d LASER_OFFSET = new Vector3d(1.0, 0.1, 1.0);
	private static final Vector3d LASER_BURST_OFFSET = new Vector3d(1.0, 0.1, 1.0);
	
	public RoboWarriorEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 180)
				.add(Attributes.ARMOR, 10)
				.add(Attributes.ARMOR_TOUGHNESS, 3)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.4D)
				.add(Attributes.ATTACK_DAMAGE, 10)
				.add(Attributes.ATTACK_KNOCKBACK, 2.5D)
				.add(Attributes.FOLLOW_RANGE, 60);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<RoboWarriorEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return !attackController.getWrappedController().getAnimationState().equals(AnimationState.Stopped) || isShielded() || isShieldDestroyed() || isShieldGoingDown() || isDeadOrDying() ? PlayState.STOP : PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState ambiencePredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return isDeadOrDying() || isShielded() || isShieldDestroyed() || isShieldGoingDown() ? PlayState.STOP : PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState shieldPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState walkPredicate(AnimationEvent<E> event) {
		return isAttacking() || isShielded() || isShieldDestroyed() || isShieldGoingDown() || isDeadOrDying() || isStuck() || isOnAttackCooldown() ? PlayState.STOP : PlayState.CONTINUE;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1, 3) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isShielded() && !isShieldDestroyed() && !isShieldGoingDown();
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !isShielded() && !isShieldDestroyed() && !isShieldGoingDown();
			}
		});
		this.goalSelector.addGoal(1, new RoboWarriorShieldGoal(this, () -> shieldUpAnim, () -> shieldedAnim, () -> shieldDownAnim, () -> shieldDestroyedAnim));
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, null, UPPERCUT_ATTACK_ID, 16D, 18.4D, 80.0D, 1, 10, (owner) -> !isShielded() && !isShieldDestroyed() && !isShieldGoingDown() && !isPlayingAnimation(shieldUpAnim) && !isPlayingAnimation(shieldedAnim) && !isPlayingAnimation(shieldDownAnim) && !isPlayingAnimation(shieldDestroyedAnim)).pickBetweenAnimations(() -> leftUppercutAnim, () -> rightUppercutAnim).soundOnStart(CASoundEvents.ROBO_WARRIOR_UPPERCUT, 1.0F));
		this.targetSelector.addGoal(0, new AnimatableShootGoal(this, CHARGED_SHOT_ATTACK_ID, () -> chargedLaserAttackAnim, LASER_FACTORY_CHARGED, LASER_OFFSET, 73.5D, 75.6D, 20, 100, 10, 0) {

			@Override
			public boolean canUse() {
				return super.canUse() && !isShielded() && !isShieldDestroyed() && !isShieldGoingDown();
			}
		});
		this.targetSelector.addGoal(0, new AnimatableShootGoal(this, CHARGED_SHOT_ATTACK_ID, () -> chargedLaserAttackAnim, LASER_FACTORY_CHARGED, LASER_OFFSET, 73.5D, 75.6D, 20, 100, 10, 0) {

			@Override
			public boolean canUse() {
				return ObjectUtil.performNullityChecks(false, getTarget())
						&& distanceTo(getTarget()) >= minimumDistance && canSee(getTarget()) && !getTarget().isInvulnerable()
						&& isAlive() && !isAttacking() && getTarget().isAlive() && !getTarget().isDeadOrDying()
						&& !isOnAttackCooldown() && (EntityUtil.getAllEntitiesAround(RoboWarriorEntity.this, 6.0D, 6.0D, 6.0D, 6.0D).size() >= 2 || EntityUtil.getAllEntitiesAround(RoboWarriorEntity.this, 16.0D, 16.0D, 16.0D, 6.0D).size() >= 4)
						&& !isShielded() && !isShieldDestroyed() && !isShieldGoingDown();
			}
		});
		this.targetSelector.addGoal(0, new AnimatableShootGoal(this, LASER_BURST_ATTACK_ID, () -> burstLaserAttackAnim, LASER_FACTORY_BURST, LASER_BURST_OFFSET, 19.6D, 24.4D, 20, 80, 7, 0) {
			private boolean shotSecond = false;

			@Override
			public boolean canUse() {
				return super.canUse() && !isShielded() && !isShieldDestroyed() && !isShieldGoingDown();
			}

			@Override
			public void start() {
				super.start();

				playSound(CASoundEvents.ROBO_WARRIOR_LASER_BURST.get(), 1.0F, 1.0F);

				this.shotSecond = false;
			}

			@Override
			public void tick() {
				super.tick();

				if (MathUtil.isBetween(burstLaserAttackAnim.getWrappedAnimProgress(), actionPointTickStart, actionPointTickEnd) && !hasShotProjectile) {
					CAScreenShakeEntity.shakeScreen(level, position(), 20.0F, 0.074F, 5, 20);
				}

				if (MathUtil.isBetween(burstLaserAttackAnim.getWrappedAnimProgress(), 28.0D, 30.8D) && !shotSecond) {
					if (!shotSecond) {
						if (LASER_FACTORY_BURST.apply(RoboWarriorEntity.this, LASER_BURST_OFFSET) != null) level.addFreshEntity(LASER_FACTORY_BURST.apply(RoboWarriorEntity.this, LASER_BURST_OFFSET));
						CAScreenShakeEntity.shakeScreen(level, position(), 20.0F, 0.074F, 5, 20);
						this.shotSecond = true;
					}
				}
			}
		});
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<VillagerEntity>(this, VillagerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<AnimalEntity>(this, AnimalEntity.class, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(RoboWarriorEntity.class));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HAS_HIT_HEALTH_THRESHOLD, false);
		this.entityData.define(IS_SHIELDED, false);
		this.entityData.define(SHIELD_DESTROYED, false);
		this.entityData.define(SHIELD_DAMAGE, 0);
		this.entityData.define(STORED_DAMAGE, 0);
		this.entityData.define(SHIELD_ACTIVATION_TIME, 0);
		this.entityData.define(SHIELD_ACTIVATION_DAMAGE_THRESHOLD, 90);
	}

	public boolean hasHitHealthThreshold() {
		return this.entityData.get(HAS_HIT_HEALTH_THRESHOLD);
	}

	public void setHasHitHealthThreshold(boolean hasHitHealthThreshold) {
		this.entityData.set(HAS_HIT_HEALTH_THRESHOLD, hasHitHealthThreshold);
	}

	public boolean isShielded() {
		return this.entityData.get(IS_SHIELDED);
	}

	public void setShielded(boolean shielded) {
		this.entityData.set(IS_SHIELDED, shielded);
	}

	public boolean isShieldDestroyed() {
		return this.entityData.get(SHIELD_DESTROYED);
	}

	public void setShieldDestroyed(boolean shieldDestroyed) {
		this.entityData.set(SHIELD_DESTROYED, shieldDestroyed);
	}

	public int getShieldDamage() {
		return this.entityData.get(SHIELD_DAMAGE);
	}

	public void setShieldDamage(int shieldDamage) {
		this.entityData.set(SHIELD_DAMAGE, shieldDamage);
	}

	public int getStoredDamage() {
		return this.entityData.get(STORED_DAMAGE);
	}

	public void setStoredDamage(int storedDamage) {
		this.entityData.set(STORED_DAMAGE, storedDamage);
	}

	public int getShieldActivationTime() {
		return this.entityData.get(SHIELD_ACTIVATION_TIME);
	}

	public void setShieldActivationTime(int shieldActivationTime) {
		this.entityData.set(SHIELD_ACTIVATION_TIME, shieldActivationTime);
	}

	public int getShieldActivationDamageThreshold() {
		return this.entityData.get(SHIELD_ACTIVATION_DAMAGE_THRESHOLD);
	}

	public void setShieldActivationDamageThreshold(int shieldActivationDamageThreshold) {
		this.entityData.set(SHIELD_ACTIVATION_DAMAGE_THRESHOLD, shieldActivationDamageThreshold);
	}

	public boolean isShieldGoingDown() {
		return isPlayingAnimation(shieldDownAnim);
	}

	public boolean isPresumptuouslyAttacking() {
		return isPlayingAnimation(leftUppercutAnim) || isPlayingAnimation(rightUppercutAnim) || isPlayingAnimation(burstLaserAttackAnim) || isPlayingAnimation(chargedLaserAttackAnim);
	}

	@Override
	public int animationInterval() {
		return isShielded() ? 2 : 1;
	}

	@Override
	public void manageAttack(LivingEntity target) {
	}

	@Override
	public boolean isMaxGroupSizeReached(int entityCount) {
		return entityCount >= 2;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public boolean canSee(Entity pEntity) {
		if (pEntity == null) return false;

		boolean directLOS = this.level.clip(new RayTraceContext(position(), pEntity.position(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() == RayTraceResult.Type.MISS;
		return super.canSee(pEntity) || (directLOS
				? pEntity.level == this.level && MathUtil.getHorizontalDistanceBetween(this, pEntity) <= getFollowRange() && MathUtil.getVerticalDistanceBetween(this, pEntity) <= 20
				: pEntity.level == this.level && MathUtil.getHorizontalDistanceBetween(this, pEntity) <= getFollowRange() / 6 && MathUtil.getVerticalDistanceBetween(this, pEntity) <= 14);
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (isShielded()) setShieldDamage((int) (getShieldDamage() + (pAmount / 2)));
		else setStoredDamage((int) (getStoredDamage() + pAmount));

		if (pSource.isBypassInvul()) return super.hurt(pSource, pAmount);

		return !isShielded() && super.hurt(pSource, pAmount);
	}

	@Override
	protected void onSpawn(boolean hasAlreadyDied) {
		if (!hasAlreadyDied && level.isClientSide) {
			SoundUtil.playIdleSoundAsTickable(CASoundEvents.ROBO_WARRIOR_IDLE.get(), this);
			SoundUtil.playWalkingSoundAsTickable(CASoundEvents.ROBO_WARRIOR_WALK.get(), this);
		}
	}

	@Nullable
	@Override
	public Team getTeam() {
		return CATeams.ROBO_TEAM;
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}
	
	@Override
	public float getMeleeAttackReach() {
		return super.getMeleeAttackReach() * 0.75F;
	}

	@Override
	public boolean isAffectedByFluids() {
		return !isShielded();
	}

	@Override
	public boolean canBeKnockedBack() {
		return !isShielded();
	}

	@Override
	public boolean isPushedByFluid() {
		return !isShielded();
	}

	@Override
	public boolean canCollideWith(Entity pEntity) {
		return !isShielded();
	}

	@Override
	public double getMovementThreshold() {
		return super.getMovementThreshold() * 200;
	}

	@Override
	public void tick() {
		super.tick();

		setHasHitHealthThreshold(!isDeadOrDying() && MathUtil.isBetween(getHealth(), 60.0F, 100.0F) || (getHealth() <= 80.0F && lastDamageAmount >= 20.0F) || (getStoredDamage() >= getShieldActivationDamageThreshold()));
		setShieldDestroyed(getShieldDamage() >= 150.0F || isDeadOrDying());

		if (isShielded()) setShieldActivationTime(getShieldActivationTime() + 1);
	}

	@Override
	protected void tickDeath() {
		setShielded(false);
		setStoredDamage(0);
		setShieldDamage(0);
		setShieldDestroyed(false);

		super.tickDeath();
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	public SingletonAnimationBuilder getIdleAnim() {
		return idleAnim;
	}

	@Override
	public SingletonAnimationBuilder getWalkAnim() {
		return walkAnim;
	}

	@Override
	public SingletonAnimationBuilder getDeathAnim() {
		return deathAnim;
	}

	@Override
	public String getOwnerMDFileName() {
		return ROBO_WARRIOR_MDF_NAME;
	}

	@Override
	protected void handleBaseAnimations() {
		playAnimation(idleExtrasAnim, true);

		if (getIdleAnim() != null && !isShielded() && !isShieldDestroyed() && !isShieldGoingDown() && !isAttacking() && !isMoving() && !isDeadOrDying()) playAnimation(idleAnim, false);
		if (getWalkAnim() != null && !isShielded() && !isShieldDestroyed() && !isShieldGoingDown() && isMoving() && !isAttacking() && !isDeadOrDying() && !isOnAttackCooldown()) playAnimation(walkAnim, false);

		if (isPlayingAnimation(shieldedAnim)) {
			setDeltaMovement(0, getDeltaMovement().y, 0);
			EntityUtil.repelEntities(this, getBbWidth() * 1.6D, getBbHeight(), 1);
		}

		if (MathUtil.isBetween(chargedLaserAttackAnim.getWrappedAnimProgress(), 75.5D, 76.6D)) {
			CAScreenShakeEntity.shakeScreen(level, position(), 150.0F, 0.278F, 7, 20);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<RoboWarriorEntity>> getWrappedControllers() {
		return roboWarriorControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return roboWarriorAnimations;
	}
}
