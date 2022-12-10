package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.blocks.tileentities.CASignTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class CAWallSignBlock extends WallSignBlock {
	public CAWallSignBlock(AbstractBlock.Properties propertiesIn, WoodType woodTypeIn) {
		super(propertiesIn, woodTypeIn);
	}

	@Override
	public boolean hasTileEntity(BlockState stateIn) {
		return true;
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new CASignTileEntity();
	}
}
