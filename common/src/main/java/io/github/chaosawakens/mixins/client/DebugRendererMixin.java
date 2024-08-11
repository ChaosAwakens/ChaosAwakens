package io.github.chaosawakens.mixins.client;

import net.minecraft.client.renderer.debug.DebugRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DebugRenderer.class)
public abstract class DebugRendererMixin {

    private DebugRendererMixin() {
        throw new IllegalAccessError("Attempted to construct standalone Mixin Class!");
    }

    
}
