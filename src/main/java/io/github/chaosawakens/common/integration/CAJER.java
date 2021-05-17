package io.github.chaosawakens.common.integration;

import io.github.chaosawakens.common.registry.CABlocks;
import jeresources.api.distributions.DistributionTriangular;
import jeresources.compatibility.JERAPI;
import net.minecraft.item.ItemStack;

public class CAJER {

    public static void init() {
        registerOres();
    }

    private static void registerOres() {
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.AMETHYST_ORE.get()), new DistributionTriangular(28, 24, 4));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.ALUMINIUM_ORE.get()), new DistributionTriangular(28, 24, 8));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.RUBY_ORE.get()), new DistributionTriangular(24, 24, 28));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.TIGERS_EYE_ORE.get()), new DistributionTriangular(24, 24, 5));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.TITANIUM_ORE.get()), new DistributionTriangular(12, 12, 2));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.URANIUM_ORE.get()), new DistributionTriangular(12, 12, 2));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.COPPER_ORE.get()), new DistributionTriangular(24, 12, 6));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.TIN_ORE.get()), new DistributionTriangular(24, 12, 5));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.SILVER_ORE.get()), new DistributionTriangular(18, 12, 4));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.PLATINUM_ORE.get()), new DistributionTriangular(12, 12, 3));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.SALT_ORE.get()), new DistributionTriangular(48, 48, 18));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.FOSSILISED_ENT.get()), new DistributionTriangular(48, 48, 10));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.FOSSILISED_HERCULES_BEETLE.get()), new DistributionTriangular(48, 48, 10));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.FOSSILISED_RUBY_BUG.get()), new DistributionTriangular(48, 48, 10));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.FOSSILISED_EMERALD_GATOR.get()), new DistributionTriangular(48, 48, 10));

        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.RED_ANT_INFESTED_ORE.get()), new DistributionTriangular(12, 12, 5));
        JERAPI.getInstance().getWorldGenRegistry().register(new ItemStack(CABlocks.TERMITE_INFESTED_ORE.get()), new DistributionTriangular(12, 12, 2));
    }

}