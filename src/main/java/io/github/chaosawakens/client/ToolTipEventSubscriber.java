package io.github.chaosawakens.client;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

/**
 * @author invalid2
 */
public class ToolTipEventSubscriber {
	public static void onToolTipEvent(ItemTooltipEvent event) {
		if(event.getPlayer() == null)return;
		if(CAConfig.COMMON.enableTooltips.get()) {
			final Item item = event.getItemStack().getItem();
			if (item.is(CATags.Items.CUSTOM_TOOLTIPS)) {
				if (Screen.hasShiftDown() || Screen.hasControlDown())
					event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + item.getRegistryName().toString().replaceAll("chaosawakens:", "")).withStyle(TextFormatting.AQUA));
				else
					event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens.default").withStyle(TextFormatting.AQUA));
			}
		}
	}
}
