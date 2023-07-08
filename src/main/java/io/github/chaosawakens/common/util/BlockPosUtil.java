package io.github.chaosawakens.common.util;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.util.EnumUtil.BlockPatternShape;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockPosUtil {
	
	private BlockPosUtil() {
		throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
	}
	
	public static ObjectArrayList<BlockPos> createHollowCircle(World targetWorld, BlockPos originPos, double radius, double height, @Nullable ObjectArrayList<Block> blackListedBlocks) {
		return BlockPatternShape.CIRCLE.applyShape(targetWorld, originPos, radius, height, false, false, blackListedBlocks);
	}
	
	public static ObjectArrayList<BlockPos> createHollowCircle(World targetWorld, BlockPos originPos, double radius, double height) {
		return createHollowCircle(targetWorld, originPos, radius, height, null);
	}
}