package io.github.chaosawakens.client.sounds.tickable.robo.robopounder;

import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableIdleMonsterSound;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public class RoboPounderTickableIdleSound extends AnimatableTickableIdleMonsterSound {
    private final RoboPounderEntity animatable;
    @Nullable
    private ITickableSound criticalSound;
    private boolean hasSwitched = false;

    public RoboPounderTickableIdleSound(SoundEvent soundToPlay, float volume, float pitch, RoboPounderEntity targetEntity) {
        super(soundToPlay, volume, pitch, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboPounderTickableIdleSound(SoundEvent soundToPlay, float volume, RoboPounderEntity targetEntity) {
        super(soundToPlay, volume, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboPounderTickableIdleSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, RoboPounderEntity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboPounderTickableIdleSound(SoundEvent soundToPlay, RoboPounderEntity targetEntity) {
        super(soundToPlay, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboPounderTickableIdleSound setCriticalSound(ITickableSound critSoundToPlay) {
        this.criticalSound = critSoundToPlay;
        return this;
    }

    @Override
    public void tick() {
        super.tick();

        if (shouldSwitchToCriticalSound() && !hasSwitched) {
            this.hasSwitched = true;
            Minecraft.getInstance().getSoundManager().queueTickingSound(criticalSound);
        } else if (!shouldSwitchToCriticalSound()) {
            this.hasSwitched = false;
            Minecraft.getInstance().getSoundManager().stop(criticalSound);
        }
    }

    @Override
    public boolean shouldPause() {
        return animatable.isRageRunning() || shouldSwitchToCriticalSound() || super.shouldPause();
    }

    @Override
    public boolean shouldSwitchToBgVolume() {
        return super.shouldSwitchToBgVolume() || animatable.isPlayingAnimation(animatable.getCachedAnimationByName("Taunt"));
    }

    public boolean shouldSwitchToCriticalSound() {
        return animatable.getHealth() <= 50.0F && criticalSound != null;
    }
}