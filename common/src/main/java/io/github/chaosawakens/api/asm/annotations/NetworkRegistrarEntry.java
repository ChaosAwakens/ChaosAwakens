package io.github.chaosawakens.api.asm.annotations;

import io.github.chaosawakens.api.platform.services.INetworkManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation interface used by loader-specific implementations of {@link INetworkManager} in order to discover and load network registrar classes annotated with this annotation.
 *
 * @see INetworkManager
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface NetworkRegistrarEntry {
}
