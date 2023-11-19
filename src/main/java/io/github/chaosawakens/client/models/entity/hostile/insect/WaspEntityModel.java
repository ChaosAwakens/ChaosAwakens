package io.github.chaosawakens.client.models.entity.hostile.insect;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.hostile.insect.WaspEntity;
import net.minecraft.util.ResourceLocation;

public class WaspEntityModel extends ExtendedAnimatedTickingGeoModel<WaspEntity> {


    @Override
    public ResourceLocation getAnimationFileLocation(WaspEntity object) {
        return ChaosAwakens.prefix("animations/entity/hostile/insect/wasp.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(WaspEntity object) {
        return ChaosAwakens.prefix("geo/entity/hostile/insect/wasp.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WaspEntity object) {
        return ChaosAwakens.prefix("textures/entity/hostile/insect/wasp.png");
    }

    @Override
    protected boolean shouldApplyHeadRot() {
        return true;
    }

    @Override
    protected boolean shouldApplyChildScaling() {
        return false;
    }
}
