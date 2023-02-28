package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.enchantments.HoplologyEnchantment;
import io.github.chaosawakens.common.enchantments.IgniteEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAEnchantments {
	
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ChaosAwakens.MODID);
	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
	
	public static final RegistryObject<Enchantment> IGNITE = ENCHANTMENTS.register("ignite", () -> new IgniteEnchantment(ARMOR_SLOTS));
	public static final RegistryObject<Enchantment> HOPLOLOGY = ENCHANTMENTS.register("hoplology", () -> new HoplologyEnchantment(ARMOR_SLOTS));
	
}
