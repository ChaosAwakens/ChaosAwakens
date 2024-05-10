package io.github.chaosawakens.common.entity.hostile.robo;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.registry.CATeams;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class RoboWarriorEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<RoboWarriorEntity>> roboWarriorControllers = new ObjectArrayList<WrappedAnimationController<RoboWarriorEntity>>(4);
	private final ObjectArrayList<IAnimationBuilder> roboWarriorAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private static final DataParameter<Boolean> CAN_ACTIVATE_SHIELD = EntityDataManager.defineId(RoboWarriorEntity.class, DataSerializers.BOOLEAN);
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
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(shieldController);
	private final SingletonAnimationBuilder idleExtrasAnim = new SingletonAnimationBuilder(this, "Idle Extras", EDefaultLoopTypes.LOOP).setWrappedController(ambienceController);
	private final SingletonAnimationBuilder leftUppercutAnim = new SingletonAnimationBuilder(this, "Left Uppercut Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder rightUppercutAnim = new SingletonAnimationBuilder(this, "Right Uppercut Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder shieldUpAnim = new SingletonAnimationBuilder(this, "Activate Shield", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(shieldController);
	private final SingletonAnimationBuilder shieldedAnim = new SingletonAnimationBuilder(this, "Shield Up", EDefaultLoopTypes.LOOP).setWrappedController(shieldController);
	private final SingletonAnimationBuilder shieldDownAnim = new SingletonAnimationBuilder(this, "Deactivate Shield", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(shieldController);
	private final SingletonAnimationBuilder shieldDestroyedAnim = new SingletonAnimationBuilder(this, "Destroy Shield", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(shieldController);
	private static final byte UPPERCUT_ATTACK_ID = 1;
	public static final String ROBO_WARRIOR_MDF_NAME = "robo_warrior";
	
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
		if (isAttacking() || isShielded() || isShieldDestroyed() || !hasShieldGoneDown() || isDeadOrDying()) return PlayState.STOP;
		else return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState ambiencePredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return isDeadOrDying() || isShielded() || isShieldDestroyed() || !hasShieldGoneDown() ? PlayState.STOP : PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState shieldPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1, 3) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isShielded() && !isShieldDestroyed() && hasShieldGoneDown();
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !isShielded() && !isShieldDestroyed() && hasShieldGoneDown();
			}
		});
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, null, UPPERCUT_ATTACK_ID, 16D, 18.4D, 80.0D, 1, 0, (owner) -> !isShielded() && !isShieldDestroyed() && hasShieldGoneDown()).pickBetweenAnimations(() -> leftUppercutAnim, () -> rightUppercutAnim));
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
		this.entityData.define(CAN_ACTIVATE_SHIELD, false);
	}

	public boolean canActivateShield() {
		return this.entityData.get(CAN_ACTIVATE_SHIELD);
	}

	public void setCanActivateShield(boolean canActivateShield) {
		this.entityData.set(CAN_ACTIVATE_SHIELD, canActivateShield);
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

	public boolean hasShieldGoneDown() {
		return !isPlayingAnimation(shieldDownAnim) || shieldDownAnim.hasAnimationFinished();
	}

	@Override
	public int animationInterval() {
		return isShielded() ? 2 : 1;
	}

	@Override
	public void manageAttack(LivingEntity target) {
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (isShielded()) setShieldDamage((int) (getShieldDamage() + (pAmount / 2)));
		else setStoredDamage((int) (getStoredDamage() + pAmount));

		if (pSource.isBypassInvul()) return super.hurt(pSource, pAmount);

		return !isShielded() && super.hurt(pSource, pAmount);
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
	public float getMeleeAttackReach(LivingEntity target) {
		return super.getMeleeAttackReach(target) * 0.75F;
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
		handleShield();
	}

	protected void handleShield() {
		if (MathUtil.isBetween(getHealth(), 150.0F, getMaxHealth())) setHasHitHealthThreshold(true);

		setCanActivateShield(!isDeadOrDying() && getHealth() <= 90.0F && !isAttacking() && !isShielded() && hasHitHealthThreshold() && getStoredDamage() >= getShieldActivationDamageThreshold());

	/*	ChaosAwakens.debug("", "--------------------------------------------------------------");
		ChaosAwakens.debug("Can Activate Shield", canActivateShield());
		ChaosAwakens.debug("Has Hit Health Threshold", hasHitHealthThreshold());
		ChaosAwakens.debug("Is Shielded", isShielded());
		ChaosAwakens.debug("Shield Activation Time", getShieldActivationTime());
		ChaosAwakens.debug("Shield Activation Damage Threshold", getShieldActivationDamageThreshold());
		ChaosAwakens.debug("Stored Damage", getStoredDamage());
		ChaosAwakens.debug("Shield Damage", getShieldDamage());
		ChaosAwakens.debug("Shield Destroyed", isShieldDestroyed());
		ChaosAwakens.debug("", "--------------------------------------------------------------"); */

		if (canActivateShield()) {
			playAnimation(shieldUpAnim, false);
			setShieldDestroyed(false);
			setStoredDamage(0);
			setShieldActivationTime(0);
			setHasHitHealthThreshold(false);
			setShielded(true);
		}

		if (isShielded() && !isPlayingAnimation(shieldUpAnim)) {
			playAnimation(shieldedAnim, false);
			EntityUtil.repelEntitiesOfClass(this, Entity.class, getType().getWidth() * 1.65D, getType().getHeight(), 0.1D);
			heal(0.5F);
			setShieldActivationTime(getShieldActivationTime() + 1);

			if (getHealth() >= getMaxHealth()) {
				playAnimation(shieldDownAnim, false);
				setShieldDestroyed(false);
				setShielded(false);
				setHasHitHealthThreshold(false);
				setShieldActivationTime(0);
				setShieldDamage(0);
			}

			if (getShieldDamage() >= 90 || isDeadOrDying()) {
				setShieldDestroyed(true);
				setHasHitHealthThreshold(false);
				setShielded(false);
				setShieldActivationTime(0);
				setShieldDamage(0);
			}
		}

		if (isShieldDestroyed()) playAnimation(shieldDestroyedAnim, false); // They both need to be in separate conditions otherwise they don't work as intended :skull:
		if (shieldDestroyedAnim.hasAnimationFinished() || !isPlayingAnimation(shieldDestroyedAnim) || isDeadOrDying()) setShieldDestroyed(false);
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

		if (getIdleAnim() != null && !isShielded() && !isShieldDestroyed() && hasShieldGoneDown() && !isAttacking() && !isMoving() && !isDeadOrDying()) playAnimation(idleAnim, false);
		if (getWalkAnim() != null && !isShielded() && !isShieldDestroyed() && hasShieldGoneDown() && isMoving() && !isAttacking() && !isDeadOrDying()) playAnimation(walkAnim, false);
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
