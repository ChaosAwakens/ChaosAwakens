package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.GoldenAppleCowModel;
import io.github.chaosawakens.entity.GoldenAppleCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GoldenAppleCowRender extends MobRenderer<GoldenAppleCowEntity, GoldenAppleCowModel<GoldenAppleCowEntity>> {
    private static final ResourceLocation GOLDEN_APPLE_COW_TEXTURES = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/golden_apple_cow.png");

    public GoldenAppleCowRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new GoldenAppleCowModel<>(), 0.7F);
    }

    public ResourceLocation getEntityTexture(GoldenAppleCowEntity entity) {
        return GOLDEN_APPLE_COW_TEXTURES;
    }
}
