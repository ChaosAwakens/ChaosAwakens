package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.CrystalAppleCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrystalAppleCowRender extends MobRenderer<CrystalAppleCowEntity, CowModel<CrystalAppleCowEntity>> {
    private static final ResourceLocation CRYSTAL_APPLE_COW_TEXTURES = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/crystal_apple_cow.png");

    public CrystalAppleCowRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
    }

    public ResourceLocation getEntityTexture(CrystalAppleCowEntity entity) {
        return CRYSTAL_APPLE_COW_TEXTURES;
    }
}
