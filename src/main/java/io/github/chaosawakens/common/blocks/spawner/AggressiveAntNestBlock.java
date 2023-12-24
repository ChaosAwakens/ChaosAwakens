package io.github.chaosawakens.common.blocks.spawner;

import io.github.chaosawakens.common.entity.hostile.AggressiveAntEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class AggressiveAntNestBlock extends EntityNestBlock {
	private final Supplier<EntityType<AggressiveAntEntity>> antType;

	public AggressiveAntNestBlock(Supplier<EntityType<AggressiveAntEntity>> antEntity, Properties builder) {
		super(antEntity, 10, builder);
		this.antType = antEntity;
	}
	
	@Override
	protected boolean shouldSpawnEntity(World world) {
		AggressiveAntEntity ant = antType.get().create(world);
		return super.shouldSpawnEntity(world) && !world.isRainingAt(ant.blockPosition());
	}
}
