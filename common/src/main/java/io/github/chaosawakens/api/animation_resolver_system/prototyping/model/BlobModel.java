package io.github.chaosawakens.api.animation_resolver_system.prototyping.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.chaosawakens.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlobModel<E extends Entity> extends EntityModel<E> {
    protected AABB blobAABB;

    public BlobModel() {
    }

    @Override
    public void setupAnim(E entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // NO-OP
    }

    @Override
    public void prepareMobModel(E entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.blobAABB = entity != null ? entity.getBoundingBox() : null;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (blobAABB != null) {
            poseStack.pushPose();

            Vec3 curCameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

            double minX = blobAABB.minX - curCameraPos.x;
            double minY = blobAABB.minY - curCameraPos.y;
            double minZ = blobAABB.minZ - curCameraPos.z;
            double maxX = blobAABB.maxX - curCameraPos.x;
            double maxY = blobAABB.maxY - curCameraPos.y;
            double maxZ = blobAABB.maxZ - curCameraPos.z;

            double xSize = blobAABB.getXsize();
            double ySize = blobAABB.getYsize();
            double zSize = blobAABB.getZsize();

            poseStack.scale((float) xSize, (float) ySize, (float) zSize);

            // Render faces for each aabb face
            RenderUtil.renderBox(buffer, poseStack, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);

            poseStack.popPose();
        }
    }
}
