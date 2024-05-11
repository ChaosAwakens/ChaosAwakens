package io.github.chaosawakens.common.entity.boss.insect;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.client.sounds.tickable.boss.insect.HerculesBeetleTickableIdleSound;
import io.github.chaosawakens.client.sounds.tickable.boss.insect.HerculesBeetleTickableWalkSound;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboSniperEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.EnumUtil;
import io.github.chaosawakens.common.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class HerculesBeetleEntity extends AnimatableMonsterEntity { //TODO In 1.20.1 probably since I'm this far in, but make proper use of clean phasing rather than having 1 morbillion different state fields to cycle through
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<HerculesBeetleEntity>> herculesBeetleControllers = new ObjectArrayList<WrappedAnimationController<HerculesBeetleEntity>>(2);
	private final ObjectArrayList<IAnimationBuilder> herculesBeetleAnimations = new ObjectArrayList<IAnimationBuilder>(16);
	private static final DataParameter<Boolean> DOCILE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> AWAKENING = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> ACTIVE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> DEFENSIVE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> OFFENSIVE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> EVASIVE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CRITICAL = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> WALKING = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> DOCILITY_TIME = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> ACTIVITY_TIME = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.INT);
	private final WrappedAnimationController<HerculesBeetleEntity> mainController = createMainMappedController("herculesbeetlemaincontroller");
	private final WrappedAnimationController<HerculesBeetleEntity> attackController = createMappedController("herculesbeetleattackcontroller", this::attackPredicate);
	private final SingletonAnimationBuilder docileAnim = new SingletonAnimationBuilder(this, "Docile", ILoopType.EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder awakenedAnim = new SingletonAnimationBuilder(this, "Awakened", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", ILoopType.EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", ILoopType.EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder flyAnim = new SingletonAnimationBuilder(this, "Fly", ILoopType.EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder deathMidairAnim = new SingletonAnimationBuilder(this, "Death (Midair)", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder deathMidairFallingAnim = new SingletonAnimationBuilder(this, "Death (Midair, Falling)", ILoopType.EDefaultLoopTypes.LOOP).setWrappedController(attackController);
	private final SingletonAnimationBuilder deathFallenAnim = new SingletonAnimationBuilder(this, "Death (Fallen)", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder roarAnim = new SingletonAnimationBuilder(this, "Roar Defense Mechanism", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder ramAttackAnim = new SingletonAnimationBuilder(this, "Ram Attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder grabAnim = new SingletonAnimationBuilder(this, "Grab", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder munchAttackAnim = new SingletonAnimationBuilder(this, "Munch Attack", ILoopType.EDefaultLoopTypes.LOOP).setWrappedController(attackController);
	private final SingletonAnimationBuilder bodySlamAttackAnim = new SingletonAnimationBuilder(this, "Body Slam Attack", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME).setWrappedController(attackController);
	private final SingletonAnimationBuilder bodySlamAttackHitAnim = new SingletonAnimationBuilder(this, "Body Slam Attack (Hit)", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME).setWrappedController(attackController);
	public static final byte RAM_ATTACK_ID = 1;
	public static final byte MUNCH_ATTACK_ID = 2;
	public static final byte BODY_SLAM_ATTACK_ID = 3;
	public static final String HERCULES_BEETLE_MDF_NAME = "hercules_beetle";
	private final EnumUtil.HerculesBeetleType type;

	public HerculesBeetleEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.type = EnumUtil.HerculesBeetleType.MODERN;
	}

	public HerculesBeetleEntity(EntityType<? extends MonsterEntity> type, World worldIn, EnumUtil.HerculesBeetleType beetleType) {
		super(type, worldIn);
		this.type = beetleType;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 250)
				.add(Attributes.ARMOR, 20)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.FLYING_SPEED, 0.37D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
				.add(Attributes.ATTACK_SPEED, 10)
				.add(Attributes.ATTACK_DAMAGE, 25)
				.add(Attributes.ATTACK_KNOCKBACK, 2D)
				.add(Attributes.FOLLOW_RANGE, 40);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public WrappedAnimationController<? extends IAnimatableEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public ObjectArrayList<WrappedAnimationController<HerculesBeetleEntity>> getWrappedControllers() {
		return herculesBeetleControllers;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DOCILE, true);
		this.entityData.define(AWAKENING, false);
		this.entityData.define(ACTIVE, false);
		this.entityData.define(DEFENSIVE, false);
		this.entityData.define(OFFENSIVE, false);
		this.entityData.define(EVASIVE, false);
		this.entityData.define(CRITICAL, false);
		this.entityData.define(WALKING, false);
		this.entityData.define(FLYING, false);
		this.entityData.define(DOCILITY_TIME, 0);
		this.entityData.define(ACTIVITY_TIME, 0);
	}

	public boolean isDocile() {
		return this.entityData.get(DOCILE);
	}

	public void setDocile(boolean isDocile) {
		this.entityData.set(DOCILE, isDocile);
	}

	public boolean isAwakening() {
		return this.entityData.get(AWAKENING);
	}

	public void setAwakening(boolean isAwakening) {
		this.entityData.set(AWAKENING, isAwakening);
	}

	public boolean isActive() {
		return this.entityData.get(ACTIVE);
	}

	public void setActive(boolean isActive) {
		this.entityData.set(ACTIVE, isActive);
	}

	public boolean isDefensive() {
		return this.entityData.get(DEFENSIVE);
	}

	public void setDefensive(boolean isDefensive) {
		this.entityData.set(DEFENSIVE, isDefensive);
	}

	public boolean isOffensive() {
		return this.entityData.get(OFFENSIVE);
	}

	public void setOffensive(boolean isOffensive) {
		this.entityData.set(OFFENSIVE, isOffensive);
	}

	public boolean isEvasive() {
		return this.entityData.get(EVASIVE);
	}

	public void setEvasive(boolean isEvasive) {
		this.entityData.set(EVASIVE, isEvasive);
	}

	public boolean isCritical() {
		return this.entityData.get(CRITICAL);
	}

	public void setCritical(boolean isCritical) {
		this.entityData.set(CRITICAL, isCritical);
	}

	public boolean isWalking() {
		return this.entityData.get(WALKING);
	}

	public void setWalking(boolean isWalking) {
		this.entityData.set(WALKING, isWalking);
	}

	public boolean isFlying() {
		return this.entityData.get(FLYING);
	}

	public void setFlying(boolean isFlying) {
		this.entityData.set(FLYING, isFlying);
	}

	public int getDocilityTime() {
		return this.entityData.get(DOCILITY_TIME);
	}

	public void setDocilityTime(int docilityTime) {
		this.entityData.set(DOCILITY_TIME, docilityTime);
	}

	public void updateDocilityTime(int incrementalValue) {
		this.entityData.set(DOCILITY_TIME, getDocilityTime() + incrementalValue);
	}

	public int getActivityTime() {
		return this.entityData.get(ACTIVITY_TIME);
	}

	public void setActivityTime(int activityTime) {
		this.entityData.set(ACTIVITY_TIME, activityTime);
	}

	public void updateActivityTime(int incrementalValue) {
		this.entityData.set(ACTIVITY_TIME, getActivityTime() + incrementalValue);
	}

	public boolean isActivelyPassivelyWandering() {
		return isActive() && isWalking() && !isFlying() && !isDefensive() && !isOffensive() && !isEvasive() && !isCritical();
	}

	public void setActivelyPassivelyWandering() {
		setActive(true);
		setWalking(true);
		setFlying(false);
		setDefensive(false);
		setOffensive(false);
		setEvasive(false);
		setCritical(false);
	}

	public boolean isActivelyPassivelyFlying() {
		return isActive() && isFlying() && !isWalking() && !isDefensive() && !isOffensive() && !isEvasive() && !isCritical();
	}

	public void setActivelyPassivelyFlying() {
		setActive(true);
		setWalking(false);
		setFlying(true);
		setDefensive(false);
		setOffensive(false);
		setEvasive(false);
		setCritical(false);
	}

	private void resetStatesToBaselineActivity() {
		setActive(true);
		setWalking(isWalking());
		setFlying(isFlying());
		setDefensive(false);
		setDocile(false);
		setEvasive(false);
		setCritical(false);
		setAwakening(false);
		setOffensive(false);
		setDocilityTime(0);
		setActivityTime(0);
	}

	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return herculesBeetleAnimations;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return isDocile() && !isDeadOrDying() ? PlayState.STOP : PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboPounderEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboPounderEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboSniperEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboJefferyEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboWarriorEntity.class, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	@Override
	public int animationInterval() {
		return 2;
	}

	@Override
	public boolean canBeKnockedBack() {
		return false;
	}

	@Override
	protected void tickDeath() {
		resetStatesToBaselineActivity();
		setActive(false);
		setFlying(false);
		setWalking(false);

		super.tickDeath();
	}

	@Override
	public void aiStep() {
		handleStates();
		super.aiStep();
	}

	private void handleStates() {
		handleDocility();
		handleActivity();
	}

	private void handleDocility() {
		boolean hasValidTarget = getTarget() != null && getTarget().isAlive() && (getTarget() instanceof PlayerEntity && getTarget().isCrouching() ? getTarget().distanceTo(getTarget()) <= 8.0F : distanceTo(getTarget()) <= 20.0F);

		if (isDocile()) {
			boolean shouldAwaken = (hurtTime > 0 || hasValidTarget);

			if (shouldAwaken) {
				setDocile(false);
				setDocilityTime(0);

				if (!isDeadOrDying()) setAwakening(true);
			} else {
				updateDocilityTime(1);
				if (tickCount % 20 == 0) heal(5.0F);
			}
		} else if (!isDocile()) {
			boolean shouldGoOffensive = hasValidTarget;
			boolean shouldGoDefensive = hasValidTarget && (lastDamageAmount >= 30.0F || getHealth() <= 200.0F);
			boolean shouldGoEvasive = (hasValidTarget && (lastDamageAmount >= 50.0F || getHealth() <= 175.0F)) || isInLava();
			boolean shouldGoCritical = hasValidTarget && (lastDamageAmount >= 150.0F || getHealth() <= 80.0F);
			boolean shouldFlyAwayPassively = !shouldGoDefensive && (isInLava() || isInWater());

			if (isAwakening()) {
				if (!isPlayingAnimation(awakenedAnim)) {
					playAnimation(awakenedAnim, false);
					playSound(CASoundEvents.HERCULES_BEETLE_AWAKEN.get(), 1.0F, 1.0F);
				} else if (awakenedAnim.hasAnimationFinished()) {
					setAwakening(false);
					setActive(true);

					if (shouldGoOffensive) {
						resetStatesToBaselineActivity(); // Failsafe
						setOffensive(true);
					} else if (shouldGoDefensive) {
						resetStatesToBaselineActivity();
						setDefensive(true);
					} else if (shouldGoEvasive) {
						resetStatesToBaselineActivity();
						setEvasive(true);
					} else if (shouldGoCritical) {
						resetStatesToBaselineActivity();
						setCritical(true);
					} else if (shouldFlyAwayPassively) setActivelyPassivelyFlying();
					else setActivelyPassivelyWandering();
				}
			}
		}
	}

	private void handleActivity() {
		if (isActive()) {
			updateActivityTime(1);
			handleBattleStates();

			boolean hasValidTarget = getTarget() != null && getTarget().isAlive() && distanceTo(getTarget()) <= getFollowRange();

			if (isOnGround() && !hasValidTarget && ((getActivityTime() > 500 && getActivityTime() % MathHelper.nextInt(random, 10, getActivityTime() * 2) == 0) || (getActivityTime() > 1000 && getHealth() < 100.0F)) && !isDeadOrDying()) {
				resetStatesToBaselineActivity();
				setActive(false);
				setFlying(false);
				setWalking(true);
				setDocile(true);
				setActivityTime(0);
			}
		}
	}

	private void handleBattleStates() {

	}

	@Override
	protected void onSpawn(boolean hasAlreadyDied) {
		if (!hasAlreadyDied && level.isClientSide) {
			Minecraft.getInstance().getSoundManager().queueTickingSound(new HerculesBeetleTickableIdleSound(CASoundEvents.HERCULES_BEETLE_IDLE.get(), this).setDocilitySound(new HerculesBeetleTickableIdleSound(CASoundEvents.HERCULES_BEETLE_DOCILE.get(), this)));
			Minecraft.getInstance().getSoundManager().queueTickingSound(new HerculesBeetleTickableWalkSound(CASoundEvents.HERCULES_BEETLE_WALK.get(), this).setFlyingSound(new HerculesBeetleTickableWalkSound(CASoundEvents.HERCULES_BEETLE_FLY.get(), this)));
		}
	}

	@Override
	public void manageAttack(LivingEntity target) {
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		SoundEvent criticalDamageSound = CASoundEvents.HERCULES_BEETLE_DAMAGE_CRITICAL.get();
		SoundEvent evasiveDamageSound = CASoundEvents.HERCULES_BEETLE_DAMAGE_EVASIVE.get();
		SoundEvent defenseDamageSound = CASoundEvents.HERCULES_BEETLE_DAMAGE_DEFENSE.get();
		SoundEvent offenseDamageSound = CASoundEvents.HERCULES_BEETLE_DAMAGE_OFFENSE.get();

		return isCritical() ? criticalDamageSound : (isEvasive() ? evasiveDamageSound : (isDefensive() ? defenseDamageSound : offenseDamageSound));
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.HERCULES_BEETLE_DEATH.get();
	}

	@Nullable
	@Override
	public IAnimationBuilder getIdleAnim() {
		return idleAnim;
	}

	@Nullable
	@Override
	public IAnimationBuilder getWalkAnim() {
		return walkAnim;
	}

	@Nullable
	@Override
	public IAnimationBuilder getDeathAnim() {
		return deathAnim;
	}

	@Override
	public String getOwnerMDFileName() {
		return HERCULES_BEETLE_MDF_NAME;
	}

	@Override
	protected void handleBaseAnimations() {
		if (isDocile() && !isDeadOrDying()) playAnimation(docileAnim, true);

		if (isActivelyPassivelyWandering() && !isMoving()) playAnimation(idleAnim, true);
		else if (isActivelyPassivelyWandering() && isMoving()) playAnimation(walkAnim, false);

		if (isFlying()) playAnimation(flyAnim, false);

		if (isPlayingAnimation(awakenedAnim)) {
			if (MathUtil.isBetween(awakenedAnim.getWrappedAnimProgress(), 60, 90)) CAScreenShakeEntity.shakeScreen(level, position(), 440F, (float) (awakenedAnim.getWrappedAnimProgress() / 100F) / 6, 2, 210);
			if (MathUtil.isBetween(awakenedAnim.getWrappedAnimProgress(), 90, 140)) CAScreenShakeEntity.shakeScreen(level, position(), 340F, (float) (awakenedAnim.getWrappedAnimProgress() / 100F) / 18, 2, 210);
			if (MathUtil.isBetween(awakenedAnim.getWrappedAnimProgress(), 60, 140)) EntityUtil.repelEntities(this, 10, 8, MathHelper.clamp((awakenedAnim.getWrappedAnimProgress() / 100F) / 6, 0.1F, 0.35F));
		}
	}
}
