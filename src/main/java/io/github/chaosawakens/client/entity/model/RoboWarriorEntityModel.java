package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RoboWarriorEntityModel extends AnimatedGeoModel{

    @Override
    public ResourceLocation getModelLocation(Object object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/robo_warrior.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/robo_warrior.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object object) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/robo_warrior.animation.json");
    }
}