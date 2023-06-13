package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.common.util.ObjectUtil;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public interface ICAGeoModel {

	default void setBabyScaling(AnimationProcessor<?> targetProcessor, AnimationEvent<?> customPredicate, boolean scaleHead) {
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
		IBone root = targetProcessor.getBone("root");
		IBone head = targetProcessor.getBone("head");
		IBone headH = targetProcessor.getBone("Head");

		if (ObjectUtil.performNullityChecks(false, extraData, root, head)) {
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
				if (scaleHead) {
					head.setScaleX(1f);
					head.setScaleY(1f);
					head.setScaleZ(1f);
					head.setPivotY(7.75f);
				}
				head.setScaleX(2f);
				head.setScaleY(2f);
				head.setScaleZ(2f);
			} else {
				head.setScaleX(1.0f);
				head.setScaleY(1.0f);
				head.setScaleZ(1.0f);
			}
		}

		if (ObjectUtil.performNullityChecks(false, extraData, root, headH)) {
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
				headH.setScaleX(2f);
				headH.setScaleY(2f);
				headH.setScaleZ(2f);
			} else {
				headH.setScaleX(1.0f);
				headH.setScaleY(1.0f);
				headH.setScaleZ(1.0f);
			}
		}
	}

	default void setBabyScaling(AnimationProcessor<?> targetProcessor, AnimationEvent<?> customPredicate) {
		setBabyScaling(targetProcessor, customPredicate, false);
	}

	default void applyHeadRotations(AnimationProcessor<?> targetProcessor, AnimationEvent<?> customPredicate) {
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		IBone head = targetProcessor.getBone("head");
		IBone headH = targetProcessor.getBone("Head");

		if (ObjectUtil.performNullityChecks(false, extraData, head)) {
			head.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
			head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
		}

		if (ObjectUtil.performNullityChecks(false, extraData, headH)) {
			headH.setRotationX((extraData.headPitch) * ((float) Math.PI / 180F));
			headH.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
		}
	}
}
