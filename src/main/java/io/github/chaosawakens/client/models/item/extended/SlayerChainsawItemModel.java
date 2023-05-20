package io.github.chaosawakens.client.models.item.extended;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.weapons.extended.SlayerChainsawItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SlayerChainsawItemModel extends AnimatedGeoModel<SlayerChainsawItem> {
	
	@Override
	public ResourceLocation getAnimationFileLocation(SlayerChainsawItem animatable) {
		return ChaosAwakens.prefix("animations/item/extended/slayer_chainsaw.animation.json");
	}
	
	@Override
	public ResourceLocation getModelLocation(SlayerChainsawItem object) {
		return ChaosAwakens.prefix("geo/item/extended/slayer_chainsaw.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SlayerChainsawItem object) {
		return ChaosAwakens.prefix("textures/item/slayer_chainsaw_model.png");
	}
}
