package io.github.chaosawakens.common.items;

import java.util.List;

import io.github.chaosawakens.client.config.CAClientConfig;
import io.github.chaosawakens.common.config.CACommonConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ExperienceSwordItem extends EnchantedSwordItem {

	public ExperienceSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
		if (!CAClientConfig.CLIENT.enableTooltips.get()) return;
		super.appendHoverText(stack, world, tooltip, flag);
		
		tooltip.add(new StringTextComponent("Weapon Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("XP Hunter").withStyle(TextFormatting.YELLOW)).append(new StringTextComponent(" (...)").withStyle(TextFormatting.GREEN)));
		
		if (Screen.hasShiftDown() || Screen.hasControlDown()) {
			tooltip.removeIf((s) -> s.toString().contains("(...)"));
			tooltip.add(new StringTextComponent("Weapon Bonus: ").withStyle(TextFormatting.GOLD).append(new StringTextComponent("XP Hunter").withStyle(TextFormatting.YELLOW))
					.append(new StringTextComponent("\nAll XP drops have a " + CACommonConfig.COMMON.experienceSwordXPMultiplier.get() + "X multiplier. Stacks with Experience Armor Set Bonus, for a total of a " + (CACommonConfig.COMMON.experienceSwordXPMultiplier.get() + CACommonConfig.COMMON.experienceArmorSetXPMultiplier.get()) + "X multiplier.").withStyle(TextFormatting.GREEN)));
		}
		
		if (!CACommonConfig.COMMON.enableExperienceSwordBonus.get()) {
			tooltip.add(new StringTextComponent("This weapon bonus is disabled in the config!").withStyle(TextFormatting.RED).withStyle(TextFormatting.BOLD));
		}
	}

}
