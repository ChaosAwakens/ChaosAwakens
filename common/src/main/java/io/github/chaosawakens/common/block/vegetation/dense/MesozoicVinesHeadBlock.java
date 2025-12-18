package io.github.chaosawakens.common.block.vegetation.dense;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MesozoicVinesHeadBlock extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0, 6.0, 0.0, 16.0, 16.0, 16.0);

    public MesozoicVinesHeadBlock(BlockBehaviour.Properties properties) {
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
    public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
        BlockPos $$3 = $$2.relative(this.growthDirection.getOpposite());
        BlockState $$4 = $$1.getBlockState($$3);
        return !this.canAttachTo($$4) ? false : $$4.is(this.getHeadBlock()) || $$4.is(this.getBodyBlock()) || $$4.is(CABlocks.MESOZOIC_LEAVES.get());
    }

    @Override
    protected Block getBodyBlock() {
        return CABlocks.MESOZOIC_VINES_PLANT.get();
    }
}
