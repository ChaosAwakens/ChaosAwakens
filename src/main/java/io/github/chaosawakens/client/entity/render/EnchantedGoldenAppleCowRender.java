package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.model.EnchantedGoldenAppleCowModel;
import io.github.chaosawakens.client.entity.render.layers.CowGlintLayer;
import io.github.chaosawakens.common.entity.EnchantedGoldenAppleCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantedGoldenAppleCowRender extends MobRenderer<EnchantedGoldenAppleCowEntity, EnchantedGoldenAppleCowModel<EnchantedGoldenAppleCowEntity>> {
    private static final ResourceLocation GOLDEN_APPLE_COW_TEXTURES = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/golden_apple_cow.png");

    public EnchantedGoldenAppleCowRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new EnchantedGoldenAppleCowModel<>(), 0.7F);
        this.addLayer(new CowGlintLayer(this));
    }

    public ResourceLocation getEntityTexture(EnchantedGoldenAppleCowEntity entity) {
        return GOLDEN_APPLE_COW_TEXTURES;
    }
}
