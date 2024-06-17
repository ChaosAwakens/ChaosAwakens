package io.github.chaosawakens.client.sounds.tickable.boss.insect;

import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableIdleSound;
import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class HerculesBeetleTickableMunchSound extends AnimatableTickableIdleSound {
    private final HerculesBeetleEntity ownerBeetle;

    public HerculesBeetleTickableMunchSound(SoundEvent soundToPlay, float volume, float pitch, HerculesBeetleEntity ownerBeetle) {
        super(soundToPlay, volume, pitch, ownerBeetle);
        this.ownerBeetle = ownerBeetle;
    }

    public HerculesBeetleTickableMunchSound(SoundEvent soundToPlay, float volume,HerculesBeetleEntity ownerBeetle) {
        super(soundToPlay, volume, ownerBeetle);
        this.ownerBeetle = ownerBeetle;
    }

    public HerculesBeetleTickableMunchSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, HerculesBeetleEntity ownerBeetle) {
        super(soundToPlay, targetEntitySoundCategory, ownerBeetle);
        this.ownerBeetle = ownerBeetle;
    }

    public HerculesBeetleTickableMunchSound(SoundEvent soundToPlay, HerculesBeetleEntity ownerBeetle) {
        super(soundToPlay, ownerBeetle);
        this.ownerBeetle = ownerBeetle;
    }

    @Override
    public boolean shouldPause() {
        return super.shouldPause() || ownerBeetle.getAttackID() != HerculesBeetleEntity.MUNCH_ATTACK_ID;
    }
}
