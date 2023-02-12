package io.github.chaosawakens.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.RegistryObject;

public class GenericFarmlandBlock extends FarmlandBlock {

	private final RegistryObject<? extends Block> turnTo;

	public GenericFarmlandBlock(Properties prop, RegistryObject<? extends Block> turnTo) {
		super(prop);
		this.turnTo = turnTo;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		return !this.defaultBlockState().canSurvive(pContext.getLevel(), pContext.getClickedPos()) ? this.turnTo.get().defaultBlockState() : this.defaultBlockState();
	}
	
	@Override
	public void tick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRand) {
		if(!pState.canSurvive(pLevel, pPos)) {
			turnTo(pState, pLevel, pPos);
		}
	}
	
	@Override
	public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
		int i = pState.getValue(MOISTURE);
		if (!isNearWater(pLevel, pPos) && !pLevel.isRainingAt(pPos.above())) {
			if (i > 0) {
				pLevel.setBlock(pPos, pState.setValue(MOISTURE, Integer.valueOf(i - 1)), 2);
			} else if (!isUnderCrops(pLevel, pPos)) {
				turnTo(pState, pLevel, pPos);
			}
		} else if (i < 7) {
			pLevel.setBlock(pPos, pState.setValue(MOISTURE, Integer.valueOf(7)), 2);
		}
	}
	
	@Override
	public void fallOn(World world, BlockPos pos, Entity entity, float p_180658_4_) {
		if (!world.isClientSide && ForgeHooks.onFarmlandTrample(world, pos, this.turnTo.get().defaultBlockState(), p_180658_4_, entity)) {
			turnTo(world.getBlockState(pos), world, pos);
		}
		entity.causeFallDamage(p_180658_4_, 1.0F);
	}
	
	private static boolean isNearWater(IWorldReader pLevel, BlockPos pPos) {
		for(BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4, 1, 4))) {
			if (pLevel.getFluidState(blockpos).is(FluidTags.WATER))return true;
		}
		return FarmlandWaterManager.hasBlockWaterTicket(pLevel, pPos);
	}
	
	protected boolean isUnderCrops(IBlockReader pLevel, BlockPos pPos) {
		BlockState plant = pLevel.getBlockState(pPos.above());
		BlockState state = pLevel.getBlockState(pPos);
		return plant.getBlock() instanceof IPlantable && state.canSustainPlant(pLevel, pPos, Direction.UP, (IPlantable)plant.getBlock());
	}
	
	private void turnTo(BlockState pState, World pLevel, BlockPos pPos) {
		pLevel.setBlockAndUpdate(pPos, pushEntitiesUp(pState, this.turnTo.get().defaultBlockState(), pLevel, pPos));
	}
}
