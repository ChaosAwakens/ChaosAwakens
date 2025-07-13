package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.common.worldgen.feature.configurations.NBTTreeConfiguration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class NBTTreeFeature extends Feature<NBTTreeConfiguration> {
    public NBTTreeFeature(Codec<NBTTreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NBTTreeConfiguration> featurePlaceContext) {
        if (featurePlaceContext.level().isClientSide()) return false;
        NBTTreeConfiguration config = featurePlaceContext.config();

        Optional<StructureTemplate> template = featurePlaceContext.level().getServer().getStructureManager().get(config.template().left().get());
        if (template.isPresent()) {
            template.get().placeInWorld(featurePlaceContext.level(), featurePlaceContext.origin(), featurePlaceContext.origin(), new StructurePlaceSettings(), featurePlaceContext.random(), 0);
            return true;
        }
        return false;
    }
}
