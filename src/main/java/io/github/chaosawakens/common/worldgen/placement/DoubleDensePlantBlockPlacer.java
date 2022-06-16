package io.github.chaosawakens.common.worldgen.placement;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.blocks.DoubleDensePlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class DoubleDensePlantBlockPlacer extends BlockPlacer {
	public static final Codec<DoubleDensePlantBlockPlacer> CODEC;
	public static final DoubleDensePlantBlockPlacer INSTANCE = new DoubleDensePlantBlockPlacer();

	protected BlockPlacerType<?> type() {
		return BlockPlacerType.DOUBLE_PLANT_PLACER;
	}

	public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
		((DoubleDensePlantBlock) state.getBlock()).placeAt(world, pos, 2);
	}

	static {
		CODEC = Codec.unit(() -> INSTANCE);
	}
}
