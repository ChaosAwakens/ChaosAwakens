package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.WoodFishEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WoodFishModel extends AnimatedGeoModel<WoodFishEntity> {

    @Override
    public ResourceLocation getModelLocation(WoodFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/wood_fish.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WoodFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/wood_fish.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WoodFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/wood_fish.animation.json");
    }

    @Override
    public void setLivingAnimations(WoodFishEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }

}
