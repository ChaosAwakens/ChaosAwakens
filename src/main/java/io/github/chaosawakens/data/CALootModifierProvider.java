package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;

import io.github.chaosawakens.common.items.UltimatePickaxeItem;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CALootModifiers;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class CALootModifierProvider extends GlobalLootModifierProvider {
	public CALootModifierProvider(DataGenerator generator, String modid) {
		super(generator, modid);
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + " Loot Modifiers";
	}

	@Override
	protected void start() {
		add("ultimate_pickaxe_smelting", CALootModifiers.ULTIMATE_PICKAXE_SMELTING.get(),
				new UltimatePickaxeItem.UltimateAutoSmeltingModifier(new ILootCondition[] {
						MatchTool.toolMatches(ItemPredicate.Builder.item().of(CAItems.ULTIMATE_PICKAXE.get()))
						.build()
				}));
	}
}
