package io.github.chaosawakens.common.util;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.util.EnumUtil.BlockPatternShape;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BlockPosUtil {
	
	private BlockPosUtil() {
		throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
	}
	
	public static ObjectArrayList<BlockPos> createHollowCircle(World targetWorld, BlockPos originPos, double radius, double height, @Nullable Predicate<Block> blockAffectPredicate) {
		return BlockPatternShape.CIRCLE.applyShape(targetWorld, originPos, radius, height, false, false, blockAffectPredicate);
	}
	
	public static ObjectArrayList<BlockPos> createHollowCircle(World targetWorld, BlockPos originPos, double radius, double height) {
		return createHollowCircle(targetWorld, originPos, radius, height, null);
	}
	
	/**
	 * Destroys all blocks between the specified positions and returns a list of {@link BlockPos}es affected by this operation.
	 * @param targetWorld The world to destroy blocks in.
	 * @param x1 Minimum X bound/first X position.
	 * @param y1 Minimum Y bound/first Y position.
	 * @param z1 Minimum Z bound/first Z position.
	 * @param x2 Maximum X bound/second X position.
	 * @param y2 Maximum Y bound/second Y position.
	 * @param z2 Maximum Z bound/second Z position.
	 * @param dropBlocks Whether or not the destroyed blocks should be dropped.
	 * @param destructibilityPredicate A predicate applied to a {@link Block}. Can be null.
	 * @return A list of {@link BlockPos}es affected by the destruction operation.
	 */
	public static ObjectArrayList<BlockPos> destroyBlocksBetween(World targetWorld, int x1, int y1, int z1, int x2, int y2, int z2, boolean dropBlocks, @Nullable Predicate<Block> destructibilityPredicate) {
		ObjectArrayList<BlockPos> positionsToDestroy = new ObjectArrayList<BlockPos>();
		
		// Cache positions beforehand
		BlockPos.betweenClosed(x1, y1, z1, x2, y2, z2).forEach((curPos) -> positionsToDestroy.add(curPos));
		
		if (!positionsToDestroy.isEmpty()) {
			positionsToDestroy.forEach((targetPos) -> {
				if (targetPos != null) {
					BlockState targetState = targetWorld.getBlockState(targetPos);
					Block targetBlock = targetState.getBlock();
					
					if (destructibilityPredicate == null || (destructibilityPredicate != null && destructibilityPredicate.test(targetBlock))) targetWorld.destroyBlock(targetPos, dropBlocks);
				}
			});
		}
		
		return positionsToDestroy;
	}
	
	/**
	 * Overloaded method for {@link #destroyBlocksBetween(World, int, int, int, int, int, int, boolean, Predicate)}.
	 * @param targetWorld The world to destroy blocks in.
	 * @param posA The starting position to destroy blocks from.
	 * @param posB The bound position to destroy blocks to.
	 * @param dropBlocks Whether or not the destroyed blocks should be dropped.
	 * @param destructibilityPredicate A predicate applied to a {@link Block}. Can be null.
	 * @return A list of {@link BlockPos}es affected by the destruction operation.
	 */
	public static ObjectArrayList<BlockPos> destroyBlocksBetween(World targetWorld, BlockPos posA, BlockPos posB, boolean dropBlocks, @Nullable Predicate<Block> destructibilityPredicate) {
		return destroyBlocksBetween(targetWorld, posA.getX(), posA.getY(), posA.getZ(), posB.getX(), posB.getY(), posB.getZ(), dropBlocks, destructibilityPredicate);
	}
	
	/**
	 * Overloaded method for {@link #destroyBlocksBetween(World, int, int, int, int, int, int, boolean, Predicate)}. Only destroys blocks that collide 
	 * with the specified {@link AxisAlignedBB}.
	 * @param targetWorld The world to destroy blocks in.
	 * @param targetHitbox The hitbox to destroy blocks with which it collides.
	 * @param dropBlocks Whether or not the destroyed blocks should be dropped.
	 * @param destructibilityPredicate A predicate applied to a {@link Block}. Can be null.
	 * @return A list of {@link BlockPos}es affected by the destruction operation.
	 */
	public static ObjectArrayList<BlockPos> destroyCollidingBlocks(World targetWorld, AxisAlignedBB targetHitbox, boolean dropBlocks, @Nullable Predicate<Block> destructibilityPredicate) {
		return destroyBlocksBetween(targetWorld, (int) Math.floor(targetHitbox.minX), (int) Math.floor(targetHitbox.minY), (int) Math.floor(targetHitbox.minZ), (int) Math.floor(targetHitbox.maxX), (int) Math.floor(targetHitbox.maxY), (int) Math.floor(targetHitbox.maxZ), dropBlocks, destructibilityPredicate);
	}
	
	/**
	 * Overloaded method for {@link #destroyCollidingBlocks(World, AxisAlignedBB, boolean, Predicate)}.
	 * @param targetEntity The entity to destroy blocks with which it collides.
	 * @param dropBlocks Whether or not the destroyed blocks should be dropped.
	 * @param destructibilityPredicate A predicate applied to a {@link Block}. Can be null.
	 * @return A list of {@link BlockPos}es affected by the destruction operation.
	 */
	public static ObjectArrayList<BlockPos> destroyCollidingBlocks(Entity targetEntity, boolean dropBlocks, @Nullable Predicate<Block> destructibilityPredicate) {
		return destroyCollidingBlocks(targetEntity.level, targetEntity.getBoundingBox(), dropBlocks, destructibilityPredicate);
	}
}