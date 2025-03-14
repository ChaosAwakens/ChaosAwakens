package io.github.chaosawakens.api.animation.faal.base;

import io.github.chaosawakens.api.animation.faal.entity.WrappedAnimatableEntity;
import io.github.chaosawakens.api.animation.faal.item.WrappedAnimatableItem;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.common.networking.packets.s2c.AnimationPlayPacket;
import io.github.chaosawakens.common.networking.packets.s2c.AnimationStopPacket;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * The base {@code interface} representing any type of object that wants to be animated utilising VKF and FAAL. This interface contains the lowest-level implementations required to
 * implement animatable objects of any given type. When applied to fleshed-out objects on its own, this is effectively useless. See this interface's implementations if you want to
 * work with natively supported (or otherwise existing) animatable types.
 *
 * @see WrappedAnimatableEntity
 * @see WrappedAnimatableItem
 */
public interface WrappedAnimatable {

    default void playAnimation(String animationStateName, boolean forcePose) {
        if (this instanceof Entity ownerEntity) {
            Optional<ExtendedAnimationState> targetAnimOptional = getCachedAnimationStates().stream().filter(curAnimState -> curAnimState.getAnimationName().equals(animationStateName)).findFirst();

            targetAnimOptional.ifPresent(targetAnim -> {
                if (!ownerEntity.level().isClientSide()) {
                    CAServices.NETWORK_MANAGER.sendToTrackingClients(new AnimationPlayPacket(ownerEntity.getId(), animationStateName, forcePose), ownerEntity);

                    if (targetAnim.isServerTickable() && !targetAnim.isStarted()) targetAnim.start(ownerEntity.tickCount);
                } else { // Extra guard check
                    if (forcePose) getCachedAnimationStates().stream().filter(curAnimState -> !curAnimState.getAnimationName().equals(animationStateName)).forEach(this::stopAnimation);
                    if (!targetAnim.isStarted()) targetAnim.start(ownerEntity.tickCount);
                }
            });
        }
    }

    default void stopAnimation(String animationStateName) {
        if (this instanceof Entity ownerEntity) {
            Optional<ExtendedAnimationState> targetAnimOptional = getCachedAnimationStates().stream().filter(curAnimState -> curAnimState.getAnimationName().equals(animationStateName)).findFirst();

            targetAnimOptional.ifPresent(targetAnim -> {
                if (!ownerEntity.level().isClientSide()) {
                    CAServices.NETWORK_MANAGER.sendToTrackingClients(new AnimationStopPacket(ownerEntity.getId(), animationStateName), ownerEntity);

                    if (targetAnim.isServerTickable() && targetAnim.isStarted()) targetAnim.stop();
                } else targetAnim.stop();
            });
        }
    }

    default void playAnimation(String animationStateName) {
        playAnimation(animationStateName, false);
    }

    default void playAnimation(ExtendedAnimationState animationState, boolean forcePose) {
        playAnimation(animationState.getAnimationName(), forcePose);
    }

    default void stopAnimation(ExtendedAnimationState animationState) {
        stopAnimation(animationState.getAnimationName());
    }

    default void playAnimation(ExtendedAnimationState animationState) {
        playAnimation(animationState, false);
    }

    default ExtendedAnimationState wrapState(String animationName) {
        return wrapState(animationName, -1);
    }

    default ExtendedAnimationState wrapState(String animationName, int tickDuration) {
        ExtendedAnimationState defState = new WrappedAnimation(animationName, tickDuration);

        if (!getCachedAnimationStates().contains(defState)) getCachedAnimationStates().add(defState);

        return defState;
    }

    @Nullable
    default ExtendedAnimationState getAnimation(String animName) {
        return getCachedAnimationStates().stream().filter(curAnimState -> curAnimState.getAnimationName().equals(animName)).findFirst().get();
    }

    ObjectArrayList<ExtendedAnimationState> getCachedAnimationStates();

    default boolean requiresServerAnimationTicking() {
        return true;
    }
}
