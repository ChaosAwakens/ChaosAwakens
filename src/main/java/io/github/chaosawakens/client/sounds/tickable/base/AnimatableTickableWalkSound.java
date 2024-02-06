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

import java.util.concurrent.atomic.AtomicBoolean;

public class AnimatableTickableWalkSound extends EntityTickableSound {
    protected static final SoundEngine MC_SOUND_ENGINE = Minecraft.getInstance().getSoundManager().soundEngine;
    private final IAnimatableEntity animatableTarget;
    private boolean isPaused = false;

    public AnimatableTickableWalkSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, Entity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
        this.animatableTarget = (IAnimatableEntity) targetEntity;
    }

    public AnimatableTickableWalkSound(SoundEvent soundToPlay, Entity targetEntity) {
        this(soundToPlay, targetEntity.getSoundSource(), targetEntity);
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
        LivingEntity targetLivingEntity = (LivingEntity) animatableTarget;
        double dx = targetLivingEntity.getX() - targetLivingEntity.xo;
        double dz = targetLivingEntity.getZ() - targetLivingEntity.zo;
        double dxSqr = dx * dx;
        double dzSqr = dz * dz;

        return dxSqr + dzSqr > getDeltaMovementThreshold() && !targetLivingEntity.isDeadOrDying() && targetLivingEntity.isOnGround();
    }

    public boolean shouldPause() {
        return false;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public double getDeltaMovementThreshold() {
        return 2.500000277905201E-7;
    }
}
