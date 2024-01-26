package io.github.chaosawakens.client.sounds.tickable.robo;

import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableWalkSound;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class RoboPounderTickableWalkSound extends AnimatableTickableWalkSound {
    private final RoboPounderEntity animatable;

    public RoboPounderTickableWalkSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, RoboPounderEntity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboPounderTickableWalkSound(SoundEvent soundToPlay, RoboPounderEntity targetEntity) {
        super(soundToPlay, targetEntity);
        this.animatable = targetEntity;
    }

    @Override
    public boolean shouldPlaySound() {
        return !animatable.isAttacking() && !animatable.isDeadOrDying() && super.shouldPlaySound();
    }
}
