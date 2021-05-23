package io.github.chaosawakens.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

abstract public class RoboEntity extends MonsterEntity {
	
	public boolean didLaser;
	
	public RoboEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@OnlyIn(Dist.CLIENT)
	abstract public boolean isAttacking();
	
	abstract public void setAttacking(boolean attacking);
	
	/*
	 *private void setDidLaser(boolean didLaserIn) {
	 *	this.didLaser = didLaserIn;
	 *}
	 */
}
