package io.github.chaosawakens.client.renderer.base.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiPredicate;

public class BasicOverlayRenderLayer<E extends Entity, EM extends EntityModel<E>> extends RenderLayer<E, EM> {
    protected final RenderType renderType;
    @Nullable
    protected BiPredicate<E, Float> extraRenderConditions;

    public BasicOverlayRenderLayer(RenderLayerParent<E, EM> pRenderer, RenderType renderType) {
        super(pRenderer);
        this.renderType = renderType;
    }

    public BasicOverlayRenderLayer<E, EM> setExtraRenderConditions(BiPredicate<E, Float> extraRenderConditions) {
        this.extraRenderConditions = extraRenderConditions;
        return this;
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, E pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        boolean isVisible = isVisible(pLivingEntity);
        boolean isTranslucent = isTranslucent(pLivingEntity);

        if ((isVisible || isTranslucent) && renderType != null && (extraRenderConditions == null || extraRenderConditions.test(pLivingEntity, pPartialTick))) {
            VertexConsumer overlayRenderVertexBuffer = pBuffer.getBuffer(renderType);

            getParentModel().renderToBuffer(pPoseStack, overlayRenderVertexBuffer, pPackedLight, OverlayTexture.NO_OVERLAY, getRed(pLivingEntity, isTranslucent), getGreen(pLivingEntity, isTranslucent), getBlue(pLivingEntity, isTranslucent), getAlpha(pLivingEntity, isTranslucent));
        }
    }

    public float getRed(E owner, boolean isTranslucent) {
        return 1.0F;
    }

    public float getGreen(E owner, boolean isTranslucent) {
        return 1.0F;
    }

    public float getBlue(E owner, boolean isTranslucent) {
        return 1.0F;
    }

    public float getAlpha(E owner, boolean isTranslucent) {
        return 1.0F;
    }

    public boolean isVisible(E owner) {
        return !owner.isInvisible();
    }

    public boolean isTranslucent(E owner) {
        return !isVisible(owner) && !owner.isInvisibleTo(Minecraft.getInstance().player);
    }
}
