package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableSet;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerBlock.DefossilizerType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAVillagers {
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, ChaosAwakens.MODID);
	public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ChaosAwakens.MODID);

	public static final RegistryObject<PointOfInterestType> ARCHAEOLOGIST_POI = POI_TYPES.register("archaeologist",
			() -> new PointOfInterestType("archaeologist", PointOfInterestType.getBlockStates(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()), 1, 2));
	public static final RegistryObject<PointOfInterestType> ARCHAEOLOGIST_COPPER_POI = POI_TYPES.register("archaeologist_copper",
			() -> new PointOfInterestType("archaeologist_copper", PointOfInterestType.getBlockStates(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()), 1, 2));
	public static final RegistryObject<PointOfInterestType> ARCHAEOLOGIST_CRYSTAL_POI = POI_TYPES.register("archaeologist_crystal",
			() -> new PointOfInterestType("archaeologist_crystal", PointOfInterestType.getBlockStates(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get()), 1, 2));

	public static final RegistryObject<VillagerProfession> ARCHAEOLOGIST = PROFESSIONS.register("archaeologist",
			() -> new VillagerProfession("archaeologist", ARCHAEOLOGIST_POI.get(), ImmutableSet.of(), ImmutableSet.of(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get(), CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get()), SoundEvents.VILLAGER_WORK_CARTOGRAPHER));
	public static final RegistryObject<VillagerProfession> ARCHAEOLOGIST_COPPER = PROFESSIONS.register("archaeologist_copper",
			() -> new VillagerProfession("archaeologist_copper", ARCHAEOLOGIST_COPPER_POI.get(), ImmutableSet.of(), ImmutableSet.of(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get(), CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get()), SoundEvents.VILLAGER_WORK_CARTOGRAPHER));
	public static final RegistryObject<VillagerProfession> ARCHAEOLOGIST_CRYSTAL = PROFESSIONS.register("archaeologist_crystal",
			() -> new VillagerProfession("archaeologist_crystal", ARCHAEOLOGIST_CRYSTAL_POI.get(), ImmutableSet.of(), ImmutableSet.of(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get(), CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get()), SoundEvents.VILLAGER_WORK_CARTOGRAPHER));

	@SuppressWarnings("unused")
	private static VillagerType createType(String type) {
		return VillagerType.register(ChaosAwakens.prefix(type).toString());
	}

	@SuppressWarnings("unused")
	@SafeVarargs
	private static void registerVillagerType(VillagerType type, RegistryKey<Biome>... biomes) {
		for (RegistryKey<Biome> biome : biomes) VillagerType.BY_BIOME.put(biome, type);
	}
}
