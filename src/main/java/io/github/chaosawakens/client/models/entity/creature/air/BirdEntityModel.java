package io.github.chaosawakens.client.models.entity.creature.air;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.models.entity.base.ExtendedAnimatedTickingGeoModel;
import io.github.chaosawakens.common.entity.creature.air.BirdEntity;
import net.minecraft.util.ResourceLocation;

public class BirdEntityModel extends ExtendedAnimatedTickingGeoModel<BirdEntity> {

    public BirdEntityModel() {
        super();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BirdEntity animatable) {
        return ChaosAwakens.prefix("animations/entity/creature/air/bird.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(BirdEntity object) {
        return ChaosAwakens.prefix("geo/entity/creature/air/bird.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BirdEntity object) {
        switch (object.getColour()) {
            default: return ChaosAwakens.prefix("textures/entity/creature/air/bird/black.png");
            case 1: return ChaosAwakens.prefix("textures/entity/creature/air/bird/blue.png");
            case 2: return ChaosAwakens.prefix("textures/entity/creature/air/bird/brown.png");
            case 3: return ChaosAwakens.prefix("textures/entity/creature/air/bird/green.png");
            case 4: return ChaosAwakens.prefix("textures/entity/creature/air/bird/red.png");
            case 5: return ChaosAwakens.prefix("textures/entity/creature/air/bird/ruby.png");
        }
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
