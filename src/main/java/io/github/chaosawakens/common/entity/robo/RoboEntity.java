package io.github.chaosawakens.common.entity.robo;

import java.util.Random;

import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

abstract public class RoboEntity extends AnimatableMonsterEntity {

	public RoboEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 32.0F));
		this.goalSelector.addGoal(4, new LookAtGoal(this, IronGolemEntity.class, 32.0F));
		this.goalSelector.addGoal(4, new LookAtGoal(this, SnowGolemEntity.class, 32.0F));
		this.goalSelector.addGoal(3, new LookAtGoal(this, AbstractVillagerEntity.class, 32.0F));
		this.goalSelector.addGoal(4, new LookAtGoal(this, AnimalEntity.class, 32.0F));
		this.targetSelector.addGoal(5, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, SnowGolemEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, true));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return CASoundEvents.ROBO_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return CASoundEvents.ROBO_DEATH.get();
	}

	public static boolean checkRoboSpawnRules(EntityType<? extends RoboEntity> entityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
		return world.canSeeSky(pos) && checkMonsterSpawnRules(entityType, world, reason, pos, random);
	}
}
