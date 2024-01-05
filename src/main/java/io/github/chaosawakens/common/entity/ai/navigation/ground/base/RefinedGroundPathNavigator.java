package io.github.chaosawakens.common.entity.ai.navigation.ground.base;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.World;

public class RefinedGroundPathNavigator extends GroundPathNavigator {

	public RefinedGroundPathNavigator(MobEntity owner, World curWorld) {
		super(owner, curWorld);
	}

	@Override
	protected void followThePath() {
		
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	protected boolean hasValidPathType(PathNodeType pPathType) {
		return !pPathType.equals(PathNodeType.WATER) && !pPathType.equals(PathNodeType.LAVA) && !pPathType.equals(PathNodeType.OPEN);
	}
}