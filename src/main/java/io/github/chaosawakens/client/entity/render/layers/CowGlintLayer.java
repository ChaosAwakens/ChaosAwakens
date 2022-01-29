package io.github.chaosawakens.client.entity.render.layers;

import io.github.chaosawakens.client.entity.model.AppleCowModel;
import io.github.chaosawakens.common.entity.EnchantedGoldenAppleCowEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CowGlintLayer extends EnergyLayer<EnchantedGoldenAppleCowEntity, AppleCowModel<EnchantedGoldenAppleCowEntity>> {
    private static final ResourceLocation GLINT_TEXTURE = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private final AppleCowModel<EnchantedGoldenAppleCowEntity> cowModel = new AppleCowModel<>();

    public CowGlintLayer(IEntityRenderer<EnchantedGoldenAppleCowEntity, AppleCowModel<EnchantedGoldenAppleCowEntity>> p_i50947_1_) {
        super(p_i50947_1_);
    }

    protected float xOffset(float p_225634_1_) {
        return p_225634_1_ * 0.01F;
    }

    protected ResourceLocation getTextureLocation() {
        return GLINT_TEXTURE;
    }

    protected EntityModel<EnchantedGoldenAppleCowEntity> model() {
        return this.cowModel;
    }
}