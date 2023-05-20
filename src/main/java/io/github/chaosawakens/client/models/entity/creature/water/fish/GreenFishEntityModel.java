package io.github.chaosawakens.client.models.entity.creature.water.fish;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.water.fish.GreenFishEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class GreenFishEntityModel extends ExtendedAnimatedTickingGeoModel<GreenFishEntity> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(GreenFishEntity object) {
		return ChaosAwakens.prefix("animations/entity/creature/water/fish/green_fish.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(GreenFishEntity object) {
		return ChaosAwakens.prefix("geo/entity/creature/water/fish/green_fish.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(GreenFishEntity object) {
		return ChaosAwakens.prefix("textures/entity/creature/water/fish/green_fish.png");
	}

	@Override
	protected boolean shouldApplyHeadRot() {
		return false;
	}

	@Override
	protected boolean shouldApplyChildScaling() {
		return false;
	}
	
	@Override
	public void setLivingAnimations(GreenFishEntity entity, Integer uniqueID, @SuppressWarnings("rawtypes") AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		
		IBone root = this.getAnimationProcessor().getBone("root");	
		if (!entity.isInWaterOrBubble()) root.setRotationZ(-90);
	}
}
