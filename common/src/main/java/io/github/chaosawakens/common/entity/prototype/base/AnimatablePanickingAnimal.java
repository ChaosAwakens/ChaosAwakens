package io.github.chaosawakens.common.entity.prototype.base;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class AnimatablePanickingAnimal extends AnimatableAnimal implements Panickable {
    private static final EntityDataAccessor<Boolean> IS_PANICKING = SynchedEntityData.defineId(AnimatablePanickingAnimal.class, EntityDataSerializers.BOOLEAN);

    protected AnimatablePanickingAnimal(EntityType<? extends AnimatableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(IS_PANICKING, false);
    }

    @Override
    public boolean isPanicking() {
        return this.entityData.get(IS_PANICKING);
    }

    @Override
    public void setPanicking(boolean panicking) {
        this.entityData.set(IS_PANICKING, panicking);
    }

    @Override
    public @Nullable AttributeModifier getPanicSpeedModifier() {
        return null;
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();

        setPanicking(false);
    }
}
