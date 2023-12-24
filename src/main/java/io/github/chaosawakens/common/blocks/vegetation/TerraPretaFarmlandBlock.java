package io.github.chaosawakens.common.blocks.vegetation;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;

import java.util.Random;

public class TerraPretaFarmlandBlock extends GenericFarmlandBlock {
	private IntegerProperty AGE;
	
	public TerraPretaFarmlandBlock(Properties prop, RegistryObject<? extends Block> turnTo) {
		super(prop, turnTo);
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.randomTick(state, worldIn, pos, random);
		BlockPos abovePos = pos.above();
		BlockState aboveState = worldIn.getBlockState(abovePos);
		if(isUnderCrops(worldIn, pos) && random.nextInt(8) == 0) {
			aboveState.getValues().forEach( (property, comparable) -> {
				if(property != null && property.getName().equals("age"))AGE = (IntegerProperty) property;
			});
			if(AGE != null) {
				int maxAge = AGE.getPossibleValues().size() - 1;
				if(aboveState.getValue(AGE) < maxAge) {
					worldIn.setBlockAndUpdate(abovePos, aboveState.setValue(AGE, aboveState.getValue(AGE) + 1));
				}
			}
		}
	}
}
