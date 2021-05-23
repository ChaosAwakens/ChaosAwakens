package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.entity.ai.RoboAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RoboWarriorEntity extends RoboEntity implements IAnimatable, IRangedAttackMob {
	private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(RoboEntity.class, DataSerializers.BOOLEAN);
	private final AnimationFactory factory = new AnimationFactory(this);
	
	public RoboWarriorEntity(EntityType<? extends RoboEntity> type, World worldIn) {
		super(type, worldIn);
		this.ignoreFrustumCheck = true;
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		
		if(this.isAttacking()) {
			if (event.isMoving()) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_warrior.walking_shooting_animation", true));
				return PlayState.CONTINUE;
			} else {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_warrior.shooting_animation", true));
				return PlayState.CONTINUE;
			}
		} else {
			if (event.isMoving()) {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_warrior.walking_animation", true));
				return PlayState.CONTINUE;
			} else {
				event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_warrior.idle_animation", true));
				return PlayState.CONTINUE;
			}
		}
		//return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 32.0F));
		this.goalSelector.addGoal(2, new LookAtGoal(this, IronGolemEntity.class, 32.0F));
		this.goalSelector.addGoal(2, new LookAtGoal(this, SnowGolemEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new RoboAttackGoal(this, 13, 15.0F, 0.7F));
		this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.6));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(7, new SwimGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
	}
	
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 180)
				.createMutableAttribute(Attributes.ARMOR, 10)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.105D)
				.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 3.5)
				.createMutableAttribute(Attributes.ATTACK_SPEED, 7.5)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 50)
				.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 3.5D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 32);
	}
	
	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "robowarriorcontroller", 0, this::predicate));
	}
	
	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
	
	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		this.getAttackTarget();
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isAttacking() {
		return this.dataManager.get(ATTACKING);
	}
	
	@Override
	public void setAttacking(boolean attacking) {
		this.dataManager.set(ATTACKING, attacking);
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(ATTACKING, false);
	}
}
