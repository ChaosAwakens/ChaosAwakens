package io.github.chaosawakens.common.entity.creature.land.carrotpig;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.base.AnimatableRideableAnimalEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.manager.CAConfigManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EnchantedGoldenCarrotPigEntity extends AnimatableRideableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<EnchantedGoldenCarrotPigEntity>> enchantedGoldenCarrotPigEntity = new ObjectArrayList<WrappedAnimationController<EnchantedGoldenCarrotPigEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> enchantedGoldenCarrotPigAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private final WrappedAnimationController<EnchantedGoldenCarrotPigEntity> mainController = createMainMappedController("enchantedgoldencarrotpigmaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.POTATO, Items.BEETROOT);
	public static final String ENCHANTED_CARROT_PIG_MDF_NAME = "carrot_pig";

	public EnchantedGoldenCarrotPigEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.25D)
				.add(Attributes.FOLLOW_RANGE, 10);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<EnchantedGoldenCarrotPigEntity> getMainWrappedController() {
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
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<MonsterEntity>(this, MonsterEntity.class, 12.0F, 1.2D, 2.0D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(CAItems.BEETROOT_ON_A_STICK.get()), false));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, false, FOOD_ITEMS));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}
	
	public void aiStep() {
		if (this.level.isClientSide) this.level.addParticle(ParticleTypes.INSTANT_EFFECT, false, this.getRandomX(0.5D), this.getY(0.85D), this.getRandomZ(0.5D), -1000, -1000, -1000);
		super.aiStep();
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
		return ENCHANTED_CARROT_PIG_MDF_NAME;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.PIG_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.PIG_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PIG_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}
	
	@Override
	protected boolean canPlayerRiderControl(PlayerEntity passenger) {
		return EntityUtil.isHoldingItem(passenger, CAItems.GOLDEN_BEETROOT_ON_A_STICK.get());
	}

	@Override
	protected SoundEvent getSaddleSoundEvent() {
		return SoundEvents.PIG_SADDLE;
	}
	
	@Override
	public ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
		ItemStack handStack = playerIn.getItemInHand(hand);
		
		if (handStack.getItem() == Items.SHEARS && canShear()) {
			this.level.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
			if (this.level instanceof ServerWorld) ((ServerWorld) this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
			EntityUtil.convertEntity(this, EntityType.PIG, this.level);
			
			this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(1.0D), this.getZ(), new ItemStack(CAItems.ENCHANTED_GOLDEN_CARROT.get())));
			if (!this.level.isClientSide) handStack.hurtAndBreak(1, playerIn, (heldStack) -> heldStack.broadcastBreakEvent(hand));
			
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		} else return super.mobInteract(playerIn, hand);
	}
	
	private boolean canShear() {
		return isAlive() && !isBaby();
	}
	
	@Override
	public boolean isFood(ItemStack pStack) {
		return FOOD_ITEMS.test(pStack);
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
		return CAConfigManager.MAIN_COMMON.enableEnchantedAnimalBreeding.get() ? CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG.get().create(world) : CAEntityTypes.GOLDEN_CARROT_PIG.get().create(world);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<EnchantedGoldenCarrotPigEntity>> getWrappedControllers() {
		return enchantedGoldenCarrotPigEntity;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return enchantedGoldenCarrotPigAnimations;
	}
}
