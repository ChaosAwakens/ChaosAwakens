package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.RegistryKey;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAVillagers {
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, ChaosAwakens.MODID);
	public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ChaosAwakens.MODID);

	public static void registerVillagerTypes() {
		VillagerTrades.TRADES.isEmpty();
	}

	@SuppressWarnings("unused")
	private static VillagerType createType(String type) {
		return VillagerType.register(ChaosAwakens.MODID + ":" + type);
	}

	@SuppressWarnings("unused")
	@SafeVarargs
	private static void registerVillagerType(VillagerType type, RegistryKey<Biome>... biomes) {
		for (RegistryKey<Biome> biome : biomes) VillagerType.BY_BIOME.put(biome, type);
	}
}
