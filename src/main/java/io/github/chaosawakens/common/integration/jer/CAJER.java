package io.github.chaosawakens.common.integration.jer;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import io.github.chaosawakens.common.loot.CATreasure;
import io.github.chaosawakens.common.registry.CABiomes;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.manager.CAConfigManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import jeresources.api.IDungeonRegistry;
import jeresources.api.IPlantRegistry;
import jeresources.api.IWorldGenRegistry;
import jeresources.api.distributions.DistributionSquare;
import jeresources.api.drop.LootDrop;
import jeresources.api.drop.PlantDrop;
import jeresources.api.render.IMobRenderHook;
import jeresources.api.restrictions.BiomeRestriction;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.compatibility.JERAPI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;

public class CAJER {
	private static final ObjectArrayList<Class<? extends LivingEntity>> CUSTOM_RENDER_HOOK_ENTITIES = new ObjectArrayList<Class<? extends LivingEntity>>();
	public static IMobRenderHook DEFAULT = (renderInfo, targetEntity) -> {
		if (targetEntity != null && targetEntity instanceof IAnimatableEntity && !CUSTOM_RENDER_HOOK_ENTITIES.contains(targetEntity.getClass())) {
			IAnimatableEntity targetAnimatable = (IAnimatableEntity) targetEntity;
			
			if (targetAnimatable.getIdleAnim() != null) targetAnimatable.playAnimation(targetAnimatable.getIdleAnim(), true);
		}
		return renderInfo;
	};
	public static IMobRenderHook<RoboWarriorEntity> ROBO_WARRIOR = (renderInfo, targetEntity) -> {
		if (targetEntity != null) {
			targetEntity.playAnimation(targetEntity.getCachedAnimationByName("Idle Extras"), true);
			targetEntity.playAnimation(targetEntity.getIdleAnim(), false);
		}
		
		return renderInfo;
	};

	public static void register() {
		registerEntityRenderers();
		registerDungeonLoot();
		registerPlants();
		registerOres();
	}

	private static void registerDungeonLoot() {
		IDungeonRegistry dungeonRegistry = JERAPI.getInstance().getDungeonRegistry();
		// ENT DUNGEONS
		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_ACACIA_LOOT.stringName, CATreasure.ENT_TREE_ACACIA_LOOT.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_ACACIA_LOOT.stringName, CATreasure.ENT_TREE_ACACIA_LOOT.lootTable);

		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_BIRCH_LOOT.stringName, CATreasure.ENT_TREE_BIRCH_LOOT.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_BIRCH_LOOT.stringName, CATreasure.ENT_TREE_BIRCH_LOOT.lootTable);

		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_CRIMSON_LOOT.stringName, CATreasure.ENT_TREE_CRIMSON_LOOT.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_CRIMSON_LOOT.stringName, CATreasure.ENT_TREE_CRIMSON_LOOT.lootTable);

		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_DARK_OAK_LOOT.stringName, CATreasure.ENT_TREE_DARK_OAK_LOOT.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_DARK_OAK_LOOT.stringName, CATreasure.ENT_TREE_DARK_OAK_LOOT.lootTable);

		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_JUNGLE_LOOT.stringName, CATreasure.ENT_TREE_JUNGLE_LOOT.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_JUNGLE_LOOT.stringName, CATreasure.ENT_TREE_JUNGLE_LOOT.lootTable);

		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_JUNGLE_TRAP.stringName, CATreasure.ENT_TREE_JUNGLE_TRAP.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_JUNGLE_TRAP.stringName, CATreasure.ENT_TREE_JUNGLE_TRAP.lootTable);

		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_OAK_LOOT.stringName, CATreasure.ENT_TREE_OAK_LOOT.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_OAK_LOOT.stringName, CATreasure.ENT_TREE_OAK_LOOT.lootTable);

		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_SPRUCE_LOOT.stringName, CATreasure.ENT_TREE_SPRUCE_LOOT.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_SPRUCE_LOOT.stringName, CATreasure.ENT_TREE_SPRUCE_LOOT.lootTable);

		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_WARPED_LOOT.stringName, CATreasure.ENT_TREE_WARPED_LOOT.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_WARPED_LOOT.stringName, CATreasure.ENT_TREE_WARPED_LOOT.lootTable);

		// Uncomment: When Mushroom Dungeons are added.
		//		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_BROWN_MUSHROOM_LOOT.stringName, CATreasure.ENT_TREE_BROWN_MUSHROOM_LOOT.langName);
		//		dungeonRegistry.registerChest(CATreasure.ENT_TREE_BROWN_MUSHROOM_LOOT.stringName, CATreasure.ENT_TREE_BROWN_MUSHROOM_LOOT.lootTable);
		//
		//		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_RED_MUSHROOM_LOOT.stringName, CATreasure.ENT_TREE_RED_MUSHROOM_LOOT.langName);
		// 	dungeonRegistry.registerChest(CATreasure.ENT_TREE_RED_MUSHROOM_LOOT.stringName, CATreasure.ENT_TREE_RED_MUSHROOM_LOOT.lootTable);

		dungeonRegistry.registerCategory(CATreasure.ENT_TREE_GINKGO_LOOT.stringName, CATreasure.ENT_TREE_GINKGO_LOOT.langName);
		dungeonRegistry.registerChest(CATreasure.ENT_TREE_GINKGO_LOOT.stringName, CATreasure.ENT_TREE_GINKGO_LOOT.lootTable);

		// WASP DUNGEON
		// Uncomment: When Wasp and Wasp Dungeons are added again.
		// 	dungeonRegistry.registerCategory(CATreasure.WASP_DUNGEON_LOOT.stringName, CATreasure.WASP_DUNGEON_LOOT.langName);
		// 	dungeonRegistry.registerChest(CATreasure.WASP_DUNGEON_LOOT.stringName, CATreasure.WASP_DUNGEON_LOOT.lootTable);
		//
		//		dungeonRegistry.registerCategory(CATreasure.MINING_WASP_DUNGEON_LOOT.stringName, CATreasure.MINING_WASP_DUNGEON_LOOT.langName);
		//		dungeonRegistry.registerChest(CATreasure.MINING_WASP_DUNGEON_LOOT.stringName, CATreasure.MINING_WASP_DUNGEON_LOOT.lootTable);
	}

	private static void registerPlants() {
		IPlantRegistry plantRegistry = JERAPI.getInstance().getPlantRegistry();
		PlantDrop radish = new PlantDrop(new ItemStack(CAItems.RADISH.get()), 1, 1);
		PlantDrop radishSeeds = new PlantDrop(new ItemStack(CAItems.RADISH_SEEDS.get()), 0, 3);
		plantRegistry.register(CABlocks.RADISH.get(), radish, radishSeeds);

		PlantDrop lettuce = new PlantDrop(new ItemStack(CAItems.LETTUCE.get()), 1, 1);
		PlantDrop lettuceSeeds = new PlantDrop(new ItemStack(CAItems.LETTUCE_SEEDS.get()), 0, 3);
		plantRegistry.register(CABlocks.LETTUCE.get(), lettuce, lettuceSeeds);
	}

	private static void registerOres() {
		IWorldGenRegistry worldGenRegistry = JERAPI.getInstance().getWorldGenRegistry();

		if (CAConfigManager.MAIN_COMMON.enableOreGen.get()) {
			if (CAConfigManager.MAIN_COMMON.enableOreAluminumGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.ALUMINUM_ORE.get()),
						new DistributionSquare(2, 14, 20, 60),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.ALUMINUM_ORE.get()),
						new DistributionSquare(6, 14, 40, 120),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
			}

			if (CAConfigManager.MAIN_COMMON.enableOreAmethystGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.AMETHYST_ORE.get()),
						new DistributionSquare(8, 24, 20),
						Restriction.OVERWORLD,
						true,
						new LootDrop(new ItemStack(CAItems.AMETHYST.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.AMETHYST_ORE.get()),
						new DistributionSquare(4, 3, 24, 56),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
						true,
						new LootDrop(new ItemStack(CAItems.AMETHYST.get())));
			}

			if (CAConfigManager.MAIN_COMMON.enableOreRubyGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.RUBY_ORE.get()),
						new DistributionSquare(3, 7, 1, 18),
						Restriction.OVERWORLD,
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.NETHERRACK_RUBY_ORE.get()),
						new DistributionSquare(2, 5, 18, 34),
						Restriction.NETHER,
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.NETHERRACK_RUBY_ORE.get()),
						new DistributionSquare(4, 3, 100, 122),
						Restriction.NETHER,
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.BLACKSTONE_RUBY_ORE.get()),
						new DistributionSquare(6, 3, 0, 128),
						Restriction.NETHER,
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.RUBY_ORE.get()),
						new DistributionSquare(4, 7, 6, 18),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
						true,
						new LootDrop(new ItemStack(CAItems.RUBY.get())));
			}

			if (CAConfigManager.MAIN_COMMON.enableOreTigersEyeGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.TIGERS_EYE_ORE.get()),
						new DistributionSquare(5, 6, 1, 22),
						Restriction.OVERWORLD,
						true,
						new LootDrop(new ItemStack(CAItems.TIGERS_EYE.get())));
				worldGenRegistry.register(new ItemStack(CABlocks.TIGERS_EYE_ORE.get()),
						new DistributionSquare(5, 6, 1, 24),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
						true,
						new LootDrop(new ItemStack(CAItems.TIGERS_EYE.get())));
			}

			if (CAConfigManager.MAIN_COMMON.enableDzMineralOreGen.get()) {
				if (CAConfigManager.MAIN_COMMON.spawnDzOresInOverworld.get()) {
					if (CAConfigManager.MAIN_COMMON.enableOreCopperGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.COPPER_ORE.get()),
								new DistributionSquare(6, 4, 40, 104),
								Restriction.OVERWORLD,
								true,
								lootDropMinMax(new ItemStack(CAItems.COPPER_LUMP.get()), 2, 5));
					}
					if (CAConfigManager.MAIN_COMMON.enableOreTinGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.TIN_ORE.get()),
								new DistributionSquare(5, 3, 32, 80),
								Restriction.OVERWORLD,
								true,
								new LootDrop(new ItemStack(CAItems.TIN_LUMP.get())));
					}
					if (CAConfigManager.MAIN_COMMON.enableOreSilverGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.SILVER_ORE.get()),
								new DistributionSquare(4, 2, 12, 52),
								Restriction.OVERWORLD,
								true,
								new LootDrop(new ItemStack(CAItems.SILVER_LUMP.get())));
					}
					if (CAConfigManager.MAIN_COMMON.enableOrePlatinumGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.PLATINUM_ORE.get()),
								new DistributionSquare(3, 2, 8, 24),
								Restriction.OVERWORLD,
								true,
								new LootDrop(new ItemStack(CAItems.PLATINUM_LUMP.get())));
					}
					if (CAConfigManager.MAIN_COMMON.enableOreSunstoneGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.SUNSTONE_ORE.get()),
								new DistributionSquare(8, 3, 1, 128),
								Restriction.OVERWORLD,
								true,
								new LootDrop(new ItemStack(CAItems.SUNSTONE.get())));
					}
					if (CAConfigManager.MAIN_COMMON.enableOreBloodstoneGen.get()) {
						worldGenRegistry.register(new ItemStack(CABlocks.BLOODSTONE_ORE.get()),
								new DistributionSquare(8, 4, 1, 128),
								Restriction.OVERWORLD,
								true,
								new LootDrop(new ItemStack(CAItems.BLOODSTONE.get())));
					}
				}

				if (CAConfigManager.MAIN_COMMON.enableOreCopperGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.COPPER_ORE.get()),
							new DistributionSquare(6, 4, 34, 94),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							lootDropMinMax(new ItemStack(CAItems.COPPER_LUMP.get()), 2, 5));
				}
				if (CAConfigManager.MAIN_COMMON.enableOreTinGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.TIN_ORE.get()),
							new DistributionSquare(5, 3, 18, 78),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.TIN_LUMP.get())));
				}
				if (CAConfigManager.MAIN_COMMON.enableOreSilverGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.SILVER_ORE.get()),
							new DistributionSquare(4, 2, 8, 56),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.SILVER_LUMP.get())));
				}
				if (CAConfigManager.MAIN_COMMON.enableOrePlatinumGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.PLATINUM_ORE.get()),
							new DistributionSquare(3, 2, 12, 28),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.PLATINUM_LUMP.get())));
				}
				if (CAConfigManager.MAIN_COMMON.enableOreSunstoneGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.SUNSTONE_ORE.get()),
							new DistributionSquare(8, 3, 8, 136),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.SUNSTONE.get())));
				}
				if (CAConfigManager.MAIN_COMMON.enableOreBloodstoneGen.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.BLOODSTONE_ORE.get()),
							new DistributionSquare(8, 4, 8, 136),
							new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
							true,
							new LootDrop(new ItemStack(CAItems.BLOODSTONE.get())));
				}
			}

			if (CAConfigManager.MAIN_COMMON.enableOreSaltGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.SALT_ORE.get()),
						new DistributionSquare(14, 7, 32, 96),
						Restriction.OVERWORLD,
						true,
						lootDropMinMax(new ItemStack(CAItems.SALT.get()), 4, 8));
				worldGenRegistry.register(new ItemStack(CABlocks.SALT_ORE.get()),
						new DistributionSquare(12, 7, 32, 152),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)),
						true,
						lootDropMinMax(new ItemStack(CAItems.SALT.get()), 4, 8));
			}

			if (CAConfigManager.MAIN_COMMON.enableOreTitaniumGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.TITANIUM_ORE.get()),
						new DistributionSquare(3, 3, 1, 12),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.TITANIUM_ORE.get()),
						new DistributionSquare(3, 3, 1, 18),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
			}

			if (CAConfigManager.MAIN_COMMON.enableOreUraniumGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.URANIUM_ORE.get()),
						new DistributionSquare(3, 3, 1, 12),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.URANIUM_ORE.get()),
						new DistributionSquare(3, 3, 1, 18),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
			}

			if (CAConfigManager.MAIN_COMMON.enableTrollOreGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.RED_ANT_INFESTED_ORE.get()),
						new DistributionSquare(1, 7, 1, 16),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.RED_ANT_INFESTED_ORE.get()),
						new DistributionSquare(1, 7, 1, 16),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.TERMITE_INFESTED_ORE.get()),
						new DistributionSquare(6, 1, 4, 32),
						new Restriction(
								BiomeRestriction.EXTREME_HILLS,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.TERMITE_INFESTED_ORE.get()),
						new DistributionSquare(6, 1, 4, 32),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
			}

			worldGenRegistry.register(new ItemStack(CABlocks.TERRA_PRETA.get()),
					new DistributionSquare(3, 23, 75, 105),
					new Restriction(
							new BiomeRestriction(CABiomes.MESOZOIC_JUNGLE.get()),
							new DimensionRestriction(CADimensions.MINING_PARADISE)));
			worldGenRegistry.register(new ItemStack(CABlocks.LATOSOL.get()),
					new DistributionSquare(1, 23, 75, 105),
					new Restriction(
							new BiomeRestriction(CABiomes.MESOZOIC_JUNGLE.get()),
							new DimensionRestriction(CADimensions.MINING_PARADISE)));
			worldGenRegistry.register(new ItemStack(CABlocks.TAR.get()),
					new DistributionSquare(1, 19, 75, 105),
					new Restriction(
							new BiomeRestriction(CABiomes.MESOZOIC_JUNGLE.get()),
							new DimensionRestriction(CADimensions.MINING_PARADISE)));

			if (CAConfigManager.MAIN_COMMON.enableMarbleGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.MARBLE.get()),
						new DistributionSquare(6, 39, 0, 128),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));

				if (CAConfigManager.MAIN_COMMON.enableMarbleGenInOverworld.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.MARBLE.get()),
							new DistributionSquare(6, 39, 0, 128),
							Restriction.OVERWORLD);
				}
			}
			if (CAConfigManager.MAIN_COMMON.enableLimestoneGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.LIMESTONE.get()),
						new DistributionSquare(3, 35, 0, 116),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));

				if (CAConfigManager.MAIN_COMMON.enableLimestoneGenInOverworld.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.LIMESTONE.get()),
							new DistributionSquare(3, 35, 0, 116),
							Restriction.OVERWORLD);
				}
			}
			if (CAConfigManager.MAIN_COMMON.enableRhinestoneGen.get()) {
				worldGenRegistry.register(new ItemStack(CABlocks.RHINESTONE.get()),
						new DistributionSquare(4, 37, 0, 114),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));

				if (CAConfigManager.MAIN_COMMON.enableRhinestoneGenInOverworld.get()) {
					worldGenRegistry.register(new ItemStack(CABlocks.RHINESTONE.get()),
							new DistributionSquare(4, 37, 0, 114),
							Restriction.OVERWORLD);
				}
			}

			worldGenRegistry.register(new ItemStack(CABlocks.ENERGIZED_KYANITE.get()),
					new DistributionSquare(3, 15, 16, 96),
					new Restriction(new DimensionRestriction(CADimensions.CRYSTAL_WORLD)));
			worldGenRegistry.register(new ItemStack(CABlocks.PINK_TOURMALINE_CLUSTER.get()),
					new DistributionSquare(28, 52, 12),
					new Restriction(new DimensionRestriction(CADimensions.CRYSTAL_WORLD)),
					true,
					lootDropMinMax(new ItemStack(CAItems.PINK_TOURMALINE_SHARD.get()), 1, 4));
			worldGenRegistry.register(new ItemStack(CABlocks.CATS_EYE_CLUSTER.get()),
					new DistributionSquare(6, 30, 18),
					new Restriction(new DimensionRestriction(CADimensions.CRYSTAL_WORLD)),
					true,
					lootDropMinMax(new ItemStack(CAItems.CATS_EYE_SHARD.get()), 1, 4));

			if (CAConfigManager.MAIN_COMMON.enableFossilGen.get()) {
				// Note: Chaos Awakens Overworld Fossils
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_HERCULES_BEETLE.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WTF.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PIRAPORU.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_APPLE_COW.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_CARROT_PIG.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_LETTUCE_CHICKEN.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BIRD.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);

				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ACACIA_ENT.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.SAVANNA,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BIRCH_ENT.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.FOREST,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_DARK_OAK_ENT.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.FOREST,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_JUNGLE_ENT.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.JUNGLE,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_OAK_ENT.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								new BiomeRestriction(Biome.Category.PLAINS, Biome.Category.FOREST),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SPRUCE_ENT.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								new BiomeRestriction(Biome.Category.EXTREME_HILLS, Biome.Category.TAIGA),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BEAVER.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								new BiomeRestriction(Biome.Category.PLAINS, Biome.Category.FOREST, Biome.Category.SWAMP),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_RUBY_BUG.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.SWAMP,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_EMERALD_GATOR.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.SWAMP,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SCORPION.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.DESERT,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WASP.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								new BiomeRestriction(Biome.Category.FOREST, Biome.Category.SWAMP),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_TREE_FROG.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.SWAMP,
								DimensionRestriction.OVERWORLD));

				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_GREEN_FISH.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ROCK_FISH.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SPARK_FISH.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WOOD_FISH.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WHALE.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));

				// Note: Vanilla Overworld Fossils
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BAT.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BEE.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_CAVE_SPIDER.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_CHICKEN.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_COW.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_CREEPER.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_DONKEY.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ENDERMAN.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_HORSE.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PIG.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_RABBIT.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SHEEP.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SKELETON.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SKELETON_HORSE.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SPIDER.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WANDERING_TRADER.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WITCH.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ZOMBIE.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ZOMBIE_HORSE.get()),
						new DistributionSquare(4, 2, 0, 96),
						Restriction.OVERWORLD);

				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PHANTOM.get()),
						new DistributionSquare(4, 2, 62, 122),
						Restriction.OVERWORLD);

				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_COD.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_DOLPHIN.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_DROWNED.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_GUARDIAN.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PUFFERFISH.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SALMON.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SQUID.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_TROPICAL_FISH.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_TURTLE.get()),
						new DistributionSquare(4, 2, 16, 80),
						new Restriction(
								new BiomeRestriction(Biome.Category.OCEAN, Biome.Category.RIVER),
								DimensionRestriction.OVERWORLD));

				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_IRON_GOLEM.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								new BiomeRestriction(Biome.Category.PLAINS,
										Biome.Category.ICY, Biome.Category.TAIGA,
										Biome.Category.DESERT, Biome.Category.SAVANNA),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_VILLAGER.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								new BiomeRestriction(Biome.Category.PLAINS,
										Biome.Category.ICY, Biome.Category.TAIGA,
										Biome.Category.DESERT, Biome.Category.SAVANNA),
								DimensionRestriction.OVERWORLD));

				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_EVOKER.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.FOREST,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ILLUSIONER.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.FOREST,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PILLAGER.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.FOREST,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PILLAGER.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								new BiomeRestriction(Biome.Category.PLAINS,
										Biome.Category.ICY, Biome.Category.TAIGA,
										Biome.Category.DESERT, Biome.Category.SAVANNA),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_RAVAGER.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								new BiomeRestriction(Biome.Category.PLAINS,
										Biome.Category.ICY, Biome.Category.TAIGA,
										Biome.Category.DESERT, Biome.Category.SAVANNA),
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_VINDICATOR.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.FOREST,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_VINDICATOR.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								new BiomeRestriction(Biome.Category.PLAINS,
										Biome.Category.ICY, Biome.Category.TAIGA,
										Biome.Category.DESERT, Biome.Category.SAVANNA),
								DimensionRestriction.OVERWORLD));

				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_FOX.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.TAIGA,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_LLAMA.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.EXTREME_HILLS,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_MOOSHROOM.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.MUSHROOM,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_OCELOT.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.JUNGLE,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PANDA.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.JUNGLE,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WOLF.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.FOREST,
								DimensionRestriction.OVERWORLD));

				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_HUSK.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.DESERT,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_HUSK_SANDSTONE.get()),
						new DistributionSquare(4, 2, 42, 102),
						new Restriction(
								BiomeRestriction.DESERT,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SLIME.get()),
						new DistributionSquare(4, 2, 0, 96),
						new Restriction(
								BiomeRestriction.SWAMP,
								DimensionRestriction.OVERWORLD));

				worldGenRegistry.register(new ItemStack(CABlocks.FROZEN_POLAR_BEAR.get()),
						new DistributionSquare(9, 2, 40, 100),
						new Restriction(
								BiomeRestriction.ICY,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FROZEN_SNOW_GOLEM.get()),
						new DistributionSquare(9, 2, 40, 100),
						new Restriction(
								BiomeRestriction.ICY,
								DimensionRestriction.OVERWORLD));
				worldGenRegistry.register(new ItemStack(CABlocks.FROZEN_STRAY.get()),
						new DistributionSquare(9, 2, 40, 100),
						new Restriction(
								BiomeRestriction.ICY,
								DimensionRestriction.OVERWORLD));

				// Note: Chaos Awakens Nether Fossils
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_CRIMSON_ENT.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WARPED_ENT.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_LAVA_EEL.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);

				// Note: Vanilla Nether Fossils
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BLAZE.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_GHAST.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_HOGLIN.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ENDERMAN_NETHERRACK.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_MAGMA_CUBE_NETHERRACK.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_MAGMA_CUBE_BLACKSTONE.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PIGLIN.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SKELETON_SOUL_SOIL.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_STRIDER.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WITHER_SKELETON.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ZOMBIFIED_PIGLIN.get()),
						new DistributionSquare(6, 2, 0, 128),
						Restriction.NETHER);

				// Note: Vanilla End Fossils
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ENDERMAN_END_STONE.get()),
						new DistributionSquare(8, 2, 10, 64),
						Restriction.END);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ENDERMITE.get()),
						new DistributionSquare(8, 2, 10, 64),
						Restriction.END);
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SHULKER.get()),
						new DistributionSquare(8, 2, 10, 64),
						Restriction.END);

				// Note: Chaos Awakens Mining Paradise Fossils
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_HERCULES_BEETLE.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BEAVER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_RUBY_BUG.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_EMERALD_GATOR.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WTF.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SCORPION.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WASP.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PIRAPORU.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_APPLE_COW.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_GOLDEN_APPLE_COW.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_CARROT_PIG.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_GOLDEN_CARROT_PIG.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_LETTUCE_CHICKEN.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BIRD.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_DIMETRODON.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_TREE_FROG.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));

				// Note: Vanilla Mining Paradise Fossils
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BAT.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_BEE.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_CAVE_SPIDER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_CHICKEN.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_COW.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_CREEPER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_DONKEY.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ENDERMAN.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_EVOKER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_FOX.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_GIANT.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ILLUSIONER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_IRON_GOLEM.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_LLAMA.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_MOOSHROOM.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_OCELOT.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PANDA.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PIG.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_PILLAGER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_RABBIT.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_RAVAGER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SHEEP.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SKELETON.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SKELETON_HORSE.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SLIME.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_SPIDER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_VILLAGER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_VINDICATOR.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WANDERING_TRADER.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WITCH.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_WOLF.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ZOMBIE.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ZOMBIE_HORSE.get()),
						new DistributionSquare(2, 2, 0, 108),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));

				// Note: Chaos Awakens Crystal World Fossils
				worldGenRegistry.register(new ItemStack(CABlocks.CRYSTALISED_CRYSTAL_APPLE_COW.get()),
						new DistributionSquare(3, 2, 10, 64),
						new Restriction(new DimensionRestriction(CADimensions.CRYSTAL_WORLD)));
				worldGenRegistry.register(new ItemStack(CABlocks.CRYSTALISED_CRYSTAL_CARROT_PIG.get()),
						new DistributionSquare(2, 1, 16, 62),
						new Restriction(new DimensionRestriction(CADimensions.CRYSTAL_WORLD)));
				worldGenRegistry.register(new ItemStack(CABlocks.CRYSTALISED_CRYSTAL_GATOR.get()),
						new DistributionSquare(2, 1, 13, 63),
						new Restriction(new DimensionRestriction(CADimensions.CRYSTAL_WORLD)));
			}

			if (CAConfigManager.MAIN_COMMON.enableNestGen.get()) {
				// Note: Overworld Nests
				worldGenRegistry.register(new ItemStack(CABlocks.BROWN_ANT_NEST.get()),
						new DistributionSquare(1, 1, 30, 120),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.RAINBOW_ANT_NEST.get()),
						new DistributionSquare(1, 1, 30, 120),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.RED_ANT_NEST.get()),
						new DistributionSquare(1, 1, 30, 120),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.UNSTABLE_ANT_NEST.get()),
						new DistributionSquare(1, 1, 30, 120),
						Restriction.OVERWORLD);
				worldGenRegistry.register(new ItemStack(CABlocks.TERMITE_NEST.get()),
						new DistributionSquare(1, 1, 30, 120),
						Restriction.OVERWORLD);

				worldGenRegistry.register(new ItemStack(CABlocks.RAINBOW_ANT_NEST.get()),
						new DistributionSquare(1, 1, 30, 120),
						new Restriction(new DimensionRestriction(CADimensions.VILLAGE_MANIA)));
				worldGenRegistry.register(new ItemStack(CABlocks.DENSE_RED_ANT_NEST.get()),
						new DistributionSquare(6, 1, 100, 180),
						new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)));
				worldGenRegistry.register(new ItemStack(CABlocks.CRYSTAL_TERMITE_NEST.get()),
						new DistributionSquare(6, 1, 30, 120),
						new Restriction(new DimensionRestriction(CADimensions.CRYSTAL_WORLD)));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void registerEntityRenderers() {
	//	registerMobRenderHook((Class<? extends LivingEntity>) IAnimatableEntity.class, DEFAULT);
	//	registerMobRenderHook(RoboWarriorEntity.class, ROBO_WARRIOR);
	}

	/**
	 * @param item         The dropped {@link net.minecraft.item.ItemStack}
	 * @param minDrop      the maximum amount dropped
	 * @param maxDrop      the minimum amount dropped
	 */
	public static LootDrop lootDropMinMax(ItemStack item, int minDrop, int maxDrop) {
		return new LootDrop(item, minDrop, maxDrop, 1F);
	}
	
	public static <E extends LivingEntity> void registerMobRenderHook(Class<E> clazz, IMobRenderHook<E> renderHook) {
		if (renderHook != DEFAULT) CUSTOM_RENDER_HOOK_ENTITIES.add(clazz);
        JERAPI.getInstance().getMobRegistry().registerRenderHook(clazz, renderHook);
    }
	
	public static void renderAnimatableEntity() {
		
	}
}