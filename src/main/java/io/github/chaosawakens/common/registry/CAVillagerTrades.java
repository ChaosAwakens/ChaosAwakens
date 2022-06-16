package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.util.TradeUtil;
import io.github.chaosawakens.common.util.TradeUtil.CATrade;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID)
public class CAVillagerTrades {
	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		TradeUtil.addWandererTrades(event,
				new CATrade(1, CABlocks.CYAN_ROSE.get().asItem(), 12),
				new CATrade(1, CABlocks.RED_ROSE.get().asItem(), 12),
				new CATrade(1, CABlocks.PAEONIA.get().asItem(), 12),

				new CATrade(3, CABlocks.TUBE_WORM.get().asItem(), 12),

				new CATrade(5, CABlocks.APPLE_SAPLING.get().asItem(), 8),
				new CATrade(5, CABlocks.CHERRY_SAPLING.get().asItem(), 8),
				new CATrade(5, CABlocks.PEACH_SAPLING.get().asItem(), 8),

				new CATrade(5, Items.NAME_TAG, 5));


		if (CACommonConfig.COMMON.wanderingTraderSellsTriffidGoo.get()) {
			TradeUtil.addWandererTrades(event,
					new CATrade(4, CAItems.TRIFFID_GOO.get().asItem(), 5));
		}

		if (CACommonConfig.COMMON.wanderingTraderSellsUraniumAndTitanium.get()) {
			TradeUtil.addRareWandererTrades(event,
					new CATrade(15, CAItems.TITANIUM_NUGGET.get(), 3),
					new CATrade(15, CAItems.URANIUM_NUGGET.get(), 3));
		}
	}

	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.NOVICE,
				new CATrade(2, CAItems.CHERRIES.get(), 6, 16, 2),
				new CATrade(2, CAItems.LETTUCE.get(), 6, 12, 2),
				new CATrade(1, CAItems.CORN.get(), 5, 12, 3),
				new CATrade(1, CAItems.TOMATO.get(), 5, 12, 3));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.APPRENTICE,
				new CATrade(1, CAItems.PEACH.get(), 6, 16, 5),
				new CATrade(2, CAItems.STRAWBERRY.get(), 6, 12, 5),
				new CATrade(1, CAItems.RADISH.get(), 5, 12, 8));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.JOURNEYMAN,
				new CATrade(1, CAItems.RADISH_STEW.get(), 1, 12, 15));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.EXPERT,
				new CATrade(1, CAItems.GOLDEN_MELON_SLICE.get().asItem(), 4, 12, 15),
				new CATrade(1, CAItems.GOLDEN_POTATO.get().asItem(), 4, 12, 15));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.MASTER,
				new CATrade(CAItems.GOLDEN_MELON_SLICE.get().asItem(), 6, 1, 12, 30),
				new CATrade(CAItems.GOLDEN_POTATO.get().asItem(), 6, 1, 12, 30));

		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.NOVICE,
				new CATrade(CAItems.BACON.get(), 18, 1, 16, 2),
				new CATrade(CAItems.CORNDOG.get(), 18, 1, 16, 2));
		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.APPRENTICE,
				new CATrade(1, CAItems.COOKED_BACON.get(), 6, 16, 5),
				new CATrade(1, CAItems.COOKED_CORNDOG.get(), 6, 16, 5));
		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.JOURNEYMAN,
				new CATrade(CAItems.CRAB_MEAT.get(), 12, 1, 16, 20),
				new CATrade(1, CAItems.COOKED_CRAB_MEAT.get(), 5, 16, 10));
		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.EXPERT,
				new CATrade(CAItems.PEACOCK_LEG.get(), 12, 1, 16, 20),
				new CATrade(1, CAItems.COOKED_PEACOCK_LEG.get(), 5, 16, 20));

		TradeUtil.addVillagerTrades(event, VillagerProfession.FLETCHER, TradeUtil.MASTER,
				new CATrade(3, CAItems.PEACOCK_FEATHER.get(), 4, 12, 30));

		TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.EXPERT,
				new CATrade(5, CAItems.GREEN_FISH.get(), 1, 16, 30),
				new CATrade(5, CAItems.ROCK_FISH.get(), 1, 16, 30),
				new CATrade(5, CAItems.SPARK_FISH.get(), 1, 16, 30),
				new CATrade(5, CAItems.WOOD_FISH.get(), 1, 16, 30));
		TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.MASTER,
				new CATrade(3, CAItems.LAVA_EEL.get(), 1, 12, 30));
	}

//    @SubscribeEvent
//    public static void onOPTradesEvent(VillagerTradesEvent event) {
//        TradeUtil.addVillagerTrades(event, CAVillagers.ODDITIES_PURVEYOR.get(), TradeUtil.NOVICE,
//                new CATrade(CAItems.TIN_LUMP.get(), 3, 4, 3, 3),
//                new CATrade(CAItems.PLATINUM_LUMP.get(), 1, 3, 1, 6)
//        );
//        TradeUtil.addVillagerTrades(event, CAVillagers.ODDITIES_PURVEYOR.get(), TradeUtil.APPRENTICE,
//                new CATrade(1, Items.FLOWER_POT, 1, 10, 10),
//                new CATrade(6, Blocks.COAL_BLOCK.asItem(), 2, 5, 10),
//                new CATrade(13, Blocks.IRON_BLOCK.asItem(), 1, 3, 15)
//        );
//        TradeUtil.addVillagerTrades(event, CAVillagers.ODDITIES_PURVEYOR.get(), TradeUtil.JOURNEYMAN,
//                new CATrade(CAItems.TIN_LUMP.get(), 3, 4, 3, 19),
//                new CATrade(CAItems.PLATINUM_LUMP.get(), 1, 1, 1, 21)
//        );
//        TradeUtil.addVillagerTrades(event, CAVillagers.ODDITIES_PURVEYOR.get(), TradeUtil.EXPERT,
//                new CATrade(CAItems.TIN_LUMP.get(), 3, 4, 3, 28),
//                new CATrade(CAItems.PLATINUM_LUMP.get(), 1, 5, 1, 30)
//        );
//        TradeUtil.addVillagerTrades(event, CAVillagers.ODDITIES_PURVEYOR.get(), TradeUtil.MASTER,
//                new CATrade(CAItems.COPPER_LUMP.get(), 4, CAItems.TIN_LUMP.get(), 2, 3, 40),
//                new CATrade(CAItems.PLATINUM_LUMP.get(), 1, 7, 1, 46)
//        );
//    }

	private static boolean notOnBlacklist(Item item, String[] items) {
		for (String name : items) if (item.getRegistryName().toString().contains(name)) return false;
		return true;
	}

	private static boolean notOnBlacklist(Item item, String[] items, String[] items2) {
		for (String name : items) if (item.getRegistryName().toString().contains(name)) return false;
		for (String name : items2) if (item.getRegistryName().toString().contains(name)) return false;
		return true;
	}
}
