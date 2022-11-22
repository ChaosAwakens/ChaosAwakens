package io.github.chaosawakens.common.entity.robo;

import io.github.chaosawakens.common.entity.ai.RoboAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboSniperEntity extends RoboRangedEntity implements IRangedAttackMob {
	private final AnimationFactory factory = new AnimationFactory(this);
	private final AnimationController<?> controller = new AnimationController<>(this, "robosnipercontroller", animationInterval(), this::predicate);

	public RoboSniperEntity(EntityType<? extends RoboEntity> type, World worldIn) {
		super(type, worldIn);
		this.noCulling = true;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 25)
				.add(Attributes.MOVEMENT_SPEED, 0.28D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
				.add(Attributes.ATTACK_SPEED, 10)
				.add(Attributes.ATTACK_DAMAGE, 25)
				.add(Attributes.ATTACK_KNOCKBACK, 3.5D)
				.add(Attributes.FOLLOW_RANGE, 500);
	}

	@Override
	public <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.getAttacking()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_sniper.attacking_animation", true));
			return PlayState.CONTINUE;
		}
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_sniper.walking_animation", true));
		}

		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_sniper.idle_animation", true));
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new RoboAttackGoal(this, 11, 8.0F, 0.75F));
		this.goalSelector.addGoal(1, new WaterAvoidingRandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(controller);
	}
	
	@Override
	public AnimationController<?> getController() {
		return controller;
	}
	
	@Override
	public void manageAttack(LivingEntity target) {
		
	}

	@Override
	public int animationInterval() {
		return 0;
	}
	
	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		this.getTarget();
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}
}
