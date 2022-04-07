package io.github.chaosawakens.common.entity;

import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

abstract public class RoboEntity extends AnimatableMonsterEntity {
	protected static final DataParameter<Boolean> RAGE_MODE_ENABLED = EntityDataManager.defineId(RoboEntity.class, DataSerializers.BOOLEAN); 
	protected static final DataParameter<Boolean> RAGE_MODE_DISABLED = EntityDataManager.defineId(RoboEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> RAGE_RUNNING = EntityDataManager.defineId(RoboEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> RAGE_RUNNING_DISABLED = EntityDataManager.defineId(RoboEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> SIDE_SWEEPING = EntityDataManager.defineId(RoboEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> PUNCHING = EntityDataManager.defineId(RoboEntity.class, DataSerializers.BOOLEAN);
	
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
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.entityData.define(RAGE_MODE_ENABLED, false);
    	this.entityData.define(RAGE_RUNNING, false);
    	this.entityData.define(SIDE_SWEEPING, false);
    	this.entityData.define(PUNCHING, false);
    }
    
    public boolean getRageMode() {
    	return this.entityData.get(RAGE_MODE_ENABLED);
    }
    
    
    public void setRageMode(boolean rageMode) {
    	this.entityData.set(RAGE_MODE_ENABLED, rageMode);
    }
    
    public boolean getRageRunning() {
    	return this.entityData.get(RAGE_RUNNING);
    }
    
    public void setRageRunning(boolean rageRunning) {
    	this.entityData.set(RAGE_RUNNING, rageRunning);
    }
    
    public boolean getSideSweep() {
    	return this.entityData.get(SIDE_SWEEPING);
    }
    
    public void setSideSweeping(boolean sideSweep) {
    	this.entityData.set(SIDE_SWEEPING, sideSweep);
    }
    
    public boolean getPunching() {
    	return this.entityData.get(PUNCHING);
    }
    
    public void setPunching(boolean punching) {
    	this.entityData.set(PUNCHING, punching);
    }
}
