package io.github.chaosawakens.common.entity.creature.water.fish;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.base.AnimatableGroupFishEntity;
import io.github.chaosawakens.common.registry.CAItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SparkFishEntity extends AnimatableGroupFishEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final WrappedAnimationController<SparkFishEntity> mainController = new WrappedAnimationController<SparkFishEntity>(this, createMainMappedController("sparkfishmaincontroller"));
	private final SingletonAnimationBuilder idleAnim = new SingletonAnimationBuilder(this, "Idle", EDefaultLoopTypes.LOOP);
	private final SingletonAnimationBuilder swimAnim = new SingletonAnimationBuilder(this, "Swim", EDefaultLoopTypes.LOOP);

	public SparkFishEntity(EntityType<? extends AbstractFishEntity> type, World world) {
		super(type, world);
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 8.0D)
				.add(Attributes.MOVEMENT_SPEED, 1.3D)
				.add(Attributes.FOLLOW_RANGE, 8.0D);
	}
	
	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public AnimationController<? extends IAnimatableEntity> getMainController() {
		return mainController.getWrappedController();
	}
	
	@Override
	public WrappedAnimationController<? extends IAnimatableEntity> getMainWrappedController() {
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
	protected ItemStack getBucketItemStack() {
		return new ItemStack(CAItems.SPARK_FISH_BUCKET.get());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<AnimationController<SparkFishEntity>> getControllers() {
		return new ObjectArrayList<AnimationController<SparkFishEntity>>(1);
	}
}
