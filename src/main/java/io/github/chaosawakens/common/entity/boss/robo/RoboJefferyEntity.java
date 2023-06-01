package io.github.chaosawakens.common.entity.boss.robo;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableAOEGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.base.AnimatableBossEntity;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.AnimationUtil;
import io.github.chaosawakens.common.util.EntityUtil;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboJefferyEntity extends AnimatableBossEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<AnimationController<RoboJefferyEntity>> roboJefferyControllers = new ObjectArrayList<AnimationController<RoboJefferyEntity>>(1);
	private final ServerBossInfo info = (ServerBossInfo) new ServerBossInfo(getType().getDescription().copy().append(getDisplayName().copy().withStyle(TextFormatting.DARK_PURPLE).withStyle(TextFormatting.BOLD)), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS).setDarkenScreen(true).setCreateWorldFog(true);
	private static final DataParameter<Boolean> HAS_CORE = EntityDataManager.defineId(RoboJefferyEntity.class, DataSerializers.BOOLEAN);
	private final AnimationController<RoboJefferyEntity> mainController = createMainMappedController("robojefferymaincontroller");
	private final AnimationController<RoboJefferyEntity> ambienceController = createMappedController("robojefferyambiencecontroller", this::ambiencePredicate);
	private final AnimationController<RoboJefferyEntity> attackController = createMappedController("robojefferyattackcontroller", this::attackPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder idleExtrasAnim = new SingletonAnimationBuilder(this, "Idle Extras", EDefaultLoopTypes.LOOP).setController(ambienceController);
	private final SingletonAnimationBuilder lowHealthAnim = new SingletonAnimationBuilder(this, "Low Health", EDefaultLoopTypes.LOOP).setController(ambienceController);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE);
	private final SingletonAnimationBuilder leftPunchAnim = new SingletonAnimationBuilder(this, "Left Punch Attack", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder rightPunchAnim = new SingletonAnimationBuilder(this, "Right Punch Attack", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder smashAnim = new SingletonAnimationBuilder(this, "Smash Attack", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	public static final byte PUNCH_ATTACK_ID = 1;
	public static final byte SMASH_ATTACK_ID = 2;
	public static final byte LEAP_ATTACK_ID = 3;
	
	public RoboJefferyEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 600)
				.add(Attributes.ARMOR, 80)
				.add(Attributes.ARMOR_TOUGHNESS, 50)
				.add(Attributes.MOVEMENT_SPEED, 0.23D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 720.0D)
				.add(Attributes.ATTACK_DAMAGE, 45)
				.add(Attributes.ATTACK_KNOCKBACK, 50)
				.add(Attributes.FOLLOW_RANGE, 95);
	}

	@Override
	public ServerBossInfo getBossInfo() {
		return info;
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
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState ambiencePredicate(AnimationEvent<E> event) {
		if (!isAttacking() && getHealth() > getMaxHealth() / 8 && !isDeadOrDying()) playAnimation(idleExtrasAnim);
		if (getHealth() <= getMaxHealth() / 8 && !isDeadOrDying()) playAnimation(lowHealthAnim);
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1, 3));
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, AnimationUtil.pickAnimation(() -> leftPunchAnim, () -> rightPunchAnim, random), PUNCH_ATTACK_ID, 16.6D, 20.1D, 55));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, smashAnim, SMASH_ATTACK_ID, 20D, 25D, 18D, 5));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<VillagerEntity>(this, VillagerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<AnimalEntity>(this, AnimalEntity.class, false));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HAS_CORE, true);
	}

	@Override
	public int animationInterval() {
		return 2;
	}

	@Override
	public void manageAttack(LivingEntity target) {
		if (getAttackID() == PUNCH_ATTACK_ID || getAttackID() == SMASH_ATTACK_ID) {
			if (EntityUtil.isHoldingItem(target, Items.SHIELD)) {
				if (target instanceof PlayerEntity) {
					PlayerEntity playerTarget = (PlayerEntity) target;
					playerTarget.disableShield(false);
				}
			}
		}
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
	public ObjectArrayList<AnimationController<RoboJefferyEntity>> getControllers() {
		return roboJefferyControllers;
	}
}
