package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.WhaleEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

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

  //  @Override
  //  public void setLivingAnimations(WhaleEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
  //      super.setLivingAnimations(entity, uniqueID, customPredicate);
//
   //     IBone head = this.getAnimationProcessor().getBone("Head");
   //     EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
   //     head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
   //     head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
   // }
}
