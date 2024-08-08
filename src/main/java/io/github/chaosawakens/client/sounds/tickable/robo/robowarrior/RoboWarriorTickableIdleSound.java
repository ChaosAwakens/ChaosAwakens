package io.github.chaosawakens.client.sounds.tickable.robo.robowarrior;

import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableIdleMonsterSound;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public class RoboWarriorTickableIdleSound extends AnimatableTickableIdleMonsterSound {
    private final RoboWarriorEntity animatable;
    @Nullable
    private ITickableSound shieldSound;
    private boolean hasSwitched = false;

    public RoboWarriorTickableIdleSound(SoundEvent soundToPlay, float volume, float pitch, RoboWarriorEntity targetEntity) {
        super(soundToPlay, volume, pitch, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboWarriorTickableIdleSound(SoundEvent soundToPlay, float volume, RoboWarriorEntity targetEntity) {
        super(soundToPlay, volume, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboWarriorTickableIdleSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, RoboWarriorEntity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboWarriorTickableIdleSound(SoundEvent soundToPlay, RoboWarriorEntity targetEntity) {
        super(soundToPlay, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboWarriorTickableIdleSound setShieldSound(ITickableSound shieldSoundToPlay) {
        this.shieldSound = shieldSoundToPlay;
        return this;
    }

    @Override
    public void tick() { //TODO Common impl in 1.20+
        super.tick();

        if (shouldSwitchToShieldSound() && !hasSwitched) {
            this.hasSwitched = true;
            Minecraft.getInstance().getSoundManager().queueTickingSound(shieldSound);
        } else if (!shouldSwitchToShieldSound()) {
            this.hasSwitched = false;
            Minecraft.getInstance().getSoundManager().stop(shieldSound);
        }
    }

    @Override
    public boolean shouldPause() {
        return shouldSwitchToShieldSound() || super.shouldPause();
    }

    public boolean shouldSwitchToShieldSound() {
        return animatable.isPlayingAnimation("Shield Active") && shieldSound != null;
    }
}
