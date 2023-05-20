package io.github.chaosawakens.common.blocks.dense;

import io.github.chaosawakens.common.registry.CADamageSources;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThornySunBlock extends DoubleDensePlantBlock {

	public ThornySunBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public void entityInside(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
		pEntity.hurt(CADamageSources.THORNY_SUN, 1.0f);
	}
}
