package io.github.chaosawakens.client.sounds.tickable.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.EntityTickableSound;
import net.minecraft.client.audio.SoundEngine;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class AnimatableTickableWalkSound extends EntityTickableSound { //TODO Probably group and clean all this up by 1.19/1.20+ (laziness moment)
    protected static final SoundEngine MC_SOUND_ENGINE = Minecraft.getInstance().getSoundManager().soundEngine;
    protected final IAnimatableEntity animatableTarget;
    protected boolean isPaused = false;

    public AnimatableTickableWalkSound(SoundEvent soundToPlay, float volume, float pitch, Entity targetEntity) {
        super(soundToPlay, targetEntity.getSoundSource(), volume, pitch, targetEntity);
        this.animatableTarget = (IAnimatableEntity) targetEntity;
    }

    public AnimatableTickableWalkSound(SoundEvent soundToPlay, float volume, Entity targetEntity) {
        super(soundToPlay, targetEntity.getSoundSource(), volume, 1.0F, targetEntity);
        this.animatableTarget = (IAnimatableEntity) targetEntity;
    }

    public AnimatableTickableWalkSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, Entity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
        this.animatableTarget = (IAnimatableEntity) targetEntity;
    }

    public AnimatableTickableWalkSound(SoundEvent soundToPlay, Entity targetEntity) {
        this(soundToPlay, targetEntity.getSoundSource(), targetEntity);
    }

    public AnimatableTickableWalkSound setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public void tick() {
        super.tick();

        if (shouldPause() && canPlaySound()) {
            this.isPaused = true;
            MC_SOUND_ENGINE.instanceToChannel.forEach((curSound, soundChannelEntry) -> {
                if (curSound.getLocation().equals(getLocation())) soundChannelEntry.execute(SoundSource::pause);
            });
        } else if (canPlaySound() && isPaused()) {
            this.isPaused = false;
            MC_SOUND_ENGINE.instanceToChannel.forEach((curSound, soundChannelEntry) -> {
                if (curSound.getLocation().equals(getLocation())) soundChannelEntry.execute(SoundSource::unpause);
            });
        }
    }

    @Override
    public boolean canPlaySound() {
        return !((LivingEntity) animatableTarget).isDeadOrDying();
    }

    public boolean shouldPause() {
        LivingEntity targetAnimatableLivingEntity = (LivingEntity) animatableTarget;
        double dx = targetAnimatableLivingEntity.getX() - targetAnimatableLivingEntity.xo;
        double dz = targetAnimatableLivingEntity.getZ() - targetAnimatableLivingEntity.zo;
        double dxSqr = dx * dx;
        double dzSqr = dz * dz;

        return !MC_SOUND_ENGINE.loaded || dxSqr + dzSqr < getMovementThreshold();
    }

    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public boolean isLooping() {
        return canPlaySound();
    }

    public double getMovementThreshold() {
        return 2.500000277905201E-7;
    }
}
