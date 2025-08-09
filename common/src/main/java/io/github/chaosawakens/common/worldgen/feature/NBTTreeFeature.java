package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;

public class NBTTreeFeature extends Feature<NBTTreeConfiguration> {

    public NBTTreeFeature(Codec<NBTTreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NBTTreeConfiguration> featurePlaceContext) {
        WorldGenLevel curLevel = featurePlaceContext.level();

        if (curLevel.isClientSide()) return false;

        NBTTreeConfiguration config = featurePlaceContext.config();
        Optional<StructureTemplate> treeTemplate = curLevel.getServer().getStructureManager().get(config.template().left().get());

        if (treeTemplate.isEmpty()) return false;

        Vec3i size = treeTemplate.get().getSize();
        BoundingBox trunkBB = config.trunkBB();

        BlockPos firstCorner = curLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, featurePlaceContext.origin()).offset(trunkBB.minX(), 0, trunkBB.minZ());
        BlockPos secondCorner = curLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, featurePlaceContext.origin()).offset(trunkBB.maxX(), 0, trunkBB.minZ());
        BlockPos thirdCorner = curLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, featurePlaceContext.origin()).offset(trunkBB.minX(), 0, trunkBB.maxZ());
        BlockPos fourthCorner = curLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, featurePlaceContext.origin()).offset(trunkBB.maxX(), 0, trunkBB.maxZ());

        RandomSource rand = featurePlaceContext.random();
        BlockState validSurface = config.validSurface().getState(rand, BlockPos.ZERO);

        if (validSurface != curLevel.getBlockState(firstCorner.below())) return false;
        if (validSurface != curLevel.getBlockState(secondCorner.below())) return false;
        if (validSurface != curLevel.getBlockState(thirdCorner.below())) return false;
        if (validSurface != curLevel.getBlockState(fourthCorner.below())) return false;

        // Check for lowest corner and set offset Y level to it
        BlockPos offsetPos;
        if (firstCorner.getY() <= secondCorner.getY() && firstCorner.getY() <= thirdCorner.getY() && firstCorner.getY() <= fourthCorner.getY()) {
            offsetPos = featurePlaceContext.origin().atY(firstCorner.getY() - config.groundLevel());
        } else if (secondCorner.getY() <= firstCorner.getY() && secondCorner.getY() <= thirdCorner.getY() && secondCorner.getY() <= fourthCorner.getY()) {
            offsetPos = featurePlaceContext.origin().atY(secondCorner.getY() - config.groundLevel());
        } else if (thirdCorner.getY() <= firstCorner.getY() && thirdCorner.getY() <= secondCorner.getY() && thirdCorner.getY() <= fourthCorner.getY()) {
            offsetPos = featurePlaceContext.origin().atY(thirdCorner.getY() - config.groundLevel());
        } else {
            offsetPos = featurePlaceContext.origin().atY(fourthCorner.getY() - config.groundLevel());
        }

        StructureTemplate rawTemp = treeTemplate.get();
        StructurePlaceSettings settings = new StructurePlaceSettings();

        settings.setRotationPivot(new BlockPos(size.getX() / 2, 0, size.getZ() / 2));
        settings.setRotation(randomRotation(rand));

        BoundingBox treeBB = rawTemp.getBoundingBox(settings, offsetPos);
        int treeHeightAtPosition = offsetPos.getY() + treeBB.getYSpan();

        if (treeHeightAtPosition > curLevel.getMaxBuildHeight()) return false;

        treeTemplate.get().placeInWorld(curLevel, offsetPos, offsetPos, settings, rand, 0);

        return true;
    }

    private Rotation randomRotation(RandomSource random) {
        return switch (random.nextInt(4)) {
            case 0 -> Rotation.NONE;
            case 1 -> Rotation.CLOCKWISE_90;
            case 2 -> Rotation.CLOCKWISE_180;
            default -> Rotation.COUNTERCLOCKWISE_90;
        };
    }
}
