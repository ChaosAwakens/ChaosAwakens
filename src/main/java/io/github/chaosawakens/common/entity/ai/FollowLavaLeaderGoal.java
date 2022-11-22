package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.AbstractLavaGroupFishEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.List;

public class FollowLavaLeaderGoal extends Goal {
	private final AbstractLavaGroupFishEntity mob;
	private int timeToRecalcPath;
	private int nextStartTick;

	public FollowLavaLeaderGoal(AbstractLavaGroupFishEntity entity) {
		this.mob = entity;
		this.nextStartTick = this.nextStartTick(entity);
	}

	protected int nextStartTick(AbstractLavaGroupFishEntity leaderTaskOwner) {
		return 200 + leaderTaskOwner.getRandom().nextInt(200) % 20;
	}

	public boolean canUse() {
		if (this.mob.hasFollowers()) {
			return false;
		} else if (this.mob.isFollower()) {
			return true;
		} else if (this.nextStartTick > 0) {
			--this.nextStartTick;
			return false;
		} else {
			this.nextStartTick = this.nextStartTick(this.mob);
			java.util.function.Predicate<AbstractLavaGroupFishEntity> predicate = (p_212824_0_) -> p_212824_0_.canBeFollowed() || !p_212824_0_.isFollower();
			List<AbstractLavaGroupFishEntity> list = this.mob.level.getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), predicate);
			AbstractLavaGroupFishEntity abstractLavaGroupFishEntity = list.stream().filter(AbstractLavaGroupFishEntity::canBeFollowed).findAny().orElse(this.mob);
			abstractLavaGroupFishEntity.addFollowers(list.stream().filter((p_212823_0_) -> !p_212823_0_.isFollower()));
			return this.mob.isFollower();
		}
	}

	public boolean canContinueToUse() {
		return this.mob.isFollower() && this.mob.inRangeOfLeader();
	}

	public void start() {
		this.timeToRecalcPath = 0;
	}

	public void stop() {
		this.mob.stopFollowing();
	}

	public void tick() {
		if (--this.timeToRecalcPath <= 0) {
			this.timeToRecalcPath = 10;
			this.mob.pathToLeader();
		}
	}
}
