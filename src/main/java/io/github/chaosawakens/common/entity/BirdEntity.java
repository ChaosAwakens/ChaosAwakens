package io.github.chaosawakens.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
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

import javax.annotation.Nullable;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.entity.ai.RandomFlyingGoal;
import io.github.chaosawakens.common.entity.robo.RoboEntity;

import java.util.Random;

public class BirdEntity extends ParrotEntity implements IAnimatable, IFlyingAnimal, IUtilityHelper {
	private final AnimationFactory factory = new AnimationFactory(this);
	private static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(BirdEntity.class, DataSerializers.INT);
	private float flap;
	private float flapSpeed;
	private float flapping = 1.0F;

	public BirdEntity(EntityType<? extends ParrotEntity> p_i50251_1_, World p_i50251_2_) {
		super(p_i50251_1_, p_i50251_2_);
		this.moveControl = new FlyingMovementController(this, 10, true);
		this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
		this.setPathfindingMalus(PathNodeType.LAVA, -1.0F);
		this.setPathfindingMalus(PathNodeType.DAMAGE_CACTUS, -1.0F);
		this.setPathfindingMalus(PathNodeType.DANGER_OTHER, -1.0F);
		this.setPathfindingMalus(PathNodeType.WALKABLE, 1.0F);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 5)
				.add(Attributes.MOVEMENT_SPEED, 0.6F)
				.add(Attributes.FLYING_SPEED, 1.3F)
				.add(Attributes.LUCK, 1.0F)
				.add(Attributes.FOLLOW_RANGE, 12);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, RoboEntity.class, 1.0F, 0.25D, 0.45D));
		this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, MonsterEntity.class, 1.0F, 0.25D, 0.45D));
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(2, new SitGoal(this));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 1.0F) {
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(4, new RandomFlyingGoal(this, 2.0D, 1, true) {
			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(1, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new FollowMobGoal(this, 2.0D, 4.0F, 8.0F));
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.dead) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bird.idle", true));
			return PlayState.CONTINUE;
		}
		if (this.onGround && !event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bird.idle", true));
		}
		if (event.isMoving() && this.onGround) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bird.walk", true));
			return PlayState.CONTINUE;
		}
		if (this.isFlying()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.bird.fly", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected PathNavigator createNavigation(World w) {
		FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, w);
		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanFloat(true);
		flyingpathnavigator.setCanPassDoors(true);
		return flyingpathnavigator;
	}

	@Override
	protected float getStandingEyeHeight(Pose p, EntitySize s) {
		return s.height * 0.6F;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.PARROT_HURT;
	}

	@Override
	protected float playFlySound(float p_191954_1_) {
		this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
		return p_191954_1_ + this.flapSpeed / 2.0F;
	}

	@Override
	public boolean canMate(AnimalEntity e) {
		return false;
	}

	@Override
	protected float getSoundVolume() {
		return 0.2F;
	}

	public int getBirdType() {
		return MathHelper.clamp(this.entityData.get(DATA_TYPE_ID), 0, 40);
	}

	private void setBirdType(int id) {
		this.entityData.set(DATA_TYPE_ID, id);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TYPE_ID, 0);
	}

	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("BirdType", this.getBirdType());
	}

	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		this.setBirdType(nbt.getInt("BirdType"));
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "birdcontroller", 0, this::predicate));
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		super.playStepSound(p_180429_1_, p_180429_2_);
	}

	public static boolean checkBirdSpawnRules(EntityType<BirdEntity> p, IWorld w, SpawnReason reason, BlockPos pos, Random random) {
		BlockState blockstate = w.getBlockState(pos.below());
		return (blockstate.is(BlockTags.LEAVES) || blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(BlockTags.LOGS) || blockstate.is(Blocks.AIR)) && w.getRawBrightness(pos, 0) > 8;
	}

	private void calculateFlapping() {
		float oFlap = this.flap;
		float oFlapSpeed = this.flapSpeed;
		this.flapSpeed = (float) ((double) this.flapSpeed + (double) (!this.onGround && !this.isPassenger() ? 4 : -1) * 0.3D);
		this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
		if (!this.onGround && this.flapping < 1.0F) this.flapping = 1.0F;

		this.flapping = (float) ((double) this.flapping * 0.9D);
		Vector3d vector3d = this.getDeltaMovement();
		if (!this.onGround && vector3d.y < 0.0D) this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));

		this.flap += this.flapping * 2.0F;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.calculateFlapping();
	}

	@Override
	public boolean checkSpawnRules(IWorld p_213380_1_, SpawnReason p_213380_2_) {
		return true;
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity p, Hand hand) {
		return super.mobInteract(p, hand);
	}

	@Override
	@Nullable
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}

	@Override
	protected boolean makeFlySound() {
		return true;
	}

	@Override
	protected float getVoicePitch() {
		return BirdEntity.getPitch(this.random);
	}

	public static float getPitch(Random r) {
		return (r.nextFloat() - r.nextFloat()) * 0.25F + 1.2F;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public Vector3d getLeashOffset() {
		return new Vector3d(0.0D, (0.5F * this.getEyeHeight()), (this.getBbWidth() * 0.4F));
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	@Override
	public SoundCategory getSoundSource() {
		return SoundCategory.NEUTRAL;
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	public boolean causeFallDamage(float f, float f2) {
		return false;
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, ILivingEntityData entityData, CompoundNBT compoundNBT) {
		int i = this.setBirdType(world);
		if (entityData instanceof BirdEntity.BirdData) i = ((BirdData) entityData).birdType;
		else entityData = new BirdEntity.BirdData(i);
		this.setBirdType(i);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, compoundNBT);
	}

	protected int setBirdType(IServerWorld world) {
		Biome biome = world.getBiome(this.blockPosition());
		int i = this.random.nextInt(5);
		if (biome.getBiomeCategory() == Biome.Category.BEACH) i = 40;
		return i;
	}

	static class BirdData extends AgeableEntity.AgeableData {
		public final int birdType;
		private BirdData(int birdType) {
			super(true);
			this.birdType = birdType;
		}
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
