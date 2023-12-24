package io.github.chaosawakens.api.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;

/**
 * TODO Unused
 */
public class AnimationDataHolder {
    private final String animationName;
    private final double animationLength;
    private final ResourceLocation clientFileLoc;
    public static final AnimationDataHolder EMPTY = new AnimationDataHolder("empty", 0, null);
    public static final Codec<AnimationDataHolder> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.STRING.fieldOf("animation_name").forGetter(data -> data.animationName),
                    Codec.DOUBLE.fieldOf("animation_length").forGetter(data -> data.animationLength),
                    ResourceLocation.CODEC.fieldOf("client_file_loc").forGetter(data -> data.clientFileLoc)
            ).apply(builder, AnimationDataHolder::new));

    public AnimationDataHolder(String animationName, double animationLength, ResourceLocation clientFileLoc) {
        this.animationName = animationName;
        this.animationLength = animationLength;
        this.clientFileLoc = clientFileLoc;
    }

    public String getAnimationName() {
        return animationName;
    }

    public double getAnimationLength() {
        return animationLength;
    }

    public ResourceLocation getClientFileLoc() {
        return clientFileLoc;
    }
}
