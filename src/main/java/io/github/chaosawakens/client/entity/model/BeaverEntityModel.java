package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BeaverEntityModel extends AnimatedGeoModel{

    @Override
    public ResourceLocation getModelLocation(Object object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/beaver.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/beaver.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object object) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/beaver.animation.json");
    }
}