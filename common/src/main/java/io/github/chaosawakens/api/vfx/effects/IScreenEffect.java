package io.github.chaosawakens.api.vfx.effects;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Hook interface which allows for the modification of the client's visual display by providing commonly-used methods that allow the alteration of rendered elements on-screen (Static/animated overlays, camera effects, level
 * rendering, ghost objects, etc.).
 */
public interface IScreenEffect {

    /**
     * Renders the {@code overlayTextureLocation} passed into this method through Minecraft's {@link Gui}.
     *
     * @param mcGuiGraphics The {@link GuiGraphics} used by the {@linkplain GameRenderer#render(float, long, boolean) GameRenderer's render method}.
     * @param overlayTextureLocation The {@link ResourceLocation} representing the location of the target overlay texture.
     * @param overlayTexAlpha The {@code alpha} value of the passed-in {@code overlayTextureLocation} (Basically its transparency on-screen. Clamped between {@code 0.0F} and {@code 1.0F}).
     * @param partialTick The value representing the partial tick in Minecraft's primary renderer (Essentially the amount of time between each frame, in ticks).
     * @param textureWidth The value representing the total width of the passed-in {@code overlayTextureLocation}, in pixels.
     * @param textureHeight The value representing the total height of the passed-in {@code overlayTextureLocation}, in pixels.
     * @param screenWidth The value representing the total width of the current Minecraft window, in pixels.
     * @param screenHeight The value representing the total height of the current Minecraft window, in pixels.
     *
     * @see Gui#render(GuiGraphics, float)
     */
    void renderBasicOverlay(GuiGraphics mcGuiGraphics, ResourceLocation overlayTextureLocation, double overlayTexAlpha, float partialTick, int textureWidth, int textureHeight, int screenWidth, int screenHeight);
}
