package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.items.base.StructureItem;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.*;

import java.util.Random;

public class NBTFeature extends Feature<NBTFeatureConfig> { //TODO Barebones impl, for now

    public NBTFeature(Codec<NBTFeatureConfig> cfgCdc) {
        super(cfgCdc);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NBTFeatureConfig config) {
        BlockPos determinedSurfacePos = new BlockPos(pos.getX(), generator.getBaseHeight(pos.getX(), pos.getZ(), config.heightmapType), pos.getZ()).below();
        TemplateManager templateManager = reader.getLevel().getServer().getStructureManager();
        Template targetNBTTemplate = templateManager.getOrCreate(config.nbtLoc);

        if ((determinedSurfacePos.getY() + targetNBTTemplate.getSize().getY() > reader.getMaxBuildHeight()) || (determinedSurfacePos.getY() < 0)) return false;

        Rotation randRot = Rotation.getRandom(rand);
        Direction randDir = Direction.getRandom(rand);
        StructureItem.PlacementHelper targetPlacement = new StructureItem.PlacementHelper(determinedSurfacePos, targetNBTTemplate.getSize(), randDir);
        PlacementSettings structureSettings = config.matchTerrain ? new PlacementSettings().setRotation(randRot).setMirror(Mirror.NONE).setIgnoreEntities(false).setChunkPos(config.chunkOffset ? new ChunkPos(determinedSurfacePos) : null)
                .addProcessor(new GravityStructureProcessor(Heightmap.Type.WORLD_SURFACE_WG, -1)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR)
                : new PlacementSettings().setRotation(randRot).setMirror(Mirror.NONE).setIgnoreEntities(false).setChunkPos(new ChunkPos(determinedSurfacePos))
                .addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR);

        targetNBTTemplate.placeInWorld(reader, targetPlacement.getTargetPos(), structureSettings, rand);

        return true;
    }
}
