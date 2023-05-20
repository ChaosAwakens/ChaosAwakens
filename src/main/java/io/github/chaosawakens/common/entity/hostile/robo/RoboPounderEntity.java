package io.github.chaosawakens.common.entity.hostile.robo;

import java.util.List;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.AnimationUtil;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboPounderEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this); //TODO Diff method of controller reg. + sync
	private final ObjectArrayList<AnimationController<RoboPounderEntity>> roboPounderControllers = new ObjectArrayList<AnimationController<RoboPounderEntity>>(1);
	private static final DataParameter<Boolean> SHOULD_TAUNT = EntityDataManager.defineId(RoboPounderEntity.class, DataSerializers.BOOLEAN);
	private final AnimationController<RoboPounderEntity> mainController = createMainMappedController("robopoundermaincontroller");
	private final AnimationController<RoboPounderEntity> attackController = createMappedController("robopounderattackcontroller", this::attackPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE);
	private final SingletonAnimationBuilder tauntAnim = new SingletonAnimationBuilder(this, "Taunt", EDefaultLoopTypes.PLAY_ONCE);
	private final SingletonAnimationBuilder rightPunchAnim = new SingletonAnimationBuilder(this, "RPunch", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder leftPunchAnim = new SingletonAnimationBuilder(this, "LPunch", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder rightSwingAnim = new SingletonAnimationBuilder(this, "RSweep", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder leftSwingAnim = new SingletonAnimationBuilder(this, "LSweep", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder dashAttackAnim = new SingletonAnimationBuilder(this, "Dash", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder rightStompAnim = new SingletonAnimationBuilder(this, "RStomp", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder leftStompAnim = new SingletonAnimationBuilder(this, "LStomp", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder groundSlamAnim = new SingletonAnimationBuilder(this, "HStomp", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private static final byte PUNCH_ATTACK_ID = 1;
	private static final byte SWING_ATTACK_ID = 2;
	private static final byte DASH_ATTACK_ID = 3;
	private static final byte STOMP_ATTACK_ID = 4;
	private static final byte GROUND_SLAM_ATTACK_ID = 5;
	
	public RoboPounderEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 300)
				.add(Attributes.ARMOR, 30)
				.add(Attributes.ARMOR_TOUGHNESS, 20)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 360.0D)
				.add(Attributes.ATTACK_DAMAGE, 20)
				.add(Attributes.ATTACK_KNOCKBACK, 20)
				.add(Attributes.FOLLOW_RANGE, 69);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public AnimationController<? extends AnimatableMonsterEntity> getMainController() {
		return mainController;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		if (shouldTaunt()) playAnimation(tauntAnim);
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public int animationInterval() {
		return 2;
	}
	
	//TODO Info packets for c2s comms
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1, 3));
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, AnimationUtil.pickAnimation(() -> leftPunchAnim, () -> rightPunchAnim, random), PUNCH_ATTACK_ID, 14.2D, 17.3D, 50, 1));
//		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, AnimationUtil.pickAnimation(leftSwingAnim, rightSwingAnim, random), SWING_ATTACK_ID, 12D, 14D, 80, (owner) -> EntityUtil.getAllEntitiesAround(owner, 6.0D, 6.0D, 6.0D, 6.0D).size() >= 3));		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, dashAttackAnim, DASH_ATTACK_ID, 9.6D, 13.5D, 10));
//		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, AnimationUtil.pickAnimation(leftStompAnim, rightStompAnim, random), STOMP_ATTACK_ID, 10.4D, 14.7D, 8.0D, 5));
//		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, groundSlamAnim, GROUND_SLAM_ATTACK_ID, 11.6D, 17.5D, 12.0D, 8));
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
	}
	
	public boolean shouldTaunt() {
		return this.entityData.get(SHOULD_TAUNT);
	}
	
	public void setShouldTaunt(boolean shouldTaunt) {
		this.entityData.set(SHOULD_TAUNT, shouldTaunt);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (!level.isClientSide && !dead) handleTaunting();	
		if (tickCount % 10 == 0 && isAlive()) {
			ChaosAwakens.debug("Anim", "--------------------------------------------------------------------------");
			ChaosAwakens.debug("Anim", "[IS PLAYING IDLE ANIM]: " + isPlayingAnimation(idleAnim));
/*			ChaosAwakens.debug("Anim", "[HAS IDLE FINISHED]: " + idleAnim.hasAnimationFinished());
			ChaosAwakens.debug("Anim", "[HAS DEATH FINISHED]: " + deathAnim.hasAnimationFinished());
			ChaosAwakens.debug("Anim", "[IDLE PROGRESS]: " + idleAnim.getProgressTicks());
			ChaosAwakens.debug("Anim", "[WALK PROGRESS]: " + walkAnim.getProgressTicks());
			ChaosAwakens.debug("Anim", "[IDLE LENGTH]: " + idleAnim.getLengthTicks());*/
			ChaosAwakens.debug("Anim", "[RPA TICKS]: " + rightPunchAnim.getProgressTicks(getId()));
			ChaosAwakens.debug("Anim", "[LPA TICKS]: " + leftPunchAnim.getProgressTicks(getId()));
			ChaosAwakens.debug("Anim", "[ATTACK ID]: " + getAttackID());
			ChaosAwakens.debug("Anim", "[MAINCONT]: " + mainController.getAnimationState());
			ChaosAwakens.debug("Anim", "[ATKCONT]: " + attackController.getAnimationState());
			ChaosAwakens.debug("Anim", "------------------------------------------------------------------------------");
			
			ChaosAwakens.debug("AnimCont", "--------------------------------------------------------------------------");
			if (getMainController().getCurrentAnimation() != null) ChaosAwakens.debug("AnimCont", "[CUR MAIN ANIM]: " + getMainController().getCurrentAnimation().animationName);
			if (getTarget() != null) ChaosAwakens.debug("AnimCont", "[HAS TARGET]: " + getTarget());
			ChaosAwakens.debug("AnimCont", "[ALL CONTROLLERS: SIZE]: " + getControllers().size());
			if (getTarget() != null) ChaosAwakens.debug("AnimCont", "[Can Attak]: " + getTarget() != null && isAlive() && !isAttacking() && getTarget().isAlive() && MathUtil.getDistanceBetween(this, getTarget()) <= getMeleeAttackReachSqr(getTarget()) + 5D);
			ChaosAwakens.debug("AnimCont", "--------------------------------------------------------------------------");	
		}
	}
	
	private void handleTaunting() {
		List<LivingEntity> potentialTargets = EntityUtil.getAllEntitiesAround(this, getFollowRange(), getFollowRange(), getFollowRange(), getFollowRange());
		List<LivingEntity> confirmedTargets = new ObjectArrayList<LivingEntity>(1);
		
		if (getTarget() != null) {
			for (LivingEntity potentialTarget : potentialTargets) {
				if (getTarget() == potentialTarget && !confirmedTargets.contains(potentialTarget)) confirmedTargets.add(potentialTarget);
				if (potentialTarget.isDeadOrDying() && confirmedTargets.contains(potentialTarget)) confirmedTargets.remove(potentialTarget);
			}
		}
		
	//	if (random.nextInt(500) == 0) setShouldTaunt(true);
	//	else setShouldTaunt(false);
	}
	
	@Override
	public void manageAttack(LivingEntity target) {
		if (getAttackID() == PUNCH_ATTACK_ID) {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<AnimationController<RoboPounderEntity>> getControllers() {
		return roboPounderControllers;
	}
}