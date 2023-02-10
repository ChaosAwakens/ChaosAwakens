package io.github.chaosawakens.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin {
	
	@Overwrite
	public void randomTick(BlockState pState, ServerWorld pLevel, BlockPos pPos, Random pRandom) {
		if (!pState.getValue(LeavesBlock.PERSISTENT) && pState.getValue(LeavesBlock.DISTANCE) == 7) {
			pLevel.setBlock(pPos, Blocks.DIRT.defaultBlockState(), 0);
		}
	}
}
