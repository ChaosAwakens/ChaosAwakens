package io.github.chaosawakens.common.entity.creature.land;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BeaverEntity extends AnimatableAnimalEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private static final DataParameter<Boolean> CHIPPING = EntityDataManager.defineId(BeaverEntity.class, DataSerializers.BOOLEAN);
	private final AnimationController<BeaverEntity> mainController = createMainMappedController("beavermaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder gnawAnim = new SingletonAnimationBuilder(this, "Gnaw", EDefaultLoopTypes.LOOP);

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
	public AnimationController<? extends IAnimatableEntity> getMainController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return 3;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
/*		this.goalSelector.addGoal(0, new BreakBlockGoal(Blocks.OAK_LOG, this, 1.2D, 20) {
			@Override
			public void tick() {
				super.tick();
				playAnimation(gnawAnim);
			}
		});*/
		this.goalSelector.addGoal(0, new RandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.set(CHIPPING, false);
	}
	
	public boolean getChipping() {
		return this.entityData.get(CHIPPING);
	}
	
	public void setChipping(boolean chipping) {
		this.entityData.set(CHIPPING, chipping);
	}

	@Override
	public SingletonAnimationBuilder getIdleAnim() {
		return null;
	}

	@Override
	public SingletonAnimationBuilder getWalkAnim() {
		return idleAnim;
	}

	@Override
	public SingletonAnimationBuilder getDeathAnim() {
		return walkAnim;
	}

	@Override
	public BeaverEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<AnimationController<BeaverEntity>> getControllers() {
		return new ObjectArrayList<AnimationController<BeaverEntity>>(1);
	}
}
