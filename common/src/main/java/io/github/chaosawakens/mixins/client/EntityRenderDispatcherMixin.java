package io.github.chaosawakens.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.util.RenderUtil;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    private EntityRenderDispatcherMixin() {
        throw new IllegalAccessError("Attempted to construct Mixin class! (EntityRenderDispatcherMixin)");
    }

    @WrapOperation(method = "renderHitbox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLineBox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDDDDFFFF)V"))
    private static void chaosawakens$overrideStandardAABB(PoseStack poseStack, VertexConsumer consumer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha, Operation<Void> original, @Local(argsOnly = true) Entity owner) {
        if (!(owner instanceof MappableHitboxOwner))
            original.call(poseStack, consumer, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
    }

    @Inject(method = "renderHitbox", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewVector(F)Lnet/minecraft/world/phys/Vec3;"))
    private static void chaosawakens$renderSkeletonHitbox(PoseStack poseStack, VertexConsumer buffer, Entity entity, float partialTicks, CallbackInfo ci) {
        if (entity instanceof MappableHitboxOwner mappableHitboxOwner) {
            RenderUtil.renderSkeletonHitbox(mappableHitboxOwner.getSkeleton(), poseStack, buffer);
        }
    }
}