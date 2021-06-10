package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.entity.*;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class AntNestBlock extends Block {

	public AntNestBlock(Properties builder) {
		super(builder);
	}

	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (!worldIn.isRemote) {
			if (worldIn.isRaining()) {
				return;
			}
			final BlockState bid = worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));

			if (bid == Blocks.AIR.getDefaultState()) {
				for (int i = 0; i < 1; ++i) {
					if (this.getBlock() == CABlocks.BROWN_ANT_NEST.get()) {
						if ((Math.random() <= 0.1)) {
							BrownAntEntity entity = new BrownAntEntity(CAEntityTypes.BROWN_ANT.get(), worldIn);
							entity.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
							worldIn.addEntity(entity);
						}
					}
					else if (this.getBlock() == CABlocks.RAINBOW_ANT_NEST.get()) {
						if ((Math.random() <= 0.1)) {
							RainbowAntEntity entity = new RainbowAntEntity(CAEntityTypes.RAINBOW_ANT.get(), worldIn);
							entity.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
							worldIn.addEntity(entity);
						}
					}
					else if (this.getBlock() == CABlocks.RED_ANT_NEST.get()) {
						if ((Math.random() <= 0.1)) {
							RedAntEntity entity = new RedAntEntity(CAEntityTypes.RED_ANT.get(), worldIn);
							entity.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
							worldIn.addEntity(entity);
						}
					}
					else if (this.getBlock() == CABlocks.UNSTABLE_ANT_NEST.get()) {
						if ((Math.random() <= 0.1)) {
							UnstableAntEntity entity = new UnstableAntEntity(CAEntityTypes.UNSTABLE_ANT.get(), worldIn);
							entity.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
							worldIn.addEntity(entity);
						}
					}
					else if (this.getBlock().matchesBlock(CABlocks.TERMITE_NEST.get())) {
						if ((Math.random() <= 0.1)) {
							TermiteEntity entity = new TermiteEntity(CAEntityTypes.TERMITE.get(), worldIn);
							entity.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
							worldIn.addEntity(entity);
						}
					}
				}
			}
		}
	}
}