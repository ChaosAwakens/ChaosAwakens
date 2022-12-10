package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RoboLaserProjectileRenderer extends LaserProjectileRendererer {
	public static final ResourceLocation LASER_TEXTURE = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/projectiles/laser.png");

	public RoboLaserProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getTextureLocation(Entity entity) {
		return LASER_TEXTURE;
	}
}
