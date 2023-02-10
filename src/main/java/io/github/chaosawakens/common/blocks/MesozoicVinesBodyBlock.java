package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.AbstractBodyPlantBlock;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public class MesozoicVinesBodyBlock extends AbstractBodyPlantBlock {
	public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	
	public MesozoicVinesBodyBlock(Properties prop) {
		super(prop, Direction.DOWN, SHAPE, false);
	}

	@Override
	protected AbstractTopPlantBlock getHeadBlock() {
		return CABlocks.MESOZOIC_VINES.get();
	}

}
