package io.github.chaosawakens.api.platform.services;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * Loader-agnostic interface used for dynamically delegating object registration without needing multiple methods, classes, or redundant loader-specific setup.
 */
public interface IRegistrar {

    /**
     * Main method for this service interface, called in {@link ChaosAwakens} in order to load it and its loader-specific implementations accordingly. Should NOT be called anywhere else!
     */
    void setupRegistrar();

    /**
     * Attempts to register an object to the specified {@linkplain Registry targetRegistry}.
     * <p>
     * Generally, any registries available in the {@link Registries} class can be used for this method. This could include custom registry types (depending on the loader you're working with).
     *
     * @param objId The objId of the object to register, following Minecraft's regex naming conventions/constraints (<code>[a-z0-9_.-]</code>). Duplicate exceptions and other edge-cases are handled accordingly
     *           within the target mod-loader's registry implementation.
     * @param objSup The object to register. Has to be valid (e.g. not null, matching the target registry's type, etc. duh) for the target registry.
     * @param targetRegistry The target {@link Registry} to register the specified object to.
     *
     * @return The <code>objSup</code> that was registered.
     *
     * @param <T> The object type to register (e.g. {@link Item} or {@link Attribute}).
     */
    <V, T extends V> Supplier<T> registerObject(final ResourceLocation objId, final Supplier<T> objSup, Registry<V> targetRegistry);
}
