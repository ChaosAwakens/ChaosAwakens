package io.github.chaosawakens.client.sounds.tickable.robo.robopounder;

import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableWalkMonsterSound;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public class RoboPounderTickableWalkSound extends AnimatableTickableWalkMonsterSound {
    private final RoboPounderEntity animatable;
    @Nullable
    private ITickableSound rageRunSound;
    private boolean hasSwitched = false;

    public RoboPounderTickableWalkSound(SoundEvent soundToPlay, float volume, float pitch, RoboPounderEntity targetEntity) {
        super(soundToPlay, volume, pitch, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboPounderTickableWalkSound(SoundEvent soundToPlay, float volume, RoboPounderEntity targetEntity) {
        super(soundToPlay, volume, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboPounderTickableWalkSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, RoboPounderEntity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboPounderTickableWalkSound(SoundEvent soundToPlay, RoboPounderEntity targetEntity) {
        super(soundToPlay, targetEntity);
        this.animatable = targetEntity;
    }

    public RoboPounderTickableWalkSound setRageRunSound(ITickableSound rageRunSound) {
        this.rageRunSound = rageRunSound;
        return this;
    }

    @Override
    public void tick() {
        super.tick();

        if (shouldSwitchToRageRunSound() && !hasSwitched) {
            this.hasSwitched = true;
            Minecraft.getInstance().getSoundManager().queueTickingSound(rageRunSound);
        } else if (!shouldSwitchToRageRunSound()) {
            this.hasSwitched = false;
            Minecraft.getInstance().getSoundManager().stop(rageRunSound);
        }
    }

    @Override
    public boolean shouldPause() {
        double dx = animatable.getX() - animatable.xo;
        double dz = animatable.getZ() - animatable.zo;
        double dxSqr = dx * dx;
        double dzSqr = dz * dz;

        return shouldSwitchToRageRunSound() || !MC_SOUND_ENGINE.loaded || dxSqr + dzSqr < getMovementThreshold() || (shouldSwitchToRageRunSound() && !animatable.isPlayingAnimation("Rage Run"));
    }

    public boolean shouldSwitchToRageRunSound() {
        return animatable.isRageRunning() && animatable.isPlayingAnimation("Rage Run") && rageRunSound != null;
    }
}
