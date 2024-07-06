package io.github.chaosawakens.client.models.entity.projectile;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.projectile.RoboRayEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class RoboRayModel extends AnimatedTickingGeoModel<RoboRayEntity> {

    @Override
    public ResourceLocation getModelLocation(RoboRayEntity object) {
        return ChaosAwakens.prefix("geo/entity/projectiles/robo_ray.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(RoboRayEntity object) {
        return ChaosAwakens.prefix("textures/entity/projectiles/robo_ray.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(RoboRayEntity animatable) {
        return ChaosAwakens.prefix("animations/entity/projectiles/robo_ray.animation.json");
    }
}
