package io.github.chaosawakens.api.animation_resolver_system.prototyping.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.chaosawakens.api.animation_resolver_system.prototyping.model.BlobModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class BlobRenderer<E extends Entity> extends EntityRenderer<E> {
    protected final BlobModel<E> model;

    public BlobRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.model = new BlobModel<>();
    }

    @Override
    public void render(E entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);

        this.model.prepareMobModel(entity, 0.0F, 0.0F, partialTick);
        this.model.setupAnim(entity, 0.0F, 0.0F, partialTick, 0.0F, 0.0F);

        this.model.renderToBuffer(poseStack, buffer.getBuffer(RenderType.debugQuads()), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(E e) {
        return new ResourceLocation("none");
    }
}
