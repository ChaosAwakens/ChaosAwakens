package io.github.chaosawakens.common.items;

import java.util.List;

import io.github.chaosawakens.client.config.CAClientConfig;
import io.github.chaosawakens.common.config.CACommonConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class LapisArmorItem extends EnchantedArmorItem {

	public LapisArmorItem(IArmorMaterial material, EquipmentSlotType type, Properties properties, EnchantmentData[] enchantments) {
		super(material, type, properties, enchantments);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (!CAClientConfig.CLIENT.enableTooltips.get()) return;
		super.appendHoverText(stack, world, tooltip, flag);
		
		tooltip.add(new StringTextComponent("Full Set Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Enchanter's Delight").withStyle(TextFormatting.BLUE)).append(new StringTextComponent(" (...)").withStyle(TextFormatting.GREEN)));
		
		if (Screen.hasShiftDown() || Screen.hasControlDown()) {
			tooltip.removeIf((s) -> s.toString().contains("(...)"));
			tooltip.add(new StringTextComponent("Full Set Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Enchanter's Delight").withStyle(TextFormatting.BLUE))
					.append(new StringTextComponent("\nEnchantment table level requirements are reduced (" + CACommonConfig.LAPISMODLIST.get(0) + " levels for the first row, " + CACommonConfig.LAPISMODLIST.get(1) + " levels for the second row, and " + CACommonConfig.LAPISMODLIST.get(2) + " levels for the third row respectively." + "). " + "In addition, the power of bookshelves is multiplied by " + CACommonConfig.COMMON.lapisArmorSetBookshelfPowerModifier.get() + ", reducing the amount of bookshelves required for max level to " + 16 / CACommonConfig.COMMON.lapisArmorSetBookshelfPowerModifier.get() + " bookshelves.").withStyle(TextFormatting.GREEN)));
		}
	
		if (!CACommonConfig.COMMON.enableLapisArmorSetBonus.get()) {
			tooltip.add(new StringTextComponent("This full set bonus is disabled in the config!").withStyle(TextFormatting.RED).withStyle(TextFormatting.BOLD));
		}
	}
	

}
