package io.github.chaosawakens.api.animation_resolver_system.faal.rendering.geckolib.model;

import com.mememan.nexus.util.RegistryUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.faal.rendering.base.SkeletonModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class GeckolibEntitySkeletonModel<AE extends Entity & MappableHitboxOwner> extends EntityModel<AE> implements SkeletonModel<AE> {

    public GeckolibEntitySkeletonModel() {

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AE mappableHitboxOwner) {
        return RegistryUtil.getTextureLocationOrDefault(mappableHitboxOwner::getType, "entity").withPrefix("textures/").withSuffix(".png");
    }

    @Override
    public void setupAnim(AE owner, float limbSwing, float limbSwingSpeed, float bobbing, float curYRot, float curXRot) {

    }

    @Override
    public void renderToBuffer(PoseStack curStack, VertexConsumer buffer, int packedLight, int overlayCoords, float r, float g, float b, float a) {

    }
}
