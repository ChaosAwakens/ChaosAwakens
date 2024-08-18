package io.github.chaosawakens.common.entity.hostile;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableAOEGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import io.github.chaosawakens.common.entity.creature.land.BeaverEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboSniperEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.util.EnumUtil.EntType;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.SoundUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<EntEntity>> entControllers = new ObjectArrayList<WrappedAnimationController<EntEntity>>(2);
	private final ObjectArrayList<IAnimationBuilder> entAnimations = new ObjectArrayList<IAnimationBuilder>(7);
	private final WrappedAnimationController<EntEntity> mainController = createMainMappedController("entmaincontroller");
	private final WrappedAnimationController<EntEntity> attackController = createMappedController("entattackcontroller", this::attackPredicate);
	private final WrappedAnimationController<EntEntity> ambientController = createMappedController("entambientcontroller", this::ambientPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder idleExtrasAnim = new SingletonAnimationBuilder(this, "Idle Extras", EDefaultLoopTypes.LOOP).setWrappedController(ambientController);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder leftPunchAnim = new SingletonAnimationBuilder(this, "Left Punch", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder rightPunchAnim = new SingletonAnimationBuilder(this, "Right Punch", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private final SingletonAnimationBuilder smashAttackAnim = new SingletonAnimationBuilder(this, "Smash Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private static final byte PUNCH_ATTACK_ID = 1;
	private static final byte SMASH_ATTACK_ID = 2;
	private final EntType entType;
	public static final String ENT_MDF_NAME = "ent";

	public EntEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.entType = EntType.OAK;
	}

	public EntEntity(EntityType<? extends MonsterEntity> type, World worldIn, EntType entType) {
		super(type, worldIn);
		this.entType = entType;
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 150)
				.add(Attributes.ARMOR, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.325D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
				.add(Attributes.ATTACK_DAMAGE, 15)
				.add(Attributes.ATTACK_KNOCKBACK, 4.5D)
				.add(Attributes.FOLLOW_RANGE, 30);
	}
	
	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<EntEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		if (isAttacking() || isDeadOrDying()) {
			stopAnimation(walkAnim);
			playAnimation(idleAnim, true);
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState ambientPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public int animationInterval() {
		return 2;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 1.2D) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isAttacking() && !isOnAttackCooldown() && getTarget() == null;
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !isAttacking() && !isOnAttackCooldown() && getTarget() == null;
			}
		});
		this.goalSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1.1, 3));
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, null, PUNCH_ATTACK_ID, 20.5D, 22.4D, 2).pickBetweenAnimations(() -> leftPunchAnim, () -> rightPunchAnim).soundOnStart(() -> getPunchSound(), 0.4F));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, () -> smashAttackAnim, SMASH_ATTACK_ID, 21.6D, 22.6D, 4.5D, 1, 18, false, false, true, 60).soundOnStart(() -> getSmashSound(), 1.0F));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, () -> smashAttackAnim, SMASH_ATTACK_ID, 21.6D, 22.6D, 4.5D, 2, 10, false, false, true, 45).soundOnStart(() -> getSmashSound(), 1.0F));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, () -> smashAttackAnim, SMASH_ATTACK_ID, 21.6D, 22.6D, 4.5D, 4, 2, false, false, true, 35).soundOnStart(() -> getSmashSound(), 1.0F));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboJefferyEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboPounderEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboSniperEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboWarriorEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, BeaverEntity.class, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	@Override
	public void manageAttack(LivingEntity target) {
		if (getAttackID() == SMASH_ATTACK_ID) {
			if (target.getOffhandItem().getItem().equals(Items.SHIELD) || target.getMainHandItem().getItem().equals(Items.SHIELD)) {
				if (target instanceof PlayerEntity) {
					PlayerEntity playerTarget = (PlayerEntity) target;
					playerTarget.disableShield(false);
				}
			}
		}
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
		return ENT_MDF_NAME;
	}

	@Override
	protected void onSpawn(boolean hasAlreadyDied) {
		if (!hasAlreadyDied && level.isClientSide) {
			SoundUtil.playIdleSoundAsTickable(getIdleSound(), this);
			SoundUtil.playWalkingSoundAsTickable(getWalkSound(), this);
		}

		if (!level.isClientSide) setMovementSpeed(0.325D);
	}
	
	protected SoundEvent getIdleSound() {
		switch (getEntType()) {
		case ACACIA:
			return CASoundEvents.ACACIA_ENT_IDLE.get();
		case APPLE:
			return CASoundEvents.APPLE_ENT_IDLE.get();
		case BIRCH:
			return CASoundEvents.BIRCH_ENT_IDLE.get();
		case CHERRY:
			return CASoundEvents.CHERRY_ENT_IDLE.get();
		case CRIMSON:
			return CASoundEvents.CRIMSON_ENT_IDLE.get();
		case DARK_OAK:
			return CASoundEvents.DARK_OAK_ENT_IDLE.get();
		case JUNGLE:
			return CASoundEvents.JUNGLE_ENT_IDLE.get();
		case OAK:
			return CASoundEvents.OAK_ENT_IDLE.get();
		case PEACH:
			return CASoundEvents.PEACH_ENT_IDLE.get();
		case SKYWOOD:
			return CASoundEvents.SKYWOOD_ENT_IDLE.get();
		case SPRUCE:
			return CASoundEvents.SPRUCE_ENT_IDLE.get();
		case WARPED:
			return CASoundEvents.WARPED_ENT_IDLE.get();
		case GINKGO:
			return CASoundEvents.GINKGO_ENT_IDLE.get();
		default:
			return CASoundEvents.ENT_IDLE.get();
		}
	}
	
	protected SoundEvent getWalkSound() {
		switch (getEntType()) {
		case ACACIA:
			return CASoundEvents.ACACIA_ENT_WALK.get();
		case APPLE:
			return CASoundEvents.APPLE_ENT_WALK.get();
		case BIRCH:
			return CASoundEvents.BIRCH_ENT_WALK.get();
		case CHERRY:
			return CASoundEvents.CHERRY_ENT_WALK.get();
		case CRIMSON:
			return CASoundEvents.CRIMSON_ENT_WALK.get();
		case DARK_OAK:
			return CASoundEvents.DARK_OAK_ENT_WALK.get();
		case JUNGLE:
			return CASoundEvents.JUNGLE_ENT_WALK.get();
		case OAK:
			return CASoundEvents.OAK_ENT_WALK.get();
		case PEACH:
			return CASoundEvents.PEACH_ENT_WALK.get();
		case SKYWOOD:
			return CASoundEvents.SKYWOOD_ENT_WALK.get();
		case SPRUCE:
			return CASoundEvents.SPRUCE_ENT_WALK.get();
		case WARPED:
			return CASoundEvents.WARPED_ENT_WALK.get();
		case GINKGO:
			return CASoundEvents.GINKGO_ENT_WALK.get();
		default:
			return CASoundEvents.ENT_WALK.get();
		}
	}

	protected SoundEvent getPunchSound() {
		switch (getEntType()) {
		case ACACIA:
			return CASoundEvents.ACACIA_ENT_TREE_PUNCH.get();
		case APPLE:
			return CASoundEvents.APPLE_ENT_TREE_PUNCH.get();
		case BIRCH:
			return CASoundEvents.BIRCH_ENT_TREE_PUNCH.get();
		case CHERRY:
			return CASoundEvents.CHERRY_ENT_TREE_PUNCH.get();
		case CRIMSON:
			return CASoundEvents.CRIMSON_ENT_TREE_PUNCH.get();
		case DARK_OAK:
			return CASoundEvents.DARK_OAK_ENT_TREE_PUNCH.get();
		case JUNGLE:
			return CASoundEvents.JUNGLE_ENT_TREE_PUNCH.get();
		case OAK:
			return CASoundEvents.OAK_ENT_TREE_PUNCH.get();
		case PEACH:
			return CASoundEvents.PEACH_ENT_TREE_PUNCH.get();
		case SKYWOOD:
			return CASoundEvents.SKYWOOD_ENT_TREE_PUNCH.get();
		case SPRUCE:
			return CASoundEvents.SPRUCE_ENT_TREE_PUNCH.get();
		case WARPED:
			return CASoundEvents.WARPED_ENT_TREE_PUNCH.get();
		case GINKGO:
			return CASoundEvents.GINKGO_ENT_TREE_PUNCH.get();
		default:
			return CASoundEvents.ENT_TREE_PUNCH.get();
		}
	}

	protected SoundEvent getSmashSound() {
		switch (getEntType()) {
		case ACACIA:
			return CASoundEvents.ACACIA_ENT_ENT_SMASH.get();
		case APPLE:
			return CASoundEvents.APPLE_ENT_ENT_SMASH.get();
		case BIRCH:
			return CASoundEvents.BIRCH_ENT_ENT_SMASH.get();
		case CHERRY:
			return CASoundEvents.CHERRY_ENT_ENT_SMASH.get();
		case CRIMSON:
			return CASoundEvents.CRIMSON_ENT_ENT_SMASH.get();
		case DARK_OAK:
			return CASoundEvents.DARK_OAK_ENT_ENT_SMASH.get();
		case JUNGLE:
			return CASoundEvents.JUNGLE_ENT_ENT_SMASH.get();
		case OAK:
			return CASoundEvents.OAK_ENT_ENT_SMASH.get();
		case PEACH:
			return CASoundEvents.PEACH_ENT_ENT_SMASH.get();
		case SKYWOOD:
			return CASoundEvents.SKYWOOD_ENT_ENT_SMASH.get();
		case SPRUCE:
			return CASoundEvents.SPRUCE_ENT_ENT_SMASH.get();
		case WARPED:
			return CASoundEvents.WARPED_ENT_ENT_SMASH.get();
		case GINKGO:
			return CASoundEvents.GINKGO_ENT_ENT_SMASH.get();
		default:
			return CASoundEvents.ENT_ENT_SMASH.get();
		}
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (isDeadOrDying())
			return getDeathSound();
		else
			switch (getEntType()) {
			case ACACIA:
				return CASoundEvents.ACACIA_ENT_DAMAGE.get();
			case APPLE:
				return CASoundEvents.APPLE_ENT_DAMAGE.get();
			case BIRCH:
				return CASoundEvents.BIRCH_ENT_DAMAGE.get();
			case CHERRY:
				return CASoundEvents.CHERRY_ENT_DAMAGE.get();
			case CRIMSON:
				return CASoundEvents.CRIMSON_ENT_DAMAGE.get();
			case DARK_OAK:
				return CASoundEvents.DARK_OAK_ENT_DAMAGE.get();
			case JUNGLE:
				return CASoundEvents.JUNGLE_ENT_DAMAGE.get();
			case OAK:
				return CASoundEvents.OAK_ENT_DAMAGE.get();
			case PEACH:
				return CASoundEvents.PEACH_ENT_DAMAGE.get();
			case SKYWOOD:
				return CASoundEvents.SKYWOOD_ENT_DAMAGE.get();
			case SPRUCE:
				return CASoundEvents.SPRUCE_ENT_DAMAGE.get();
			case WARPED:
				return CASoundEvents.WARPED_ENT_DAMAGE.get();
			case GINKGO:
				return CASoundEvents.GINKGO_ENT_DAMAGE.get();
			default:
				return CASoundEvents.ENT_DAMAGE.get();
			}
	}

	@Override
	protected SoundEvent getDeathSound() {
		switch (getEntType()) {
		case ACACIA:
			return CASoundEvents.ACACIA_ENT_DEATH.get();
		case APPLE:
			return CASoundEvents.APPLE_ENT_DEATH.get();
		case BIRCH:
			return CASoundEvents.BIRCH_ENT_DEATH.get();
		case CHERRY:
			return CASoundEvents.CHERRY_ENT_DEATH.get();
		case CRIMSON:
			return CASoundEvents.CRIMSON_ENT_DEATH.get();
		case DARK_OAK:
			return CASoundEvents.DARK_OAK_ENT_DEATH.get();
		case JUNGLE:
			return CASoundEvents.JUNGLE_ENT_DEATH.get();
		case OAK:
			return CASoundEvents.OAK_ENT_DEATH.get();
		case PEACH:
			return CASoundEvents.PEACH_ENT_DEATH.get();
		case SKYWOOD:
			return CASoundEvents.SKYWOOD_ENT_DEATH.get();
		case SPRUCE:
			return CASoundEvents.SPRUCE_ENT_DEATH.get();
		case WARPED:
			return CASoundEvents.WARPED_ENT_DEATH.get();
		case GINKGO:
			return CASoundEvents.GINKGO_ENT_DEATH.get();
		default:
			return CASoundEvents.ENT_DEATH.get();
		}
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
	}

	@Override
	protected float getVoicePitch() {
		return isDeadOrDying() ? super.getVoicePitch() * 1.32F : super.getVoicePitch();
	}

	@Override
	protected float getSoundVolume() {
		return 1.2F;
	}
	
	public EntType getEntType() {
		return entType;
	}

	@Override
	protected float getStandingEyeHeight(Pose pPose, EntitySize pSize) {
		return super.getStandingEyeHeight(pPose, pSize) * 0.8F;
	}

	@Override
	public float getDeltaKnockbackResistance() {
		return 95.0F;
	}

	@Override
	public boolean canBeKnockedBack() {
		return false;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean canCollideWith(Entity pEntity) {
		return false;
	}

	@Override
	public float getMeleeAttackReach() {
		return super.getMeleeAttackReach() / 1.32F;
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (MathUtil.isBetween(leftPunchAnim.getWrappedAnimProgress(), 18.8D, 22.4D) || MathUtil.isBetween(rightPunchAnim.getWrappedAnimProgress(), 18.8D, 22.4D)) CAScreenShakeEntity.shakeScreen(level, position(), 15.0F, 0.07F, 5, 20); // Cause it's part of the AI n stuff :p
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<EntEntity>> getWrappedControllers() {
		return entControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return entAnimations;
	}

	@Override
	protected void handleBaseAnimations() {
		super.handleBaseAnimations();

		if (idleExtrasAnim != null && !isDeadOrDying()) playAnimation(idleExtrasAnim, false);
	}
}