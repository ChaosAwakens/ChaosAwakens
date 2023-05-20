package io.github.chaosawakens.client.models.item.base;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public abstract class DummyAnimatedGeoModel<I extends IAnimatable> extends AnimatedGeoModel<I> {

	@Override
	public final ResourceLocation getAnimationFileLocation(I animatable) {
		return ChaosAwakens.prefix("animations/misc/dummy.animation.json");
	}

}
