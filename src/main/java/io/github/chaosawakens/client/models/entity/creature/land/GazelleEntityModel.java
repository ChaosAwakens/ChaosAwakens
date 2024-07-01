package io.github.chaosawakens.client.models.entity.creature.land;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.land.GazelleEntity;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

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
		return getAnimationProcessor().getBone("Head");
	}

	@Override
	public <E extends IAnimatableEntity> void applyHeadRotations(E animatable, AnimationProcessor<?> targetProcessor, AnimationEvent<?> customPredicate) {
		EntityModelData extraData = customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		IBone neck = getBone("Neck");

		if (ObjectUtil.performNullityChecks(false, extraData, neck) && !animatable.isPlayingAnimation("Graze")) {
			neck.setRotationX(((extraData.headPitch) * ((float) Math.PI / 180F)) + 0.85F);
			neck.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 270F));
		}
	}

	@Override
	public <E extends IAnimatableEntity> void setBabyScaling(E animatable, AnimationProcessor<?> targetProcessor, AnimationEvent<?> customPredicate, boolean scaleHead) {
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);
		IBone root = getBodyBone();
		IBone neck = getBone("Neck");

		if (ObjectUtil.performNullityChecks(false, extraData, root, neck)) {
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
					neck.setScaleX(1f);
					neck.setScaleY(1f);
					neck.setScaleZ(1f);
					neck.setPivotY(10.75f);
				} else {
					neck.setScaleX(2f);
					neck.setScaleY(2f);
					neck.setScaleZ(2f);
					neck.setPivotY(12.70385F);
				}
			} else {
				neck.setScaleX(1.0f);
				neck.setScaleY(1.0f);
				neck.setScaleZ(1.0f);
				neck.setPivotY(12.70385F);
			}
		}
	}
}
