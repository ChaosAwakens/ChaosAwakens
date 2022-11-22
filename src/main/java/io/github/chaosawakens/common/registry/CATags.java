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
		public static final ITag.INamedTag<Item> LEAF_CARPETS = tag("leaf_carpets");
		public static final ITag.INamedTag<Item> FOSSILS = tag("fossils");
		public static final ITag.INamedTag<Item> FOSSILS_STONE = tag("fossils/stone");
		public static final ITag.INamedTag<Item> FOSSILS_STONE_VANILLA = tag("fossils/stone/vanilla");
		public static final ITag.INamedTag<Item> FOSSILS_STONE_CHAOSAWAKENS = tag("fossils/stone/chaosawakens");
		public static final ITag.INamedTag<Item> FOSSILS_GRAVEL = tag("fossils/gravel");
		public static final ITag.INamedTag<Item> FOSSILS_GRAVEL_VANILLA = tag("fossils/gravel/vanilla");
		public static final ITag.INamedTag<Item> FOSSILS_GRAVEL_CHAOSAWAKENS = tag("fossils/gravel/chaosawakens");
		public static final ITag.INamedTag<Item> FOSSILS_SAND = tag("fossils/sand");
		public static final ITag.INamedTag<Item> FOSSILS_SAND_VANILLA = tag("fossils/sand/vanilla");
		public static final ITag.INamedTag<Item> FOSSILS_SAND_CHAOSAWAKENS = tag("fossils/sand/chaosawakens");
		public static final ITag.INamedTag<Item> FOSSILS_SANDSTONE = tag("fossils/sandstone");
		public static final ITag.INamedTag<Item> FOSSILS_SANDSTONE_VANILLA = tag("fossils/sandstone/vanilla");
		public static final ITag.INamedTag<Item> FOSSILS_SANDSTONE_CHAOSAWAKENS = tag("fossils/sandstone/chaosawakens");
		public static final ITag.INamedTag<Item> FOSSILS_ICE = tag("fossils/ice");
		public static final ITag.INamedTag<Item> FOSSILS_ICE_VANILLA = tag("fossils/ice/vanilla");
		public static final ITag.INamedTag<Item> FOSSILS_ICE_CHAOSAWAKENS = tag("fossils/ice/chaosawakens");
		public static final ITag.INamedTag<Item> FOSSILS_NETHERRACK = tag("fossils/netherrack");
		public static final ITag.INamedTag<Item> FOSSILS_NETHERRACK_VANILLA = tag("fossils/netherrack/vanilla");
		public static final ITag.INamedTag<Item> FOSSILS_NETHERRACK_CHAOSAWAKENS = tag("fossils/netherrack/chaosawakens");
		public static final ITag.INamedTag<Item> FOSSILS_BLACKSTONE = tag("fossils/blackstone");
		public static final ITag.INamedTag<Item> FOSSILS_BLACKSTONE_VANILLA = tag("fossils/blackstone/vanilla");
		public static final ITag.INamedTag<Item> FOSSILS_BLACKSTONE_CHAOSAWAKENS = tag("fossils/blackstone/chaosawakens");
		public static final ITag.INamedTag<Item> FOSSILS_SOUL_SOIL = tag("fossils/soul_soil");
		public static final ITag.INamedTag<Item> FOSSILS_SOUL_SOIL_VANILLA = tag("fossils/soul_soil/vanilla");
		public static final ITag.INamedTag<Item> FOSSILS_SOUL_SOIL_CHAOSAWAKENS = tag("fossils/soul_soil/chaosawakens");
		public static final ITag.INamedTag<Item> FOSSILS_END_STONE = tag("fossils/end_stone");
		public static final ITag.INamedTag<Item> FOSSILS_END_STONE_VANILLA = tag("fossils/end_stone/vanilla");
		public static final ITag.INamedTag<Item> FOSSILS_END_STONE_CHAOSAWAKENS = tag("fossils/end_stone/chaosawakens");
		public static final ITag.INamedTag<Item> FOSSILS_KYANITE = tag("fossils/kyanite");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_FOSSILS = tag("villager_trades/archaeologist/fossils");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_FOSSILS_COMMON = tag("villager_trades/archaeologist/fossils/common");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_FOSSILS_UNCOMMON = tag("villager_trades/archaeologist/fossils/uncommon");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_FOSSILS_RARE = tag("villager_trades/archaeologist/fossils/rare");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_FOSSILS_EPIC = tag("villager_trades/archaeologist/fossils/epic");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_SPAWN_EGGS = tag("villager_trades/archaeologist/spawn_eggs");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_SPAWN_EGGS_COMMON = tag("villager_trades/archaeologist/spawn_eggs/common");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_SPAWN_EGGS_UNCOMMON = tag("villager_trades/archaeologist/spawn_eggs/uncommon");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_SPAWN_EGGS_RARE = tag("villager_trades/archaeologist/spawn_eggs/rare");
		public static final ITag.INamedTag<Item> ARCHAEOLOGIST_SPAWN_EGGS_EPIC = tag("villager_trades/archaeologist/spawn_eggs/epic");
		public static final ITag.INamedTag<Item> SPAWNER_SPAWN_EGGS = tag("spawner_spawn_eggs");
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
