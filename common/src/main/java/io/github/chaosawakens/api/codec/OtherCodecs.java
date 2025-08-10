package io.github.chaosawakens.api.codec;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.joml.Vector2d;
import org.joml.Vector3d;

public final class OtherCodecs {
    public static final Codec<Vector2d> VECTOR_2D_CODEC = Codec.DOUBLE.listOf().xmap(
            vectorCoords -> new Vector2d(vectorCoords.get(0), vectorCoords.get(1)),
            originalVector -> ObjectArrayList.of(originalVector.x, originalVector.y)
    );
    public static final Codec<Vector3d> VECTOR_3D_CODEC = Codec.DOUBLE.listOf().xmap(
            vectorCoords -> new Vector3d(vectorCoords.get(0), vectorCoords.get(1), vectorCoords.get(2)),
            originalVector -> ObjectArrayList.of(originalVector.x, originalVector.y, originalVector.z)
    );

    private OtherCodecs() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }
}
