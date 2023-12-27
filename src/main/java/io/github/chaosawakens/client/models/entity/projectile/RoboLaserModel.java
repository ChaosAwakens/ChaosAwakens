package io.github.chaosawakens.client.models.entity.projectile;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.projectile.RoboLaserEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class RoboLaserModel extends AnimatedTickingGeoModel<RoboLaserEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(RoboLaserEntity entity) {
		return ChaosAwakens.prefix("animations/entity/projectiles/robo_sniper_laser.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(RoboLaserEntity entity) {
		return ChaosAwakens.prefix("geo/entity/projectiles/robo_sniper_laser.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(RoboLaserEntity entity) {
		return ChaosAwakens.prefix("textures/entity/projectiles/robo_sniper_laser.png");
	}

}
