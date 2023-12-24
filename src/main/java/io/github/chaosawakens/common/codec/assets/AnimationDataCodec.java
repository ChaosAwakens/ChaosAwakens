package io.github.chaosawakens.common.codec.assets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.shadowed.eliotlash.mclib.math.IValue;

import java.util.List;

//TODO Bunch of unused stuff, left here for future reference (Heat Death of the Universe:tm:)
public class AnimationDataCodec {
    public static final Codec<AnimationDataCodec> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    ResourceLocation.CODEC.fieldOf("anim_file_loc").forGetter(data -> data.animFileLoc),
                    Codec.STRING.fieldOf("animation_name").forGetter(data -> data.animationName),
                    Codec.DOUBLE.fieldOf("animation_length").forGetter(data -> data.animationLength),
                    Codec.STRING.fieldOf("loop_type").forGetter(data -> ((ILoopType.EDefaultLoopTypes) data.loopType).toString())
            ).apply(builder, AnimationDataCodec::new));

    private final ResourceLocation animFileLoc;
    private final String animationName;
    private final Double animationLength;
    private final ILoopType loopType;

    public AnimationDataCodec(ResourceLocation animFileLoc, String animationName, Double animationLength, ILoopType loopType) {
        this.animFileLoc = animFileLoc;
        this.animationName = animationName;
        this.animationLength = animationLength;
        this.loopType = loopType;
    }

    public AnimationDataCodec(ResourceLocation animFileLoc, String animationName, Double animationLength, String loopTypeByName) {
        this(animFileLoc, animationName, animationLength, ILoopType.EDefaultLoopTypes.valueOf(loopTypeByName));
    }

    public ResourceLocation getAnimFileLoc() {
        return animFileLoc;
    }

    public String getAnimationName() {
        return animationName;
    }

    public Double getAnimationLength() {
        return animationLength;
    }

    public ILoopType getLoopType() {
        return loopType;
    }

    public static class BoneAnimationMetadataCodec {
        public static final Codec<BoneAnimationMetadataCodec> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                Codec.STRING.fieldOf("bone_name").forGetter(data -> data.boneName),
                VectorKeyFrameListDataCodec.CODEC.fieldOf("rotation_key_frames").forGetter(data -> data.rotationKeyFrames),
                VectorKeyFrameListDataCodec.CODEC.fieldOf("position_key_frames").forGetter(data -> data.positionKeyFrames),
                VectorKeyFrameListDataCodec.CODEC.fieldOf("scale_key_frames").forGetter(data -> data.scaleKeyFrames)
        ).apply(builder, BoneAnimationMetadataCodec::new));

        private final String boneName;
        private final VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>> rotationKeyFrames;
        private final VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>> positionKeyFrames;
        private final VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>> scaleKeyFrames;

        public BoneAnimationMetadataCodec(String boneName, VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>> rotationKeyFrames, VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>> positionKeyFrames, VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>> scaleKeyFrames) {
            this.boneName = boneName;
            this.rotationKeyFrames = rotationKeyFrames;
            this.positionKeyFrames = positionKeyFrames;
            this.scaleKeyFrames = scaleKeyFrames;
        }

        public String getBoneName() {
            return boneName;
        }

        public VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>> getRotationKeyFrames() {
            return rotationKeyFrames;
        }

        public VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>> getPositionKeyFrames() {
            return positionKeyFrames;
        }

        public VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>> getScaleKeyFrames() {
            return scaleKeyFrames;
        }
    }

    public static class VectorKeyFrameListDataCodec<T extends KeyframeDataCodec<? extends IValue>> {
        public static final Codec<VectorKeyFrameListDataCodec<KeyframeDataCodec<? extends IValue>>> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                KeyframeDataCodec.CODEC.listOf().fieldOf("x_key_frames").forGetter(data -> data.xKeyFrames),
                KeyframeDataCodec.CODEC.listOf().fieldOf("y_key_frames").forGetter(data -> data.yKeyFrames),
                KeyframeDataCodec.CODEC.listOf().fieldOf("z_key_frames").forGetter(data -> data.zKeyFrames)
        ).apply(builder, VectorKeyFrameListDataCodec::new));

        private final List<T> xKeyFrames;
        private final List<T> yKeyFrames;
        private final List<T> zKeyFrames;

        public VectorKeyFrameListDataCodec(List<T> xKeyFrames, List<T> yKeyFrames, List<T> zKeyFrames) {
            this.xKeyFrames = xKeyFrames;
            this.yKeyFrames = yKeyFrames;
            this.zKeyFrames = zKeyFrames;
        }

        public List<T> getXKeyFrames() {
            return xKeyFrames;
        }

        public List<T> getYKeyFrames() {
            return yKeyFrames;
        }

        public List<T> getZKeyFrames() {
            return zKeyFrames;
        }
    }

    public static class KeyframeDataCodec<T extends IValue> {
        public static final Codec<KeyframeDataCodec<? extends IValue>> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                Codec.DOUBLE.fieldOf("length").forGetter(data -> data.length),
                Codec.DOUBLE.fieldOf("start_value").forGetter(data -> data.startValue.get()),
                Codec.DOUBLE.fieldOf("end_value").forGetter(data -> data.endValue.get()),
                Codec.STRING.fieldOf("easing_type").forGetter(data -> data.easingType.name()),
                Codec.DOUBLE.listOf().fieldOf("easing_args").forGetter(data -> data.easingArgs)
        ).apply(builder, KeyframeDataCodec::new));

        private final Double length;
        private final T startValue;
        private final T endValue;
        private final EasingType easingType;
        private final List<Double> easingArgs;

        public KeyframeDataCodec(Double length, T startValue, T endValue, EasingType easingType, List<Double> easingArgs) {
            this.length = length;
            this.startValue = startValue;
            this.endValue = endValue;
            this.easingType = easingType;
            this.easingArgs = easingArgs;
        }

        public KeyframeDataCodec(Double length, Double startValue, Double endValue, String easingType, List<Double> easingArgs) {
            this(length, (T) new IValue() {
                @Override
                public double get() {
                    return startValue;
                }
            }, (T) new IValue() {
                @Override
                public double get() {
                    return endValue;
                }
            }, EasingType.valueOf(easingType), easingArgs);
        }

        public Double getLength() {
            return length;
        }

        public T getStartValue() {
            return startValue;
        }

        public T getEndValue() {
            return endValue;
        }

        public EasingType getEasingType() {
            return easingType;
        }

        public List<Double> getEasingArgs() {
            return easingArgs;
        }
    }
}
