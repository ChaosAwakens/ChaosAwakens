package io.github.chaosawakens.common.entity.neutral.land.gator;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.goals.neutral.AnimatableAngerMeleeAttackGoal;
import io.github.chaosawakens.common.entity.base.AnimatableAngerableAnimalEntity;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CASoundEvents;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
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

import javax.annotation.Nullable;
import java.util.Random;

public class CrystalGatorEntity extends AnimatableAngerableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<CrystalGatorEntity>> crystalGatorControllers = new ObjectArrayList<WrappedAnimationController<CrystalGatorEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> crystalGatorAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private static final DataParameter<Integer> TYPE_ID = EntityDataManager.defineId(CrystalGatorEntity.class, DataSerializers.INT);
	private final WrappedAnimationController<CrystalGatorEntity> mainController = createMainMappedController("crystalgatormaincontroller");
	private final WrappedAnimationController<CrystalGatorEntity> attackController = createMappedController("crystalgatorattackcontroller", this::attackPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder swimAnim = new SingletonAnimationBuilder(this, "Swim", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder biteAnim = new SingletonAnimationBuilder(this, "Bite Attack", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
	private static final RangedInteger ANGER_TIME_RANGE = TickRangeConverter.rangeOfSeconds(40, 80);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.COD, Items.PUFFERFISH, Items.SALMON, Items.TROPICAL_FISH);
	private static final byte BITE_ATTACK_ID = 1;
	public static final String CRYSTAL_GATOR_MDF_NAME = "gator";
	
	public CrystalGatorEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 16)
				.add(Attributes.ATTACK_DAMAGE, 7)
				.add(Attributes.ATTACK_KNOCKBACK, 1.5)
				.add(Attributes.ATTACK_SPEED, 1)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 8);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<CrystalGatorEntity> getMainWrappedController() {
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
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.targetSelector.addGoal(0, new LeapAtTargetGoal(this, 0.4F));
		this.targetSelector.addGoal(0, new AnimatableAngerMeleeAttackGoal(this, biteAnim, BITE_ATTACK_ID, 20, 24));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(CrystalGatorEntity.class, EmeraldGatorEntity.class));
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
	
	private int getRandomCrystalGatorType() {
		return this.random.nextInt(6);
	}
	
	public int getCrystalGatorType() {
		return MathHelper.clamp(this.entityData.get(TYPE_ID), 0, 6);
	}

	public void setCrystalGatorType(int type) {
		this.entityData.set(TYPE_ID, type);
	}
	
	public static boolean checkCrystalGatorSpawnRules(EntityType<? extends AnimalEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
		return world.getBlockState(blockPos.below()).is(CABlocks.CRYSTAL_GRASS_BLOCK.get()) && world.getRawBrightness(blockPos, 0) > 8;
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
		return CRYSTAL_GATOR_MDF_NAME;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return CASoundEvents.CRYSTAL_GATOR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.CRYSTAL_GATOR_DEATH.get();
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
	public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, ILivingEntityData pSpawnData, CompoundNBT pDataTag) {
		int randCrystalGatorType = getRandomCrystalGatorType();
		
		if (pSpawnData instanceof CrystalGatorData) randCrystalGatorType = ((CrystalGatorData) pSpawnData).crystalGatorType;
		else pSpawnData = new CrystalGatorData(randCrystalGatorType);
		
		setCrystalGatorType(randCrystalGatorType);
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	@Override
	public CrystalGatorEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		CrystalGatorEntity offspring = CAEntityTypes.CRYSTAL_GATOR.get().create(pServerLevel);

		assert offspring != null;
		offspring.setCrystalGatorType(((CrystalGatorEntity) pMate).getCrystalGatorType());

		return offspring;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<CrystalGatorEntity>> getWrappedControllers() {
		return crystalGatorControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return crystalGatorAnimations;
	}
	
	@Override
	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isMoving() && !isAttacking() && !isInWaterOrBubble()) playAnimation(getIdleAnim(), false);
		if (getWalkAnim() != null && isMoving() && !isAttacking() && !isInWaterOrBubble()) playAnimation(getWalkAnim(), false);
		if (isInWaterOrBubble()) playAnimation(swimAnim, false);
	}
	
	private class CrystalGatorData extends AgeableData {
		public final int crystalGatorType;

		private CrystalGatorData(int crystalGatorType) {
			super(true);
			this.crystalGatorType = crystalGatorType;
		}
	}
}
