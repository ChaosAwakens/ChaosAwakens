package io.github.chaosawakens.common.entity;

import java.util.Random;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
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
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CrystalAppleCowEntity extends AnimatableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<>(this, "crystalapplecowcontroller", animationInterval(), this::predicate);

	public CrystalAppleCowEntity(EntityType<? extends CrystalAppleCowEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
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

	protected SoundEvent getAmbientSound() {
		return SoundEvents.COW_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.COW_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.COW_DEATH;
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	public static boolean checkCrystalAppleCowSpawnRules(EntityType<? extends AnimalEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
		return world.getBlockState(blockPos.below()).is(CABlocks.CRYSTAL_GRASS_BLOCK.get()) && world.getRawBrightness(blockPos, 0) > 8;
	}

	public ActionResultType mobInteract(PlayerEntity playerIn, Hand hand) {
		ItemStack itemstack = playerIn.getItemInHand(hand);
		if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
			playerIn.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
			ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, playerIn, Items.MILK_BUCKET.getDefaultInstance());
			playerIn.setItemInHand(hand, itemstack1);
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		} else return super.mobInteract(playerIn, hand);
	}

	public CrystalAppleCowEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
		return CAEntityTypes.CRYSTAL_APPLE_COW.get().create(world);
	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return this.isBaby() ? sizeIn.height * 0.95F : 1.3F;
	}

	public void thunderHit(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
		if (net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, CAEntityTypes.CRYSTAL_CARROT_PIG.get(), (timer) -> {})) {
			CrystalCarrotPigEntity crystalCarrotPigEntity = CAEntityTypes.CRYSTAL_CARROT_PIG.get().create(serverWorld);
			assert crystalCarrotPigEntity != null;
			crystalCarrotPigEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, this.xRot);
			crystalCarrotPigEntity.setNoAi(this.isNoAi());
			crystalCarrotPigEntity.setBaby(this.isBaby());
			if (this.hasCustomName()) {
				crystalCarrotPigEntity.setCustomName(this.getCustomName());
				crystalCarrotPigEntity.setCustomNameVisible(this.isCustomNameVisible());
			}

			crystalCarrotPigEntity.setPersistenceRequired();
			net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, crystalCarrotPigEntity);
			serverWorld.addFreshEntity(crystalCarrotPigEntity);
			this.remove();
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

	@Override
	public int tickTimer() {
		return tickCount;
	}

	@Override
	public int animationInterval() {
		return 4;
	}

	@Override
	public AnimationController<?> getController() {
		return controller;
	}
}
