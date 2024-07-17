package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class NBTFeatureConfig implements IFeatureConfig {
    public static final Codec<NBTFeatureConfig> CODEC = RecordCodecBuilder.create(builder -> builder
            .group(ResourceLocation.CODEC.fieldOf("nbt_loc").forGetter(config -> config.nbtLoc),
                    Heightmap.Type.CODEC.fieldOf("heightmap_type").forGetter(config -> config.heightmapType),
                    Codec.BOOL.fieldOf("match_terrain").forGetter(config -> config.matchTerrain),
                    Codec.BOOL.fieldOf("chunk_offset").forGetter(config -> config.chunkOffset)
            ).apply(builder, NBTFeatureConfig::new));
    public final ResourceLocation nbtLoc;
    public final Heightmap.Type heightmapType;
    public final boolean matchTerrain;
    public final boolean chunkOffset;

    public NBTFeatureConfig(ResourceLocation nbtLoc, Heightmap.Type heightmapType, boolean matchTerrain, boolean chunkOffset) {
        this.nbtLoc = nbtLoc;
        this.heightmapType = heightmapType;
        this.matchTerrain = matchTerrain;
        this.chunkOffset = chunkOffset;
    }

    public NBTFeatureConfig(ResourceLocation nbtLoc, Heightmap.Type heightmapType, boolean matchTerrain) {
        this(nbtLoc, heightmapType, matchTerrain, true);
    }

    public NBTFeatureConfig(ResourceLocation nbtLoc, Heightmap.Type heightmapType) {
        this(nbtLoc, heightmapType, true, true);
    }
}
