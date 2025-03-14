package io.github.chaosawakens.client.model.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

public abstract class WrappedAgeableHierarchalModel<E extends Entity> extends WrappedHierarchicalModel<E> {
    public static final AnimationDefinition BABY_TRANSFORM = AnimationDefinition.Builder.withLength(0.0F)
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.SCALE, new Keyframe(0.0F, KeyframeAnimations.scaleVec(2.0F, 2.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.85F, 0.65F), AnimationChannel.Interpolations.LINEAR)))
            .build();

    public WrappedAgeableHierarchalModel(Function<ResourceLocation, RenderType> pRenderType) {
        super(pRenderType);
    }

    public WrappedAgeableHierarchalModel() {
        super(RenderType::entityCutoutNoCull);
    }

    @Override
    public void renderToBuffer(PoseStack curPoseStack, VertexConsumer curBuffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (isBaby()) {
            curPoseStack.pushPose();
            curPoseStack.scale(getBabyScale(), getBabyScale(), getBabyScale());
            curPoseStack.translate(0.0D, 1.5D, 0.0D);

            root().render(curPoseStack, curBuffer, packedLight, packedOverlay, red, green, blue, alpha);

            curPoseStack.popPose();
        } else super.renderToBuffer(curPoseStack, curBuffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(E pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);

        // Misc
        if (isBaby() && !scaleHeadProportionately()) applyStatic(BABY_TRANSFORM);
    }

    public boolean isBaby() {
        return young;
    }

    public boolean scaleHeadProportionately() {
        return false;
    }

    public float getBabyScale() {
        return 0.5F;
    }
}
