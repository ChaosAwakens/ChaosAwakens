package io.github.chaosawakens.common.entity.prototype.base;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class AnimatablePanickingSaddleableAnimal extends AnimatableSaddleableAnimal implements Panickable {
    private static final EntityDataAccessor<Boolean> IS_PANICKING = SynchedEntityData.defineId(AnimatablePanickingSaddleableAnimal.class, EntityDataSerializers.BOOLEAN);

    protected AnimatablePanickingSaddleableAnimal(EntityType<? extends AnimatableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(IS_PANICKING, false);
    }

    @Override
    public boolean isPanicking() {
        return this.entityData.get(IS_PANICKING);
    }

    @Override
    public void setPanicking(boolean panicking) {
        this.entityData.set(IS_PANICKING, panicking);
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();

        setPanicking(false);
    }

    @Override
    public InteractionResult mobInteract(Player interactingPlayer, InteractionHand curHand) {
        boolean isValidFood = isFood(interactingPlayer.getItemInHand(curHand));

        if (!isValidFood && isSaddled() && !isVehicle() && !interactingPlayer.isSecondaryUseActive()) {
            if (!level().isClientSide) interactingPlayer.startRiding(this);

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            InteractionResult defaultInteractionResult = super.mobInteract(interactingPlayer, curHand);

            if (!defaultInteractionResult.consumesAction()) {
                ItemStack heldStack = interactingPlayer.getItemInHand(curHand);

                if (heldStack.is(Items.SADDLE)) {
                    setPanicking(false);

                    return heldStack.interactLivingEntity(interactingPlayer, this, curHand);
                } else return InteractionResult.PASS;
            } else return defaultInteractionResult;
        }
    }
}
