package io.github.chaosawakens.client.animation.baked.neutral.rubber_ducky;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class RubberDuckyMiscAnimation {
    public static final AnimationDefinition HIDE_ATTACK = AnimationDefinition.Builder.withLength(0.0F).looping()
            .addAnimation("attack_tendril", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition SKEDDADLE = AnimationDefinition.Builder.withLength(1.8F).looping()
            .addAnimation("Root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.9392F, 0.0F, 1.9696F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Root", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.1286F, 5.2F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Root", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0211F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Rubber_ducky", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 7.0625F, -0.6875F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Rubber_ducky", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril_eye", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.8751F, -14.9026F, -4.6347F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril_eye", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril_eye", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril_eye", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.8751F, 14.9026F, 4.6347F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril_eye", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril_eye", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0237F, 1.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.5557F, -19.5557F, -29.8333F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.8392F, -0.7586F, -1.0837F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.1259F, 1.2856F, 0.4289F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.0474F, -6.0474F, -22.0686F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.8392F, -0.7586F, -1.0837F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.1259F, 1.2856F, 0.4289F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.4243F, 32.4243F, 26.5154F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril2", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.8392F, -0.7586F, -1.0837F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.1259F, 1.2856F, 0.4289F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.5557F, 39.5557F, 29.8333F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.8392F, -0.7586F, -1.0837F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.1259F, 1.2856F, 0.4289F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.4097F, -6.4772F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.6477F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 0.9131F, 1.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition CORRECT_ROOT = AnimationDefinition.Builder.withLength(1.0F).looping()
            .addAnimation("Left_tendril_eye", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Left_tendril2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril2", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Right_tendril_eye", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
}
