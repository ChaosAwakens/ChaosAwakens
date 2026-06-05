package io.github.chaosawakens.content.item.food;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BowledFoodItem extends Item {
    public BowledFoodItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack targetStack, Level curLevel, LivingEntity livingOwner) {
        ItemStack resultStack = super.finishUsingItem(targetStack, curLevel, livingOwner);
        return livingOwner instanceof Player ownerPlayer && ownerPlayer.getAbilities().instabuild ? resultStack : new ItemStack(Items.BOWL);
    }
}
