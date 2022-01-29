package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.api.IGrabber;
import io.github.chaosawakens.common.entity.ai.AnimatableGrabGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.AnimatableMultiAttackGoal;
import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class HerculesBeetleEntity extends AnimatableMonsterEntity implements IAnimatable, IGrabber {
    protected final Vector3d grabOffset = new Vector3d(0, 0.5, 2);
    private final AnimationFactory factory = new AnimationFactory(this);

    public HerculesBeetleEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 250)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 30)
                .add(Attributes.ATTACK_KNOCKBACK, 7.5D)
                .add(Attributes.FOLLOW_RANGE, 16);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//        if (this.dead) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.death_animation", true));
//            return PlayState.CONTINUE;
//        }

        if (this.getAttacking()) {
            if (this.getGrabbing(this)) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.walk_attack_animation", true));
                return PlayState.CONTINUE;
            }

            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.attack_animation", true));
            return PlayState.CONTINUE;
        }

        if (this.getMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.walking_animation", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hercules_beetle.idle_animation", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, IronGolemEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new LookAtGoal(this, SnowGolemEntity.class, 24.0F));
        this.goalSelector.addGoal(3, new AnimatableMoveToTargetGoal(this, 1.75, 10));
//		this.goalSelector.addGoal(4, new AnimatableGrabGoal<HerculesBeetleEntity>(this, 8));
        this.goalSelector.addGoal(3, new AnimatableMeleeGoal(this, 30.4, 0.75, 0.85));
//		this.goalSelector.addGoal(3, new ThrowRiderAttackGoal(this, 0.125F, false));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.6));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
    //    this.targetSelector.addGoal(4, new AnimatableMultiAttackGoal(this, new ZombieEntity(level), new AnimatableMeleeGoal(this, 30.4, 0.75, 0.85), new LookAtGoal(this, PlayerEntity.class, 24.0F), new RandomWalkingGoal(this, 1.6)));
   //     if (this.distanceTo(this.getTarget()) >= 20.0D) {
        //	this.targetSelector.addGoal(4, new AnimatableGrabGoal<HerculesBeetleEntity>(h, 8));
   //     }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "herculesbeetlecontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GRABBING, false);
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public void positionRider(Entity passenger) {
        this.positionRider(this, passenger, Entity::setPos);
    }

    public Vector3d getGrabOffset() {
        return this.grabOffset;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return CASoundEvents.HERCULES_BEETLE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return CASoundEvents.HERCULES_BEETLE_DEATH.get();
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }
}
