package io.github.chaosawakens.entities;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
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

public class EntEntity extends MonsterEntity implements IAnimatable {
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(EntEntity.class,
            DataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isAttacking() {
        return this.getDataManager().get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.getDataManager().set(ATTACKING, attacking);
    }

    protected void defineSynchedData() {
        super.registerData();
        this.getDataManager().register(ATTACKING, false);
    }

    public EntEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreFrustumCheck = true;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
                .createMutableAttribute(Attributes.ARMOR, 6.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE,3.5D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 23.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.21F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.4));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
