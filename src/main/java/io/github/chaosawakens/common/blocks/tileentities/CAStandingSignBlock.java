package io.github.chaosawakens.common.blocks.tileentities;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class CAStandingSignBlock extends StandingSignBlock {
	
	public CAStandingSignBlock(Properties propertiesIn, WoodType woodTypeIn) {
		super(propertiesIn, woodTypeIn);
	}

	@Override
	public boolean hasTileEntity(BlockState stateIn) {
		return true;
	}

	@Override
	public TileEntity newBlockEntity(@Nonnull IBlockReader worldIn) {
		return new CASignTileEntity();
	}
}
