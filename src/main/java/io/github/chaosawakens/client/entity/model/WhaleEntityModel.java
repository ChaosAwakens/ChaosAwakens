package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.WhaleEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WhaleEntityModel extends AnimatedGeoModel<WhaleEntity> {

    @Override
    public ResourceLocation getModelLocation(WhaleEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/whale.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WhaleEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/whale.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WhaleEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/whale.animation.json");
    }

    @Override
    public void setLivingAnimations(WhaleEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
