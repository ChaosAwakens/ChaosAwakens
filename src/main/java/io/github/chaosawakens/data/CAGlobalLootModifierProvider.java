package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.UltimatePickaxeItem;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CAGlobalLootModifiers;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class CAGlobalLootModifierProvider extends GlobalLootModifierProvider {
	public CAGlobalLootModifierProvider(DataGenerator generator) {
		super(generator, ChaosAwakens.MODID);
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + " Global Loot Modifiers";
	}

	@Override
	protected void start() {
		add("ultimate_pickaxe_smelting", CAGlobalLootModifiers.ULTIMATE_PICKAXE_SMELTING.get(),
				new UltimatePickaxeItem.UltimateAutoSmeltingModifier(new ILootCondition[] {
						MatchTool.toolMatches(ItemPredicate.Builder.item().of(CAItems.ULTIMATE_PICKAXE.get())).build()
				}));
	}
}
