package io.github.chaosawakens.common.entity.neutral.land.dino;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.goals.neutral.AnimatableAngerMeleeAttackGoal;
import io.github.chaosawakens.common.entity.base.AnimatableAngerableAnimalEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class DimetrodonEntity extends AnimatableAngerableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<DimetrodonEntity>> dimetrodonControllers = new ObjectArrayList<WrappedAnimationController<DimetrodonEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> dimetrodonAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private static final DataParameter<Integer> TYPE_ID = EntityDataManager.defineId(DimetrodonEntity.class, DataSerializers.INT);
	private final WrappedAnimationController<DimetrodonEntity> mainController = createMainMappedController("dimetrodonmaincontroller");
	private final WrappedAnimationController<DimetrodonEntity> attackController = createMappedController("dimetrodonattackcontroller", this::attackPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder swimAnim = new SingletonAnimationBuilder(this, "Swim", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder biteAnim = new SingletonAnimationBuilder(this, "Bite Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private static final RangedInteger ANGER_TIME_RANGE = TickRangeConverter.rangeOfSeconds(60, 120);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.COD, Items.PUFFERFISH, Items.SALMON, Items.TROPICAL_FISH, CAItems.GREEN_FISH.get(), CAItems.SPARK_FISH.get());
	private static final byte BITE_ATTACK_ID = 1;
	public static final String DIMETRODON_MDF_NAME = "dimetrodon";
	
	public DimetrodonEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 24)
				.add(Attributes.ATTACK_DAMAGE, 5)
				.add(Attributes.ATTACK_KNOCKBACK, 1)
				.add(Attributes.ATTACK_SPEED, 1)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 20);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<DimetrodonEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return 1;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		if (isInWater()) playAnimation(swimAnim, false);
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1.4D, 3));
		this.targetSelector.addGoal(0, new LeapAtTargetGoal(this, 0.4F));
		this.targetSelector.addGoal(0, new AnimatableAngerMeleeAttackGoal(this, biteAnim, BITE_ATTACK_ID, 12, 14));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(DimetrodonEntity.class));
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
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TYPE_ID, 0);
	}
	
	private int getRandomDimetrodonType() {
		return this.random.nextInt(4);
	}
	
	public int getDimetrodonType() {
		return MathHelper.clamp(this.entityData.get(TYPE_ID), 0, 4);
	}

	public void setDimetrodonType(int type) {
		this.entityData.set(TYPE_ID, type);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("DimetrodonType", this.getDimetrodonType());
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);		
		setDimetrodonType(nbt.getInt("DimetrodonType"));
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
	public String getOwnerMDFileName() {
		return DIMETRODON_MDF_NAME;
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
	
	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
		int randDimetrodonType = getRandomDimetrodonType();
		
		if (entityData instanceof DimetrodonData) randDimetrodonType = ((DimetrodonData) entityData).dimetrodonType;
		else entityData = new DimetrodonData(randDimetrodonType);
		
		setDimetrodonType(randDimetrodonType);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
	}

	@Override
	public DimetrodonEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
		DimetrodonEntity offspring = CAEntityTypes.DIMETRODON.get().create(world);

		assert offspring != null;
		offspring.setDimetrodonType(((DimetrodonEntity) mate).getDimetrodonType());

		return offspring;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<DimetrodonEntity>> getWrappedControllers() {
		return dimetrodonControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return dimetrodonAnimations;
	}
	
	private class DimetrodonData extends AgeableData {
		public final int dimetrodonType;

		private DimetrodonData(int dimetrodonType) {
			super(true);
			this.dimetrodonType = dimetrodonType;
		}
	}
}
