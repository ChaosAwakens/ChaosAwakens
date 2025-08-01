package io.github.chaosawakens.common.entity.prototype.hostile;

import io.github.chaosawakens.api.animation.faal.base.ExtendedAnimationState;
import io.github.chaosawakens.api.vfx.basic.ScreenShakeEffect;
import io.github.chaosawakens.common.entity.prototype.ai.goal.hostile.AnimatableAttackGoal;
import io.github.chaosawakens.common.entity.prototype.ai.goal.hostile.BandaidMoveToTargetGoal;
import io.github.chaosawakens.common.entity.prototype.base.AnimatableMonster;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Ent extends AnimatableMonster {
    public static final byte ARBOREAL_PUNCH_ATTACK_ID = 1;
    public static final byte ENT_SMASH_ATTACK_ID = 2;
    public static final String IDLE_ANIM = "Idle";
    public static final String DEATH_ANIM = "Death";
    public static final String LEFT_ARBOREAL_PUNCH_ATTACK_ANIM = "Arboreal Punch Attack (Left)";
    public static final String RIGHT_ARBOREAL_PUNCH_ATTACK_ANIM = "Arboreal Punch Attack (Right)";
    public static final String ENT_SMASH_ATTACK_ANIM = "Ent Smash Attack";
    public static final String CORE_ANIM = "Core (Always Play)";
    public final ExtendedAnimationState idleAnimState = wrapState(IDLE_ANIM);
    public final ExtendedAnimationState deathAnimState = wrapState(DEATH_ANIM);
    public final ExtendedAnimationState leftArborealPunchAttackAnim = wrapState(LEFT_ARBOREAL_PUNCH_ATTACK_ANIM);
    public final ExtendedAnimationState rightArborealPunchAttackAnim = wrapState(RIGHT_ARBOREAL_PUNCH_ATTACK_ANIM);
    public final ExtendedAnimationState entSmashAttackAnim = wrapState(ENT_SMASH_ATTACK_ANIM);
    public final ExtendedAnimationState coreAnimState = wrapState(CORE_ANIM);

    public Ent(EntityType<? extends AnimatableMonster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.noCulling = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 150)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.325D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 15)
                .add(Attributes.ATTACK_KNOCKBACK, 4.5D)
                .add(Attributes.FOLLOW_RANGE, 30);
    }

    @Override
    public boolean isFunctionallyAnimatingAttack() {
        return (leftArborealPunchAttackAnim.isStarted() && leftArborealPunchAttackAnim.getAccumulatedTime() <= 1800) || (rightArborealPunchAttackAnim.isStarted() && rightArborealPunchAttackAnim.getAccumulatedTime() <= 1800) || (entSmashAttackAnim.isStarted() && entSmashAttackAnim.getAccumulatedTime() <= 1800);
    }

    @Override
    protected void registerGoals() {
        // Targeting
        targetSelector.addGoal(0, new HurtByTargetGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, false));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));

        // Melee Attacks
        goalSelector.addGoal(0, new AnimatableAttackGoal<>(this, ObjectArrayList.of(() -> leftArborealPunchAttackAnim, () -> rightArborealPunchAttackAnim), 36, true, ARBOREAL_PUNCH_ATTACK_ID)
                .attackArc(107.0D)
                .potentialTargetRadius(5.0D)
                .attackFrame(21.0D, 26.0D)
                .attackTickCooldown(20.0D)
                .initiationRange(3.0D)
                .additionalStartConditions(animatable -> animatable.getRandom().nextDouble() < 0.8D)
                .actionOnStart((animatable, target, potentialTargets, curTick) -> {
                    animatable.stopAnimation(idleAnimState);
                })
                .actionOnAttack((animatable, target, potentialTargets, curTick) -> new ScreenShakeEffect(animatable.blockPosition(), 15.5D, 0.0018F, 12.5F, 0.4F).enqueue(animatable.level()))
                .actionOnEnd((animatable, target, potentialTargets, curTick) -> animatable.playAnimation(idleAnimState, true)));

        // AOE Attacks
        goalSelector.addGoal(0, new AnimatableAttackGoal<>(this, ObjectArrayList.of(() -> entSmashAttackAnim), 36, true, ENT_SMASH_ATTACK_ID)
                .performDefaultAttack(false)
                .forcePose(true)
                .potentialTargetRadius(8.0D)
                .attackFrame(26.0D, 28.0D)
                .attackTickCooldown(80.0D)
                .initiationRange(4.0D)
                .additionalStartConditions(animatable -> animatable.getRandom().nextDouble() < 0.6D)
                .actionOnStart((animatable, target, potentialTargets, curTick) -> {
                    animatable.stopAnimation(idleAnimState);
                })
                .actionOnAttack((animatable, target, potentialTargets, curTick) -> {
                    if (curTick == 26.0D) animatable.playSound(SoundEvents.GENERIC_EXPLODE);

                    potentialTargets.forEach(curTarget -> {
                        animatable.doHurtTarget(curTarget);

                        curTarget.setDeltaMovement(curTarget.getDeltaMovement().add(0.0D, 0.2D, 0.0D));
                    });
                    new ScreenShakeEffect(animatable.blockPosition(), 15.5D, 0.0038F, 12.5F, 1.0F).enqueue(animatable.level());
                })
                .actionOnEnd((animatable, target, potentialTargets, curTick) -> animatable.playAnimation(idleAnimState, true)));

        // Navigation
        goalSelector.addGoal(0, new BandaidMoveToTargetGoal(this)
                .satisfactoryDist(2.5D));
        goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.2D, 0.2F) {
            @Override
            public boolean canUse() {
                return super.canUse() && getTarget() == null;
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && getTarget() == null;
            }
        });
    }

    @Override
    public int getDeathDuration() {
        return 72;
    }

    @Override
    public void tickClientAnimations() {
        if (!isFunctionallyAnimatingAttack() && !isDeadOrDying()) playAnimation(idleAnimState);
        else stopAnimation(idleAnimState);

        if (isDeadOrDying()) {
            playAnimation(deathAnimState, true);
            stopAnimation(coreAnimState);
        } else {
            playAnimation(coreAnimState);
            stopAnimation(deathAnimState);
        }
    }

    @Override
    public void tickServerAnimations() {

    }

    @Override
    public void tickGeneralClient() {

    }

    @Override
    protected boolean canRide(@NotNull Entity vehicle) {
        return false;
    }

    @Override
    protected float getJumpPower() {
        return 0.0F;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distToClosestPlayer) {
        return false;
    }
}
