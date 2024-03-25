package io.github.chaosawakens.client.models.entity.projectile;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.projectile.arrow.UltimateCrossbowBoltEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import javax.annotation.Nullable;
import java.util.Optional;

public class UltimateCrossbowBoltEntityModel extends AnimatedTickingGeoModel<UltimateCrossbowBoltEntity> {

    @Override
    public ResourceLocation getAnimationFileLocation(UltimateCrossbowBoltEntity animatable) {
        return ChaosAwakens.prefix("animations/entity/projectiles/ultimate_bolt.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(UltimateCrossbowBoltEntity object) {
        return ChaosAwakens.prefix("geo/entity/projectiles/ultimate_bolt.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(UltimateCrossbowBoltEntity object) {
        return ChaosAwakens.prefix("textures/entity/projectiles/ultimate_bolt.png");
    }
}
