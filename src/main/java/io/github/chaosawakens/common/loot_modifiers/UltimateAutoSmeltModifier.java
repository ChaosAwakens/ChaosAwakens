package io.github.chaosawakens.common.loot_modifiers;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class UltimateAutoSmeltModifier extends LootModifier {
	
	public UltimateAutoSmeltModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}
	
	protected final ItemStack getSmeltedOutput(LootContext context, ItemStack stack) {
		if (context.getLevel() != null) {
			for (PlayerEntity targetPlayer : context.getLevel().getPlayers((player) -> player.isCrouching())) {
				int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, targetPlayer.getMainHandItem());
				boolean hasFortune = fortuneLevel > 0;
				Random random = new Random();
				
				return context.getLevel().getRecipeManager()
						.getRecipeFor(IRecipeType.SMELTING, new Inventory(stack), context.getLevel())
						.map(FurnaceRecipe::getResultItem).filter(targetStack -> !targetStack.isEmpty() && targetStack != null)
						.map(targetStack -> hasFortune && targetPlayer.isCrouching() ? copyStackWithSize(targetStack, stack.getCount() + random.nextInt(fortuneLevel + 1)) : copyStackWithSize(targetStack, stack.getCount())).orElse(stack);
			}
		} 
		return stack;
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
		List<ItemStack> stackArrayList = new ObjectArrayList<>();
		generatedLoot.forEach((targetStack) -> stackArrayList.add(getSmeltedOutput(context, targetStack)));
		return stackArrayList;
	}
	
	public static class Serializer extends GlobalLootModifierSerializer<UltimateAutoSmeltModifier> {
		@Override
		public UltimateAutoSmeltModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
			return new UltimateAutoSmeltModifier(conditionsIn);
		}
		
		@Override
		public JsonObject write(UltimateAutoSmeltModifier instance) {
			return makeConditions(instance.conditions);
		}
	}
}
