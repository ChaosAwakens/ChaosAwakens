package io.github.chaosawakens.common;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


//Made by ya boi Meme Man

public class CAVillagerInit{
	public static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ChaosAwakens.MODID);
	
	public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, ChaosAwakens.MODID);
	
	public static final RegistryObject<PointOfInterestType> BLAST_BUILDING = POINT_OF_INTEREST_TYPES.register("blast_building",
			()->new PointOfInterestType("blast_building", PointOfInterestType.getAllStates(CABlocks.CRYSTAL_FURNACE.get()), 1, 2));
	
	public static final RegistryObject<VillagerProfession> CHAOTIC_BLASTER = VILLAGER_PROFESSIONS.register("chaotic_blaster",
			()->new VillagerProfession("chaotic_blaster", BLAST_BUILDING.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_ARMORER));
	
	public static void registerPOI(){
		try {
			ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "registerBlockStates", PointOfInterestType.class).invoke(null, BLAST_BUILDING.get());
		}catch(IllegalAccessException | InvocationTargetException e) {//Catching errors and exceptions be like (yay I learned how to use those :D)
			e.printStackTrace();
		}
	}
	public static void fillTradeData() {
		MultiItemsForEmeraldsTrade multiTrade = new MultiItemsForEmeraldsTrade(ImmutableList.of(Items.DIAMOND, CAItems.RUBY.get(), Items.IRON_INGOT), ImmutableList.of(5, 4, 1), ImmutableList.of(6, 3, 1), 5, 50);
		VillagerTrades.ITrade[] level1 = new VillagerTrades.ITrade[]{new VillagerTrades.EmeraldForItemsTrade(CAItems.ALUMINUM_INGOT.get(), 3, 4, 3), new VillagerTrades.ItemsForEmeraldsTrade(CAItems.TIN_LUMP.get(), 2, 7, 2)};
		VillagerTrades.ITrade[] level2 = new VillagerTrades.ITrade[]{multiTrade};//lol set the trades how you want to
		VillagerTrades.ITrade[] level3 = new VillagerTrades.ITrade[]{multiTrade};
		VillagerTrades.ITrade[] level4 = new VillagerTrades.ITrade[]{multiTrade};
		VillagerTrades.ITrade[] level5 = new VillagerTrades.ITrade[]{multiTrade};
		VillagerTrades.VILLAGER_DEFAULT_TRADES.put(CHAOTIC_BLASTER.get(), gatAsIntMap(ImmutableMap.of(1, level1, 2, level2, 3, level3, 4, level4, 5, level5)));
	}
    private static Int2ObjectMap<VillagerTrades.ITrade[]> gatAsIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
		    return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }//Needed part from minecraft's VillagerTrades class
    
    public static class MultiItemsForEmeraldsTrade implements VillagerTrades.ITrade{
    	private final List<Item> items;
    	private final List<Integer> amountOfItems;
    	private final List<Integer> amountOfEmeralds;
    	private final int uses;
    	private final int villagerEXP;
    	
        public MultiItemsForEmeraldsTrade(List<Item> items, List<Integer> amountOfItems, List<Integer> amountOfEmeralds, int uses, int villagerEXP) {
        	this.items = items;
        	this.amountOfItems = amountOfItems;
        	this.amountOfEmeralds = amountOfEmeralds;
        	this.uses = uses;
        	this.villagerEXP = villagerEXP;   	
        }
    	@Nullable
		@Override
		public MerchantOffer getOffer(@Nonnull Entity entity, Random random) {
			int choose = (int) (random.nextFloat() * items.size());
			return new MerchantOffer(new ItemStack(Items.EMERALD, amountOfEmeralds.get(choose)), new ItemStack((IItemProvider) items.get(choose), amountOfItems.get(0)), this.uses, this.villagerEXP, 0.05f);
		}
    	//MultiItemsForEmeraldsTrade. Specify the items in brackets of course. First 3 integers are the item amounts in respective order. Second 3 integers are emerald amounts each in respective order. after that it's the max uses, then the xp gained for the villager and you (in points)
    }
}