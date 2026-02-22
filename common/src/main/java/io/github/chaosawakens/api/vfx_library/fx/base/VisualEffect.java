package io.github.chaosawakens.api.vfx_library.fx.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public interface VisualEffect<T> {

    void render(RenderTarget<T> renderTarget, PoseStack curStack, MultiBufferSource buffer, float partialTicks);

    boolean shouldDispose();

    interface RenderTarget<T> {

        T getTarget();
    }
}
