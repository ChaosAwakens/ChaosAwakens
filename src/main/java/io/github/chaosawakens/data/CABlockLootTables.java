package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.TableBonus;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CABlockLootTables extends BlockLootTables {
	private static final ILootCondition.IBuilder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
	private static final ILootCondition.IBuilder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
	private static final ILootCondition.IBuilder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
	private static final ILootCondition.IBuilder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
	private static final ILootCondition.IBuilder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
	
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
		add(CABlocks.NETHERRACK_RUBY_ORE.get(), (ore) -> createOreDrop(ore, CAItems.RUBY.get()));
		add(CABlocks.BLACKSTONE_RUBY_ORE.get(), (ore) -> createOreDrop(ore, CAItems.RUBY.get()));
		add(CABlocks.SALT_ORE.get(), (ore) -> randomDropping(CAItems.SALT.get(), 4, 8));
		add(CABlocks.SUNSTONE_ORE.get(), (ore) -> createOreDrop(ore, CAItems.SUNSTONE.get()));
		add(CABlocks.TIGERS_EYE_ORE.get(), (ore) -> createOreDrop(ore, CAItems.TIGERS_EYE.get()));
		
		// Plants
		add(CABlocks.TUBE_WORM.get(), (plant) -> createShearsOnlyDrop(CABlocks.TUBE_WORM.get()));
		add(CABlocks.CORN_TOP_BLOCK.get(), (plant) -> randomDropping(CAItems.CORN_SEEDS.get(), 1, 3));
		add(CABlocks.CORN_BODY_BLOCK.get(), (plant) -> cropBodyBlock(CAItems.CORN.get(), CAItems.CORN_SEEDS.get()));
		add(CABlocks.TOMATO_TOP_BLOCK.get(), (plant) -> randomDropping(CAItems.TOMATO_SEEDS.get(), 1, 3));
		add(CABlocks.TOMATO_BODY_BLOCK.get(), (plant) -> cropBodyBlock(CAItems.TOMATO.get(), CAItems.TOMATO_SEEDS.get()));
		add(CABlocks.STRAWBERRY_PLANT.get(), (plant) -> createCropDrops(plant, CAItems.STRAWBERRY.get(), CAItems.STRAWBERRY_SEEDS.get(), BlockStateProperty.hasBlockStateProperties(plant).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_3, 3))));
		
		dropOther(CABlocks.MOLDY_PLANKS.get(), Items.AIR);
		dropOther(CABlocks.MOLDY_SLAB.get(), Items.AIR);
		dropOther(CABlocks.MOLDY_FENCE.get(), Items.AIR);
		dropOther(CABlocks.MINING_LAMP.get(), Items.AIR);
		dropOther(CABlocks.DUPLICATION_LOG.get(), CABlocks.DEAD_DUPLICATION_LOG.get());

		add(CABlocks.APPLE_LEAVES.get(), createCALeavesDrops(CABlocks.APPLE_LEAVES.get(), CABlocks.APPLE_SAPLING.get(), Items.APPLE, 0.05F, 0.0625F, 0.083333336F, 0.1F));
		add(CABlocks.CHERRY_LEAVES.get(), createCALeavesDrops(CABlocks.CHERRY_LEAVES.get(), CABlocks.CHERRY_SAPLING.get(), CAItems.CHERRIES.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F));
		add(CABlocks.DUPLICATION_LEAVES.get(), createSilkTouchOnlyTable(CABlocks.DUPLICATION_LEAVES.get()));
		add(CABlocks.PEACH_LEAVES.get(), createCALeavesDrops(CABlocks.PEACH_LEAVES.get(), CABlocks.PEACH_SAPLING.get(), CAItems.PEACH.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F));

		dropSelf(CABlocks.ALUMINUM_ORE.get());
		dropSelf(CABlocks.ALUMINUM_BLOCK.get());
		dropSelf(CABlocks.AMETHYST_BLOCK.get());
		dropSelf(CABlocks.BLOODSTONE_BLOCK.get());
		dropSelf(CABlocks.BROWN_ANT_NEST.get());
		dropSelf(CABlocks.CATS_EYE_BLOCK.get());
		dropSelf(CABlocks.CATS_EYE_CLUSTER.get());
		dropSelf(CABlocks.COPPER_BLOCK.get());
		dropSelf(CABlocks.COPPER_ORE.get());
		dropSelf(CABlocks.CRYSTAL_CRAFTING_TABLE.get());
		dropSelf(CABlocks.CRYSTAL_ENERGY.get());
		dropSelf(CABlocks.CRYSTAL_FURNACE.get());
		dropSelf(CABlocks.CRYSTAL_LOG.get());
		dropSelf(CABlocks.CRYSTAL_TERMITE_NEST.get());
		dropSelf(CABlocks.CRYSTAL_WOOD.get());
		dropSelf(CABlocks.CRYSTAL_WOOD_PLANKS.get());
		dropSelf(CABlocks.DEFOSSILIZER.get());
		dropSelf(CABlocks.ENDER_EYE_BLOCK.get());
		dropSelf(CABlocks.ENDER_PEARL_BLOCK.get());

		dropSelf(CABlocks.FOSSILISED_ACACIA_ENT.get());
		dropSelf(CABlocks.FOSSILISED_BIRCH_ENT.get());
		dropSelf(CABlocks.FOSSILISED_DARK_OAK_ENT.get());
		dropSelf(CABlocks.FOSSILISED_JUNGLE_ENT.get());
		dropSelf(CABlocks.FOSSILISED_OAK_ENT.get());
		dropSelf(CABlocks.FOSSILISED_SPRUCE_ENT.get());
		dropSelf(CABlocks.FOSSILISED_HERCULES_BEETLE.get());
		dropSelf(CABlocks.FOSSILISED_RUBY_BUG.get());
		dropSelf(CABlocks.FOSSILISED_EMERALD_GATOR.get());
		dropSelf(CABlocks.FOSSILISED_GREEN_FISH.get());
		dropSelf(CABlocks.FOSSILISED_ROCK_FISH.get());
		dropSelf(CABlocks.FOSSILISED_SPARK_FISH.get());
		dropSelf(CABlocks.FOSSILISED_WOOD_FISH.get());
		dropSelf(CABlocks.FOSSILISED_WHALE.get());
		dropSelf(CABlocks.FOSSILISED_WTF.get());
		dropSelf(CABlocks.FOSSILISED_SCORPION.get());
		dropSelf(CABlocks.FOSSILISED_WASP.get());
		dropSelf(CABlocks.FOSSILISED_PIRAPORU.get());
		dropSelf(CABlocks.FOSSILISED_APPLE_COW.get());
		dropSelf(CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get());
		dropSelf(CABlocks.FOSSILISED_CARROT_PIG.get());
		dropSelf(CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get());

		dropSelf(CABlocks.FOSSILISED_BAT.get());
		dropSelf(CABlocks.FOSSILISED_BEE.get());
		dropSelf(CABlocks.FOSSILISED_CAVE_SPIDER.get());
		dropSelf(CABlocks.FOSSILISED_CHICKEN.get());
		dropSelf(CABlocks.FOSSILISED_COD.get());
		dropSelf(CABlocks.FOSSILISED_COW.get());
		dropSelf(CABlocks.FOSSILISED_CREEPER.get());
		dropSelf(CABlocks.FOSSILISED_DOLPHIN.get());
		dropSelf(CABlocks.FOSSILISED_DONKEY.get());
		dropSelf(CABlocks.FOSSILISED_DROWNED.get());
		dropSelf(CABlocks.FOSSILISED_ENDERMAN.get());
		dropSelf(CABlocks.FOSSILISED_EVOKER.get());
		dropSelf(CABlocks.FOSSILISED_FOX.get());
		dropSelf(CABlocks.FOSSILISED_GIANT.get());
		dropSelf(CABlocks.FOSSILISED_GUARDIAN.get());
		dropSelf(CABlocks.FOSSILISED_HUSK.get());
		dropSelf(CABlocks.FOSSILISED_HUSK_STONE.get());
		dropSelf(CABlocks.FOSSILISED_ILLUSIONER.get());
		dropSelf(CABlocks.FOSSILISED_IRON_GOLEM.get());
		dropSelf(CABlocks.FOSSILISED_LLAMA.get());
		dropSelf(CABlocks.FOSSILISED_MOOSHROOM.get());
		dropSelf(CABlocks.FOSSILISED_OCELOT.get());
		dropSelf(CABlocks.FOSSILISED_PANDA.get());
		dropSelf(CABlocks.FOSSILISED_PIG.get());
		dropSelf(CABlocks.FOSSILISED_PHANTOM.get());
		dropSelf(CABlocks.FOSSILISED_PILLAGER.get());
		dropSelf(CABlocks.FOSSILISED_POLAR_BEAR.get());
		dropSelf(CABlocks.FOSSILISED_PUFFERFISH.get());
		dropSelf(CABlocks.FOSSILISED_RABBIT.get());
		dropSelf(CABlocks.FOSSILISED_RAVAGER.get());
		dropSelf(CABlocks.FOSSILISED_SALMON.get());
		dropSelf(CABlocks.FOSSILISED_SHEEP.get());
		dropSelf(CABlocks.FOSSILISED_SKELETON.get());
		dropSelf(CABlocks.FOSSILISED_SKELETON_HORSE.get());
		dropSelf(CABlocks.FOSSILISED_SLIME.get());
		dropSelf(CABlocks.FOSSILISED_SNOW_GOLEM.get());
		dropSelf(CABlocks.FOSSILISED_SPIDER.get());
		dropSelf(CABlocks.FOSSILISED_SQUID.get());
		dropSelf(CABlocks.FOSSILISED_STRAY.get());
		dropSelf(CABlocks.FOSSILISED_TROPICAL_FISH.get());
		dropSelf(CABlocks.FOSSILISED_TURTLE.get());
		dropSelf(CABlocks.FOSSILISED_VILLAGER.get());
		dropSelf(CABlocks.FOSSILISED_VINDICATOR.get());
		dropSelf(CABlocks.FOSSILISED_WANDERING_TRADER.get());
		dropSelf(CABlocks.FOSSILISED_WITCH.get());
		dropSelf(CABlocks.FOSSILISED_WOLF.get());
		dropSelf(CABlocks.FOSSILISED_ZOMBIE.get());
		dropSelf(CABlocks.FOSSILISED_ZOMBIE_HORSE.get());

		dropSelf(CABlocks.FOSSILISED_CRIMSON_ENT.get());
		dropSelf(CABlocks.FOSSILISED_WARPED_ENT.get());
		dropSelf(CABlocks.FOSSILISED_LAVA_EEL.get());

		dropSelf(CABlocks.FOSSILISED_BLAZE.get());
		dropSelf(CABlocks.FOSSILISED_GHAST.get());
		dropSelf(CABlocks.FOSSILISED_HOGLIN.get());
		dropSelf(CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get());
		dropSelf(CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get());
		dropSelf(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get());
		dropSelf(CABlocks.FOSSILISED_PIGLIN.get());
		dropSelf(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get());
		dropSelf(CABlocks.FOSSILISED_STRIDER.get());
		dropSelf(CABlocks.FOSSILISED_WITHER_SKELETON.get());
		dropSelf(CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get());

		dropSelf(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get());
		dropSelf(CABlocks.FOSSILISED_ENDERMITE.get());
		dropSelf(CABlocks.FOSSILISED_SHULKER.get());

		dropSelf(CABlocks.GATE_BLOCK.get());
		dropSelf(CABlocks.RANDOM_TELEPORT_BLOCK.get());
		dropSelf(CABlocks.GREEN_CRYSTAL_LEAVES.get());
		dropSelf(CABlocks.KYANITE.get());
		dropSelf(CABlocks.NEST_BLOCK.get());
		dropSelf(CABlocks.PINK_TOURMALINE_BLOCK.get());
		dropSelf(CABlocks.PINK_TOURMALINE_CLUSTER.get());
		dropSelf(CABlocks.PLATINUM_BLOCK.get());
		dropSelf(CABlocks.PLATINUM_ORE.get());
		dropSelf(CABlocks.RAINBOW_ANT_NEST.get());
		dropSelf(CABlocks.RED_ANT_NEST.get());
		dropSelf(CABlocks.RED_CRYSTAL_LEAVES.get());
		dropSelf(CABlocks.RUBY_BLOCK.get());
		dropSelf(CABlocks.SILVER_BLOCK.get());
		dropSelf(CABlocks.SILVER_ORE.get());
		dropSelf(CABlocks.SKYWOOD_LOG.get());
		dropSelf(CABlocks.STRIPPED_APPLE_LOG.get());
		dropSelf(CABlocks.STRIPPED_CHERRY_LOG.get());
		dropSelf(CABlocks.STRIPPED_DUPLICATION_LOG.get());
		dropSelf(CABlocks.STRIPPED_PEACH_LOG.get());
		dropSelf(CABlocks.STRIPPED_SKYWOOD_LOG.get());
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

		dropSelf(CABlocks.APPLE_SAPLING.get());
		dropSelf(CABlocks.CHERRY_SAPLING.get());
		dropSelf(CABlocks.PEACH_SAPLING.get());
		dropSelf(CABlocks.APPLE_LOG.get());
		dropSelf(CABlocks.CHERRY_LOG.get());
		dropSelf(CABlocks.DEAD_DUPLICATION_LOG.get());
		dropSelf(CABlocks.PEACH_LOG.get());
		dropSelf(CABlocks.SKYWOOD_LOG.get());
		dropSelf(CABlocks.APPLE_PLANKS.get());
		dropSelf(CABlocks.CHERRY_PLANKS.get());
		dropSelf(CABlocks.DUPLICATION_PLANKS.get());
		dropSelf(CABlocks.PEACH_PLANKS.get());
		dropSelf(CABlocks.SKYWOOD_PLANKS.get());
		dropSelf(CABlocks.STRIPPED_PEACH_LOG.get());
		dropSelf(CABlocks.STRIPPED_SKYWOOD_LOG.get());
		dropSelf(CABlocks.APPLE_STAIRS.get());
		dropSelf(CABlocks.CHERRY_STAIRS.get());
		dropSelf(CABlocks.PEACH_STAIRS.get());
		dropSelf(CABlocks.DUPLICATION_STAIRS.get());
		dropSelf(CABlocks.SKYWOOD_STAIRS.get());
		dropSelf(CABlocks.APPLE_SLAB.get());
		dropSelf(CABlocks.CHERRY_SLAB.get());
		dropSelf(CABlocks.PEACH_SLAB.get());
		dropSelf(CABlocks.DUPLICATION_SLAB.get());
		dropSelf(CABlocks.SKYWOOD_SLAB.get());
		dropSelf(CABlocks.APPLE_FENCE.get());
		dropSelf(CABlocks.CHERRY_FENCE.get());
		dropSelf(CABlocks.PEACH_FENCE.get());
		dropSelf(CABlocks.DUPLICATION_FENCE.get());
		dropSelf(CABlocks.SKYWOOD_FENCE.get());
		dropSelf(CABlocks.APPLE_FENCE_GATE.get());
		dropSelf(CABlocks.CHERRY_FENCE_GATE.get());
		dropSelf(CABlocks.PEACH_FENCE_GATE.get());
		dropSelf(CABlocks.DUPLICATION_FENCE_GATE.get());
		dropSelf(CABlocks.SKYWOOD_FENCE_GATE.get());
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks() {
//		return CABlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
		return ForgeRegistries.BLOCKS.getValues().stream().filter(b -> Objects.requireNonNull(b.getRegistryName()).getNamespace().equals(ChaosAwakens.MODID)).collect(Collectors.toList());
	}
	
	private LootTable.Builder cropBodyBlock(Item fruit, Item seed) {
		return LootTable.lootTable()
			.withPool(applyExplosionCondition(fruit, 
				LootPool.lootPool().setRolls(RandomValueRange.between(1, 3)))
					.add(ItemLootEntry.lootTableItem(fruit))
					.add(ItemLootEntry.lootTableItem(seed)));
	}

	protected static LootTable.Builder createCALeavesDrops(Block p_218526_0_, Block p_218526_1_, Item p_218526_2_, float... p_218526_3_) {
		return createLeavesDrops(p_218526_0_, p_218526_1_, p_218526_3_)
				.withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
						.when(HAS_NO_SHEARS_OR_SILK_TOUCH)
						.add(applyExplosionCondition(p_218526_0_, ItemLootEntry.lootTableItem(p_218526_2_))
								.when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
	}

	private LootTable.Builder randomDropping(IItemProvider item, float random1, float random2) {
		return LootTable.lootTable().withPool(applyExplosionCondition(item, LootPool.lootPool().setRolls(RandomValueRange.between(random1, random2))).add(ItemLootEntry.lootTableItem(item)));
	}

	@Nullable
	private Supplier<Block> blockSupplier;

	public boolean generateCustomBlock()
	{
		return blockSupplier != null;
	}

	public Block getBlock()
	{
		return blockSupplier != null ? blockSupplier.get() : Blocks.AIR;
	}
}
