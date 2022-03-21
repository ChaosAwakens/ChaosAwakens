package io.github.chaosawakens.common.blocks;

import java.util.Random;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CrystalGrassBlock extends TallGrassBlock{
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

	public CrystalGrassBlock(Properties p_i48388_1_) {
		super(p_i48388_1_);
	}
	
	@Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }
	
	@Override
	public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
		super.animateTick(p_180655_1_, p_180655_2_, p_180655_3_, p_180655_4_);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	 public boolean isValidBonemealTarget(IBlockReader p_176473_1_, BlockPos p_176473_2_, BlockState p_176473_3_, boolean p_176473_4_) {
	      return p_176473_1_.getBlockState(p_176473_2_.above()).isAir();
	   }

	@Override
	   public boolean isBonemealSuccess(World p_180670_1_, Random p_180670_2_, BlockPos p_180670_3_, BlockState p_180670_4_) {
	      return true;
	   }

	@Override
	   public void performBonemeal(ServerWorld w, Random r, BlockPos pos, BlockState state) {
		 DoublePlantBlock doubleplantblock = (DoublePlantBlock)(this == CABlocks.CRYSTAL_GRASS.get() ? CABlocks.TALL_CRYSTAL_GRASS.get() : CABlocks.TALL_CRYSTAL_GRASS.get());
	      if (doubleplantblock.defaultBlockState().canSurvive(w, pos) && w.isEmptyBlock(pos.above()) && w.getBlockState(pos.below()).getBlock().is(CABlocks.CRYSTAL_GRASS_BLOCK.get())) {
	         doubleplantblock.placeAt(w, pos, 2);
	      }
	   }
	
	
}
