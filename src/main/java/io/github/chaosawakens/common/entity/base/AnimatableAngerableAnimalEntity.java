package io.github.chaosawakens.common.entity.base;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RangedInteger;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class AnimatableAngerableAnimalEntity extends AnimatableAnimalEntity implements IAngerable {
	protected static final DataParameter<Byte> ATTACK_ID = EntityDataManager.defineId(AnimatableAngerableAnimalEntity.class, DataSerializers.BYTE);
	protected static final DataParameter<Integer> ANGER_TIME = EntityDataManager.defineId(AnimatableAngerableAnimalEntity.class, DataSerializers.INT);
	protected UUID persistentAngerTarget;
	protected float lastDamageAmount;

	public AnimatableAngerableAnimalEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		super.addAdditionalSaveData(nbt);
		addPersistentAngerSaveData(nbt);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		super.readAdditionalSaveData(nbt);
		if (!level.isClientSide) readPersistentAngerSaveData((ServerWorld) this.level, nbt);
	}
	
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(ATTACK_ID, (byte) 0);
		this.entityData.define(ANGER_TIME, 0);
	}
	
	public byte getAttackID() {
		return !isAngry() ? (byte) 0 : this.entityData.get(ATTACK_ID);
	}
	
	public void setAttackID(byte attackID) {
		if (!isAngry()) return;
		this.entityData.set(ATTACK_ID, attackID);
	}
	
	public boolean isAttacking() {
		return isAngry() && this.entityData.get(ATTACK_ID) != (byte) 0;
	}
	
	public abstract RangedInteger getAngerTimeRange();
	
	@Override
	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(ANGER_TIME);
	}
	
	@Override
	public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {
		this.entityData.set(ANGER_TIME, pRemainingPersistentAngerTime);
	}
	
	@Override
	public void startPersistentAngerTimer() {
		setRemainingPersistentAngerTime(getAngerTimeRange().randomValue(random));
	}
	
	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID target) {
		this.persistentAngerTarget = target;
	}

	public float getLastDamageAmount() {
		return lastDamageAmount;
	}

	public void setLastDamageAmount(float updatedPrevDamageAmount) {
		this.lastDamageAmount = updatedPrevDamageAmount;
	}

	@Override
	protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {
		super.actuallyHurt(pDamageSource, pDamageAmount);

		setLastDamageAmount(pDamageAmount);
	}

	@Override
	public boolean canBeLeashed(PlayerEntity player) {
		return !isAngry() && super.canBeLeashed(player);
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void handleBaseAnimations() {
		if (getIdleAnim() != null && !isMoving() && !isAttacking()) playAnimation(getIdleAnim(), false);
		if (getWalkAnim() != null && isMoving() && !isAttacking()) playAnimation(getWalkAnim(), false);
	}
}
