package io.github.chaosawakens.client.entity.render;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.projectile.UltimateArrowEntity;
import io.github.chaosawakens.common.entity.projectile.UltimateFishingBobberEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.FishRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UltimateBobberRender extends FishRenderer {
    private static final ResourceLocation BOBBER = new ResourceLocation(ChaosAwakens.MODID,"textures/entity/ultimate_fishing_hook.png");

    public UltimateBobberRender(EntityRendererManager manager) {
        super(manager);
    }

    public ResourceLocation getEntityTexture(UltimateFishingBobberEntity entity) {
        return BOBBER;
    }
}