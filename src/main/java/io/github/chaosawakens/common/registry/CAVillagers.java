package io.github.chaosawakens.common.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern.PlacementBehaviour;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
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
			() -> new PointOfInterestType("archaeologist", PointOfInterestType.getBlockStates(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get()), 1, 2));
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
		addToPool(new ResourceLocation("minecraft:village/desert/houses"), "chaosawakens:village/desert/desert_achaeologist_house", 250);
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
	
	public static void registerVillagerStructures() {	
		
		DesertVillagePools.bootstrap();
		PlainsVillagePools.bootstrap();
		SavannaVillagePools.bootstrap();
		SnowyVillagePools.bootstrap();
		TaigaVillagePools.bootstrap();
		
	//	ChaosAwakens.debug("Village Generation", "Generating village structure ");
	//	registerVillagerStructureToPool(templatePoolRegistry, new ResourceLocation("minecraft:village/desert/houses"), "chaosawakens:village/desert/desert_achaeologist_house", PlacementBehaviour.RIGID, 400);
		addToPool(new ResourceLocation("minecraft:village/desert/houses"), "chaosawakens:village/desert/desert_achaeologist_house", 16000);
//		registerVillagerStructureToPool(templatePoolRegistry, new ResourceLocation("minecraft:village/savannah/houses"), "chaosawakens:village/desert/desert_achaeologist_house",  150);
//		registerVillagerStructureToPool(templatePoolRegistry, new ResourceLocation("minecraft:village/plains/houses"), "chaosawakens:village/desert/desert_achaeologist_house", 150);
//		registerVillagerStructureToPool(templatePoolRegistry, new ResourceLocation("minecraft:village/taiga/houses"), "chaosawakens:village/desert/desert_achaeologist_house", 150);
		//		registerVillagerStructureToPool(event.getServer(), new ResourceLocation("minecraft:village/taiga/houses"), new ResourceLocation("chaosawakens:village/taiga/taiga_archaeologist"), 10);
	}
	
    private static void registerVillagerStructureToPool(MutableRegistry<JigsawPattern> templatePoolRegistry, ResourceLocation poolRL, String nbtPieceRL, PlacementBehaviour projection, int weight) {
        JigsawPattern pool = templatePoolRegistry.get(poolRL);
        if (pool == null) return;

        SingleJigsawPiece piece = poolRL.getPath().endsWith("streets") ? SingleJigsawPiece.legacy(nbtPieceRL).apply(projection) : SingleJigsawPiece.single(nbtPieceRL).apply(projection);


        for (int i = 0; i < weight; i++) {
            pool.templates.add(piece);
        }

        List<Pair<JigsawPiece, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
        listOfPieceEntries.add(new Pair<>(piece, weight));
        pool.rawTemplates = listOfPieceEntries;
    }
    
    private static void addToPool(ResourceLocation pool, String toAdd, int weight) {
        JigsawPattern old = WorldGenRegistries.TEMPLATE_POOL.get(pool);
        if (old == null) return;
        List<JigsawPiece> shuffled = old.getShuffledTemplates(ThreadLocalRandom.current());
        List<Pair<JigsawPiece, Integer>> newPieces = shuffled.stream().map(p -> Pair.of(p, 1)).collect(Collectors.toList());
        JigsawPiece newPiece = JigsawPiece.legacy(toAdd).apply(PlacementBehaviour.RIGID);
        newPieces.add(Pair.of(newPiece, weight));
        Registry.register(WorldGenRegistries.TEMPLATE_POOL, pool, new JigsawPattern(pool, old.getName(), newPieces));
    }
}
