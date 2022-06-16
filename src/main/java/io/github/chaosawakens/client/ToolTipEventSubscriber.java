package io.github.chaosawakens.client;

import com.mojang.blaze3d.matrix.MatrixStack;

import io.github.chaosawakens.client.config.CAClientConfig;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.ITooltip;
import net.minecraft.item.Item;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ToolTipEventSubscriber {
	public static void onToolTipEvent(ItemTooltipEvent event) {
		if (event.getPlayer() == null) return;
		if (CAClientConfig.CLIENT.enableTooltips.get()) {
			final Item item = event.getItemStack().getItem();
			if (item.is(CATags.Items.CUSTOM_TOOLTIPS)) {
				if (Screen.hasShiftDown() || Screen.hasControlDown())
					event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens." + item.getRegistryName().toString().replaceAll("chaosawakens:", "")).withStyle(CAClientConfig.CLIENT.toolTipColor.get()));
				else
					event.getToolTip().add(new TranslationTextComponent("tooltip.chaosawakens.default").withStyle(CAClientConfig.CLIENT.toolTipColor.get()));
			}
		}
	}
	
	public class TooltipRenderHelper implements ITooltip {
		@Override
		public void onTooltip(Button button, MatrixStack stack, int width, int height) {	
			stack.pushPose();
			
			stack.popPose();
		}
	}
	
	public static void onPreTooltipRender(RenderTooltipEvent.Pre event) {
		
	}
	
	public static void onTooltipColorRender(RenderTooltipEvent.Color event) {
		
	}
}
