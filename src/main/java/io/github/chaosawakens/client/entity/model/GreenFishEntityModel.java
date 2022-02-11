package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.GreenFishEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GreenFishEntityModel extends AnimatedGeoModel<GreenFishEntity> {
    @Override
    public ResourceLocation getModelLocation(GreenFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/green_fish.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GreenFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/green_fish.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GreenFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/green_fish.animation.json");
    }
    
    @Override
    public void setLivingAnimations(GreenFishEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
    	super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
