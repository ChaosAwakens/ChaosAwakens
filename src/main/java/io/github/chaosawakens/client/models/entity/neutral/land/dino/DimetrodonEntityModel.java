package io.github.chaosawakens.client.models.entity.neutral.land.dino;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.neutral.land.dino.DimetrodonEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class DimetrodonEntityModel extends ExtendedAnimatedTickingGeoModel<DimetrodonEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(DimetrodonEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/neutral/land/dimetrodon.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(DimetrodonEntity animatable) {
		return ChaosAwakens.prefix("geo/entity/neutral/land/dimetrodon.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(DimetrodonEntity animatable) {
		switch (animatable.getDimetrodonType()) {
		default: return ChaosAwakens.prefix("textures/entity/neutral/land/dimetrodon/green_dimetrodon.png");
		case 1: return ChaosAwakens.prefix("textures/entity/neutral/land/dimetrodon/orange_dimetrodon.png");
		case 2: return ChaosAwakens.prefix("textures/entity/neutral/land/dimetrodon/purple_dimetrodon.png");
		case 3: return ChaosAwakens.prefix("textures/entity/neutral/land/dimetrodon/throwback_dimetrodon.png");
		}
	}

	@Override
	protected boolean shouldApplyHeadRot() {
		return true;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return true;
	}
	
	@Override
	protected boolean scaleHeadWithChild() {
		return true;
	}
	
	@Override
	public void setLivingAnimations(DimetrodonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		
		EntityModelData data = (EntityModelData) customPredicate.getExtraData().get(0);
		IBone root = this.getAnimationProcessor().getBone("dimetrodon"); //TODO Support bone name method overload
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
			head.setScaleX(1f);
			head.setScaleY(1f);
			head.setScaleZ(1f);
			head.setPivotY(7.75f);
		} else {
			head.setScaleX(1.0f);
			head.setScaleY(1.0f);
			head.setScaleZ(1.0f);
		}
	}
}
