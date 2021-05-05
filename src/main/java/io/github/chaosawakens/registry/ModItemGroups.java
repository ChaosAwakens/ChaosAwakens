package io.github.chaosawakens.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
	
	// ITEM GROUPS
	public static ItemGroup blocksItemGroup = new ItemGroup("chaosawakens_blocks_item_group") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModBlocks.TITANIUM_BLOCK.get());
		}
	};
	
	public static ItemGroup itemsItemGroup = new ItemGroup("chaosawakens_items_item_group") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.URANIUM_INGOT.get());
		}
	};

	public static ItemGroup foodItemGroup = new ItemGroup("chaosawakens_food_item_group") {

		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.CORN.get());
		}
	};
	
	public static ItemGroup equipmentItemGroup = new ItemGroup("chaosawakens_equipment_item_group") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.ULTIMATE_AXE.get());
		}
	};
	
	public static ItemGroup eggsItemGroup = new ItemGroup("chaosawakens_eggs_item_group") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.RAINBOW_ANT_SPAWN_EGG.get());
		}
	};
}
