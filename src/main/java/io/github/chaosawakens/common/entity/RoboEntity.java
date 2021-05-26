package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
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
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return CASoundEvents.ROBO_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.ROBO_DEATH.get();
	}
}
