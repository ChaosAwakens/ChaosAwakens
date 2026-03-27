package io.github.chaosawakens.content.block.vegetation.mining_paradise;

import io.github.chaosawakens.content.registry.CABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class MesozoicVinesHeadBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0, 6.0, 0.0, 16.0, 16.0, 16.0);

    public MesozoicVinesHeadBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1f);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource source) {
        return NetherVines.getBlocksToGrowWhenBonemealed(source);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    public boolean canSurvive(BlockState targetState, LevelReader curLevel, BlockPos targetPos) {
        BlockPos oppositePos = targetPos.relative(growthDirection.getOpposite());
        BlockState oppositeState = curLevel.getBlockState(oppositePos);
        return canAttachTo(oppositeState) && (oppositeState.is(getHeadBlock()) || oppositeState.is(getBodyBlock()) || oppositeState.is(CABlocks.MESOZOIC_LEAVES.get()));
    }

    @Override
    protected @NotNull Block getBodyBlock() {
        return CABlocks.MESOZOIC_VINES_BODY.get();
    }
}
