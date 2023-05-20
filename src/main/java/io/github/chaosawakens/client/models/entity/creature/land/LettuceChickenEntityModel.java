package io.github.chaosawakens.client.models.entity.creature.land;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.LettuceChickenEntity;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class LettuceChickenEntityModel extends ExtendedAnimatedTickingGeoModel<LettuceChickenEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(LettuceChickenEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/lettuce_chicken.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(LettuceChickenEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/land/lettuce_chicken.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(LettuceChickenEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/land/lettuce_chicken.png");
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
	public void setBabyScaling(AnimationProcessor<?> targetProcessor, AnimationEvent<?> customPredicate) {
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
		IBone root = targetProcessor.getBone("body");
		IBone head = targetProcessor.getBone("head");
		
		if (ObjectUtil.performNullityChecks(true, extraData, root, head)) {
			if (extraData.isChild) {
				root.setScaleX(0.5f);
				root.setScaleY(0.5f);
				root.setScaleZ(0.5f);
				root.setPivotY(-0.5f);
			} else {
				root.setScaleX(1.0f);
				root.setScaleY(1.0f);
				root.setScaleZ(1.0f);
			}
			if (extraData.isChild) {
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
}
