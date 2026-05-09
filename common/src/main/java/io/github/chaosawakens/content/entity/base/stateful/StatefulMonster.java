package io.github.chaosawakens.content.entity.base.stateful;

import io.github.chaosawakens.api.ai.prototyping.control.body.BandaidBodyRotationControl;
import io.github.chaosawakens.api.ai.prototyping.control.movement.BandaidMoveControl;
import io.github.chaosawakens.api.ai.prototyping.goals.hostile.BandaidMoveToTargetGoal;
import io.github.chaosawakens.api.ai.prototyping.pathnav.DirectGroundPathNavigation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class StatefulMonster extends Monster {
    public static final byte NO_ATTACK_ID = 0;
    private static final EntityDataAccessor<Byte> ATTACK_ID = SynchedEntityData.defineId(StatefulMonster.class, EntityDataSerializers.BYTE);

    protected StatefulMonster(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

        this.moveControl = new BandaidMoveControl(this);

        setMaxUpStep(1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(0, new BandaidMoveToTargetGoal(this)
                .satisfactoryDist(Math.max(getBbWidth() / 2.0D, 10.0D)));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(ATTACK_ID, NO_ATTACK_ID);
    }

    public byte getAttackID() {
        return this.entityData.get(ATTACK_ID);
    }

    public void setAttackID(byte attackID) {
        this.entityData.set(ATTACK_ID, attackID);
    }

    public void resetAttackId() {
        setAttackID(NO_ATTACK_ID);
    }

    public boolean isAttacking() {
        return getAttackID() != NO_ATTACK_ID;
    }

    public abstract boolean isAttackingStatically();

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
}
