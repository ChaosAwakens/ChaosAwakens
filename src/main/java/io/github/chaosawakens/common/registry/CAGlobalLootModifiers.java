package io.github.chaosawakens.common.registry;

import com.google.gson.JsonObject;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.items.UltimatePickaxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class CAGlobalLootModifiers {
	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, ChaosAwakens.MODID);

	public static final RegistryObject<UltimatePickaxeItem.UltimateAutoSmeltingModifier.Serializer> ULTIMATE_PICKAXE_SMELTING = LOOT_MODIFIERS.register("ultimate_pickaxe_smelting", UltimatePickaxeItem.UltimateAutoSmeltingModifier.Serializer::new);
}
