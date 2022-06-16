package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.*;

public class CATags {
	public static class Blocks {
		public static ITag.INamedTag<Block> tag(String name) {
			return BlockTags.bind(ChaosAwakens.MODID + ":" + name);
		}

		public static final ITag.INamedTag<Block> BASE_STONE_CRYSTAL = tag("base_stone_crystal");
		public static final ITag.INamedTag<Block> BASE_STONE_MINING = tag("base_stone_mining");
		public static final ITag.INamedTag<Block> BASE_STONE_VILLAGE = tag("base_stone_village");

		public static final ITag.INamedTag<Block> MINERS_DREAM_MINEABLE = tag("miners_dream_mineable");

		public static final ITag.INamedTag<Block> APPLE_LOGS = tag("apple_logs");
		public static final ITag.INamedTag<Block> CHERRY_LOGS = tag("cherry_logs");
		public static final ITag.INamedTag<Block> DUPLICATION_LOGS = tag("duplication_logs");
		public static final ITag.INamedTag<Block> PEACH_LOGS = tag("peach_logs");
		public static final ITag.INamedTag<Block> SKYWOOD_LOGS = tag("skywood_logs");
		public static final ITag.INamedTag<Block> GINKGO_LOGS = tag("ginkgo_logs");
		public static final ITag.INamedTag<Block> CRYSTAL_LOGS = tag("crystal_logs");
		public static final ITag.INamedTag<Block> CRYSTAL_LEAVES = tag("crystal_leaves");
		public static final ITag.INamedTag<Block> CRYSTAL_SAPLING = tag("crystal_sapling");
		public static final ITag.INamedTag<Block> RUBY_ORES = tag("ruby_ores");

		public static final ITag.INamedTag<Block> WHITELIST = tag("whitelist");
		public static final ITag.INamedTag<Block> BLACKLIST = tag("blacklist");
	}

	public static class Items {
		public static ITag.INamedTag<Item> tag(String name) {
			return ItemTags.bind(ChaosAwakens.MODID + ":" + name);
		}

		public static final ITag.INamedTag<Item> APPLE_LOGS = tag("apple_logs");
		public static final ITag.INamedTag<Item> CHERRY_LOGS = tag("cherry_logs");
		public static final ITag.INamedTag<Item> DUPLICATION_LOGS = tag("duplication_logs");
		public static final ITag.INamedTag<Item> PEACH_LOGS = tag("peach_logs");
		public static final ITag.INamedTag<Item> SKYWOOD_LOGS = tag("skywood_logs");
		public static final ITag.INamedTag<Item> GINKGO_LOGS = tag("ginkgo_logs");
		public static final ITag.INamedTag<Item> CRYSTAL_LOGS = tag("crystal_logs");
		public static final ITag.INamedTag<Item> CRYSTAL_LEAVES = tag("crystal_leaves");
		public static final ITag.INamedTag<Item> CRYSTAL_SAPLING = tag("crystal_sapling");
		public static final ITag.INamedTag<Item> ULTIMATE_GEAR_HANDLES = tag("ultimate_gear_handles");
		public static final ITag.INamedTag<Item> FISH = tag("fish");
		public static final ITag.INamedTag<Item> GEMSTONES = tag("gemstones");
		public static final ITag.INamedTag<Item> NUGGETS = tag("nuggets");
		public static final ITag.INamedTag<Item> INGOTS = tag("ingots");
		public static final ITag.INamedTag<Item> MINERAL_LUMPS = tag("mineral_lumps");
		public static final ITag.INamedTag<Item> PLANTS = tag("plants");
		public static final ITag.INamedTag<Item> RUBY_ORES = tag("ruby_ores");
		public static final ITag.INamedTag<Item> SEEDS = tag("seeds");

		public static final ITag.INamedTag<Item> CUSTOM_TOOLTIPS = tag("custom_tooltips");
	}

	public static class EntityTypes {
		public static ITag.INamedTag<EntityType<?>> tag(String name) {
			return EntityTypeTags.bind(ChaosAwakens.MODID + ":" + name);
		}

		public static final ITag.INamedTag<EntityType<?>> CRITTER_CAGE_BLACKLISTED = tag("critter_cage_blacklisted");
	}

	public static class Fluids {
		public static ITag.INamedTag<Fluid> tag(String name) {
			return FluidTags.bind(ChaosAwakens.MODID + ":" + name);
		}
	}
}
