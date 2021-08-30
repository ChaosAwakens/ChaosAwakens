package io.github.chaosawakens.data;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;

public class CABlockLootTables extends BlockLootTables {

	@Override
	protected void addTables() {
		add(CABlocks.AMETHYST_ORE.get(), (ore) -> createOreDrop(ore, CAItems.AMETHYST.get()));
		add(CABlocks.ATTACHED_GOLDEN_MELON_STEM.get(), (seeds) -> createSingleItemTable(CAItems.GOLDEN_MELON_SEEDS.get()));
		add(CABlocks.BLOODSTONE_ORE.get(), (ore) -> createOreDrop(ore, CAItems.BLOODSTONE.get()));
		add(CABlocks.BUDDING_CATS_EYE.get(), (block) -> createSingleItemTable(CABlocks.KYANITE.get()));
		add(CABlocks.BUDDING_PINK_TOURMALINE.get(), (block) -> createSingleItemTable(CABlocks.KYANITE.get()));
		add(CABlocks.CRYSTAL_GRASS_BLOCK.get(), (block) -> createSingleItemTable(CABlocks.KYANITE.get()));
		add(CABlocks.CRYSTAL_TORCH.get(), (block) -> createSingleItemTable(CABlocks.CRYSTAL_TORCH.get()));
		add(CABlocks.SUNSTONE_TORCH.get(), (block) -> createSingleItemTable(CABlocks.SUNSTONE_TORCH.get()));
		add(CABlocks.EXTREME_TORCH.get(), (block) -> createSingleItemTable(CABlocks.EXTREME_TORCH.get()));
		add(CABlocks.GOLDEN_MELON.get(), (food) -> randomDropping(CAItems.GOLDEN_MELON_SLICE.get(), 3, 7));
		add(CABlocks.GOLDEN_MELON_STEM.get(), (seeds) -> createSingleItemTable(CAItems.GOLDEN_MELON_SEEDS.get()));
		add(CABlocks.RUBY_ORE.get(), (ore) -> createOreDrop(ore, CAItems.RUBY.get()));
		add(CABlocks.NETHER_RUBY_ORE.get(), (ore) -> createOreDrop(ore, CAItems.RUBY.get()));
		add(CABlocks.SALT_ORE.get(), (ore) -> randomDropping(CAItems.SALT.get(), 4, 8));
		add(CABlocks.SUNSTONE_ORE.get(), (ore) -> createOreDrop(ore, CAItems.SUNSTONE.get()));
		add(CABlocks.TIGERS_EYE_ORE.get(), (ore) -> createOreDrop(ore, CAItems.TIGERS_EYE.get()));

		//Plants
		add(CABlocks.TUBE_WORM.get(), (plant) -> createShearsOnlyDrop(CABlocks.TUBE_WORM.get()));
		add(CABlocks.CORN_PLANT.get(), (plant) -> createCropDrops(plant, CAItems.CORN.get(), CAItems.CORN_SEEDS.get(),
				BlockStateProperty.hasBlockStateProperties(plant).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))));
		add(CABlocks.TOMATO_PLANT.get(), (plant) -> createCropDrops(plant, CAItems.TOMATO.get(), CAItems.TOMATO_SEEDS.get(),
				BlockStateProperty.hasBlockStateProperties(plant).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))));
		add(CABlocks.STRAWBERRY_PLANT.get(), (plant) -> createCropDrops(plant, CAItems.STRAWBERRY.get(), CAItems.STRAWBERRY_SEEDS.get(),
				BlockStateProperty.hasBlockStateProperties(plant).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))));

		dropOther(CABlocks.MOULDY_PLANKS.get(), Items.AIR);
		dropOther(CABlocks.MOULDY_SLAB.get(), Items.AIR);
		dropOther(CABlocks.MOULDY_FENCE.get(), Items.AIR);
		dropOther(CABlocks.PEACH_LEAVES.get(), Items.AIR);
		dropOther(CABlocks.CHERRY_LEAVES.get(), Items.AIR);
		dropOther(CABlocks.DUPLICATION_LOG.get(), CABlocks.DEAD_DUPLICATION_LOG.get());
		dropOther(CABlocks.DUPLICATION_LEAVES.get(), Items.AIR);

		dropSelf(CABlocks.APPLE_LOG.get());
		dropSelf(CABlocks.APPLE_PLANKS.get());
		dropSelf(CABlocks.ALUMINUM_ORE.get());
		dropSelf(CABlocks.ALUMINUM_BLOCK.get());
		dropSelf(CABlocks.AMETHYST_BLOCK.get());
		dropSelf(CABlocks.BLOODSTONE_BLOCK.get());
		dropSelf(CABlocks.BROWN_ANT_NEST.get());
		dropSelf(CABlocks.CATS_EYE_BLOCK.get());
		dropSelf(CABlocks.CATS_EYE_CLUSTER.get());
		dropSelf(CABlocks.CHERRY_LOG.get());
		dropSelf(CABlocks.CHERRY_PLANKS.get());
		dropSelf(CABlocks.COPPER_BLOCK.get());
		dropSelf(CABlocks.COPPER_ORE.get());
		dropSelf(CABlocks.CRYSTAL_CRAFTING_TABLE.get());
		dropSelf(CABlocks.CRYSTAL_ENERGY.get());
		dropSelf(CABlocks.CRYSTAL_FURNACE.get());
		dropSelf(CABlocks.CRYSTAL_LOG.get());
		dropSelf(CABlocks.CRYSTAL_TERMITE_NEST.get());
		dropSelf(CABlocks.CRYSTAL_WOOD.get());
		dropSelf(CABlocks.CRYSTAL_WOOD_PLANKS.get());
		dropSelf(CABlocks.DEAD_DUPLICATION_LOG.get());
		dropSelf(CABlocks.DUPLICATION_PLANKS.get());
		dropSelf(CABlocks.ENDER_EYE_BLOCK.get());
		dropSelf(CABlocks.ENDER_PEARL_BLOCK.get());
		dropSelf(CABlocks.FOSSILISED_EMERALD_GATOR.get());
		dropSelf(CABlocks.FOSSILISED_ENT.get());
		dropSelf(CABlocks.FOSSILISED_HERCULES_BEETLE.get());
		dropSelf(CABlocks.FOSSILISED_RUBY_BUG.get());
		dropSelf(CABlocks.FOSSILISED_WTF.get());
		dropSelf(CABlocks.FOSSILISED_SCORPION.get());
		dropSelf(CABlocks.FOSSILISED_WASP.get());
		dropSelf(CABlocks.FOSSILISED_PIRAPORU.get());
		dropSelf(CABlocks.GATE_BLOCK.get());
		dropSelf(CABlocks.RANDOM_TELEPORT_BLOCK.get());
		dropSelf(CABlocks.GREEN_CRYSTAL_LEAVES.get());
		dropSelf(CABlocks.KYANITE.get());
		dropSelf(CABlocks.NEST_BLOCK.get());
		dropSelf(CABlocks.PINK_TOURMALINE_BLOCK.get());
		dropSelf(CABlocks.PINK_TOURMALINE_CLUSTER.get());
		dropSelf(CABlocks.PLATINUM_BLOCK.get());
		dropSelf(CABlocks.PLATINUM_ORE.get());
		dropSelf(CABlocks.PEACH_LOG.get());
		dropSelf(CABlocks.PEACH_PLANKS.get());
		dropSelf(CABlocks.RAINBOW_ANT_NEST.get());
		dropSelf(CABlocks.RED_ANT_NEST.get());
		dropSelf(CABlocks.RED_CRYSTAL_LEAVES.get());
		dropSelf(CABlocks.RUBY_BLOCK.get());
		dropSelf(CABlocks.SILVER_BLOCK.get());
		dropSelf(CABlocks.SILVER_ORE.get());
		dropSelf(CABlocks.STRIPPED_APPLE_LOG.get());
		dropSelf(CABlocks.STRIPPED_CHERRY_LOG.get());
		dropSelf(CABlocks.STRIPPED_DUPLICATION_LOG.get());
		dropSelf(CABlocks.STRIPPED_PEACH_LOG.get());
		dropSelf(CABlocks.SUNSTONE_BLOCK.get());
		dropSelf(CABlocks.TERMITE_NEST.get());
		dropSelf(CABlocks.TIGERS_EYE_BLOCK.get());
		dropSelf(CABlocks.TIN_BLOCK.get());
		dropSelf(CABlocks.TIN_ORE.get());
		dropSelf(CABlocks.TITANIUM_BLOCK.get());
		dropSelf(CABlocks.TITANIUM_ORE.get());
		dropSelf(CABlocks.UNSTABLE_ANT_NEST.get());
		dropSelf(CABlocks.URANIUM_BLOCK.get());
		dropSelf(CABlocks.URANIUM_ORE.get());
		dropSelf(CABlocks.YELLOW_CRYSTAL_LEAVES.get());
		
		dropSelf(CABlocks.APPLE_LOG.get());
		dropSelf(CABlocks.CHERRY_LOG.get());
		dropSelf(CABlocks.PEACH_LOG.get());
		add(CABlocks.PEACH_LEAVES.get(), (plant) -> createShearsOnlyDrop(CABlocks.PEACH_LEAVES.get()));
		add(CABlocks.CHERRY_LEAVES.get(), (plant) -> createShearsOnlyDrop(CABlocks.CHERRY_LEAVES.get()));	
		dropSelf(CABlocks.PEACH_PLANKS.get());
		dropSelf(CABlocks.STRIPPED_PEACH_LOG.get());
		dropSelf(CABlocks.APPLE_LOG.get());
	}

	protected static LootTable.Builder randomDropping(IItemProvider item, float random1, float random2) {
		return LootTable.lootTable().withPool(applyExplosionCondition(item, LootPool.lootPool().setRolls(RandomValueRange.between(random1, random2))).add(ItemLootEntry.lootTableItem(item)));
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return CABlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
	}
}
