package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
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
        BlockPos offsetPos = curLevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, featurePlaceContext.origin()).offset(0, -featurePlaceContext.config().groundLevel(), 0);

        if (treeTemplate.isPresent()) { // TODO Actually place this properly (Weeyurd)
            RandomSource rand = featurePlaceContext.random();

            StructureTemplate rawTemp = treeTemplate.get();
            StructurePlaceSettings settings = new StructurePlaceSettings();

            settings.setRotation(randomRotation(rand));

            BoundingBox treeBB = rawTemp.getBoundingBox(settings, offsetPos);
            int treeHeightAtPosition = offsetPos.getY() + treeBB.getYSpan();

            if (treeHeightAtPosition > curLevel.getMaxBuildHeight()) return false;

            treeTemplate.get().placeInWorld(curLevel, offsetPos, offsetPos, settings, rand, 0);

            return true;
        }

        return false;
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
