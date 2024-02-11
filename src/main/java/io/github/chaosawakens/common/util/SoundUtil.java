package io.github.chaosawakens.common.util;

import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableIdleMonsterSound;
import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableIdleSound;
import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableWalkMonsterSound;
import io.github.chaosawakens.client.sounds.tickable.base.AnimatableTickableWalkSound;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundEvent;

public final class SoundUtil { //TODO Add common method impls for non-monster entities (laziness momentos)

    private SoundUtil() {
        throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
    }

    /**
     * Queues a looping ticking sound on method call (appropriate checks included, see {@link AnimatableTickableIdleMonsterSound} and {@link AnimatableTickableIdleSound}).
     *
     * @param soundToPlay The {@linkplain SoundEvent looping idle sound} to wrap in an {@link AnimatableTickableIdleMonsterSound} and play.
     * @param targetEntity The target {@link AnimatableMonsterEntity} instance to play the sound for.
     * @param baseVolume The base ("normal") volume of the target sound to play.
     * @param bgVolume The background volume of the target sound to play (E.G. When the target {@link AnimatableMonsterEntity} is in combat, walking, etc.). The {@linkplain AnimatableTickableIdleMonsterSound#bgVolumeSwitchPredicate}
     *                   determines whether this volume variable is used.
     * @param basePitch The base ("normal") pitch of the target sound to play.
     */
    public static void playIdleSoundAsTickable(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity, float baseVolume, float bgVolume, float basePitch) {
        AnimatableTickableIdleMonsterSound targetSound = new AnimatableTickableIdleMonsterSound(soundToPlay, baseVolume, basePitch, targetEntity).setBaseVolume(baseVolume).setBgVolume(bgVolume);
        Minecraft.getInstance().getSoundManager().queueTickingSound(targetSound);
    }

    /**
     * Overloaded variant of {@link #playIdleSoundAsTickable(SoundEvent, AnimatableMonsterEntity, float, float, float)}, but with a hard-set {@code bgVolume} value.
     *
     * @param soundToPlay The {@linkplain SoundEvent looping idle sound} to wrap in an {@link AnimatableTickableIdleMonsterSound} and play.
     * @param targetEntity The target {@link AnimatableMonsterEntity} instance to play the sound for.
     * @param baseVolume The base ("normal") volume of the target sound to play.
     * @param basePitch The base ("normal") pitch of the target sound to play.
     */
    public static void playIdleSoundAsTickable(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity, float baseVolume, float basePitch) {
        playIdleSoundAsTickable(soundToPlay, targetEntity, baseVolume, 0.35F, basePitch);
    }

    /**
     * Overloaded variant of {@link #playIdleSoundAsTickable(SoundEvent, AnimatableMonsterEntity, float, float)}, but with a hard-set {@code basePitch} value.
     *
     * @param soundToPlay The {@linkplain SoundEvent looping idle sound} to wrap in an {@link AnimatableTickableIdleMonsterSound} and play.
     * @param targetEntity The target {@link AnimatableMonsterEntity} instance to play the sound for.
     * @param baseVolume The base ("normal") volume of the target sound to play.
     */
    public static void playIdleSoundAsTickable(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity, float baseVolume) {
        playIdleSoundAsTickable(soundToPlay, targetEntity, baseVolume, 1.0F);
    }

    /**
     * Overloaded variant of {@link #playIdleSoundAsTickable(SoundEvent, AnimatableMonsterEntity, float)}, but with a hard-set {@code baseVolume} value.
     *
     * @param soundToPlay The {@linkplain SoundEvent looping idle sound} to wrap in an {@link AnimatableTickableIdleMonsterSound} and play.
     * @param targetEntity The target {@link AnimatableMonsterEntity} instance to play the sound for.
     */
    public static void playIdleSoundAsTickable(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity) {
        playIdleSoundAsTickable(soundToPlay, targetEntity, 1.0F);
    }

    /**
     * Queues a looping ticking sound on method call (appropriate checks included, see {@link AnimatableTickableWalkMonsterSound} and {@link AnimatableTickableWalkSound}).
     *
     * @param soundToPlay The {@linkplain SoundEvent looping idle sound} to wrap in an {@link AnimatableTickableWalkMonsterSound} and play.
     * @param targetEntity The target {@link AnimatableMonsterEntity} instance to play the sound for.
     * @param baseVolume The base ("normal") volume of the target sound to play.
     * @param bgVolume The background volume of the target sound to play (E.G. When the target {@link AnimatableMonsterEntity} is in combat, walking, etc.). The {@linkplain AnimatableTickableWalkMonsterSound#bgVolumeSwitchPredicate}
     *                   determines whether this volume variable is used.
     * @param basePitch The base ("normal") pitch of the target sound to play.
     */
    public static void playWalkingSoundAsTickable(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity, float baseVolume, float bgVolume, float basePitch) {
        AnimatableTickableWalkMonsterSound targetSound = new AnimatableTickableWalkMonsterSound(soundToPlay, baseVolume, basePitch, targetEntity).setBaseVolume(baseVolume).setBgVolume(bgVolume);
        Minecraft.getInstance().getSoundManager().queueTickingSound(targetSound);
    }

    /**
     * Overloaded variant of {@link #playWalkingSoundAsTickable(SoundEvent, AnimatableMonsterEntity, float, float, float)}, but with a hard-set {@code bgVolume} value.
     *
     * @param soundToPlay The {@linkplain SoundEvent looping idle sound} to wrap in an {@link AnimatableTickableWalkMonsterSound} and play.
     * @param targetEntity The target {@link AnimatableMonsterEntity} instance to play the sound for.
     * @param baseVolume The base ("normal") volume of the target sound to play.
     * @param basePitch The base ("normal") pitch of the target sound to play.
     */
    public static void playWalkingSoundAsTickable(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity, float baseVolume, float basePitch) {
        playWalkingSoundAsTickable(soundToPlay, targetEntity, baseVolume, 0.35F, basePitch);
    }

    /**
     * Overloaded variant of {@link #playWalkingSoundAsTickable(SoundEvent, AnimatableMonsterEntity, float, float)}, but with a hard-set {@code basePitch} value.
     *
     * @param soundToPlay The {@linkplain SoundEvent looping idle sound} to wrap in an {@link AnimatableTickableWalkMonsterSound} and play.
     * @param targetEntity The target {@link AnimatableMonsterEntity} instance to play the sound for.
     * @param baseVolume The base ("normal") volume of the target sound to play.
     */
    public static void playWalkingSoundAsTickable(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity, float baseVolume) {
        playWalkingSoundAsTickable(soundToPlay, targetEntity, baseVolume, 1.0F);
    }

    /**
     * Overloaded variant of {@link #playWalkingSoundAsTickable(SoundEvent, AnimatableMonsterEntity, float)}, but with a hard-set {@code baseVolume} value.
     *
     * @param soundToPlay The {@linkplain SoundEvent looping idle sound} to wrap in an {@link AnimatableTickableWalkMonsterSound} and play.
     * @param targetEntity The target {@link AnimatableMonsterEntity} instance to play the sound for.
     */
    public static void playWalkingSoundAsTickable(SoundEvent soundToPlay, AnimatableMonsterEntity targetEntity) {
        playWalkingSoundAsTickable(soundToPlay, targetEntity, 1.0F);
    }
}