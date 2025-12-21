package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.block.base.general.MultiLayerPlantBlock;
import io.github.chaosawakens.common.worldgen.feature.configurations.MultiLayerPlantBlockConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MultiLayerPlantBlockFeature extends Feature<MultiLayerPlantBlockConfiguration> {

    public MultiLayerPlantBlockFeature(Codec<MultiLayerPlantBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext context) {
        MultiLayerPlantBlockConfiguration config = (MultiLayerPlantBlockConfiguration) context.config();
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        BlockState state = config.provider().getState(context.random(), origin);
        if (state.canSurvive(level, origin)) {
            if (state.getBlock() instanceof MultiLayerPlantBlock) {
                if (!level.isEmptyBlock(origin.above(config.maxLevel()))) {
                    return false;
                }

                MultiLayerPlantBlock.placeAt(level, state, origin, 2);
            } else {
                level.setBlock(origin, state, 2);
            }

            return true;
        } else {
            return false;
        }
    }
}
