package io.github.chaosawakens.content.client.slot.defossilizer;

import io.github.chaosawakens.content.block_entity.defossilizer.AbstractDefossilizerBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DefossilizerResultSlot extends Slot {
    protected final Player ownerPlayer;
    protected int removedItemCount = 0;

    public DefossilizerResultSlot(Container container, int slot, int x, int y, Player ownerPlayer) {
        super(container, slot, x, y);

        this.ownerPlayer = ownerPlayer;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public @NotNull ItemStack remove(int amount) {
        if (hasItem()) this.removedItemCount += Math.min(amount, getItem().getCount());

        return super.remove(amount);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        checkTakeAchievements(stack);

        super.onTake(player, stack);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removedItemCount += amount;

        checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(ownerPlayer.level(), ownerPlayer, removedItemCount);

        if (ownerPlayer instanceof ServerPlayer ownerServerPlayer && container instanceof AbstractDefossilizerBlockEntity targetDefossilizerBlockEntity) {
            targetDefossilizerBlockEntity.awardUsedRecipesAndPopExperience(ownerServerPlayer);
        }

        this.removedItemCount = 0;
    }
}
