package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.ElderGuardianEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

abstract public class RoboEntity extends AnimatedMonsterEntity {
	
	public boolean didLaser;
	
	public RoboEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, IronGolemEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, SnowGolemEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, AbstractVillagerEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, EnderDragonEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, WitherEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, ElderGuardianEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, ZombieEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, SkeletonEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, CreeperEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, WitchEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, GuardianEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, CreatureEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, HerculesBeetleEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, EntEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, RedAntEntity.class, 32.0F));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, EnderDragonEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, WitherEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, ElderGuardianEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, SkeletonEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, CreeperEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, WitchEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, GuardianEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, CreatureEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, HerculesBeetleEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, EntEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, RedAntEntity.class, true));
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return CASoundEvents.ROBO_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.ROBO_DEATH.get();
	}
}
