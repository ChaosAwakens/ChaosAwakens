package io.github.chaosawakens.data;

import java.nio.file.Path;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

@SuppressWarnings("all")
public class CATagProvider extends BlockTagsProvider {
	public CATagProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, ChaosAwakens.MODID, existingFileHelper);
	}

	public static class CABlockTagProvider extends BlockTagsProvider {
		public CABlockTagProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
			super(gen, ChaosAwakens.MODID, existingFileHelper);
		}

		protected Path getPath(ResourceLocation resourceLocation) {
			return this.generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/tags/blocks/" + resourceLocation.getPath() + ".json");
		}

		@Override
		public String getName() {
			return ChaosAwakens.MODNAME + ": Block Tags";
		}

		@Override
		protected void addTags() {
			this.tag(CATags.Blocks.APPLE_LOGS).add(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get(), CABlocks.APPLE_WOOD.get(), CABlocks.STRIPPED_APPLE_WOOD.get());
			this.tag(CATags.Blocks.CHERRY_LOGS).add(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get(), CABlocks.CHERRY_WOOD.get(), CABlocks.STRIPPED_CHERRY_WOOD.get());
			this.tag(CATags.Blocks.DUPLICATION_LOGS).add(CABlocks.DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get(), CABlocks.DEAD_DUPLICATION_LOG.get(), CABlocks.DUPLICATION_WOOD.get(), CABlocks.STRIPPED_DUPLICATION_WOOD.get(), CABlocks.DEAD_DUPLICATION_WOOD.get());
			this.tag(CATags.Blocks.PEACH_LOGS).add(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get(), CABlocks.PEACH_WOOD.get(), CABlocks.STRIPPED_PEACH_WOOD.get());
			this.tag(CATags.Blocks.SKYWOOD_LOGS).add(CABlocks.SKYWOOD_LOG.get(), CABlocks.STRIPPED_SKYWOOD_LOG.get(), CABlocks.SKYWOOD_WOOD.get(), CABlocks.STRIPPED_SKYWOOD_WOOD.get());
			this.tag(CATags.Blocks.GINKGO_LOGS).add(CABlocks.GINKGO_LOG.get(), CABlocks.STRIPPED_GINKGO_LOG.get(), CABlocks.GINKGO_WOOD.get(), CABlocks.STRIPPED_GINKGO_WOOD.get());
			this.tag(CATags.Blocks.CRYSTAL_LOGS).add(CABlocks.CRYSTAL_LOG.get(), CABlocks.CRYSTAL_WOOD.get());
			this.tag(CATags.Blocks.CRYSTAL_LEAVES).add(CABlocks.RED_CRYSTAL_LEAVES.get(), CABlocks.GREEN_CRYSTAL_LEAVES.get(), CABlocks.YELLOW_CRYSTAL_LEAVES.get());
			this.tag(CATags.Blocks.CRYSTAL_SAPLING).add(CABlocks.RED_CRYSTAL_SAPLING.get(), CABlocks.GREEN_CRYSTAL_SAPLING.get(), CABlocks.YELLOW_CRYSTAL_SAPLING.get());
			this.tag(CATags.Blocks.RUBY_ORES).add(CABlocks.RUBY_ORE.get(), CABlocks.NETHERRACK_RUBY_ORE.get(), CABlocks.BLACKSTONE_RUBY_ORE.get());

			this.tag(CATags.Blocks.BASE_STONE_CRYSTAL).add(CABlocks.KYANITE.get());
			this.tag(CATags.Blocks.BASE_STONE_MINING).addTags(BlockTags.BASE_STONE_OVERWORLD);
			this.tag(CATags.Blocks.BASE_STONE_VILLAGE).addTags(BlockTags.BASE_STONE_OVERWORLD);
			this.tag(CATags.Blocks.MINERS_DREAM_MINEABLE).addTags(BlockTags.BASE_STONE_OVERWORLD, BlockTags.BASE_STONE_NETHER, CATags.Blocks.BASE_STONE_CRYSTAL, Tags.Blocks.STONE, Tags.Blocks.NETHERRACK, Tags.Blocks.SANDSTONE, Tags.Blocks.END_STONES, Tags.Blocks.GRAVEL, Tags.Blocks.SAND, Tags.Blocks.DIRT).add(Blocks.ICE);

			this.tag(CATags.Blocks.WHITELIST).addTags(BlockTags.LOGS);
			this.tag(CATags.Blocks.BLACKLIST).add(Blocks.BEDROCK);

			this.tag(BlockTags.LOGS).addTags(CATags.Blocks.DUPLICATION_LOGS);
			this.tag(BlockTags.LOGS_THAT_BURN).addTags(CATags.Blocks.APPLE_LOGS, CATags.Blocks.CHERRY_LOGS, CATags.Blocks.GINKGO_LOGS, CATags.Blocks.PEACH_LOGS, CATags.Blocks.SKYWOOD_LOGS, CATags.Blocks.CRYSTAL_LOGS);
			this.tag(BlockTags.PLANKS).add(CABlocks.APPLE_PLANKS.get(), CABlocks.CHERRY_PLANKS.get(), CABlocks.DUPLICATION_PLANKS.get(), CABlocks.GINKGO_PLANKS.get(), CABlocks.PEACH_PLANKS.get(), CABlocks.SKYWOOD_PLANKS.get(), CABlocks.MOLDY_PLANKS.get());
			this.tag(BlockTags.LEAVES).add(CABlocks.APPLE_LEAVES.get(), CABlocks.CHERRY_LEAVES.get(), CABlocks.GINKGO_LEAVES.get(), CABlocks.PEACH_LEAVES.get(), CABlocks.SKYWOOD_LEAVES.get());			this.tag(BlockTags.PLANKS).add(CABlocks.APPLE_PLANKS.get(), CABlocks.CHERRY_PLANKS.get(), CABlocks.PEACH_PLANKS.get(), CABlocks.DUPLICATION_PLANKS.get(), CABlocks.SKYWOOD_PLANKS.get(), CABlocks.GINKGO_PLANKS.get(), CABlocks.MOLDY_PLANKS.get());
			this.tag(BlockTags.SAPLINGS).add(CABlocks.APPLE_SAPLING.get(), CABlocks.CHERRY_SAPLING.get(), CABlocks.PEACH_SAPLING.get());
			this.tag(BlockTags.WOODEN_SLABS).add(CABlocks.APPLE_SLAB.get(), CABlocks.CHERRY_SLAB.get(), CABlocks.PEACH_SLAB.get(), CABlocks.DUPLICATION_SLAB.get(), CABlocks.SKYWOOD_SLAB.get(), CABlocks.GINKGO_SLAB.get(), CABlocks.MOLDY_SLAB.get(), CABlocks.CRYSTAL_SLAB.get());
			this.tag(BlockTags.WOODEN_STAIRS).add(CABlocks.APPLE_STAIRS.get(), CABlocks.CHERRY_STAIRS.get(), CABlocks.PEACH_STAIRS.get(), CABlocks.DUPLICATION_STAIRS.get(), CABlocks.SKYWOOD_STAIRS.get(), CABlocks.GINKGO_STAIRS.get(), CABlocks.CRYSTAL_STAIRS.get());
			this.tag(BlockTags.FENCE_GATES).add(CABlocks.APPLE_FENCE_GATE.get(), CABlocks.CHERRY_FENCE_GATE.get(), CABlocks.PEACH_FENCE_GATE.get(), CABlocks.DUPLICATION_FENCE_GATE.get(), CABlocks.SKYWOOD_FENCE_GATE.get(), CABlocks.GINKGO_FENCE_GATE.get(), CABlocks.CRYSTAL_FENCE_GATE.get());
			this.tag(BlockTags.WOODEN_FENCES).add(CABlocks.APPLE_FENCE.get(), CABlocks.CHERRY_FENCE.get(), CABlocks.PEACH_FENCE.get(), CABlocks.DUPLICATION_FENCE.get(), CABlocks.SKYWOOD_FENCE.get(), CABlocks.GINKGO_FENCE.get(), CABlocks.MOLDY_FENCE.get(), CABlocks.CRYSTAL_FENCE.get());
			this.tag(BlockTags.WOODEN_BUTTONS).add(CABlocks.APPLE_BUTTON.get(), CABlocks.CHERRY_BUTTON.get(), CABlocks.PEACH_BUTTON.get(), CABlocks.DUPLICATION_BUTTON.get(), CABlocks.SKYWOOD_BUTTON.get(), CABlocks.GINKGO_BUTTON.get(), CABlocks.CRYSTAL_BUTTON.get());
			this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(CABlocks.APPLE_PRESSURE_PLATE.get(), CABlocks.CHERRY_PRESSURE_PLATE.get(), CABlocks.PEACH_PRESSURE_PLATE.get(), CABlocks.DUPLICATION_PRESSURE_PLATE.get(), CABlocks.SKYWOOD_PRESSURE_PLATE.get(), CABlocks.GINKGO_PRESSURE_PLATE.get(), CABlocks.CRYSTAL_PRESSURE_PLATE.get());
			this.tag(BlockTags.SMALL_FLOWERS).add(CABlocks.CYAN_ROSE.get(), CABlocks.RED_ROSE.get(), CABlocks.PAEONIA.get(), CABlocks.BLUE_BULB.get(), CABlocks.PINK_BULB.get(), CABlocks.PURPLE_BULB.get(), CABlocks.RED_CRYSTAL_FLOWER.get(), CABlocks.GREEN_CRYSTAL_FLOWER.get(), CABlocks.YELLOW_CRYSTAL_FLOWER.get(), CABlocks.BLUE_CRYSTAL_FLOWER.get(), CABlocks.RED_CRYSTAL_GROWTH.get(), CABlocks.BLUE_CRYSTAL_GROWTH.get(), CABlocks.GREEN_CRYSTAL_GROWTH.get(), CABlocks.YELLOW_CRYSTAL_GROWTH.get(), CABlocks.ORANGE_CRYSTAL_GROWTH.get(), CABlocks.PINK_CRYSTAL_GROWTH.get());
			this.tag(BlockTags.FLOWER_POTS).add(CABlocks.POTTED_CYAN_ROSE.get(), CABlocks.POTTED_RED_ROSE.get(), CABlocks.POTTED_PAEONIA.get(), CABlocks.POTTED_BLUE_BULB.get(), CABlocks.POTTED_PINK_BULB.get(), CABlocks.POTTED_PURPLE_BULB.get(), CABlocks.POTTED_RED_CRYSTAL_FLOWER.get(), CABlocks.POTTED_GREEN_CRYSTAL_FLOWER.get(), CABlocks.POTTED_BLUE_CRYSTAL_FLOWER.get(), CABlocks.POTTED_YELLOW_CRYSTAL_FLOWER.get(), CABlocks.POTTED_RED_CRYSTAL_GROWTH.get(), CABlocks.POTTED_BLUE_CRYSTAL_GROWTH.get(), CABlocks.POTTED_GREEN_CRYSTAL_GROWTH.get(), CABlocks.POTTED_YELLOW_CRYSTAL_GROWTH.get(), CABlocks.POTTED_ORANGE_CRYSTAL_GROWTH.get(), CABlocks.POTTED_PINK_CRYSTAL_GROWTH.get());
			this.tag(BlockTags.BEACON_BASE_BLOCKS).add(CABlocks.AMETHYST_BLOCK.get(), CABlocks.RUBY_BLOCK.get(), CABlocks.TIGERS_EYE_BLOCK.get(), CABlocks.TITANIUM_BLOCK.get(), CABlocks.URANIUM_BLOCK.get(), CABlocks.ALUMINUM_BLOCK.get(), CABlocks.COPPER_BLOCK.get(), CABlocks.TIN_BLOCK.get(), CABlocks.SILVER_BLOCK.get(), CABlocks.PLATINUM_BLOCK.get(), CABlocks.PINK_TOURMALINE_BLOCK.get(), CABlocks.CATS_EYE_BLOCK.get(), CABlocks.SUNSTONE_BLOCK.get(), CABlocks.BLOODSTONE_BLOCK.get());
			this.tag(BlockTags.CROPS).add(CABlocks.LETTUCE.get(), CABlocks.RADISH.get());
			this.tag(BlockTags.STANDING_SIGNS).add(CABlocks.APPLE_SIGN.get(), CABlocks.CHERRY_SIGN.get(), CABlocks.DUPLICATION_SIGN.get(), CABlocks.GINKGO_SIGN.get(), CABlocks.PEACH_SIGN.get(), CABlocks.SKYWOOD_SIGN.get());
			this.tag(BlockTags.WALL_SIGNS).add(CABlocks.APPLE_WALL_SIGN.get(), CABlocks.CHERRY_WALL_SIGN.get(), CABlocks.DUPLICATION_WALL_SIGN.get(), CABlocks.GINKGO_WALL_SIGN.get(), CABlocks.PEACH_WALL_SIGN.get(), CABlocks.SKYWOOD_WALL_SIGN.get());
			this.tag(BlockTags.WOODEN_TRAPDOORS).add(CABlocks.APPLE_TRAPDOOR.get(), CABlocks.CHERRY_TRAPDOOR.get(), CABlocks.DUPLICATION_TRAPDOOR.get(), CABlocks.GINKGO_TRAPDOOR.get(), CABlocks.PEACH_TRAPDOOR.get(), CABlocks.SKYWOOD_TRAPDOOR.get());
			this.tag(BlockTags.DOORS).add(CABlocks.APPLE_DOOR.get(), CABlocks.CHERRY_DOOR.get(), CABlocks.DUPLICATION_DOOR.get(), CABlocks.GINKGO_DOOR.get(), CABlocks.PEACH_DOOR.get(), CABlocks.SKYWOOD_DOOR.get());
			this.tag(BlockTags.ENDERMAN_HOLDABLE).add(CABlocks.DENSE_GRASS_BLOCK.get(), CABlocks.DENSE_DIRT.get());
			this.tag(BlockTags.BASE_STONE_OVERWORLD).add(CABlocks.MARBLE.get()).add(CABlocks.LIMESTONE.get()).add(CABlocks.RHINESTONE.get());
			this.tag(BlockTags.WALLS).add(CABlocks.MARBLE_WALL.get()).add(CABlocks.CHISELED_MARBLE_BRICK_WALL.get()).add(CABlocks.CRACKED_MARBLE_BRICK_WALL.get()).add(CABlocks.MARBLE_BRICK_WALL.get()).add(CABlocks.POLISHED_MARBLE_WALL.get()).add(CABlocks.MOSSY_MARBLE_BRICK_WALL.get()).add(CABlocks.LIMESTONE_WALL.get()).add(CABlocks.CHISELED_LIMESTONE_BRICK_WALL.get()).add(CABlocks.CRACKED_LIMESTONE_BRICK_WALL.get()).add(CABlocks.LIMESTONE_BRICK_WALL.get()).add(CABlocks.POLISHED_LIMESTONE_WALL.get()).add(CABlocks.MOSSY_LIMESTONE_BRICK_WALL.get()).add(CABlocks.ROBO_WALL_I.get()).add(CABlocks.ROBO_WALL_X.get()).add(CABlocks.BLACK_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.BLUE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.BROWN_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CYAN_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.GRAY_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.GREEN_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.LIME_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.MAGENTA_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.ORANGE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.PINK_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.PURPLE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.RED_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.TERRACOTTA_BRICK_WALL.get()).add(CABlocks.WHITE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.YELLOW_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_BLACK_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_BLUE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_BROWN_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_CYAN_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_GRAY_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_GREEN_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_LIGHT_BLUE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_LIGHT_GRAY_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_LIME_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_MAGENTA_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_ORANGE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_PINK_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_PURPLE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_RED_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_WHITE_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.CRACKED_YELLOW_TERRACOTTA_BRICK_WALL.get()).add(CABlocks.RHINESTONE_WALL.get()).add(CABlocks.CRACKED_RHINESTONE_BRICK_WALL.get()).add(CABlocks.MOSSY_RHINESTONE_BRICK_WALL.get()).add(CABlocks.POLISHED_RHINESTONE_WALL.get());

			this.tag(Tags.Blocks.DIRT).add(CABlocks.CRYSTAL_GRASS_BLOCK.get(), CABlocks.DENSE_GRASS_BLOCK.get(), CABlocks.DENSE_DIRT.get());
			this.tag(Tags.Blocks.STONE).add(CABlocks.KYANITE.get(), CABlocks.MARBLE.get(), CABlocks.LIMESTONE.get());
			this.tag(Tags.Blocks.ORES).add(CABlocks.ALUMINUM_ORE.get(), CABlocks.AMETHYST_ORE.get(), CABlocks.RUBY_ORE.get(), CABlocks.TIGERS_EYE_ORE.get(), CABlocks.TITANIUM_ORE.get(), CABlocks.URANIUM_ORE.get(), CABlocks.SALT_ORE.get(), CABlocks.COPPER_ORE.get(), CABlocks.TIN_ORE.get(), CABlocks.SILVER_ORE.get(), CABlocks.PLATINUM_ORE.get(), CABlocks.SUNSTONE_ORE.get(), CABlocks.BLOODSTONE_ORE.get());
			this.tag(Tags.Blocks.STORAGE_BLOCKS).add(CABlocks.ALUMINUM_BLOCK.get(), CABlocks.AMETHYST_BLOCK.get(), CABlocks.RUBY_BLOCK.get(), CABlocks.TIGERS_EYE_BLOCK.get(), CABlocks.TITANIUM_BLOCK.get(), CABlocks.URANIUM_BLOCK.get(), CABlocks.SALT_BLOCK.get(), CABlocks.CATS_EYE_BLOCK.get(), CABlocks.PINK_TOURMALINE_BLOCK.get(), CABlocks.SALT_BLOCK.get(), CABlocks.COPPER_BLOCK.get(), CABlocks.TIN_BLOCK.get(), CABlocks.SILVER_BLOCK.get(), CABlocks.PLATINUM_BLOCK.get(), CABlocks.SUNSTONE_BLOCK.get(), CABlocks.BLOODSTONE_BLOCK.get());
		}
	}

	public static class CAItemTagProvider extends ItemTagsProvider {
		public CAItemTagProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
			super(gen, new CABlockTagProvider(gen, existingFileHelper), ChaosAwakens.MODID, existingFileHelper);
		}

		protected Path getPath(ResourceLocation resourceLocation) {
			return this.generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/tags/items/" + resourceLocation.getPath() + ".json");
		}

		@Override
		public String getName() {
			return ChaosAwakens.MODNAME + ": Item Tags";
		}

		@Override
		protected void addTags() {
			this.tag(CATags.Items.APPLE_LOGS).add(CABlocks.APPLE_LOG.get().asItem(), CABlocks.STRIPPED_APPLE_LOG.get().asItem(), CABlocks.APPLE_WOOD.get().asItem(), CABlocks.STRIPPED_APPLE_WOOD.get().asItem());
			this.tag(CATags.Items.CHERRY_LOGS).add(CABlocks.CHERRY_LOG.get().asItem(), CABlocks.STRIPPED_CHERRY_LOG.get().asItem(), CABlocks.CHERRY_WOOD.get().asItem(), CABlocks.STRIPPED_CHERRY_WOOD.get().asItem());
			this.tag(CATags.Items.DUPLICATION_LOGS).add(CABlocks.DUPLICATION_LOG.get().asItem(), CABlocks.STRIPPED_DUPLICATION_LOG.get().asItem(), CABlocks.DEAD_DUPLICATION_LOG.get().asItem(), CABlocks.DUPLICATION_WOOD.get().asItem(), CABlocks.STRIPPED_DUPLICATION_WOOD.get().asItem(), CABlocks.DEAD_DUPLICATION_WOOD.get().asItem());
			this.tag(CATags.Items.PEACH_LOGS).add(CABlocks.PEACH_LOG.get().asItem(), CABlocks.STRIPPED_PEACH_LOG.get().asItem(), CABlocks.PEACH_WOOD.get().asItem(), CABlocks.STRIPPED_PEACH_WOOD.get().asItem());
			this.tag(CATags.Items.SKYWOOD_LOGS).add(CABlocks.SKYWOOD_LOG.get().asItem(), CABlocks.STRIPPED_SKYWOOD_LOG.get().asItem(), CABlocks.SKYWOOD_WOOD.get().asItem(), CABlocks.STRIPPED_SKYWOOD_WOOD.get().asItem());
			this.tag(CATags.Items.GINKGO_LOGS).add(CABlocks.GINKGO_LOG.get().asItem(), CABlocks.STRIPPED_GINKGO_LOG.get().asItem(), CABlocks.GINKGO_WOOD.get().asItem(), CABlocks.STRIPPED_GINKGO_WOOD.get().asItem());
			this.tag(CATags.Items.CRYSTAL_LOGS).add(CABlocks.CRYSTAL_LOG.get().asItem(), CABlocks.CRYSTAL_WOOD.get().asItem());
			this.tag(CATags.Items.CRYSTAL_LEAVES).add(CABlocks.RED_CRYSTAL_LEAVES.get().asItem(), CABlocks.GREEN_CRYSTAL_LEAVES.get().asItem(), CABlocks.YELLOW_CRYSTAL_LEAVES.get().asItem());
			this.tag(CATags.Items.CRYSTAL_SAPLING).add(CABlocks.RED_CRYSTAL_SAPLING.get().asItem(), CABlocks.GREEN_CRYSTAL_SAPLING.get().asItem(), CABlocks.YELLOW_CRYSTAL_SAPLING.get().asItem());
			this.tag(CATags.Items.PLANTS).add(CAItems.STRAWBERRY.get(), CAItems.CHERRIES.get(), CAItems.TOMATO.get(), CAItems.PEACH.get(), CAItems.CORN.get(), CAItems.LETTUCE.get(), CAItems.RADISH.get());
			this.tag(CATags.Items.SEEDS).add(CAItems.STRAWBERRY_SEEDS.get(), CAItems.TOMATO_SEEDS.get(), CAItems.CORN_SEEDS.get(), CAItems.LETTUCE_SEEDS.get(), CAItems.RADISH_SEEDS.get());
			this.tag(CATags.Items.ULTIMATE_GEAR_HANDLES).add(CAItems.PLATINUM_LUMP.get()).addOptionalTag(new ResourceLocation("forge", "ingots/platinum"));
			this.tag(CATags.Items.FISH).add(CAItems.GREEN_FISH.get(), CAItems.ROCK_FISH.get(), CAItems.SPARK_FISH.get(), CAItems.WOOD_FISH.get());
			this.tag(CATags.Items.GEMSTONES).add(CAItems.AMETHYST.get(), CAItems.RUBY.get(), CAItems.TIGERS_EYE.get(), CAItems.SUNSTONE.get(), CAItems.BLOODSTONE.get());
			this.tag(CATags.Items.NUGGETS).add(CAItems.ALUMINUM_NUGGET.get(), CAItems.TITANIUM_NUGGET.get(), CAItems.URANIUM_NUGGET.get(), CAItems.CATS_EYE_NUGGET.get(), CAItems.PINK_TOURMALINE_NUGGET.get());
			this.tag(CATags.Items.INGOTS).add(CAItems.ALUMINUM_INGOT.get(), CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get(), CAItems.CATS_EYE_INGOT.get(), CAItems.PINK_TOURMALINE_INGOT.get());
			this.tag(CATags.Items.MINERAL_LUMPS).add(CAItems.COPPER_LUMP.get(), CAItems.PLATINUM_LUMP.get(), CAItems.SILVER_LUMP.get(), CAItems.TIN_LUMP.get());
			this.tag(CATags.Items.RUBY_ORES).add(CABlocks.RUBY_ORE.get().asItem(), CABlocks.NETHERRACK_RUBY_ORE.get().asItem(), CABlocks.BLACKSTONE_RUBY_ORE.get().asItem());
			this.tag(CATags.Items.LEAF_CARPETS).add(CABlocks.ACACIA_LEAF_CARPET.get().asItem(), CABlocks.BIRCH_LEAF_CARPET.get().asItem(), CABlocks.DARK_OAK_LEAF_CARPET.get().asItem(), CABlocks.JUNGLE_LEAF_CARPET.get().asItem(), CABlocks.OAK_LEAF_CARPET.get().asItem(), CABlocks.SPRUCE_LEAF_CARPET.get().asItem(), CABlocks.APPLE_LEAF_CARPET.get().asItem(), CABlocks.CHERRY_LEAF_CARPET.get().asItem(), CABlocks.DUPLICATION_LEAF_CARPET.get().asItem(), CABlocks.GINKGO_LEAF_CARPET.get().asItem(), CABlocks.PEACH_LEAF_CARPET.get().asItem(), CABlocks.SKYWOOD_LEAF_CARPET.get().asItem());

			this.tag(ItemTags.LOGS).addTags(CATags.Items.DUPLICATION_LOGS);
			this.tag(ItemTags.LOGS_THAT_BURN).addTags(CATags.Items.APPLE_LOGS, CATags.Items.CHERRY_LOGS, CATags.Items.PEACH_LOGS, CATags.Items.SKYWOOD_LOGS, CATags.Items.GINKGO_LOGS, CATags.Items.CRYSTAL_LOGS);
			this.tag(ItemTags.PLANKS).add(CABlocks.APPLE_PLANKS.get().asItem(), CABlocks.CHERRY_PLANKS.get().asItem(), CABlocks.PEACH_PLANKS.get().asItem(), CABlocks.DUPLICATION_PLANKS.get().asItem(), CABlocks.SKYWOOD_PLANKS.get().asItem(), CABlocks.GINKGO_PLANKS.get().asItem(), CABlocks.MOLDY_PLANKS.get().asItem());
			this.tag(ItemTags.LEAVES).add(CABlocks.APPLE_LEAVES.get().asItem(), CABlocks.CHERRY_LEAVES.get().asItem(), CABlocks.PEACH_LEAVES.get().asItem(), CABlocks.SKYWOOD_LEAVES.get().asItem(), CABlocks.GINKGO_LEAVES.get().asItem());
			this.tag(ItemTags.SAPLINGS).add(CABlocks.APPLE_SAPLING.get().asItem(), CABlocks.CHERRY_SAPLING.get().asItem(), CABlocks.PEACH_SAPLING.get().asItem());
			this.tag(ItemTags.WOODEN_SLABS).add(CABlocks.APPLE_SLAB.get().asItem(), CABlocks.CHERRY_SLAB.get().asItem(), CABlocks.PEACH_SLAB.get().asItem(), CABlocks.DUPLICATION_SLAB.get().asItem(), CABlocks.SKYWOOD_SLAB.get().asItem(), CABlocks.GINKGO_SLAB.get().asItem(), CABlocks.MOLDY_SLAB.get().asItem(), CABlocks.CRYSTAL_SLAB.get().asItem());
			this.tag(ItemTags.WOODEN_STAIRS).add(CABlocks.APPLE_STAIRS.get().asItem(), CABlocks.CHERRY_STAIRS.get().asItem(), CABlocks.PEACH_STAIRS.get().asItem(), CABlocks.DUPLICATION_STAIRS.get().asItem(), CABlocks.SKYWOOD_STAIRS.get().asItem(), CABlocks.GINKGO_STAIRS.get().asItem(), CABlocks.CRYSTAL_STAIRS.get().asItem());
			this.tag(ItemTags.WOODEN_FENCES).add(CABlocks.APPLE_FENCE.get().asItem(), CABlocks.CHERRY_FENCE.get().asItem(), CABlocks.PEACH_FENCE.get().asItem(), CABlocks.DUPLICATION_FENCE.get().asItem(), CABlocks.SKYWOOD_FENCE.get().asItem(), CABlocks.GINKGO_FENCE.get().asItem(), CABlocks.MOLDY_FENCE.get().asItem(), CABlocks.CRYSTAL_FENCE.get().asItem());
			this.tag(ItemTags.WOODEN_BUTTONS).add(CABlocks.APPLE_BUTTON.get().asItem(), CABlocks.CHERRY_BUTTON.get().asItem(), CABlocks.PEACH_BUTTON.get().asItem(), CABlocks.DUPLICATION_BUTTON.get().asItem(), CABlocks.SKYWOOD_BUTTON.get().asItem(), CABlocks.GINKGO_BUTTON.get().asItem(), CABlocks.CRYSTAL_BUTTON.get().asItem());
			this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(CABlocks.APPLE_PRESSURE_PLATE.get().asItem(), CABlocks.CHERRY_PRESSURE_PLATE.get().asItem(), CABlocks.PEACH_PRESSURE_PLATE.get().asItem(), CABlocks.DUPLICATION_PRESSURE_PLATE.get().asItem(), CABlocks.SKYWOOD_PRESSURE_PLATE.get().asItem(), CABlocks.GINKGO_PRESSURE_PLATE.get().asItem(), CABlocks.CRYSTAL_PRESSURE_PLATE.get().asItem());
			this.tag(ItemTags.SMALL_FLOWERS).add(CABlocks.CYAN_ROSE.get().asItem(), CABlocks.RED_ROSE.get().asItem(), CABlocks.PAEONIA.get().asItem(), CABlocks.BLUE_BULB.get().asItem(), CABlocks.PINK_BULB.get().asItem(), CABlocks.PURPLE_BULB.get().asItem(), CABlocks.RED_CRYSTAL_FLOWER.get().asItem(), CABlocks.GREEN_CRYSTAL_FLOWER.get().asItem(), CABlocks.YELLOW_CRYSTAL_FLOWER.get().asItem(), CABlocks.BLUE_CRYSTAL_FLOWER.get().asItem(), CABlocks.RED_CRYSTAL_GROWTH.get().asItem(), CABlocks.BLUE_CRYSTAL_GROWTH.get().asItem(), CABlocks.GREEN_CRYSTAL_GROWTH.get().asItem(), CABlocks.YELLOW_CRYSTAL_GROWTH.get().asItem(), CABlocks.ORANGE_CRYSTAL_GROWTH.get().asItem(), CABlocks.PINK_CRYSTAL_GROWTH.get().asItem());
			this.tag(ItemTags.ARROWS).add(CAItems.IRUKANDJI_ARROW.get());
			this.tag(ItemTags.BEACON_PAYMENT_ITEMS).addTags(CATags.Items.GEMSTONES, CATags.Items.INGOTS, CATags.Items.MINERAL_LUMPS);

			this.tag(Tags.Items.STONE).add(CABlocks.KYANITE.get().asItem());
			this.tag(Tags.Items.ORES).add(CABlocks.ALUMINUM_ORE.get().asItem(), CABlocks.AMETHYST_ORE.get().asItem(), CABlocks.RUBY_ORE.get().asItem(), CABlocks.TIGERS_EYE_ORE.get().asItem(), CABlocks.TITANIUM_ORE.get().asItem(), CABlocks.URANIUM_ORE.get().asItem(), CABlocks.SALT_ORE.get().asItem(), CABlocks.COPPER_ORE.get().asItem(), CABlocks.TIN_ORE.get().asItem(), CABlocks.SILVER_ORE.get().asItem(), CABlocks.PLATINUM_ORE.get().asItem(), CABlocks.SUNSTONE_ORE.get().asItem(), CABlocks.BLOODSTONE_ORE.get().asItem());
			this.tag(Tags.Items.STORAGE_BLOCKS).add(CABlocks.ALUMINUM_BLOCK.get().asItem(), CABlocks.AMETHYST_BLOCK.get().asItem(), CABlocks.RUBY_BLOCK.get().asItem(), CABlocks.TIGERS_EYE_BLOCK.get().asItem(), CABlocks.TITANIUM_BLOCK.get().asItem(), CABlocks.URANIUM_BLOCK.get().asItem(), CABlocks.CATS_EYE_BLOCK.get().asItem(), CABlocks.PINK_TOURMALINE_BLOCK.get().asItem(), CABlocks.SALT_BLOCK.get().asItem(), CABlocks.COPPER_BLOCK.get().asItem(), CABlocks.TIN_BLOCK.get().asItem(), CABlocks.SILVER_BLOCK.get().asItem(), CABlocks.PLATINUM_BLOCK.get().asItem(), CABlocks.SUNSTONE_BLOCK.get().asItem(), CABlocks.BLOODSTONE_BLOCK.get().asItem());
			this.tag(Tags.Items.NUGGETS).addTags(CATags.Items.NUGGETS);
			this.tag(Tags.Items.INGOTS).addTags(CATags.Items.INGOTS);
			this.tag(Tags.Items.GEMS).addTags(CATags.Items.GEMSTONES);
			this.tag(Tags.Items.CROPS).add(CAItems.CHERRIES.get(), CAItems.CORN.get(), CAItems.LETTUCE.get(), CAItems.RADISH.get(), CAItems.STRAWBERRY.get(), CAItems.TOMATO.get());
			this.tag(Tags.Items.SEEDS).add(CAItems.CORN_SEEDS.get(), CAItems.LETTUCE_SEEDS.get(), CAItems.RADISH_SEEDS.get(), CAItems.STRAWBERRY_SEEDS.get(), CAItems.TOMATO_SEEDS.get());

			this.tag(CATags.Items.FOSSILS).addTags(CATags.Items.FOSSILS_STONE, CATags.Items.FOSSILS_GRAVEL, CATags.Items.FOSSILS_SAND, CATags.Items.FOSSILS_SANDSTONE, CATags.Items.FOSSILS_ICE, CATags.Items.FOSSILS_NETHERRACK, CATags.Items.FOSSILS_BLACKSTONE, CATags.Items.FOSSILS_SOUL_SOIL, CATags.Items.FOSSILS_END_STONE, CATags.Items.FOSSILS_KYANITE);
			this.tag(CATags.Items.FOSSILS_STONE).addTags(CATags.Items.FOSSILS_STONE_VANILLA, CATags.Items.FOSSILS_STONE_CHAOSAWAKENS);
			this.tag(CATags.Items.FOSSILS_STONE_VANILLA).add(CABlocks.FOSSILISED_BAT.get().asItem(), CABlocks.FOSSILISED_BEE.get().asItem(), CABlocks.FOSSILISED_CAVE_SPIDER.get().asItem(), CABlocks.FOSSILISED_CHICKEN.get().asItem(),
					CABlocks.FOSSILISED_COW.get().asItem(), CABlocks.FOSSILISED_CREEPER.get().asItem(), CABlocks.FOSSILISED_DONKEY.get().asItem(), CABlocks.FOSSILISED_ENDERMAN.get().asItem(),
					CABlocks.FOSSILISED_EVOKER.get().asItem(), CABlocks.FOSSILISED_FOX.get().asItem(), CABlocks.FOSSILISED_GIANT.get().asItem(), CABlocks.FOSSILISED_HORSE.get().asItem(),
					CABlocks.FOSSILISED_HUSK.get().asItem(), CABlocks.FOSSILISED_ILLUSIONER.get().asItem(), CABlocks.FOSSILISED_IRON_GOLEM.get().asItem(), CABlocks.FOSSILISED_LLAMA.get().asItem(),
					CABlocks.FOSSILISED_MOOSHROOM.get().asItem(), CABlocks.FOSSILISED_OCELOT.get().asItem(), CABlocks.FOSSILISED_PANDA.get().asItem(), CABlocks.FOSSILISED_PIG.get().asItem(),
					CABlocks.FOSSILISED_PHANTOM.get().asItem(), CABlocks.FOSSILISED_PILLAGER.get().asItem(), CABlocks.FOSSILISED_RABBIT.get().asItem(), CABlocks.FOSSILISED_RAVAGER.get().asItem(),
					CABlocks.FOSSILISED_SHEEP.get().asItem(), CABlocks.FOSSILISED_SKELETON.get().asItem(), CABlocks.FOSSILISED_SKELETON_HORSE.get().asItem(), CABlocks.FOSSILISED_SLIME.get().asItem(),
					CABlocks.FOSSILISED_SPIDER.get().asItem(), CABlocks.FOSSILISED_VILLAGER.get().asItem(), CABlocks.FOSSILISED_VINDICATOR.get().asItem(), CABlocks.FOSSILISED_WANDERING_TRADER.get().asItem(),
					CABlocks.FOSSILISED_WITCH.get().asItem(), CABlocks.FOSSILISED_WOLF.get().asItem(), CABlocks.FOSSILISED_ZOMBIE.get().asItem(), CABlocks.FOSSILISED_ZOMBIE_HORSE.get().asItem());
			this.tag(CATags.Items.FOSSILS_STONE_CHAOSAWAKENS).add(CABlocks.FOSSILISED_ACACIA_ENT.get().asItem(), CABlocks.FOSSILISED_BIRCH_ENT.get().asItem(), CABlocks.FOSSILISED_DARK_OAK_ENT.get().asItem(), CABlocks.FOSSILISED_JUNGLE_ENT.get().asItem(),
					CABlocks.FOSSILISED_OAK_ENT.get().asItem(), CABlocks.FOSSILISED_SPRUCE_ENT.get().asItem(), CABlocks.FOSSILISED_HERCULES_BEETLE.get().asItem(), CABlocks.FOSSILISED_RUBY_BUG.get().asItem(), CABlocks.FOSSILISED_BEAVER.get().asItem(),
					CABlocks.FOSSILISED_EMERALD_GATOR.get().asItem(), CABlocks.FOSSILISED_WTF.get().asItem(), CABlocks.FOSSILISED_SCORPION.get().asItem(), CABlocks.FOSSILISED_WASP.get().asItem(),
					CABlocks.FOSSILISED_PIRAPORU.get().asItem(), CABlocks.FOSSILISED_APPLE_COW.get().asItem(), CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get().asItem(), CABlocks.FOSSILISED_CARROT_PIG.get().asItem(),
					CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get().asItem(), CABlocks.FOSSILISED_BIRD.get().asItem(), CABlocks.FOSSILISED_TREE_FROG.get().asItem(), CABlocks.FOSSILISED_DIMETRODON.get().asItem());
			this.tag(CATags.Items.FOSSILS_GRAVEL).addTags(CATags.Items.FOSSILS_GRAVEL_VANILLA, CATags.Items.FOSSILS_GRAVEL_CHAOSAWAKENS);
			this.tag(CATags.Items.FOSSILS_GRAVEL_VANILLA).add(CABlocks.FOSSILISED_COD.get().asItem(), CABlocks.FOSSILISED_DOLPHIN.get().asItem(), CABlocks.FOSSILISED_DROWNED.get().asItem(), CABlocks.FOSSILISED_GUARDIAN.get().asItem(),
					CABlocks.FOSSILISED_PUFFERFISH.get().asItem(), CABlocks.FOSSILISED_SALMON.get().asItem(), CABlocks.FOSSILISED_SQUID.get().asItem(), CABlocks.FOSSILISED_TROPICAL_FISH.get().asItem(),
					CABlocks.FOSSILISED_TURTLE.get().asItem());
			this.tag(CATags.Items.FOSSILS_GRAVEL_CHAOSAWAKENS).add(CABlocks.FOSSILISED_GREEN_FISH.get().asItem(), CABlocks.FOSSILISED_ROCK_FISH.get().asItem(), CABlocks.FOSSILISED_SPARK_FISH.get().asItem(), CABlocks.FOSSILISED_WOOD_FISH.get().asItem(),
					CABlocks.FOSSILISED_WHALE.get().asItem());
			this.tag(CATags.Items.FOSSILS_SAND).addTags(CATags.Items.FOSSILS_SAND_VANILLA, CATags.Items.FOSSILS_SAND_CHAOSAWAKENS);
			this.tag(CATags.Items.FOSSILS_SAND_VANILLA).add(Items.AIR);
			this.tag(CATags.Items.FOSSILS_SAND_CHAOSAWAKENS).add(Items.AIR);
			this.tag(CATags.Items.FOSSILS_SANDSTONE).addTags(CATags.Items.FOSSILS_SANDSTONE_VANILLA, CATags.Items.FOSSILS_SANDSTONE_CHAOSAWAKENS);
			this.tag(CATags.Items.FOSSILS_SANDSTONE_VANILLA).add(CABlocks.FOSSILISED_HUSK_SANDSTONE.get().asItem());
			this.tag(CATags.Items.FOSSILS_SANDSTONE_CHAOSAWAKENS).add(Items.AIR);
			this.tag(CATags.Items.FOSSILS_ICE).addTags(CATags.Items.FOSSILS_ICE_VANILLA, CATags.Items.FOSSILS_ICE_CHAOSAWAKENS);
			this.tag(CATags.Items.FOSSILS_ICE_VANILLA).add(CABlocks.FROZEN_POLAR_BEAR.get().asItem(), CABlocks.FROZEN_SNOW_GOLEM.get().asItem(), CABlocks.FROZEN_STRAY.get().asItem());
			this.tag(CATags.Items.FOSSILS_ICE_CHAOSAWAKENS).add(Items.AIR);
			this.tag(CATags.Items.FOSSILS_NETHERRACK).addTags(CATags.Items.FOSSILS_NETHERRACK_VANILLA, CATags.Items.FOSSILS_NETHERRACK_CHAOSAWAKENS);
			this.tag(CATags.Items.FOSSILS_NETHERRACK_VANILLA).add(CABlocks.FOSSILISED_BLAZE.get().asItem(), CABlocks.FOSSILISED_GHAST.get().asItem(), CABlocks.FOSSILISED_HOGLIN.get().asItem(), CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get().asItem(),
					CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get().asItem(), CABlocks.FOSSILISED_PIGLIN.get().asItem(), CABlocks.FOSSILISED_STRIDER.get().asItem(), CABlocks.FOSSILISED_WITHER_SKELETON.get().asItem(),
					CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get().asItem());
			this.tag(CATags.Items.FOSSILS_NETHERRACK_CHAOSAWAKENS).add(CABlocks.FOSSILISED_CRIMSON_ENT.get().asItem(), CABlocks.FOSSILISED_WARPED_ENT.get().asItem(), CABlocks.FOSSILISED_LAVA_EEL.get().asItem());
			this.tag(CATags.Items.FOSSILS_BLACKSTONE).addTags(CATags.Items.FOSSILS_BLACKSTONE_VANILLA, CATags.Items.FOSSILS_BLACKSTONE_CHAOSAWAKENS);
			this.tag(CATags.Items.FOSSILS_BLACKSTONE_VANILLA).add(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get().asItem());
			this.tag(CATags.Items.FOSSILS_BLACKSTONE_CHAOSAWAKENS).add(Items.AIR);
			this.tag(CATags.Items.FOSSILS_SOUL_SOIL).addTags(CATags.Items.FOSSILS_SOUL_SOIL_VANILLA, CATags.Items.FOSSILS_SOUL_SOIL_CHAOSAWAKENS);
			this.tag(CATags.Items.FOSSILS_SOUL_SOIL_VANILLA).add(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get().asItem());
			this.tag(CATags.Items.FOSSILS_SOUL_SOIL_CHAOSAWAKENS).add(Items.AIR);
			this.tag(CATags.Items.FOSSILS_END_STONE).addTags(CATags.Items.FOSSILS_END_STONE_VANILLA, CATags.Items.FOSSILS_END_STONE_CHAOSAWAKENS);
			this.tag(CATags.Items.FOSSILS_END_STONE_VANILLA).add(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get().asItem(), CABlocks.FOSSILISED_ENDERMITE.get().asItem(), CABlocks.FOSSILISED_SHULKER.get().asItem());
			this.tag(CATags.Items.FOSSILS_END_STONE_CHAOSAWAKENS).add(Items.AIR);
			this.tag(CATags.Items.FOSSILS_KYANITE).add(CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get().asItem());

			this.tag(CATags.Items.ARCHAEOLOGIST_FOSSILS_COMMON).add(CABlocks.FOSSILISED_BEE.get().asItem(), CABlocks.FOSSILISED_BIRD.get().asItem(), CABlocks.FOSSILISED_COW.get().asItem(), CABlocks.FOSSILISED_DONKEY.get().asItem(), CABlocks.FOSSILISED_DOLPHIN.get().asItem(), CABlocks.FOSSILISED_COD.get().asItem(), CABlocks.FOSSILISED_SALMON.get().asItem(), CABlocks.FOSSILISED_PIG.get().asItem(), CABlocks.FOSSILISED_SHEEP.get().asItem());
			this.tag(CATags.Items.ARCHAEOLOGIST_FOSSILS_UNCOMMON).add(CABlocks.FOSSILISED_RABBIT.get().asItem(), CABlocks.FOSSILISED_TREE_FROG.get().asItem(), CABlocks.FOSSILISED_APPLE_COW.get().asItem(), CABlocks.FOSSILISED_CARROT_PIG.get().asItem(), CABlocks.FOSSILISED_CREEPER.get().asItem(), CABlocks.FOSSILISED_ZOMBIE.get().asItem(), CABlocks.FOSSILISED_DROWNED.get().asItem(), CABlocks.FOSSILISED_SKELETON.get().asItem(), CABlocks.FOSSILISED_FOX.get().asItem(), CABlocks.FOSSILISED_CHICKEN.get().asItem(), CABlocks.FOSSILISED_HORSE.get().asItem(), CABlocks.FOSSILISED_WITCH.get().asItem(), CABlocks.FOSSILISED_WOLF.get().asItem());
			this.tag(CATags.Items.ARCHAEOLOGIST_FOSSILS_RARE).add(CABlocks.FROZEN_STRAY.get().asItem(), CABlocks.FOSSILISED_HUSK.get().asItem(), CABlocks.FOSSILISED_HUSK_SANDSTONE.get().asItem(), CABlocks.FOSSILISED_SKELETON_HORSE.get().asItem(), CABlocks.FOSSILISED_ZOMBIE_HORSE.get().asItem(), CABlocks.FOSSILISED_ACACIA_ENT.get().asItem(), CABlocks.FOSSILISED_BIRCH_ENT.get().asItem(), CABlocks.FOSSILISED_JUNGLE_ENT.get().asItem(), CABlocks.FOSSILISED_OAK_ENT.get().asItem(), CABlocks.FOSSILISED_DARK_OAK_ENT.get().asItem(), CABlocks.FOSSILISED_SPRUCE_ENT.get().asItem(), CABlocks.FOSSILISED_WASP.get().asItem(), CABlocks.FOSSILISED_IRON_GOLEM.get().asItem(), CABlocks.FROZEN_SNOW_GOLEM.get().asItem(), CABlocks.FROZEN_POLAR_BEAR.get().asItem(), CABlocks.FOSSILISED_PANDA.get().asItem(), CABlocks.FOSSILISED_WHALE.get().asItem(), CABlocks.FOSSILISED_GHAST.get().asItem());
			this.tag(CATags.Items.ARCHAEOLOGIST_FOSSILS_EPIC).add(CABlocks.FOSSILISED_DIMETRODON.get().asItem(), CABlocks.FOSSILISED_CRIMSON_ENT.get().asItem(), CABlocks.FOSSILISED_WARPED_ENT.get().asItem(), CABlocks.FOSSILISED_HERCULES_BEETLE.get().asItem(), CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get().asItem(), CABlocks.FOSSILISED_GIANT.get().asItem(), CABlocks.FOSSILISED_ENDERMAN.get().asItem(), CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get().asItem(), CABlocks.FOSSILISED_ENDERMAN_END_STONE.get().asItem(), CABlocks.FOSSILISED_EVOKER.get().asItem(), CABlocks.FOSSILISED_CAVE_SPIDER.get().asItem(), CABlocks.FOSSILISED_ILLUSIONER.get().asItem(), CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get().asItem(), CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get().asItem(), CABlocks.FOSSILISED_VINDICATOR.get().asItem(), CABlocks.FOSSILISED_LAVA_EEL.get().asItem(), CABlocks.FOSSILISED_MOOSHROOM.get().asItem(), CABlocks.FOSSILISED_PHANTOM.get().asItem(), CABlocks.FOSSILISED_BLAZE.get().asItem(), CABlocks.FOSSILISED_WITHER_SKELETON.get().asItem());
			this.tag(CATags.Items.ARCHAEOLOGIST_FOSSILS).addTags(CATags.Items.ARCHAEOLOGIST_FOSSILS_COMMON, CATags.Items.ARCHAEOLOGIST_FOSSILS_UNCOMMON, CATags.Items.ARCHAEOLOGIST_FOSSILS_RARE, CATags.Items.ARCHAEOLOGIST_FOSSILS_EPIC);
			this.tag(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_COMMON).add(Items.BEE_SPAWN_EGG, CAItems.BIRD_SPAWN_EGG.get(), Items.COW_SPAWN_EGG, Items.DONKEY_SPAWN_EGG, Items.DOLPHIN_SPAWN_EGG, Items.COD_SPAWN_EGG, Items.SALMON_SPAWN_EGG, Items.PIG_SPAWN_EGG, Items.SHEEP_SPAWN_EGG);
			this.tag(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_UNCOMMON).add(Items.RABBIT_SPAWN_EGG, CAItems.TREE_FROG_SPAWN_EGG.get(), CAItems.APPLE_COW_SPAWN_EGG.get(), CAItems.CARROT_PIG_SPAWN_EGG.get(), Items.CREEPER_SPAWN_EGG, Items.ZOMBIE_SPAWN_EGG, Items.DROWNED_SPAWN_EGG, Items.SKELETON_SPAWN_EGG, Items.FOX_SPAWN_EGG, Items.CHICKEN_SPAWN_EGG, Items.HORSE_SPAWN_EGG, Items.WITCH_SPAWN_EGG, Items.WOLF_SPAWN_EGG);
			this.tag(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_RARE).add(Items.STRAY_SPAWN_EGG, Items.HUSK_SPAWN_EGG, Items.SKELETON_HORSE_SPAWN_EGG, Items.ZOMBIE_HORSE_SPAWN_EGG, CAItems.ACACIA_ENT_SPAWN_EGG.get(), CAItems.BIRCH_ENT_SPAWN_EGG.get(), CAItems.JUNGLE_ENT_SPAWN_EGG.get(), CAItems.OAK_ENT_SPAWN_EGG.get(), CAItems.DARK_OAK_ENT_SPAWN_EGG.get(), CAItems.SPRUCE_ENT_SPAWN_EGG.get(), CAItems.WASP_SPAWN_EGG.get(), CAItems.IRON_GOLEM_SPAWN_EGG.get(), CAItems.SNOW_GOLEM_SPAWN_EGG.get(), Items.POLAR_BEAR_SPAWN_EGG, Items.PANDA_SPAWN_EGG, CAItems.WHALE_SPAWN_EGG.get(), Items.GHAST_SPAWN_EGG);
			this.tag(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_EPIC).add(CAItems.DIMETRODON_SPAWN_EGG.get(), CAItems.CRIMSON_ENT_SPAWN_EGG.get(), CAItems.WARPED_ENT_SPAWN_EGG.get(), CAItems.HERCULES_BEETLE_SPAWN_EGG.get(), CAItems.CRYSTAL_APPLE_COW_SPAWN_EGG.get(), CAItems.GIANT_SPAWN_EGG.get(), Items.ENDERMAN_SPAWN_EGG, Items.EVOKER_SPAWN_EGG, Items.CAVE_SPIDER_SPAWN_EGG, CAItems.ILLUSIONER_SPAWN_EGG.get(), Items.MAGMA_CUBE_SPAWN_EGG, Items.VINDICATOR_SPAWN_EGG, CAItems.LAVA_EEL_SPAWN_EGG.get(), Items.MOOSHROOM_SPAWN_EGG, Items.PHANTOM_SPAWN_EGG, Items.BLAZE_SPAWN_EGG, Items.WITHER_SKELETON_SPAWN_EGG);
			this.tag(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS).addTags(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_COMMON, CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_UNCOMMON, CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_RARE, CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_EPIC);

			this.tag(CATags.Items.SPAWNER_SPAWN_EGGS).add(CAItems.ENDER_DRAGON_SPAWN_EGG.get(), CAItems.WITHER_SPAWN_EGG.get(), CAItems.SNOW_GOLEM_SPAWN_EGG.get(), CAItems.IRON_GOLEM_SPAWN_EGG.get());
			
			this.tag(ItemTags.bind("forge:ingots/copper")).add(CAItems.COPPER_LUMP.get());
		}
	}

	public static class CAEntityTypeTagProvider extends EntityTypeTagsProvider {
		public CAEntityTypeTagProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
			super(gen, ChaosAwakens.MODID, existingFileHelper);
		}

		protected Path getPath(ResourceLocation resourceLocation) {
			return this.generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/tags/entity_types/" + resourceLocation.getPath() + ".json");
		}

		@Override
		public String getName() {
			return ChaosAwakens.MODNAME + ": Entity Tags";
		}

		@Override
		protected void addTags() {
			this.tag(CATags.EntityTypes.CRITTER_CAGE_BLACKLISTED).add(EntityType.ENDER_DRAGON, EntityType.WITHER, CAEntityTypes.ACACIA_ENT.get(), CAEntityTypes.BIRCH_ENT.get(), CAEntityTypes.CRIMSON_ENT.get(), CAEntityTypes.DARK_OAK_ENT.get(), CAEntityTypes.JUNGLE_ENT.get(), CAEntityTypes.OAK_ENT.get(), CAEntityTypes.SPRUCE_ENT.get(), CAEntityTypes.WARPED_ENT.get(), CAEntityTypes.HERCULES_BEETLE.get(), CAEntityTypes.THROWBACK_HERCULES_BEETLE.get(), CAEntityTypes.ROBO_POUNDER.get(), CAEntityTypes.ROBO_WARRIOR.get());
		}
	}
}
