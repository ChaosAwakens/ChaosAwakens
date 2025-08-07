package io.github.chaosawakens.api.animation.geckolib.math.easing.base;

import io.github.chaosawakens.util.MathUtil;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Locale;
import java.util.Optional;

/**
 * Base {@code interface} representing a mathematical easing function between any given keyframe(s). This is the
 * functional equivalent of Geckolib's {@code EasingType} {@code interface}, except it's revised to have full integration
 * with CA's in-house ARS system.
 * <br></br>
 * The mathematical functions used here are mostly provided generously by <a href="https://easings.net">easings.net</a>.
 */
@FunctionalInterface
public interface MathematicalEasing {
    Object2ObjectOpenHashMap<String, MathematicalEasing> KNOWN_EASING_TYPES = new Object2ObjectOpenHashMap<>();

    // Built In Easings (all Geckolib-supported easing types) - minimal changes for now since the names are hardcoded in Geckolib (e.g. "easeInSine"), rather than being super-categorised based on keyframe easing type.
    MathematicalEasing LINEAR = registerEasing("linear", easingArgs -> originalProgress -> originalProgress);
    MathematicalEasing QUADRATIC_EASE_IN = registerEasing("easeinquad", easingArgs -> MathUtil::quadraticEasing);

    /**
     * Transforms the value passed in according to the easing function. Takes an input of easing args and outputs a
     * {@code Double2DoubleFunction} whose input is the original keyframe progress value and whose output is the transformed
     * value.
     *
     * @param easingArgs Arguments specified in the easing function. May be empty. Primarily used for easings that require
     *                   different parameters, such as step easing, whose only easingArg is the step count.
     *
     * @return The transformed value based on the easing function to be applied.
     */
    Double2DoubleFunction applyEasing(Optional<DoubleList> easingArgs);

    /**
     * Registers a new easing type to be tracked and used by animations.
     *
     * @param name The name of the easing type, lowercased and trimmed.
     * @param easing The easing type to register.
     *
     * @return The easing type that was registered.
     */
    static MathematicalEasing registerEasing(String name, MathematicalEasing easing) {
        KNOWN_EASING_TYPES.putIfAbsent(name.toLowerCase(Locale.ROOT).trim().replaceAll("\t\n", ""), easing);
        return easing;
    }

    /**
     * Retrieves a previously registered easing type. Formats the input {@code name}.
     *
     * @param name The name of the easing type, lowercased and trimmed.
     *
     * @return The easing type that was registered.
     */
    static MathematicalEasing getEasing(String name) {
        return KNOWN_EASING_TYPES.getOrDefault(name.toLowerCase(Locale.ROOT).trim().replaceAll("\t\n", ""), LINEAR);
    }

    /**
     * Attempts to retrieve the easing name from the easing type.
     *
     * @param easing The easing type to retrieve the name from.
     *
     * @return The easing name. Defaults to "linear" if not found.
     */
    static String getEasingName(MathematicalEasing easing) {
        return KNOWN_EASING_TYPES.keySet().stream()
                .filter(easingName -> KNOWN_EASING_TYPES.get(easingName) == easing)
                .findFirst()
                .orElse("linear");
    }
}
