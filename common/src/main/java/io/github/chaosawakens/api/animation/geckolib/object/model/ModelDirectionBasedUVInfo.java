package io.github.chaosawakens.api.animation.geckolib.object.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.codec.OtherCodecs;
import org.joml.Vector2d;

public record ModelDirectionBasedUVInfo(Vector2d uvCoords, Vector2d texSize) {
    public static final Codec<ModelDirectionBasedUVInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            OtherCodecs.VECTOR_2D_CODEC.fieldOf("uv").forGetter(ModelDirectionBasedUVInfo::uvCoords),
            OtherCodecs.VECTOR_2D_CODEC.fieldOf("uv_size").forGetter(ModelDirectionBasedUVInfo::texSize)
    ).apply(instance, ModelDirectionBasedUVInfo::new));
}
