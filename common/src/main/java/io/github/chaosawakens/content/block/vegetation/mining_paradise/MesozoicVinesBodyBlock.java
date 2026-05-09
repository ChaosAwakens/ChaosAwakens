package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import io.github.chaosawakens.content.registry.CABlocks;
import io.github.chaosawakens.content.registry.CASoundTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class MesozoicVinesBodyBlock extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public MesozoicVinesBodyBlock(Properties properties) {
        super(properties.sound(CASoundTypes.DENSE_GRASS), Direction.DOWN, SHAPE, false);
    }

    @Override
    protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
        return CABlocks.MESOZOIC_VINES_HEAD.get();
    }

    @Override
    public boolean canSurvive(BlockState targetState, LevelReader curLevel, BlockPos targetPos) {
        BlockPos oppositePos = targetPos.relative(growthDirection.getOpposite());
        BlockState oppositeState = curLevel.getBlockState(oppositePos);
        return canAttachTo(oppositeState) && (oppositeState.is(getHeadBlock()) || oppositeState.is(getBodyBlock()) || oppositeState.is(CABlocks.MESOZOIC_LEAVES.get()));
    }
}
