package io.github.chaosawakens.client.entity.render.layers;


import io.github.chaosawakens.client.entity.model.EnchantedGoldenAppleCowModel;
import io.github.chaosawakens.common.entity.EnchantedGoldenAppleCowEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CowGlintLayer extends EnergyLayer<EnchantedGoldenAppleCowEntity, EnchantedGoldenAppleCowModel<EnchantedGoldenAppleCowEntity>> {
    private static final ResourceLocation GLINT_TEXTURE = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private final EnchantedGoldenAppleCowModel<EnchantedGoldenAppleCowEntity> cowModel = new EnchantedGoldenAppleCowModel<>();

    public CowGlintLayer(IEntityRenderer<EnchantedGoldenAppleCowEntity, EnchantedGoldenAppleCowModel<EnchantedGoldenAppleCowEntity>> p_i50947_1_) {
        super(p_i50947_1_);
    }

    protected float func_225634_a_(float p_225634_1_) {
        return p_225634_1_ * 0.01F;
    }

    protected ResourceLocation func_225633_a_() {
        return GLINT_TEXTURE;
    }

    protected EntityModel<EnchantedGoldenAppleCowEntity> func_225635_b_() {
        return this.cowModel;
    }
}