package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.render.util.EntityTextureEnum;
import io.github.chaosawakens.common.entity.AppleCowEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class AppleCowRender extends MobRenderer<AppleCowEntity, CowModel<AppleCowEntity>> {

    public AppleCowRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(AppleCowEntity entity) {
        EntityTextureEnum coatType = EntityTextureEnum.byId(entity.getTextureType());
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/apple_cow/" + coatType.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
