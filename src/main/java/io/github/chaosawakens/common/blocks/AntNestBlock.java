package io.github.chaosawakens.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class AntNestBlock extends Block {

	private final Supplier<? extends EntityType<? extends CreatureEntity>> ant;

	public AntNestBlock(Supplier<? extends EntityType<? extends CreatureEntity>> ant, Properties builder) {
		super(builder);
		this.ant = ant;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (worldIn.isClientSide || worldIn.isRaining()) return;

		final BlockPos abovePos = pos.above();
		final int amountToSpawn = MathHelper.nextInt(random, 0, 3);
		if (worldIn.getBlockState(abovePos).isAir(worldIn, abovePos)) {
			for (int i = 0; i < amountToSpawn; ++i) {
				CreatureEntity entity = ant.get().create(worldIn);
				assert entity != null;
				entity.setPos(pos.getX() + Math.random(), pos.getY() + 1, pos.getZ() + Math.random());
				worldIn.addFreshEntity(entity);
			}
		}
	}
}
