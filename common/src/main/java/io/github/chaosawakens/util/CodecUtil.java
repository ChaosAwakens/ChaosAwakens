package io.github.chaosawakens.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.joml.Vector2d;
import org.joml.Vector3d;

public final class CodecUtil {
    public static final Codec<Vector2d> VECTOR_2D_CODEC = Codec.DOUBLE.listOf().xmap(
            vectorCoords -> new Vector2d(vectorCoords.get(0), vectorCoords.get(1)),
            originalVector -> ObjectArrayList.of(originalVector.x, originalVector.y)
    );
    public static final Codec<Vector3d> VECTOR_3D_CODEC = Codec.DOUBLE.listOf().xmap(
            vectorCoords -> new Vector3d(vectorCoords.get(0), vectorCoords.get(1), vectorCoords.get(2)),
            originalVector -> ObjectArrayList.of(originalVector.x, originalVector.y, originalVector.z)
    );

    private CodecUtil() {
        throw new IllegalAccessError("Attempted to construct instance of utility class! (CodecUtil)");
    }

    public static <T> Codec<ObjectWrapper<T>> wrapInObject(Codec<T> originalCodec, String targetFieldName) {
        return RecordCodecBuilder.create(instance -> instance.group(
                originalCodec.fieldOf(targetFieldName).forGetter(a -> a.value)
        ).apply(instance, ObjectWrapper::new));
    }

    public record ObjectWrapper<T>(T value) {
    }
}
