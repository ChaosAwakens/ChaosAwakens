package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelMetadata;
import io.github.chaosawakens.util.CodecUtil;
import org.joml.Vector3d;

public record GeckolibModelMetadata(String geometryId, double textureWidth, double textureHeight, double visibleBoundsWidth, double visibleBoundsHeight, Vector3d visibleBoundsOffset) implements ModelMetadata {
    public static final Codec<GeckolibModelMetadata> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("identifier").forGetter(GeckolibModelMetadata::geometryId),
            Codec.DOUBLE.fieldOf("texture_width").forGetter(GeckolibModelMetadata::textureWidth),
            Codec.DOUBLE.fieldOf("texture_height").forGetter(GeckolibModelMetadata::textureHeight),
            Codec.DOUBLE.optionalFieldOf("visible_bounds_width", 0.0D).forGetter(GeckolibModelMetadata::visibleBoundsWidth),
            Codec.DOUBLE.optionalFieldOf("visible_bounds_height", 0.0D).forGetter(GeckolibModelMetadata::visibleBoundsHeight),
            CodecUtil.VECTOR_3D_CODEC.optionalFieldOf("visible_bounds_offset", new Vector3d()).forGetter(GeckolibModelMetadata::visibleBoundsOffset)
    ).apply(instance, GeckolibModelMetadata::new));

    @Override
    public String getGeometryId() {
        return geometryId;
    }

    @Override
    public double getTextureWidth() {
        return textureWidth;
    }

    @Override
    public double getTextureHeight() {
        return textureHeight;
    }

    @Override
    public double getVisibleBoundsWidth() {
        return visibleBoundsWidth;
    }

    @Override
    public double getVisibleBoundsHeight() {
        return visibleBoundsHeight;
    }

    @Override
    public double getVisibleBoundsOffsetX() {
        return visibleBoundsOffset.x();
    }

    @Override
    public double getVisibleBoundsOffsetY() {
        return visibleBoundsOffset.y();
    }

    @Override
    public double getVisibleBoundsOffsetZ() {
        return visibleBoundsOffset.z();
    }
}
