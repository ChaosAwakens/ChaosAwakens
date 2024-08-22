package io.github.chaosawakens.api.vfx.effects;

/**
 * An interface representing any element that can be rendered on a client's visual display (Static/animated overlays, camera effects, level
 * rendering, ghost objects, etc.).
 */
public interface IScreenElement {

    /**
     * Whether this element should render at all on the client's visual display.
     *
     * @return Whether this element should render at all on the client's visual display.
     */
    boolean shouldRender();
}
