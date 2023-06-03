package io.github.chaosawakens.common.items.weapons;

import java.util.List;
import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.EnchantedSwordItem;
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

public class ExperienceSwordItem extends EnchantedSwordItem {
	
	public ExperienceSwordItem(CAItemTier pTier, Supplier<IntValue> configDmg, double reach, double attackKnockback,Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, reach, attackKnockback, pProperties, enchantments);
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (!CAConfigManager.MAIN_CLIENT.enableTooltips.get()) return;
		super.appendHoverText(stack, world, tooltip, flag);
		
		tooltip.add(new StringTextComponent("Weapon Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("XP Hunter").withStyle(TextFormatting.YELLOW)).append(new StringTextComponent(" (...)").withStyle(TextFormatting.GREEN)));
		
		if (Screen.hasShiftDown() || Screen.hasControlDown()) {
			tooltip.removeIf((s) -> s.toString().contains("(...)"));
			tooltip.add(new StringTextComponent("Weapon Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("XP Hunter").withStyle(TextFormatting.YELLOW))
					.append(new StringTextComponent("\nAll XP drops have a " + CAConfigManager.MAIN_COMMON.experienceSwordXPMultiplier.get() + "X multiplier. Stacks with Experience Armor Set Bonus, for a total of a " + (CAConfigManager.MAIN_COMMON.experienceSwordXPMultiplier.get() + CAConfigManager.MAIN_COMMON.experienceArmorSetXPMultiplier.get()) + "X multiplier.").withStyle(TextFormatting.GREEN)));
		}
		
		if (!CAConfigManager.MAIN_COMMON.enableExperienceSwordBonus.get()) {
			tooltip.add(new StringTextComponent("This weapon bonus is disabled in the config!").withStyle(TextFormatting.RED).withStyle(TextFormatting.BOLD));
		}
	}

}
