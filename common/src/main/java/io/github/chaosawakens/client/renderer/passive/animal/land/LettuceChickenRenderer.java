package io.github.chaosawakens.client.renderer.passive.animal.land;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.model.passive.animal.land.LettuceChickenModel;
import io.github.chaosawakens.common.entity.prototype.passive.animal.land.LettuceChicken;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LettuceChickenRenderer<LC extends LettuceChicken> extends MobRenderer<LC, LettuceChickenModel<LC>> {
    public static final ResourceLocation BASE_TEXTURE = CAConstants.prefix("textures/entity/passive/animal/land/lettuce_chicken/lettuce_chicken.png");

    public LettuceChickenRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new LettuceChickenModel<>(ctx.bakeLayer(LettuceChickenModel.BASE_LAYER)), 0.32F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(LC ownerStinkBug) {
        return BASE_TEXTURE;
    }
}