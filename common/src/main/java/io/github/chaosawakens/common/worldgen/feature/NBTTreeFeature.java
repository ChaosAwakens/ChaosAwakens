package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
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
        if (featurePlaceContext.level().isClientSide()) return false;

        NBTTreeConfiguration config = featurePlaceContext.config();
        Optional<StructureTemplate> treeTemplate = featurePlaceContext.level().getServer().getStructureManager().get(config.template().left().get());

        if (treeTemplate.isPresent() && config.validSurface().getState(featurePlaceContext.random(),featurePlaceContext.origin()) == featurePlaceContext.level().getBlockState(featurePlaceContext.origin())) {
            StructurePlaceSettings settings = new StructurePlaceSettings();
            settings.setRotation(randomRotation(featurePlaceContext.random()));
            treeTemplate.get().placeInWorld(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.origin(), settings, featurePlaceContext.random(), 0);
            return true;
        }

        return false;
    }

    private Rotation randomRotation(RandomSource random) {
        switch (random.nextInt(4)) {
            case 0:
                return Rotation.NONE;
            case 1:
                return Rotation.CLOCKWISE_90;
            case 2:
                return Rotation.CLOCKWISE_180;
            default:
                return Rotation.COUNTERCLOCKWISE_90;
        }
    }
}
