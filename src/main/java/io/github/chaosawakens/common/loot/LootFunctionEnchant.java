package io.github.chaosawakens.common.loot;

import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.HashMap;
import java.util.Map;

public class LootFunctionEnchant extends LootFunction {
	private final Map<IRegistryDelegate<Enchantment>, Short> enchantments;

	protected LootFunctionEnchant(ILootCondition[] conditions, Map<IRegistryDelegate<Enchantment>, Short> enchantments) {
		super(conditions);
		this.enchantments = enchantments;
	}

	public static LootFunctionEnchant.Builder builder() {
		return new LootFunctionEnchant.Builder();
	}

	@Override
	public LootFunctionType getType() {
		return CATreasure.ENCHANT;
	}

	@Override
	public ItemStack run(ItemStack stack, LootContext context) {
		for (Map.Entry<IRegistryDelegate<Enchantment>, Short> enchantmentEntry : enchantments.entrySet()) {
			if (stack.getItem() == Items.ENCHANTED_BOOK) EnchantedBookItem.addEnchantment(stack, new EnchantmentData(enchantmentEntry.getKey().get(), enchantmentEntry.getValue()));
			else stack.enchant(enchantmentEntry.getKey().get(), enchantmentEntry.getValue());
		}
		return stack;
	}

	public static class Builder extends LootFunction.Builder<LootFunctionEnchant.Builder> {
		private final Map<IRegistryDelegate<Enchantment>, Short> enchants = Maps.newHashMap();

		protected LootFunctionEnchant.Builder getThis() {
			return this;
		}

		public LootFunctionEnchant.Builder apply(Enchantment targetEnchantment, Integer level) {
			this.enchants.put(targetEnchantment.delegate, level.shortValue());
			return this;
		}

		public ILootFunction build() {
			return new LootFunctionEnchant(this.getConditions(), this.enchants);
		}
	}

	public static class Serializer extends LootFunction.Serializer<LootFunctionEnchant> {
		@Override
		public void serialize(JsonObject object, LootFunctionEnchant function, JsonSerializationContext ctx) {
			if (!function.enchantments.isEmpty()) {
				JsonObject obj = new JsonObject();

				for (Map.Entry<IRegistryDelegate<Enchantment>, Short> enchantmentEntry : function.enchantments.entrySet()) obj.addProperty(enchantmentEntry.getKey().get().getRegistryName().toString(), enchantmentEntry.getValue());

				object.add("enchantments", obj);
			}
		}

		@Override
		public LootFunctionEnchant deserialize(JsonObject object, JsonDeserializationContext ctx, ILootCondition[] conditions) {
			Map<IRegistryDelegate<Enchantment>, Short> enchantments = new HashMap<>();

			if (object.has("enchantments")) {
				JsonObject enchantObj = JSONUtils.getAsJsonObject(object, "enchantments");

				for (Map.Entry<String, JsonElement> enchantmentEntry : enchantObj.entrySet()) {
					ResourceLocation enchantmentId = new ResourceLocation(enchantmentEntry.getKey());
					if (!ForgeRegistries.ENCHANTMENTS.containsKey(enchantmentId)) throw new JsonSyntaxException("Can't find enchantment " + enchantmentEntry.getKey());

					Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(enchantmentId);
					short lvl = enchantmentEntry.getValue().getAsShort();

					for (IRegistryDelegate<Enchantment> other : enchantments.keySet()) {
						if (!ench.isCompatibleWith(other.get())) throw new JsonParseException(String.format("Enchantments %s and %s conflict", ench.getRegistryName(), other.get().getRegistryName()));
					}

					enchantments.put(ench.delegate, lvl);
				}
			}

			return new LootFunctionEnchant(conditions, enchantments);
		}
	}
}
