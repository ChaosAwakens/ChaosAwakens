package io.github.chaosawakens.common.integration;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CADimensions;
import io.github.chaosawakens.common.registry.CAItems;
import jeresources.api.IWorldGenRegistry;
import jeresources.api.distributions.DistributionSquare;
import jeresources.api.distributions.DistributionTriangular;
import jeresources.api.drop.LootDrop;
import jeresources.api.restrictions.DimensionRestriction;
import jeresources.api.restrictions.Restriction;
import jeresources.compatibility.JERAPI;
import jeresources.entry.WorldGenEntry;
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
        JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/ent_dungeon", "dungeon.chaosawakens.jer.ent_dungeon");
        JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/ent_dungeon", new ResourceLocation("chaosawakens:chests/ent_dungeon"));
        JERAPI.getInstance().getDungeonRegistry().registerCategory("chaosawakens:chests/wasp_dungeon", "dungeon.chaosawakens.jer.wasp_dungeon");
        JERAPI.getInstance().getDungeonRegistry().registerChest("chaosawakens:chests/wasp_dungeon", new ResourceLocation("chaosawakens:chests/wasp_dungeon"));
    }

    private static void registerPlants() {
    }

    private static void registerOres() {
        registerWorldGen(new WorldGenEntry(new ItemStack(CABlocks.AMETHYST_ORE.get()), new DistributionSquare(8, 5, 22, 34), new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)), true, new LootDrop(new ItemStack(CAItems.BLOODSTONE.get(), 1))));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.BLOODSTONE_ORE.get()), new DistributionSquare(8, 5, 22, 34));
        IWorldGenRegistry worldGenRegistry = JERAPI.getInstance().getWorldGenRegistry();

        worldGenRegistry.register(new ItemStack(CABlocks.BLOODSTONE_ORE.get()).copy(), new DistributionSquare(8, 5, 22, 34));
        worldGenRegistry.register(new ItemStack(CABlocks.BLOODSTONE_ORE.get()), new DistributionSquare(8, 5, 22, 34), new Restriction(new DimensionRestriction(CADimensions.MINING_PARADISE)), true, new LootDrop[]{new LootDrop(new ItemStack(CAItems.BLOODSTONE.get()))});
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.AMETHYST_ORE.get()), new DistributionTriangular(28, 24, 4));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.ALUMINUM_ORE.get()), new DistributionTriangular(28, 24, 8));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.RUBY_ORE.get()), new DistributionTriangular(24, 24, 28));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.TIGERS_EYE_ORE.get()), new DistributionTriangular(24, 24, 5));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.TITANIUM_ORE.get()), new DistributionTriangular(12, 12, 2));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.URANIUM_ORE.get()), new DistributionTriangular(12, 12, 2));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.COPPER_ORE.get()), new DistributionTriangular(24, 12, 6));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.TIN_ORE.get()), new DistributionTriangular(24, 12, 5));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.SILVER_ORE.get()), new DistributionTriangular(18, 12, 4));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.PLATINUM_ORE.get()), new DistributionTriangular(12, 12, 3));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.SALT_ORE.get()), new DistributionTriangular(48, 48, 18));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.FOSSILISED_ACACIA_ENT.get()), new DistributionTriangular(48, 48, 10));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.FOSSILISED_HERCULES_BEETLE.get()), new DistributionTriangular(48, 48, 10));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.FOSSILISED_RUBY_BUG.get()), new DistributionTriangular(48, 48, 10));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.FOSSILISED_EMERALD_GATOR.get()), new DistributionTriangular(48, 48, 10));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.RED_ANT_INFESTED_ORE.get()), new DistributionTriangular(12, 12, 5));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.TERMITE_INFESTED_ORE.get()), new DistributionTriangular(12, 12, 2));
    }

    public static void registerWorldGen(WorldGenEntry entry) {
        WorldGenRegistry.getInstance().registerEntry(entry);
    }
}