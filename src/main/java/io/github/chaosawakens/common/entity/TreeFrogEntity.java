package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CALootTables;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class TreeFrogEntity extends AnimatableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<>(this, "treefrogcontroller", animationInterval(), this::predicate);
	public static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(TreeFrogEntity.class, DataSerializers.INT);

	public TreeFrogEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("TreeFrogType", this.getTreeFrogType());
	}

	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		this.setTreeFrogType(nbt.getInt("TreeFrogType"));
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new PanicGoal(this, 1.2D));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 6)
				.add(Attributes.MOVEMENT_SPEED, 0.15D)
				.add(Attributes.JUMP_STRENGTH, 2.0D)
				.add(Attributes.FOLLOW_RANGE, 8.0D);
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TYPE_ID, 0);
	}

	public int getTreeFrogType() {
		return MathHelper.clamp(this.entityData.get(DATA_TYPE_ID), 0, 99);
	}

	public void setTreeFrogType(int type) {
		if (type == 99) {
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(8.0D);
			this.goalSelector.addGoal(4, new TreeFrogEntity.EvilAttackGoal(this));
			this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		}
		this.entityData.set(DATA_TYPE_ID, type);
	}

	public static boolean checkTreeFrogSpawnRules(EntityType<TreeFrogEntity> treeFrogEntity, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
		BlockState blockstate = world.getBlockState(pos.below());
		Biome biome = world.getBiome(pos.below());
		return ((blockstate.is(BlockTags.LEAVES) && !(biome.getBiomeCategory() == Biome.Category.NETHER))
				|| (blockstate.is(BlockTags.WART_BLOCKS) &&
				world.getRawBrightness(pos, 0) > 8 && biome.getBiomeCategory() == Biome.Category.NETHER));
	}

	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
		int i = this.setTreeFrogType(world);
		if (entityData instanceof TreeFrogEntity.TreeFrogData) i = ((TreeFrogData) entityData).treeFrogType;
		else entityData = new TreeFrogEntity.TreeFrogData(i);
		this.setTreeFrogType(i);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
	}

	private int setTreeFrogType(IWorld world) {
		Biome biome = world.getBiome(this.blockPosition());
		int i = this.random.nextInt(8);
		if (biome.getBiomeCategory() == Biome.Category.NETHER) i = 99;
		return i;
	}

	@Override
	protected ResourceLocation getDefaultLootTable() {
		if (this.getTreeFrogType() == 99) {
			return CALootTables.TREE_FROG_NETHER;
		}
		return CALootTables.TREE_FROG_OVERWORLD;
	}

	public boolean doHurtTarget(Entity entity) {
		if (this.getTreeFrogType() == 99) {
			this.playSound(SoundEvents.RABBIT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			return entity.hurt(DamageSource.mobAttack(this), 8.0F);
		} else return entity.hurt(DamageSource.mobAttack(this), 3.0F);
	}

	public boolean fireImmune() {
		return this.getTreeFrogType() == 99;
	}

	@Nullable
	@Override
	public TreeFrogEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
		return null;
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.tree_frog.jump_animation", true));
			return PlayState.CONTINUE;
		} else if (!event.isMoving()) return PlayState.CONTINUE;
		return PlayState.CONTINUE;
	}

	public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
		return false;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
	}

	@Override
	protected void jumpFromGround() {
		float f = this.getJumpPower();
		if (this.hasEffect(Effects.JUMP)) f += 0.1F * (float) (Objects.requireNonNull(this.getEffect(Effects.JUMP)).getAmplifier() + 1);

		Vector3d vector3d = this.getDeltaMovement();
		this.setDeltaMovement(vector3d.x, f, vector3d.z);
		if (this.isSprinting()) {
			float f1 = this.yRot * ((float) Math.PI / 180F);
			this.setDeltaMovement(this.getDeltaMovement().add((-MathHelper.sin(f1) * 0.2F), 0.0D, (MathHelper.cos(f1) * 0.2F)));
		}

		this.hasImpulse = true;
		ForgeHooks.onLivingJump(this);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	static class TreeFrogData extends AgeableEntity.AgeableData {
		public final int treeFrogType;

		private TreeFrogData(int treeFrogType) {
			super(true);
			this.treeFrogType = treeFrogType;
		}
	}

	static class EvilAttackGoal extends MeleeAttackGoal {
		public EvilAttackGoal(TreeFrogEntity entity) {
			super(entity, 1.4D, true);
		}

		protected double getAttackReachSqr(LivingEntity livingEntity) {
			return (4.0F + livingEntity.getBbWidth());
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
