package io.github.chaosawakens.common.entity.misc;

import java.util.List;
import java.util.function.Consumer;

import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AOEHitboxEntity extends Entity {
	private static final DataParameter<Float> CUR_RADIUS = EntityDataManager.defineId(AOEHitboxEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> CUR_HEIGHT = EntityDataManager.defineId(AOEHitboxEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> EXPANSION_SPEED = EntityDataManager.defineId(AOEHitboxEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> RADIUS_CAP = EntityDataManager.defineId(AOEHitboxEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> HEIGHT_CAP = EntityDataManager.defineId(AOEHitboxEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> LIFETIME = EntityDataManager.defineId(AOEHitboxEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> ACTION_EXECUTION_INTERVAL = EntityDataManager.defineId(AOEHitboxEntity.class, DataSerializers.INT);
	private Consumer<LivingEntity> actionOnIntersection;

	public AOEHitboxEntity(EntityType<?> pType, World pLevel) {
		super(pType, pLevel);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(CUR_RADIUS, 0.0F);
		this.entityData.define(CUR_HEIGHT, 0.0F);
		this.entityData.define(EXPANSION_SPEED, 0.0F);
		this.entityData.define(RADIUS_CAP, 0.0F);
		this.entityData.define(HEIGHT_CAP, 0.0F);
		this.entityData.define(LIFETIME, 0);
		this.entityData.define(ACTION_EXECUTION_INTERVAL, 20);
	}
	
	public float getRadius() {
		return this.entityData.get(CUR_RADIUS);
	}
	
	public void setRadius(float curRadius) {
		this.entityData.set(CUR_RADIUS, curRadius);
	}
	
	public float getHeight() {
		return this.entityData.get(CUR_HEIGHT);
	}
	
	public void setHeight(float curHeight) {
		this.entityData.set(CUR_HEIGHT, curHeight);
	}
	
	protected void incrementRadius() {
		setRadius(getRadius() + getExpansionSpeed());
	}
	
	protected void incrementHeight() {
		setHeight(getHeight() + getExpansionSpeed());
	}
	
	protected void incrementSize() {
		incrementRadius();
		incrementHeight();
	}
	
	public float getExpansionSpeed() {
		return this.entityData.get(EXPANSION_SPEED);
	}
	
	public void setExpansionSpeed(float expansionSpeed) {
		this.entityData.set(EXPANSION_SPEED, expansionSpeed);
	}
	
	public float getMaxRadius() {
		return this.entityData.get(RADIUS_CAP);
	}
	
	public void setMaxRadius(float radiusCap) {
		this.entityData.set(RADIUS_CAP, radiusCap);
	}
	
	public float getMaxHeight() {
		return this.entityData.get(HEIGHT_CAP);
	}
	
	public void setMaxHeight(float heightCap) {
		this.entityData.set(HEIGHT_CAP, heightCap);
	}
	
	public int getMaxAge() {
		return this.entityData.get(LIFETIME);
	}
	
	public void setMaxAge(int lifetime) {
		this.entityData.set(LIFETIME, lifetime);
	}
	
	protected void setAge(int tickCount) {
		this.tickCount = tickCount;
	}
	
	public int getActionExecutionInterval() {
		return this.entityData.get(ACTION_EXECUTION_INTERVAL);
	}
	
	public void setActionExecutionInterval(int actionExecutionInterval) {
		this.entityData.set(ACTION_EXECUTION_INTERVAL, actionExecutionInterval);
	}
	
	public Consumer<LivingEntity> getActionOnIntersection(List<LivingEntity> targets) {
		return actionOnIntersection;
	}
	
	public void setActionOnIntersection(Consumer<LivingEntity> actionOnIntersection) {
		this.actionOnIntersection = actionOnIntersection;
	}
	
	@Override
	public void tick() {
		if (tickCount >= getMaxAge()) remove();
		
		updateHitbox();
	}
	
	private void updateHitbox() {
		if (getRadius() < getMaxRadius()) incrementRadius();
		if (getHeight() < getMaxHeight()) incrementHeight();
		
		List<LivingEntity> potentialAffectedTargets = EntityUtil.getAllEntitiesAround(this, getBoundingBox().getXsize(), getBoundingBox().getYsize(), getBoundingBox().getZsize(), getBoundingBox().getSize());
		
		for (LivingEntity potentialTarget : potentialAffectedTargets) {
			if (actionOnIntersection == null) break;
			if (potentialTarget == null) continue;
			
			actionOnIntersection.accept(potentialTarget);
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		setAge(pCompound.getInt("Age"));
		setRadius(pCompound.getFloat("Radius"));
		setHeight(pCompound.getFloat("Height"));
		setExpansionSpeed(pCompound.getFloat("RadiusExpansionSpeed"));
		setMaxRadius(pCompound.getFloat("RadiusCap"));
		setMaxHeight(pCompound.getFloat("HeightCap"));
		setMaxAge(pCompound.getInt("Lifetime"));
		setActionExecutionInterval(pCompound.getInt("ActionExecutionInterval"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		pCompound.putInt("Age", tickCount);
		pCompound.putFloat("Radius", getRadius());
		pCompound.putFloat("Height", getHeight());
		pCompound.putFloat("RadiusExpansionSpeed", getExpansionSpeed());
		pCompound.putFloat("RadiusCap", getMaxRadius());
		pCompound.putFloat("HeightCap", getMaxHeight());
		pCompound.putInt("Lifetime", getMaxAge());
		pCompound.putInt("ActionExecutionInterval", getActionExecutionInterval());
	}
	
	@Override
	public EntitySize getDimensions(Pose pPose) {
		return EntitySize.scalable(getRadius() * 2.0F, getHeight() * 2.0F);
	}
	
	@Override
	public PushReaction getPistonPushReaction() {
		return PushReaction.IGNORE;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
