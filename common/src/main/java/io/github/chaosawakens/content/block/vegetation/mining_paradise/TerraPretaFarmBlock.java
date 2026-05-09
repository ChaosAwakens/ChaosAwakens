package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import com.mememan.nexus.template.object.block.vegetation.DefaultableFarmBlock;
import io.github.chaosawakens.content.registry.CASoundTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class TerraPretaFarmBlock extends DefaultableFarmBlock {

    public TerraPretaFarmBlock(Properties properties) {
        super(properties.sound(CASoundTypes.DENSE_GRASS));
    }

    @Override
    public void randomTick(BlockState targetState, ServerLevel curServerLevel, BlockPos targetPos, RandomSource randSrc) {
        super.randomTick(targetState, curServerLevel, targetPos, randSrc);

        BlockPos abovePos = targetPos.above();
        BlockState aboveState = curServerLevel.getBlockState(abovePos);
        IntegerProperty curAgeProperty = aboveState.getValues().keySet().stream()
                .filter(property -> property.getName().equals("age"))
                .map(property -> (IntegerProperty) property)
                .findFirst()
                .orElse(null);

        if (aboveState.is(BlockTags.CROPS) && curAgeProperty != null) {
            int maxAge = curAgeProperty.getPossibleValues().size() - 1;

            if (randSrc.nextInt(4) == 0 && aboveState.getValue(curAgeProperty) < maxAge) curServerLevel.setBlockAndUpdate(abovePos, aboveState.setValue(curAgeProperty, aboveState.getValue(curAgeProperty) + 1));
        }
    }
}
