package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
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

        if (treeTemplate.isPresent()) {
            StructurePlaceSettings settings = new StructurePlaceSettings();

            treeTemplate.get().placeInWorld(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.origin(), settings, featurePlaceContext.random(), 0);
            return true;
        }

        return false;
    }
}
