package io.github.chaosawakens.common.entity.ai.goals.passive.water;

import java.util.List;
import java.util.function.Predicate;

import io.github.chaosawakens.common.entity.base.AnimatableGroupFishEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.entity.ai.goal.Goal;

// Slightly rewritten net.minecraft.entity.ai.goal.FollowSchoolLeaderGoal
public class FollowSchoolLeaderGoal extends Goal {
	private final AnimatableGroupFishEntity curGroupFish;
	private int nextStartTick;
	private final double maxRange;

	public FollowSchoolLeaderGoal(AnimatableGroupFishEntity curGroupFish, double maxFollowRange) {
		this.curGroupFish = curGroupFish;
		this.maxRange = maxFollowRange;
		this.nextStartTick = nextStartTick(curGroupFish);
	}
	
	public FollowSchoolLeaderGoal(AnimatableGroupFishEntity curGroupFish) {
		this(curGroupFish, 8.0D);
		this.nextStartTick = nextStartTick(curGroupFish);
	}

	protected int nextStartTick(AnimatableGroupFishEntity groupFish) {
		return 20 + groupFish.getRandom().nextInt(200) % 40;
	}

	@Override
	public boolean canUse() {
		if (nextStartTick > 0) {
			--nextStartTick;
			return false;
		}
		this.nextStartTick = nextStartTick(curGroupFish);
		Predicate<AnimatableGroupFishEntity> canFollow = (targetLeader) -> targetLeader.canBeFollowed() || !targetLeader.isFollower();
		List<AnimatableGroupFishEntity> availableLeaders = EntityUtil.getEntitiesAround(curGroupFish, AnimatableGroupFishEntity.class, maxRange, maxRange, maxRange, canFollow);

		AnimatableGroupFishEntity curLeader = availableLeaders.stream().filter(AnimatableGroupFishEntity::canBeFollowed).findAny().orElse(curGroupFish);
		curLeader.addFollowers(availableLeaders.stream().filter((targetFollower) -> !targetFollower.isFollower() && !targetFollower.canBeFollowed()));
		return !curGroupFish.hasFollowers() && curGroupFish.isFollower();
	}

	@Override
	public boolean canContinueToUse() {
		return curGroupFish.isFollower() && curGroupFish.inRangeOfLeader() && curGroupFish.isAlive() && curGroupFish.isInWater();
	}

	@Override
	public void start() {
		curGroupFish.moveToLeader();
	}
	
	@Override
	public void stop() {
		curGroupFish.stopFollowing();
	}

	@Override
	public void tick() {
		if (!curGroupFish.getNavigation().isDone()) curGroupFish.moveToLeader();
	}
}