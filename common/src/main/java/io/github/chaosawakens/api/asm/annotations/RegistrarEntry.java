package io.github.chaosawakens.api.asm.annotations;

import io.github.chaosawakens.api.platform.services.IRegistrar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used by loader-specific implementations of {@link IRegistrar} in order to discover and load registrar classes annotated with this annotation.
 *
 * @see IRegistrar
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RegistrarEntry {
}
