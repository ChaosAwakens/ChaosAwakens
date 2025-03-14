package io.github.chaosawakens.common.item.mob;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class WrappedFoodOnAStickItem<EIS extends Entity & ItemSteerable> extends FoodOnAStickItem<EIS> {
    protected final Supplier<EntityType<EIS>> targetVehicleEntityType;
    protected final int consumeItemDamage;

    public WrappedFoodOnAStickItem(Properties properties, Supplier<EntityType<EIS>> targetVehicleEntityType, int consumeItemDamage) {
        super(properties, null, consumeItemDamage);

        this.targetVehicleEntityType = targetVehicleEntityType;
        this.consumeItemDamage = consumeItemDamage;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level curLevel, Player interactingOwnerPlayer, InteractionHand curHand) {
        ItemStack heldStack = interactingOwnerPlayer.getItemInHand(curHand);

        if (!curLevel.isClientSide()) {
            Entity curVehicle = interactingOwnerPlayer.getControlledVehicle();

            if (interactingOwnerPlayer.isPassenger() && curVehicle instanceof ItemSteerable steerableVehicleEntity) {
                if (curVehicle.getType() == targetVehicleEntityType.get() && steerableVehicleEntity.boost()) {
                    heldStack.hurtAndBreak(consumeItemDamage, interactingOwnerPlayer, (ownerPlayer) -> ownerPlayer.broadcastBreakEvent(curHand));

                    if (heldStack.isEmpty()) {
                        ItemStack fishingRodStack = Items.FISHING_ROD.getDefaultInstance();

                        fishingRodStack.setTag(heldStack.getTag());
                        return InteractionResultHolder.success(fishingRodStack);
                    }

                    return InteractionResultHolder.success(heldStack);
                }
            }

            interactingOwnerPlayer.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.pass(heldStack);
        }

        return InteractionResultHolder.pass(heldStack);
    }
}
