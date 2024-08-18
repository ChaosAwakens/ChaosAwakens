package io.github.chaosawakens.common.entity.creature.land;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.goals.passive.land.AnimatableBreakBlockGoal;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BeaverEntity extends AnimatableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<BeaverEntity>> beaverControllers = new ObjectArrayList<WrappedAnimationController<BeaverEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> beaverAnimations = new ObjectArrayList<IAnimationBuilder>(3);
	private static final DataParameter<Boolean> PANICKING = EntityDataManager.defineId(BeaverEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> CHIPPING = EntityDataManager.defineId(BeaverEntity.class, DataSerializers.BOOLEAN);
	private final WrappedAnimationController<BeaverEntity> mainController = createMainMappedController("beavermaincontroller");
	private final WrappedAnimationController<BeaverEntity> chopController = createMappedController("beaverchopcontroller", this::chopPredicate);
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder chopAnim = new SingletonAnimationBuilder(this, "Chop", EDefaultLoopTypes.PLAY_ONCE).setWrappedController(chopController);
	public static final String BEAVER_MDF_NAME = "beaver";
	private final ObjectArrayList<Block> mappedDestroyedBlocks = new ObjectArrayList<>();

	public BeaverEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 6)
				.add(Attributes.MOVEMENT_SPEED, 0.15D)
				.add(Attributes.FOLLOW_RANGE, 8);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public WrappedAnimationController<BeaverEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return 1;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		if (isChipping()) playAnimation(idleAnim, true);
		return MathUtil.isBetween(chopAnim.getWrappedAnimProgress(), 1, 73.4D) ? PlayState.STOP : PlayState.CONTINUE;
	}

	public <E extends IAnimatableEntity> PlayState chopPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new AnimatableBreakBlockGoal(this, BlockTags.LOGS, 20, 20.0D, 1.2D, () -> chopAnim, 38, 60) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isPanicking();
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !isPanicking();
			}

			@Override
			public void start() {
				super.start();
				if (hasReachedTarget) setChipping(true);
			}

			@Override
			public void stop() {
				super.stop();
				setChipping(false);
			}

			@Override
			public void tick() {
				if (targetPos == null) return;

				this.hasReachedTarget = targetPos != null && level.getBlockState(targetPos).is(targetBlockTag) && distanceToSqr(targetPos.getX(), targetPos.getY(), targetPos.getZ()) <= distThreshold * distThreshold && !isDeadOrDying();

				if (tickCount % 100 == 0 && (!hasReachedTarget || getNavigation().isStuck())) findValidPos(targetBlockTag);

				if (hasReachedTarget) {
					getNavigation().stop();
					setChipping(true);

					playAnimation(breakAnim.get(), false);
				} else if (targetPos != null && tickCount % 100 == 0) getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.15D);

				if (hasReachedTarget && breakAnim.get().getWrappedAnimProgress() >= animActionStartTick) {
					CAScreenShakeEntity.shakeScreen(level, position(), 10.0F, (float) (breakAnim.get().getWrappedAnimProgress() / 100.0D) / 20.0F, 5, 20);
					getLookControl().setLookAt(new Vector3d(targetPos.getX(), targetPos.getY(), targetPos.getZ()));

					if (level instanceof ServerWorld){
						((ServerWorld) level).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, level.getBlockState(targetPos)), targetPos.getX() + 0.5D, targetPos.getY() + 0.5D, targetPos.getZ() + 0.5D, getRandom().nextInt(10), 0, 0, 0, 1);
						level.destroyBlockProgress(getId(), targetPos, (int) MathUtil.normalizeValues(breakAnim.get().getWrappedAnimProgress() * 25, animActionStartTick, animActionTick, 0, 10));
					}

					if (MathUtil.isBetween(breakAnim.get().getWrappedAnimProgress(), animActionTick - 3, animActionTick)) {
						((ServerWorld) level).sendParticles(new BlockParticleData(ParticleTypes.BLOCK, level.getBlockState(targetPos)), targetPos.getX() + 0.5D, targetPos.getY() + 0.5D, targetPos.getZ() + 0.5D, getRandom().nextInt(80), 0, 0, 0, 1);

						mappedDestroyedBlocks.add(level.getBlockState(targetPos).getBlock());

						level.destroyBlock(targetPos, false);

						CAScreenShakeEntity.shakeScreen(level, position(), 10.0F, 0.12F, 5, 20);
					}
				}
			}

			@Override
			public boolean isInterruptable() {
				return super.isInterruptable() || isPanicking();
			}
		});
		this.goalSelector.addGoal(0, new PanicGoal(this, 2.9D) {
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
		this.goalSelector.addGoal(1, new RandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PANICKING, false);
		this.entityData.define(CHIPPING, false);
	}

	public boolean isPanicking() {
		return this.entityData.get(PANICKING);
	}

	public void setPanicking(boolean chipping) {
		this.entityData.set(PANICKING, chipping);
	}
	
	public boolean isChipping() {
		return this.entityData.get(CHIPPING);
	}
	
	public void setChipping(boolean chipping) {
		this.entityData.set(CHIPPING, chipping);
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
		if (!mappedDestroyedBlocks.isEmpty()) {
			mappedDestroyedBlocks.stream()
					.map(Block::asItem)
					.forEach(this::spawnAtLocation);
		}
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
		return BEAVER_MDF_NAME;
	}

	@Override
	public BeaverEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<BeaverEntity>> getWrappedControllers() {
		return beaverControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return beaverAnimations;
	}

	@Override
	protected void handleBaseAnimations() {
		super.handleBaseAnimations();

		walkAnim.setAnimSpeed(isPanicking() ? 2.8D : 1.0D);
	}
}