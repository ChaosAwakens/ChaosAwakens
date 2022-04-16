package io.github.chaosawakens.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class CreeperRepellentBlock extends Block{
	protected Vector3d blockPosition;
	protected BlockPos p;
	protected CreatureEntity mob;
	protected Path path;
	protected final PathNavigator pathNav;

	public CreeperRepellentBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
		blockPosition = Vector3d.ZERO;
		pathNav = mob.getNavigation();
	}
	
	public CreeperRepellentBlock(Properties p_i48440_1_, double x, double y, double z) {
		super(p_i48440_1_);
		p = new BlockPos(x, y, z);
		pathNav = mob.getNavigation();
	}
	
	@Override
	public void onPlace(BlockState state, World w, BlockPos pos, BlockState state2, boolean b) {
		repel();
	}
	
	public boolean isARepellableMob(LivingEntity entity) {
		return entity instanceof CreeperEntity;
	}
	
	public boolean repel() {
		if (!isARepellableMob(mob)) return false;

		if (mob == null) return false;

		if (mob.position().distanceTo(blockPosition) >= 50.0D) {
			Vector3d v = RandomPositionGenerator.getPosAvoid(this.mob, 16, 7, blockPosition);
			if (v == null) return false;
			this.path = this.pathNav.createPath(v.x, v.y, v.z, 0);
			this.pathNav.moveTo(this.path, 2.0D);
			return this.path != null;
		}
		return this.path != null;
	}
}
