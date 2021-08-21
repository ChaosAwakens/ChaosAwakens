package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableSet;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.entity.ai.brain.task.GiveHeroGiftsTask;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAVillagers {
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, ChaosAwakens.MODID);
	public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ChaosAwakens.MODID);

	public static final RegistryObject<PointOfInterestType> BLAST_BUILDING = POI_TYPES.register("blast_building", () -> new PointOfInterestType("blast_building", PointOfInterestType.getBlockStates(CABlocks.CRYSTAL_FURNACE.get()), 1, 2));

	public static final RegistryObject<VillagerProfession> ODDITIES_PURVEYOR = PROFESSIONS.register("oddities_purveyor", () -> new VillagerProfession("oddities_purveyor", BLAST_BUILDING.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_ARMORER));

	public static void registerVillagerTypes() {
		VillagerTrades.TRADES.isEmpty();

		GiveHeroGiftsTask.gifts.put(ODDITIES_PURVEYOR.get(), new ResourceLocation(ChaosAwakens.MODID, "gameplay/hero_of_the_village/oddities_purveyor_gift"));
	}

	private static VillagerType createType(String type) {
		return VillagerType.register(ChaosAwakens.MODID + ":" + type);
	}

	@SafeVarargs
	private static void registerVillagerType(VillagerType type, RegistryKey<Biome>... biomes) {
		for (RegistryKey<Biome> biome : biomes) {
			VillagerType.BY_BIOME.put(biome, type);
		}
	}

//	private static void setupVillagerHouses() {
//		PlainsVillagePools.init();
//		SnowyVillagePools.init();
//		SavannaVillagePools.init();
//		DesertVillagePools.init();
//		TaigaVillagePools.init();
//
//		addVillagerHouse("ceramist", "plains", 2);
//		addVillagerHouse("ceramist", "snowy", 4);
//		addVillagerHouse("ceramist", "savanna", 6);
//		addVillagerHouse("ceramist", "desert", 5);
//		addVillagerHouse("ceramist", "taiga", 7);
//
//		addVillagerHouse("carpenter", "plains", 10);
//		addVillagerHouse("carpenter", "snowy", 11);
//		addVillagerHouse("carpenter", "savanna", 7);
//		addVillagerHouse("carpenter", "desert", 9);
//		addVillagerHouse("carpenter", "taiga", 5);
//	}

//	private static void addVillagerHouse(String type, String biome, int weight) {
//		DataUtil.addToJigsawPattern(new ResourceLocation("village/" + biome + "/houses"), JigsawPiece.single(ChaosAwakens.MODID + ":village/" + type + "_house_" + biome + "_1").apply(JigsawPattern.PlacementBehaviour.RIGID), weight);
//	}
}
