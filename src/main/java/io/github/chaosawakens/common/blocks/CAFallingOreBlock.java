package io.github.chaosawakens.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

import java.util.Random;
import java.util.function.Function;

public class CAFallingOreBlock extends FallingBlock {
	private Function<Random, Integer> expFormula = (rand) -> 0;

	public CAFallingOreBlock(Properties properties) {
		super(properties);
	}

	public CAFallingOreBlock withExpDrop(int min, int max) {
		this.expFormula = (rand) -> MathHelper.nextInt(rand, min, max);
		return this;
	}

	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
		return silktouch == 0 ? this.expFormula.apply(RANDOM) : 0;
	}
}
