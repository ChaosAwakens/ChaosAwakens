package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entity.RainbowAntEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RainbowAntEntityModel<T extends RainbowAntEntity> extends AnimatedGeoModel{

    @Override
    public ResourceLocation getModelLocation(Object object)
    {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/ant.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object)
    {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/ant/rainbow_ant.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object object)
    {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/ant.animation.json");
    }
}
