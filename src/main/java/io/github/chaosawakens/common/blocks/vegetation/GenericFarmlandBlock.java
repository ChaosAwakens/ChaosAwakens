package io.github.chaosawakens.common.blocks.vegetation;

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
		int moisture = pState.getValue(MOISTURE);
		
		if (!isNearWater(pLevel, pPos) && !pLevel.isRainingAt(pPos.above())) {
			if (moisture > 0) {
				pLevel.setBlock(pPos, pState.setValue(MOISTURE, Integer.valueOf(moisture - 1)), 2);
			} else if (!isUnderCrops(pLevel, pPos)) {
				turnTo(pState, pLevel, pPos);
			}
		} else if (moisture < 7) {
			pLevel.setBlock(pPos, pState.setValue(MOISTURE, Integer.valueOf(7)), 2);
		}
	}
	
	@Override
	public void fallOn(World world, BlockPos pos, Entity entity, float fallDistance) {
		if (!world.isClientSide && ForgeHooks.onFarmlandTrample(world, pos, this.turnTo.get().defaultBlockState(), fallDistance, entity)) {
			turnTo(world.getBlockState(pos), world, pos);
		}
		entity.causeFallDamage(fallDistance, 1.0F);
	}
	
	private static boolean isNearWater(IWorldReader pLevel, BlockPos pPos) {
		for(BlockPos targetPos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4, 1, 4))) {
			if (pLevel.getFluidState(targetPos).is(FluidTags.WATER)) return true;
		}
		return FarmlandWaterManager.hasBlockWaterTicket(pLevel, pPos);
	}
	
	protected boolean isUnderCrops(IBlockReader pLevel, BlockPos pPos) {
		BlockState plantState = pLevel.getBlockState(pPos.above());
		BlockState targetState = pLevel.getBlockState(pPos);
		
		return plantState.getBlock() instanceof IPlantable && targetState.canSustainPlant(pLevel, pPos, Direction.UP, (IPlantable) plantState.getBlock());
	}
	
	private void turnTo(BlockState pState, World pLevel, BlockPos pPos) {
		pLevel.setBlockAndUpdate(pPos, pushEntitiesUp(pState, this.turnTo.get().defaultBlockState(), pLevel, pPos));
	}
}
