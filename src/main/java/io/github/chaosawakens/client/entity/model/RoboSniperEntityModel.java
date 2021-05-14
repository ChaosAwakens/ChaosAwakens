package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RoboSniperEntityModel extends AnimatedGeoModel{

    @Override
    public ResourceLocation getModelLocation(Object object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/robo_sniper.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/robo_sniper.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object object) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/robo_sniper.animation.json");
    }
}