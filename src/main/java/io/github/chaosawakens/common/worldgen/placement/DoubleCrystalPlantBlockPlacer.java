package io.github.chaosawakens.common.worldgen.placement;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.blocks.DoubleCrystalPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

import java.util.Random;

public class DoubleCrystalPlantBlockPlacer extends BlockPlacer {
	public static final Codec<DoubleCrystalPlantBlockPlacer> CODEC;
	public static final DoubleCrystalPlantBlockPlacer INSTANCE = new DoubleCrystalPlantBlockPlacer();

	protected BlockPlacerType<?> type() {
		return BlockPlacerType.DOUBLE_PLANT_PLACER;
	}

	public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
		((DoubleCrystalPlantBlock) state.getBlock()).placeAt(world, pos, 2);
	}

	static {
		CODEC = Codec.unit(() -> INSTANCE);
	}
}
