package io.github.chaosawakens.common.entity;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CrystalGatorEntity extends AnimatableAnimalEntity implements IAngerable {
	private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.defineId(CrystalGatorEntity.class, DataSerializers.INT);
	private static final RangedInteger ANGER_TIME_RANGE = TickRangeConverter.rangeOfSeconds(20, 69);
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController(this, "crystalgatorcontroller", animationInterval(), this::predicate);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.COD, Items.PUFFERFISH, Items.SALMON, Items.TROPICAL_FISH);
	private UUID persistentAngerTarget;
	public static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(CrystalGatorEntity.class, DataSerializers.INT);

	public CrystalGatorEntity(EntityType<? extends AnimatableAnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveControl = new MovementController(this);
		this.noCulling = true;
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
	public int animationInterval() {
		return 1;
	}
	
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.emerald_gator.walking_animation", true));
			return PlayState.CONTINUE;
		}
		if (this.getAttacking()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.emerald_gator.bite_animation", false));
			return PlayState.CONTINUE;
		}
		if (this.isSwimming() || this.isInWater() && event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.emerald_gator.swim_animation", true));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.emerald_gator.idle_animation", true));
		return PlayState.CONTINUE;
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new RandomWalkingGoal(this, 1.0));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, false, FOOD_ITEMS));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 0.25, false));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0));
		this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
	}

	public static boolean checkCrystalGatorSpawnRules(EntityType<? extends AnimalEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
		return world.getBlockState(blockPos.below()).is(CABlocks.CRYSTAL_GRASS_BLOCK.get()) && world.getRawBrightness(blockPos, 0) > 8;
	}

	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	public int getGatorType() {
		return MathHelper.clamp(this.entityData.get(DATA_TYPE_ID), 0, 6);
	}

	public void setGatorType(int type) {
		this.entityData.set(DATA_TYPE_ID, type);
	}

	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
		int i = this.getRandomGatorType(world);
		if (entityData instanceof GatorData) i = ((GatorData) entityData).gatorType;
		else entityData = new GatorData(i);
		this.setGatorType(i);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
	}

	@SuppressWarnings("unused")
	private int getRandomGatorType(IWorld world) {
		Biome biome = world.getBiome(this.blockPosition());
		int i = this.random.nextInt(6);
		return i;
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ANGER_TIME, 0);
		this.entityData.define(DATA_TYPE_ID, 0);
	}

	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		this.addPersistentAngerSaveData(compound);
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);

		if (!level.isClientSide) this.readPersistentAngerSaveData((ServerWorld) this.level, compound);
	}

	public boolean hurt(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) return false;
		else {
			Entity entity = source.getEntity();
			if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) amount = (amount + 1.0F) / 2.0F;
			return super.hurt(source, amount);
		}
	}

	public boolean doHurtTarget(Entity entityIn) {
		boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
		if (flag) this.doEnchantDamageEffects(this, entityIn);
		return flag;
	}

	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(ANGER_TIME);
	}

	public void setRemainingPersistentAngerTime(int time) {
		this.entityData.set(ANGER_TIME, time);
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(ANGER_TIME_RANGE.randomValue(this.random));
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public void setPersistentAngerTarget(@Nullable UUID target) {
		this.persistentAngerTarget = target;
	}

	public boolean canBeLeashed(PlayerEntity player) {
		return !this.isAngry() && super.canBeLeashed(player);
	}

	@OnlyIn(Dist.CLIENT)
	public Vector3d getLeashOffset() {
		return new Vector3d(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
	}

	public void tick() {
		super.tick();
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "crystalgatorcontroller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public CrystalGatorEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
		CrystalGatorEntity e = (CrystalGatorEntity) mate;
		CrystalGatorEntity e1 = CAEntityTypes.CRYSTAL_GATOR.get().create(world);
		e1.setGatorType(e.getGatorType());
		return e1;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return CASoundEvents.EMERALD_GATOR_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.EMERALD_GATOR_DEATH.get();
	}

	static class GatorData extends AgeableEntity.AgeableData {
		public final int gatorType;

		private GatorData(int gatorType) {
			super(true);
			this.gatorType = gatorType;
		}
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}
}
