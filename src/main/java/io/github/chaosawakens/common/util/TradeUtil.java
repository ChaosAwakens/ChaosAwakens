package io.github.chaosawakens.common.util;

import java.util.Random;


import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;

public class TradeUtil {
	public static final int NOVICE = 1;
	public static final int APPRENTICE = 2;
	public static final int JOURNEYMAN = 3;
	public static final int EXPERT = 4;
	public static final int MASTER = 5;

	public static void addVillagerTrades(VillagerTradesEvent event, int level, VillagerTrades.ITrade... trades) {
		for (VillagerTrades.ITrade trade : trades) event.getTrades().get(level).add(trade);
	}

	public static void addVillagerTrades(VillagerTradesEvent event, VillagerProfession profession, int level, VillagerTrades.ITrade... trades) {
		if (event.getType() == profession) addVillagerTrades(event, level, trades);
	}

	public static void addWandererTrades(WandererTradesEvent event, VillagerTrades.ITrade... trades) {
		for (VillagerTrades.ITrade trade : trades) event.getGenericTrades().add(trade);
	}

	public static void addRareWandererTrades(WandererTradesEvent event, VillagerTrades.ITrade... trades) {
		for (VillagerTrades.ITrade trade : trades) event.getRareTrades().add(trade);
	}

	public static class CABasicTrade extends BasicTrade {
		public CABasicTrade(ItemStack input, ItemStack input2, ItemStack output, int maxTrades, int xp, float priceMult) {
			super(input, input2, output, maxTrades, xp, priceMult);
		}

		public CABasicTrade(Item input, int inputCount, Item output, int outputCount, int maxTrades, int xp, float priceMult) {
			this(new ItemStack(input, inputCount), ItemStack.EMPTY, new ItemStack(output, outputCount), maxTrades, xp, priceMult);
		}

		public CABasicTrade(Item input, int inputCount, Item output, int outputCount, int maxTrades, int xp) {
			this(input, inputCount, output, outputCount, maxTrades, xp, 0.15F);
		}

		public CABasicTrade(Item input, int inputCount, int emeraldCount, int maxTrades, int xp, float priceMult) {
			this(new ItemStack(input, inputCount), ItemStack.EMPTY, new ItemStack(Items.EMERALD, emeraldCount), maxTrades, xp, priceMult);
		}

		public CABasicTrade(Item input, int inputCount, int emeraldCount, int maxTrades, int xp) {
			this(input, inputCount, emeraldCount, maxTrades, xp, 0.15F);
		}

		public CABasicTrade(int emeraldCount, Item output, int outputCount, int maxTrades, int xp, float priceMult) {
			this(new ItemStack(Items.EMERALD, emeraldCount), ItemStack.EMPTY, new ItemStack(output, outputCount), maxTrades, xp, priceMult);
		}

		public CABasicTrade(int emeraldCount, Item output, int outputCount, int maxTrades, int xp) {
			this(emeraldCount, output, outputCount, maxTrades, xp, 0.15F);
		}

		public CABasicTrade(int emeraldCount, Item output, int maxTrades) {
			this(emeraldCount, output, 1, maxTrades, 0, 0);
		}
	}

	public static class CAIngredientTrade extends IngredientTrade {
		public CAIngredientTrade(Pair<Ingredient, Integer> input, Pair<Ingredient, Integer> input2, Pair<Ingredient, Integer> forSale, int maxTrades, int xp, float priceMult) {
			super(input, input2, forSale, maxTrades, xp, priceMult);
		}

		public CAIngredientTrade(Pair<Ingredient, Integer> input, Pair<Ingredient, Integer> forSale, int maxTrades, int xp) {
			this(input, Pair.of(Ingredient.EMPTY, 0), forSale, maxTrades, xp, 0.15F);
		}

		public CAIngredientTrade(int emeraldCount, Pair<Ingredient, Integer> forSale, int maxTrades, int xp) {
			this(Pair.of(Ingredient.of(Items.EMERALD), emeraldCount), forSale, maxTrades, xp);
		}

		public CAIngredientTrade(Pair<Ingredient, Integer> input, Pair<Ingredient, Integer> input2, Pair<Ingredient, Integer> forSale, int maxTrades, int xp) {
			this(input, input2, forSale, maxTrades, xp, 0.15F);
		}

		public CAIngredientTrade(Pair<Ingredient, Integer> input, Pair<Ingredient, Integer> input2, int emeraldCount, int maxTrades, int xp) {
			this(input, input2, Pair.of(Ingredient.of(Items.EMERALD), emeraldCount), maxTrades, xp);
		}

		public CAIngredientTrade(Pair<Ingredient, Integer> input, int emeraldCount, int maxTrades, int xp) {
			this(input, Pair.of(Ingredient.EMPTY, 0), Pair.of(Ingredient.of(Items.EMERALD), emeraldCount), maxTrades, xp);
		}
	}

	public static class IngredientTrade implements VillagerTrades.ITrade {
		protected final Pair<Ingredient, Integer> input;
		protected final Pair<Ingredient, Integer> input2;
		protected final Pair<Ingredient, Integer> forSale;
		protected final int maxTrades;
		protected final int xp;
		protected final float priceMult;

		public IngredientTrade(Pair<Ingredient, Integer> input, Pair<Ingredient, Integer> input2, Pair<Ingredient, Integer> forSale, int maxTrades, int xp, float priceMult) {
			this.input = input;
			this.input2 = input2;
			this.forSale = forSale;
			this.maxTrades = maxTrades;
			this.xp = xp;
			this.priceMult = priceMult;
		}

		@Nullable
		@Override
		public MerchantOffer getOffer(Entity trader, Random rand) {
			ItemStack input = getRandomItemStack(this.input.getFirst(), rand);
			input.setCount(this.input.getSecond());

			ItemStack input2 = getRandomItemStack(this.input2.getFirst(), rand);
			input2.setCount(this.input2.getSecond());

			ItemStack forSale = getRandomItemStack(this.forSale.getFirst(), rand);
			forSale.setCount(this.forSale.getSecond());

			return new MerchantOffer(input, input2, forSale, maxTrades, xp, priceMult);
		}

		private ItemStack getRandomItemStack(Ingredient ingredient, Random random) {
			if(ingredient.isEmpty())
				return ItemStack.EMPTY;

			if(ingredient.getItems().length > 1)
				return ingredient.getItems()[random.nextInt(ingredient.getItems().length)];
			else
				return ingredient.getItems()[0];
		}
	}
}
