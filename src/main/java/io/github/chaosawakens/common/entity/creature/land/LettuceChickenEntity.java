package io.github.chaosawakens.common.entity.creature.land;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.util.EntityUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LettuceChickenEntity extends AnimatableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<LettuceChickenEntity>> lettuceChickenControllers = new ObjectArrayList<WrappedAnimationController<LettuceChickenEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> lettuceChickenAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private static final DataParameter<Boolean> PANICKING = EntityDataManager.defineId(LettuceChickenEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> SITTING = EntityDataManager.defineId(LettuceChickenEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SITTING_TIME = EntityDataManager.defineId(LettuceChickenEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> SITTING_COOLDOWN = EntityDataManager.defineId(LettuceChickenEntity.class, DataSerializers.INT);
	private final WrappedAnimationController<LettuceChickenEntity> mainController = createMainMappedController("lettucechickenmaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder runAnim = new SingletonAnimationBuilder(this, "Run", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder flapAnim = new SingletonAnimationBuilder(this, "Flap", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder sitAnim = new SingletonAnimationBuilder(this, "Sit", EDefaultLoopTypes.LOOP);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS, CAItems.LETTUCE_SEEDS.get(), CAItems.CORN_SEEDS.get(), CAItems.RADISH_SEEDS.get(), CAItems.STRAWBERRY_SEEDS.get(), CAItems.TOMATO_SEEDS.get());
	private int eggTime = MathHelper.nextInt(random, 2000, 4000);
	public static final String LETTUCE_CHICKEN_MDF_NAME = "lettuce_chicken";

	public LettuceChickenEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 5)
				.add(Attributes.FOLLOW_RANGE, 5)
				.add(Attributes.MOVEMENT_SPEED, 0.25F);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public WrappedAnimationController<LettuceChickenEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return 2;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PANICKING, false);
		this.entityData.define(SITTING, false);
		this.entityData.define(SITTING_TIME, 0);
		this.entityData.define(SITTING_COOLDOWN, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 1.1D) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isSitting();
			}
		});
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D) {
			@Override
			public void start() {
				super.start();
				setPanicking(true);
			}

			@Override
			public void stop() {
				super.stop();
				setPanicking(false);
			}
		});
		this.goalSelector.addGoal(1, new AvoidEntityGoal<MonsterEntity>(this, MonsterEntity.class, 12.0F, 1.1D, 1.44D) {
			@Override
			public void stop() {
				super.stop();
				setPanicking(false);
			}
			
			@Override
			public void tick() {
				super.tick();

				setPanicking(distanceToSqr(toAvoid) < 120.0D);
				getNavigation().setSpeedModifier(distanceToSqr(toAvoid) < 120.0D ? 1.44D : 1.1D);
			}
		});
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.2D));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, false, FOOD_ITEMS));
		this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.2D));
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
	}

	public boolean isPanicking() {
		return this.entityData.get(PANICKING);
	}

	public void setPanicking(boolean panicking) {
		this.entityData.set(PANICKING, panicking);
	}

	public boolean isSitting() {
		return this.entityData.get(SITTING);
	}

	public void setSitting(boolean sitting) {
		this.entityData.set(SITTING, sitting);
	}

	public int getSittingTime() {
		return this.entityData.get(SITTING_TIME);
	}

	public void setSittingTime(int sittingTime) {
		this.entityData.set(SITTING_TIME, sittingTime);
	}

	public void updateSittingTime() {
		setSittingTime(getSittingTime() + 1);
	}

	public void resetSittingTime() {
		setSittingTime(0);
	}

	public int getSittingCooldown() {
		return this.entityData.get(SITTING_COOLDOWN);
	}

	public void setSittingCooldown(int sittingCooldown) {
		this.entityData.set(SITTING_COOLDOWN, sittingCooldown);
	}

	public void updateSittingCooldown() {
		setSittingCooldown(getSittingCooldown() - 1);
	}

	public void resetSittingCooldown() {
		setSittingCooldown(0);
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
	public String getOwnerMDFileName() {
		return LETTUCE_CHICKEN_MDF_NAME;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.CHICKEN_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.CHICKEN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.CHICKEN_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pPos, BlockState pBlock) {
		playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public void aiStep() {
		super.aiStep();
	}

	@Override
	public void tick() {
		super.tick();
		layEggs(CAItems.LETTUCE_CHICKEN_EGG.get());
		handleSitting();

		if (!isOnGround() && getDeltaMovement().y < 0.0D) setDeltaMovement(getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
	}

	protected void layEggs(IItemProvider targetEggItem) {
		if (!level.isClientSide() && isAlive() && !isBaby() && --this.eggTime <= 0) {
			playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			spawnAtLocation(targetEggItem);
			this.eggTime = MathHelper.nextInt(random, 2000, 4000);
		}
	}

	private void handleSitting() {
		final boolean canSit = !isPanicking() && isAlive() && isOnGround() && !isBaby() && hurtTime == 0 && getSittingCooldown() == 0 && getSittingTime() < random.nextInt(700) + 700;
		setSitting(canSit);

		if (isSitting()) {
			updateSittingTime();
			getNavigation().stop();
			getDeltaMovement().multiply(0, 1, 0);
			EntityUtil.freezeEntityRotation(this);
		} else if (!isSitting()) {
			if (getSittingCooldown() <= 0) setSittingCooldown(MathHelper.nextInt(random, 250, 1200));
			resetSittingTime();
		}

		if (getSittingCooldown() > 0) updateSittingCooldown();
		if (getSittingCooldown() < 0) resetSittingCooldown();
	}

	@Override
	public int getMaxHeadXRot() {
		return isSitting() ? 0 : super.getMaxHeadXRot();
	}
	
	@Override
	protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
		return 0;
	}

	@Override
	public boolean isFood(ItemStack pStack) {
		return FOOD_ITEMS.test(pStack);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, ILivingEntityData pSpawnData, CompoundNBT pDataTag) {
		setSittingCooldown(random.nextInt(100) + 60);
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	@Override
	public LettuceChickenEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return CAEntityTypes.LETTUCE_CHICKEN.get().create(pServerLevel);
	}
	
	@Override
	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isMoving() && isOnGround() && !isSitting()) playAnimation(getIdleAnim(), false);
		if (getWalkAnim() != null && isMoving() && !isPanicking() && isOnGround()) playAnimation(getWalkAnim(), false);
		if (!isOnGround()) playAnimation(flapAnim, false);
		if (isSitting()) playAnimation(sitAnim, false);
		if (isPanicking() && isOnGround()) playAnimation(runAnim, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<LettuceChickenEntity>> getWrappedControllers() {
		return lettuceChickenControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return lettuceChickenAnimations;
	}
}