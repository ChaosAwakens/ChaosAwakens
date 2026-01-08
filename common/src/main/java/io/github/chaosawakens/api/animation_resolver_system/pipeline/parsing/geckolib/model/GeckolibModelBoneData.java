package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelBoneData;
import io.github.chaosawakens.util.CodecUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.joml.Vector3d;

import java.util.List;
import java.util.Optional;

public record GeckolibModelBoneData(String boneName, Optional<String> parentBoneName, Vector3d pivot, List<GeckolibModelCubeData> cubes) implements ModelBoneData {
    public static final Codec<GeckolibModelBoneData> BARE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(GeckolibModelBoneData::boneName),
            Codec.STRING.optionalFieldOf("parent").forGetter(GeckolibModelBoneData::parentBoneName),
            CodecUtil.VECTOR_3D_CODEC.fieldOf("pivot").forGetter(GeckolibModelBoneData::pivot),
            GeckolibModelCubeData.LIST_CODEC.optionalFieldOf("cubes", ObjectArrayList.of()).forGetter(GeckolibModelBoneData::cubes)
    ).apply(instance, GeckolibModelBoneData::new));
    public static final Codec<List<GeckolibModelBoneData>> LIST_CODEC = Codec.list(BARE_CODEC);

    public GeckolibModelBoneData(String boneName, Vector3d pivot, List<GeckolibModelCubeData> cubes) {
        this(boneName, Optional.empty(), pivot, cubes);
    }

    @Override
    public String getBoneName() {
        return boneName;
    }

    @Override
    public Vector3d getPivot() {
        return pivot;
    }

    @Override
    public Optional<String> getParentBoneName() {
        return parentBoneName;
    }

    @Override
    public List<GeckolibModelCubeData> getCubes() {
        return cubes;
    }
}
