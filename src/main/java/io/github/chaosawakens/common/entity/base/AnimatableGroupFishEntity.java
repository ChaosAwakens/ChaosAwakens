package io.github.chaosawakens.common.entity.base;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.ai.goals.passive.water.FollowSchoolLeaderGoal;
import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AnimatableGroupFishEntity extends AnimatableFishEntity {
	private AnimatableGroupFishEntity animatableGroupLeader;
	private int schoolSize = 1;
	private int probabilityToAddFollower = 200; //TODO Getters and setters
	private double maxLeaderRange = 121.0D;

	public AnimatableGroupFishEntity(EntityType<? extends AbstractFishEntity> type, World world) {
		super(type, world);
	}

	public AnimatableGroupFishEntity startFollowing(AnimatableGroupFishEntity pLeader) {
		this.animatableGroupLeader = pLeader;
		pLeader.addFollower();
		return pLeader;
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(2, new FollowSchoolLeaderGoal(this));
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return this.getMaxSchoolSize();
	}

	public int getMaxSchoolSize() {
		return super.getMaxSpawnClusterSize();
	}

	@Override
	protected boolean canRandomSwim() {
		return !this.isFollower();
	}
	
	@Override
	public boolean canRoam() {
		return isAlive() && canRandomSwim();
	}

	public boolean isFollower() {
		return animatableGroupLeader != null && animatableGroupLeader.isAlive();
	}

	public void stopFollowing() {
		this.animatableGroupLeader.removeFollower();
		this.animatableGroupLeader = null;
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
	
	@Override
	public void tick() {
		super.tick();

		if (hasFollowers() && this.level.random.nextInt(probabilityToAddFollower) == 0 && animatableGroupLeader != null) {
			List<AnimatableGroupFishEntity> curSchool = EntityUtil.getEntitiesAround(animatableGroupLeader, AnimatableGroupFishEntity.class, 8.0D, 8.0D, 8.0D, 8.0D);
			if (curSchool.size() <= 1) this.schoolSize = 1;
		}
	}

	public boolean hasFollowers() {
		return this.schoolSize > 1;
	}

	public boolean inRangeOfLeader() {
		return this.distanceToSqr(animatableGroupLeader) <= maxLeaderRange;
	}

	public void moveToLeader() {
		if (this.isFollower()) this.getNavigation().moveTo(animatableGroupLeader, 1.0D);
	}

	public void addFollowers(Stream<AnimatableGroupFishEntity> pFollowers) {
		pFollowers.limit((long)(this.getMaxSchoolSize() - this.schoolSize)).filter((follower) -> follower != this).forEach((follower) -> follower.startFollowing(this));
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Nullable
	public ILivingEntityData finalizeSpawn(IServerWorld pLevel, DifficultyInstance pDifficulty, SpawnReason pReason, @Nullable ILivingEntityData pSpawnData, @Nullable CompoundNBT pDataTag) {
		super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
		
		if (pSpawnData == null) pSpawnData = new GroupData(this);
		else this.startFollowing(((GroupData) pSpawnData).schoolLeader);

		return pSpawnData;
	}

	public static class GroupData implements ILivingEntityData {
		public final AnimatableGroupFishEntity schoolLeader;

		public GroupData(AnimatableGroupFishEntity pLeader) {
			this.schoolLeader = pLeader;
		}
	}
}
