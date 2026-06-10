package io.github.chaosawakens.api.animation_resolver_system.faal.animation.controller;

/**
 * IDLE - Inactive
 * TRANSITIONING - Transition ticks (0 - 1 across literal ticks on the server)
 * TICKING - Tick animation ticks
 * <br></br>
 * Animations should be responsible for managing only their own states.
 */
@FunctionalInterface
public interface ControllerState {
    ControllerState IDLE = (controller) -> {
        // Transition to TRANSITIONING (or from TRANSITIONING)
    };
    ControllerState TICKING = (controller) -> {
        // Update ticking for per-animation
    };
    ControllerState TRANSITIONING = (controller) -> {
        // Update transitioning for per-animation
    };

    void tick(AnimationController<?> controller);
}
