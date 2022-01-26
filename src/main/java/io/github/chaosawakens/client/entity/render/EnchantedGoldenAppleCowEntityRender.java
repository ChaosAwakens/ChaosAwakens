package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.render.layers.CowGlintLayer;
import io.github.chaosawakens.client.entity.model.AppleCowModel;
import io.github.chaosawakens.common.entity.EnchantedGoldenAppleCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantedGoldenAppleCowEntityRender extends MobRenderer<EnchantedGoldenAppleCowEntity, AppleCowModel<EnchantedGoldenAppleCowEntity>> {
    private static final ResourceLocation GOLDEN_APPLE_COW_TEXTURE = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/golden_apple_cow.png");

    public EnchantedGoldenAppleCowEntityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new AppleCowModel<>(), 0.7F);
        this.addLayer(new CowGlintLayer(this));
    }

    public ResourceLocation getTextureLocation(EnchantedGoldenAppleCowEntity entity) {
        return GOLDEN_APPLE_COW_TEXTURE;
    }
}
