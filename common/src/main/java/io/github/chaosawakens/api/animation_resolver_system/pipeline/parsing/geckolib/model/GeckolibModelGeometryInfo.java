package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelGeometryInfo;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;
import java.util.Optional;

public record GeckolibModelGeometryInfo(Optional<GeckolibModelMetadata> metadata, List<GeckolibModelBoneData> bones) implements ModelGeometryInfo {
    public static final Codec<GeckolibModelGeometryInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeckolibModelMetadata.CODEC.optionalFieldOf("description").forGetter(GeckolibModelGeometryInfo::metadata),
            GeckolibModelBoneData.LIST_CODEC.optionalFieldOf("bones", ObjectArrayList.of()).forGetter(GeckolibModelGeometryInfo::bones)
    ).apply(instance, GeckolibModelGeometryInfo::new));
    public static final Codec<List<GeckolibModelGeometryInfo>> LIST_CODEC = Codec.list(CODEC); // We shouldn't need this for 1 GEOMETRY OBJECT BUT OF COURSE, LEGACY FORMAT PARSING!!!

    @Override
    public Optional<GeckolibModelMetadata> getMetadata() {
        return metadata;
    }

    @Override
    public List<GeckolibModelBoneData> getBones() {
        return bones;
    }
}
