package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.keyframe.*;
import software.bernie.geckolib3.core.util.Axis;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;
import software.bernie.shadowed.eliotlash.molang.MolangParser;

import java.util.*;

@Mixin(value = AnimationController.class, remap = false)
public abstract class AnimationControllerMixin<T extends IAnimatable> {
    @Shadow
    protected T animatable;
    @Shadow
    protected Queue<Animation> animationQueue;
    @Shadow
    @Final
    private HashMap<String, BoneAnimationQueue> boneAnimationQueues;
    @Shadow
    @Final
    private Set<EventKeyFrame<?>> executedKeyFrames = new HashSet<>();
    @Shadow
    protected Animation currentAnimation;
    @Shadow
    protected AnimationState animationState;
    @Shadow
    protected boolean shouldResetTick = false;
    @Shadow
    public double transitionLengthTicks;
    @Shadow
    private AnimationController.ISoundListener<T> soundListener;
    @Shadow
    private AnimationController.IParticleListener<T> particleListener;
    @Shadow
    private AnimationController.ICustomInstructionListener<T> customInstructionListener;

    @Shadow
    protected abstract void resetEventKeyFrames();

    @Shadow
    protected abstract double adjustTick(double tick);

    @Shadow
    protected abstract void setAnimTime(MolangParser parser, double tick);

    @Shadow
    protected abstract AnimationPoint getAnimationPointAtTick(List<KeyFrame<IValue>> frames, double tick, boolean isRotation, Axis axis);

    @Redirect(method = "process", at = @At(value = "INVOKE", target = "Lsoftware/bernie/geckolib3/core/controller/AnimationController;processCurrentAnimation(DDLsoftware/bernie/shadowed/eliotlash/molang/MolangParser;Z)V"))
    private void chaosawakens$processCurrentAnimation(AnimationController instance, double tick, double rotationKeyFrames, MolangParser parser, boolean crashWhenCantFindBone) {
        processCurrentAnimation(tick, rotationKeyFrames, parser, crashWhenCantFindBone);
    }

    @Unique
    private void processCurrentAnimation(double tick, double actualTick, MolangParser parser, boolean crashWhenCantFindBone) {
        if (currentAnimation != null) {
            if (tick >= this.currentAnimation.animationLength) {
                if (this.currentAnimation.loop.isRepeatingAfterEnd()) {
                    this.shouldResetTick = true;

                    tick = adjustTick(actualTick);
                    resetEventKeyFrames();
                } else {
                    if (!this.currentAnimation.loop.equals(ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME)) {
                        Animation nextAnimation = this.animationQueue.peek();
                        if ((nextAnimation == null || nextAnimation.animationName.equalsIgnoreCase("None"))) {
                            this.animationState = AnimationState.Stopped;

                            return;
                        } else {
                            this.animationState = AnimationState.Transitioning;
                            this.shouldResetTick = true;
                            tick = adjustTick(actualTick);
                            this.currentAnimation = this.animationQueue.poll();
                            resetEventKeyFrames();
                        }
                    }
                }
            }

            final double finalAdjustedTick = tick;

            setAnimTime(parser, finalAdjustedTick);

            List<BoneAnimation> boneAnimations = currentAnimation.boneAnimations;
            for (BoneAnimation boneAnimation : boneAnimations) {
                BoneAnimationQueue boneAnimationQueue = boneAnimationQueues.get(boneAnimation.boneName);
                if (boneAnimationQueue == null) {
                    if (crashWhenCantFindBone) {
                        throw new RuntimeException("Could not find bone: " + boneAnimation.boneName);
                    } else {
                        continue;
                    }
                }

                VectorKeyFrameList<KeyFrame<IValue>> rotationKeyFrames = boneAnimation.rotationKeyFrames;
                VectorKeyFrameList<KeyFrame<IValue>> positionKeyFrames = boneAnimation.positionKeyFrames;
                VectorKeyFrameList<KeyFrame<IValue>> scaleKeyFrames = boneAnimation.scaleKeyFrames;

                if (!rotationKeyFrames.xKeyFrames.isEmpty()) {
                    boneAnimationQueue.rotationXQueue
                            .add(getAnimationPointAtTick(rotationKeyFrames.xKeyFrames, tick, true, Axis.X));
                    boneAnimationQueue.rotationYQueue
                            .add(getAnimationPointAtTick(rotationKeyFrames.yKeyFrames, tick, true, Axis.Y));
                    boneAnimationQueue.rotationZQueue
                            .add(getAnimationPointAtTick(rotationKeyFrames.zKeyFrames, tick, true, Axis.Z));
                }

                if (!positionKeyFrames.xKeyFrames.isEmpty()) {
                    boneAnimationQueue.positionXQueue
                            .add(getAnimationPointAtTick(positionKeyFrames.xKeyFrames, tick, false, Axis.X));
                    boneAnimationQueue.positionYQueue
                            .add(getAnimationPointAtTick(positionKeyFrames.yKeyFrames, tick, false, Axis.Y));
                    boneAnimationQueue.positionZQueue
                            .add(getAnimationPointAtTick(positionKeyFrames.zKeyFrames, tick, false, Axis.Z));
                }

                if (!scaleKeyFrames.xKeyFrames.isEmpty()) {
                    boneAnimationQueue.scaleXQueue
                            .add(getAnimationPointAtTick(scaleKeyFrames.xKeyFrames, tick, false, Axis.X));
                    boneAnimationQueue.scaleYQueue
                            .add(getAnimationPointAtTick(scaleKeyFrames.yKeyFrames, tick, false, Axis.Y));
                    boneAnimationQueue.scaleZQueue
                            .add(getAnimationPointAtTick(scaleKeyFrames.zKeyFrames, tick, false, Axis.Z));
                }
            }

            AnimationController<T> controller = (AnimationController<T>) (Object) this;

            if (soundListener != null || particleListener != null || customInstructionListener != null) {
                for (EventKeyFrame<String> soundKeyFrame : currentAnimation.soundKeyFrames) {
                    if (!this.executedKeyFrames.contains(soundKeyFrame) && tick >= soundKeyFrame.getStartTick()) {
                        SoundKeyframeEvent<T> event = new SoundKeyframeEvent<>(animatable, tick,
                                soundKeyFrame.getEventData(), controller);
                        soundListener.playSound(event);

                        this.executedKeyFrames.add(soundKeyFrame);
                    }
                }

                for (ParticleEventKeyFrame particleEventKeyFrame : currentAnimation.particleKeyFrames) {
                    if (!this.executedKeyFrames.contains(particleEventKeyFrame)
                            && tick >= particleEventKeyFrame.getStartTick()) {
                        ParticleKeyFrameEvent<T> event = new ParticleKeyFrameEvent<>(animatable, tick,
                                particleEventKeyFrame.effect, particleEventKeyFrame.locator, particleEventKeyFrame.script,
                                controller);
                        particleListener.summonParticle(event);

                        this.executedKeyFrames.add(particleEventKeyFrame);
                    }
                }

                for (EventKeyFrame<String> customInstructionKeyFrame : currentAnimation.customInstructionKeyframes) {
                    if (!this.executedKeyFrames.contains(customInstructionKeyFrame)
                            && tick >= customInstructionKeyFrame.getStartTick()) {
                        CustomInstructionKeyframeEvent<T> event = new CustomInstructionKeyframeEvent<>(animatable,
                                tick, customInstructionKeyFrame.getEventData(), controller);
                        customInstructionListener.executeInstruction(event);

                        this.executedKeyFrames.add(customInstructionKeyFrame);
                    }
                }
            }

            if (this.transitionLengthTicks == 0 && shouldResetTick && this.animationState == AnimationState.Transitioning) {
                this.currentAnimation = animationQueue.poll();
            }
        }
    }
}
