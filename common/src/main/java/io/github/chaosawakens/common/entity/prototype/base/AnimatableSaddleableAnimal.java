package io.github.chaosawakens.common.entity.prototype.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AnimatableSaddleableAnimal extends AnimatableAnimal implements ItemSteerable, Saddleable {
    private static final EntityDataAccessor<Boolean> IS_SADDLED = SynchedEntityData.defineId(AnimatableSaddleableAnimal.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> BOOST_TIME = SynchedEntityData.defineId(AnimatableSaddleableAnimal.class, EntityDataSerializers.INT);
    private final ItemBasedSteering itemBasedSteeringControl = new ItemBasedSteering(entityData, BOOST_TIME, IS_SADDLED);

    protected AnimatableSaddleableAnimal(EntityType<? extends AnimatableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(IS_SADDLED, false);
        this.entityData.define(BOOST_TIME, 0);
    }

    @Override
    public boolean isSaddled() {
        return itemBasedSteeringControl.hasSaddle();
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(IS_SADDLED, saddled);
    }

    public int getBoostTime() {
        return this.entityData.get(BOOST_TIME);
    }

    public void setBoostTime(int boostTime) {
        this.entityData.set(BOOST_TIME, boostTime);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> curKey) {
        if (BOOST_TIME.equals(curKey) && level().isClientSide) itemBasedSteeringControl.onSynced();

        super.onSyncedDataUpdated(curKey);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag curTag) {
        super.addAdditionalSaveData(curTag);

        this.itemBasedSteeringControl.addAdditionalSaveData(curTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag curTag) {
        super.readAdditionalSaveData(curTag);

        this.itemBasedSteeringControl.readAdditionalSaveData(curTag);
    }

    public abstract Ingredient getValidControllerIngredient();

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        if (isSaddled()) {
            Entity controllingPassenger = getFirstPassenger();

            if (controllingPassenger instanceof Player controllingPlayerPassenger) {
                if (getValidControllerIngredient() != null && !getValidControllerIngredient().isEmpty() && (getValidControllerIngredient().test(controllingPlayerPassenger.getMainHandItem()) || getValidControllerIngredient().test(controllingPlayerPassenger.getOffhandItem()))) {
                    return controllingPlayerPassenger;
                }
            }
        }

        return null;
    }

    @Override
    public boolean boost() {
        return itemBasedSteeringControl.boost(random);
    }

    @Override
    public boolean isSaddleable() {
        return isAlive() && !isBaby();
    }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {
        itemBasedSteeringControl.setSaddle(true);

        if (soundSource != null) level().playSound(null, this, SoundEvents.PIG_SADDLE, soundSource, 0.5F, 1.0F);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();

        if (isSaddled()) spawnAtLocation(Items.SADDLE);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player interactingPlayer, InteractionHand curHand) {
        boolean isValidFood = isFood(interactingPlayer.getItemInHand(curHand));

        if (!isValidFood && isSaddled() && !isVehicle() && !interactingPlayer.isSecondaryUseActive()) {
            if (!level().isClientSide) interactingPlayer.startRiding(this);

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            InteractionResult defaultInteractionResult = super.mobInteract(interactingPlayer, curHand);

            if (!defaultInteractionResult.consumesAction()) {
                ItemStack heldStack = interactingPlayer.getItemInHand(curHand);

                return heldStack.is(Items.SADDLE) ? heldStack.interactLivingEntity(interactingPlayer, this, curHand) :InteractionResult.PASS;
            } else return defaultInteractionResult;
        }
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(LivingEntity targetPassenger) {
        Direction curMovementDir = getMotionDirection();

        if (curMovementDir.getAxis() != Direction.Axis.Y) {
            int[][] dismountStepOffsets = DismountHelper.offsetsForDirection(curMovementDir);
            BlockPos curPos = blockPosition();
            BlockPos.MutableBlockPos potentialDismountPos = new BlockPos.MutableBlockPos();

            for (Pose curDismountPose : targetPassenger.getDismountPoses()) {
                AABB aabb = targetPassenger.getLocalBoundsForPose(curDismountPose);

                for (int[] dismountAxisPos : dismountStepOffsets) {
                    potentialDismountPos.set(curPos.getX() + dismountAxisPos[0], curPos.getY(), curPos.getZ() + dismountAxisPos[1]);

                    double dismountPosBlockHeight = level().getBlockFloorHeight(potentialDismountPos);

                    if (DismountHelper.isBlockFloorValid(dismountPosBlockHeight)) {
                        Vec3 centeredDismountPos = Vec3.upFromBottomCenterOf(potentialDismountPos, dismountPosBlockHeight);

                        if (DismountHelper.canDismountTo(this.level(), targetPassenger, aabb.move(centeredDismountPos))) {
                            targetPassenger.setPose(curDismountPose);
                            return centeredDismountPos;
                        }
                    }
                }
            }
        }

        return super.getDismountLocationForPassenger(targetPassenger);
    }

    @Override
    protected void tickRidden(Player pPlayer, Vec3 pTravelVector) {
        super.tickRidden(pPlayer, pTravelVector);

        setRot(pPlayer.getYRot(), pPlayer.getXRot() * 0.5F);

        this.yRotO = yBodyRot = yHeadRot = getYRot();
        this.itemBasedSteeringControl.tickBoost();
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(Player ridingPlayer, Vec3 travelVector) {
        return new Vec3(0.0D, 0.0D, 1.0D);
    }

    @Override
    protected float getRiddenSpeed(Player pPlayer) {
        return (float) (getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.225D * itemBasedSteeringControl.boostFactor());
    }
}
