package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.CrystalCarrotPigEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CrystalCarrotPigEntityModel extends AnimatedGeoModel<CrystalCarrotPigEntity> {
	@Override
	public ResourceLocation getModelLocation(CrystalCarrotPigEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/carrot_pig.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CrystalCarrotPigEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/crystal/crystal_carrot_pig.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(CrystalCarrotPigEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/carrot_pig.animation.json");
	}

	@Override
	public void setLivingAnimations(CrystalCarrotPigEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);

		EntityModelData data = (EntityModelData) customPredicate.getExtraData().get(0);
		IBone root = this.getAnimationProcessor().getBone("root");
		IBone head = this.getAnimationProcessor().getBone("head");
		if (data.isChild) {
			root.setScaleX(0.5f);
			root.setScaleY(0.5f);
			root.setScaleZ(0.5f);
			root.setPivotY(-0.5f);
		} else {
			root.setScaleX(1.0f);
			root.setScaleY(1.0f);
			root.setScaleZ(1.0f);
		}
		if (data.isChild) {
			head.setScaleX(2f);
			head.setScaleY(2f);
			head.setScaleZ(2f);
			head.setPivotY(7.75f);
		} else {
			head.setScaleX(1.0f);
			head.setScaleY(1.0f);
			head.setScaleZ(1.0f);
		}
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
	}

}
