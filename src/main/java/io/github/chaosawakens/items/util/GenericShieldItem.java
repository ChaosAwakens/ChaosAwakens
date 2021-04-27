package io.github.chaosawakens.items.util;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class GenericShieldItem extends ShieldItem {
    private Item repairIngredient;
    public GenericShieldItem(Settings settings, Item repairIngredient) {
        super(settings);
        this.repairIngredient = repairIngredient;
    }

    @Override
    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem() == repairIngredient ? true : false;
    }
}
