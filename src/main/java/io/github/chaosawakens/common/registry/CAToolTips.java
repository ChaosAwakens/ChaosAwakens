package io.github.chaosawakens.common.registry;

import java.util.List;


import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid=ChaosAwakens.MODID, bus=EventBusSubscriber.Bus.FORGE)
public class CAToolTips{
	
	public void addToolTips(ItemStack stack, World worldIn, List<ITextComponent> list, ITooltipFlag toolFlag) {
		if(Screen.hasShiftDown() || Screen.hasControlDown()) {
			if(stack.getItem() == CAItems.ALUMINUM_INGOT.get()) {
				list.add(new TranslationTextComponent("tooltip.chaosawakens.aluminum_shift"));
			}
		}
		else {
			list.add(new TranslationTextComponent("tooltip.chaosawakens.default"));
		}
	}
}

