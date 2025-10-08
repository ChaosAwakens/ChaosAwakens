package io.github.chaosawakens.common.block.vegetation.dense;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MesozoicVinesPlantBlock extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public MesozoicVinesPlantBlock(BlockBehaviour.Properties prop) {
        super(prop, Direction.DOWN, SHAPE, false);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) CABlocks.MESOZOIC_VINES.get();
    }

    @Override
    public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
        BlockPos $$3 = $$2.relative(this.growthDirection.getOpposite());
        BlockState $$4 = $$1.getBlockState($$3);
        return !this.canAttachTo($$4) ? false : $$4.is(this.getHeadBlock()) || $$4.is(this.getBodyBlock()) || $$4.is(CABlocks.MESOZOIC_LEAVES.get());
    }
}
