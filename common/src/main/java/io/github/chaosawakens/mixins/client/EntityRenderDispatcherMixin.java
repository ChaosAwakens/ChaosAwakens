package io.github.chaosawakens.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.model.GeckolibModelInfo;
import io.github.chaosawakens.content.registry.CAResourceReloadListeners;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    private EntityRenderDispatcherMixin() {
        throw new IllegalAccessError("Attempted to construct Mixin class! (EntityRenderDispatcherMixin)");
    }

    @Inject(method = "renderHitbox", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewVector(F)Lnet/minecraft/world/phys/Vec3;"))
    private static void chaosawakens$renderSkeletonHitbox(PoseStack poseStack, VertexConsumer buffer, Entity entity, float partialTicks, CallbackInfo ci) {
        AABB aabb = entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ()).move(1, 1, 1);

        if (entity instanceof Sheep) {
            List<GeckolibModelInfo> list = CAResourceReloadListeners.GECKOLIB_MODEL_INFO.getMappedObjectData().entrySet().stream().filter(e -> e.getKey().getPath().contains("robo_jeffery")).map(Map.Entry::getValue).toList();

            list.forEach(modelInf -> {
                int idx = list.indexOf(modelInf);

                modelInf.getGeometryInfo().getBones().forEach(boneData -> {
                    Vector3d bonePivot = boneData.getPivot();
                    float scale = 1.0f / 16.0f;

                    boneData.getCubes().forEach(cube -> {
                        Vector3d origin = cube.getOrigin();
                        Vector3d size = cube.getSize();

                        // Start with identity matrix
                        Matrix4f transform = new Matrix4f().identity();
                        Quaternionf rotation = new Quaternionf();

                        // Translate to bone pivot (this is where the bone is positioned)
                        transform.translate(
                                (float)(bonePivot.x * scale),
                                (float)(bonePivot.y * scale),
                                (float)(bonePivot.z * scale)
                        );

                        // If cube has rotation, apply it around its pivot
                        if (cube.getRotation().isPresent()) {
                            Vector3d cubeRot = cube.getRotation().get();
                            Vector3d cubePivot = cube.getPivot().orElse(bonePivot);

                            // Move to cube pivot
                            transform.translate(
                                    (float)((cubePivot.x - bonePivot.x) * scale),
                                    (float)((cubePivot.y - bonePivot.y) * scale),
                                    (float)((cubePivot.z - bonePivot.z) * scale)
                            );

                            // Apply rotation
                            transform.rotateXYZ(
                                    (float)Math.toRadians(cubeRot.x),
                                    (float)Math.toRadians(cubeRot.y),
                                    (float)Math.toRadians(cubeRot.z)
                            );

                            // Move back
                            transform.translate(
                                    (float)((bonePivot.x - cubePivot.x) * scale),
                                    (float)((bonePivot.y - cubePivot.y) * scale),
                                    (float)((bonePivot.z - cubePivot.z) * scale)
                            );
                        }

                        // Define 8 corners relative to bone pivot
                        Vector4f[] corners = new Vector4f[8];

                        // Cube origin is relative to bone pivot in GeckoLib
                        float minX = (float)((origin.x - bonePivot.x) * scale);
                        float minY = (float)((origin.y - bonePivot.y) * scale);
                        float minZ = (float)((origin.z - bonePivot.z) * scale);
                        float maxX = (float)((origin.x - bonePivot.x + size.x) * scale);
                        float maxY = (float)((origin.y - bonePivot.y + size.y) * scale);
                        float maxZ = (float)((origin.z - bonePivot.z + size.z) * scale);

                        corners[0] = new Vector4f(minX, minY, minZ, 1.0f);
                        corners[1] = new Vector4f(maxX, minY, minZ, 1.0f);
                        corners[2] = new Vector4f(maxX, minY, maxZ, 1.0f);
                        corners[3] = new Vector4f(minX, minY, maxZ, 1.0f);
                        corners[4] = new Vector4f(minX, maxY, minZ, 1.0f);
                        corners[5] = new Vector4f(maxX, maxY, minZ, 1.0f);
                        corners[6] = new Vector4f(maxX, maxY, maxZ, 1.0f);
                        corners[7] = new Vector4f(minX, maxY, maxZ, 1.0f);

                        // Transform all corners
                        for (int i = 0; i < 8; i++) {
                            corners[i] = transform.transform(corners[i]);
                        }

                        // Find AABB bounds from transformed corners
                        float finalMinX = Float.MAX_VALUE, finalMinY = Float.MAX_VALUE, finalMinZ = Float.MAX_VALUE;
                        float finalMaxX = -Float.MAX_VALUE, finalMaxY = -Float.MAX_VALUE, finalMaxZ = -Float.MAX_VALUE;

                        for (Vector4f corner : corners) {
                            finalMinX = Math.min(finalMinX, corner.x);
                            finalMinY = Math.min(finalMinY, corner.y);
                            finalMinZ = Math.min(finalMinZ, corner.z);
                            finalMaxX = Math.max(finalMaxX, corner.x);
                            finalMaxY = Math.max(finalMaxY, corner.y);
                            finalMaxZ = Math.max(finalMaxZ, corner.z);
                        }

                        AABB wrapped = new AABB(finalMinX, finalMinY, finalMinZ, finalMaxX, finalMaxY, finalMaxZ).move(idx + 2, idx + 2, idx + 2);
                        LevelRenderer.renderLineBox(poseStack, buffer, wrapped, 0.0F, 1.0F, 0.0F, 1.0F);
                    });
                });
            });
        }
    }
}