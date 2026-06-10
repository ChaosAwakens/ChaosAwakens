package io.github.chaosawakens.mixins.client;

import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    private GameRendererMixin() {
        throw new IllegalAccessError("Attempted to construct Mixin class! (GameRendererMixin)");
    }
}
