package io.github.chaosawakens.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.World;

public class AnimatableGroundPathNavigator extends GroundPathNavigator {
    private MobEntity entity;
	protected long tickDelta;
	protected double animationProgress;
	protected double animationLength;
	private long lastGameTime;
	private boolean isFirsLoop = true;

    public AnimatableGroundPathNavigator(MobEntity mob, World world) {
        super(mob, world);
        this.entity = mob;
    }

    @Override
    public void tick() {
		if (this.entity == null) return;
		if (this.isFirsLoop) {
			this.isFirsLoop = false;
			this.animationProgress += 1;
			this.lastGameTime = this.entity.level.getGameTime();
			return;
		}
		this.tickDelta = this.entity.level.getGameTime() - this.lastGameTime;
		this.animationProgress += 1 + this.tickDelta / 100000.0;
		this.lastGameTime = this.entity.level.getGameTime();
        this.tick++;
    }

    public boolean moveTo(double x, double y, double z, double speedModifier) {
        mob.getNavigation().moveTo(x, y, z, speedModifier);
        return true;
    }

    public boolean moveToEntity(Entity entityIn, double speedModifier) {
        mob.getNavigation().moveTo(entityIn.getX(), entityIn.getY(), entityIn.getZ(), speedModifier);
        return true;
    }
    
}
