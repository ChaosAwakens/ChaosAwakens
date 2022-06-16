package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.entity.AggressiveAntEntity;
import io.github.chaosawakens.common.entity.AntEntity;
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
			entity.hurt(DamageSource.DROWN, Integer.MAX_VALUE);
		}
	}
}
