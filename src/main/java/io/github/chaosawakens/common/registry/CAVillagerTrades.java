package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.util.TradeUtil;
import io.github.chaosawakens.common.util.TradeUtil.CATrade;
import net.minecraft.block.Blocks;
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
                new CATrade(3, CAItems.GOLDEN_MELON_SEEDS.get().asItem(), 4, 2, 1),
                new CATrade(1, CAItems.GOLDEN_BREAD.get().asItem(), 2, 4, 1),
                new CATrade(2, CAItems.GOLDEN_CANDYCANE.get().asItem(), 3, 3, 1),
                new CATrade(1, CAItems.GOLDEN_POTATO.get().asItem(), 4, 2, 1),

                new CATrade(1, CAItems.TIGERS_EYE.get().asItem(), 1, 5, 1),
                new CATrade(3, CAItems.AMETHYST.get().asItem(), 1, 5, 1),
                new CATrade(5, CAItems.RUBY.get().asItem(), 1, 5, 1)
        );

        TradeUtil.addRareWandererTrades(event,
                new CATrade(12, CAItems.TITANIUM_NUGGET.get(), 1, 3, 5),
                new CATrade(12, CAItems.URANIUM_NUGGET.get(), 1, 3, 5)
        );
    }

    @SubscribeEvent
    public static void onVillagerTradesEvent(VillagerTradesEvent event) {
        TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.NOVICE,
                new CATrade(1, CAItems.CHERRIES.get(), 6, 16, 5),
                new CATrade(1, CAItems.CORN.get(), 5, 12, 5),
                new CATrade(1, CAItems.TOMATO.get(), 5, 12, 5),
                new CATrade(1, CAItems.LETTUCE.get(), 6, 12, 5)
        );
        TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.APPRENTICE,
                new CATrade(1, CAItems.PEACH.get(), 6, 16, 5),
                new CATrade(1, CAItems.RADISH.get(), 5, 12, 5),
                new CATrade(1, CAItems.STRAWBERRY.get(), 6, 12, 5)
        );
        TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.JOURNEYMAN,
                new CATrade(1, CAItems.RADISH_STEW.get(), 5, 12, 5)
        );

        TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.NOVICE,
                new CATrade(CAItems.BACON.get(), 18, 1, 16, 2),
                new CATrade(CAItems.CORNDOG.get(), 18, 1, 16, 2)
        );
        TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.APPRENTICE,
                new CATrade(1, CAItems.COOKED_BACON.get(), 6, 16, 5),
                new CATrade(1, CAItems.COOKED_CORNDOG.get(), 6, 16, 5)
        );
        TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.JOURNEYMAN,
                new CATrade(CAItems.CRAB_MEAT.get(), 12, 1, 16, 20),
                new CATrade(1, CAItems.COOKED_CRAB_MEAT.get(), 5, 16, 10)
        );
        TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.EXPERT,
                new CATrade(CAItems.PEACOCK_LEG.get(), 12, 1, 16, 20),
                new CATrade(1, CAItems.COOKED_PEACOCK_LEG.get(), 5, 16, 10)
        );

        TradeUtil.addVillagerTrades(event, VillagerProfession.FLETCHER, TradeUtil.MASTER,
                new CATrade(1, CAItems.PEACOCK_FEATHER.get(), 4, 2, 3)
        );
    }

    @SubscribeEvent
    public static void onDTTradesEvent(VillagerTradesEvent event) {
        TradeUtil.addVillagerTrades(event, CAVillagers.DIMENSIONAL_TRADER.get(), TradeUtil.NOVICE,
                new CATrade(CAItems.TIN_LUMP.get(), 3, 4, 3, 3),
                new CATrade(CAItems.PLATINUM_LUMP.get(), 1, 3, 1, 6)
        );
        TradeUtil.addVillagerTrades(event, CAVillagers.DIMENSIONAL_TRADER.get(), TradeUtil.APPRENTICE,
                new CATrade(1, Items.FLOWER_POT, 1, 10, 10),
                new CATrade(6, Blocks.COAL_BLOCK.asItem(), 2, 5, 10),
                new CATrade(13, Blocks.IRON_BLOCK.asItem(), 1, 3, 15)
        );
        TradeUtil.addVillagerTrades(event, CAVillagers.DIMENSIONAL_TRADER.get(), TradeUtil.JOURNEYMAN,
                new CATrade(CAItems.TIN_LUMP.get(), 3, 4, 3, 19),
                new CATrade(CAItems.PLATINUM_LUMP.get(), 1, 1, 1, 21)
        );
        TradeUtil.addVillagerTrades(event, CAVillagers.DIMENSIONAL_TRADER.get(), TradeUtil.EXPERT,
                new CATrade(CAItems.TIN_LUMP.get(), 3, 4, 3, 28),
                new CATrade(CAItems.PLATINUM_LUMP.get(), 1, 5, 1, 30)
        );
        TradeUtil.addVillagerTrades(event, CAVillagers.DIMENSIONAL_TRADER.get(), TradeUtil.MASTER,
                new CATrade(CAItems.COPPER_LUMP.get(), 4, CAItems.TIN_LUMP.get(), 2, 3, 40),
                new CATrade(CAItems.PLATINUM_LUMP.get(), 1, 7, 1, 46)
        );
    }

    private static boolean notOnBlacklist(Item item, String[] items) {
        for (String name : items) {
            if (item.getRegistryName().toString().contains(name))
                return false;
        }
        return true;
    }

    private static boolean notOnBlacklist(Item item, String[] items, String[] items2) {
        for (String name : items) {
            if (item.getRegistryName().toString().contains(name))
                return false;
        }
        for (String name : items2) {
            if (item.getRegistryName().toString().contains(name))
                return false;
        }
        return true;
    }
}
