package io.github.chaosawakens.common.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import io.github.chaosawakens.api.IAutoEnchantable;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class UltimatePickaxeItem extends EnchantedPickaxeItem implements IAutoEnchantable {
	
	public UltimatePickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
	}
	
	@Override
	public boolean isFireResistant() {
		return true;
	}
	
	@Override
	public float getXpRepairRatio(ItemStack stack) {
		return 20.0F;
	}
	
	public static class UltimateAutoSmeltingModifier extends LootModifier {
		public UltimateAutoSmeltingModifier(ILootCondition[] conditionsIn) {
			super(conditionsIn);
		}
		
		protected final ItemStack getSmeltedOutput(LootContext context, ItemStack stack) {
			if (context.getLevel() != null) {
				//TODO make this more efficient, cause currently it just loops through each player to check if they're crouching, so it may 
				//potentially cause problems in very large servers --Meme Man
				//Also I didn't have to do MinecraftForge.EVENT_BUS.post(event)??? :O
				int i = 0;
				PlayerEvent e = new PlayerEvent(context.getLevel().players().get(i++));
				if (i > context.getLevel().players().size()) i = 0;
				if (!e.getPlayer().isCrouching()) return stack;
				
				int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, e.getPlayer().getMainHandItem());
				boolean hasFortune = fortuneLevel > 0;
				
				return context.getLevel().getRecipeManager()
						.getRecipeFor(IRecipeType.SMELTING, new Inventory(stack), context.getLevel())
						.map(FurnaceRecipe::getResultItem).filter(itemStack -> !itemStack.isEmpty())
						.map(itemStack -> hasFortune ? copyStackWithSize(itemStack, stack.getCount() + random.nextInt(fortuneLevel + 1)) : copyStackWithSize(itemStack, stack.getCount())).orElse(stack);
			} else return stack;
		}
		
		@Nonnull
		public static ItemStack copyStackWithSize(@Nonnull ItemStack itemStack, int size) {
			if (size == 0) return ItemStack.EMPTY;
			ItemStack copy = itemStack.copy();
			copy.setCount(size);
			return copy;
		}
		
		@Nonnull
		@Override
		protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
			ArrayList<ItemStack> stackArrayList = new ArrayList<>();
			generatedLoot.forEach((stack) -> stackArrayList.add(getSmeltedOutput(context, stack)));
			return stackArrayList;
		}
		
		public static class Serializer extends GlobalLootModifierSerializer<UltimateAutoSmeltingModifier> {
			@Override
			public UltimateAutoSmeltingModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
				return new UltimateAutoSmeltingModifier(conditionsIn);
			}
			
			@Override
			public JsonObject write(UltimateAutoSmeltingModifier instance) {
				return makeConditions(instance.conditions);
			}
		}
	}
}
