package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
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
        Optional<StructureTemplate> treeTemplate = curLevel.getServer().getStructureManager().get(config.template.left().get());

        if (treeTemplate.isEmpty()) return false;

        BlockPos origin = featurePlaceContext.origin();
        Vec3i size = treeTemplate.get().getSize();

        if (origin.getY() + size.getY() > curLevel.getMaxBuildHeight()) return false;

        RandomSource rand = featurePlaceContext.random();
        StructurePlaceSettings settings = new StructurePlaceSettings();

        settings.setRotationPivot(new BlockPos(size.getX() / 2, 0, size.getZ() / 2));
        settings.setRotation(randomRotation(rand));

        if (config.processors.isPresent()) {
            config.processors.get().list().forEach(structureProcessor -> settings.addProcessor(structureProcessor));
        }

        if (config.placedFromSapling)
            treeTemplate.get().placeInWorld(curLevel, origin, origin, settings, rand, Block.UPDATE_CLIENTS);
        else
            treeTemplate.get().placeInWorld(curLevel, origin, origin, settings, rand, Block.UPDATE_NEIGHBORS);

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
