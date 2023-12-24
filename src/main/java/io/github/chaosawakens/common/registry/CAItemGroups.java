package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerBlock.DefossilizerType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;

import java.util.Random;

public class CAItemGroups {
	private static Random RANDOM = new Random();

	public static final ItemGroup BLOCKS = new ItemGroup("chaosawakens.blocks") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(CABlocks.TITANIUM_BLOCK.get());
		}
	};

	public static final ItemGroup ITEMS = new ItemGroup("chaosawakens.items") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(CAItems.URANIUM_INGOT.get());
		}
	};

	public static final ItemGroup FOOD = new ItemGroup("chaosawakens.food") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(CAItems.CORN.get());
		}
	};

	public static final ItemGroup EQUIPMENT = new ItemGroup("chaosawakens.equipment") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(CAItems.ULTIMATE_AXE.get());
		}
	};

	public static final ItemGroup SPAWN_EGGS = new ItemGroup("chaosawakens.spawn_eggs") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(CAItems.RAINBOW_ANT_SPAWN_EGG.get());
		}
	};

	public static final ItemGroup FOSSILS = new ItemGroup("chaosawakens.fossils") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(RANDOM.nextInt(2))).get());
		}
	};
	
	public static ItemGroup DEVELOPMENT = ChaosAwakens.isInDevEnv() ? new ItemGroup("chaosawakens.development") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(Items.COMMAND_BLOCK);
		}

		@Override
		public void fillItemList(NonNullList<ItemStack> items) {
			items.add(Items.SPAWNER.getDefaultInstance());
			items.add(Items.COMMAND_BLOCK.getDefaultInstance());
			items.add(Items.REPEATING_COMMAND_BLOCK.getDefaultInstance());
			items.add(Items.CHAIN_COMMAND_BLOCK.getDefaultInstance());
			items.add(Items.STRUCTURE_BLOCK.getDefaultInstance());
			items.add(Items.STRUCTURE_VOID.getDefaultInstance());
			items.add(Items.BARRIER.getDefaultInstance());
			items.add(Items.JIGSAW.getDefaultInstance());
			items.add(Items.DEBUG_STICK.getDefaultInstance());

			super.fillItemList(items);
		}
	} : null;
}
