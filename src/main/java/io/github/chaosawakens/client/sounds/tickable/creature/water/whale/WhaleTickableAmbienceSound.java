package io.github.chaosawakens.client.sounds.tickable.creature.water.whale;

import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableIdleSound;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class WhaleTickableAmbienceSound extends AnimatableTickableIdleSound {

    public WhaleTickableAmbienceSound(SoundEvent soundToPlay, float volume, float pitch, Entity targetEntity) {
        super(soundToPlay, volume, pitch, targetEntity);
    }

    public WhaleTickableAmbienceSound(SoundEvent soundToPlay, float volume, Entity targetEntity) {
        super(soundToPlay, volume, targetEntity);
    }

    public WhaleTickableAmbienceSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, Entity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
    }

    public WhaleTickableAmbienceSound(SoundEvent soundToPlay, Entity targetEntity) {
        super(soundToPlay, targetEntity);
    }

    @Override
    public void tick() {
        super.tick();
        setVolume(5.0F);
    }

    @Override
    public boolean shouldPause() {
        return super.shouldPause() || (Minecraft.getInstance().player != null && !Minecraft.getInstance().player.isUnderWater());
    }
}
