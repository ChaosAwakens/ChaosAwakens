package io.github.chaosawakens.common.entity.hostile.insect;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.controllers.movement.air.JumpFlyingMovementController;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboSniperEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class WaspEntity extends AnimatableMonsterEntity {
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private final ObjectArrayList<WrappedAnimationController<WaspEntity>> waspControllers = new ObjectArrayList<>(2);
    private final ObjectArrayList<IAnimationBuilder> waspAnimations = new ObjectArrayList<>(2);
    private final WrappedAnimationController<WaspEntity> mainController = createMainMappedController("waspmaincontroller");
    private final WrappedAnimationController<WaspEntity> attackController = createMappedController("waspattackcontroller", this::attackPredicate);
    private final SingletonAnimationBuilder flyAnim = new SingletonAnimationBuilder(this, "Fly", ILoopType.EDefaultLoopTypes.LOOP);
    private final SingletonAnimationBuilder pinchAttackAnim = new SingletonAnimationBuilder(this, "Pinch Attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setAnimSpeed(2.0D).setWrappedController(attackController);
    private static final byte FLY_ATTACK_ID = 1;
    public static final String WASP_MDF_NAME = "wasp";

    public WaspEntity(EntityType<? extends WaspEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlyingMovementController(this, 20, true);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.MOVEMENT_SPEED, 1.25)
                .add(Attributes.FLYING_SPEED, 0.55)
                .add(Attributes.FOLLOW_RANGE, 25)
                .add(Attributes.ARMOR, 4)
                .add(Attributes.ATTACK_DAMAGE, 12)
                .add(Attributes.ATTACK_SPEED, 15)
                .add(Attributes.ATTACK_KNOCKBACK, 3);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public WrappedAnimationController<WaspEntity> getMainWrappedController() {
        return mainController;
    }

    @Override
    public <E extends IAnimatableEntity> PlayState mainPredicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    public <E extends IAnimatableEntity> PlayState attackPredicate(AnimationEvent<E> event) {
        return isDeadOrDying() ? PlayState.STOP : PlayState.CONTINUE;
    }

    @Override
    public int animationInterval() {
        return 1;
    }

    @Override
    protected PathNavigator createNavigation(World pLevel) {
        FlyingPathNavigator waspNav = new FlyingPathNavigator(this, pLevel);
        return waspNav;
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1.2, 3));
        this.targetSelector.addGoal(0, new AnimatableMeleeGoal(this, () -> pinchAttackAnim, FLY_ATTACK_ID, 20D, 22.4D, 135, 1, 10));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboPounderEntity.class, false)); //TODO 1.20.1: Unify hierarchally (I love inventing words)
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboWarriorEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboJefferyEntity.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, RoboSniperEntity.class, false));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(0, new WaterAvoidingRandomFlyingGoal(this,1.1D));
    }

    @Override
    public void manageAttack(LivingEntity target) {
        switch (getAttackID()) {
            case FLY_ATTACK_ID:
                target.addEffect(new EffectInstance(Effects.POISON, MathHelper.nextInt(random, 20, 200), MathHelper.nextInt(random, 1, 3)));
                break;
        }
    }

    @Override
    public float getMeleeAttackReach(LivingEntity target) {
        return 2.35F;
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pDamageMultiplier) {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntitySize pSize) {
        return super.getStandingEyeHeight(pPose, pSize) - 0.2F;
    }

    @Override
    public SingletonAnimationBuilder getIdleAnim() {
        return flyAnim;
    }

    @Override
    public SingletonAnimationBuilder getWalkAnim() {
        return flyAnim;
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

    @SuppressWarnings("unchecked")
    @Override
    public ObjectArrayList<IAnimationBuilder> getCachedAnimations() {
        return waspAnimations;
    }

    @Override
    public String getOwnerMDFileName() {
        return WASP_MDF_NAME;
    }
}