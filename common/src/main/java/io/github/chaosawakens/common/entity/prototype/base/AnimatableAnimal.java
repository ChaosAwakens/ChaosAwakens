package io.github.chaosawakens.common.entity.prototype.base;

import io.github.chaosawakens.api.animation.faal.base.ExtendedAnimationState;
import io.github.chaosawakens.api.animation.faal.entity.WrappedAnimatableEntity;
import io.github.chaosawakens.common.entity.prototype.ai.body_rotation_control.BandaidBodyRotationControl;
import io.github.chaosawakens.common.entity.prototype.ai.move_control.ReinforcedMoveControl;
import io.github.chaosawakens.common.entity.prototype.ai.path_navigation.DirectGroundPathNavigation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public abstract class AnimatableAnimal extends Animal implements WrappedAnimatableEntity {
    private final ObjectArrayList<ExtendedAnimationState> cachedAnimationStates = new ObjectArrayList<>();
    protected int customDeathTime = 0;
    protected float yDeathRot = 0.0F;
    private boolean requiresServerAnimTicking = true;

    protected AnimatableAnimal(EntityType<? extends AnimatableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public boolean requiresServerAnimationTicking() {
        return requiresServerAnimTicking;
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new BandaidBodyRotationControl(this);
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new DirectGroundPathNavigation(this, pLevel);
    }

    public boolean canBeKnockedBack() {
        return true;
    }

    public int getDeathDuration() {
        return 20;
    }

    public boolean useCustomDeathTime() {
        return true;
    }

    public boolean isStuck() {
        double dx = getX() - xo;
        double dz = getZ() - zo;
        double dxSqr = dx * dx;
        double dzSqr = dz * dz;

        return dxSqr + dzSqr < getMovementThreshold();
    }

    public boolean isMoving() {
        return !isStuck();
    }

    @Override
    public double getMovementThreshold() {
        return ReinforcedMoveControl.MIN_SPEED_SQR;
    }

    public double getCustomDeathTime() {
        return customDeathTime;
    }

    @Override
    public void tick() {
        if (level().isClientSide()) {
            tickClientAnimations();
            tickGeneralClient();
        } else {
            if (requiresServerAnimationTicking()) {
                if (cachedAnimationStates.stream().noneMatch(ExtendedAnimationState::isServerTickable)) this.requiresServerAnimTicking = false;

                cachedAnimationStates.stream()
                        .filter(curAnimState -> curAnimState.isServerTickable() && curAnimState.isStarted())
                        .peek(curAnimState -> {
                            if (curAnimState.getAccumulatedTicks() >= curAnimState.getAnimationTickDuration()) stopAnimation(curAnimState);
                        })
                        .forEach(curAnimState -> curAnimState.updateTime(tickCount, (float) curAnimState.getAnimationSpeedMultiplier()));
            }

            tickServerAnimations();
        }

        super.tick();
    }

    @Override
    protected void tickDeath() {
        setYRot(yDeathRot);
        setYHeadRot(yDeathRot);

        if (useCustomDeathTime()) customDeathTime++;
        else deathTime++;

        if ((useCustomDeathTime() ? customDeathTime : deathTime) >= getDeathDuration() && !level().isClientSide() && !isRemoved()) {
            level().broadcastEntityEvent(this, EntityEvent.POOF);
            remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public boolean hurt(DamageSource lastSrc, float dmgAmount) {
        if (lastSrc.getEntity() != null && !canBeKnockedBack()) {
            super.hurt(lastSrc, dmgAmount);
            return false;
        }

        return super.hurt(lastSrc, dmgAmount);
    }

    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);

        this.yDeathRot = getYRot();
    }

    public abstract Ingredient getTemptIngredient();

    @Override
    public boolean isFood(ItemStack targetStack) {
        return getTemptIngredient().test(targetStack);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.75D) {

            @Override
            public void start() {
                super.start();

                if (mob instanceof Panickable panickableOwner) {
                    panickableOwner.setPanicking(true);

                    if (panickableOwner.getPanicSpeedModifier() != null) mob.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(panickableOwner.getPanicSpeedModifier());
                }
            }

            @Override
            public void stop() {
                super.stop();

                if (mob instanceof Panickable panickableOwner) {
                    panickableOwner.setPanicking(false);

                    if (panickableOwner.getPanicSpeedModifier() != null) mob.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(panickableOwner.getPanicSpeedModifier());
                }
            }
        });
        this.goalSelector.addGoal(1, new AvoidEntityGoal<Monster>(this, Monster.class, 12.0F, 1.2D, 2.0D) {

            @Override
            public void stop() {
                super.stop();

                if (mob instanceof Panickable panickableOwner) panickableOwner.setPanicking(false);
            }

            @Override
            public void tick() {
                super.tick();

                if (mob instanceof Panickable panickableOwner) {
                    panickableOwner.setPanicking(distanceToSqr(toAvoid) < 109.0D);

                    getNavigation().setSpeedModifier(distanceToSqr(toAvoid) < 109.0D ? 2.0D : 1.2D);
                }
            }
        });
        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));

        if (getTemptIngredient() != null && !getTemptIngredient().isEmpty()) this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, getTemptIngredient(), false));

        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D) {

            @Override
            public boolean canUse() {
                return super.canUse() && !(mob instanceof Panickable panickableOwner && panickableOwner.isPanicking());
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && !(mob instanceof Panickable panickableOwner && panickableOwner.isPanicking());
            }
        });
    }

    @Override
    public boolean isPushable() {
        return canBeKnockedBack();
    }

    @Override
    public void push(double pX, double pY, double pZ) {
        if (!canBeKnockedBack()) return;
        super.push(pX, pY, pZ);
    }

    @Override
    public void knockback(double pStrength, double pRatioX, double pRatioZ) {
        if (!canBeKnockedBack()) return;
        super.knockback(pStrength, pRatioX, pRatioZ);
    }

    @Override
    public ObjectArrayList<ExtendedAnimationState> getCachedAnimationStates() {
        return cachedAnimationStates;
    }
}
