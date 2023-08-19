package io.github.chaosawakens.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.LeavesBlock;


@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin {
	
	private LeavesBlockMixin() {
		throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
	}
	
/*	@Overwrite
	private void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
		if (!pState.getValue(LeavesBlock.PERSISTENT) && pState.getValue(LeavesBlock.DISTANCE) == 7) {
			pLevel.setBlock(pPos, Blocks.DIRT.defaultBlockState(), 2);
		}
	}*/
}