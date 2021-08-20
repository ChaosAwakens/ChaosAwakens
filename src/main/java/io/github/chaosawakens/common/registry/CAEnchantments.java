package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.enchantments.EnchantmentWitherCurse;
import io.github.chaosawakens.common.enchantments.EnchantmentKiller;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CAEnchantments {
	
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ChaosAwakens.MODID);

	 public static final RegistryObject<EnchantmentKiller> KILLER = ENCHANTMENTS.register("killer", () -> new EnchantmentKiller(Enchantment.Rarity.VERY_RARE, 5, EnchantmentType.WEAPON, EquipmentSlotType.MAINHAND));
	 public static final RegistryObject<EnchantmentWitherCurse> CURSE_OF_DEATH = ENCHANTMENTS.register("curse_of_withering", () -> new EnchantmentWitherCurse(Enchantment.Rarity.RARE, 1, EnchantmentType.ARMOR, EquipmentSlotType.CHEST));
}
