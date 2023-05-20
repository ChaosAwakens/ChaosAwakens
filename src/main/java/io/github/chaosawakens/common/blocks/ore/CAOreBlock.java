package io.github.chaosawakens.common.blocks.ore;

import java.util.Random;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class CAOreBlock extends Block {
	private Function<Random, Integer> expFormula = (rand) -> 0;

	public CAOreBlock(Properties properties) {
		super(properties);
	}

	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
		return silktouch == 0 ? this.expFormula.apply(RANDOM) : 0;
	}
	
	public CAOreBlock withExpDrop(int min, int max) {
		this.expFormula = (rand) -> MathHelper.nextInt(rand, min, max);
		return this;
	}
}
