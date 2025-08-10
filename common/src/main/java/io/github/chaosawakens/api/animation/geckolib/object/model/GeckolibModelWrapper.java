package io.github.chaosawakens.api.animation.geckolib.object.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public record GeckolibModelWrapper(ModelGeometryInfo modelGeometry) { // We're only supporting modern format exports, no weird geometry lists for the resultant record object
    public static final Codec<GeckolibModelWrapper> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ModelGeometryInfo.LIST_CODEC.xmap(
                    modelGeomList -> modelGeomList.get(0),
                    ObjectArrayList::of
            ).fieldOf("minecraft:geometry").forGetter(GeckolibModelWrapper::modelGeometry)
    ).apply(instance, GeckolibModelWrapper::new));
}
