package io.github.chaosawakens.client.renderer.passive.animal.land;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.model.passive.animal.land.CarrotPigModel;
import io.github.chaosawakens.client.renderer.base.layer.BasicOverlayRenderLayer;
import io.github.chaosawakens.client.renderer.base.layer.EntityGlintOverlayLayer;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.CarrotPig;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CarrotPigRenderer<CP extends CarrotPig> extends MobRenderer<CP, CarrotPigModel<CP>> {
    public static final ResourceLocation BASE_TEXTURE = CAConstants.prefix("textures/entity/passive/animal/land/carrot_pig/carrot_pig.png");
    public static final ResourceLocation GOLDEN_TEXTURE = CAConstants.prefix("textures/entity/passive/animal/land/carrot_pig/golden_carrot_pig.png");
    public static final ResourceLocation SADDLE_TEXTURE = CAConstants.prefix("textures/entity/passive/animal/land/carrot_pig/carrot_pig_saddle.png");

    public CarrotPigRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new CarrotPigModel<>(ctx.bakeLayer(CarrotPigModel.BASE_LAYER)), 0.5F);

        addLayer(new EntityGlintOverlayLayer<>(this).setExtraRenderConditions((ownerCarrotPig, partialTicks) -> ownerCarrotPig.getType().getDescriptionId().contains("enchanted")));
        addLayer(new BasicOverlayRenderLayer<>(this, RenderType.entityCutoutNoCull(SADDLE_TEXTURE)).setExtraRenderConditions((ownerCarrotPig, partialTicks) -> ownerCarrotPig.isSaddled()));
    }

    @Override
    public ResourceLocation getTextureLocation(CP ownerAppleCow) {
        return ownerAppleCow.getType().getDescriptionId().contains("golden") ? GOLDEN_TEXTURE : BASE_TEXTURE;
    }
}
