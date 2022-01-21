package io.github.chaosawakens.client.entity.model;

import java.util.Locale;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.BirdEntity;
import io.github.chaosawakens.common.entity.BirdEntity.BirdTypes;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BirdEntityModel extends AnimatedGeoModel<BirdEntity>{

    @Override
    public ResourceLocation getModelLocation(BirdEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/ruby_bug.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BirdEntity object) {
    	BirdTypes type = BirdTypes.byId(object.getColorTextureType());
        return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/" + type.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BirdEntity object) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/bird.animation.json");
    }
    
    @Override
    public void setLivingAnimations(BirdEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
    	super.setLivingAnimations(entity, uniqueID);
    	
    	EntityModelData data = (EntityModelData) customPredicate.getExtraData().get(0);
        IBone root = this.getAnimationProcessor().getBone("root");
        IBone head = this.getAnimationProcessor().getBone("head");
        
        
        
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
        head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
    }

}
