package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.entity.ai.LavaMoveHelper;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class LavaEelEntity extends AbstractLavaGroupFishEntity implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);

	public LavaEelEntity(EntityType<? extends LavaEelEntity> entityType, World world) {
		super(entityType, world);
		this.noCulling = true;
		this.moveControl = new LavaMoveHelper(this);
		this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
		this.setPathfindingMalus(PathNodeType.LAVA, 8.0F);
		this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 0.0F);
	}

	@Override
	public Entity getEntity() {
		return this;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		if (this.level.getDifficulty() != Difficulty.PEACEFUL) {
			this.targetSelector.addGoal(1, new LavaEelEntity.AttackGoal(this, 2.0F, false));
			this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		}
		this.goalSelector.addGoal(0, new LavaEelEntity.SwimGoal(this));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createLivingAttributes()
				.add(Attributes.MAX_HEALTH, 6.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.ATTACK_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 1)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.35D)
				.add(Attributes.FOLLOW_RANGE, 10.0D);
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<>(this, "lavaeelcontroller", 0, this::predicate));
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if (event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.swim", true));
			return PlayState.CONTINUE;
		}
		if (!event.isMoving()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.swim", true));
			return PlayState.CONTINUE;
		}
		if (this.dead) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.flop", true));
			return PlayState.CONTINUE;
		}
		if (this.isSwimming()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.swim", true));
			return PlayState.CONTINUE;
		}
		if (this.isDeadOrDying()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.flop", true));
			return PlayState.CONTINUE;
		}
		if (this.isInLava()) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.lava_eel.swim", true));
			return PlayState.CONTINUE;
		}
		return PlayState.CONTINUE;
	}

	@Override
	public int getMaxSchoolSize() {
		return 4;
	}

	public boolean okTarget(@Nullable LivingEntity livingEntity) {
		if (livingEntity != null) return !this.level.isDay() || livingEntity.isInLava();
		else return false;
	}

	@Override
	public boolean doHurtTarget(Entity livingEntity) {
		LivingEntity target = this.getTarget();
		LavaEelEntity attacker = (LavaEelEntity) livingEntity;
		if (target.getLastHurtByMob() == attacker) {
			if (target instanceof LivingEntity) {
				float health = attacker.getHealth();
				target.setSecondsOnFire((int) (health * 4));
			}
		}
		return super.doHurtTarget(attacker);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 6;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	protected ItemStack getBucketItemStack() {
		return new ItemStack(CAItems.LAVA_EEL_BUCKET.get());
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.TROPICAL_FISH_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.TROPICAL_FISH_DEATH;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.TROPICAL_FISH_AMBIENT;
	}

	static class AttackGoal extends MeleeAttackGoal {
		private final LavaEelEntity lavaEel;

		public AttackGoal(LavaEelEntity entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
			super(entity, speedModifier, followingTargetEvenIfNotSeen);
			this.lavaEel = entity;
		}

		public boolean canUse() {
			return super.canUse() && this.lavaEel.okTarget(this.lavaEel.getTarget());
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && this.lavaEel.okTarget(this.lavaEel.getTarget());
		}
	}
}
