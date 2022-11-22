package io.github.chaosawakens.common.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.pathfinding.SwimNodeProcessor;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.extensions.IForgeFluidState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LavaNodeSwimmingProcessor extends SwimNodeProcessor {

	public LavaNodeSwimmingProcessor(boolean b) {
		super(false);
	}

	@SuppressWarnings("deprecation")
	private PathNodeType notRestrictedOrIsFree(int i, int j, int k) {
		BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable();
		for (int x = i; x < i + this.entityWidth; ++x) {
			for (int y = j; y < j + this.entityHeight; ++y) {
				for (int z = k; z < k + this.entityDepth; ++z) {
					IForgeFluidState fstate = this.level.getFluidState(mutableBlockPos.move(x, y, z));
					BlockState bstate = this.level.getBlockState(mutableBlockPos.move(x, y, z));
					if (fstate.getFluidState().isEmpty() && bstate.isAir(level, mutableBlockPos)) return PathNodeType.BREACH;
					if (!fstate.getFluidState().is(FluidTags.LAVA)) return PathNodeType.BLOCKED;
				}
			}
		}
		return PathNodeType.LAVA;
	}

	@Nullable
	private PathPoint getLavaNode(int i, int j, int k) {
		PathNodeType p = this.notRestrictedOrIsFree(i, j, k);
		return p != PathNodeType.LAVA ? null : this.getGoal(i, j, k);
	}

	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public PathNodeType getBlockPathType(IBlockReader baccess, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		IForgeFluidState fstate = baccess.getFluidState(pos);
		BlockState bstate = baccess.getBlockState(pos);

		if (fstate.getFluidState().isEmpty() && bstate.isAir(baccess, pos)) {
			return PathNodeType.BREACH;
		} else {
			return fstate.getFluidState().is(FluidTags.LAVA) ? PathNodeType.LAVA : PathNodeType.BLOCKED;
		}
	}

	@Override
	public int getNeighbors(@Nonnull PathPoint[] p1, @Nonnull PathPoint p2) {
		int n = 0;

		for (Direction d : Direction.values()) {
			PathPoint p = this.getLavaNode(p2.x + d.getStepX(), p2.y + d.getStepY(), p2.z + d.getStepZ());
			if (p != null && !p.closed) p1[n++] = p;
		}
		return n;
	}
}
