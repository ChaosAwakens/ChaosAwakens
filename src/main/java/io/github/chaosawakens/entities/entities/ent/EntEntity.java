package io.github.chaosawakens.entities.entities.ent;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
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

public class EntEntity extends HostileEntity implements IAnimatable {
    private static final double baseSpeed = 0.15D; //0.15D

    private AnimationFactory factory = new AnimationFactory(this);

    public EntEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.walking_animation", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.idle_animation", true));
        }
        if (this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ent.attacking_animation", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0f, false));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 24.0f));
        this.goalSelector.add(3, new LookAtEntityGoal(this, MobEntity.class, 5.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new WanderAroundGoal(this, baseSpeed));
        this.targetSelector.add(1, new FollowTargetGoal(this, SnowGolemEntity.class, true).setMaxTimeWithoutVisibility(30));
        this.targetSelector.add(2, new FollowTargetGoal(this, IronGolemEntity.class, true).setMaxTimeWithoutVisibility(30));
        this.targetSelector.add(3, new FollowTargetGoal(this, PlayerEntity.class, true).setMaxTimeWithoutVisibility(30));
    }

    public static DefaultAttributeContainer.Builder setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 150)
                .add(EntityAttributes.GENERIC_ARMOR, 3.0D)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 10.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, baseSpeed)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 25.0D)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 3.5D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0D);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<EntEntity>(this, "entcontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}