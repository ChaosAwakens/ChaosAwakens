package io.github.chaosawakens.client.renderer.neutral;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.client.model.neutral.RubberDuckyModel;
import io.github.chaosawakens.common.entity.prototype.neutral.RubberDucky;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RubberDuckyRenderer<RD extends RubberDucky> extends MobRenderer<RD, RubberDuckyModel<RD>> {
    public static final ResourceLocation BASE_TEXTURE = CAConstants.prefix("textures/entity/neutral/rubber_ducky.png");

    public RubberDuckyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new RubberDuckyModel<>(ctx.bakeLayer(RubberDuckyModel.BASE_LAYER)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(RD rd) {
        return BASE_TEXTURE;
    }
}
