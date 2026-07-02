package io.github.chaosawakens.content.item.equipment.tools;

import io.github.chaosawakens.content.item.misc.EnchantedCrossbowItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import java.util.Map;

public class UltimateCrossbow extends EnchantedCrossbowItem {
    public UltimateCrossbow(Properties properties, Map<Enchantment, Integer> enchantments) {
        super(properties, enchantments);
    }

}
