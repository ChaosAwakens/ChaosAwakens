package io.github.chaosawakens.common.entity.neutral.land.gator;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.ai.goals.neutral.AnimatableAngerMeleeAttackGoal;
import io.github.chaosawakens.common.entity.base.AnimatableAngerableAnimalEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CASoundEvents;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EmeraldGatorEntity extends AnimatableAngerableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<EmeraldGatorEntity> mainController = createMainMappedController("emeraldgatormaincontroller");
	private final AnimationController<EmeraldGatorEntity> attackController = createMappedController("emeraldgatorattackcontroller", this::attackPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder swimAnim = new SingletonAnimationBuilder(this, "Swim", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder biteAnim = new SingletonAnimationBuilder(this, "Bite Attack", EDefaultLoopTypes.PLAY_ONCE).setController(attackController);
	private static final RangedInteger ANGER_TIME_RANGE = TickRangeConverter.rangeOfSeconds(40, 80);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.COD, Items.PUFFERFISH, Items.SALMON, Items.TROPICAL_FISH);
	private static final byte BITE_ATTACK_ID = 1;
	
	public EmeraldGatorEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 18)
				.add(Attributes.ATTACK_DAMAGE, 3)
				.add(Attributes.ATTACK_KNOCKBACK, 1)
				.add(Attributes.ATTACK_SPEED, 1)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 8);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public AnimationController<? extends IAnimatableEntity> getMainController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return 1;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		if (isInWater()) playAnimation(swimAnim);
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new LeapAtTargetGoal(this, 0.4F));
		this.targetSelector.addGoal(0, new AnimatableAngerMeleeAttackGoal(this, biteAnim, BITE_ATTACK_ID, 20, 24));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(EmeraldGatorEntity.class, CrystalGatorEntity.class));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 1.0));
		this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, false, FOOD_ITEMS));
		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
	}
	
	@Override
	public RangedInteger getAngerTimeRange() {
		return ANGER_TIME_RANGE;
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
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return CASoundEvents.EMERALD_GATOR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.EMERALD_GATOR_DEATH.get();
	}
	
	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (isInvulnerableTo(source)) return false;
		else {
			Entity sourceDamageEntity = source.getEntity();
			if (sourceDamageEntity != null && !(sourceDamageEntity instanceof PlayerEntity) && !(sourceDamageEntity instanceof AbstractArrowEntity)) amount = (amount + 1.0F) / 2.0F;
			return super.hurt(source, amount);
		}
	}
	
	@Override
	public boolean isFood(ItemStack pStack) {
		return FOOD_ITEMS.test(pStack);
	}

	@Override
	public EmeraldGatorEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return CAEntityTypes.EMERALD_GATOR.get().create(pServerLevel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<AnimationController<EmeraldGatorEntity>> getControllers() {
		return new ObjectArrayList<AnimationController<EmeraldGatorEntity>>(1);
	}
}
