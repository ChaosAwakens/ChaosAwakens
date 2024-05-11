package io.github.chaosawakens.client.sounds.tickable.boss.insect;

import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableIdleMonsterSound;
import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

public class HerculesBeetleTickableIdleSound extends AnimatableTickableIdleMonsterSound {
    private final HerculesBeetleEntity animatable;
    @Nullable
    private ITickableSound docilitySound;
    private boolean hasSwitched = false;

    public HerculesBeetleTickableIdleSound(SoundEvent soundToPlay, float volume, float pitch, HerculesBeetleEntity targetEntity) {
        super(soundToPlay, volume, pitch, targetEntity);
        this.animatable = targetEntity;
    }

    public HerculesBeetleTickableIdleSound(SoundEvent soundToPlay, float volume, HerculesBeetleEntity targetEntity) {
        super(soundToPlay, volume, targetEntity);
        this.animatable = targetEntity;
    }

    public HerculesBeetleTickableIdleSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, HerculesBeetleEntity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
        this.animatable = targetEntity;
    }

    public HerculesBeetleTickableIdleSound(SoundEvent soundToPlay, HerculesBeetleEntity targetEntity) {
        super(soundToPlay, targetEntity);
        this.animatable = targetEntity;
    }

    public HerculesBeetleTickableIdleSound setDocilitySound(ITickableSound docilitySound) {
        this.docilitySound = docilitySound;
        return this;
    }

    @Override
    public void tick() {
        super.tick();

        if (shouldSwitchToDocilitySound() && !hasSwitched) {
            this.hasSwitched = true;
            Minecraft.getInstance().getSoundManager().queueTickingSound(docilitySound);
        } else if (!shouldSwitchToDocilitySound()) {
            this.hasSwitched = false;
            Minecraft.getInstance().getSoundManager().stop(docilitySound);
        }
    }

    @Override
    public boolean shouldPause() {
        return animatable.isFlying() || shouldSwitchToDocilitySound() || animatable.isAwakening() || super.shouldPause();
    }

    public boolean shouldSwitchToDocilitySound() {
        return animatable.isDocile() && docilitySound != null;
    }
}
