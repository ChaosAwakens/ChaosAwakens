package io.github.chaosawakens.common.entity;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.ai.FollowLavaLeaderGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class AbstractLavaGroupFishEntity extends AbstractLavaEntity{
	
	private AbstractLavaGroupFishEntity leader;
	private int schoolSize = 1;
	
	public AbstractLavaGroupFishEntity(EntityType<? extends AbstractLavaEntity> p_i48565_1_, World p_i48565_2_) {
		super(p_i48565_1_, p_i48565_2_);	
	}
	
	@Override
	public int getMaxSpawnClusterSize() {
		return this.maxSchoolSize();
	}
	
	public int maxSchoolSize() {
		return this.getMaxSpawnClusterSize();
	}
	
    public boolean hasSchoolLeader() {
	   return this.leader != null && this.leader.isAlive();
    }
	
	@Override
	protected boolean canRandomSwim() {
		return !this.hasSchoolLeader();
	}
	
	public AbstractLavaGroupFishEntity joinSchool(AbstractLavaGroupFishEntity leader) {
		this.leader = leader;
		leader.increaseSchoolSize();
		return leader;
	}
	
	public void leaveSchool() {
		this.leader.decreaseSchoolSize();
		this.leader = null;
	}
	
	private void increaseSchoolSize() {
		++this.schoolSize;
	}
	
	private void decreaseSchoolSize() {
		--this.schoolSize;
	}
	
	public boolean isSchoolLeader() {
		return this.schoolSize > 1;
	}
	
	public boolean canSchoolIncreaseInSize() {
		return this.isSchoolLeader() && this.schoolSize < this.maxSchoolSize();
	}
	
	public boolean withinRangeOfSchoolLeader() {
		return this.distanceToSqr(leader) <= 100.0D;
	}
	
	public void moveTowardsSchoolLeader() {
		if(this.hasSchoolLeader()) {
			this.getNavigation().moveTo(leader, 2.0D);
		}
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(5, new FollowLavaLeaderGoal(this));
	}
	
	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) {
		 super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
		 
		 if(spawnData == null) {
			 spawnData = new AbstractLavaGroupFishEntity.GroupData(this);
		 } else {
			 this.joinSchool(((AbstractLavaGroupFishEntity.GroupData)spawnData).fishData);
		 }
		 return spawnData;
	}
	
	public void addWithLimitAndFilter(Stream<AbstractLavaGroupFishEntity> f) {
		f.limit(this.maxSchoolSize() - this.schoolSize)
		.filter((f1) -> f1 != this)
		.forEach((f2) -> f2.joinSchool(this));
	}
	
	 public static class GroupData implements ILivingEntityData {

	    public final AbstractLavaGroupFishEntity fishData;

	    public GroupData(AbstractLavaGroupFishEntity fish) {
	      this.fishData = fish;
	    }
     }
	 
    @Override
	public void tick() {
		super.tick();
		if(this.isSchoolLeader() && this.level.random.nextInt(300 - 100) == 1) {
			List<AbstractLavaEntity> list = this.level.getEntitiesOfClass(this.getClass(), this.getBoundingBox().expandTowards(8.0D, 8.0D, 8.0D));
			if(list.size() <= 1) {
				this.schoolSize = 1;
			}
		}
	}
}
