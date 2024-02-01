package io.github.chaosawakens.common.entity.boss.insect;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.util.EntityUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HerculesBeetleEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<HerculesBeetleEntity>> herculesBeetleControllers = new ObjectArrayList<WrappedAnimationController<HerculesBeetleEntity>>(3);
	private final ObjectArrayList<IAnimationBuilder> herculesBeetleAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private static final DataParameter<Boolean> IS_DOCILE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> DOCILITY_DURATION = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> IS_AWAKENING = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_OFFENSIVE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_DEFENSIVE = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> UNDISTURBED_DURATION = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.INT);
	private static final DataParameter<Float> DAMAGE_BUILDUP = EntityDataManager.defineId(HerculesBeetleEntity.class, DataSerializers.FLOAT);
	private final WrappedAnimationController<HerculesBeetleEntity> mainController = createMainMappedController("herculesbeetlemaincontroller");
	private final WrappedAnimationController<HerculesBeetleEntity> attackController = createMappedController("herculesbeetleattackcontroller", this::attackPredicate);
	private final WrappedAnimationController<HerculesBeetleEntity> deathController = createMappedController("herculesbeetledeathcontroller", this::deathPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(deathController);
	private final SingletonAnimationBuilder docileAnim = new SingletonAnimationBuilder(this, "Docile", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder awakeningAnim = new SingletonAnimationBuilder(this, "Awaken", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder ramAnim = new SingletonAnimationBuilder(this, "Ram Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder grabAnim = new SingletonAnimationBuilder(this, "Grab", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder munchAnim = new SingletonAnimationBuilder(this, "Munch Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private static final byte RAM_ATTACK_ID = 1;
	private static final byte MUNCH_ATTACK_ID = 2;
	public static final String HERCULES_BEETLE_MDF_NAME = "hercules_beetle";
	
	public HerculesBeetleEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
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
	public WrappedAnimationController<HerculesBeetleEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		if (isDocile() && !isAttacking()) playAnimation(docileAnim, true);
		if (isAwakening()) playAnimation(awakeningAnim, true);
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState deathPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_DOCILE, true);
		this.entityData.define(DOCILITY_DURATION, 0);
		this.entityData.define(IS_AWAKENING, false);
		this.entityData.define(IS_OFFENSIVE, false);
		this.entityData.define(IS_DEFENSIVE, false);
		this.entityData.define(UNDISTURBED_DURATION, 0);
		this.entityData.define(DAMAGE_BUILDUP, 0F);
	}
	
	public boolean isDocile() {
		return this.entityData.get(IS_DOCILE);
	}
	
	public void setDocile(boolean isDocile) {
		this.entityData.set(IS_DOCILE, isDocile);
	}
	
	public int getDocilityDuration() {
		return this.entityData.get(DOCILITY_DURATION);
	}
	
	public void setDocilityDuration(int docilityDuration) {
		this.entityData.set(DOCILITY_DURATION, docilityDuration);
	}
	
	public boolean isAwakening() {
		return this.entityData.get(IS_AWAKENING);
	}
	
	public void setAwakening(boolean isAwakening) {
		this.entityData.set(IS_AWAKENING, isAwakening);
	}
	
	public boolean isOffensive() {
		return this.entityData.get(IS_OFFENSIVE);
	}
	
	public void setOffensive(boolean isOffensive) {
		this.entityData.set(IS_OFFENSIVE, isOffensive);
	}
	
	public boolean isDefensive() {
		return this.entityData.get(IS_DEFENSIVE);
	}
	
	public void setDefensive(boolean isDefensive) {
		this.entityData.set(IS_DEFENSIVE, isDefensive);
	}
	
	public int getUndisturbedDuration() {
		return this.entityData.get(UNDISTURBED_DURATION);
	}
	
	public void setUndisturbedDuration(int undisturbedDuration) {
		this.entityData.set(UNDISTURBED_DURATION, undisturbedDuration);
	}
	
	public float getCurrentDamageBuildup() {
		return this.entityData.get(DAMAGE_BUILDUP);
	}
	
	public void setCurrentDamageBuildup(float currentDamageBuildup) {
		this.entityData.set(DAMAGE_BUILDUP, currentDamageBuildup);
	}
	
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, () -> ramAnim, RAM_ATTACK_ID, 9.6D, 12.8D, 42, (owner) -> isOffensive()));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}
	
	@Override
	public void tick() {
		super.tick();
		if (!level.isClientSide) handleStates();
	}
	
	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (pSource != null && !pSource.isCreativePlayer() && pSource != DamageSource.OUT_OF_WORLD && pAmount < getMaxHealth()) setCurrentDamageBuildup(getCurrentDamageBuildup() + pAmount);;
		return super.hurt(pSource, pAmount);
	}
	
	private void handleStates() {
		if (isDocile()) {
			this.navigation.stop();
			setDeltaMovement(0, getDeltaMovement().y, 0);
			EntityUtil.freezeEntityRotation(this);
			setDocilityDuration(getDocilityDuration() + 1);
			setArmor(20);
			
			if (getDocilityDuration() >= 100 && getHealth() < getMaxHealth()) {
				if (tickCount % 20 == 0) heal(1.0F);
			}
			
			if (wasDisturbed()) {
				setDocile(false);
				setAwakening(true);
				setDocilityDuration(0);
			}
		}
		
		if (isAwakening()) {
			this.navigation.stop();
			repelEntities(blockPosition(), 15.0F);
			EntityUtil.freezeEntityRotation(this);
			setArmor(10);
			
			if (awakeningAnim.hasAnimationFinished()) {
				setAwakening(false);
				setOffensive(true);
			}
		}
		
		if (getTarget() != null) {
			if (!isOffensive() && !isDefensive() && getCurrentDamageBuildup() < 50.0F) {
				setOffensive(true);
				setDefensive(false);
			} else if (isOffensive() && getCurrentDamageBuildup() >= 50.0F) {
				setOffensive(false);
				setDefensive(true);
				setCurrentDamageBuildup(0);
			}
			
			if (isDefensive() && (getCurrentDamageBuildup() >= 25.0F || getTarget().getHealth() < getTarget().getMaxHealth() / 4)) {
				setOffensive(true);
				setDefensive(false);
				setCurrentDamageBuildup(0);
			}
		}
		
		if (isOffensive()) {
			setArmor(15);
		}
		
		if (isDefensive()) {
			setArmor(25);
		}
		
		if (isDeadOrDying()) {
			setDocile(false);
			setAwakening(false);
			setOffensive(false);
			setDefensive(false);
			EntityUtil.freezeEntityRotation(this);
		}
		
		if (!isDocile()) {
			if (!wasDisturbed()) setUndisturbedDuration(getUndisturbedDuration() + 1);
			
			if (getUndisturbedDuration() >= 1200) {
				setOffensive(false);
				setDefensive(false);
				setDocile(true);
			}
		}
	}
	
	private boolean wasDisturbed() {
		return getLastHurtByMob() != null || getTarget() != null || hurtTime > 0;
	}

	@Override
	public int animationInterval() {
		return 1;
	}

	@Override
	public void manageAttack(LivingEntity target) {		
	}
	
	@Override
	public boolean isPersistenceRequired() {
		return true;
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
		return HERCULES_BEETLE_MDF_NAME;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return CASoundEvents.HERCULES_BEETLE_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.HERCULES_BEETLE_DEATH.get();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<HerculesBeetleEntity>> getWrappedControllers() {
		return herculesBeetleControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return herculesBeetleAnimations;
	}
}
