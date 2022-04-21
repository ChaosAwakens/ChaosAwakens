package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.ExtendedHitAxeItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BattleAxeItemModel extends AnimatedGeoModel<ExtendedHitAxeItem> {
	
    @Override
    public ResourceLocation getModelLocation(ExtendedHitAxeItem object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/battle_axe.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ExtendedHitAxeItem object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/item/battle_axe_model.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ExtendedHitAxeItem animatable) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/dummy.animation.json");
    }
}
