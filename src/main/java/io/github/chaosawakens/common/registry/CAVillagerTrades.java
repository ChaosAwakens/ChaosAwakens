package io.github.chaosawakens.common.registry;

import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.util.TradeUtil;
import io.github.chaosawakens.common.util.TradeUtil.CABasicTrade;
import io.github.chaosawakens.common.util.TradeUtil.CAIngredientTrade;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID)
public class CAVillagerTrades {
	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		TradeUtil.addWandererTrades(event,
				new CABasicTrade(1, CABlocks.CYAN_ROSE.get().asItem(), 12),
				new CABasicTrade(1, CABlocks.RED_ROSE.get().asItem(), 12),
				new CABasicTrade(1, CABlocks.PAEONIA.get().asItem(), 12),

				new CABasicTrade(3, CABlocks.TUBE_WORM.get().asItem(), 12),

				new CABasicTrade(5, CABlocks.APPLE_SAPLING.get().asItem(), 8),
				new CABasicTrade(5, CABlocks.CHERRY_SAPLING.get().asItem(), 8),
				new CABasicTrade(5, CABlocks.PEACH_SAPLING.get().asItem(), 8),

				new CABasicTrade(5, Items.NAME_TAG, 5));


		if (CACommonConfig.COMMON.wanderingTraderSellsTriffidGoo.get()) {
			TradeUtil.addWandererTrades(event,
					new CABasicTrade(4, CAItems.TRIFFID_GOO.get().asItem(), 5));
		}

		if (CACommonConfig.COMMON.wanderingTraderSellsUraniumAndTitanium.get()) {
			TradeUtil.addRareWandererTrades(event,
					new CABasicTrade(15, CAItems.TITANIUM_NUGGET.get(), 3),
					new CABasicTrade(15, CAItems.URANIUM_NUGGET.get(), 3));
		}
	}

	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.NOVICE,
				new CABasicTrade(2, CAItems.CHERRIES.get(), 6, 16, 2),
				new CABasicTrade(2, CAItems.LETTUCE.get(), 6, 12, 2),
				new CABasicTrade(1, CAItems.CORN.get(), 5, 12, 3),
				new CABasicTrade(1, CAItems.TOMATO.get(), 5, 12, 3));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.APPRENTICE,
				new CABasicTrade(1, CAItems.PEACH.get(), 6, 16, 5),
				new CABasicTrade(2, CAItems.STRAWBERRY.get(), 6, 12, 5),
				new CABasicTrade(1, CAItems.RADISH.get(), 5, 12, 8));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.JOURNEYMAN,
				new CABasicTrade(1, CAItems.RADISH_STEW.get(), 1, 12, 15));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.EXPERT,
				new CABasicTrade(1, CAItems.GOLDEN_MELON_SLICE.get().asItem(), 4, 12, 15),
				new CABasicTrade(1, CAItems.GOLDEN_POTATO.get().asItem(), 4, 12, 15));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.MASTER,
				new CABasicTrade(CAItems.GOLDEN_MELON_SLICE.get().asItem(), 6, 1, 12, 30),
				new CABasicTrade(CAItems.GOLDEN_POTATO.get().asItem(), 6, 1, 12, 30));

		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.NOVICE,
				new CABasicTrade(CAItems.BACON.get(), 18, 1, 16, 2),
				new CABasicTrade(CAItems.CORNDOG.get(), 18, 1, 16, 2));
		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.APPRENTICE,
				new CABasicTrade(1, CAItems.COOKED_BACON.get(), 6, 16, 5),
				new CABasicTrade(1, CAItems.COOKED_CORNDOG.get(), 6, 16, 5));
		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.JOURNEYMAN,
				new CABasicTrade(CAItems.CRAB_MEAT.get(), 12, 1, 16, 20),
				new CABasicTrade(1, CAItems.COOKED_CRAB_MEAT.get(), 5, 16, 10));
		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.EXPERT,
				new CABasicTrade(CAItems.PEACOCK_LEG.get(), 12, 1, 16, 20),
				new CABasicTrade(1, CAItems.COOKED_PEACOCK_LEG.get(), 5, 16, 20));

		TradeUtil.addVillagerTrades(event, VillagerProfession.FLETCHER, TradeUtil.MASTER,
				new CABasicTrade(3, CAItems.PEACOCK_FEATHER.get(), 4, 12, 30));

		TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.JOURNEYMAN,
				new CABasicTrade(3, CAItems.BLUE_FISH.get(), 1, 16, 10),
				new CABasicTrade(3, CAItems.GRAY_FISH.get(), 1, 16, 10),
				new CABasicTrade(3, CAItems.GREEN_FISH.get(), 1, 12, 10),
				new CABasicTrade(3, CAItems.PINK_FISH.get(), 1, 12, 10));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.EXPERT,
				new CABasicTrade(4, CAItems.ROCK_FISH.get(), 1, 16, 20),
				new CABasicTrade(4, CAItems.SPARK_FISH.get(), 1, 12, 20),
				new CABasicTrade(4, CAItems.WOOD_FISH.get(), 1, 16, 20));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.MASTER,
				new CABasicTrade(5, CAItems.FIRE_FISH.get(), 1, 12, 20),
				new CABasicTrade(5, CAItems.LAVA_EEL.get(), 1, 12, 20),
				new CABasicTrade(5, CAItems.SUN_FISH.get(), 1, 8, 30));
	}

	@SubscribeEvent
	public static void onArchaeologistTradesEvent(VillagerTradesEvent event) {
		TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.NOVICE,
				new CABasicTrade(1, Items.WATER_BUCKET, 1, 6, 2),
				new CABasicTrade(2, CAItems.ALUMINUM_POWER_CHIP.get(), 3, 8, 2),
				new CABasicTrade(1, CAItems.COPPER_LUMP.get(), 6, 6, 3),
				new CAIngredientTrade(Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_COMMON), 3), 5, 3, 3));
		TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.APPRENTICE,
				new CABasicTrade(1, Items.LAVA_BUCKET, 1, 6, 5),
				new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_COMMON), 3), 4, 5),
				new CAIngredientTrade(3, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS), 1), 9, 8),
				new CAIngredientTrade(2, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_COMMON), 1), 3, 8));
		TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.JOURNEYMAN,
				new CABasicTrade(CAItems.ALUMINUM_POWER_CHIP.get(), 8, 2, 15, 8, 2),
				new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_UNCOMMON), 2), 3, 10),
				new CAIngredientTrade(4, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS), 2), 6, 10),
				new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_UNCOMMON), 1), 6, 20),
				new CAIngredientTrade(Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS), 6), 11, 3, 20));
		TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.EXPERT,
				new CABasicTrade(1, CAItems.ALUMINUM_POWER_CHIP.get(), 6, 5, 20),
				new CABasicTrade(Items.LAVA_BUCKET, 1, 2, 2, 20),
				new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS), 2), 3, 20),
				new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_RARE), 1), 6, 20),
				new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_RARE), 1), 6, 30));
		TradeUtil.addVillagerTrades(event, CAVillagers.ARCHAEOLOGIST.get(), TradeUtil.MASTER,
				new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS), 3), 6, 30),
				new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_FOSSILS_EPIC), 1), 6, 30),
				new CAIngredientTrade(1, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_RARE), 2), 7, 30),
				new CAIngredientTrade(2, Pair.of(Ingredient.of(CATags.Items.ARCHAEOLOGIST_SPAWN_EGGS_EPIC), 1), 3, 40));
	}

	@SuppressWarnings("unused")
	private static boolean notOnBlacklist(Item item, String[] items) {
		for (String name : items) if (Objects.requireNonNull(item.getRegistryName()).toString().contains(name)) return false;
		return true;
	}

	@SuppressWarnings("unused")
	private static boolean notOnBlacklist(Item item, String[] items, String[] items2) {
		for (String name : items) if (Objects.requireNonNull(item.getRegistryName()).toString().contains(name)) return false;
		for (String name : items2) if (Objects.requireNonNull(item.getRegistryName()).toString().contains(name)) return false;
		return true;
	}
}
