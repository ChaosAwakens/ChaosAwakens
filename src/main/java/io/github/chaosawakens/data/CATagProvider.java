package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.nio.file.Path;

public class CATagProvider extends BlockTagsProvider {
	public CATagProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, ChaosAwakens.MODID, existingFileHelper);
	}

	public static class CATagProviderForBlocks extends BlockTagsProvider {

		/**
		 * Static class for the main tag provider to make a separate package for the
		 * block tags to work properly in crafting recipes and such
		 */

		public CATagProviderForBlocks(DataGenerator gen, ExistingFileHelper existingFileHelper) {
			super(gen, ChaosAwakens.MODID, existingFileHelper);
		}

		protected Path getPath(ResourceLocation resourceLocation) {
			return this.generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/tags/blocks/" + resourceLocation.getPath() + ".json");
		}

		public String getName() {
			return "Chaos Awakens Block Tags";
		}

		@Override
		protected void addTags() {
			this.tag(CATags.Blocks.APPLE_LOGS).add(CABlocks.APPLE_LOG.get(), CABlocks.STRIPPED_APPLE_LOG.get(), CABlocks.APPLE_WOOD.get(), CABlocks.STRIPPED_APPLE_WOOD.get());
			this.tag(CATags.Blocks.CHERRY_LOGS).add(CABlocks.CHERRY_LOG.get(), CABlocks.STRIPPED_CHERRY_LOG.get(), CABlocks.CHERRY_WOOD.get(), CABlocks.STRIPPED_CHERRY_WOOD.get());
			this.tag(CATags.Blocks.DUPLICATION_LOGS).add(CABlocks.DUPLICATION_LOG.get(), CABlocks.STRIPPED_DUPLICATION_LOG.get(), CABlocks.DEAD_DUPLICATION_LOG.get(), CABlocks.DUPLICATION_WOOD.get(), CABlocks.STRIPPED_DUPLICATION_WOOD.get(), CABlocks.DEAD_DUPLICATION_WOOD.get());
			this.tag(CATags.Blocks.PEACH_LOGS).add(CABlocks.PEACH_LOG.get(), CABlocks.STRIPPED_PEACH_LOG.get(), CABlocks.PEACH_WOOD.get(), CABlocks.STRIPPED_PEACH_WOOD.get());
			this.tag(CATags.Blocks.SKYWOOD_LOGS).add(CABlocks.SKYWOOD_LOG.get(), CABlocks.STRIPPED_SKYWOOD_LOG.get(), CABlocks.SKYWOOD_WOOD.get(), CABlocks.STRIPPED_SKYWOOD_WOOD.get());
			this.tag(CATags.Blocks.CRYSTAL_LEAVES).add(CABlocks.RED_CRYSTAL_LEAVES.get(), CABlocks.GREEN_CRYSTAL_LEAVES.get(), CABlocks.YELLOW_CRYSTAL_LEAVES.get());
			this.tag(CATags.Blocks.CRYSTAL_SAPLING).add(CABlocks.RED_CRYSTAL_SAPLING.get(), CABlocks.GREEN_CRYSTAL_SAPLING.get(), CABlocks.YELLOW_CRYSTAL_SAPLING.get());
			this.tag(CATags.Blocks.RUBY_ORES).add(CABlocks.RUBY_ORE.get(), CABlocks.NETHERRACK_RUBY_ORE.get(), CABlocks.BLACKSTONE_RUBY_ORE.get());

			this.tag(CATags.Blocks.BASE_STONE_CRYSTAL).add(CABlocks.KYANITE.get());
			this.tag(CATags.Blocks.BASE_STONE_MINING).addTags(BlockTags.BASE_STONE_OVERWORLD);
			this.tag(CATags.Blocks.BASE_STONE_VILLAGE).addTags(BlockTags.BASE_STONE_OVERWORLD);
			this.tag(CATags.Blocks.MINERS_DREAM_MINEABLE).addTags(BlockTags.BASE_STONE_OVERWORLD, BlockTags.BASE_STONE_NETHER, CATags.Blocks.BASE_STONE_CRYSTAL, Tags.Blocks.STONE, Tags.Blocks.NETHERRACK, Tags.Blocks.SANDSTONE, Tags.Blocks.END_STONES, Tags.Blocks.GRAVEL, Tags.Blocks.SAND).add(Blocks.WATER, Blocks.LAVA);

			this.tag(CATags.Blocks.WHITELIST).addTags(BlockTags.LOGS);
			this.tag(CATags.Blocks.BLACKLIST).add(Blocks.BEDROCK.getBlock());

			this.tag(BlockTags.LOGS).addTags(CATags.Blocks.DUPLICATION_LOGS);
			this.tag(BlockTags.LOGS_THAT_BURN).addTags(CATags.Blocks.APPLE_LOGS, CATags.Blocks.CHERRY_LOGS, CATags.Blocks.PEACH_LOGS, CATags.Blocks.SKYWOOD_LOGS);
			this.tag(BlockTags.PLANKS).add(CABlocks.APPLE_PLANKS.get(), CABlocks.CHERRY_PLANKS.get(), CABlocks.PEACH_PLANKS.get(), CABlocks.DUPLICATION_PLANKS.get(), CABlocks.SKYWOOD_PLANKS.get(), CABlocks.MOLDY_PLANKS.get());
			this.tag(BlockTags.LEAVES).add(CABlocks.APPLE_LEAVES.get(), CABlocks.CHERRY_LEAVES.get(), CABlocks.PEACH_LEAVES.get(), CABlocks.SKYWOOD_LEAVES.get());
			this.tag(BlockTags.FENCE_GATES).add(CABlocks.APPLE_FENCE_GATE.get(), CABlocks.CHERRY_FENCE_GATE.get(), CABlocks.PEACH_FENCE_GATE.get(), CABlocks.DUPLICATION_FENCE_GATE.get(), CABlocks.SKYWOOD_FENCE_GATE.get(), CABlocks.CRYSTAL_FENCE_GATE.get());
			this.tag(BlockTags.WOODEN_FENCES).add(CABlocks.APPLE_FENCE.get(), CABlocks.CHERRY_FENCE.get(), CABlocks.PEACH_FENCE.get(), CABlocks.DUPLICATION_FENCE.get(), CABlocks.SKYWOOD_FENCE.get(), CABlocks.MOLDY_FENCE.get(), CABlocks.CRYSTAL_FENCE.get());
			this.tag(BlockTags.SMALL_FLOWERS).add(CABlocks.CYAN_ROSE.get(), CABlocks.RED_ROSE.get(), CABlocks.PAEONIA.get());
			this.tag(BlockTags.FLOWER_POTS).add(CABlocks.POTTED_CYAN_ROSE.get(), CABlocks.POTTED_RED_ROSE.get(), CABlocks.POTTED_PAEONIA.get());
			this.tag(BlockTags.BEACON_BASE_BLOCKS).add(CABlocks.AMETHYST_BLOCK.get(), CABlocks.RUBY_BLOCK.get(), CABlocks.TIGERS_EYE_BLOCK.get(), CABlocks.TITANIUM_BLOCK.get(), CABlocks.URANIUM_BLOCK.get(), CABlocks.ALUMINUM_BLOCK.get(), CABlocks.COPPER_BLOCK.get(), CABlocks.TIN_BLOCK.get(), CABlocks.SILVER_BLOCK.get(), CABlocks.PLATINUM_BLOCK.get(), CABlocks.PINK_TOURMALINE_BLOCK.get(), CABlocks.CATS_EYE_BLOCK.get(), CABlocks.SUNSTONE_BLOCK.get(), CABlocks.BLOODSTONE_BLOCK.get(), CABlocks.AMETHYST_BLOCK.get());

			this.tag(Tags.Blocks.DIRT).add(CABlocks.CRYSTAL_GRASS_BLOCK.get());
			this.tag(Tags.Blocks.STONE).add(CABlocks.KYANITE.get());
			this.tag(Tags.Blocks.ORES).add(CABlocks.ALUMINUM_ORE.get(), CABlocks.AMETHYST_ORE.get(), CABlocks.RUBY_ORE.get(), CABlocks.TIGERS_EYE_ORE.get(), CABlocks.TITANIUM_ORE.get(), CABlocks.URANIUM_ORE.get(), CABlocks.SALT_ORE.get(), CABlocks.COPPER_ORE.get(), CABlocks.TIN_ORE.get(), CABlocks.SILVER_ORE.get(), CABlocks.PLATINUM_ORE.get(), CABlocks.SUNSTONE_ORE.get(), CABlocks.BLOODSTONE_ORE.get());
			this.tag(Tags.Blocks.STORAGE_BLOCKS).add(CABlocks.ALUMINUM_BLOCK.get(), CABlocks.AMETHYST_BLOCK.get(), CABlocks.RUBY_BLOCK.get(), CABlocks.TIGERS_EYE_BLOCK.get(), CABlocks.TITANIUM_BLOCK.get(), CABlocks.URANIUM_BLOCK.get(), CABlocks.CATS_EYE_BLOCK.get(), CABlocks.PINK_TOURMALINE_BLOCK.get(), CABlocks.COPPER_BLOCK.get(), CABlocks.TIN_BLOCK.get(), CABlocks.SILVER_BLOCK.get(), CABlocks.PLATINUM_BLOCK.get(), CABlocks.SUNSTONE_BLOCK.get(), CABlocks.BLOODSTONE_BLOCK.get());
		}
	}

	// TODO make the static class and the main class function as one instead of having to make them separate
	public static class CAItemTagProvider extends ItemTagsProvider {

		/**
		 * Static class for the main tag provider to make a separate package for the
		 * block tags to work properly in crafting recipes and such
		 */

		public CAItemTagProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
			super(gen, new CATagProviderForBlocks(gen, existingFileHelper), ChaosAwakens.MODID, existingFileHelper);
		}

		protected Path getPath(ResourceLocation resourceLocation) {
			return this.generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/tags/items/" + resourceLocation.getPath() + ".json");
		}

		public String getName() {
			return "Chaos Awakens Item Tags";
		}

		@Override
		protected void addTags() {
			this.tag(CATags.Items.APPLE_LOGS).add(CABlocks.APPLE_LOG.get().asItem(), CABlocks.STRIPPED_APPLE_LOG.get().asItem(), CABlocks.APPLE_WOOD.get().asItem(), CABlocks.STRIPPED_APPLE_WOOD.get().asItem());
			this.tag(CATags.Items.CHERRY_LOGS).add(CABlocks.CHERRY_LOG.get().asItem(), CABlocks.STRIPPED_CHERRY_LOG.get().asItem(), CABlocks.CHERRY_WOOD.get().asItem(), CABlocks.STRIPPED_CHERRY_WOOD.get().asItem());
			this.tag(CATags.Items.DUPLICATION_LOGS).add(CABlocks.DUPLICATION_LOG.get().asItem(), CABlocks.STRIPPED_DUPLICATION_LOG.get().asItem(), CABlocks.DEAD_DUPLICATION_LOG.get().asItem(), CABlocks.DUPLICATION_WOOD.get().asItem(), CABlocks.STRIPPED_DUPLICATION_WOOD.get().asItem(), CABlocks.DEAD_DUPLICATION_WOOD.get().asItem());
			this.tag(CATags.Items.PEACH_LOGS).add(CABlocks.PEACH_LOG.get().asItem(), CABlocks.STRIPPED_PEACH_LOG.get().asItem(), CABlocks.PEACH_WOOD.get().asItem(), CABlocks.STRIPPED_PEACH_WOOD.get().asItem());
			this.tag(CATags.Items.SKYWOOD_LOGS).add(CABlocks.SKYWOOD_LOG.get().asItem(), CABlocks.STRIPPED_SKYWOOD_LOG.get().asItem(), CABlocks.SKYWOOD_WOOD.get().asItem(), CABlocks.STRIPPED_SKYWOOD_WOOD.get().asItem());
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

			this.tag(ItemTags.LOGS).addTags(CATags.Items.DUPLICATION_LOGS);
			this.tag(ItemTags.LOGS_THAT_BURN).addTags(CATags.Items.APPLE_LOGS, CATags.Items.CHERRY_LOGS, CATags.Items.PEACH_LOGS, CATags.Items.SKYWOOD_LOGS);
			this.tag(ItemTags.PLANKS).add(CABlocks.APPLE_PLANKS.get().asItem(), CABlocks.CHERRY_PLANKS.get().asItem(), CABlocks.PEACH_PLANKS.get().asItem(), CABlocks.DUPLICATION_PLANKS.get().asItem(), CABlocks.SKYWOOD_PLANKS.get().asItem(), CABlocks.MOLDY_PLANKS.get().asItem());
			this.tag(ItemTags.LEAVES).add(CABlocks.APPLE_LEAVES.get().asItem(), CABlocks.CHERRY_LEAVES.get().asItem(), CABlocks.PEACH_LEAVES.get().asItem(), CABlocks.SKYWOOD_LEAVES.get().asItem());
			this.tag(ItemTags.SAPLINGS).add(CABlocks.APPLE_SAPLING.get().asItem(), CABlocks.CHERRY_SAPLING.get().asItem(), CABlocks.PEACH_SAPLING.get().asItem());
			this.tag(ItemTags.WOODEN_FENCES).add(CABlocks.APPLE_FENCE.get().asItem(), CABlocks.CHERRY_FENCE.get().asItem(), CABlocks.PEACH_FENCE.get().asItem(), CABlocks.DUPLICATION_FENCE.get().asItem(), CABlocks.SKYWOOD_FENCE.get().asItem(), CABlocks.MOLDY_FENCE.get().asItem(), CABlocks.CRYSTAL_FENCE.get().asItem());
			this.tag(ItemTags.SMALL_FLOWERS).add(CABlocks.CYAN_ROSE.get().asItem(), CABlocks.RED_ROSE.get().asItem(), CABlocks.PAEONIA.get().asItem());
			this.tag(ItemTags.ARROWS).add(CAItems.IRUKANDJI_ARROW.get());
			this.tag(ItemTags.BEACON_PAYMENT_ITEMS).addTags(CATags.Items.GEMSTONES, CATags.Items.INGOTS, CATags.Items.MINERAL_LUMPS);

			this.tag(Tags.Items.STONE).add(CABlocks.KYANITE.get().asItem());
			this.tag(Tags.Items.ORES).add(CABlocks.ALUMINUM_ORE.get().asItem(), CABlocks.AMETHYST_ORE.get().asItem(), CABlocks.RUBY_ORE.get().asItem(), CABlocks.TIGERS_EYE_ORE.get().asItem(), CABlocks.TITANIUM_ORE.get().asItem(), CABlocks.URANIUM_ORE.get().asItem(), CABlocks.SALT_ORE.get().asItem(), CABlocks.COPPER_ORE.get().asItem(), CABlocks.TIN_ORE.get().asItem(), CABlocks.SILVER_ORE.get().asItem(), CABlocks.PLATINUM_ORE.get().asItem(), CABlocks.SUNSTONE_ORE.get().asItem(), CABlocks.BLOODSTONE_ORE.get().asItem());
			this.tag(Tags.Items.STORAGE_BLOCKS).add(CABlocks.ALUMINUM_BLOCK.get().asItem(), CABlocks.AMETHYST_BLOCK.get().asItem(), CABlocks.RUBY_BLOCK.get().asItem(), CABlocks.TIGERS_EYE_BLOCK.get().asItem(), CABlocks.TITANIUM_BLOCK.get().asItem(), CABlocks.URANIUM_BLOCK.get().asItem(), CABlocks.CATS_EYE_BLOCK.get().asItem(), CABlocks.PINK_TOURMALINE_BLOCK.get().asItem(), CABlocks.COPPER_BLOCK.get().asItem(), CABlocks.TIN_BLOCK.get().asItem(), CABlocks.SILVER_BLOCK.get().asItem(), CABlocks.PLATINUM_BLOCK.get().asItem(), CABlocks.SUNSTONE_BLOCK.get().asItem(), CABlocks.BLOODSTONE_BLOCK.get().asItem());
			this.tag(Tags.Items.NUGGETS).addTags(CATags.Items.NUGGETS);
			this.tag(Tags.Items.INGOTS).addTags(CATags.Items.INGOTS);
			this.tag(Tags.Items.GEMS).addTags(CATags.Items.GEMSTONES);
			this.tag(Tags.Items.CROPS).add(CAItems.CHERRIES.get(), CAItems.CORN.get(), CAItems.LETTUCE.get(), CAItems.RADISH.get(), CAItems.STRAWBERRY.get(), CAItems.TOMATO.get());
			this.tag(Tags.Items.SEEDS).add(CAItems.CORN_SEEDS.get(), CAItems.LETTUCE_SEEDS.get(), CAItems.RADISH_SEEDS.get(), CAItems.STRAWBERRY_SEEDS.get(), CAItems.TOMATO_SEEDS.get());


			this.tag(CATags.Items.CUSTOM_TOOLTIPS).add(CAItems.RUBY.get(), CAItems.AMETHYST.get(),
					CAItems.URANIUM_INGOT.get(), CAItems.TITANIUM_INGOT.get(), CAItems.TIGERS_EYE.get(),
					CAItems.SUNSTONE.get(), CAItems.BLOODSTONE.get(), CAItems.HERCULES_BEETLE_SPAWN_EGG.get(),
					CAItems.ACACIA_ENT_SPAWN_EGG.get(), CAItems.BIRCH_ENT_SPAWN_EGG.get(), CAItems.CRIMSON_ENT_SPAWN_EGG.get(),
					CAItems.DARK_OAK_ENT_SPAWN_EGG.get(), CAItems.JUNGLE_ENT_SPAWN_EGG.get(), CAItems.OAK_ENT_SPAWN_EGG.get(),
					CAItems.SPRUCE_ENT_SPAWN_EGG.get(), CAItems.WARPED_ENT_SPAWN_EGG.get(),
					CABlocks.FOSSILISED_WTF.get().asItem(), CABlocks.FOSSILISED_SCORPION.get().asItem(), CABlocks.FOSSILISED_PIRAPORU.get().asItem());
		}
	}
}
