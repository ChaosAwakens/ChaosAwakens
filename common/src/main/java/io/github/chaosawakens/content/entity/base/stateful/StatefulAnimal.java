package io.github.chaosawakens.content.entity.base.stateful;

import io.github.chaosawakens.api.ai.prototyping.control.body.BandaidBodyRotationControl;
import io.github.chaosawakens.api.ai.prototyping.control.movement.BandaidMoveControl;
import io.github.chaosawakens.api.ai.prototyping.goals.passive.StatefulPanicGoal;
import io.github.chaosawakens.api.ai.prototyping.pathnav.DirectGroundPathNavigation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class StatefulAnimal extends Animal {
    private static final EntityDataAccessor<Boolean> PANICKING = SynchedEntityData.defineId(StatefulAnimal.class, EntityDataSerializers.BOOLEAN);

    protected StatefulAnimal(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

        this.moveControl = new BandaidMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new StatefulPanicGoal(this));

        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        
        this.goalSelector.addGoal(2, new FloatGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(PANICKING, false);
    }

    public boolean isPanicking() {
        return this.entityData.get(PANICKING);
    }

    public void setPanicking(boolean panicking) {
        this.entityData.set(PANICKING, panicking);
    }

    public boolean canBeKnockedBack() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() != null && !canBeKnockedBack()) {
            super.hurt(pSource, pAmount);
            return false;
        }

        return super.hurt(pSource, pAmount);
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
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new DirectGroundPathNavigation(this, level);
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new BandaidBodyRotationControl(this);
    }

    public double getPanicSpeedModifier() {
        return 1.33D;
    }
}