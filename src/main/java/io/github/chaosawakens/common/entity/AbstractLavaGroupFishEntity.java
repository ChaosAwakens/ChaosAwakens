package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.entity.ai.FollowLavaLeaderGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractLavaGroupFishEntity extends AbstractLavaEntity {
	private AbstractLavaGroupFishEntity leader;
	private int schoolSize = 5;

	public AbstractLavaGroupFishEntity(EntityType<? extends AbstractLavaGroupFishEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(4, new FollowLavaLeaderGoal(this));
	}

	public int getMaxSpawnClusterSize() {
		return this.getMaxSchoolSize();
	}

	public int getMaxSchoolSize() {
		return super.getMaxSpawnClusterSize();
	}

	protected boolean canRandomSwim() {
		return !this.isFollower();
	}

	public boolean isFollower() {
		return this.leader != null && this.leader.isAlive();
	}

	public void startFollowing(AbstractLavaGroupFishEntity leader) {
		this.leader = leader;
		leader.addFollower();
	}

	public void stopFollowing() {
		this.leader.removeFollower();
		this.leader = null;
	}

	private void addFollower() {
		++this.schoolSize;
	}

	private void removeFollower() {
		--this.schoolSize;
	}

	public boolean canBeFollowed() {
		return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
	}

	public void tick() {
		super.tick();
		if (this.hasFollowers() && this.level.random.nextInt(300 - 100) == 1) {
			List<AbstractLavaEntity> list = this.level.getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
			if (list.size() <= 1) this.schoolSize = 1;
		}
	}

	public boolean hasFollowers() {
		return this.schoolSize > 1;
	}

	public boolean inRangeOfLeader() {
		return this.distanceToSqr(this.leader) <= 121.0D;
	}

	public void pathToLeader() {
		if (this.isFollower()) this.getNavigation().moveTo(this.leader, 1.0D);
	}

	public void addFollowers(Stream<AbstractLavaGroupFishEntity> entity) {
		entity.limit((this.getMaxSchoolSize() - this.schoolSize)).filter((entity1) -> entity1 != this).forEach((entity2) -> entity2.startFollowing(this));
	}

	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
		super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
		if (spawnData == null) spawnData = new AbstractLavaGroupFishEntity.GroupData(this);
		else this.startFollowing(((AbstractLavaGroupFishEntity.GroupData) spawnData).leader);
		return spawnData;
	}

	public static class GroupData implements ILivingEntityData {
		public final AbstractLavaGroupFishEntity leader;

		public GroupData(AbstractLavaGroupFishEntity fish) {
			this.leader = fish;
		}
	}
}
