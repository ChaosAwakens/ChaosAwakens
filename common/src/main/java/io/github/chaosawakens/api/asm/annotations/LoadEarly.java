package io.github.chaosawakens.api.asm.annotations;

import io.github.chaosawakens.ChaosAwakens;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation interface used to load classes that need early class-loading ahead of all other mod-loading stages.
 *
 * @see ChaosAwakens
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface LoadEarly {
}
