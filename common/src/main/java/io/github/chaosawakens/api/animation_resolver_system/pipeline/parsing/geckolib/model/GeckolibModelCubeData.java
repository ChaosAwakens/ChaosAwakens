package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeData;
import io.github.chaosawakens.util.CodecUtil;
import org.joml.Vector3d;

import java.util.List;
import java.util.Optional;

public record GeckolibModelCubeData(Vector3d origin, Optional<Vector3d> standalonePivot, Optional<Vector3d> standaloneRotation, Vector3d size, GeckolibModelCubeUVData uvData) implements ModelCubeData {
    public static final Codec<GeckolibModelCubeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CodecUtil.VECTOR_3D_CODEC.fieldOf("origin").forGetter(GeckolibModelCubeData::origin),
            CodecUtil.VECTOR_3D_CODEC.optionalFieldOf("pivot").forGetter(GeckolibModelCubeData::standalonePivot),
            CodecUtil.VECTOR_3D_CODEC.optionalFieldOf("rotation").forGetter(GeckolibModelCubeData::standaloneRotation),
            CodecUtil.VECTOR_3D_CODEC.fieldOf("size").forGetter(GeckolibModelCubeData::size),
            GeckolibModelCubeUVData.CODEC.fieldOf("uv").forGetter(GeckolibModelCubeData::uvData)
    ).apply(instance, GeckolibModelCubeData::new));
    public static final Codec<List<GeckolibModelCubeData>> LIST_CODEC = Codec.list(CODEC);

    @Override
    public Vector3d getOrigin() {
        return origin;
    }

    @Override
    public Vector3d getSize() {
        return size;
    }

    @Override
    public Optional<Vector3d> getPivot() {
        return standalonePivot;
    }

    @Override
    public Optional<Vector3d> getRotation() {
        return standaloneRotation;
    }

    @Override
    public GeckolibModelCubeUVData getUVData() {
        return uvData;
    }
}
