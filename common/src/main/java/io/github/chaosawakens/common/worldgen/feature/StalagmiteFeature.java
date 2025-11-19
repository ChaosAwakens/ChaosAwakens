package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import io.github.chaosawakens.common.worldgen.feature.configurations.StalagmiteConfiguration;

public class StalagmiteFeature extends Feature<StalagmiteConfiguration> {
    public StalagmiteFeature(Codec<StalagmiteConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<StalagmiteConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos pos = context.origin();
        StalagmiteConfiguration config = context.config();
        BlockState state = config.block().getState(random, pos);

        int radius = config.minRadius() + random.nextInt(config.maxRadius() - config.minRadius() + 1);
        float steepness = config.steepness() + ((random.nextFloat() - 0.5F) * config.variation() * 2) * config.steepness();

        int variationFloor = (int) Math.floor(steepness);
        radius = Math.max(1, radius + (random.nextInt(variationFloor * 2 + 1) - variationFloor));

        MutableBlockPos targetPos = new MutableBlockPos.MutableBlockPos(
                pos.getX(),
                getLowestCorner(level, pos, radius),
                pos.getZ()
        );

        float rrs = radius * radius * steepness;

        for (int y = 0; y < rrs; y++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    float pillarCeiling = (-x * x * steepness - z * z * steepness + rrs);
                    int smallerThanZeroFlag = x < 0 || z < 0 ? -1 : 0;

                    if (pillarCeiling >= -1 * smallerThanZeroFlag && pillarCeiling >= y) {
                        BlockPos placePos = targetPos.offset(x, y, z);
                        if (placePos.getY() > level.getMaxBuildHeight()) break;

                        level.setBlock(placePos, state, 2);
                    }
                }
            }
        }
        return true;
    }

    private int getLowestCorner(WorldGenLevel level, BlockPos pos, int radius) {
        int pXpY = level.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX() + radius, pos.getZ() + radius);
        int mXpY = level.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX() - radius, pos.getZ() + radius);
        int pXmY = level.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX() + radius, pos.getZ() - radius);
        int mXmY = level.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX() - radius, pos.getZ() - radius);
        return Math.min(Math.min(Math.min(pXpY, mXpY), pXmY), mXmY);
    }
}
