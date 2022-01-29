package io.github.chaosawakens.client.entity.model;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.AttitudeAdjusterItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AttitudeAdjusterItemModel extends AnimatedGeoModel<AttitudeAdjusterItem> {
    @Override
    public ResourceLocation getModelLocation(AttitudeAdjusterItem object) {
        return new ResourceLocation(ChaosAwakens.MODID, "geo/attitude_adjuster.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AttitudeAdjusterItem object) {
        return new ResourceLocation(ChaosAwakens.MODID, "textures/item/attitude_adjuster_model.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AttitudeAdjusterItem animatable) {
        return new ResourceLocation(ChaosAwakens.MODID, "animations/dummy.animation.json");
    }
}
