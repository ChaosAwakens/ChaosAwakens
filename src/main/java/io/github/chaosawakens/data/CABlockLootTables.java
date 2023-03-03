package io.github.chaosawakens.data;

import java.util.Objects;
import java.util.stream.Collectors;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.CACropsBlock;
import io.github.chaosawakens.common.blocks.CAFallingOreBlock;
import io.github.chaosawakens.common.blocks.CAOreBlock;
import io.github.chaosawakens.common.blocks.CrystalClusterBlock;
import io.github.chaosawakens.common.blocks.LeafCarpetBlock;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.TableBonus;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("all")
public class CABlockLootTables extends BlockLootTables {
	private static final ILootCondition.IBuilder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
	private static final ILootCondition.IBuilder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
	private static final ILootCondition.IBuilder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
	private static final ILootCondition.IBuilder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
	private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[] { 0.05F, 0.0625F, 0.083333336F, 0.1F };
	private static final float[] JUNGLE_LEAVES_SAPLING_CHANGES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
	
	@Override
	protected void addTables() {
		// Ores
		add(CABlocks.AMETHYST_ORE.get(), (ore) -> createOreDrop(ore, CAItems.AMETHYST.get()));
		add(CABlocks.BLOODSTONE_ORE.get(), (ore) -> createOreDrop(ore, CAItems.BLOODSTONE.get()));
		add(CABlocks.RUBY_ORE.get(), (ore) -> createOreDrop(ore, CAItems.RUBY.get()));
		add(CABlocks.NETHERRACK_RUBY_ORE.get(), (ore) -> createOreDrop(ore, CAItems.RUBY.get()));
		add(CABlocks.BLACKSTONE_RUBY_ORE.get(), (ore) -> createOreDrop(ore, CAItems.RUBY.get()));
		add(CABlocks.SUNSTONE_ORE.get(), (ore) -> createOreDrop(ore, CAItems.SUNSTONE.get()));
		add(CABlocks.TIGERS_EYE_ORE.get(), (ore) -> createOreDrop(ore, CAItems.TIGERS_EYE.get()));

		add(CABlocks.SALT_ORE.get(), (ore) -> randomDropping(CAItems.SALT.get(), 4, 8));

		add(CABlocks.COPPER_ORE.get(), (ore) -> createSilkTouchDispatchTable(ore, applyExplosionDecay(ore,
				ItemLootEntry.lootTableItem(CAItems.COPPER_LUMP.get())
						.apply(SetCount.setCount(RandomValueRange.between(2.0F, 5.0F)))
						.apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
		add(CABlocks.PLATINUM_ORE.get(), (ore) -> createOreDropNoFortune(ore, CAItems.PLATINUM_LUMP.get()));
		add(CABlocks.SILVER_ORE.get(), (ore) -> createOreDrop(ore, CAItems.SILVER_LUMP.get()));
		add(CABlocks.TIN_ORE.get(), (ore) -> createOreDrop(ore, CAItems.TIN_LUMP.get()));

		add(CABlocks.CATS_EYE_CLUSTER.get(), (ore) -> createCluster(ore, CAItems.CATS_EYE_SHARD.get()));
		add(CABlocks.PINK_TOURMALINE_CLUSTER.get(), (ore) -> createCluster(ore, CAItems.PINK_TOURMALINE_SHARD.get()));

		dropSelf(CABlocks.ALUMINUM_ORE.get());
		dropSelf(CABlocks.TITANIUM_ORE.get());
		dropSelf(CABlocks.URANIUM_ORE.get());
		
		add(CABlocks.APPLE_DOOR.get(), (block) -> createDoorDrop(CABlocks.APPLE_DOOR.get()));
		add(CABlocks.CHERRY_DOOR.get(), (block) -> createDoorDrop(CABlocks.CHERRY_DOOR.get()));
		add(CABlocks.DUPLICATION_DOOR.get(), (block) -> createDoorDrop(CABlocks.DUPLICATION_DOOR.get()));
		add(CABlocks.GINKGO_DOOR.get(), (block) -> createDoorDrop(CABlocks.GINKGO_DOOR.get()));
		add(CABlocks.MESOZOIC_DOOR.get(), (block) -> createDoorDrop(CABlocks.MESOZOIC_DOOR.get()));
		add(CABlocks.DENSEWOOD_DOOR.get(), (block) -> createDoorDrop(CABlocks.DENSEWOOD_DOOR.get()));
		add(CABlocks.PEACH_DOOR.get(), (block) -> createDoorDrop(CABlocks.PEACH_DOOR.get()));
		add(CABlocks.SKYWOOD_DOOR.get(), (block) -> createDoorDrop(CABlocks.SKYWOOD_DOOR.get()));
		
	//	dropSelf(CABlocks.ROCK.get());

		// Ore Blocks
		dropSelf(CABlocks.ALUMINUM_BLOCK.get());
		dropSelf(CABlocks.AMETHYST_BLOCK.get());
		dropSelf(CABlocks.BLOODSTONE_BLOCK.get());
		dropSelf(CABlocks.CATS_EYE_BLOCK.get());
		dropSelf(CABlocks.COPPER_BLOCK.get());
		dropSelf(CABlocks.PINK_TOURMALINE_BLOCK.get());
		dropSelf(CABlocks.PLATINUM_BLOCK.get());
		dropSelf(CABlocks.RUBY_BLOCK.get());
		dropSelf(CABlocks.SALT_BLOCK.get());
		dropSelf(CABlocks.SILVER_BLOCK.get());
		dropSelf(CABlocks.SUNSTONE_BLOCK.get());
		dropSelf(CABlocks.TIGERS_EYE_BLOCK.get());
		dropSelf(CABlocks.TIN_BLOCK.get());
		dropSelf(CABlocks.TITANIUM_BLOCK.get());
		dropSelf(CABlocks.URANIUM_BLOCK.get());

		// Plants
		add(CABlocks.TUBE_WORM.get(), (plant) -> createShearsOnlyDrop(CABlocks.TUBE_WORM.get()));

		add(CABlocks.CORN_TOP_BLOCK.get(), (plant) -> randomDropping(CAItems.CORN_SEEDS.get(), 1, 3));
		add(CABlocks.CORN_BODY_BLOCK.get(), (plant) -> cropBodyBlock(CAItems.CORN.get(), CAItems.CORN_SEEDS.get()));
		add(CABlocks.TOMATO_TOP_BLOCK.get(), (plant) -> randomDropping(CAItems.TOMATO_SEEDS.get(), 1, 3));
		add(CABlocks.TOMATO_BODY_BLOCK.get(), (plant) -> cropBodyBlock(CAItems.TOMATO.get(), CAItems.TOMATO_SEEDS.get()));
		add(CABlocks.STRAWBERRY_BUSH.get(), (plant) -> applyExplosionDecay(plant,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.when(BlockStateProperty.hasBlockStateProperties(CABlocks.STRAWBERRY_BUSH.get())
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(SweetBerryBushBlock.AGE, 3)))
								.add(ItemLootEntry.lootTableItem(CAItems.STRAWBERRY.get()))
								.apply(SetCount.setCount(RandomValueRange.between(2.0F, 3.0F)))
								.apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))
						.withPool(LootPool.lootPool()
								.when(BlockStateProperty.hasBlockStateProperties(CABlocks.STRAWBERRY_BUSH.get())
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(SweetBerryBushBlock.AGE, 2)))
								.add(ItemLootEntry.lootTableItem(CAItems.STRAWBERRY.get()))
								.apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))
								.apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
		ILootCondition.IBuilder radish = BlockStateProperty.hasBlockStateProperties(CABlocks.RADISH.get())
				.setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(CACropsBlock.AGE, 3));
		add(CABlocks.RADISH.get(), createCropDrops(CABlocks.RADISH.get(), CAItems.RADISH.get(), CAItems.RADISH_SEEDS.get(), radish));
		ILootCondition.IBuilder lettuce = BlockStateProperty.hasBlockStateProperties(CABlocks.LETTUCE.get())
				.setProperties(StatePropertiesPredicate.Builder.properties()
						.hasProperty(CACropsBlock.AGE, 3));
		add(CABlocks.LETTUCE.get(), createCropDrops(CABlocks.LETTUCE.get(), CAItems.LETTUCE.get(), CAItems.LETTUCE_SEEDS.get(), lettuce));

		// Wood Types/Leaves
		dropOther(CABlocks.DUPLICATION_LOG.get(), CABlocks.DEAD_DUPLICATION_LOG.get());
		dropOther(CABlocks.DUPLICATION_WOOD.get(), CABlocks.DEAD_DUPLICATION_WOOD.get());
		dropOther(CABlocks.STRIPPED_DUPLICATION_LOG.get(), CABlocks.STRIPPED_DEAD_DUPLICATION_LOG.get());
		dropOther(CABlocks.STRIPPED_DUPLICATION_WOOD.get(), CABlocks.STRIPPED_DEAD_DUPLICATION_WOOD.get());

		add(CABlocks.APPLE_LEAVES.get(), createFruitLeavesDrops(CABlocks.APPLE_LEAVES.get(), CABlocks.APPLE_SAPLING.get(), Items.APPLE, NORMAL_LEAVES_SAPLING_CHANCES));
		add(CABlocks.CHERRY_LEAVES.get(), createFruitLeavesDrops(CABlocks.CHERRY_LEAVES.get(), CABlocks.CHERRY_SAPLING.get(), CAItems.CHERRIES.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(CABlocks.DUPLICATION_LEAVES.get(), createSilkTouchOnlyTable(CABlocks.DUPLICATION_LEAVES.get()));
		add(CABlocks.GINKGO_LEAVES.get(), createLeavesDrops(CABlocks.GINKGO_LEAVES.get(), CABlocks.GINKGO_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(CABlocks.MESOZOIC_LEAVES.get(), createLeavesDrops(CABlocks.MESOZOIC_LEAVES.get(), CABlocks.MESOZOIC_SAPLING.get(), JUNGLE_LEAVES_SAPLING_CHANGES));
		add(CABlocks.DENSEWOOD_LEAVES.get(), createLeavesDrops(CABlocks.DENSEWOOD_LEAVES.get(), CABlocks.DENSEWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(CABlocks.PEACH_LEAVES.get(), createFruitLeavesDrops(CABlocks.PEACH_LEAVES.get(), CABlocks.PEACH_SAPLING.get(), CAItems.PEACH.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(CABlocks.SKYWOOD_LEAVES.get(), createSilkTouchOnlyTable(CABlocks.SKYWOOD_LEAVES.get()));

		// Ant/Termite Nests
		dropSelf(CABlocks.BROWN_ANT_NEST.get());
		dropSelf(CABlocks.RAINBOW_ANT_NEST.get());
		dropSelf(CABlocks.RED_ANT_NEST.get());
		dropSelf(CABlocks.TERMITE_NEST.get());
		dropSelf(CABlocks.UNSTABLE_ANT_NEST.get());

		// Dense Blocks
		add(CABlocks.DENSE_GRASS_BLOCK.get(), (block) -> createSingleItemTableWithSilkTouch(block, CABlocks.DENSE_DIRT.get()));
		dropSelf(CABlocks.DENSE_RED_ANT_NEST.get());
		dropSelf(CABlocks.DENSE_DIRT.get());
		add(CABlocks.DENSE_FARMLAND.get(), (block) -> createSingleItemTableWithSilkTouch(block, CABlocks.DENSE_DIRT.get()));
		add(CABlocks.DENSE_GRASS.get(), (plant) -> createShearsOnlyDrop(CABlocks.DENSE_GRASS.get()));
		add(CABlocks.TALL_DENSE_GRASS.get(), (plant) -> createShearsOnlyDrop(CABlocks.TALL_DENSE_GRASS.get()));
		add(CABlocks.CRYSTAL_GRASS.get(), (plant) -> createShearsOnlyDrop(CABlocks.CRYSTAL_GRASS.get()));
		add(CABlocks.TALL_CRYSTAL_GRASS.get(), (plant) -> createShearsOnlyDrop(CABlocks.TALL_CRYSTAL_GRASS.get()));
		add(CABlocks.THORNY_SUN.get(), (plant) -> createSinglePropConditionTable(plant, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		add(CABlocks.ALSTROEMERIAT.get(), (plant) -> createSinglePropConditionTable(plant, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
		add(CABlocks.SMALL_BUSH.get(), (plant) -> createShearsOnlyDrop(CABlocks.TALL_CRYSTAL_GRASS.get()));
		dropSelf(CABlocks.TERRA_PRETA.get());
		dropSelf(CABlocks.SMALL_CARNIVOROUS_PLANT.get());
		dropSelf(CABlocks.BIG_CARNIVOROUS_PLANT.get());
		add(CABlocks.TERRA_PRETA_FARMLAND.get(), (block) -> createSingleItemTableWithSilkTouch(block, CABlocks.TERRA_PRETA.get()));
		dropSelf(CABlocks.TAR.get());
		dropSelf(CABlocks.LATOSOL.get());
		dropSelf(CABlocks.DENSE_ORCHID.get());
		dropSelf(CABlocks.BLUE_BULB.get());
		dropSelf(CABlocks.PINK_BULB.get());
		dropSelf(CABlocks.PURPLE_BULB.get());
		add(CABlocks.MESOZOIC_VINES.get(), (plant) -> createShearsOnlyDrop(CABlocks.MESOZOIC_VINES.get()));
		add(CABlocks.MESOZOIC_VINES_PLANT.get(), (plant) -> createShearsOnlyDrop(CABlocks.MESOZOIC_VINES_PLANT.get()));

		// Limestone
		dropSelf(CABlocks.LIMESTONE.get());
		dropSelf(CABlocks.LIMESTONE_BRICKS.get());
		dropSelf(CABlocks.CHISELED_LIMESTONE_BRICKS.get());
		dropSelf(CABlocks.CRACKED_LIMESTONE_BRICKS.get());
		dropSelf(CABlocks.MOSSY_LIMESTONE_BRICKS.get());
		dropSelf(CABlocks.POLISHED_LIMESTONE.get());
		dropSelf(CABlocks.LIMESTONE_PILLAR.get());
		dropSelf(CABlocks.LIMESTONE_SLAB.get());
		dropSelf(CABlocks.LIMESTONE_BRICK_SLAB.get());
		dropSelf(CABlocks.CHISELED_LIMESTONE_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_LIMESTONE_BRICK_SLAB.get());
		dropSelf(CABlocks.MOSSY_LIMESTONE_BRICK_SLAB.get());
		dropSelf(CABlocks.POLISHED_LIMESTONE_SLAB.get());
		dropSelf(CABlocks.LIMESTONE_STAIRS.get());
		dropSelf(CABlocks.LIMESTONE_BRICK_STAIRS.get());
		dropSelf(CABlocks.CHISELED_LIMESTONE_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_LIMESTONE_BRICK_STAIRS.get());
		dropSelf(CABlocks.MOSSY_LIMESTONE_BRICK_STAIRS.get());
		dropSelf(CABlocks.POLISHED_LIMESTONE_STAIRS.get());
		dropSelf(CABlocks.LIMESTONE_WALL.get());
		dropSelf(CABlocks.LIMESTONE_BRICK_WALL.get());
		dropSelf(CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_LIMESTONE_BRICK_WALL.get());
		dropSelf(CABlocks.MOSSY_LIMESTONE_BRICK_WALL.get());
		dropSelf(CABlocks.POLISHED_LIMESTONE_WALL.get());

		// Marble
		dropSelf(CABlocks.MARBLE.get());
		dropSelf(CABlocks.MARBLE_BRICKS.get());
		dropSelf(CABlocks.CHISELED_MARBLE_BRICKS.get());
		dropSelf(CABlocks.CRACKED_MARBLE_BRICKS.get());
		dropSelf(CABlocks.MOSSY_MARBLE_BRICKS.get());
		dropSelf(CABlocks.POLISHED_MARBLE.get());
		dropSelf(CABlocks.MARBLE_PILLAR.get());
		dropSelf(CABlocks.MARBLE_SLAB.get());
		dropSelf(CABlocks.MARBLE_BRICK_SLAB.get());
		dropSelf(CABlocks.CHISELED_MARBLE_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_MARBLE_BRICK_SLAB.get());
		dropSelf(CABlocks.MOSSY_MARBLE_BRICK_SLAB.get());
		dropSelf(CABlocks.POLISHED_MARBLE_SLAB.get());
		dropSelf(CABlocks.MARBLE_STAIRS.get());
		dropSelf(CABlocks.MARBLE_BRICK_STAIRS.get());
		dropSelf(CABlocks.CHISELED_MARBLE_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_MARBLE_BRICK_STAIRS.get());
		dropSelf(CABlocks.MOSSY_MARBLE_BRICK_STAIRS.get());
		dropSelf(CABlocks.POLISHED_MARBLE_STAIRS.get());
		dropSelf(CABlocks.MARBLE_WALL.get());
		dropSelf(CABlocks.MARBLE_BRICK_WALL.get());
		dropSelf(CABlocks.CHISELED_MARBLE_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_MARBLE_BRICK_WALL.get());
		dropSelf(CABlocks.MOSSY_MARBLE_BRICK_WALL.get());
		dropSelf(CABlocks.POLISHED_MARBLE_WALL.get());
		
		// Rhinestone
		dropSelf(CABlocks.RHINESTONE.get());
		dropSelf(CABlocks.RHINESTONE_BRICKS.get());
		dropSelf(CABlocks.CHISELED_RHINESTONE_BRICKS.get());
		dropSelf(CABlocks.CRACKED_RHINESTONE_BRICKS.get());
		dropSelf(CABlocks.MOSSY_RHINESTONE_BRICKS.get());
		dropSelf(CABlocks.POLISHED_RHINESTONE.get());
		dropSelf(CABlocks.RHINESTONE_PILLAR.get());
		dropSelf(CABlocks.RHINESTONE_SLAB.get());
		dropSelf(CABlocks.RHINESTONE_BRICK_SLAB.get());
		dropSelf(CABlocks.CHISELED_RHINESTONE_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_RHINESTONE_BRICK_SLAB.get());
		dropSelf(CABlocks.MOSSY_RHINESTONE_BRICK_SLAB.get());
		dropSelf(CABlocks.POLISHED_RHINESTONE_SLAB.get());
		dropSelf(CABlocks.RHINESTONE_STAIRS.get());
		dropSelf(CABlocks.RHINESTONE_BRICK_STAIRS.get());
		dropSelf(CABlocks.CHISELED_RHINESTONE_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_RHINESTONE_BRICK_STAIRS.get());
		dropSelf(CABlocks.MOSSY_RHINESTONE_BRICK_STAIRS.get());
		dropSelf(CABlocks.POLISHED_RHINESTONE_STAIRS.get());
		dropSelf(CABlocks.RHINESTONE_WALL.get());
		dropSelf(CABlocks.RHINESTONE_BRICK_WALL.get());
		dropSelf(CABlocks.CHISELED_RHINESTONE_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_RHINESTONE_BRICK_WALL.get());
		dropSelf(CABlocks.MOSSY_RHINESTONE_BRICK_WALL.get());
		dropSelf(CABlocks.POLISHED_RHINESTONE_WALL.get());

		// Crystal Blocks
		add(CABlocks.CRYSTAL_GRASS_BLOCK.get(), (silk) -> createSilkTouchOnlyTable(CABlocks.KYANITE.get()));
		dropSelf(CABlocks.CRYSTAL_TERMITE_NEST.get());
		dropSelf(CABlocks.CRYSTAL_LOG.get());
		dropSelf(CABlocks.CRYSTAL_PLANKS.get());
		dropSelf(CABlocks.CRYSTAL_WOOD.get());
		dropSelf(CABlocks.KYANITE.get());
		add(CABlocks.BUDDING_CATS_EYE.get(), (block) -> createSingleItemTable(CABlocks.KYANITE.get()));
		add(CABlocks.BUDDING_PINK_TOURMALINE.get(), (block) -> createSingleItemTable(CABlocks.KYANITE.get()));

		dropSelf(CABlocks.CRYSTAL_CRAFTING_TABLE.get());
		dropSelf(CABlocks.CRYSTAL_ENERGY.get());
		dropSelf(CABlocks.CRYSTAL_FURNACE.get());

		add(CABlocks.RED_CRYSTAL_LEAVES.get(), (leaves) -> createLeavesDrops(leaves, CABlocks.RED_CRYSTAL_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(CABlocks.GREEN_CRYSTAL_LEAVES.get(), (leaves) -> createLeavesDrops(leaves, CABlocks.GREEN_CRYSTAL_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(CABlocks.YELLOW_CRYSTAL_LEAVES.get(), (leaves) -> createLeavesDrops(leaves, CABlocks.YELLOW_CRYSTAL_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));		
		add(CABlocks.PINK_CRYSTAL_LEAVES.get(), (leaves) -> createLeavesDrops(leaves, CABlocks.PINK_CRYSTAL_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));	
		add(CABlocks.BLUE_CRYSTAL_LEAVES.get(), (leaves) -> createLeavesDrops(leaves, CABlocks.BLUE_CRYSTAL_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));		
		add(CABlocks.ORANGE_CRYSTAL_LEAVES.get(), (leaves) -> createLeavesDrops(leaves, CABlocks.ORANGE_CRYSTAL_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));	
		
		dropSelf(CABlocks.RED_CRYSTAL_SAPLING.get());
		dropSelf(CABlocks.GREEN_CRYSTAL_SAPLING.get());
		dropSelf(CABlocks.YELLOW_CRYSTAL_SAPLING.get());
		dropSelf(CABlocks.PINK_CRYSTAL_SAPLING.get());
		dropSelf(CABlocks.BLUE_CRYSTAL_SAPLING.get());
		dropSelf(CABlocks.ORANGE_CRYSTAL_SAPLING.get());
		dropSelf(CABlocks.RED_CRYSTAL_FLOWER.get());
		dropSelf(CABlocks.BLUE_CRYSTAL_FLOWER.get());
		dropSelf(CABlocks.GREEN_CRYSTAL_FLOWER.get());
		dropSelf(CABlocks.YELLOW_CRYSTAL_FLOWER.get());
		dropSelf(CABlocks.PINK_CRYSTAL_FLOWER.get());
		dropSelf(CABlocks.ORANGE_CRYSTAL_FLOWER.get());
		dropSelf(CABlocks.RED_CRYSTAL_GROWTH.get());
		dropSelf(CABlocks.BLUE_CRYSTAL_GROWTH.get());
		dropSelf(CABlocks.GREEN_CRYSTAL_GROWTH.get());
		dropSelf(CABlocks.YELLOW_CRYSTAL_GROWTH.get());
		dropSelf(CABlocks.ORANGE_CRYSTAL_GROWTH.get());
		dropSelf(CABlocks.PINK_CRYSTAL_GROWTH.get());
		dropSelf(CABlocks.CRYSTAL_ROSE.get());

		// Torches
		add(CABlocks.CRYSTAL_TORCH.get(), (block) -> createSingleItemTable(CABlocks.CRYSTAL_TORCH.get()));
		add(CABlocks.SUNSTONE_TORCH.get(), (block) -> createSingleItemTable(CABlocks.SUNSTONE_TORCH.get()));
		add(CABlocks.EXTREME_TORCH.get(), (block) -> createSingleItemTable(CABlocks.EXTREME_TORCH.get()));

		// Drop Blocks
		dropSelf(CABlocks.ENDER_EYE_BLOCK.get());
		dropSelf(CABlocks.ENDER_PEARL_BLOCK.get());
		dropSelf(CABlocks.MOTH_SCALE_BLOCK.get());
		dropSelf(CABlocks.WATER_DRAGON_SCALE_BLOCK.get());
		dropSelf(CABlocks.ENDER_DRAGON_SCALE_BLOCK.get());
		dropSelf(CABlocks.NIGHTMARE_SCALE_BLOCK.get());
		dropSelf(CABlocks.MOBZILLA_SCALE_BLOCK.get());
		dropSelf(CABlocks.ROYAL_GUARDIAN_SCALE_BLOCK.get());
		dropSelf(CABlocks.QUEEN_SCALE_BLOCK.get());
		dropSelf(CABlocks.BASILISK_SCALE_BLOCK.get());
		dropSelf(CABlocks.EMPEROR_SCORPION_SCALE_BLOCK.get());

		// Robo Blocks
		dropSelf(CABlocks.ROBO_BLOCK_I.get());
		dropSelf(CABlocks.ROBO_BLOCK_V.get());
		dropSelf(CABlocks.ROBO_BLOCK_X.get());
		dropSelf(CABlocks.ROBO_SLAB_I.get());
		dropSelf(CABlocks.ROBO_SLAB_X.get());
		dropSelf(CABlocks.ROBO_STAIRS_I.get());
		dropSelf(CABlocks.ROBO_STAIRS_X.get());
		dropSelf(CABlocks.ROBO_WALL_I.get());
		dropSelf(CABlocks.ROBO_WALL_X.get());
		dropSelf(CABlocks.ROBO_LAMP.get());
		dropSelf(CABlocks.ROBO_BRICKS.get());
		dropSelf(CABlocks.ROBO_BRICK_SLAB.get());
		dropSelf(CABlocks.ROBO_BRICK_STAIRS.get());
		dropSelf(CABlocks.ROBO_BRICK_WALL.get());
		dropSelf(CABlocks.COMPACT_ROBO_BLOCK.get());
		dropSelf(CABlocks.ROBO_GATE_BLOCK.get());
		dropSelf(CABlocks.ROBO_CRATE.get());

		// Flower Blocks
		dropSelf(CABlocks.FLOWER_STEM.get());
		dropSelf(CABlocks.NAVY_BLUE_PETAL_BLOCK.get());
		dropSelf(CABlocks.BLOOD_RED_PETAL_BLOCK.get());
		dropSelf(CABlocks.BRIGHT_PINK_PETAL_BLOCK.get());

		// Terracotta Bricks
		dropSelf(CABlocks.BLACK_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.BLUE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.BROWN_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CYAN_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.GRAY_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.GREEN_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.LIME_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.MAGENTA_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.ORANGE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.PINK_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.PURPLE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.RED_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.WHITE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.YELLOW_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.BLACK_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.BLUE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.BROWN_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CYAN_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.GRAY_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.GREEN_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.LIME_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.MAGENTA_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.ORANGE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.PINK_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.PURPLE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.RED_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.WHITE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.YELLOW_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.BLACK_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.BLUE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.BROWN_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CYAN_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.GRAY_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.GREEN_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.LIME_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.MAGENTA_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.ORANGE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.PINK_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.PURPLE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.RED_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.WHITE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.YELLOW_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.LIME_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.PINK_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.RED_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get());

		// Cracked Terracotta Bricks
		dropSelf(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_LIME_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_PINK_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_RED_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICKS.get());
		dropSelf(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_SLAB.get());
		dropSelf(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_STAIRS.get());
		dropSelf(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get());
		
		dropSelf(CABlocks.SKY_MOSS_BLOCK.get());
/*		dropSelf(CABlocks.SKY_MOSS_CARPET.get());
		dropSelf(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get());
		dropSelf(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get());
*/

		dropSelf(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.COPPER.getId())).get());
		dropSelf(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get());
		dropSelf(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.CRYSTAL.getId())).get());
		
		for (Block block : ForgeRegistries.BLOCKS) {
			if (!ChaosAwakens.MODID.equals(Objects.requireNonNull(block.getRegistryName()).getNamespace())) continue;

			String name = block.getRegistryName().getPath();
			ChaosAwakens.LOGGER.debug(block.getRegistryName());

			if (block instanceof CAOreBlock || block instanceof CAFallingOreBlock) {
				if (name.contains("fossilised") || name.contains("frozen") || name.contains("crystalised")) {
					dropSelf(block);
				}
			}
		}

		dropSelf(CABlocks.CYAN_ROSE.get());
		dropSelf(CABlocks.RED_ROSE.get());
		dropSelf(CABlocks.PAEONIA.get());
		dropSelf(CABlocks.DAISY.get());
		dropSelf(CABlocks.SWAMP_MILKWEED.get());
		dropSelf(CABlocks.PRIMROSE.get());
		
		dropSelf(CABlocks.APPLE_SAPLING.get());
		dropSelf(CABlocks.CHERRY_SAPLING.get());
		dropSelf(CABlocks.PEACH_SAPLING.get());
		dropSelf(CABlocks.GINKGO_SAPLING.get());
		dropSelf(CABlocks.MESOZOIC_SAPLING.get());
		dropSelf(CABlocks.DENSEWOOD_SAPLING.get());
		dropSelf(CABlocks.APPLE_LOG.get());
		dropSelf(CABlocks.APPLE_WOOD.get());
		dropSelf(CABlocks.CHERRY_LOG.get());
		dropSelf(CABlocks.CHERRY_WOOD.get());
		dropSelf(CABlocks.DEAD_DUPLICATION_LOG.get());
		dropSelf(CABlocks.DEAD_DUPLICATION_WOOD.get());
		dropSelf(CABlocks.STRIPPED_DEAD_DUPLICATION_LOG.get());
		dropSelf(CABlocks.STRIPPED_DEAD_DUPLICATION_WOOD.get());
		dropSelf(CABlocks.GINKGO_LOG.get());
		dropSelf(CABlocks.GINKGO_WOOD.get());
		dropSelf(CABlocks.MESOZOIC_LOG.get());
		dropSelf(CABlocks.MESOZOIC_WOOD.get());
		dropSelf(CABlocks.STRIPPED_MESOZOIC_LOG.get());
		dropSelf(CABlocks.STRIPPED_MESOZOIC_WOOD.get());
		dropSelf(CABlocks.DENSEWOOD_LOG.get());
		dropSelf(CABlocks.DENSEWOOD_WOOD.get());
		dropSelf(CABlocks.STRIPPED_DENSEWOOD_LOG.get());
		dropSelf(CABlocks.STRIPPED_DENSEWOOD_WOOD.get());
		dropSelf(CABlocks.PEACH_LOG.get());
		dropSelf(CABlocks.PEACH_WOOD.get());
		dropSelf(CABlocks.SKYWOOD_LOG.get());
		dropSelf(CABlocks.SKYWOOD_WOOD.get());
		dropSelf(CABlocks.APPLE_PLANKS.get());
		dropSelf(CABlocks.CHERRY_PLANKS.get());
		dropSelf(CABlocks.DUPLICATION_PLANKS.get());
		dropSelf(CABlocks.GINKGO_PLANKS.get());
		dropSelf(CABlocks.MESOZOIC_PLANKS.get());
		dropSelf(CABlocks.DENSEWOOD_PLANKS.get());
		dropSelf(CABlocks.PEACH_PLANKS.get());
		dropSelf(CABlocks.SKYWOOD_PLANKS.get());
		dropSelf(CABlocks.STRIPPED_APPLE_LOG.get());
		dropSelf(CABlocks.STRIPPED_APPLE_WOOD.get());
		dropSelf(CABlocks.STRIPPED_CHERRY_LOG.get());
		dropSelf(CABlocks.STRIPPED_CHERRY_WOOD.get());
		dropSelf(CABlocks.STRIPPED_DUPLICATION_LOG.get());
		dropSelf(CABlocks.STRIPPED_DUPLICATION_WOOD.get());
		dropSelf(CABlocks.STRIPPED_GINKGO_LOG.get());
		dropSelf(CABlocks.STRIPPED_GINKGO_WOOD.get());
		dropSelf(CABlocks.STRIPPED_PEACH_LOG.get());
		dropSelf(CABlocks.STRIPPED_PEACH_WOOD.get());
		dropSelf(CABlocks.STRIPPED_SKYWOOD_LOG.get());
		dropSelf(CABlocks.STRIPPED_SKYWOOD_WOOD.get());
		dropSelf(CABlocks.APPLE_STAIRS.get());
		dropSelf(CABlocks.CHERRY_STAIRS.get());
		dropSelf(CABlocks.PEACH_STAIRS.get());
		dropSelf(CABlocks.GINKGO_STAIRS.get());
		dropSelf(CABlocks.MESOZOIC_STAIRS.get());
		dropSelf(CABlocks.DENSEWOOD_STAIRS.get());
		dropSelf(CABlocks.DUPLICATION_STAIRS.get());
		dropSelf(CABlocks.SKYWOOD_STAIRS.get());
		dropSelf(CABlocks.CRYSTAL_STAIRS.get());
		dropSelf(CABlocks.APPLE_SLAB.get());
		dropSelf(CABlocks.CHERRY_SLAB.get());
		dropSelf(CABlocks.DUPLICATION_SLAB.get());
		dropSelf(CABlocks.GINKGO_SLAB.get());
		dropSelf(CABlocks.MESOZOIC_SLAB.get());
		dropSelf(CABlocks.DENSEWOOD_SLAB.get());
		dropSelf(CABlocks.PEACH_SLAB.get());
		dropSelf(CABlocks.SKYWOOD_SLAB.get());
		dropSelf(CABlocks.CRYSTAL_SLAB.get());
		dropSelf(CABlocks.APPLE_FENCE.get());
		dropSelf(CABlocks.CHERRY_FENCE.get());
		dropSelf(CABlocks.DUPLICATION_FENCE.get());
		dropSelf(CABlocks.GINKGO_FENCE.get());
		dropSelf(CABlocks.MESOZOIC_FENCE.get());
		dropSelf(CABlocks.DENSEWOOD_FENCE.get());
		dropSelf(CABlocks.PEACH_FENCE.get());
		dropSelf(CABlocks.SKYWOOD_FENCE.get());
		dropSelf(CABlocks.CRYSTAL_FENCE.get());
		dropSelf(CABlocks.APPLE_FENCE_GATE.get());
		dropSelf(CABlocks.CHERRY_FENCE_GATE.get());
		dropSelf(CABlocks.DUPLICATION_FENCE_GATE.get());
		dropSelf(CABlocks.GINKGO_FENCE_GATE.get());
		dropSelf(CABlocks.MESOZOIC_FENCE_GATE.get());
		dropSelf(CABlocks.DENSEWOOD_FENCE_GATE.get());
		dropSelf(CABlocks.PEACH_FENCE_GATE.get());
		dropSelf(CABlocks.SKYWOOD_FENCE_GATE.get());
		dropSelf(CABlocks.CRYSTAL_FENCE_GATE.get());
		dropSelf(CABlocks.APPLE_PRESSURE_PLATE.get());
		dropSelf(CABlocks.CHERRY_PRESSURE_PLATE.get());
		dropSelf(CABlocks.DUPLICATION_PRESSURE_PLATE.get());
		dropSelf(CABlocks.GINKGO_PRESSURE_PLATE.get());
		dropSelf(CABlocks.MESOZOIC_PRESSURE_PLATE.get());
		dropSelf(CABlocks.DENSEWOOD_PRESSURE_PLATE.get());
		dropSelf(CABlocks.PEACH_PRESSURE_PLATE.get());
		dropSelf(CABlocks.SKYWOOD_PRESSURE_PLATE.get());
		dropSelf(CABlocks.CRYSTAL_PRESSURE_PLATE.get());
		dropSelf(CABlocks.APPLE_BUTTON.get());
		dropSelf(CABlocks.CHERRY_BUTTON.get());
		dropSelf(CABlocks.DUPLICATION_BUTTON.get());
		dropSelf(CABlocks.GINKGO_BUTTON.get());
		dropSelf(CABlocks.MESOZOIC_BUTTON.get());
		dropSelf(CABlocks.DENSEWOOD_BUTTON.get());
		dropSelf(CABlocks.PEACH_BUTTON.get());
		dropSelf(CABlocks.SKYWOOD_BUTTON.get());
		dropSelf(CABlocks.CRYSTAL_BUTTON.get());

		dropSelf(CABlocks.APPLE_SIGN.get());
		dropSelf(CABlocks.CHERRY_SIGN.get());
		dropSelf(CABlocks.DUPLICATION_SIGN.get());
		dropSelf(CABlocks.GINKGO_SIGN.get());
		dropSelf(CABlocks.MESOZOIC_SIGN.get());
		dropSelf(CABlocks.DENSEWOOD_SIGN.get());
		dropSelf(CABlocks.PEACH_SIGN.get());
		dropSelf(CABlocks.SKYWOOD_SIGN.get());

		dropSelf(CABlocks.APPLE_TRAPDOOR.get());
		dropSelf(CABlocks.CHERRY_TRAPDOOR.get());
		dropSelf(CABlocks.DUPLICATION_TRAPDOOR.get());
		dropSelf(CABlocks.GINKGO_TRAPDOOR.get());
		dropSelf(CABlocks.MESOZOIC_TRAPDOOR.get());
		dropSelf(CABlocks.DENSEWOOD_TRAPDOOR.get());
		dropSelf(CABlocks.PEACH_TRAPDOOR.get());
		dropSelf(CABlocks.SKYWOOD_TRAPDOOR.get());

		dropSelf(CABlocks.APPLE_GATE_BLOCK.get());
		dropSelf(CABlocks.CHERRY_GATE_BLOCK.get());
		dropSelf(CABlocks.DUPLICATION_GATE_BLOCK.get());
		dropSelf(CABlocks.GINKGO_GATE_BLOCK.get());
		dropSelf(CABlocks.PEACH_GATE_BLOCK.get());
		dropSelf(CABlocks.SKYWOOD_GATE_BLOCK.get());
		dropSelf(CABlocks.MUSHROOM_GATE_BLOCK.get());

		dropSelf(CABlocks.ACACIA_GATE_BLOCK.get());
		dropSelf(CABlocks.BIRCH_GATE_BLOCK.get());
		dropSelf(CABlocks.CRIMSON_GATE_BLOCK.get());
		dropSelf(CABlocks.DARK_OAK_GATE_BLOCK.get());
		dropSelf(CABlocks.JUNGLE_GATE_BLOCK.get());
		dropSelf(CABlocks.OAK_GATE_BLOCK.get());
		dropSelf(CABlocks.SPRUCE_GATE_BLOCK.get());
		dropSelf(CABlocks.WARPED_GATE_BLOCK.get());
		dropSelf(CABlocks.NEST_BLOCK.get());

		dropAir(CABlocks.MOLDY_PLANKS.get());
		dropAir(CABlocks.MOLDY_SLAB.get());
		dropAir(CABlocks.MOLDY_FENCE.get());
		dropAir(CABlocks.MINING_LAMP.get());
		dropAir(CABlocks.RANDOM_TELEPORT_BLOCK.get());

		dropPottedContents(CABlocks.POTTED_CYAN_ROSE.get());
		dropPottedContents(CABlocks.POTTED_RED_ROSE.get());
		dropPottedContents(CABlocks.POTTED_PAEONIA.get());
		dropPottedContents(CABlocks.POTTED_BLUE_BULB.get());
		dropPottedContents(CABlocks.POTTED_PINK_BULB.get());
		dropPottedContents(CABlocks.POTTED_PURPLE_BULB.get());
		dropPottedContents(CABlocks.POTTED_DENSE_ORCHID.get());
		dropPottedContents(CABlocks.POTTED_SWAMP_MILKWEED.get());
		dropPottedContents(CABlocks.POTTED_SMALL_BUSH.get());
		dropPottedContents(CABlocks.POTTED_SMALL_CARNIVOROUS_PLANT.get());
		dropPottedContents(CABlocks.POTTED_BIG_CARNIVOROUS_PLANT.get());
		dropPottedContents(CABlocks.POTTED_PRIMROSE.get());
		dropPottedContents(CABlocks.POTTED_DAISY.get());
		dropPottedContents(CABlocks.POTTED_APPLE_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_CHERRY_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_PEACH_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_GINKGO_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_MESOZOIC_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_DENSEWOOD_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_RED_CRYSTAL_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_GREEN_CRYSTAL_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_YELLOW_CRYSTAL_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_PINK_CRYSTAL_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_BLUE_CRYSTAL_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_ORANGE_CRYSTAL_SAPLING.get());
		dropPottedContents(CABlocks.POTTED_RED_CRYSTAL_FLOWER.get());
		dropPottedContents(CABlocks.POTTED_BLUE_CRYSTAL_FLOWER.get());
		dropPottedContents(CABlocks.POTTED_GREEN_CRYSTAL_FLOWER.get());
		dropPottedContents(CABlocks.POTTED_YELLOW_CRYSTAL_FLOWER.get());
		dropPottedContents(CABlocks.POTTED_PINK_CRYSTAL_FLOWER.get());
		dropPottedContents(CABlocks.POTTED_ORANGE_CRYSTAL_FLOWER.get());
		dropPottedContents(CABlocks.POTTED_RED_CRYSTAL_GROWTH.get());
		dropPottedContents(CABlocks.POTTED_BLUE_CRYSTAL_GROWTH.get());
		dropPottedContents(CABlocks.POTTED_GREEN_CRYSTAL_GROWTH.get());
		dropPottedContents(CABlocks.POTTED_YELLOW_CRYSTAL_GROWTH.get());
		dropPottedContents(CABlocks.POTTED_ORANGE_CRYSTAL_GROWTH.get());
		dropPottedContents(CABlocks.POTTED_PINK_CRYSTAL_GROWTH.get());
		dropPottedContents(CABlocks.POTTED_CRYSTAL_ROSE.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ForgeRegistries.BLOCKS.getValues().stream()
				.filter(block -> Objects.requireNonNull(block.getRegistryName()).getNamespace().equals(ChaosAwakens.MODID))
				.filter(block -> !(block instanceof LeafCarpetBlock))
				.collect(Collectors.toList());
	}
	
	private static LootTable.Builder createCluster(Block cluster, Item shard) {
		return applyExplosionDecay(cluster, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.when(BlockStateProperty.hasBlockStateProperties(cluster)
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(CrystalClusterBlock.AGE, 0)))
						.add(ItemLootEntry.lootTableItem(shard)
								.apply(SetCount.setCount(ConstantRange.exactly(1)))
								.apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))
				.withPool(LootPool.lootPool()
						.when(BlockStateProperty.hasBlockStateProperties(cluster)
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(CrystalClusterBlock.AGE, 1)))
						.add(ItemLootEntry.lootTableItem(shard)
								.apply(SetCount.setCount(ConstantRange.exactly(2)))
								.apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))
				.withPool(LootPool.lootPool()
						.when(BlockStateProperty.hasBlockStateProperties(cluster)
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(CrystalClusterBlock.AGE, 2)))
						.add(ItemLootEntry.lootTableItem(shard)
								.apply(SetCount.setCount(ConstantRange.exactly(3)))
								.apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))
				.withPool(LootPool.lootPool()
						.when(BlockStateProperty.hasBlockStateProperties(cluster)
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(CrystalClusterBlock.AGE, 3)))
						.add(ItemLootEntry.lootTableItem(shard)
								.apply(SetCount.setCount(ConstantRange.exactly(4)))
								.apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))
				);
	}

	private LootTable.Builder cropBodyBlock(Item fruit, Item seed) {
		return LootTable.lootTable()
				.withPool(applyExplosionCondition(fruit, LootPool.lootPool().setRolls(RandomValueRange.between(1, 3)))
						.add(ItemLootEntry.lootTableItem(fruit)).add(ItemLootEntry.lootTableItem(seed)));
	}

	protected static LootTable.Builder createFruitLeavesDrops(Block leaves, Block sapling, Item fruit, float... chances) {
		return createLeavesDrops(leaves, sapling, chances)
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(HAS_NO_SHEARS_OR_SILK_TOUCH)
						.add(applyExplosionCondition(leaves, ItemLootEntry.lootTableItem(fruit))
								.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F,
										0.00625F, 0.008333334F, 0.025F))));
	}

	protected static LootTable.Builder createOreDropNoFortune(Block block, Item item) {
		return createSilkTouchDispatchTable(block, applyExplosionDecay(block, ItemLootEntry.lootTableItem(item)));
	}

	private LootTable.Builder randomDropping(IItemProvider item, float random1, float random2) {
		return LootTable.lootTable().withPool(
				applyExplosionCondition(item, LootPool.lootPool()
						.setRolls(RandomValueRange.between(random1, random2)))
						.add(ItemLootEntry.lootTableItem(item)));
	}

	private LootTable.Builder createDoorDrop(Block block) {
		return LootTable.lootTable().withPool(
				applyExplosionCondition(block, LootPool.lootPool()
						.setRolls(ConstantRange.exactly(1))
						.add(ItemLootEntry.lootTableItem(block)
								.when(BlockStateProperty.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(DoorBlock.HALF, DoubleBlockHalf.LOWER))))));
	}

	public void dropAir(Block block) {
		this.dropOther(block, Blocks.AIR);
	}
}
