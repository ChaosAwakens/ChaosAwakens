package io.github.chaosawakens.api.animation.geckolib.object.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.codec.OtherCodecs;
import org.joml.Vector3d;

public record ModelMetadataInfo(String geomId, double texWidth, double texHeight, double texVisibleBoundsWidth, double texVisibleBoundsHeight, Vector3d texVisibleBoundsOffset) {
    public static final Codec<ModelMetadataInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("identifier").forGetter(ModelMetadataInfo::geomId),
            Codec.DOUBLE.fieldOf("texture_width").forGetter(ModelMetadataInfo::texWidth),
            Codec.DOUBLE.fieldOf("texture_height").forGetter(ModelMetadataInfo::texHeight),
            Codec.DOUBLE.fieldOf("visible_bounds_width").forGetter(ModelMetadataInfo::texVisibleBoundsWidth),
            Codec.DOUBLE.fieldOf("visible_bounds_height").forGetter(ModelMetadataInfo::texVisibleBoundsHeight),
            OtherCodecs.VECTOR_3D_CODEC.fieldOf("visible_bounds_offset").forGetter(ModelMetadataInfo::texVisibleBoundsOffset)
    ).apply(instance, ModelMetadataInfo::new));
}
