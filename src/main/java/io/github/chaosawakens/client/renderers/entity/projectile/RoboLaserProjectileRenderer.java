package io.github.chaosawakens.client.renderers.entity.projectile;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RoboLaserProjectileRenderer extends LaserProjectileRendererer {
	private static final ResourceLocation LASER_TEXTURE = ChaosAwakens.prefix("textures/entity/projectiles/laser.png"); //TODO Texture

	public RoboLaserProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getTextureLocation(Entity entity) {
		return LASER_TEXTURE;
	}
}
