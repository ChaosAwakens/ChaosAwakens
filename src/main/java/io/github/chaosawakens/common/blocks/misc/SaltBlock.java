package io.github.chaosawakens.common.blocks.misc;

import io.github.chaosawakens.common.entity.creature.land.AntEntity;
import io.github.chaosawakens.common.entity.hostile.AggressiveAntEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SaltBlock extends Block {
	
	public SaltBlock(Properties propertiesIn) {
		super(propertiesIn);
	}

	@Override
	public void stepOn(World world, BlockPos blockPos, Entity entity) {
		if (entity instanceof AntEntity || entity instanceof AggressiveAntEntity) {
			entity.hurt(DamageSource.DRY_OUT, Integer.MAX_VALUE);
		}
	}
}
