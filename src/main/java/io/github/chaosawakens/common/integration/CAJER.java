package io.github.chaosawakens.common.integration;

import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CAItems;
import jeresources.api.IWorldGenRegistry;
import jeresources.api.distributions.DistributionSquare;
import jeresources.api.drop.LootDrop;
import jeresources.api.drop.PlantDrop;
import jeresources.api.restrictions.BiomeRestriction;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.compatibility.JERAPI;
import jeresources.entry.PlantEntry;
import jeresources.registry.PlantRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CAJER {
	public static void init() {
		registerDungeonLoot();
		registerPlants();
		registerOres();
	}

	private static void registerDungeonLoot() {
		// ENT DUNGEONS
		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon/acacia_loot", "dungeon.chaosawakens.jer.ent_dungeon.acacia_loot");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon/acacia_loot", new ResourceLocation("chaosawakens:chests/ent_dungeon/acacia_loot"));

		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon/birch_loot", "dungeon.chaosawakens.jer.ent_dungeon.birch_loot");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon/birch_loot", new ResourceLocation("chaosawakens:chests/ent_dungeon/birch_loot"));

		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon/crimson_loot", "dungeon.chaosawakens.jer.ent_dungeon.crimson_loot");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon/crimson_loot", new ResourceLocation("chaosawakens:chests/ent_dungeon/crimson_loot"));

		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon/dark_oak_loot", "dungeon.chaosawakens.jer.ent_dungeon.dark_oak_loot");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon/dark_oak_loot", new ResourceLocation("chaosawakens:chests/ent_dungeon/dark_oak_loot"));

		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon/jungle_loot", "dungeon.chaosawakens.jer.ent_dungeon.jungle_loot");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon/jungle_loot", new ResourceLocation("chaosawakens:chests/ent_dungeon/jungle_loot"));

		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon/jungle_trap", "dungeon.chaosawakens.jer.ent_dungeon.jungle_trap");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon/jungle_trap", new ResourceLocation("chaosawakens:chests/ent_dungeon/jungle_trap"));

		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon/oak_loot", "dungeon.chaosawakens.jer.ent_dungeon.oak_loot");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon/oak_loot", new ResourceLocation("chaosawakens:chests/ent_dungeon/oak_loot"));

		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon/spruce_loot", "dungeon.chaosawakens.jer.ent_dungeon.spruce_loot");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon/spruce_loot", new ResourceLocation("chaosawakens:chests/ent_dungeon/spruce_loot"));

		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon/warped_loot", "dungeon.chaosawakens.jer.ent_dungeon.warped_loot");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon/warped_loot", new ResourceLocation("chaosawakens:chests/ent_dungeon/warped_loot"));

		// WASP DUNGEON
		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/wasp_dungeon/loot", "dungeon.chaosawakens.jer.wasp_dungeon.loot");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/wasp_dungeon/loot", new ResourceLocation("chaosawakens:chests/wasp_dungeon/loot"));

		// VILLAGE
		JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/village/cherry_house", "dungeon.chaosawakens.jer.village_cherry_house");
		JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/village/cherry_house", new ResourceLocation("chaosawakens:chests/village/cherry_house"));
	}

	private static void registerPlants() {
		PlantDrop radish = new PlantDrop(new ItemStack(CAItems.RADISH.get()), 1, 1);
		PlantDrop radishSeeds = new PlantDrop(new ItemStack(CAItems.RADISH_SEEDS.get()), 0, 3);
		PlantRegistry.getInstance().registerPlant(new PlantEntry(CABlocks.RADISH.get(), radish, radishSeeds));

		PlantDrop lettuce = new PlantDrop(new ItemStack(CAItems.LETTUCE.get()), 1, 1);
		PlantDrop lettuceSeeds = new PlantDrop(new ItemStack(CAItems.LETTUCE_SEEDS.get()), 0, 3);
		PlantRegistry.getInstance().registerPlant(new PlantEntry(CABlocks.LETTUCE.get(), lettuce, lettuceSeeds));
	}

	private static void registerOres() {
		IWorldGenRegistry worldGenRegistry = JERAPI.getInstance().getWorldGenRegistry();
		if (CACommonConfig.COMMON.enableOreGen.get()) {
			if (CACommonConfig.COMMON.enableOreAluminumGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.ALUMINUM_ORE.get()),
						new DistributionSquare(8, 5, 16, 144),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
			}

			if (CACommonConfig.COMMON.enableOreAmethystGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.AMETHYST_ORE.get()),
						new DistributionSquare(4, 4, 2, 34),
						true,
						new LootDrop(new ItemStack(CAItems.AMETHYST.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.AMETHYST_ORE.get()),
						new DistributionSquare(4, 4, 24, 56),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
						true,
						new LootDrop(new ItemStack(CAItems.AMETHYST.get())));
			}

			if (CACommonConfig.COMMON.enableOreRubyGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.RUBY_ORE.get()),
						new DistributionSquare(3, 8, 6, 18),
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.NETHERRACK_RUBY_ORE.get()),
						new DistributionSquare(5, 12, 0, 24),
						Restriction.NETHER,
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.NETHERRACK_RUBY_ORE.get()),
						new DistributionSquare(4, 3, 0, 128),
						Restriction.NETHER,
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.BLACKSTONE_RUBY_ORE.get()),
						new DistributionSquare(6, 4, 0, 128),
						Restriction.NETHER,
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.RUBY_ORE.get()),
						new DistributionSquare(4, 8, 6, 18),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
			}

			if (CACommonConfig.COMMON.enableOreTigersEyeGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.TIGERS_EYE_ORE.get()),
						new DistributionSquare(5, 7, 1, 25),
						true,
						new LootDrop(new ItemStack(CAItems.TIGERS_EYE.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.TIGERS_EYE_ORE.get()),
						new DistributionSquare(5, 7, 1, 29),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
						true,
						new LootDrop(new ItemStack(CAItems.TIGERS_EYE.get())));
			}

			if (CACommonConfig.COMMON.enableDzMineralOreGen.get()) {
				if (CACommonConfig.COMMON.spawnDzOresInOverworld.get()) {
					if (CACommonConfig.COMMON.enableOreCopperGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.COPPER_ORE.get()),
								new DistributionSquare(6, 5, 40, 104),
								true,
								LootDropMinMax(new ItemStack(CAItems.COPPER_LUMP.get()), 2, 5));
					}
					if (CACommonConfig.COMMON.enableOreTinGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.TIN_ORE.get()),
								new DistributionSquare(5, 4, 32, 80),
								true,
								new LootDrop(new ItemStack(CAItems.TIN_LUMP.get())));
					}
					if (CACommonConfig.COMMON.enableOreSilverGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.SILVER_ORE.get()),
								new DistributionSquare(4, 3, 12, 52),
								true,
								new LootDrop(new ItemStack(CAItems.SILVER_LUMP.get())));
					}
					if (CACommonConfig.COMMON.enableOrePlatinumGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.PLATINUM_ORE.get()),
								new DistributionSquare(3, 3, 8, 24),
								true,
								new LootDrop(new ItemStack(CAItems.PLATINUM_LUMP.get())));
					}
					if (CACommonConfig.COMMON.enableOreSunstoneGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.SUNSTONE_ORE.get()),
								new DistributionSquare(8, 5, 1, 128),
								true,
								new LootDrop(new ItemStack(CAItems.SUNSTONE.get())));
					}
					if (CACommonConfig.COMMON.enableOreBloodstoneGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.BLOODSTONE_ORE.get()),
								new DistributionSquare(8, 5, 1, 128),
								true,
								new LootDrop(new ItemStack(CAItems.BLOODSTONE.get())));
					}
				}

				if (CACommonConfig.COMMON.enableOreCopperGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.COPPER_ORE.get()),
							new DistributionSquare(6, 5, 34, 110),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							LootDropMinMax(new ItemStack(CAItems.COPPER_LUMP.get()), 2, 5));
				}
				if (CACommonConfig.COMMON.enableOreTinGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.TIN_ORE.get()),
							new DistributionSquare(5, 4, 26, 86),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.TIN_LUMP.get())));
				}
				if (CACommonConfig.COMMON.enableOreSilverGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.SILVER_ORE.get()),
							new DistributionSquare(4, 3, 8, 56),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.SILVER_LUMP.get())));
				}
				if (CACommonConfig.COMMON.enableOrePlatinumGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.PLATINUM_ORE.get()),
							new DistributionSquare(3, 3, 12, 28),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.PLATINUM_LUMP.get())));
				}
				if (CACommonConfig.COMMON.enableOreSunstoneGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.SUNSTONE_ORE.get()),
							new DistributionSquare(8, 5, 8, 136),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.SUNSTONE.get())));
				}
				if (CACommonConfig.COMMON.enableOreBloodstoneGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.BLOODSTONE_ORE.get()),
							new DistributionSquare(8, 5, 8, 136),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.BLOODSTONE.get())));
				}
			}

			if (CACommonConfig.COMMON.enableOreSaltGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.SALT_ORE.get()),
						new DistributionSquare(14, 8, 32, 96),
						true,
						LootDropMinMax(new ItemStack(CAItems.SALT.get()), 4, 8));
				worldGenRegistry.register(new ItemStack(CABlocks.SALT_ORE.get()),
						new DistributionSquare(14, 8, 32, 112),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
						true,
						LootDropMinMax(new ItemStack(CAItems.SALT.get()), 4, 8));
			}

			if (CACommonConfig.COMMON.enableOreTitaniumGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.TITANIUM_ORE.get()),
						new DistributionSquare(3, 4, 1, 12));
				worldGenRegistry.register(new ItemStack(CABlocks.TITANIUM_ORE.get()),
						new DistributionSquare(3, 4, 1, 18),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
			}

			if (CACommonConfig.COMMON.enableOreUraniumGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.URANIUM_ORE.get()),
						new DistributionSquare(3, 4, 1, 12));
				worldGenRegistry.register(new ItemStack(CABlocks.URANIUM_ORE.get()),
						new DistributionSquare(3, 4, 1, 18),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
			}

			if (CACommonConfig.COMMON.enableTrollOreGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.RED_ANT_INFESTED_ORE.get()),
						new DistributionSquare(1, 7, 1, 16));
				worldGenRegistry.register(new ItemStack(CABlocks.RED_ANT_INFESTED_ORE.get()),
						new DistributionSquare(1, 7, 1, 16),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.TERMITE_INFESTED_ORE.get()),
						new DistributionSquare(6, 1, 4, 32),
						new Restriction(BiomeRestriction.EXTREME_HILLS));
				worldGenRegistry.register(new ItemStack(CABlocks.TERMITE_INFESTED_ORE.get()),
						new DistributionSquare(6, 1, 4, 32),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
			}

			// Todo: All the fossils.
		}
	}

	/**
	 * @param item         The dropped {@link net.minecraft.item.ItemStack}
	 * @param minDrop      the maximum amount dropped
	 * @param maxDrop      the minimum amount dropped
	 */
	public static LootDrop LootDropMinMax(ItemStack item, int minDrop, int maxDrop) {
		return new LootDrop(item, minDrop, maxDrop, 1F);
	}
}