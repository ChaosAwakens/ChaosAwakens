package io.github.chaosawakens.common.entity;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class LavaMobEntity extends CreatureEntity {
	public LavaMobEntity(EntityType<? extends LavaMobEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
		this.setPathfindingMalus(PathNodeType.LAVA, 1.0F);
		this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 0.0F);
	}

	public boolean canBreatheUnderwater() {
		return true;
	}

	public CreatureAttribute getMobType() {
		return CreatureAttribute.WATER;
	}

	public boolean checkSpawnObstruction(IWorldReader worldReader) {
		return worldReader.isUnobstructed(this);
	}

	public int getAmbientSoundInterval() {
		return 120;
	}

	protected int getExperienceReward(PlayerEntity playerEntity) {
		return 1 + this.level.random.nextInt(3);
	}

	protected void handleAirSupply(int air) {
		if (this.isAlive() && !this.isInLava()) {
			this.setAirSupply(air - 1);
			if (this.isInWater()) {
				this.setAirSupply(0);
				this.hurt(DamageSource.DROWN, Integer.MAX_VALUE);
			} else if (this.getAirSupply() == -20) {
				this.setAirSupply(0);
				this.hurt(DamageSource.DROWN, 2.0F);
			}
		} else {
			this.setAirSupply(300);
		}
	}

	public void baseTick() {
		int i = this.getAirSupply();
		super.baseTick();
		this.handleAirSupply(i);
	}

	public boolean isPushedByFluid() {
		return false;
	}

	public boolean canBeLeashed(PlayerEntity playerEntity) {
		return false;
	}
}
