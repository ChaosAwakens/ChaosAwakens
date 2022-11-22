package io.github.chaosawakens.common.entity.ai;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.world.World;

public class LavaSwimmingNavigator extends SwimmerPathNavigator {
	public LavaSwimmingNavigator(MobEntity entity, World world) {
		super(entity, world);
	}

	@Override
	protected PathFinder createPathFinder(int maxNodes) {
		this.nodeEvaluator = new LavaNodeSwimmingProcessor(false);
		return new PathFinder(this.nodeEvaluator, maxNodes);
	}
}
