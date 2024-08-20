package io.github.chaosawakens.api.platform.services;

/**
 * Loader-agnostic interface for managing GeckoLib implementations on a fundamental level (I.E. File-parsing, model rendering, etc.) since GeckoLib4 on 1.20.1 doesn't utilise a MultiLoader project setup. Also manages
 * FAAL implementations alongside GeckoLib for each loader-specific implementation of this interface.
 */
public interface IAnimationManager {
}
