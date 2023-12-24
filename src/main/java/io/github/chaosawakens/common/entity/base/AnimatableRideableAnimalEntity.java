package io.github.chaosawakens.common.entity.base;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public abstract class AnimatableRideableAnimalEntity extends AnimatableAnimalEntity implements IRideable, IEquipable {
	private static final DataParameter<Boolean> IS_SADDLED = EntityDataManager.defineId(AnimatableRideableAnimalEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.defineId(AnimatableRideableAnimalEntity.class, DataSerializers.INT);
	private final BoostHelper steeringBoostHelper = new BoostHelper(entityData, BOOST_TIME, IS_SADDLED);

	public AnimatableRideableAnimalEntity(EntityType<? extends AnimalEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_SADDLED, false);
		this.entityData.define(BOOST_TIME, 0);
	}

	@Override
	public void onSyncedDataUpdated(DataParameter<?> pKey) {
		if (BOOST_TIME.equals(pKey) && this.level.isClientSide) steeringBoostHelper.onSynced();
		super.onSyncedDataUpdated(pKey);
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT pCompound) {
		super.addAdditionalSaveData(pCompound);
		steeringBoostHelper.addAdditionalSaveData(pCompound);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT pCompound) {
		super.readAdditionalSaveData(pCompound);
		steeringBoostHelper.readAdditionalSaveData(pCompound);
	}

	@Override
	public Vector3d getLeashOffset() {
		return new Vector3d(0.0D, (0.6F * getEyeHeight()), (getBbWidth() * 0.4F));
	}

	@Nullable
	@Override
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	@Override
	public final boolean canBeControlledByRider() {
		Entity controllingPassenger = getControllingPassenger();

		if (!(controllingPassenger instanceof PlayerEntity) || !controllingPassenger.isAlive()) return false;
		else {
			PlayerEntity playerPassenger = (PlayerEntity) controllingPassenger;
			return canPlayerRiderControl(playerPassenger);
		}
	}
	
	protected abstract boolean canPlayerRiderControl(PlayerEntity passenger);
	
	protected abstract SoundEvent getSaddleSoundEvent();
	
	public Item getSaddleItem() {
		return Items.SADDLE;
	}
	
	@Override
	public float getSteeringSpeed() {
		return (float) (getMovementSpeed() * 0.225F);
	}

	@Override
	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby();
	}
	
	@Override
	public boolean isSaddled() {
		return steeringBoostHelper.hasSaddle();
	}

	@Override
	public void equipSaddle(@Nullable SoundCategory pSource) {
		steeringBoostHelper.setSaddle(true);
		if (pSource != null) this.level.playSound((PlayerEntity) null, this, getSaddleSoundEvent(), pSource, 0.5F, 1.0F);
	}
	
	@Override
	public boolean boost() {
		return steeringBoostHelper.boost(getRandom());
	}
	
	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		if (isSaddled()) spawnAtLocation(getSaddleItem());
	}

	@Override
	public Vector3d getDismountLocationForPassenger(LivingEntity pLivingEntity) {
		Direction motionDir = getMotionDirection();
		
		if (motionDir.getAxis() == Direction.Axis.Y) return super.getDismountLocationForPassenger(pLivingEntity);
		else {
			int[][] dismountDirectionOffsets = TransportationHelper.offsetsForDirection(motionDir);
			BlockPos curBlockPos = blockPosition();
			Mutable potentialDismountPos = new Mutable();

			for(Pose passengerPose : pLivingEntity.getDismountPoses()) {
				AxisAlignedBB poseAabb = pLivingEntity.getLocalBoundsForPose(passengerPose);

				for(int[] dismountDirOffset : dismountDirectionOffsets) {
					potentialDismountPos.set(curBlockPos.getX() + dismountDirOffset[0], curBlockPos.getY(), curBlockPos.getZ() + dismountDirOffset[1]);
					double dismountPosBlockFloorHeight = level.getBlockFloorHeight(potentialDismountPos);
					
					if (TransportationHelper.isBlockFloorValid(dismountPosBlockFloorHeight)) {
						Vector3d centeredDismountPos = Vector3d.upFromBottomCenterOf(potentialDismountPos, dismountPosBlockFloorHeight);
						
						if (TransportationHelper.canDismountTo(level, pLivingEntity, poseAabb.move(centeredDismountPos))) {
							pLivingEntity.setPose(passengerPose);
							return centeredDismountPos;
						}
					}
				}
			}
			return super.getDismountLocationForPassenger(pLivingEntity);
		}
	}
	
	@Override
	public ActionResultType mobInteract(PlayerEntity pPlayer, Hand pHand) {
		boolean shouldStartRiding = !isFood(pPlayer.getItemInHand(pHand)) && isSaddled() && !isVehicle() && !pPlayer.isSecondaryUseActive();
		
		if (shouldStartRiding) {
			if (!this.level.isClientSide) pPlayer.startRiding(this);
			return ActionResultType.sidedSuccess(this.level.isClientSide);
		} else {
			ActionResultType superMobInteractResultType = super.mobInteract(pPlayer, pHand);
			
			if (!superMobInteractResultType.consumesAction()) {
				ItemStack heldItem = pPlayer.getItemInHand(pHand);
				return heldItem.getItem() == getSaddleItem() ? heldItem.interactLivingEntity(pPlayer, this, pHand) : ActionResultType.PASS;
			} else return superMobInteractResultType;
		}
	}

	@Override
	public void travel(Vector3d pTravelVector) {
		this.travel(this, steeringBoostHelper, pTravelVector);
	}

	@Override
	public void travelWithInput(Vector3d pTravelVec) {
		super.travel(pTravelVec);
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}