package io.github.chaosawakens.common.entity.creature.water;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.controllers.body.base.SmoothBodyController;
import io.github.chaosawakens.common.entity.ai.controllers.movement.water.WhaleMovementController;
import io.github.chaosawakens.common.entity.ai.goals.passive.water.whale.WhaleBreatheGoal;
import io.github.chaosawakens.common.entity.base.AnimatableWaterMobEntity;
import io.github.chaosawakens.common.entity.creature.water.fish.RockFishEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.FollowBoatGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class WhaleEntity extends AnimatableWaterMobEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<WhaleEntity>> whaleControllers = new ObjectArrayList<WrappedAnimationController<WhaleEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> whaleAnimations = new ObjectArrayList<IAnimationBuilder>(2);
	private final WrappedAnimationController<WhaleEntity> mainController = createMainMappedController("whalemaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder swimAnim = new SingletonAnimationBuilder(this, "Swim", EDefaultLoopTypes.LOOP);
	public static final String WHALE_MDF_NAME = "whale";
	
	public WhaleEntity(EntityType<? extends WaterMobEntity> type, World world) {
		super(type, world);
		this.moveControl = new WhaleMovementController(this);
		this.lookControl = new DolphinLookController(this, 1);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 120)
				.add(Attributes.MOVEMENT_SPEED, 2.5D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 50D)
				.add(Attributes.FOLLOW_RANGE, 18);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<WhaleEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return 20;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		if (isStuck()) playAnimation(idleAnim, false);
		else playAnimation(swimAnim, false);
		return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1.15D, 2));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, RockFishEntity.class, 8.0F, 1.0D, 1.0D));
	}
	
	public static boolean checkWhaleSpawnRules(EntityType<WhaleEntity> rockfish, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
		if (pos.getY() > 70 && pos.getY() < world.getSeaLevel()) {
			Optional<RegistryKey<Biome>> targetBiome = world.getBiomeName(pos);
			return (Objects.equals(targetBiome, Optional.of(Biomes.OCEAN)) || Objects.equals(targetBiome, Optional.of(Biomes.DEEP_OCEAN))) && world.getFluidState(pos).is(FluidTags.WATER);
		} else return false;
	}

	@Override
	public SingletonAnimationBuilder getIdleAnim() {
		return idleAnim;
	}

	@Override
	public SingletonAnimationBuilder getSwimAnim() {
		return swimAnim;
	}

	@Override
	public SingletonAnimationBuilder getDeathAnim() {
		return null;
	}

	@Override
	public String getOwnerMDFileName() {
		return WHALE_MDF_NAME;
	}

	@Override
	public boolean canBeKnockedBack() {
		return false;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public int getMaxAirSupply() {
		return 5000;
	}

	@Override
	public int getHeadRotSpeed() {
		return 2;
	}

	@Override
	protected BodyController createBodyControl() {
		return new SmoothBodyController(this);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<WhaleEntity>> getWrappedControllers() {
		return whaleControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return whaleAnimations;
	}

	@Override
	protected void handleBaseAnimations() {
		if (isStuck()) playAnimation(idleAnim, false);
		else playAnimation(swimAnim, false);
	}
}
