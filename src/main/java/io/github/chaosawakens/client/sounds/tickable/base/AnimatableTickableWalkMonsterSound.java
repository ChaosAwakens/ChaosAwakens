package io.github.chaosawakens.client.sounds.tickable.base;

import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class AnimatableTickableWalkMonsterSound extends AnimatableTickableWalkSound{
    public static final Predicate<AnimatableMonsterEntity> DEFAULT_BG_VOLUME_SWITCH_PREDICATE = (animatableMonsterEntity -> animatableMonsterEntity.isStuck());
    protected float baseVolume = 1.0F;
    protected float bgVolume = 0.25F;
    @Nullable
    protected Predicate<AnimatableMonsterEntity> bgVolumeSwitchPredicate = DEFAULT_BG_VOLUME_SWITCH_PREDICATE;

    public AnimatableTickableWalkMonsterSound(SoundEvent soundToPlay, float volume, float pitch, AnimatableMonsterEntity targetEntity) {
        super(soundToPlay, volume, pitch, targetEntity);
    }

    public AnimatableTickableWalkMonsterSound(SoundEvent soundToPlay, float volume, AnimatableMonsterEntity targetEntity) {
        super(soundToPlay, volume, targetEntity);
    }

    public AnimatableTickableWalkMonsterSound(SoundEvent soundToPlay, SoundCategory targetEntitySoundCategory, AnimatableMonsterEntity targetEntity) {
        super(soundToPlay, targetEntitySoundCategory, targetEntity);
    }

    public AnimatableTickableWalkMonsterSound(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity) {
        super(soundToPlay, targetEntity);
    }

    public AnimatableTickableWalkMonsterSound setBaseVolume(float baseVolume) {
        this.baseVolume = baseVolume;
        return this;
    }

    public AnimatableTickableWalkMonsterSound setBgVolume(float bgVolume) {
        this.bgVolume = bgVolume;
        return this;
    }

    public AnimatableTickableWalkMonsterSound setBgVolumeSwitchPredicate(Predicate<AnimatableMonsterEntity> bgVolumeSwitchPredicate) {
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

    @Override
    public boolean shouldPause() {
        AnimatableMonsterEntity animatableMonsterTarget = (AnimatableMonsterEntity) animatableTarget;
        return super.shouldPause() || animatableMonsterTarget.isAttacking();
    }

    public boolean shouldSwitchToBgVolume() {
        AnimatableMonsterEntity animatableMonsterTarget = (AnimatableMonsterEntity) animatableTarget;
        return bgVolumeSwitchPredicate != null && bgVolumeSwitchPredicate.test(animatableMonsterTarget);
    }
}
