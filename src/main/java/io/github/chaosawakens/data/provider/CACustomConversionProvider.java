package io.github.chaosawakens.data.provider;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CATags;
import moze_intel.projecte.api.data.CustomConversionProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;

public class CACustomConversionProvider extends CustomConversionProvider {
	
	public CACustomConversionProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": ProjectE Custom Conversions";
	}

	@Override
	protected void addCustomConversions() {
		createConversionBuilder(ChaosAwakens.prefix("stones"))
				.before(CABlocks.MARBLE.get(), 16)
				.before(CABlocks.LIMESTONE.get(), 16);
		createConversionBuilder(ChaosAwakens.prefix("plants"))
				.before(CAItems.SALT.get(), 16)
				.before(CATags.Items.SEEDS, 32)
				.before(CATags.Items.PLANTS, 96)
				.before(CAItems.CRYSTAL_APPLE.get(), 33024);
		createConversionBuilder(ChaosAwakens.prefix("gems"))
				.before(CAItems.SUNSTONE.get(), 3_072)
				.before(CAItems.BLOODSTONE.get(), 3_072)
				.before(CAItems.TIGERS_EYE.get(), 24_576)
				.before(CAItems.AMETHYST.get(), 32_768)
				.before(CAItems.RUBY.get(), 131_072);
		createConversionBuilder(ChaosAwakens.prefix("metals"))
				.before(CAItems.COPPER_LUMP.get(), 1_536)
				.before(CAItems.TIN_LUMP.get(), 3_072)
				.before(CAItems.PINK_TOURMALINE_INGOT.get(), 3_072)
				.before(CAItems.SILVER_LUMP.get(), 6_144)
				.before(CAItems.ALUMINUM_INGOT.get(), 6_144)
				.before(CAItems.PLATINUM_LUMP.get(), 12_288)
				.before(CAItems.CATS_EYE_INGOT.get(), 12_288)
				.before(CAItems.URANIUM_NUGGET.get(), 524_288)
				.before(CAItems.TITANIUM_NUGGET.get(), 524_288);
		createConversionBuilder(ChaosAwakens.prefix("animals"))
				.before(CATags.Items.FISH, 96)
				.before(CAItems.LAVA_EEL.get(), 128)
				.before(CAItems.PEACOCK_LEG.get(), 128)
				.before(CAItems.CRAB_MEAT.get(), 128)
				.before(CAItems.PEACOCK_FEATHER.get(), 144)
				.before(CAItems.DEAD_STINK_BUG.get(), 192)
				.conversion(CAItems.GREEN_FISH_BUCKET.get()).ingredient(Items.WATER_BUCKET).ingredient(CAItems.GREEN_FISH.get()).end()
				.conversion(CAItems.ROCK_FISH_BUCKET.get()).ingredient(Items.WATER_BUCKET).ingredient(CAItems.ROCK_FISH.get()).end()
				.conversion(CAItems.SPARK_FISH_BUCKET.get()).ingredient(Items.WATER_BUCKET).ingredient(CAItems.SPARK_FISH.get()).end()
				.conversion(CAItems.WOOD_FISH_BUCKET.get()).ingredient(Items.WATER_BUCKET).ingredient(CAItems.WOOD_FISH.get()).end();
		//		.conversion(CAItems.LAVA_EEL_BUCKET.get()).ingredient(Items.LAVA_BUCKET).ingredient(CAItems.LAVA_EEL.get()).end();
		createConversionBuilder(ChaosAwakens.prefix("ant_nests"))
				.before(CABlocks.BROWN_ANT_NEST.get(), 2)
				.before(CABlocks.RAINBOW_ANT_NEST.get(), 2)
				.before(CABlocks.RED_ANT_NEST.get(), 2)
				.before(CABlocks.UNSTABLE_ANT_NEST.get(), 2)
				.before(CABlocks.TERMITE_NEST.get(), 2)
				.before(CABlocks.CRYSTAL_TERMITE_NEST.get(), 3)
				.before(CABlocks.DENSE_DIRT.get(), 5);
		createConversionBuilder(ChaosAwakens.prefix("crystal"))
				.before(CABlocks.CRYSTAL_GRASS_BLOCK.get(), 2)
				.before(CABlocks.KYANITE.get(), 2)
				.before(CABlocks.CRYSTAL_GRASS.get(), 2)
				.before(CABlocks.TALL_CRYSTAL_GRASS.get(), 2)
				.before(CABlocks.CRYSTALWOOD_LOG.get(), 64)
				.before(CATags.Items.CRYSTAL_LEAVES, 2)
				.before(CATags.Items.CRYSTAL_SAPLING, 64)
				.before(CABlocks.CRYSTAL_ENERGY.get().asItem(), 3_072);
		createConversionBuilder(ChaosAwakens.prefix("dense"))
				.before(CABlocks.DENSE_GRASS_BLOCK.get(), 4)
				.before(CABlocks.DENSE_DIRT.get(), 4)
				.before(CABlocks.DENSE_GRASS.get(), 4)
				.before(CABlocks.TALL_DENSE_GRASS.get(), 4)
				.before(CABlocks.THORNY_SUN.get(), 32);
	}
}
