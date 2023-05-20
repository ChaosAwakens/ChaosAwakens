package io.github.chaosawakens.common.entity.ai;

import java.util.ArrayList;
import java.util.List;

import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.block.MagmaBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WebBlock;
import net.minecraft.block.WitherRoseBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class CAWalkNodeProcessor extends WalkNodeProcessor {
	public static boolean canBreakThroughLeaves;
	public static boolean canBreakThroughBlocks;
	public static List<Block> blockBreakingList = new ArrayList<>();;
	
	@Override
	protected PathNodeType evaluateBlockPathType(IBlockReader reader, boolean canOpenDoors, boolean canEnterDoors, BlockPos pos, PathNodeType type) {
		if (type == PathNodeType.DOOR_WOOD_CLOSED && canOpenDoors && canEnterDoors) {
			type = PathNodeType.WALKABLE;
		}
		
		if (type == PathNodeType.BLOCKED && canBreakThroughBlocks && blockBreakingList.contains(reader.getBlockState(pos).getBlock())) {
			type = PathNodeType.WALKABLE;
		}
		

		
		if (type == PathNodeType.LEAVES && this.mob instanceof RoboPounderEntity || canBreakThroughLeaves) {
			type = PathNodeType.WALKABLE;
		}
		
		if (reader.getBlockState(pos).getFluidState().is(FluidTags.WATER)) {
			type = PathNodeType.WATER;
		}
		
		if (reader.getBlockState(pos).getFluidState().is(FluidTags.LAVA)) {
			type = PathNodeType.LAVA;
		}
		

		type = PathNodeType.WALKABLE;
		return type;
	}
	
	
	public PathNodeType checkNeighborBlocks(IBlockReader blockaccessIn, int x, int y, int z, PathNodeType nodeType) {
		if (nodeType == PathNodeType.WALKABLE) {
			BlockPos.Mutable pos = new BlockPos.Mutable();
				for (int i = -1; i <= 1; ++i) {
					for (int j = -1; j <= 1; ++j) {
						if (i != 0 || j != 0) {
							BlockPos newPos = pos.set(i + x, y, j + z);
							switch (this.getBlockPathTypeRaw(blockaccessIn, newPos.getX(), newPos.getY(), newPos.getZ())) {
							case DAMAGE_CACTUS:
								return PathNodeType.DANGER_CACTUS;
							case DANGER_FIRE:
							case DAMAGE_FIRE:
								return PathNodeType.DANGER_FIRE;
							case DAMAGE_OTHER:
							case DANGER_OTHER:
								return PathNodeType.DANGER_OTHER;
							case LAVA:
								return PathNodeType.LAVA;
							default:
								break;
							}
						}		
					}			
				}
		}
		return nodeType;
	}
	
	@Override
	public PathNodeType getBlockPathType(IBlockReader blockaccessIn, int x, int y, int z) {
		PathNodeType pathNode = this.getBlockPathTypeRaw(blockaccessIn, x, y, z);
		if (pathNode == PathNodeType.OPEN && y >= 1) {
			PathNodeType pathNodeBelow = this.getBlockPathTypeRaw(blockaccessIn, x, y - 1, z);
			pathNode = pathNodeBelow != PathNodeType.WALKABLE && pathNodeBelow != PathNodeType.OPEN && pathNodeBelow != PathNodeType.WATER && pathNodeBelow != PathNodeType.LAVA ? PathNodeType.WALKABLE : PathNodeType.OPEN;
			switch (pathNodeBelow) {
			case DAMAGE_FIRE:
			case DAMAGE_CACTUS:
			case DAMAGE_OTHER:
			case DANGER_OTHER:
				pathNode = pathNodeBelow;
				break;
			default:
				break;
			}
		}

		pathNode = this.checkNeighborBlocks(blockaccessIn, x, y, z, pathNode);
		return pathNode;
	}
	
	@SuppressWarnings("deprecation")
	public PathNodeType getBlockPathTypeRaw(IBlockReader blockaccessIn, int x, int y, int z) {
		BlockPos blockPos = new BlockPos(x, y, z);
		BlockState blockState = blockaccessIn.getBlockState(blockPos);
		Block block = blockState.getBlock();
		PathNodeType forgeType = blockState.getAiPathNodeType(blockaccessIn, blockPos, this.mob);
		if (forgeType != null && block != Blocks.LAVA) return forgeType; 
		Material material = blockState.getMaterial();
		if (blockState.isAir(blockaccessIn, blockPos)) {
			return PathNodeType.OPEN;
		} else if (!(block instanceof TrapDoorBlock) && !(block instanceof LilyPadBlock)) {
			if (block instanceof FireBlock || block instanceof MagmaBlock || block instanceof CampfireBlock && blockState.getValue(CampfireBlock.LIT)) {
				return PathNodeType.DAMAGE_FIRE;
			} else if (block instanceof CactusBlock) {
				return PathNodeType.DAMAGE_CACTUS;
			} else if (block instanceof SweetBerryBushBlock && blockState.getValue(SweetBerryBushBlock.AGE) > 0 || block instanceof WitherRoseBlock || block instanceof EndPortalBlock || block instanceof NetherPortalBlock) {
				return PathNodeType.DAMAGE_OTHER;
			} else if (block instanceof SweetBerryBushBlock && blockState.getValue(SweetBerryBushBlock.AGE) == 0 || block instanceof WebBlock || block instanceof AbstractPressurePlateBlock || block instanceof SoulSandBlock) {
				return PathNodeType.DANGER_OTHER;
			} else if (block instanceof DoorBlock && material == Material.WOOD && !blockState.getValue(DoorBlock.OPEN)) {
				return PathNodeType.DOOR_WOOD_CLOSED;
			} else if (block instanceof DoorBlock && material == Material.METAL && !blockState.getValue(DoorBlock.OPEN)) {
				return PathNodeType.DOOR_IRON_CLOSED;
			} else if (block instanceof DoorBlock && blockState.getValue(DoorBlock.OPEN)) {
				return PathNodeType.DOOR_OPEN;
			} else if (block instanceof AbstractRailBlock) {
				return PathNodeType.RAIL;
			} else if (block instanceof LeavesBlock || block instanceof AbstractGlassBlock) {
				if (canBreakThroughLeaves) {
					return PathNodeType.WALKABLE;
				} else {
					return PathNodeType.LEAVES;
				}
			} else if (!block.is(BlockTags.FENCES) && !block.is(BlockTags.WALLS) && !block.is(BlockTags.FLOWER_POTS) && (!(block instanceof FenceGateBlock) || blockState.getValue(FenceGateBlock.OPEN)) && !(block instanceof EndRodBlock) && !(block instanceof AbstractSkullBlock)) {
				FluidState ifluidstate = blockaccessIn.getFluidState(blockPos);
				if (ifluidstate.is(FluidTags.WATER)) {
					return PathNodeType.WATER;
				} else if (ifluidstate.is(FluidTags.LAVA)) {
					return PathNodeType.LAVA;
				} else {
					return blockState.isPathfindable(blockaccessIn, blockPos, PathType.LAND) ? PathNodeType.WALKABLE : PathNodeType.BLOCKED;
				}
			} else {
				return PathNodeType.FENCE;
			}
		} else {
			return PathNodeType.TRAPDOOR;
		}
	}
	
	public CAWalkNodeProcessor addBlockToBreakablesList(Block... blocks) {
		for (int amount = 0; amount < blocks.length; amount++) {
			blockBreakingList.add(blocks[amount]);
		}
		return this;
	}
	
	public CAWalkNodeProcessor setCanBreakBlocks(boolean canBreakBlocks) {
		canBreakThroughBlocks = canBreakBlocks;
		return this;
	}
	
	public CAWalkNodeProcessor setCanBreakLeaves(boolean canBreakLeaves) {
		canBreakThroughLeaves = canBreakLeaves;
		return this;
	}
	
}
