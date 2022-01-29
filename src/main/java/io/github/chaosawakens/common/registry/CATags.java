package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

/**
 * Class dedicated to tag wrapper constants, so they can be referenced somewhere
 * else
 * @author invalid2
 */
public class CATags {

	public static class Blocks {
		public static final ITag.INamedTag<Block> BASE_STONE_CRYSTAL = BlockTags.bind(ChaosAwakens.MODID + ":base_stone_crystal");
		public static final ITag.INamedTag<Block> MINERS_DREAM_MINEABLE = BlockTags.bind(ChaosAwakens.MODID + ":miners_dream_mineable");
		public static final ITag.INamedTag<Block> APPLE_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":apple_logs");
		public static final ITag.INamedTag<Block> CHERRY_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":cherry_logs");
		public static final ITag.INamedTag<Block> DUPLICATION_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":duplication_logs");
		public static final ITag.INamedTag<Block> PEACH_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":peach_logs");
		public static final ITag.INamedTag<Block> SKYWOOD_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":skywood_logs");

		public static final ITag<Block> whitelist = getBlockTagWrapper("whitelist");
		public static final ITag<Block> blacklist = getBlockTagWrapper("blacklist");
	}

	public static class Items {
		public static final ITag.INamedTag<Item> APPLE_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":apple_logs");
		public static final ITag.INamedTag<Item> CHERRY_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":cherry_logs");
		public static final ITag.INamedTag<Item> DUPLICATION_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":duplication_logs");
		public static final ITag.INamedTag<Item> PEACH_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":peach_logs");
		public static final ITag.INamedTag<Item> SKYWOOD_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":skywood_logs");
		public static final ITag<Item> CUSTOM_TOOLTIPS = ItemTags.bind(ChaosAwakens.MODID + ":custom_tooltips");
	}

	public static ITag<Block> getBlockTagWrapper(String path) {
		return BlockTags.bind(ChaosAwakens.MODID + ":" + path);
	}
}
