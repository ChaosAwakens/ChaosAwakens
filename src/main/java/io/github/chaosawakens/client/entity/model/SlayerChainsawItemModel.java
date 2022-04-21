package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.SlayerChainsawItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SlayerChainsawItemModel extends AnimatedGeoModel<SlayerChainsawItem> {

    @Override
    public ResourceLocation getModelLocation(SlayerChainsawItem object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/slayer_chainsaw.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SlayerChainsawItem object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/item/slayer_chainsaw_model.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SlayerChainsawItem animatable) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/slayer_chainsaw.animation.json");
    }
}
