package io.github.chaosawakens.common.entity.hostile;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableAOEGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
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
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<EntEntity> mainController = createMainMappedController("entmaincontroller");
	private final AnimationController<EntEntity> attackController = createMappedController("entattackcontroller", this::attackPredicate);
	private final AnimationController<EntEntity> deathController = createMappedController("entdeathcontroller", this::deathPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder deathAnim = new SingletonAnimationBuilder(this, "Death", EDefaultLoopTypes.PLAY_ONCE).setController(deathController);
	private final SingletonAnimationBuilder punchAnim = new SingletonAnimationBuilder(this, "Punch", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private final SingletonAnimationBuilder smashAnim = new SingletonAnimationBuilder(this, "Smash", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private static final byte PUNCH_ATTACK_ID = 1;
	private static final byte SMASH_ATTACK_ID = 2;
	private final EntType entType;

	public EntEntity(EntityType<? extends MonsterEntity> type, World worldIn, EntType entType) {
		super(type, worldIn);
		this.entType = entType;
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 150)
				.add(Attributes.ARMOR, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.175D)
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
	public AnimationController<? extends AnimatableMonsterEntity> getMainController() {
		return mainController;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState deathPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public int animationInterval() {
		return 1;
	}
	
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, () -> punchAnim, PUNCH_ATTACK_ID, 20D, 22.4D, 42));
		this.targetSelector.addGoal(0, new AnimatableAOEGoal(this, smashAnim, SMASH_ATTACK_ID, 21.6D, 22.4D, 10.0D, 4));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, false));
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
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return CASoundEvents.ENT_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.ENT_HURT.get();
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		if (!blockState.getMaterial().isLiquid()) playSound(CASoundEvents.ENT_WALK.get(), this.getVoicePitch() * 0.30F, this.getSoundVolume() * 1);
	}

	@Override
	protected float getVoicePitch() {
		return 0.4F;
	}

	@Override
	protected float getSoundVolume() {
		return 1.2F;
	}
	
	public EntType getEntType() {
		return entType;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<AnimationController<EntEntity>> getControllers() {
		return new ObjectArrayList<AnimationController<EntEntity>>(1);
	}
	
	public enum EntType {
		APPLE("apple"),
		ACACIA("acacia"),
		BIRCH("birch"),
		CHERRY("cherry"),
		CRIMSON("crimson"),
		DARK_OAK("dark_oak"),
		GINKGO("ginkgo"),
		JUNGLE("jungle"),
		OAK("oak"),
		SKYWOOD("skywood"),
		SPRUCE("spruce"),
		PEACH("peach"),
		WARPED("warped");

		private final String name;

		EntType(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}
}
