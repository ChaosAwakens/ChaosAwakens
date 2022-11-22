package io.github.chaosawakens.common.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AppleCowEntity extends AnimatableAnimalEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);
	private static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(AppleCowEntity.class, DataSerializers.INT);
	private final AnimationController<?> controller = new AnimationController<>(this, "applecowcontroller", animationInterval(), this::predicate);

	public AppleCowEntity(EntityType<? extends AppleCowEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.2F)
				.add(Attributes.FOLLOW_RANGE, 10);
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.apple_cow.walking_animation", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.apple_cow.idle_animation", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.COW_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.COW_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.COW_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	public int getAppleCowType() {
		return MathHelper.clamp(this.entityData.get(DATA_TYPE_ID), 0, 1);
	}

	private void setAppleCowType(int id) {
		this.entityData.set(DATA_TYPE_ID, id);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TYPE_ID, 0);
	}

	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("AppleCowType", this.getAppleCowType());
	}

	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		this.setAppleCowType(nbt.getInt("AppleCowType"));
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
		ItemStack itemstack = playerIn.getItemInHand(hand);
		if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
			playerIn.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
			ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, playerIn, Items.MILK_BUCKET.getDefaultInstance());
			playerIn.setItemInHand(hand, itemstack1);
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		} else return super.mobInteract(playerIn, hand);
	}

	@Override
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.95F : 1.3F;
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
		AppleCowEntity entity = CAEntityTypes.APPLE_COW.get().create(world);

		if (this.isHalloweenObtained()) {
			assert entity != null;
			entity.setAppleCowType(1);
		} else {
			assert entity != null;
			entity.setAppleCowType(((AppleCowEntity) ageable).getAppleCowType());
		}
		return entity;
	}

	public boolean isHalloweenObtained() {
		LocalDate localdate = LocalDate.now();
		int month = localdate.get(ChronoField.MONTH_OF_YEAR);
		return (month == 10 && this.random.nextFloat() < 0.05F && CACommonConfig.COMMON.holidayTextures.get());
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, ILivingEntityData entityData, CompoundNBT compoundNBT) {
		int i = this.setAppleCowType();
		if (entityData instanceof AppleCowEntity.AppleCowData) i = ((AppleCowData) entityData).appleCowType;
		else entityData = new AppleCowEntity.AppleCowData(i);
		this.setAppleCowType(i);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, compoundNBT);
	}

	protected int setAppleCowType() {
		int i = 0;
		if (this.isHalloweenObtained()) i = 1;
		return i;
	}

	public void thunderHit(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
		if (this.getAppleCowType() != 1) {
			if (net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, CAEntityTypes.CARROT_PIG.get(), (timer) -> {})) {
				CarrotPigEntity carrotPigEntity = CAEntityTypes.CARROT_PIG.get().create(serverWorld);
				assert carrotPigEntity != null;
				carrotPigEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
				carrotPigEntity.setNoAi(this.isNoAi());
				carrotPigEntity.setBaby(this.isBaby());
				if (this.hasCustomName()) {
					carrotPigEntity.setCustomName(this.getCustomName());
					carrotPigEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				carrotPigEntity.setPersistenceRequired();
				net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, carrotPigEntity);
				serverWorld.addFreshEntity(carrotPigEntity);
				this.remove();
			} else super.thunderHit(serverWorld, lightningBoltEntity);
		} else super.thunderHit(serverWorld, lightningBoltEntity);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	static class AppleCowData extends AgeableEntity.AgeableData {
		public final int appleCowType;

		private AppleCowData(int appleCowType) {
			super(true);
			this.appleCowType = appleCowType;
		}
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}

	@Override
	public int animationInterval() {
		return 2;
	}

	@Override
	public AnimationController<?> getController() {
		return controller;
	}
}
