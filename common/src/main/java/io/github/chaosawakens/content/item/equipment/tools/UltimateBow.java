package io.github.chaosawakens.content.item.equipment.tools;

import io.github.chaosawakens.content.item.misc.EnchantedBowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import java.util.Map;

public class UltimateBow extends EnchantedBowItem {

    public UltimateBow(Properties properties, Map<Enchantment, Integer> enchantments) {
        super(properties, enchantments);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 9000;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
    
    @Override
    public boolean isFoil(ItemStack stack){
        return true;
    }
}
