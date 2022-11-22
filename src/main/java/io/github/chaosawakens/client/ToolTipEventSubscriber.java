package io.github.chaosawakens.client;

import java.util.Objects;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.config.CAClientConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.ClientLanguageMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ToolTipEventSubscriber {
	@SuppressWarnings("deprecation")
	public static void onToolTipEvent(ItemTooltipEvent event) {
		if (event.getPlayer() == null) return;
		if (CAClientConfig.CLIENT.enableTooltips.get()) {
			final ItemStack stack = event.getItemStack();
			final Item item = stack.getItem();	
			if (CAClientConfig.CLIENT.enableDamageTooltips.get()) {
				if (item.getMaxDamage() > 0) {
					event.getToolTip().add(new StringTextComponent("Durability: ").withStyle(TextFormatting.DARK_GREEN).append(new StringTextComponent((stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage()).withStyle(TextFormatting.YELLOW)));
				}
			}
			if (Objects.requireNonNull(item.getRegistryName()).getNamespace().equals(ChaosAwakens.MODID)) {
				if (ClientLanguageMap.getInstance().has("tooltip." + ChaosAwakens.MODID + "." + item.getRegistryName().getPath())) {
					if (Screen.hasShiftDown() || Screen.hasControlDown()) {
						event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + item.getRegistryName().getPath()).withStyle(CAClientConfig.CLIENT.toolTipColor.get()));
					} else {
						event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens.default").withStyle(CAClientConfig.CLIENT.toolTipColor.get()));
					}
				}
				for (int n = 2; n <= 15; n++) {
					if (ClientLanguageMap.getInstance().has("tooltip." + ChaosAwakens.MODID + "." + item.getRegistryName().getPath() + "." + n)) {
						if (Screen.hasShiftDown() || Screen.hasControlDown()) {
							event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + item.getRegistryName().getPath() + "." + n).withStyle(CAClientConfig.CLIENT.toolTipColor.get()));
						}
					}
				}
			} else {
				if (ClientLanguageMap.getInstance().has("tooltip." + ChaosAwakens.MODID + "." + item.getRegistryName().getNamespace() + "." + item.getRegistryName().getPath())) {
					if (Screen.hasShiftDown() || Screen.hasControlDown()) {
						event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + item.getRegistryName().getNamespace() + "." + item.getRegistryName().getPath()).withStyle(CAClientConfig.CLIENT.toolTipColor.get()));
					} else {
						event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens.default").withStyle(CAClientConfig.CLIENT.toolTipColor.get()));
					}
				}
				for (int n = 2; n <= 15; n++) {
					if (ClientLanguageMap.getInstance().has("tooltip." + ChaosAwakens.MODID + "." + item.getRegistryName().getNamespace() + "." + item.getRegistryName().getPath() + "." + n)) {
						if (Screen.hasShiftDown() || Screen.hasControlDown()) {
							event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + item.getRegistryName().getNamespace() + "." + item.getRegistryName().getPath() + "." + n).withStyle(CAClientConfig.CLIENT.toolTipColor.get()));
						}
					}
				}
			}
		}
	}
}
