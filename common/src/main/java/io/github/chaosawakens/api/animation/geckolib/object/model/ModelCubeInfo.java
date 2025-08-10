package io.github.chaosawakens.api.animation.geckolib.object.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.codec.OtherCodecs;
import org.joml.Vector3d;

import java.util.List;

public record ModelCubeInfo(Vector3d cubeOrigin, Vector3d cubeSize, ModelUVInfo uvInfo) {
    public static final Codec<ModelCubeInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            OtherCodecs.VECTOR_3D_CODEC.fieldOf("origin").forGetter(ModelCubeInfo::cubeOrigin),
            OtherCodecs.VECTOR_3D_CODEC.fieldOf("size").forGetter(ModelCubeInfo::cubeSize),
            ModelUVInfo.CODEC.fieldOf("uv").forGetter(ModelCubeInfo::uvInfo)
    ).apply(instance, ModelCubeInfo::new));
    public static final Codec<List<ModelCubeInfo>> LIST_CODEC = Codec.list(ModelCubeInfo.CODEC);
}
