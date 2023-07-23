package io.github.chaosawakens.common.entity.ai.goals.hostile.air;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WaspEntity extends AnimatableMonsterEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final ObjectArrayList<WrappedAnimationController<WaspEntity>> waspControllers = new ObjectArrayList<WrappedAnimationController<WaspEntity>>(1);
	private final ObjectArrayList<IAnimationBuilder> waspAnimations = new ObjectArrayList<IAnimationBuilder>(1);
	private final WrappedAnimationController<WaspEntity> mainController = createMainMappedController("waspmaincontroller");
	
	public WaspEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveControl = new FlyingMovementController(this, 180, true); //TODO Custom move control
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 80)
				.add(Attributes.ARMOR, 10)
				.add(Attributes.FLYING_SPEED, 1.25F)
				.add(Attributes.MOVEMENT_SPEED, 0.4F)
				.add(Attributes.ATTACK_DAMAGE, 12)
				.add(Attributes.FOLLOW_RANGE, 36);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
	
	@Override
	public WrappedAnimationController<WaspEntity> getMainWrappedController() {
		return mainController;
	}

	@Override
	public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}
	
	public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public int animationInterval() {
		return 2;
	}

	@Override
	public void manageAttack(LivingEntity target) {
		if (target.isAlive() && target.canBeAffected(new EffectInstance(Effects.POISON))) target.addEffect(new EffectInstance(Effects.POISON, 60 + random.nextInt(120 - 60), 2));
	}

	@Override
	public SingletonAnimationBuilder getIdleAnim() {
		return null;
	}

	@Override
	public SingletonAnimationBuilder getWalkAnim() {
		return null;
	}

	@Override
	public SingletonAnimationBuilder getDeathAnim() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ObjectArrayList<WrappedAnimationController<WaspEntity>> getWrappedControllers() {
		return waspControllers;
	}
	
	@Override
	public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
		return waspAnimations;
	}
}
