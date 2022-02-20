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
		public static final ITag.INamedTag<Block> BASE_STONE_MINING = BlockTags.bind(ChaosAwakens.MODID + ":base_stone_mining");
		public static final ITag.INamedTag<Block> BASE_STONE_VILLAGE = BlockTags.bind(ChaosAwakens.MODID + ":base_stone_village");

		public static final ITag.INamedTag<Block> MINERS_DREAM_MINEABLE = BlockTags.bind(ChaosAwakens.MODID + ":miners_dream_mineable");

		public static final ITag.INamedTag<Block> APPLE_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":apple_logs");
		public static final ITag.INamedTag<Block> CHERRY_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":cherry_logs");
		public static final ITag.INamedTag<Block> DUPLICATION_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":duplication_logs");
		public static final ITag.INamedTag<Block> PEACH_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":peach_logs");
		public static final ITag.INamedTag<Block> SKYWOOD_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":skywood_logs");
		public static final ITag.INamedTag<Block> CRYSTAL_LOGS = BlockTags.bind(ChaosAwakens.MODID + ":crystal_logs");
		public static final ITag.INamedTag<Block> CRYSTAL_LEAVES = BlockTags.bind(ChaosAwakens.MODID + ":crystal_leaves");
		public static final ITag.INamedTag<Block> CRYSTAL_SAPLING = BlockTags.bind(ChaosAwakens.MODID + ":crystal_sapling");
		public static final ITag.INamedTag<Block> RUBY_ORES = BlockTags.bind(ChaosAwakens.MODID + ":ruby_ores");

		public static final ITag.INamedTag<Block> WHITELIST = BlockTags.bind(ChaosAwakens.MODID + ":whitelist");
		public static final ITag.INamedTag<Block> BLACKLIST = BlockTags.bind(ChaosAwakens.MODID + ":blacklist");
	}

	public static class Items {
		public static final ITag.INamedTag<Item> APPLE_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":apple_logs");
		public static final ITag.INamedTag<Item> CHERRY_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":cherry_logs");
		public static final ITag.INamedTag<Item> DUPLICATION_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":duplication_logs");
		public static final ITag.INamedTag<Item> PEACH_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":peach_logs");
		public static final ITag.INamedTag<Item> SKYWOOD_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":skywood_logs");
		public static final ITag.INamedTag<Item> CRYSTAL_LOGS = ItemTags.bind(ChaosAwakens.MODID + ":crystal_logs");
		public static final ITag.INamedTag<Item> CRYSTAL_LEAVES = ItemTags.bind(ChaosAwakens.MODID + ":crystal_leaves");
		public static final ITag.INamedTag<Item> CRYSTAL_SAPLING = ItemTags.bind(ChaosAwakens.MODID + ":crystal_sapling");
		public static final ITag.INamedTag<Item> ULTIMATE_GEAR_HANDLES = ItemTags.bind(ChaosAwakens.MODID + ":ultimate_gear_handles");
		public static final ITag.INamedTag<Item> FISH = ItemTags.bind(ChaosAwakens.MODID + ":fish");
		public static final ITag.INamedTag<Item> GEMSTONES = ItemTags.bind(ChaosAwakens.MODID + ":gemstones");
		public static final ITag.INamedTag<Item> NUGGETS = ItemTags.bind(ChaosAwakens.MODID + ":nuggets");
		public static final ITag.INamedTag<Item> INGOTS = ItemTags.bind(ChaosAwakens.MODID + ":ingots");
		public static final ITag.INamedTag<Item> MINERAL_LUMPS = ItemTags.bind(ChaosAwakens.MODID + ":mineral_lumps");
		public static final ITag.INamedTag<Item> PLANTS = ItemTags.bind(ChaosAwakens.MODID + ":plants");
		public static final ITag.INamedTag<Item> RUBY_ORES = ItemTags.bind(ChaosAwakens.MODID + ":ruby_ores");
		public static final ITag.INamedTag<Item> SEEDS = ItemTags.bind(ChaosAwakens.MODID + ":seeds");

		public static final ITag.INamedTag<Item> CUSTOM_TOOLTIPS = ItemTags.bind(ChaosAwakens.MODID + ":custom_tooltips");
	}

	public static ITag<Block> getBlockTagWrapper(String path) {
		return BlockTags.bind(ChaosAwakens.MODID + ":" + path);
	}
}
