package io.github.chaosawakens.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class BossSpawnerBlock extends Block {
	private final Supplier<? extends EntityType<? extends LivingEntity>> boss;

	public BossSpawnerBlock(Supplier<? extends EntityType<? extends LivingEntity>> boss, Properties builder) {
		super(builder);
		this.boss = boss;
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.tick(state, worldIn, pos, random);
		LivingEntity entity = boss.get().create(worldIn);
		assert entity != null;
		entity.setPos(pos.getX(), pos.getY(), pos.getZ());
		worldIn.addFreshEntity(entity);
		worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	}

	@Override
	public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
		LivingEntity entity = boss.get().create(worldIn);
		assert entity != null;
		entity.setPos(pos.getX(), pos.getY(), pos.getZ());
		worldIn.addFreshEntity(entity);
		worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	}
}
