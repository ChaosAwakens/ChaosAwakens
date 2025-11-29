package io.github.chaosawakens.common.entity.prototype.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class AnimatableAngerableMob extends AnimatableMonster implements NeutralMob {
    private static final EntityDataAccessor<Optional<UUID>> PERSISTENT_ANGER_TARGET = SynchedEntityData.defineId(AnimatableAngerableMob.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> REMAINING_PERSISTENT_ANGER_TIME = SynchedEntityData.defineId(AnimatableAngerableMob.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    public AnimatableAngerableMob(EntityType<? extends AnimatableMonster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(PERSISTENT_ANGER_TARGET, Optional.empty());
        this.entityData.define(REMAINING_PERSISTENT_ANGER_TIME, 0);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(REMAINING_PERSISTENT_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
        this.entityData.set(REMAINING_PERSISTENT_ANGER_TIME, remainingPersistentAngerTime);
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return this.entityData.get(PERSISTENT_ANGER_TARGET).orElse(null);
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.entityData.set(PERSISTENT_ANGER_TARGET, Optional.ofNullable(uuid));
    }

    @Override
    public void startPersistentAngerTimer() {
        setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(getRandom()));
    }

    @Override
    protected void customServerAiStep() {
        if (!level().isClientSide()) {
            updatePersistentAnger((ServerLevel) level(), true);
        }

        super.customServerAiStep();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        readPersistentAngerSaveData(level(), tag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        addPersistentAngerSaveData(tag);
    }
}
