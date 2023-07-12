package io.github.chaosawakens.common.entity.creature.land;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.api.entity.ITeleporterMob;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AntEntity extends AnimatableAnimalEntity implements ITeleporterMob {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<AntEntity>> antControllers = new ObjectArrayList<WrappedAnimationController<AntEntity>>(1);
	private final WrappedAnimationController<AntEntity> mainController = createMainMappedController("antmaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder walkAnim = new SingletonAnimationBuilder(this, "Walk", EDefaultLoopTypes.LOOP);
	private final BooleanValue tpConfig;
	private final RegistryKey<World> targetDimension;

	public AntEntity(EntityType<? extends AnimalEntity> type, World world, BooleanValue tpConfig, RegistryKey<World> targetDimension) {
		super(type, world);
		this.tpConfig = tpConfig;
		this.targetDimension = targetDimension;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 1)
				.add(Attributes.MOVEMENT_SPEED, 0.15)
				.add(Attributes.FOLLOW_RANGE, 8);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<AntEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public int animationInterval() {
		return 1;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(1, new LookRandomlyGoal(this));
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
	protected boolean shouldDropExperience() {
		return false;
	}

	@Override
	public void baseTick() {
		super.baseTick();

		if (isAlive()) {
			if (isInWaterRainOrBubble() || isInLava()) {
				setAirSupply(0);
				hurt(DamageSource.DROWN, Integer.MAX_VALUE);
			}
		}
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
		return handleDimensionTeleporting(pPlayer, pHand, level, tpConfig, targetDimension);
	}

	@Override
	public AgeableEntity getBreedOffspring(ServerWorld pServerLevel, AgeableEntity pMate) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<AntEntity>> getWrappedControllers() {
		return antControllers;
	}
}