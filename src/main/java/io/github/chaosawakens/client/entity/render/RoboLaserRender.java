package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.projectile.RoboLaserEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RoboLaserRender extends LaserRenderer<RoboLaserEntity> {
    public static final ResourceLocation LASER_TEXTURE = new ResourceLocation(ChaosAwakens.MODID, "textures/entity/projectiles/laser.png");

    public RoboLaserRender(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return LASER_TEXTURE;
    }
}
