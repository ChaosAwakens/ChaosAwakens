package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.api.IAnimatableEntity;
import io.github.chaosawakens.common.entity.base.AnimatableCardinallyCapableMonsterEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CaveFisherEntity extends AnimatableCardinallyCapableMonsterEntity implements IAnimatableEntity {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<>(this, "cavefishercontroller", animationInterval(), this::predicate);

	public CaveFisherEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 20)
				.add(Attributes.MOVEMENT_SPEED, 1.2D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.4D)
				.add(Attributes.FOLLOW_RANGE, 30);
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cave_fisher.idle_animation", true));
			return PlayState.CONTINUE;
		}
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cave_fisher.walking_animation", true));
			return PlayState.CONTINUE;
		}
		if (this.getMovingOnWall()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cave_fisher.climb_animation", true));
		}
		if (this.getMovingOnCeiling()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cave_fisher.walking_upsidedown_animation", true));
		}
		if (this.getAttacking()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cave_fisher.attack_animation", false));
		}
		return PlayState.CONTINUE;
	}
	
	@Override
	public void manageAttack(LivingEntity target) {
		
	}
	
	@Override
	public int animationInterval() {
		return 0;
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}
}
