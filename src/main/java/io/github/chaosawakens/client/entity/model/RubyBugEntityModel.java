package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.entity.RubyBugEntity;
import io.github.chaosawakens.entity.TermiteEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class RubyBugEntityModel extends AnimatedGeoModel<RubyBugEntity> {
	
	@Override
	public ResourceLocation getModelLocation(RubyBugEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "geo/ruby_bug.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(RubyBugEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "textures/entity/ruby_bug.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(RubyBugEntity object) {
		return new ResourceLocation(ChaosAwakens.MODID, "animations/ruby_bug.animation.json");
	}
}
