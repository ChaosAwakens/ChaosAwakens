package io.github.chaosawakens.common.blocks.spawner;

import io.github.chaosawakens.common.entity.creature.land.AntEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class AntNestBlock extends EntityNestBlock {
	private final Supplier<? extends EntityType<? extends AntEntity>> antType;

	public AntNestBlock(Supplier<EntityType<? extends AntEntity>> antEntity, Properties builder) {
		super(antEntity, 10, builder);
		this.antType = antEntity;
	}
	
	@Override
	protected boolean shouldSpawnEntity(World world) {
		AntEntity ant = antType.get().create(world);
		return super.shouldSpawnEntity(world) && !world.isRainingAt(ant.blockPosition());
	}
}
