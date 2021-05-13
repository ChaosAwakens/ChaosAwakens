package io.github.chaosawakens.entity;

import io.github.chaosawakens.entity.ai.ThrowRiderAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HerculesBeetleEntity extends MonsterEntity implements IAnimatable {
	private AnimationFactory factory = new AnimationFactory(this);

	public HerculesBeetleEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.ignoreFrustumCheck = true;
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (this.isAggressive()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.attacking_animation", true));
			return PlayState.CONTINUE;
		}
		
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.walking_animation", true));
			return PlayState.CONTINUE;
		}
		
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.idle_animation", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.75, true));
		this.goalSelector.addGoal(5, new ThrowRiderAttackGoal(this, 0.125F, false) {
			@Override
			protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
				super.checkAndPerformAttack(p_190102_1_, p_190102_2_);
			}

			@Override
			public void resetTask() {
				super.resetTask();
			}
		});
		this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(7, new SwimGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.registerAttributes()
				.createMutableAttribute(Attributes.MAX_HEALTH, 250)
				.createMutableAttribute(Attributes.ARMOR, 19)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D)
				.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 2.5D)
				.createMutableAttribute(Attributes.ATTACK_SPEED, 10)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 30)
				.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 7.5)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<HerculesBeetleEntity>(this, "herculesbeetlecontroller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}
