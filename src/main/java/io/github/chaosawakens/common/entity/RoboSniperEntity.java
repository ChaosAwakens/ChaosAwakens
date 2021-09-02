package io.github.chaosawakens.common.entity;

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

public class RoboSniperEntity extends RoboEntity implements IAnimatable, IRangedAttackMob {
    private final AnimationFactory factory = new AnimationFactory(this);

    public RoboSniperEntity(EntityType<? extends RoboEntity> type, World worldIn) {
        super(type, worldIn);
        this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.ATTACK_SPEED, 10)
                .add(Attributes.ATTACK_DAMAGE, 25)
                .add(Attributes.ATTACK_KNOCKBACK, 3.5D)
                .add(Attributes.FOLLOW_RANGE, 24);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_sniper.attacking_animation", true));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_sniper.walking_animation", true));
            //event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_sniper.accelerate_animation", true));
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.robo_sniper.idle_animation", true));
        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new RoboAttackGoal(this, 11, 7.0F, 0.75F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.6));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "robosnipercontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        this.getTarget();
    }
}
