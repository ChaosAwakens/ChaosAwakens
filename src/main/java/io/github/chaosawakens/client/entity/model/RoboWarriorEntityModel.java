package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.RoboWarriorEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RoboWarriorEntityModel extends AnimatedGeoModel<RoboWarriorEntity> {

    @Override
    public ResourceLocation getModelLocation(RoboWarriorEntity entity) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/robo_warrior.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RoboWarriorEntity entity) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/robos/robo_warrior.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RoboWarriorEntity entity) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/robo_warrior.animation.json");
    }
}