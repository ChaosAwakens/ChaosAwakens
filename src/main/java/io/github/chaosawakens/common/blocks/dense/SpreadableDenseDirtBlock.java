package io.github.chaosawakens.common.blocks.dense;

import io.github.chaosawakens.common.blocks.vegetation.CASpreadableDirtBlock;
import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.Block;

public class SpreadableDenseDirtBlock extends CASpreadableDirtBlock {
	
	public SpreadableDenseDirtBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Block getDirtBlock() {
		return CABlocks.DENSE_DIRT.get();
	}
	
	
}
