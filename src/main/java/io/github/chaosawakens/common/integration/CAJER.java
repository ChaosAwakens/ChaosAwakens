package io.github.chaosawakens.common.integration;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CAItems;
import jeresources.api.IWorldGenRegistry;
import jeresources.api.distributions.DistributionSquare;
import jeresources.api.distributions.DistributionTriangular;
import jeresources.api.drop.LootDrop;
import jeresources.api.drop.PlantDrop;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.compatibility.JERAPI;
import jeresources.entry.PlantEntry;
import jeresources.entry.WorldGenEntry;
import jeresources.registry.PlantRegistry;
import jeresources.registry.WorldGenRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CAJER {
    public static void init() {
        registerDungeonLoot();
        registerPlants();
        registerOres();
    }

    private static void registerDungeonLoot() {
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
        JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/wasp_dungeon/loot", "dungeon.chaosawakens.jer.wasp_dungeon.loot");
        JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/wasp_dungeon/loot", new ResourceLocation("chaosawakens:chests/wasp_dungeon/loot"));
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

        worldGenRegistry.register(new ItemStack(CABlocks.BLOODSTONE_ORE.get()), new DistributionTriangular(64, 64, 0.001F), true, new LootDrop(new ItemStack(CAItems.BLOODSTONE.get())));
        worldGenRegistry.register(new ItemStack(CABlocks.BLOODSTONE_ORE.get()), new DistributionTriangular(64, 72, 0.001F), new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)), true, new LootDrop(new ItemStack(CAItems.BLOODSTONE.get())));

        worldGenRegistry.register(new ItemStack(CABlocks.AMETHYST_ORE.get()), new DistributionTriangular(28, 24, 4), true, new LootDrop(new ItemStack(CAItems.AMETHYST.get())));
        worldGenRegistry.register(new ItemStack(CABlocks.AMETHYST_ORE.get()), new DistributionSquare(8, 5, 22, 34), new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)), true, new LootDrop(new ItemStack(CAItems.AMETHYST.get())));

        worldGenRegistry.register(new ItemStack(CABlocks.ALUMINUM_ORE.get()), new DistributionTriangular(28, 24, 8));
        worldGenRegistry.register(new ItemStack(CABlocks.RUBY_ORE.get()), new DistributionTriangular(24, 24, 28));
        worldGenRegistry.register(new ItemStack(CABlocks.TIGERS_EYE_ORE.get()), new DistributionTriangular(24, 24, 5));

        worldGenRegistry.register(new ItemStack(CABlocks.TITANIUM_ORE.get()), new DistributionTriangular(12, 12, 2));
        worldGenRegistry.register(new ItemStack(CABlocks.URANIUM_ORE.get()), new DistributionTriangular(12, 12, 2));

        worldGenRegistry.register(new ItemStack(CABlocks.COPPER_ORE.get()), new DistributionTriangular(24, 12, 6));
        worldGenRegistry.register(new ItemStack(CABlocks.TIN_ORE.get()), new DistributionTriangular(24, 12, 5));
        worldGenRegistry.register(new ItemStack(CABlocks.SILVER_ORE.get()), new DistributionTriangular(18, 12, 4));
        worldGenRegistry.register(new ItemStack(CABlocks.PLATINUM_ORE.get()), new DistributionTriangular(12, 12, 3));

        worldGenRegistry.register(new ItemStack(CABlocks.SALT_ORE.get()), new DistributionTriangular(48, 48, 18));

        worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_ACACIA_ENT.get()), new DistributionTriangular(48, 48, 10));
        worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_HERCULES_BEETLE.get()), new DistributionTriangular(48, 48, 10));
        worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_RUBY_BUG.get()), new DistributionTriangular(48, 48, 10));
        worldGenRegistry.register(new ItemStack(CABlocks.FOSSILISED_EMERALD_GATOR.get()), new DistributionTriangular(48, 48, 10));

        worldGenRegistry.register(new ItemStack(CABlocks.RED_ANT_INFESTED_ORE.get()), new DistributionTriangular(12, 12, 5));
        worldGenRegistry.register(new ItemStack(CABlocks.TERMITE_INFESTED_ORE.get()), new DistributionTriangular(12, 12, 2));
    }

    public static void registerWorldGen(WorldGenEntry entry) {
        WorldGenRegistry.getInstance().registerEntry(entry);
    }
}