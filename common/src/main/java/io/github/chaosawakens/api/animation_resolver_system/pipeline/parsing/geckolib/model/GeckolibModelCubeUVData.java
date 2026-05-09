package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.geckolib.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeUVData;
import org.joml.Vector2d;

public record GeckolibModelCubeUVData(GeckolibModelCubeDirectionalUVData northUVInfo,
                                      GeckolibModelCubeDirectionalUVData southUVInfo,
                                      GeckolibModelCubeDirectionalUVData eastUVInfo,
                                      GeckolibModelCubeDirectionalUVData westUVInfo,
                                      GeckolibModelCubeDirectionalUVData upUVInfo,
                                      GeckolibModelCubeDirectionalUVData downUVInfo) implements ModelCubeUVData {
    public static final Codec<GeckolibModelCubeUVData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeckolibModelCubeDirectionalUVData.CODEC.fieldOf("north").forGetter(GeckolibModelCubeUVData::northUVInfo),
            GeckolibModelCubeDirectionalUVData.CODEC.fieldOf("south").forGetter(GeckolibModelCubeUVData::southUVInfo),
            GeckolibModelCubeDirectionalUVData.CODEC.fieldOf("east").forGetter(GeckolibModelCubeUVData::eastUVInfo),
            GeckolibModelCubeDirectionalUVData.CODEC.fieldOf("west").forGetter(GeckolibModelCubeUVData::westUVInfo),
            GeckolibModelCubeDirectionalUVData.CODEC.fieldOf("up").forGetter(GeckolibModelCubeUVData::upUVInfo),
            GeckolibModelCubeDirectionalUVData.CODEC.fieldOf("down").forGetter(GeckolibModelCubeUVData::downUVInfo)
    ).apply(instance, GeckolibModelCubeUVData::new));

    public GeckolibModelCubeUVData(Vector2d uv) {
        this(new GeckolibModelCubeDirectionalUVData(uv, new Vector2d()),
                new GeckolibModelCubeDirectionalUVData(uv, new Vector2d()),
                new GeckolibModelCubeDirectionalUVData(uv, new Vector2d()),
                new GeckolibModelCubeDirectionalUVData(uv, new Vector2d()),
                new GeckolibModelCubeDirectionalUVData(uv, new Vector2d()),
                new GeckolibModelCubeDirectionalUVData(uv, new Vector2d()));
    }

    @Override
    public GeckolibModelCubeDirectionalUVData getNorthUVInfo() {
        return northUVInfo;
    }

    @Override
    public GeckolibModelCubeDirectionalUVData getSouthUVInfo() {
        return southUVInfo;
    }

    @Override
    public GeckolibModelCubeDirectionalUVData getEastUVInfo() {
        return eastUVInfo;
    }

    @Override
    public GeckolibModelCubeDirectionalUVData getWestUVInfo() {
        return westUVInfo;
    }

    @Override
    public GeckolibModelCubeDirectionalUVData getUpUVInfo() {
        return upUVInfo;
    }

    @Override
    public GeckolibModelCubeDirectionalUVData getDownUVInfo() {
        return downUVInfo;
    }
}
