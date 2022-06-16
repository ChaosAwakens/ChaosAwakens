package io.github.chaosawakens.common.worldgen.placement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Random;
import java.util.stream.Stream;

public class OceanBedPlacement extends Placement<NoPlacementConfig> {
	public OceanBedPlacement() {
		super(NoPlacementConfig.CODEC);
	}

	@SuppressWarnings("null")
	@Override
	public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, NoPlacementConfig config, BlockPos pos) {
		int x = rand.nextInt(16) + pos.getX();
		int z = rand.nextInt(16) + pos.getZ();
		int y = helper.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
		if (y <= 0) return Stream.of();

		BlockPos floor = new BlockPos(x, y - 4, z);

		boolean foundWater = false;
		for (int yy = 0; yy < 5; yy++) {
			long check = BlockPos.offset(yy, Direction.UP);
			BlockState state = helper.getBlockState(BlockPos.of(check));
			Block block = state.getBlock();
			Fluid f = null;
			if (f.is(FluidTags.WATER) || block.is(BlockTags.ICE)) {
				foundWater = true;
				break;
			}
		}
		if (foundWater && BlockTags.SAND.contains(helper.getBlockState(floor).getBlock())) return Stream.of(floor);
		return Stream.of();
	}
}
