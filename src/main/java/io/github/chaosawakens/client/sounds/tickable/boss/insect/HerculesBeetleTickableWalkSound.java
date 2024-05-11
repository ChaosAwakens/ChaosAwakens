package io.github.chaosawakens.client.sounds.tickable.boss.insect;

import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableWalkMonsterSound;
import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public class HerculesBeetleTickableWalkSound extends AnimatableTickableWalkMonsterSound {
    private final HerculesBeetleEntity animatable;
    @Nullable
    private ITickableSound flySound;
    private boolean hasSwitched = false;

    public HerculesBeetleTickableWalkSound(SoundEvent soundToPlay, float volume, float pitch, HerculesBeetleEntity targetEntity) {
        super(soundToPlay, volume, pitch, targetEntity);
        this.animatable = targetEntity;
    }

    public HerculesBeetleTickableWalkSound(SoundEvent soundToPlay, float volume, HerculesBeetleEntity targetEntity) {
        super(soundToPlay, volume, targetEntity);
        this.animatable = targetEntity;
    }

    public HerculesBeetleTickableWalkSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, HerculesBeetleEntity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
        this.animatable = targetEntity;
    }

    public HerculesBeetleTickableWalkSound(SoundEvent soundToPlay, HerculesBeetleEntity targetEntity) {
        super(soundToPlay, targetEntity);
        this.animatable = targetEntity;
    }

    public HerculesBeetleTickableWalkSound setFlyingSound(ITickableSound flySound) {
        this.flySound = flySound;
        return this;
    }

    @Override
    public void tick() {
        super.tick();

        if (shouldSwitchToFlyingSound() && !hasSwitched) {
            this.hasSwitched = true;
            Minecraft.getInstance().getSoundManager().queueTickingSound(flySound);
        } else if (!shouldSwitchToFlyingSound()) {
            this.hasSwitched = false;
            Minecraft.getInstance().getSoundManager().stop(flySound);
        }
    }

    @Override
    public boolean shouldSwitchToBgVolume() {
        return super.shouldSwitchToBgVolume() || (shouldSwitchToFlyingSound() && !animatable.isMoving());
    }

    @Override
    public boolean shouldPause() {
        double dx = animatable.getX() - animatable.xo;
        double dz = animatable.getZ() - animatable.zo;
        double dxSqr = dx * dx;
        double dzSqr = dz * dz;

        return shouldSwitchToFlyingSound() || !MC_SOUND_ENGINE.loaded || dxSqr + dzSqr < getMovementThreshold() || (shouldSwitchToFlyingSound() && !animatable.isPlayingAnimation("Fly"));
    }

    public boolean shouldSwitchToFlyingSound() {
        return animatable.isFlying() && animatable.isPlayingAnimation("Fly") && flySound != null;
    }
}
