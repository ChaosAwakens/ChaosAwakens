package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeDirectionalUVData;
import io.github.chaosawakens.util.CodecUtil;
import org.joml.Vector2d;

public record GeckolibModelCubeDirectionalUVData(Vector2d uvCoords,
                                                 Vector2d uvSize) implements ModelCubeDirectionalUVData {
    public static final Codec<GeckolibModelCubeDirectionalUVData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CodecUtil.VECTOR_2D_CODEC.fieldOf("uv").forGetter(GeckolibModelCubeDirectionalUVData::uvCoords),
            CodecUtil.VECTOR_2D_CODEC.fieldOf("uv_size").forGetter(GeckolibModelCubeDirectionalUVData::uvSize)
    ).apply(instance, GeckolibModelCubeDirectionalUVData::new));

    @Override
    public Vector2d getUVCoords() {
        return uvCoords;
    }

    @Override
    public Vector2d getUVSize() {
        return uvSize;
    }
}
