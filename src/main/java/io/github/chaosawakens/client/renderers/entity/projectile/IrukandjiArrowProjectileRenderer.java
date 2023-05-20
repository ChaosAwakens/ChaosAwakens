package io.github.chaosawakens.client.renderers.entity.projectile;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.projectile.arrow.IrukandjiArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class IrukandjiArrowProjectileRenderer extends ArrowRenderer<IrukandjiArrowEntity> {
	private static final ResourceLocation IRUKANDJI_ARROW_TEXTURE = ChaosAwakens.prefix("textures/entity/projectiles/irukandji_arrow.png");

	public IrukandjiArrowProjectileRenderer(EntityRendererManager manager) {
		super(manager);
	}

	public ResourceLocation getTextureLocation(IrukandjiArrowEntity entity) {
		return IRUKANDJI_ARROW_TEXTURE;
	}
}

