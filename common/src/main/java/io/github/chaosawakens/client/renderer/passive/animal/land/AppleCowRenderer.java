package io.github.chaosawakens.client.renderer.passive.animal.land;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.model.passive.animal.land.AppleCowModel;
import io.github.chaosawakens.client.renderer.base.layer.EntityGlintOverlayLayer;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.AppleCow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AppleCowRenderer<AC extends AppleCow> extends MobRenderer<AC, AppleCowModel<AC>> {
    public static final ResourceLocation BASE_TEXTURE = CAConstants.prefix("textures/entity/passive/animal/land/apple_cow/apple_cow.png");
    public static final ResourceLocation GOLDEN_TEXTURE = CAConstants.prefix("textures/entity/passive/animal/land/apple_cow/golden_apple_cow.png");

    public AppleCowRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new AppleCowModel<>(ctx.bakeLayer(AppleCowModel.BASE_LAYER)), 0.7F);

        addLayer(new EntityGlintOverlayLayer<>(this).setExtraRenderConditions((ownerAppleCow, partialTicks) -> ownerAppleCow.getType().getDescriptionId().contains("enchanted")));
    }

    @Override
    public ResourceLocation getTextureLocation(AC ownerAppleCow) {
        return ownerAppleCow.getType().getDescriptionId().contains("golden") ? GOLDEN_TEXTURE : BASE_TEXTURE;
    }
}
