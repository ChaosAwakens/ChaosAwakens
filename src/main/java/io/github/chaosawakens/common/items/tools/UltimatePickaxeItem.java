package io.github.chaosawakens.common.items.tools;

import io.github.chaosawakens.common.items.base.EnchantedPickaxeItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.List;
import java.util.function.Supplier;

public class UltimatePickaxeItem extends EnchantedPickaxeItem {

	public UltimatePickaxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pProperties, enchantments);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (!CAConfigManager.MAIN_CLIENT.enableTooltips.get()) return;
		super.appendHoverText(stack, world, tooltip, flag);
		
		tooltip.add(new StringTextComponent("Tool Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Autosmelt").withStyle(TextFormatting.BLUE)).append(new StringTextComponent(" (...)").withStyle(TextFormatting.GREEN)));
		
		if (Screen.hasShiftDown() || Screen.hasControlDown()) {
			tooltip.removeIf((s) -> s.toString().contains("(...)"));
			tooltip.add(new StringTextComponent("Tool Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("Autosmelt").withStyle(TextFormatting.BLUE))
					.append(new StringTextComponent("\nWhen crouching, any blocks broken by the Ultimate Pickaxe will be smelted (where applicable). Fortune modifiers also apply to the resulting items.").withStyle(TextFormatting.GREEN)));
		}
		
		if (!CAConfigManager.MAIN_COMMON.enableUltimatePickaxeBonus.get()) {
			tooltip.add(new StringTextComponent("This tool bonus is disabled in the config!").withStyle(TextFormatting.RED).withStyle(TextFormatting.BOLD));
		}
	}
}
