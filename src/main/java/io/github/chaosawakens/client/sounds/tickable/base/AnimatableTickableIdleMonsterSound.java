package io.github.chaosawakens.client.sounds.tickable.base;

import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class AnimatableTickableIdleMonsterSound extends AnimatableTickableIdleSound { //TODO Probably group and clean all this up by 1.19/1.20+ (and add more appropriate methods like 'playSound' methods and stuff)
    public static final Predicate<AnimatableMonsterEntity> DEFAULT_BG_VOLUME_SWITCH_PREDICATE = (animatableMonsterEntity -> !animatableMonsterEntity.isDeadOrDying() && !animatableMonsterEntity.isSilent() && !animatableMonsterEntity.isMoving() && !animatableMonsterEntity.isAttacking());
    protected float baseVolume = 1.0F;
    protected float bgVolume = 0.35F;
    @Nullable
    protected Predicate<AnimatableMonsterEntity> bgVolumeSwitchPredicate = DEFAULT_BG_VOLUME_SWITCH_PREDICATE;

    public AnimatableTickableIdleMonsterSound(SoundEvent soundToPlay, float volume, float pitch, AnimatableMonsterEntity targetEntity) {
        super(soundToPlay, volume, pitch, targetEntity);
    }

    public AnimatableTickableIdleMonsterSound(SoundEvent soundToPlay, float volume, AnimatableMonsterEntity targetEntity) {
        super(soundToPlay, volume, targetEntity);
    }

    public AnimatableTickableIdleMonsterSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, AnimatableMonsterEntity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
    }

    public AnimatableTickableIdleMonsterSound(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity) {
        super(soundToPlay, targetEntity);
    }

    public AnimatableTickableIdleMonsterSound setBaseVolume(float baseVolume) {
        this.baseVolume = baseVolume;
        return this;
    }

    public AnimatableTickableIdleMonsterSound setBgVolume(float bgVolume) {
        this.bgVolume = bgVolume;
        return this;
    }

    public AnimatableTickableIdleMonsterSound setBgVolumeSwitchPredicate(Predicate<AnimatableMonsterEntity> bgVolumeSwitchPredicate) {
        this.bgVolumeSwitchPredicate = bgVolumeSwitchPredicate;
        return this;
    }

    public float getBaseVolume() {
        return baseVolume;
    }

    public float getBgVolume() {
        return bgVolume;
    }

    public Predicate<AnimatableMonsterEntity> getBgVolumeSwitchPredicate() {
        return bgVolumeSwitchPredicate;
    }

    @Override
    public void tick() {
        super.tick();

        setVolume(shouldSwitchToBgVolume() ? bgVolume : baseVolume);
    }

    public boolean shouldSwitchToBgVolume() {
        AnimatableMonsterEntity animatableMonsterTarget = (AnimatableMonsterEntity) animatableTarget;
        return bgVolumeSwitchPredicate != null && bgVolumeSwitchPredicate.test(animatableMonsterTarget);
    }
}
