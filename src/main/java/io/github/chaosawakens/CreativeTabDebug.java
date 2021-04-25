package io.github.chaosawakens;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Adds all items from the mod into one tab, for debug purposes only!
 * So do not forget to remove it in full releases
 * @author invalid2
 *
 */
public class CreativeTabDebug extends CreativeTabs {

	public CreativeTabDebug(String label) {
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.AMETHYST);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		for(Item item :ModItems.ITEMS) {
			item.getSubItems(CreativeTabs.SEARCH, items);
		}
	}

}
