package io.github.chaosawakens.data;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;

public class CABlockLootTables extends BlockLootTables {
	@Override
	protected void addTables() {
		registerLootTable(CABlocks.AMETHYST_ORE.get(), (ore) -> droppingItemWithFortune(ore, CAItems.AMETHYST.get()));
		registerLootTable(CABlocks.ATTACHED_GOLDEN_MELON_STEM.get(), (seeds) -> dropping(CAItems.GOLDEN_MELON_SEEDS.get()));
		registerLootTable(CABlocks.BUDDING_CATS_EYE.get(), (block) -> dropping(CABlocks.KYANITE.get()));
		registerLootTable(CABlocks.BUDDING_PINK_TOURMALINE.get(), (block) -> dropping(CABlocks.KYANITE.get()));
		registerLootTable(CABlocks.CRYSTAL_GRASS_BLOCK.get(), (block) -> dropping(CABlocks.KYANITE.get()));
		registerLootTable(CABlocks.CRYSTAL_TORCH.get(), (block) -> dropping(CABlocks.CRYSTAL_TORCH.get()));
		registerLootTable(CABlocks.SUNSTONE_TORCH.get(), (block) -> dropping(CABlocks.SUNSTONE_TORCH.get()));
		registerLootTable(CABlocks.EXTREME_TORCH.get(), (block) -> dropping(CABlocks.EXTREME_TORCH.get()));
		registerLootTable(CABlocks.GOLDEN_MELON.get(), (food) -> randomDropping(CAItems.GOLDEN_MELON_SLICE.get(), 3, 7));
		registerLootTable(CABlocks.GOLDEN_MELON_STEM.get(), (seeds) -> dropping(CAItems.GOLDEN_MELON_SEEDS.get()));
		registerLootTable(CABlocks.RUBY_ORE.get(), (ore) -> droppingItemWithFortune(ore, CAItems.RUBY.get()));
		registerLootTable(CABlocks.SALT_ORE.get(), (ore) -> randomDropping(CAItems.SALT.get(), 4, 8));
		registerLootTable(CABlocks.TIGERS_EYE_ORE.get(), (ore) -> droppingItemWithFortune(ore, CAItems.TIGERS_EYE.get()));
		registerLootTable(CABlocks.SUNSTONE_ORE.get(), (ore) -> droppingItemWithFortune(ore, CAItems.SUNSTONE.get()));
		registerLootTable(CABlocks.BLOODSTONE_ORE.get(), (ore) -> droppingItemWithFortune(ore, CAItems.BLOODSTONE.get()));
		
		registerDropSelfLootTable(CABlocks.ALUMINUM_ORE.get());
		registerDropSelfLootTable(CABlocks.ALUMINUM_BLOCK.get());
		registerDropSelfLootTable(CABlocks.AMETHYST_BLOCK.get());
		registerDropSelfLootTable(CABlocks.BROWN_ANT_NEST.get());
		registerDropSelfLootTable(CABlocks.CATS_EYE_BLOCK.get());
		registerDropSelfLootTable(CABlocks.CATS_EYE_CLUSTER.get());
		registerDropSelfLootTable(CABlocks.COPPER_BLOCK.get());
		registerDropSelfLootTable(CABlocks.COPPER_ORE.get());
		registerDropSelfLootTable(CABlocks.CRYSTAL_CRAFTING_TABLE.get());
		registerDropSelfLootTable(CABlocks.CRYSTAL_ENERGY.get());
		registerDropSelfLootTable(CABlocks.CRYSTAL_FURNACE.get());
		registerDropSelfLootTable(CABlocks.CRYSTAL_LOG.get());
		registerDropSelfLootTable(CABlocks.CRYSTAL_WOOD.get());
		registerDropSelfLootTable(CABlocks.CRYSTAL_WOOD_PLANKS.get());
		registerDropSelfLootTable(CABlocks.FOSSILISED_EMERALD_GATOR.get());
		registerDropSelfLootTable(CABlocks.FOSSILISED_ENT.get());
		registerDropSelfLootTable(CABlocks.FOSSILISED_HERCULES_BEETLE.get());
		registerDropSelfLootTable(CABlocks.FOSSILISED_RUBY_BUG.get());
		registerDropSelfLootTable(CABlocks.GATE_BLOCK.get());
		registerDropSelfLootTable(CABlocks.GREEN_CRYSTAL_LEAVES.get());
		registerDropSelfLootTable(CABlocks.KYANITE.get());
		registerDropSelfLootTable(CABlocks.PINK_TOURMALINE_BLOCK.get());
		registerDropSelfLootTable(CABlocks.PINK_TOURMALINE_CLUSTER.get());
		registerDropSelfLootTable(CABlocks.PLATINUM_BLOCK.get());
		registerDropSelfLootTable(CABlocks.PLATINUM_ORE.get());
		registerDropSelfLootTable(CABlocks.RAINBOW_ANT_NEST.get());
		registerDropSelfLootTable(CABlocks.RED_ANT_NEST.get());
		registerDropSelfLootTable(CABlocks.RED_CRYSTAL_LEAVES.get());
		registerDropSelfLootTable(CABlocks.RUBY_BLOCK.get());
		registerDropSelfLootTable(CABlocks.SILVER_BLOCK.get());
		registerDropSelfLootTable(CABlocks.SILVER_ORE.get());
		registerDropSelfLootTable(CABlocks.TERMITE_NEST.get());
		registerDropSelfLootTable(CABlocks.TIGERS_EYE_BLOCK.get());
		registerDropSelfLootTable(CABlocks.TIN_BLOCK.get());
		registerDropSelfLootTable(CABlocks.TIN_ORE.get());
		registerDropSelfLootTable(CABlocks.TITANIUM_BLOCK.get());
		registerDropSelfLootTable(CABlocks.TITANIUM_ORE.get());
		registerDropSelfLootTable(CABlocks.UNSTABLE_ANT_NEST.get());
		registerDropSelfLootTable(CABlocks.URANIUM_BLOCK.get());
		registerDropSelfLootTable(CABlocks.URANIUM_ORE.get());
		registerDropSelfLootTable(CABlocks.YELLOW_CRYSTAL_LEAVES.get());
		registerDropSelfLootTable(CABlocks.SUNSTONE_BLOCK.get());
		registerDropSelfLootTable(CABlocks.BLOODSTONE_BLOCK.get());
	}
	
	protected static LootTable.Builder randomDropping(IItemProvider item, float random1, float random2) {
		return LootTable.builder().addLootPool(withSurvivesExplosion(item, LootPool.builder().rolls(RandomValueRange.of(random1, random2))).addEntry(ItemLootEntry.builder(item)));
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return CABlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
	}
}