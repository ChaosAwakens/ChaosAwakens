package io.github.chaosawakens.common.entity.ai.pathfinding;

import java.util.Objects;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.world.World;

public class CASwimmerPathNavigator extends SwimmerPathNavigator {

	public CASwimmerPathNavigator(MobEntity entity, World world) {
		super(entity, world);
	}
	
	@Override
	protected void followThePath() {
		if (this.mob.level.isClientSide) return;
		Path pathCur = Objects.requireNonNull(path);
		
		MobEntity owner = this.mob;
		
		
		
		super.followThePath();
	}
	
	/*private BlockPos findWater() {
		return waterPos;
	}*/
	
}
