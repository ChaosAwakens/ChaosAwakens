package io.github.chaosawakens.client.models.entity.creature.land;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.GazelleEntity;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class GazelleEntityModel extends ExtendedAnimatedTickingGeoModel<GazelleEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(GazelleEntity animatable) {
		return ChaosAwakens.prefix("animations/entity/creature/land/gazelle.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(GazelleEntity animatable) {
		return ChaosAwakens.prefix("geo/entity/creature/land/gazelle.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(GazelleEntity animatable) {
		switch (animatable.getGazelleType()) {
		default: return ChaosAwakens.prefix("textures/entity/creature/land/gazelle/gazelle.png");
		case 2: return ChaosAwakens.prefix("textures/entity/creature/land/gazelle/tan_gazelle.png");
		case 3: return ChaosAwakens.prefix("textures/entity/creature/land/gazelle/dark_red_gazelle.png");
		case 4: return ChaosAwakens.prefix("textures/entity/creature/land/gazelle/black_gazelle.png");
		case 5: return ChaosAwakens.prefix("textures/entity/creature/land/gazelle/beij_gazelle.png");
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
	protected boolean shouldScaleHeadWithChild() {
		return true;
	}
	
	@Override
	public IBone getBodyBone() {
		return getAnimationProcessor().getBone("Gazelle");
	}
	
	@Override
	public IBone getHeadBone() {
		return getAnimationProcessor().getBone("Neck");
	}

	@Override
	public void setLivingAnimations(GazelleEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);

		if (!entity.isPlayingAnimation("Graze")) {
			getHeadBone().setRotationX(getHeadBone().getRotationX() + 0.85F);
		}
	}

	@Override
	public void setBabyScaling(AnimationProcessor<?> targetProcessor, AnimationEvent<?> customPredicate, boolean scaleHead) {
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
		IBone root = getBodyBone();
		IBone head = getHeadBone();

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
					head.setPivotY(10.75f);
				} else {
					head.setScaleX(2f);
					head.setScaleY(2f);
					head.setScaleZ(2f);
					head.setPivotY(12.70385F);
				}
			} else {
				head.setScaleX(1.0f);
				head.setScaleY(1.0f);
				head.setScaleZ(1.0f);
				head.setPivotY(12.70385F);
			}
		}
	}
}
