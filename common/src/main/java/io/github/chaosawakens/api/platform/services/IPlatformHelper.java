package io.github.chaosawakens.api.platform.services;

import io.github.chaosawakens.api.loader.ModLoader;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * A loader-agnostic interface for managing platform-specific implementations of certain loader-specific features, ranging from methods that provide predicates for loader detection to methods that
 * allow for more performant ways of performing certain tasks, such as class-loading, environment-detection, etc.
 */
public interface IPlatformHelper {

    /**
     * Gets the {@link ModLoader} representation of the current platform.
     *
     * @return The {@link ModLoader} representation of the current platform.
     */
    ModLoader getPlatform();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     *
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Discovers all (mod) classes that are annotated with the specified annotation type and compiles them into a {@link List}. Take note that this method <b>loads</b> (valid) discovered classes.
     *
     * @param annotationTypeClazz The annotation type class.
     *
     * @return A {@link List} of classes annotated with the specified annotation type.
     */
    List<Class<?>> discoverAnnotatedClasses(Class<? extends Annotation> annotationTypeClazz);

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }
}