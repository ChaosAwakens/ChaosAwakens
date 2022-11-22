package io.github.chaosawakens.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

import java.util.Random;
import java.util.function.Function;

public class CAOreBlock extends Block {
	private Function<Random, Integer> expFormula = (rand) -> 0;

	public CAOreBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	public CAOreBlock withExpDrop(int min, int max) {
		this.expFormula = (rand) -> MathHelper.nextInt(rand, min, max);
		return this;
	}

	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
		return silktouch == 0 ? this.expFormula.apply(RANDOM) : 0;
	}
}
