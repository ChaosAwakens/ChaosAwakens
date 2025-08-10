package io.github.chaosawakens.api.animation.geckolib.object.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.codec.OtherCodecs;
import org.joml.Vector3d;

import java.util.List;

public record ModelBoneInfo(String boneName, Vector3d bonePivotModelCoords, List<ModelCubeInfo> boneCubes) {
    public static final Codec<ModelBoneInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(ModelBoneInfo::boneName),
            OtherCodecs.VECTOR_3D_CODEC.fieldOf("pivot").forGetter(ModelBoneInfo::bonePivotModelCoords),
            ModelCubeInfo.LIST_CODEC.fieldOf("cubes").forGetter(ModelBoneInfo::boneCubes)
    ).apply(instance, ModelBoneInfo::new));
    public static final Codec<List<ModelBoneInfo>> LIST_CODEC = Codec.list(CODEC);
}
