package io.github.chaosawakens.common.items.armor;

import java.util.List;

import io.github.chaosawakens.common.util.EnumUtil.CAArmorMaterial;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EmeraldArmorItem extends ArmorItem {

	public EmeraldArmorItem(CAArmorMaterial armorMaterial, EquipmentSlotType type, Properties properties) {
		super(armorMaterial, type, properties);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (!CAConfigManager.MAIN_CLIENT.enableTooltips.get()) return;
		super.appendHoverText(stack, world, tooltip, flag);
		tooltip.add(new StringTextComponent("Full Set Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Merchant's Aura").withStyle(TextFormatting.DARK_GREEN)).append(new StringTextComponent(" (...)").withStyle(TextFormatting.GREEN)));
		
		if (Screen.hasShiftDown() || Screen.hasControlDown()) {
			tooltip.removeIf((s) -> s.toString().contains("(...)"));
			tooltip.add(new StringTextComponent("Full Set Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Merchant's Aura").withStyle(TextFormatting.DARK_GREEN))
					.append(new StringTextComponent("\nVillagers give you a ~" + (CAConfigManager.MAIN_COMMON.emeraldArmorDiscountMultiplier.get() / 8.0D) * 100 + "% Discount on items. Note that the maximum discount you can get is up to 99%, and that you cannot get a real 100% discount on trades with villagers.").withStyle(TextFormatting.GREEN)));
		}
		
		if (!CAConfigManager.MAIN_COMMON.enableEmeraldArmorSetBonus.get()) {
			tooltip.add(new StringTextComponent("This full set bonus is disabled in the config!").withStyle(TextFormatting.RED).withStyle(TextFormatting.BOLD));
		}
	}

}
