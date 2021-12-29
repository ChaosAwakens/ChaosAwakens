package io.github.chaosawakens.common.entity.ai;

import java.util.List;

import com.google.common.base.Predicate;

import io.github.chaosawakens.common.entity.AbstractLavaGroupFishEntity;
import net.minecraft.entity.ai.goal.Goal;

public class FollowLavaLeaderGoal extends Goal{
	private final AbstractLavaGroupFishEntity fishTaskOwner;
	private int pathNavTimer;
	private int nextBeginningTick;
	
	public FollowLavaLeaderGoal(AbstractLavaGroupFishEntity leaderTaskOwner) {
		this.fishTaskOwner = leaderTaskOwner;
		this.nextBeginningTick = this.nextTickBeginning(leaderTaskOwner);
	}
	
	@Override
	public boolean canUse() {
		if (this.fishTaskOwner.isSchoolLeader()) {
			return false;
		}
		else if (this.fishTaskOwner.hasSchoolLeader()) {
			return true;
		} else if (this.nextBeginningTick > 0) {
			--this.nextBeginningTick;
			return false;
		} else {
			this.nextBeginningTick = this.nextTickBeginning(fishTaskOwner);
			Predicate<AbstractLavaGroupFishEntity> p = (f) -> f.canSchoolIncreaseInSize() || !f.hasSchoolLeader();
			List<AbstractLavaGroupFishEntity> l = fishTaskOwner.level.getEntitiesOfClass(fishTaskOwner.getClass(), fishTaskOwner.getBoundingBox().expandTowards(8.0D, 8.0D, 8.0D), p);
			AbstractLavaGroupFishEntity absgrpfish = l.stream()
					.filter(AbstractLavaGroupFishEntity::canSchoolIncreaseInSize).findAny().orElse(fishTaskOwner);
			absgrpfish.addWithLimitAndFilter(l.stream().filter((ftl) -> !ftl.hasSchoolLeader()));
			return fishTaskOwner.hasSchoolLeader();
		}
	}
	
	@Override
	public void tick() {
		super.tick();
	}
	
	protected int nextTickBeginning(AbstractLavaGroupFishEntity fish) {
		return 300 - 100 + fish.getRandom().nextInt(400 - 200) % 20;
	}
	
	@Override
	public void start() {
		this.pathNavTimer = 0;
	}
	
	@Override
	public boolean canContinueToUse() {
		return this.fishTaskOwner.hasSchoolLeader() && this.fishTaskOwner.withinRangeOfSchoolLeader();
	}
	
	@Override
	public void stop() {
		this.fishTaskOwner.leaveSchool();
	}

}
