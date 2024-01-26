package io.github.chaosawakens.client.sounds.tickable.base;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.EntityTickableSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class AnimatableTickableWalkSound extends EntityTickableSound {
    private final IAnimatableEntity animatableTarget;

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

        if (!shouldPlaySound()) stop();
    }

    public void playSound() {
        SoundHandler mcSoundHandler = Minecraft.getInstance().getSoundManager();

        if (!mcSoundHandler.isActive(this) && shouldPlaySound()) {
            mcSoundHandler.queueTickingSound(this);
            ChaosAwakens.LOGGER.debug("Played");
        }
    }

    public boolean shouldPlaySound() {
        LivingEntity targetLivingEntity = (LivingEntity) animatableTarget;
        double dx = targetLivingEntity.getX() - targetLivingEntity.xo;
        double dz = targetLivingEntity.getZ() - targetLivingEntity.zo;
        double dxSqr = dx * dx;
        double dzSqr = dz * dz;

        return dxSqr + dzSqr < getDeltaMovementThreshold();
    }

    public double getDeltaMovementThreshold() {
        return 2.500000277905201E-7;
    }
}
