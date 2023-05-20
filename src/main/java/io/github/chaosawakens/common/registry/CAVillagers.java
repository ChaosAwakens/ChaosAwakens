package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableSet;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerBlock.DefossilizerType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.structure.DesertVillagePools;
import net.minecraft.world.gen.feature.structure.PlainsVillagePools;
import net.minecraft.world.gen.feature.structure.SavannaVillagePools;
import net.minecraft.world.gen.feature.structure.SnowyVillagePools;
import net.minecraft.world.gen.feature.structure.TaigaVillagePools;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAVillagers {
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, ChaosAwakens.MODID);
	public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ChaosAwakens.MODID);

	public static final RegistryObject<PointOfInterestType> ARCHAEOLOGIST_POI = POI_TYPES.register("archaeologist",
			() -> new PointOfInterestType("archaeologist", PointOfInterestType.getBlockStates(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()), 1, 2));
	public static final RegistryObject<VillagerProfession> ARCHAEOLOGIST = PROFESSIONS.register("archaeologist",
			() -> new VillagerProfession("archaeologist", ARCHAEOLOGIST_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CARTOGRAPHER));

	public static void registerVillagerTypes() {
		VillagerTrades.TRADES.isEmpty();
		
		DesertVillagePools.bootstrap();
		PlainsVillagePools.bootstrap();
		SavannaVillagePools.bootstrap();
		SnowyVillagePools.bootstrap();
		TaigaVillagePools.bootstrap();
		
		ChaosAwakens.debug("VG", "Generating Archaeologist House");
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
