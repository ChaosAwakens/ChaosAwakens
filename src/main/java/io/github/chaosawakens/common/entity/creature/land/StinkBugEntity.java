package io.github.chaosawakens.common.entity.creature.land;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.registry.CAParticleTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
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
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class StinkBugEntity extends AnimatableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<StinkBugEntity>> stinkBugControllers = new ObjectArrayList<WrappedAnimationController<StinkBugEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> stinkBugAnimations = new ObjectArrayList<IAnimationBuilder>(3);
	private static final DataParameter<Integer> TYPE_ID = EntityDataManager.defineId(StinkBugEntity.class, DataSerializers.INT);
	private static final DataParameter<Boolean> PANICKING = EntityDataManager.defineId(StinkBugEntity.class, DataSerializers.BOOLEAN);
	private final WrappedAnimationController<StinkBugEntity> mainController = createMainMappedController("stinkbugmaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder runAnim = new SingletonAnimationBuilder(this, "Run", EDefaultLoopTypes.LOOP);
	public static final String STINK_BUG_MDF_NAME = "stink_bug";

	public StinkBugEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 8)
				.add(Attributes.MOVEMENT_SPEED, 0.15D)
				.add(Attributes.FOLLOW_RANGE, 8);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<StinkBugEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return isPanicking() ? 1 : 4;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PanicGoal(this, 3.0D) {
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
		this.goalSelector.addGoal(0, new AvoidEntityGoal<MonsterEntity>(this, MonsterEntity.class, 16.0F, 2.2D, 3.0D) {
			@Override
			public void stop() {
				super.stop();
				setPanicking(false);
			}

			@Override
			public void tick() {
				super.tick();

				setPanicking(distanceToSqr(toAvoid) < 109.0D);
				getNavigation().setSpeedModifier(distanceToSqr(toAvoid) < 109.0D ? 3.0D : 2.2D);
			}
		});
		this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 1.1D));
		this.goalSelector.addGoal(1, new SwimGoal(this));
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TYPE_ID, 0);
		this.entityData.define(PANICKING, false);
	}

	private int getRandomStinkBugType(IWorld world) {
		return this.random.nextInt(4);
	}
	
	public int getStinkBugType() {
		return MathHelper.clamp(this.entityData.get(TYPE_ID), 0, 3);
	}

	public void setStinkBugType(int type) {
		this.entityData.set(TYPE_ID, type);
	}

	public boolean isPanicking() {
		return this.entityData.get(PANICKING);
	}

	public void setPanicking(boolean panicking) {
		this.entityData.set(PANICKING, panicking);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putInt("StinkBugType", getStinkBugType());
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		setStinkBugType(nbt.getInt("StinkBugType"));
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
		return STINK_BUG_MDF_NAME;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float damage) {
		if (!damageSource.isMagic() && damageSource.getDirectEntity() instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) damageSource.getDirectEntity();
			if (!damageSource.isExplosion()) attacker.addEffect(new EffectInstance(new EffectInstance(Effects.CONFUSION, 200, 0)));
		}
		return super.hurt(damageSource, damage);
	}
	
	@Override
	public void aiStep() {
		super.aiStep();
		
		//TODO Helper method somewhere in MathUtil
		if (random.nextFloat() < 0.3F) {
			for (int angle = 0; angle < 360; angle++) {
				if (angle % 180 == 0) {
					for (int count = 0; count < level.random.nextInt(4); count++) {
						if (level.random.nextInt() % 50 == 0) {
							level.addParticle(CAParticleTypes.FART.get(), getX(), this.getRandomY(), getZ(), level.random.nextBoolean() ? 0.01D : -0.01D, 0.03D, level.random.nextBoolean() ? 0.01D : -0.01D);
						}
					}
				}
			}
		}
	}
	
	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT nbt) {
		int randStinkBugType = getRandomStinkBugType(world);
		
		if (entityData instanceof StinkBugData) randStinkBugType = ((StinkBugData) entityData).stinkBugType;
		else entityData = new StinkBugData(randStinkBugType);
		
		setStinkBugType(randStinkBugType);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
	}

	@Override
	public StinkBugEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<StinkBugEntity>> getWrappedControllers() {
		return stinkBugControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return stinkBugAnimations;
	}

	@Override
	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isMoving()) playAnimation(getIdleAnim(), false);
		if (getWalkAnim() != null && isMoving() && !isPanicking()) playAnimation(getWalkAnim(), false);
		if (isMoving() && isPanicking()) playAnimation(runAnim, false);
	}

	private class StinkBugData extends AgeableData {
		public final int stinkBugType;

		private StinkBugData(int stinkBugType) {
			super(true);
			this.stinkBugType = stinkBugType;
		}
	}
}
