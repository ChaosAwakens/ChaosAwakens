package io.github.chaosawakens.common.entity.creature.water.fish;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.base.AnimatableGroupFishEntity;
import io.github.chaosawakens.common.registry.CAItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WoodFishEntity extends AnimatableGroupFishEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<WoodFishEntity>> woodFishControllers = new ObjectArrayList<WrappedAnimationController<WoodFishEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> woodFishAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private final WrappedAnimationController<WoodFishEntity> mainController = createMainMappedController("woodfishmaincontroller");
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder swimAnim = new SingletonAnimationBuilder(this, "Swim", EDefaultLoopTypes.LOOP);
	public static final String WOOD_FISH_MDF_NAME = "wood_fish";

	public WoodFishEntity(EntityType<? extends AbstractFishEntity> type, World world) {
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 8)
				.add(Attributes.MOVEMENT_SPEED, 0.7D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
				.add(Attributes.FOLLOW_RANGE, 8.0D);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<WoodFishEntity> getMainWrappedController() {
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
		super.registerGoals();
		this.goalSelector.addGoal(1, new AvoidEntityGoal<RockFishEntity>(this, RockFishEntity.class, 12.0F, 1.3D, 1.5D, EntityPredicates.NO_SPECTATORS::test));
	}

	@Override
	public SingletonAnimationBuilder getIdleAnim() {
		return idleAnim;
	}

	@Override
	public SingletonAnimationBuilder getSwimAnim() {
		return idleAnim;
	}

	@Override
	public SingletonAnimationBuilder getDeathAnim() {
		return null;
	}

	@Override
	public String getOwnerMDFileName() {
		return WOOD_FISH_MDF_NAME;
	}

	@Override
	public int getMaxAirSupply() {
		return 450;
	}

	@Override
	protected ItemStack getBucketItemStack() {
		return new ItemStack(CAItems.WOOD_FISH_BUCKET.get());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<WoodFishEntity>> getWrappedControllers() {
		return woodFishControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return woodFishAnimations;
	}
}
