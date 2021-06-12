package io.github.chaosawakens.common.blocks;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

public class AntNestBlock extends Block {
	
	private Supplier<? extends EntityType<? extends CreatureEntity>> ant;
	
	public AntNestBlock(Supplier<? extends EntityType<? extends CreatureEntity>> ant, Properties builder) {
		super(builder);
		this.ant = ant;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (!worldIn.isRemote)return;
		if (worldIn.isRaining())return;
		
		final BlockPos abovePos = pos.up();
		final int amountToSpawn = MathHelper.nextInt(random, 0, 3);
		if (worldIn.getBlockState(abovePos).isAir(worldIn, abovePos)) {
			for (int i = 0; i < amountToSpawn; ++i) {
//				if ((Math.random() <= 0.1)) {
				CreatureEntity entity = ant.get().create(worldIn);
				entity.setPosition(pos.getX() + Math.random(), pos.getY() + 1, pos.getZ() + Math.random());
				worldIn.addEntity(entity);
//				}
			}
		}
	}
}