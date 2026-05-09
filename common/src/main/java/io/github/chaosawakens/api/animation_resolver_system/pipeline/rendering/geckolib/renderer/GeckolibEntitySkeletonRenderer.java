package io.github.chaosawakens.api.animation_resolver_system.pipeline.rendering.geckolib.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeUVData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.rendering.base.SkeletonRenderer;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.rendering.geckolib.model.GeckolibEntitySkeletonModel;
import io.github.chaosawakens.util.RenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class GeckolibEntitySkeletonRenderer<AE extends Entity & MappableHitboxOwner> extends EntityRenderer<AE> implements SkeletonRenderer<AE> {
    protected GeckolibEntitySkeletonModel<AE> parentModel;
    protected AE ownerAnimatable;

    public GeckolibEntitySkeletonRenderer(EntityRendererProvider.Context context, GeckolibEntitySkeletonModel<AE> parentSkeleton) {
        super(context);

        this.parentModel = parentSkeleton;
    }

    public GeckolibEntitySkeletonRenderer(EntityRendererProvider.Context context) {
        this(context, null);
    }

    @Override
    public void render(AE entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.ownerAnimatable = entity;

        if (parentModel == null) {
            this.parentModel = RenderUtil.resolveModelFor(entity);

            if (parentModel == null) {
                throw new IllegalStateException(String.format("Failed to resolve Geckolib model for entity of type: %s", entity.getClass().getName()));
            }
        }

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);

        renderSkeleton(poseStack, parentModel.getSkeleton(), buffer, partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(AE ae) {
        return null;
    }

    @Override
    public AE getMappableHitboxOwner() {
        return ownerAnimatable;
    }

    @Override
    public void renderTextureQuads(PoseStack targetStack, ModelCubeUVData uvData, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

    }

    @Override
    public void renderCube(PoseStack targetStack, Cube targetCube, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

    }

    @Override
    public void renderBone(PoseStack targetStack, Bone targetBone, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

    }

    @Override
    public void renderChildBones(PoseStack targetStack, Bone targetBone, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

    }

    @Override
    public void renderSkeleton(PoseStack targetStack, Skeleton targetSkeleton, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

    }
}
