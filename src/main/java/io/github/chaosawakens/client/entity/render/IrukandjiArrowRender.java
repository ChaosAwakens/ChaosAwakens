package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.projectile.IrukandjiArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IrukandjiArrowRender extends ArrowRenderer<IrukandjiArrowEntity> {
    public static final ResourceLocation IRUKANDJI_ARROW_TEXTURE = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/projectiles/irukandji_arrow.png");

    public IrukandjiArrowRender(EntityRendererManager manager) {
        super(manager);
    }

    public ResourceLocation getEntityTexture(IrukandjiArrowEntity entity) {
        return IRUKANDJI_ARROW_TEXTURE;
    }

}

