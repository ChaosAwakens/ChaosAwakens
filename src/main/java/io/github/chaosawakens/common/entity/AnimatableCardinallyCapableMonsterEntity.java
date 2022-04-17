package io.github.chaosawakens.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AnimatableCardinallyCapableMonsterEntity extends AnimatableMonsterEntity{
	protected static final DataParameter<Boolean> MOVING_ON_WALL = EntityDataManager.defineId(AnimatableCardinallyCapableMonsterEntity.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Boolean> MOVING_ON_CEILING = EntityDataManager.defineId(AnimatableCardinallyCapableMonsterEntity.class, DataSerializers.BOOLEAN);
	
	public AnimatableCardinallyCapableMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

    @Override
    abstract public void registerControllers(AnimationData data);

    @Override
    abstract public AnimationFactory getFactory();
    
    @Override
    protected void defineSynchedData() {
    	super.defineSynchedData();
    	this.entityData.define(MOVING_ON_WALL, false);
    	this.entityData.define(MOVING_ON_CEILING, false);
    }
    
    public boolean getMovingOnWall() {
    	return this.entityData.get(MOVING_ON_WALL);
    }
    
    public boolean getMovingOnCeiling() {
    	return this.entityData.get(MOVING_ON_CEILING);
    }
    
    public void setMovingOnWall(boolean movingOnWall) {
    	this.entityData.set(MOVING_ON_WALL, true);
    }
    
    public void setMovingOnCeiling(boolean movingOnCeiling) {
    	this.entityData.set(MOVING_ON_CEILING, false);
    }
    
    public float getRotationY() {
    	return this.yBodyRot - this.yBodyRotO;
    }
    
}
