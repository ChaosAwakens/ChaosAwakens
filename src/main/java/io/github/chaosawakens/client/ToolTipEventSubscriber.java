package io.github.chaosawakens.client;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

/**
 * @author invalid2
 */
public class ToolTipEventSubscriber {
	// Not functional for now, idk why :(
/*	@SubscribeEvent(priority = EventPriority.HIGHEST)
	@OnlyIn(Dist.CLIENT)
	public static void itemToolTips(final ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		List<ITextComponent> tooltip = event.getToolTip();
		if (stack.getItem() instanceof BlockItem) {
			Block block = ((BlockItem) stack.getItem()).getBlock();
			if (block instanceof CAOreBlock) {
				if (stack.getItem() == CABlocks.ALUMINUM_ORE.get().asItem()) {
					tooltip.add(1, new TranslationTextComponent("tooltip.chaosawakens.aluminum_ore").withStyle(TextFormatting.AQUA));
				} else if (stack.getItem() == CABlocks.FOSSILISED_PIRAPORU.get().asItem().getItem()) {
					tooltip.add(1, new TranslationTextComponent("tooltip.chaosawakens.fossilised_piraporu").withStyle(TextFormatting.AQUA));
				} else if (stack.getItem() == CABlocks.FOSSILISED_SCORPION.get().asItem().getItem()) {
					tooltip.add(1, new TranslationTextComponent("tooltip.chaosawakens.fossilised_scorpion").withStyle(TextFormatting.AQUA));
				} else if (stack.getItem() == CABlocks.FOSSILISED_WTF.get().asItem().getItem()) {
					tooltip.add(1, new TranslationTextComponent("tooltip.chaosawakens.fossilised_wtf").withStyle(TextFormatting.AQUA));
					tooltip.add(new StringTextComponent("tooltip.chaosawakens.fossilised_wtf").withStyle(TextFormatting.AQUA));
				} else {
					tooltip.add(new TranslationTextComponent("tooltip.chaosawakens.default"));
				}
			}
		}
	}*/
	
	public static void onToolTipEvent(ItemTooltipEvent event) {
		if (CAConfig.COMMON.enableTooltips.get()) {
			final Item item = event.getItemStack().getItem();
			if (item.is(CATags.CUSTOM_TOOLTIPS)) {
				if(Screen.hasShiftDown() || Screen.hasControlDown())
					event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + item.getRegistryName().toString().replaceAll("chaosawakens:", "")));
				else
					event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens.default"));
			}
		}
	}
}
