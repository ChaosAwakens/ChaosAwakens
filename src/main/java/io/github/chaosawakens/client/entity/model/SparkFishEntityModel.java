package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.SparkFishEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SparkFishEntityModel extends AnimatedGeoModel<SparkFishEntity> {
    @Override
    public ResourceLocation getModelLocation(SparkFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/fish/spark_fish.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SparkFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/spark_fish.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SparkFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/spark_fish.animation.json");
    }
    
    @Override
    public void setLivingAnimations(SparkFishEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
    	super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
