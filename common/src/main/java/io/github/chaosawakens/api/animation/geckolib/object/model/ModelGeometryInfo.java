package io.github.chaosawakens.api.animation.geckolib.object.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;

public record ModelGeometryInfo(Optional<ModelMetadataInfo> modelMetadata, List<ModelBoneInfo> bones) {
    public static final Codec<ModelGeometryInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ModelMetadataInfo.CODEC.optionalFieldOf("description").forGetter(ModelGeometryInfo::modelMetadata),
            ModelBoneInfo.CODEC.listOf().fieldOf("bones").forGetter(ModelGeometryInfo::bones)
    ).apply(instance, ModelGeometryInfo::new));
    public static final Codec<List<ModelGeometryInfo>> LIST_CODEC = Codec.list(ModelGeometryInfo.CODEC); // We shouldn't need this for 1 GEOMETRY OBJECT BUT OF COURSE, LEGACY FORMAT PARSING!!!
}
