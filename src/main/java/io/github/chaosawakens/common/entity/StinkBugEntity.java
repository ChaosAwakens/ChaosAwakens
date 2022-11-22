package io.github.chaosawakens.common.entity;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CAParticleTypes;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
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

public class StinkBugEntity extends AnimatableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<>(this, "stinkbugcontroller", animationInterval(), this::predicate);
	public static final DataParameter<Integer> DATA_TYPE_ID = EntityDataManager.defineId(StinkBugEntity.class, DataSerializers.INT);

	public StinkBugEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 8)
				.add(Attributes.MOVEMENT_SPEED, 0.15D)
				.add(Attributes.FOLLOW_RANGE, 8);
	}
	
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("StinkBugType", this.getStinkBugType());
	}

	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		this.setStinkBugType(nbt.getInt("StinkBugType"));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TYPE_ID, 0);
	}

	public int getStinkBugType() {
		return MathHelper.clamp(this.entityData.get(DATA_TYPE_ID), 0, 7);
	}

	public void setStinkBugType(int type) {
		this.entityData.set(DATA_TYPE_ID, type);
	}

	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.stink_bug.walking_animation", true));
			return PlayState.CONTINUE;
		}

		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.stink_bug.idle_animation", true));
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		if (random.nextFloat() < 0.3F) {
			for (int angle = 0; angle < 360; angle++) {
				if (angle % 180 == 0) {
					for (int count = 0; count < 2; count++) {
						if (level.random.nextInt() % 50 == 0) {
							level.addParticle(CAParticleTypes.FART.get(), getX(), this.getRandomY(), getZ(), level.random.nextBoolean() ? 0.01D : -0.01D, 0.03D, level.random.nextBoolean() ? 0.01D : -0.01D);
						}
					}
				}
			}
		}
	}
	
	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
		return null;
	}

	public boolean hurt(DamageSource damageSource, float damage) {
		if (!damageSource.isMagic() && damageSource.getDirectEntity() instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity) damageSource.getDirectEntity();
			if (!damageSource.isExplosion()) livingentity.addEffect(new EffectInstance(new EffectInstance(Effects.CONFUSION, 200, 0)));
		}
		return super.hurt(damageSource, damage);
	}
	
	static class StinkBugData extends AgeableEntity.AgeableData {
		public final int stinkBugType;

		private StinkBugData(int stinkBugType) {
			super(true);
			this.stinkBugType = stinkBugType;
		}
	}

	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
		int i = this.getRandomStinkBugType(world);
		if (entityData instanceof StinkBugEntity.StinkBugData) i = ((StinkBugData) entityData).stinkBugType;
		else entityData = new StinkBugEntity.StinkBugData(i);
		this.setStinkBugType(i);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
	}

	private int getRandomStinkBugType(IWorld world) {
		return this.random.nextInt(7);
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
