package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public record GeckolibModelInfo(GeckolibModelGeometryInfo geometryInfo) implements ModelInfo {
    public static final Codec<GeckolibModelInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeckolibModelGeometryInfo.LIST_CODEC.xmap(
                    modelGeomList -> modelGeomList.get(0),
                    ObjectArrayList::of
            ).fieldOf("minecraft:geometry").forGetter(GeckolibModelInfo::geometryInfo)
    ).apply(instance, GeckolibModelInfo::new));

    @Override
    public GeckolibModelGeometryInfo getGeometryInfo() {
        return geometryInfo;
    }
}
