package io.github.chaosawakens.common.entity.creature.land;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.goals.passive.land.AnimatableEatGrassGoal;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GazelleEntity extends AnimatableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<GazelleEntity>> gazelleControllers = new ObjectArrayList<WrappedAnimationController<GazelleEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> gazelleAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private static final DataParameter<Boolean> PANICKING = EntityDataManager.defineId(GazelleEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> TYPE_ID = EntityDataManager.defineId(GazelleEntity.class, DataSerializers.INT);
	private final WrappedAnimationController<GazelleEntity> mainController = createMainMappedController("gazellemaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder runAnim = new SingletonAnimationBuilder(this, "Run", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder grazeAnim = new SingletonAnimationBuilder(this, "Graze", EDefaultLoopTypes.PLAY_ONCE);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.WHEAT, Items.SUGAR, Blocks.HAY_BLOCK.asItem(), Items.APPLE);

	public GazelleEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 12.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 14.0D);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public WrappedAnimationController<GazelleEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return 1;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() { //TODO Herd panic goal
		this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.0D, 1));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.5D) {
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
		this.goalSelector.addGoal(1, new AvoidEntityGoal<MonsterEntity>(this, MonsterEntity.class, 12.0F, 1.0D, 2.5D) {
			@Override
			public void stop() {
				super.stop();
				setPanicking(false);
			}

			@Override
			public void tick() {
				super.tick();
				if (distanceToSqr(toAvoid) < 49.0D) setPanicking(true);
				else setPanicking(false);
			}
		});
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 2.0F));
		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new AnimatableEatGrassGoal(this, () -> grazeAnim, 15));
		this.goalSelector.addGoal(2, new AnimatableEatGrassGoal(this, () -> grazeAnim, 30));
		this.goalSelector.addGoal(3, new TemptGoal(this, 0.2D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(3, new TemptGoal(this, 0.2D, false, FOOD_ITEMS));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PANICKING, false);
		this.entityData.define(TYPE_ID, 0);
	}

	private int getRandomGazelleType(IWorld world) {
		return this.random.nextInt(6);
	}

	public boolean isPanicking() {
		return this.entityData.get(PANICKING);
	}

	public void setPanicking(boolean panicking) {
		this.entityData.set(PANICKING, panicking);
	}

	public int getGazelleType() {
		return MathHelper.clamp(this.entityData.get(TYPE_ID), 0, 6);
	}

	public void setGazelleType(int type) {
		this.entityData.set(TYPE_ID, type);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("GazelleType", getGazelleType());
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		setGazelleType(nbt.getInt("GazelleType"));
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
	public boolean isFood(ItemStack pStack) {
		return FOOD_ITEMS.test(pStack);
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, ILivingEntityData pSpawnData, CompoundNBT pDataTag) {
		int randGazelleType = getRandomGazelleType(pLevel);

		if (pSpawnData instanceof GazelleData) randGazelleType = ((GazelleData) pSpawnData).gazelletype;
		else pSpawnData = new GazelleData(randGazelleType);

		setGazelleType(randGazelleType);
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	@Override
	public GazelleEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		GazelleEntity offspring = CAEntityTypes.GAZELLE.get().create(pServerLevel);

		assert offspring != null;
		offspring.setGazelleType(((GazelleEntity) pMate).getGazelleType());

		return offspring;
	}
	
	@Override
	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isMoving()) playAnimation(getIdleAnim(), false);
		if (getWalkAnim() != null && isMoving() && isOnGround() && !isPanicking()) playAnimation(getWalkAnim(), false);
		if (isMoving() && isOnGround() && isPanicking()) playAnimation(runAnim, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<GazelleEntity>> getWrappedControllers() {
		return gazelleControllers;
	}

	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return gazelleAnimations;
	}

	private class GazelleData extends AgeableData {
		public final int gazelletype;

		private GazelleData(int gazelletype) {
			super(true);
			this.gazelletype = gazelletype;
		}
	}
}