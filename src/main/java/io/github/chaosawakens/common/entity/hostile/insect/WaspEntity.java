package io.github.chaosawakens.common.entity.hostile.insect;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.api.animation.WrappedAnimationController;
import io.github.chaosawakens.common.entity.ai.AnimatableMoveToTargetGoal;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableFlyingAttackGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class WaspEntity extends AnimatableMonsterEntity {
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private final ObjectArrayList<WrappedAnimationController<WaspEntity>> waspControllers = new ObjectArrayList<WrappedAnimationController<WaspEntity>>(3);
    private final ObjectArrayList<IAnimationBuilder> waspAnimations = new ObjectArrayList<IAnimationBuilder>(1);
    private final WrappedAnimationController<WaspEntity> mainController = createMainMappedController("waspmaincontroller");
    private final WrappedAnimationController<WaspEntity> attackController = createMappedController("waspattackcontroller", this::attackPredicate);
    private final SingletonAnimationBuilder flyAnim = new SingletonAnimationBuilder(this, "fly", ILoopType.EDefaultLoopTypes.LOOP);
    private final SingletonAnimationBuilder flyAttackAnim = new SingletonAnimationBuilder(this, "fly_attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE).setWrappedController(attackController);
    private static final byte FLY_ATTACK_ID = 1;

    public WaspEntity(EntityType<? extends WaspEntity> entityType, World world) {
        super(entityType, world);
    }
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ARMOR, 4)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.FLYING_SPEED, 0.4);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AnimatableMoveToTargetGoal(this, 1.2, 4));
        this.targetSelector.addGoal(0, new AnimatableFlyingAttackGoal(this, () -> flyAttackAnim, FLY_ATTACK_ID, 20D, 22.4D, 2));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, BeeEntity.class, false));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this,1.00D));
    }
    @Override
    public void manageAttack(LivingEntity target) {
        if (getAttackID() == FLY_ATTACK_ID) {
            // Perform sting attack similar to Minecraft bee
            target.hurt(DamageSource.sting(this), 5.0f);
            // Apply poison effect to the target
            target.addEffect(new EffectInstance(Effects.POISON, 200, 0));
        }
    }
    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

//    Commented because it does not exist yet
//    @Override
//    protected SoundEvent getHurtSound(DamageSource damageSource) {
//        return CASoundEvents.WASP_HURT.get();
//    }
//    @Override
//    protected SoundEvent getDeathSound() {
//        return CASoundEvents.WASP_DEATH.get();
//    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        if (!blockState.getMaterial().isLiquid()) playSound(CASoundEvents.WASP_AMBIENT.get(), this.getVoicePitch() * 0.30F, this.getSoundVolume() * 1);
    }
    @Override
    public void tick() {
        super.tick();
        this.checkFallFlying();
        BlockPos groundPos = new BlockPos(this.position().x(), this.position().y() - 1, this.position().z());
        if (this.level.getBlockState(groundPos).canOcclude()) {
            this.move(MoverType.SELF, new Vector3d(0, 0.2, 0)); // Apply additional upwards motion to avoid hitting the ground
        }
        this.move(MoverType.SELF, new Vector3d(0, 0.1, 0));
        if (this.isOnFire()) {
            this.clearFire();
        }
        if (this.isInWater()) {
            this.move(MoverType.SELF, new Vector3d(0, 0.1, 0));
        }
        if (this.isInLava()) {
            this.setSecondsOnFire(1);
        }
    }

    public void checkFallFlying() {
        if (this.flyAnim.hasAnimationFinished()) {
            this.move(MoverType.SELF, new Vector3d(0, 0.1, 0));
        }
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
        return PlayState.CONTINUE;
    }
    @Override
    public int animationInterval() {
        return 1;
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
}


