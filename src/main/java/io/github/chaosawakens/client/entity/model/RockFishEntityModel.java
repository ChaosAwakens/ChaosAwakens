package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.RockFishEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RockFishEntityModel extends AnimatedGeoModel<RockFishEntity> {

    @Override
    public ResourceLocation getModelLocation(RockFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/rock_fish.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RockFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/rock_fish.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RockFishEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/rock_fish.animation.json");
    }

    @Override
    public void setLivingAnimations(RockFishEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
