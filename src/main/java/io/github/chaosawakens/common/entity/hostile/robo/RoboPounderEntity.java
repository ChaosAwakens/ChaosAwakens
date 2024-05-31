package io.github.chaosawakens.common.entity.hostile.robo;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.client.sounds.tickable.robo.robopounder.RoboPounderTickableIdleSound;
import io.github.chaosawakens.client.sounds.tickable.robo.robopounder.RoboPounderTickableWalkSound;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableAOEGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.robo.robopounder.RoboPounderDysonDashGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.robo.robopounder.RoboPounderRageRunGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.registry.CATeams;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class RoboPounderEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<RoboPounderEntity>> roboPounderControllers = new ObjectArrayList<WrappedAnimationController<RoboPounderEntity>>(2);
	private final ObjectArrayList<IAnimationBuilder> roboPounderAnimations = new ObjectArrayList<IAnimationBuilder>(18);
	private static final DataParameter<Boolean> SHOULD_TAUNT = EntityDataManager.defineId(RoboPounderEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_RAGE_RUNNING = EntityDataManager.defineId(RoboPounderEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> RAGE_RUN_DURATION = EntityDataManager.defineId(RoboPounderEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> TARGET_SHIELD_BLOCKS = EntityDataManager.defineId(RoboPounderEntity.class, DataSerializers.INT);
	private final WrappedAnimationController<RoboPounderEntity> mainController = createMainMappedController("robopoundermaincontroller");
	private final WrappedAnimationController<RoboPounderEntity> attackController = createMappedController("robopounderattackcontroller", this::attackPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController).setAnimSpeed(1.15D);
	private final SingletonAnimationBuilder tauntAnim = new SingletonAnimationBuilder(this, "Taunt", EDefaultLoopTypes.PLAY_ONCE);
	private final SingletonAnimationBuilder rightPunchAnim = new SingletonAnimationBuilder(this, "Right Heavy Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder leftPunchAnim = new SingletonAnimationBuilder(this, "Left Heavy Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder rightSwingAnim = new SingletonAnimationBuilder(this, "Right Swing Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder leftSwingAnim = new SingletonAnimationBuilder(this, "Left Swing Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder dashAttackAnim = new SingletonAnimationBuilder(this, "Dash Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController).setAnimSpeed(0.75F);
	private final SingletonAnimationBuilder rightStompAnim = new SingletonAnimationBuilder(this, "Right Leg Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder leftStompAnim = new SingletonAnimationBuilder(this, "Left Leg Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder groundSlamAnim = new SingletonAnimationBuilder(this, "Heavy AoE Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder rageBeginAnim = new SingletonAnimationBuilder(this, "Rage Begin", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder rageRunAnim = new SingletonAnimationBuilder(this, "Rage Run", EDefaultLoopTypes.LOOP).setWrappedController(attackController).setLoopType(EDefaultLoopTypes.HOLD_ON_LAST_FRAME);
	private final SingletonAnimationBuilder rageCooldownAnim = new SingletonAnimationBuilder(this, "Cooldown", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder rageCrashAnim = new SingletonAnimationBuilder(this, "Rage Crash", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder rageCrashRestartAnim = new SingletonAnimationBuilder(this, "Rage Crash Restart", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder cooldownRestartAnim = new SingletonAnimationBuilder(this, "Cooldown Restart", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	public static final byte PUNCH_ATTACK_ID = 1;
    public static final byte SWING_ATTACK_ID = 2;
    public static final byte DASH_ATTACK_ID = 3;
    public static final byte STOMP_ATTACK_ID = 4;
    public static final byte GROUND_SLAM_ATTACK_ID = 5;
    public static final byte RAGE_RUN_ATTACK_ID = 6;
	private final ObjectArrayList<LivingEntity> potentialDeadTargets = new ObjectArrayList<LivingEntity>();
	public static final String ROBO_POUNDER_MDF_NAME = "robo_pounder";

	public RoboPounderEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 300)
				.add(Attributes.ARMOR, 20)
				.add(Attributes.ARMOR_TOUGHNESS, 8)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 360.0D)
				.add(Attributes.ATTACK_DAMAGE, 20)
				.add(Attributes.ATTACK_KNOCKBACK, 10)
				.add(Attributes.FOLLOW_RANGE, 60);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public WrappedAnimationController<? extends AnimatableMonsterEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		if (isAttacking() || isDeadOrDying()) playAnimation(idleAnim, true);
		return PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public int animationInterval() {
		return isRageRunning() ? 1 : 2;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerGoals() {
		float defaultAttackVoicePitch = getHealth() <= 50.0F ? 1.25F : 1.0F; //TODO Proper updates in goals

/*		this.goalSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1, 3) {
			@Override
			public boolean canUse() {
				return super.canUse() && !shouldTaunt();
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !shouldTaunt();
			}
		}); */
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, null, PUNCH_ATTACK_ID, 14.2D, 17.3D, 95.0D, 3, 10, (owner) -> EntityUtil.getAllEntitiesAround(owner, 6.0D, 6.0D, 6.0D, 6.0D).size() <= 6).pickBetweenAnimations(() -> leftPunchAnim, () -> rightPunchAnim).soundOnStart(CASoundEvents.ROBO_POUNDER_PISTON_PUNCH, defaultAttackVoicePitch));
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, null, SWING_ATTACK_ID, 12D, 14D, 245.0D, 3, 10, (owner) -> EntityUtil.getAllEntitiesAround(owner, 6.0D, 6.0D, 6.0D, 6.0D).size() >= 3 && EntityUtil.getAllEntitiesAround(owner, 6.0D, 6.0D, 6.0D, 6.0D).size() <= 8).pickBetweenAnimations(() -> leftSwingAnim, () -> rightSwingAnim).soundOnStart(CASoundEvents.ROBO_POUNDER_SIDE_SWEEP, defaultAttackVoicePitch));
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, null, SWING_ATTACK_ID, 12D, 14D, 245.0D, 5, 50).pickBetweenAnimations(() -> leftSwingAnim, () -> rightSwingAnim).soundOnStart(CASoundEvents.ROBO_POUNDER_SIDE_SWEEP, defaultAttackVoicePitch));
		this.targetSelector.addGoal(0, new RoboPounderDysonDashGoal(this, () -> dashAttackAnim, DASH_ATTACK_ID, 9.6D, 25.4D, 360.0D, 5, 450, 5.0D).soundOnStart(CASoundEvents.ROBO_POUNDER_DYSON_DASH, defaultAttackVoicePitch));
		this.targetSelector.addGoal(0, new RoboPounderRageRunGoal(this, () -> rageBeginAnim, () -> rageRunAnim, () -> rageCooldownAnim, () -> cooldownRestartAnim, () -> rageCrashAnim, () -> rageCrashRestartAnim, RAGE_RUN_ATTACK_ID, 400, 5));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, null, STOMP_ATTACK_ID, 10.4D, 14.7D, 4.0D, 6, 2, true, false, true, 10).pickBetweenAnimations(() -> leftStompAnim, () -> rightStompAnim).soundOnStart(CASoundEvents.ROBO_POUNDER_DOME_STOMP, defaultAttackVoicePitch));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, () -> groundSlamAnim, GROUND_SLAM_ATTACK_ID, 11.6D, 17.5D, 6.0D, 10, 2, true, false, true, 15).soundOnStart(CASoundEvents.ROBO_POUNDER_GROUND_SLAM, defaultAttackVoicePitch));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, null, STOMP_ATTACK_ID, 10.4D, 14.7D, 4.0D, 1, 6, true, false, true, 40).pickBetweenAnimations(() -> leftStompAnim, () -> rightStompAnim).soundOnStart(CASoundEvents.ROBO_POUNDER_DOME_STOMP, defaultAttackVoicePitch));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, () -> groundSlamAnim, GROUND_SLAM_ATTACK_ID, 11.4D, 17.5D, 6.0D, 1, 8, true, false, true, 60).soundOnStart(CASoundEvents.ROBO_POUNDER_GROUND_SLAM, defaultAttackVoicePitch));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, null, STOMP_ATTACK_ID, 10.4D, 14.7D, 4.0D, 2, true, false, true, 40, (owner) -> owner.getTarget() != null && owner.getTarget().getMaxHealth() >= 150.0F).pickBetweenAnimations(() -> leftStompAnim, () -> rightStompAnim).soundOnStart(CASoundEvents.ROBO_POUNDER_DOME_STOMP, defaultAttackVoicePitch));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, () -> groundSlamAnim, GROUND_SLAM_ATTACK_ID, 11.4D, 17.5D, 6.0D, 3, true, false, true, 60, (owner) -> owner.getTarget() != null && owner.getTarget().getMaxHealth() >= 150.0F).soundOnStart(CASoundEvents.ROBO_POUNDER_GROUND_SLAM, defaultAttackVoicePitch));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<VillagerEntity>(this, VillagerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<AnimalEntity>(this, AnimalEntity.class, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SHOULD_TAUNT, false);
		this.entityData.define(IS_RAGE_RUNNING, false);
		this.entityData.define(RAGE_RUN_DURATION, 0);
		this.entityData.define(TARGET_SHIELD_BLOCKS, 0);
	}

	public boolean shouldTaunt() {
		return this.entityData.get(SHOULD_TAUNT);
	}

	public void setShouldTaunt(boolean shouldTaunt) {
		this.entityData.set(SHOULD_TAUNT, shouldTaunt);
	}

	public boolean isRageRunning() {
		return this.entityData.get(IS_RAGE_RUNNING);
	}

	public void setRageRunning(boolean isRageRunning) {
		this.entityData.set(IS_RAGE_RUNNING, isRageRunning);
	}

	public int getRageRunDuration() {
		return this.entityData.get(RAGE_RUN_DURATION);
	}

	public void setRageRunDuration(int rageRunDur) {
		this.entityData.set(RAGE_RUN_DURATION, rageRunDur);
	}

	public void updateRageRunDuration(int amountToDecrement) {
		setRageRunDuration(getRageRunDuration() - amountToDecrement);
	}

	public void updateRageRunDuration() {
		updateRageRunDuration(1);
	}

	public int getTargetShieldBlocks() {
		return this.entityData.get(TARGET_SHIELD_BLOCKS);
	}

	public void setTargetShieldBlocks(int targetShieldBlocks) {
		this.entityData.set(TARGET_SHIELD_BLOCKS, targetShieldBlocks);
	}

	public void updateTargetShieldBlocks(int targetShieldBlocks) {
		setTargetShieldBlocks(getTargetShieldBlocks() + targetShieldBlocks);
	}

	public void updateTargetShieldBlocks() {
		updateTargetShieldBlocks(1);
	}

	public boolean shouldRageRunBasedOnChance() {
		if (MathUtil.isBetween(getHealth(), 250.0F, getMaxHealth() - 1)) return Math.random() < 0.35D;
		else if (MathUtil.isBetween(getHealth(), 200.0F, 250.0F)) return Math.random() < 0.45D;
		else if (MathUtil.isBetween(getHealth(), 150.0F, 200.0F)) return Math.random() < 0.5D;
		else if (MathUtil.isBetween(getHealth(), 100.0F, 150.0F)) return Math.random() < 0.65D;
		else if (MathUtil.isBetween(getHealth(), 50.0F, 100.0F)) return Math.random() < 0.7D;
		else if (getHealth() <= 20.0F) return Math.random() < 0.8D;
		else return Math.random() < 0.3D;
	}

	public int getRageRunDurationStaged() {
		if (MathUtil.isBetween(getHealth(), 250.0F, getMaxHealth() - 1)) return MathHelper.nextInt(random, 300, 550);
		else if (MathUtil.isBetween(getHealth(), 200.0F, 250.0F)) return MathHelper.nextInt(random, 275, 500);
		else if (MathUtil.isBetween(getHealth(), 150.0F, 200.0F)) return MathHelper.nextInt(random, 225, 450);
		else if (MathUtil.isBetween(getHealth(), 100.0F, 150.0F)) return MathHelper.nextInt(random, 200, 400);
		else if (MathUtil.isBetween(getHealth(), 50.0F, 100.0F)) return MathHelper.nextInt(random, 150, 300);
		else if (getHealth() <= 20.0F) return MathHelper.nextInt(random, 100, 200);
		else return MathHelper.nextInt(random, 350, 700);
	}
	
	public int getRageRunFrictionOffset() {
		if (MathUtil.isBetween(getHealth(), 250.0F, getMaxHealth() - 1)) return MathHelper.nextInt(random, 4, 8);
		else if (MathUtil.isBetween(getHealth(), 200.0F, 250.0F)) return MathHelper.nextInt(random, 4, 9);
		else if (MathUtil.isBetween(getHealth(), 150.0F, 200.0F)) return MathHelper.nextInt(random, 4, 9);
		else if (MathUtil.isBetween(getHealth(), 100.0F, 150.0F)) return MathHelper.nextInt(random, 4, 10);
		else if (MathUtil.isBetween(getHealth(), 50.0F, 100.0F)) return MathHelper.nextInt(random, 5, 10);
		else if (getHealth() <= 20.0F) return MathHelper.nextInt(random, 5, 10);
		else return MathHelper.nextInt(random, 6, 12);
	}

	public double getRageRunSpeed() {
		if (MathUtil.isBetween(getHealth(), 250.0F, getMaxHealth() - 1)) return 0.56D;
		else if (MathUtil.isBetween(getHealth(), 200.0F, 250.0F)) return 0.58D;
		else if (MathUtil.isBetween(getHealth(), 150.0F, 200.0F)) return 0.6D;
		else if (MathUtil.isBetween(getHealth(), 100.0F, 150.0F)) return 0.62D;
		else if (MathUtil.isBetween(getHealth(), 50.0F, 100.0F)) return 0.64D;
		else if (getHealth() <= 20.0F) return 0.66D;
		else return 0.55D;
	}

	public double getRageRunAttackDamage() {
		if (MathUtil.isBetween(getHealth(), 250.0F, getMaxHealth() - 1)) return 26.0D;
		else if (MathUtil.isBetween(getHealth(), 200.0F, 250.0F)) return 27.0D;
		else if (MathUtil.isBetween(getHealth(), 150.0F, 200.0F)) return 28.0D;
		else if (MathUtil.isBetween(getHealth(), 100.0F, 150.0F)) return 29.0D;
		else if (MathUtil.isBetween(getHealth(), 50.0F, 100.0F)) return 30.0D;
		else if (getHealth() <= 20.0F) return 35.0D;
		else return 25.0D;
	}

	public double getRageRunArmor() {
		if (MathUtil.isBetween(getHealth(), 250.0F, getMaxHealth() - 1)) return 24.0D;
		else if (MathUtil.isBetween(getHealth(), 200.0F, 250.0F)) return 25.0D;
		else if (MathUtil.isBetween(getHealth(), 150.0F, 200.0F)) return 26.0D;
		else if (MathUtil.isBetween(getHealth(), 100.0F, 150.0F)) return 27.0D;
		else if (MathUtil.isBetween(getHealth(), 50.0F, 100.0F)) return 28.0D;
		else if (getHealth() <= 20.0F) return 30.0D;
		else return 22.0D;
	}

	public double getRageRunArmorToughness() {
		if (MathUtil.isBetween(getHealth(), 250.0F, getMaxHealth() - 1)) return 8.0D;
		else if (MathUtil.isBetween(getHealth(), 200.0F, 250.0F)) return 9.0D;
		else if (MathUtil.isBetween(getHealth(), 150.0F, 200.0F)) return 9.0D;
		else if (MathUtil.isBetween(getHealth(), 100.0F, 150.0F)) return 10.0D;
		else if (MathUtil.isBetween(getHealth(), 50.0F, 100.0F)) return 10.0D;
		else if (getHealth() <= 20.0F) return 12.0D;
		else return 7.0D;
	}

	public int getRageRunDeflectionPower() {
		if (MathUtil.isBetween(getHealth(), 250.0F, getMaxHealth() - 1)) return 6;
		else if (MathUtil.isBetween(getHealth(), 200.0F, 250.0F)) return 7;
		else if (MathUtil.isBetween(getHealth(), 150.0F, 200.0F)) return 7;
		else if (MathUtil.isBetween(getHealth(), 100.0F, 150.0F)) return 8;
		else if (MathUtil.isBetween(getHealth(), 50.0F, 100.0F)) return 9;
		else if (getHealth() <= 20.0F) return 10;
		else return 5;
	}

	@Override
	public void aiStep() {
		super.aiStep();

		handleRageRun();
		handleTaunting();
		handleIntervalSounds();
	}

	private void handleRageRun() {
		if (isRageRunning() && !isDeadOrDying()) {
			setRageRunAttributes();
			if (getRageRunDuration() == 0) setRageRunDuration(getRageRunDurationStaged());
			updateRageRunDuration();
		} else setRageRunDuration(0);
	}
	
	public void setRageRunAttributes() {
		setArmor(getRageRunArmor());
		setArmorToughness(getRageRunArmorToughness());
		setMovementSpeed(getRageRunSpeed());
		setAttackDamage(getRageRunAttackDamage());
	}

	public void resetAttributes() {
		setArmor(20.0D);
		setArmorToughness(6.0D);
		setMovementSpeed(0.25D);
		setAttackDamage(20.0D);
	}

	private void deflectProjectiles(DamageSource hurtSource) {
		EntityUtil.repelEntitiesOfClass(this, ProjectileEntity.class, getBbWidth(), getBbHeight(), getRageRunDeflectionPower());
	}

	private void handleTaunting() {
		final int killThreshold = MathHelper.nextInt(random, 4, 8);  //TODO Move to a cached field
		boolean shouldTaunt = !potentialDeadTargets.isEmpty() && !isAttacking() && !isOnAttackCooldown() && isPlayingAnimation(idleAnim) && (potentialDeadTargets.size() >= killThreshold || ((potentialDeadTargets.get(potentialDeadTargets.size() - 1) instanceof PlayerEntity || potentialDeadTargets.get(potentialDeadTargets.size() - 1).getMaxHealth() >= 150) && this.random.nextBoolean())) && getTarget() == null;
		boolean playTauntSound = false;

		if (shouldTaunt) {
			setShouldTaunt(true);
			getNavigation().stop();
			EntityUtil.freezeEntityRotation(this);
			potentialDeadTargets.clear();

            playTauntSound = true;
		}

		if (playTauntSound) {
			playSound(CASoundEvents.ROBO_POUNDER_TAUNT.get(), 1.0F, getVoicePitch());

			playTauntSound = false;
		}
		if (tauntAnim.hasAnimationFinished() || (getTarget() != null && distanceTo(getTarget()) <= 15.0D) || isDeadOrDying()) setShouldTaunt(false);
	}

	private void handleIntervalSounds() {
	}

	@Override
	public void manageAttack(LivingEntity target) {
		if (!potentialDeadTargets.contains(target)) potentialDeadTargets.add(target);

		switch (getAttackID()) {
		default:
			resetAttributes();
			break;
		case SWING_ATTACK_ID:
			setAttackDamage(12);
			break;
		case STOMP_ATTACK_ID:
			setAttackDamage(15);
			break;
		case RAGE_RUN_ATTACK_ID:
			setRageRunAttributes();
			if (target instanceof PlayerEntity) EntityUtil.disableShield((PlayerEntity) target, 20);
			break;
		case DASH_ATTACK_ID:
			setAttackDamage(25);
			if (target instanceof PlayerEntity) EntityUtil.disableShield((PlayerEntity) target, 300);
			break;
		}
	}

	@Override
	public boolean canSee(Entity pEntity) {
		return MathUtil.getHorizontalDistanceBetween(this, pEntity) <= getFollowRange() && MathUtil.getVerticalDistanceBetween(this, pEntity) <= getFollowRange() / 4;
	}

	@Override
	protected void blockedByShield(LivingEntity pDefender) {		
		if (getTargetShieldBlocks() > 25) {
			if (pDefender instanceof PlayerEntity) EntityUtil.disableShield((PlayerEntity) pDefender, 100);
			setTargetShieldBlocks(0);
			return;
		} else if (pDefender instanceof PlayerEntity && !EntityUtil.isItemOnCooldown((PlayerEntity) pDefender, Items.SHIELD)) updateTargetShieldBlocks();

		switch (getAttackID()) {
		case GROUND_SLAM_ATTACK_ID:
			if (pDefender instanceof PlayerEntity) EntityUtil.disableShield((PlayerEntity) pDefender, 200);
			break;
		}
	}

	@Override
	protected void divertTarget() {
		if (!isRageRunning()) super.divertTarget();
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (pSource != null && isRageRunning() && pSource.isProjectile()) {
			deflectProjectiles(pSource);
			return false;
		}

		return super.hurt(pSource, pAmount);
	}

	@Override
	protected void onSpawn(boolean hasAlreadyDied) {
		if (!hasAlreadyDied && level.isClientSide) {
			Minecraft.getInstance().getSoundManager().queueTickingSound(new RoboPounderTickableIdleSound(CASoundEvents.ROBO_POUNDER_IDLE.get(), this).setCriticalSound(new RoboPounderTickableIdleSound(random.nextBoolean() ? CASoundEvents.ROBO_POUNDER_CRITICAL_DAMAGE.get() : CASoundEvents.ROBO_POUNDER_CRITICAL_DAMAGE_RADIO.get(), this)));
			Minecraft.getInstance().getSoundManager().queueTickingSound(new RoboPounderTickableWalkSound(CASoundEvents.ROBO_POUNDER_WALK.get(), this).setRageRunSound(new RoboPounderTickableWalkSound(CASoundEvents.ROBO_POUNDER_RAGE_RUN.get(), this)));
		}
	}

	@Override
	protected void tickDeath() {
		super.tickDeath();

		if (MathUtil.isBetween(deathAnim.getWrappedAnimProgress(), 29.74D, 32.8D)) CAScreenShakeEntity.shakeScreen(level, position(), 20.0F, 0.074F, 5, 20);
		if (MathUtil.isBetween(deathAnim.getWrappedAnimProgress(), 52.2D, 54.6D)) CAScreenShakeEntity.shakeScreen(level, position(), 15.0F, 0.064F, 4, 18);
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	protected float getStandingEyeHeight(Pose pPose, EntitySize pSize) {
		return pSize.height * 0.85F + 0.6F;
	}

	@Override
	protected void jumpFromGround() {
	}

	@Nullable
	@Override
	public Team getTeam() {
		return CATeams.ROBO_TEAM;
	}

	@Override
	public float getMeleeAttackReach(LivingEntity target) {
		switch (getAttackID()) {
		default: return super.getMeleeAttackReach(target);
		case SWING_ATTACK_ID: return super.getMeleeAttackReach(target) * 0.8F;
		case DASH_ATTACK_ID: return super.getMeleeAttackReach(target) * 1.2F;
		case RAGE_RUN_ATTACK_ID: return super.getMeleeAttackReach(target) * 0.65F;
		}
	}

	@Override
	public int getHeadRotSpeed() {
		return isRageRunning() ? 360 : super.getHeadRotSpeed();
	}

	@Override
	public int getMaxHeadXRot() {
		return isRageRunning() ? 360 : super.getMaxHeadXRot();
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	public boolean isPersistenceRequired() {
		return true;
	}

	@Override
	public boolean canBeKnockedBack() {
		return false;
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

	@Override
	public boolean isAffectedByFluids() {
		return false;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean canCollideWith(Entity pEntity) {
		return isRageRunning() ? false : super.canCollideWith(pEntity);
	}

	@Override
	protected float getSoundVolume() {
		return super.getSoundVolume() + 0.5F;
	}

	@Override
	protected float getVoicePitch() {
		return MathHelper.clamp(super.getVoicePitch(), 1.0F, 1.02F);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		SoundEvent standardDamageSound = CASoundEvents.ROBO_POUNDER_DAMAGE_V1.get();
		SoundEvent transitionDamageSound = CASoundEvents.ROBO_POUNDER_DAMAGE_V4.get();
		SoundEvent rageRunDamageSound = CASoundEvents.ROBO_POUNDER_DAMAGE_V3.get();
		SoundEvent halfHealthDamageSound = CASoundEvents.ROBO_POUNDER_DAMAGE_V2.get();
		SoundEvent defaultDamageSound = isRageRunning() ? rageRunDamageSound : standardDamageSound;
		boolean hasPlayedTransitionDamageSound = false;
		boolean hasPlayedHalfHealthDamageSound = false;
		float updatedHealth = getHealth() - getLastDamageAmount();

		if (updatedHealth <= 50.0F && !hasPlayedTransitionDamageSound) {
			hasPlayedTransitionDamageSound = true;

			return transitionDamageSound;
		} else if (getHealth() > 50.0F && hasPlayedTransitionDamageSound) {
			hasPlayedTransitionDamageSound = false;

			return defaultDamageSound;
		}

		if (updatedHealth <= getMaxHealth() / 2 && !hasPlayedHalfHealthDamageSound) {
			hasPlayedHalfHealthDamageSound = true;

			return halfHealthDamageSound;
		} else if (getHealth() > getMaxHealth() / 2 && hasPlayedHalfHealthDamageSound) {
			hasPlayedHalfHealthDamageSound = false;

			return defaultDamageSound;
		}

		return defaultDamageSound;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.ROBO_POUNDER_DEATH.get();
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
		return ROBO_POUNDER_MDF_NAME;
	}

	@Override
	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isAttacking() && !isMoving() && !shouldTaunt() && !isDeadOrDying()) playAnimation(getIdleAnim(), true);
		if (getWalkAnim() != null && isMoving() && !isAttacking() && !shouldTaunt() && !isDeadOrDying()) playAnimation(getWalkAnim(), false);
		if (shouldTaunt() && !isAttacking() && !isDeadOrDying()) playAnimation(tauntAnim, false);
		
		double attackSpeedMult = getHealth() <= 50.0F ? 1.15D : 1.0D;
		double dashAttackSpeedMult = MathUtil.isBetween(dashAttackAnim.getWrappedAnimProgress(), 7.6, 29.2) ? 1.325D : MathUtil.isBetween(dashAttackAnim.getWrappedAnimProgress(), 29.2D, 35.6D) ? 0.02D : MathUtil.isBetween(dashAttackAnim.getWrappedAnimProgress(), 35.6D, 58.4D) ? 2.0D : 0.76D;

		leftPunchAnim.setAnimSpeed(attackSpeedMult);
		rightPunchAnim.setAnimSpeed(attackSpeedMult);
		leftSwingAnim.setAnimSpeed(attackSpeedMult);
		rightSwingAnim.setAnimSpeed(attackSpeedMult);
		leftStompAnim.setAnimSpeed(attackSpeedMult + 0.13D);
		rightStompAnim.setAnimSpeed(attackSpeedMult + 0.13D);
		groundSlamAnim.setAnimSpeed(attackSpeedMult);
		dashAttackAnim.setAnimSpeed(dashAttackSpeedMult);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<RoboPounderEntity>> getWrappedControllers() {
		return roboPounderControllers;
	}

	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return roboPounderAnimations;
	}
}